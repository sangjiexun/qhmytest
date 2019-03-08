package com.fh.service.system.cengci;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface CengciManager {
	/**
	 * 
	 * <p>描述:查询层次列表数据</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午1:55:24
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> cengci_list(Page page) throws Exception;
	/**
	 * 
	 * <p>描述:根据层次ID查询层次详细信息</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午1:55:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	
	PageData getCengci(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:更新层次信息</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:16:54
	 * @param pd
	 * @throws Exception
	 */
	void update(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:新增层次</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:17:31
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:更新层次启用状态</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:43:27
	 * @param pd
	 * @throws Exception
	 */
	void updateIsUse(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据层次PKID查询是否被学生使用</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:56:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStuJieShao(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:删除层次</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:57:02
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:查询身份证是否重复</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午4:15:55
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getCENGCI_NAME(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取层次编码</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午7:41:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getBianMa(PageData pd)throws Exception;
	
    PageData getCCDAIMA(PageData pd)throws Exception;

}
