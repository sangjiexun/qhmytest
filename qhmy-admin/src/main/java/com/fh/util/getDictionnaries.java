package com.fh.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fh.entity.system.Dictionaries;
import com.google.gson.JsonObject;

import net.sf.ehcache.Cache;
import net.sf.json.JSON;
import net.sf.json.JSONArray;

@Service("getDectionnaries")
public class getDictionnaries {

	Cache cache = Const.manager.getCache("dicserviceCache");
	
	
	public  List<Dictionaries> getSunDict(String bianma){
		 List<Dictionaries> dic=null;
		 if(bianma.equals("") || bianma==null){
			 dic=null;
		 }else{
			 Map   ca= (Map) cache.get(bianma).getObjectValue();
			 Dictionaries Dictionarie= (Dictionaries) ca.get(bianma);
			 ArrayList list = new ArrayList();
			 dic=Dictionarie.getSubDict(); 
		 }
		  return dic;
	}
	
	public  List<Dictionaries> getLinkList(String bianma){
		List listdic=null;
		if(bianma.equals("") || bianma==null){
			listdic=null;
		}else{
			  Map   ca= (Map) cache.get(bianma).getObjectValue();
			  Dictionaries Dictionarie= (Dictionaries) ca.get(bianma);
			  ArrayList list = new ArrayList();
			  List<Dictionaries> dic=Dictionarie.getLinkList();
			  JSONArray arr = JSONArray.fromObject(dic);
			  listdic = (List)JSONArray.toCollection(arr, Dictionaries.class); 
		}
		  return listdic;
	}


	
	
	
}
