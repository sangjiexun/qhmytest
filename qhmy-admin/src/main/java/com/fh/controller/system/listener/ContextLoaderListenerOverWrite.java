package com.fh.controller.system.listener;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fh.entity.system.Dictionaries;
import com.fh.util.Const;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;

public class ContextLoaderListenerOverWrite implements
		ApplicationListener<ContextRefreshedEvent> {



	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		
		/*
		
		
		// System.out.println("Spring容器加载完成触发,可用于初始化环境，准备测试数据、加载一些数据到内存00000000000000000000000000000000000");

		// 防止重复执行。
		if (ev.getApplicationContext().getParent() == null) {

			// System.out.println("Spring容器加载完成触发,可用于初始化环境，准备测试数据、加载一些数据到内存0000000000000000000000000000000");
			try {
				// dictionariesCacheUtil.saveDictionaries();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// URL url = getClass().getResource("/ehcache.xml");
		// CacheManager manager = CacheManager.create(url);
		Cache cache = Const.manager.getCache("serviceCache");
		System.out
				.println("<><><><><>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
						+ cache);
		Map<String, Dictionaries> map;
		Dictionaries dic;
		// System.out.println("llllllllllllllliiissssttt"+dictionariesService.listAllDict("0"));
		try {
			JSONArray arr = JSONArray.fromObject(dictionariesService
					.listAll("0"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Dictionaries> list = null;
		try {
			list = dictionariesService.listAll("0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ff11111111111" + list);
		String bm = null;
		for (int i = 0; i < list.size(); i++) {
			map = new HashMap<String, Dictionaries>();
			// System.out.println(list.size()+"???????????????"+list.get(i).toString());
			dic = new Dictionaries();
			String name = list.get(i).getNAME();
			String dictionnaries = list.get(i).getDICTIONARIES_ID();
			String name_en = list.get(i).getNAME_EN();
			String bianma = list.get(i).getBIANMA();
			String orderby = list.get(i).getORDER_BY();
			String parintid = list.get(i).getPARENT_ID();
			if (parintid.equals("0")) {
				bm = bianma;
			}
			String bz = list.get(i).getBZ();
			String tbsname = list.get(i).getTBSNAME();
			List<Dictionaries> subDict = list.get(i).getSubDict();
			dic.setDICTIONARIES_ID(dictionnaries);
			dic.setNAME(name);
			dic.setNAME_EN(name_en);
			dic.setBIANMA(bianma);
			dic.setORDER_BY(orderby);
			dic.setPARENT_ID(parintid);
			dic.setSubDict(subDict);
			map.put(bm, dic);
			// System.out.println("___________++++++____________"+map.toString());
			Element element = new Element(bm, map);
			cache.put(element);
		}

		// }
	
	 */
	
	}
	

}