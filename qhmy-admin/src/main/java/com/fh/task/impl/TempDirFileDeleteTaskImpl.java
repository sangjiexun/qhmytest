package com.fh.task.impl;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fh.component.UpdatePayItemListComponent;
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.task.TempDirFileDeleteTask;
import com.fh.util.FileUtil;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.Tools;

/**
 * 
 * <p>标题:TempDirFileDeleteTaskImpl</p>
 * <p>描述:1、定时删除垃圾上传的文件
 * 2、定时更新项目的状态，将过期的更新成已结束或已过期
 * 3、更改缴费名单表状态
 * </p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月19日 上午9:14:48
 */
@Component
@Lazy(false)
public class TempDirFileDeleteTaskImpl implements TempDirFileDeleteTask {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public static final String CONFIG_FILE = "task-config.properties";
	
	@Resource
	private PayManageManager payManageService;
	
	@Scheduled(cron=" 0 0/10 * * * ?")   //每天凌晨两点执行一次    0 0 2 * * ?     每3秒执行一次     0/3 * * * * ?      
    @Override  
    public void exce(){
		logger.error("TempDirFileDeleteTaskImpl start");
		ClassLoader loader = TempDirFileDeleteTaskImpl.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(CONFIG_FILE);
		Properties properties = new Properties();
		
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");//项目路径  如：e:\gxjf\
		
		try {
			properties.load(in);
			String basedir = properties.getProperty("basedir");//uploadFiles
			basedir = path + "\\" + basedir + "\\";
			
			String dirs = properties.getProperty("dirs");
			String rootPath = "";
			File file = null;
			if (Tools.notEmpty(dirs)) {
				String [] dirsArray = dirs.split(",");
				if (dirsArray!=null && dirsArray.length>0) {
					for (String dir : dirsArray) {
						rootPath = basedir + dir;//删除此目录下的所有目录和文件
						file = new File(rootPath);
						if (file.exists()) {
							FileUtil.deleteFiles(file);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定时删除垃圾文件出现异常",e);
		}
		
		
		/*
		 * 定时更新项目的状态，将过期的更新成已结束或已过期
		 */
		try {
			updateItemStatus();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TempDirFileDeleteTaskImpl=更新缴费项目状态失败",e);
		}
		//end 定时更新项目的状态，将过期的更新成已结束或已过期
		
		
		/*
		 * 更改缴费名单表状态
		 */
		try {
			updateItemListStatus();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TempDirFileDeleteTaskImpl=更改缴费名单表状态失败", e);
		}
		//end 更改缴费名单状态
		
		
		logger.error("TempDirFileDeleteTaskImpl end!");
    }
	
	/**
	 * <p>描述:更改缴费名单表状态</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月22日 上午8:14:12
	 */
	private void updateItemListStatus() throws Exception {
		//状态   0欠费, 1核验中,2已完成,3已关闭  字典类型:PayItemListStatusEnum
		PageData pageData = new PageData();
		//批量   更新名单状态
		UpdatePayItemListComponent updatePayItemListComponent = new UpdatePayItemListComponent();
		updatePayItemListComponent.updateStatus(payManageService, pageData);
	}

	/**
	 * <p>描述:定时更新项目的状态，将过期的更新成已结束或已过期</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月21日 下午7:01:43
	 * @throws Exception
	 */
	private void updateItemStatus() throws Exception{
		//PageData pd = new PageData();
		//payManageService.updatePayItemStatus(pd);
	}
	
}