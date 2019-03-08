package com.fh.service.system.shezhi;

import java.util.List;

import com.fh.entity.system.Menu;
import com.fh.util.PageData;


/**说明：MenuService 菜单处理接口
 * @author zhoudibo
 */
public interface SheZhiManager {

	public PageData getOne(PageData pd) throws Exception;
	public void savetb(PageData pd) throws Exception;
	public void updatetb(PageData pd)throws Exception;
	
}
