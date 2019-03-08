package com.fh.controller.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.service.system.upload.UploadManager;
import com.fh.util.PageData;
import com.fh.util.upload.Configurations;
import com.fh.util.upload.IoUtil;
import com.fh.util.upload.Range;
import com.fh.util.upload.StreamException;
import com.fh.util.upload.TokenUtil;
import com.fh.util.upload.UploadConst;

@Controller
@RequestMapping(value="/file")
public class FileUploadController extends BaseController {
	
	static final String FILE_NAME_FIELD = "name";
	static final String FILE_SIZE_FIELD = "size";
	static final String TOKEN_FIELD = "token";
	static final String SERVER_FIELD = "server";
	static final String SUCCESS = "success";
	static final String MESSAGE = "message";
	static final String TABLENAME = "tableName";//哪个表的附件
	static final String MULU = "mulu";//目录，每次上传，需要创建一个目录,文件名存储的是真实名称,所以目录要随机
	
	
	static final int BUFFER_LENGTH = 10240;//字节
	static final String START_FIELD = "start";
	public static final String CONTENT_RANGE_HEADER = "content-range";
	
	@Resource(name="uploadService")
	private UploadManager uploadService;
	
	
	/**
	 * tokenURL
	 * 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/token.json")
	@ResponseBody
	public ModelAndView token(HttpServletRequest req) throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = this.getPageData();
		
		String name = req.getParameter(FILE_NAME_FIELD);//得到name
		//name解码
		name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		String tableName = req.getParameter(TABLENAME);
		String mulu = req.getParameter(MULU);//目录
		String size = req.getParameter(FILE_SIZE_FIELD);//得到size
		String token = TokenUtil.generateToken(tableName,mulu,name, size);//得到token  并且创建token为名称的路径文件
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(TOKEN_FIELD, token);
		if (Configurations.isCrossed()){
			map.put(SERVER_FIELD, Configurations.getCrossServer());
		}
		map.put(SUCCESS, true);
		map.put(MESSAGE, "");
		/** TODO: save the token. */
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	/**
	 * Stream HTML5 上传路径  uploadURL
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/upload.json",method= RequestMethod.GET)
	@ResponseBody
	public ModelAndView uploadGet(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Map<String, Object> map = new HashMap<String,Object>();
		
		
		final String token = req.getParameter(TOKEN_FIELD);
		final String size = req.getParameter(FILE_SIZE_FIELD);
		String fileName = req.getParameter(FILE_NAME_FIELD);
		//name解码
		fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
		String tableName = req.getParameter(TABLENAME);
		String mulu = req.getParameter(MULU);//目录
		
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f = IoUtil.getTokenedFile(tableName,mulu,token);//得到token文件
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start){
				f.renameTo(IoUtil.getFile(tableName,mulu,fileName));
			}
		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			if (success){
				map.put(START_FIELD, start);
			}
			map.put(SUCCESS, success);
			map.put(MESSAGE, message);
		}
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	@RequestMapping(value="/upload.json",method= RequestMethod.POST)
	@ResponseBody
	public ModelAndView uploadPost(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Map<String, Object> map = new HashMap<String,Object>();
		
		final String token = req.getParameter(TOKEN_FIELD);
		String tableName = req.getParameter(TABLENAME);
		String mulu = req.getParameter(MULU);//目录
		String fileName = req.getParameter(FILE_NAME_FIELD);
		//name解码
		fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
		Range range = IoUtil.parseRange(req);
		
		OutputStream out = null;
		InputStream content = null;
		
		/** TODO: validate your token. */
		
		long start = 0;
		boolean success = true;
		String message = "";
		File f = IoUtil.getTokenedFile(tableName,mulu,token);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}
			
			out = new FileOutputStream(f, true);
			content = req.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[BUFFER_LENGTH];
			while ((read = content.read(bytes)) != -1)
				out.write(bytes, 0, read);

			start = f.length();
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);

			/** rename the file */
			if (range.getSize() == start) {
				/** fix the `renameTo` bug */
//				File dst = IoUtil.getFile(fileName);
//				dst.delete();
				// TODO: f.renameTo(dst); 重命名在Windows平台下可能会失败，stackoverflow建议使用下面这句
				try {
					// 先删除
					IoUtil.getFile(tableName,mulu,fileName).delete();
					
					Files.move(f.toPath(), f.toPath().resolveSibling(fileName));
					System.out.println("TK: `" + token + "`, NE: `" + fileName + "`");
					
					/** if `STREAM_DELETE_FINISH`, then delete it. */
					if (Configurations.isDeleteFinished()) {
						IoUtil.getFile(tableName,mulu,fileName).delete();
					}
				} catch (IOException e) {
					success = false;
					message = "Rename file error: " + e.getMessage();
				}
				
			}
			
			if (success){
				map.put(START_FIELD, start);
			}
			map.put(SUCCESS, success);
			map.put(MESSAGE, message);
		}
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	/**
	 * FormData  frmUploadURL
	 * Flash上传的URI
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/fd.json")
	@ResponseBody
	public ModelAndView fd(HttpServletResponse resp,@RequestParam(value="tableName") String tableName,
			@RequestParam(value="mulu") String mulu,
			@RequestParam(value="FileData") MultipartFile [] files) throws Exception{
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Map<String, Object> map = new HashMap<String,Object>();
		
		/** flash @ windows bug */
        req.setCharacterEncoding("utf8");

        // Check that we have a file upload request
//        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
//        if (!isMultipart) {
//            map.put(SUCCESS, false);
//            return new ModelAndView(new MappingJackson2JsonView(),map);
//        }
        
        long start = 0;
        boolean success = true;
        String message = "";

//        ServletFileUpload upload = new ServletFileUpload();
        InputStream in = null;
        String token = null;
        try {
            String filename = null;
            for (MultipartFile item : files) {
//                String name = item.getName();
                in = item.getInputStream();
//                if (false) {
////                    String value = Streams.asString(in);
////                    if (TOKEN_FIELD.equals(name)) {
////                        token = value;
////                        /** TODO: validate your token. */
////                    }
////                    System.out.println(name + ":" + value);
//                } else {
            if (token == null || token.trim().length() < 1){
            	token = req.getParameter(TOKEN_FIELD);
            }
            /** TODO: validate your token. */

            // 这里不能保证token能有值
            filename = item.getOriginalFilename();
            if (token == null || token.trim().length() < 1){
            	token = filename;
            }
            
//            String tableName = req.getParameter(TABLENAME);//得到tableName
//            String mulu = req.getParameter(MULU);//目录
            
            start = IoUtil.streaming(tableName, mulu, in, token, filename);
//                }
			}
            System.out.println("Form Saved : " + filename);
        } catch (Exception fne) {
            success = false;
            message = "Error: " + fne.getLocalizedMessage();
        } finally {
            if (success){
            	/*
				 * 解析excel文件
				 */
				
				//end 解析excel文件
            	map.put(START_FIELD, start);
            }
            map.put(SUCCESS, success);
            map.put(MESSAGE, message);
            IoUtil.close(in);
        }
        return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	/**
	 * 下载
	 * duiwupkid  队伍ID
	 * tableName  表名称
	 * fileid 文件ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/download")
	public void download(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PageData pageData = this.getPageData();
		
		String tableName = pageData.getString("tableName");
		
		
//		PrintWriter printWriter = response.getWriter();
		
		File file = null;
		
		String fileName = "";
		
		if (UploadConst.T_FENBAOSHANG_FJ.equals(tableName)) {
			//获取文件路径
			List<PageData> pageDatas = uploadService.getFenbaoshangFj(pageData);
			if (pageDatas == null || pageDatas.size()==0) {
				return;
			}
			PageData data = pageDatas.get(0);
			
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_FENBAOSHANG_FJ).getAbsolutePath();
			
			String FUJIANLUJING = data.getString("FUJIANLUJING");
			FUJIANLUJING = FUJIANLUJING.replaceAll(UploadConst.uploadPathMap.get(tableName).getRelativePath(),"");
			
			String filePath = basePath + File.separator + FUJIANLUJING;
			
			file = new File(filePath);
			
			fileName = data.getString("ZHENSHIMC");
//			tableName=T_QIYEZIZHI_FJ, CLIENT_ID=file_1489804433154_01v_1, TRNUMBER=2, fileid=file_1489804433154_01v_1, duiwupkid=1d92cf7054194a37aa28a8f3bace5bae
		}else if (UploadConst.T_QIYEZIZHI_FJ.equals(tableName)) {
			List<PageData> pageDatas = uploadService.getQiyezizhiFj(pageData);
			if (pageDatas == null || pageDatas.size()==0) {
				return;
			}
			PageData data = pageDatas.get(0);
			
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_QIYEZIZHI_FJ).getAbsolutePath();
			
			String FUJIANLUJING = data.getString("FUJIANLUJING");
			FUJIANLUJING = FUJIANLUJING.replaceAll(UploadConst.uploadPathMap.get(tableName).getRelativePath(),"");
			
			String filePath = basePath + File.separator + FUJIANLUJING;
			
			file = new File(filePath);
			
			fileName = data.getString("ZHENSHIMC");
			
		}else if (UploadConst.T_HEIMINGDAN_FJ.equals(tableName)) {
			//获取文件路径
			List<PageData> pageDatas = uploadService.getHeimingdanFj(pageData);
			if (pageDatas == null || pageDatas.size()==0) {
				return;
			}
			PageData data = pageDatas.get(0);
			
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_HEIMINGDAN_FJ).getAbsolutePath();
			
			String FUJIANLUJING = data.getString("FUJIANLUJING");
			FUJIANLUJING = FUJIANLUJING.replaceAll(UploadConst.uploadPathMap.get(tableName).getRelativePath(),"");
			
			String filePath = basePath + File.separator + FUJIANLUJING;
			
			file = new File(filePath);
			
			fileName = data.getString("ZHENSHIMC");
			
		}else if (UploadConst.T_LAOWURENYUAN_HT.equals(tableName)) {
			
			//获取文件路径
			List<PageData> pageDatas = uploadService.getLaowurenyuanFj(pageData);
			if (pageDatas == null || pageDatas.size()==0) {
				return;
			}
			PageData data = pageDatas.get(0);
			
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_LAOWURENYUAN_HT).getAbsolutePath();
			
			String FUJIANLUJING = data.getString("HETONGLUJING");
			FUJIANLUJING = FUJIANLUJING.replaceAll(UploadConst.uploadPathMap.get(tableName).getRelativePath(),"");
			
			String filePath = basePath + File.separator + FUJIANLUJING;
			
			file = new File(filePath);
			
			fileName = data.getString("ZHENSHIMC");
			
		}else if (UploadConst.T_GONGZI_FJ.equals(tableName)) {
			
			//获取文件路径
			List<PageData> pageDatas = uploadService.getGongZiFj(pageData);
			if (pageDatas == null || pageDatas.size()==0) {
				return;
			}
			PageData data = pageDatas.get(0);
			
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_GONGZI_FJ).getAbsolutePath();
			
			String FUJIANLUJING = data.getString("FUJIANLUJING");
			FUJIANLUJING = FUJIANLUJING.replaceAll(UploadConst.uploadPathMap.get(tableName).getRelativePath(),"");
			
			String filePath = basePath + File.separator + FUJIANLUJING;
			
			file = new File(filePath);
			
			fileName = data.getString("ZHENSHIMC");
			
		}else if (UploadConst.T_ANQUANJIAOYU_FJ.equals(tableName)) {
			
			//获取文件路径
			List<PageData> pageDatas = uploadService.getAnQuanJyFj(pageData);
			if (pageDatas == null || pageDatas.size()==0) {
				return;
			}
			PageData data = pageDatas.get(0);
			
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_ANQUANJIAOYU_FJ).getAbsolutePath();
			
			String FUJIANLUJING = data.getString("FUJIANLUJING");
			FUJIANLUJING = FUJIANLUJING.replaceAll(UploadConst.uploadPathMap.get(tableName).getRelativePath(),"");
			
			String filePath = basePath + File.separator + FUJIANLUJING;
			
			file = new File(filePath);
			
			fileName = data.getString("ZHENSHIMC");
			
		}else if (UploadConst.T_JIANGCHENG_FJ.equals(tableName)) {
			
			//获取文件路径
			List<PageData> pageDatas = uploadService.getJcFj(pageData);
			if (pageDatas == null || pageDatas.size()==0) {
				return;
			}
			PageData data = pageDatas.get(0);
			
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_JIANGCHENG_FJ).getAbsolutePath();
			
			String FUJIANLUJING = data.getString("FUJIANLUJING");
			FUJIANLUJING = FUJIANLUJING.replaceAll(UploadConst.uploadPathMap.get(tableName).getRelativePath(),"");
			
			String filePath = basePath + File.separator + FUJIANLUJING;
			
			file = new File(filePath);
			
			fileName = data.getString("ZHENSHIMC");
			
		}else if (UploadConst.T_LAOWURENYUAN_ZS.equals(tableName)) {
			
//			?tableName=T_LAOWURENYUAN_ZS&MULU="+ZSPKID+"&FILENAME="+file.name;"
			
			String MULU = pageData.getString("MULU");
			String filename = pageData.getString("FILENAME");
			
//			filename = URLDecoder.decode(filename, "GBK");
			
			filename = new String(filename.getBytes("ISO-8859-1"),"UTF-8");
			
			String lujing = "";
			String basePath = UploadConst.uploadPathMap.get(UploadConst.T_LAOWURENYUAN_ZS).getAbsolutePath();
			
			String FUJIANLUJING = MULU+File.separator+filename;
			
			
			
			
			lujing = basePath + File.separator + FUJIANLUJING;
			
			file = new File(lujing);
		}
		
		
		
		if (file == null) {
			return;
		}
		
		
		if (file.exists()) {
			response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/x-msdownload;charset=utf-8");// 定义输出类型
			
			FileInputStream fileInputStream = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();
			int filesize = fileInputStream.available();
			byte [] b = new byte[filesize];
			try {
				fileInputStream.read(b);
				out.write(b);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				out.close();
				fileInputStream.close();
			}
		}else{
			return;
		}
		
	}
	
	
}
