package com.keman.zhgd.common.tree;

import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

/**
 * <p>标题:TreeJson</p>
 * <p>描述:将一组数据转换为树结构数据，返回格式为json格式的字符串</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月9日 下午6:00:17
 */
public class TreeJson {
	
	private boolean isPaixuField = false;
	
	public boolean isPaixuField() {
		return isPaixuField;
	}
	public void setPaixuField(boolean isPaixuField) {
		this.isPaixuField = isPaixuField;
	}
	
	/**
	 * 
	 * @param list 数据库查询出来的list
	 * @return
	 * @throws Exception
	 */
	public String init(List list) throws Exception{
		String JsonTree="";
        List  specTableList =  list;
        List dataList = initList(specTableList);  //转换为Map节点
        // 节点列表（散列表，用于临时存储节点对象）    
        LinkedHashMap nodeMap = new LinkedHashMap();    
          // 根节点    
       /* Node root = new  Node();  
        root.id = "0";  
        root.text= "根节点";  
        root.parentId = "";       
        nodeMap.put(root.id, root); */ 
          // 根据结果集构造节点列表（存入散列表）    
        for (Iterator it = dataList.iterator(); it.hasNext();) {    
           Map dataRecord = (Map) it.next();    
           Node node = new Node();    
           node.id = (String) dataRecord.get("id");    
           node.text = (String) dataRecord.get("text");    
           node.parentId = (String) dataRecord.get("parentId");
           node.paixu = (String) dataRecord.get("paixu");
           node.type = (String) dataRecord.get("type");
           if(dataRecord.get("state")!=null&&!"".equals(dataRecord.get("state"))&&!"null".equals(dataRecord.get("state"))){
        	   node.state = (String) dataRecord.get("state");
           }else{
        	   node.state = null;
           }
           nodeMap.put(node.id, node);    
           
        } 
        // 构造无序的多叉树    
        List<Node> list_root = new ArrayList<>();
        Set entrySet = nodeMap.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext();) {    
            Node node = (Node) ((Map.Entry) it.next()).getValue();
//            System.out.println(node.parentId);
//            System.out.println(nodeMap.get(node.parentId));
            if (node.parentId == null || node.parentId.equals("00")|| nodeMap.get(node.parentId)==null) {    
                list_root.add(node);
            } else {
                ((Node) nodeMap.get(node.parentId)).addChild(node);    
            }    
        }
        Collections.sort(list_root);
        
        /*
         * 为各个节点中的子节点进行排序
         */
        for (Node node : list_root) {
        	if (node.children!=null) {
        		node.children.sortChildren();
//        		Collections.sort(node.children.list);
			}
		}
        //end 为各个节点中的子节点进行排序
        
        
        //判断排序规则
        if (isPaixuField) {//按node的paixu字段排序
        	Collections.sort(list_root, new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					int o1Int = 0;
					int o2Int = 0;
					if (o1.paixu == null || "".equals(o1.paixu)) {
						o1Int = 9999999;
					}else {
						o1Int = Integer.parseInt(o1.paixu);
					}
					
					if (o2.paixu == null || "".equals(o2.paixu)) {
						o2Int = 9999999;
					}else {
						o2Int = Integer.parseInt(o2.paixu);
					}
					return o1Int - o2Int;
				}
			});
		}
        
        
        // 输出无序的树形菜单的JSON字符串    
        for(Node root: list_root){
        	JsonTree = JsonTree+root.toString()+",";
        }
        JsonTree = JsonTree.substring(0,JsonTree.length()-1);
        return "["+JsonTree+"]";
	}
	/* 初始化数据    将数据库查询出来的节点由VO类型对象转换为Map
	 * @param specTableList  
	 * @return  
	 */  
	@SuppressWarnings({ "unchecked", "rawtypes" })  
	public List initList(List  specTableList){
	    List dataList = new ArrayList();    
	    HashMap dataRecord = new HashMap();    
	    for(int i = 0; i < specTableList.size(); i++) {  
	        VO VO = (VO)specTableList.get(i);  
	        dataRecord = new HashMap();    
	        dataRecord.put("id",VO.getId());
	        dataRecord.put("text", VO.getName());
	        dataRecord.put("paixu",VO.getPaixu());
	        dataRecord.put("type",VO.getType());
	        if(VO.getState()!=null&&!"".equals(VO.getState())&&!"null".equals(VO.getState())){
	        	dataRecord.put("state", "{\"checked\":"+VO.getState().toLowerCase()+"}");
	        }
	       /* if(VO.getIsXM()!=null&&!"".equals(VO.getIsXM())&&!"null".equals(VO.getIsXM())){
	        	dataRecord.put("state", "{checked:"+VO.getState().toLowerCase()+",selected:"+VO.getIsXM().toLowerCase()+"}");  
	        }*/
	        String parentID = VO.getParentId();  
	        if (StringUtils.isEmpty(parentID)) {  
	            parentID = "0";  
	        }  
	        dataRecord.put("parentId",parentID);   
	        dataList.add(dataRecord);
//	        Collections.sort(dataList);
	    }  
	    return dataList;  
	}  
	  
	  
	class  Node implements Comparable<Node>{  
	    public String id;
	    public String text;
	    public String parentId;
	    public String state;
	    public String isXM;
	    public String paixu;
	    public String type;
	      
	    /**  
	      * 孩子节点列表  
	      */    
	     private Children children = new Children();    
	         
	     // 先序遍历，拼接JSON字符串    
	     public String toString() {      
	      String result = "";  
	          
	      if(state!=null){
	    	  result = "{"   
	    		      + "\"text\":\"" + text + "\","  
	    		      + "\"href\":\"" + id +"\","
	    		      + "\"type\":\"" + type +"\","
	    		      + "\"paixu\":\"" + paixu +"\","
	    		      + "\"state\":" + state +"";
	      }else{
	    	  result = "{"   
	    		      + "\"text\":\"" + text + "\"," 
	    		      + "\"type\":\"" + type +"\","
	    		       + "\"paixu\":\"" + paixu +"\","
	    		      + "\"href\":\"" + id +"\"";
	      }
	      if (children != null && children.getSize() != 0) {  
	        if (result.contains("nodes")) {  
	            result += ",";  
	        }else{  
	            result += ",\"nodes\":" + children.toString();    
	        }  
	      }    
	      return result + "}";    
	     }    
	         
	     // 兄弟节点横向排序    
	     public void sortChildren() {    
	      if (children != null && children.getSize() != 0) {    
	       children.sortChildren();    
	      }    
	     }    
	         
	     // 添加孩子节点    
	     public void addChild(Node node) {    
	      this.children.addChild(node);    
	     }
		@Override
		public int compareTo(Node o) {
			/*Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
		    Collections.sort(list, cmp);*/
			Collator collator = Collator.getInstance(Locale.CHINA);
			CollationKey key1 = collator.getCollationKey(o.text);  
            CollationKey key2 = collator.getCollationKey(this.text);
            System.out.println(key2.compareTo(key1));
            return key2.compareTo(key1);  
		}
	}  
	  
	 class  Children {
	    private List list = new ArrayList();  
	      
	    public int getSize(){  
	        return list.size();  
	    }  
	    public void addChild(Node node){  
	        list.add(node);  
	    }  
	      
	     // 拼接孩子节点的JSON字符串    
	     public String toString() {    
	      String result = "[";      
	      for (Iterator it = list.iterator(); it.hasNext();) {    
	       result += ((Node) it.next()).toString();    
	       result += ",";    
	      }    
	      result = result.substring(0, result.length() - 1);    
	      result += "]";    
	      return result;    
	     }    
	         
	     // 孩子节点排序    
	     public void sortChildren() {    
	      // 对本层节点进行排序    
	      // 可根据不同的排序属性，传入不同的比较器，这里传入排序比较器    
	      Collections.sort(list, new NodePaixuComparator());    
	      // 对每个节点的下一层节点进行排序    
	      for (Iterator it = list.iterator(); it.hasNext();) {    
	       ((Node) it.next()).sortChildren();    
	      }    
	     }    
	         
	    /**  
	     * 节点比较器  
	     */    
	    class NodeIDComparator implements Comparator {
	     // 按照节点编号比较    
	     public int compare(Object o1, Object o2) {    
	      int j1 = Integer.parseInt(((Node)o1).id);    
	         int j2 = Integer.parseInt(((Node)o2).id);    
	         return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));    
	     }
	    }
	    /**  
	     * 节点比较器  
	     */    
	    class NodePaixuComparator implements Comparator {
	     // 按照节点编号比较
	     public int compare(Object o1, Object o2) {
	    	 int o1Int = 0;
				int o2Int = 0;
				if (((Node)o1).paixu == null || "".equals(((Node)o1).paixu)) {
					o1Int = 9999999;
				}else {
					o1Int = Integer.parseInt(((Node)o1).paixu);
				}
				
				if (((Node)o2).paixu == null || "".equals(((Node)o2).paixu)) {
					o2Int = 9999999;
				}else {
					o2Int = Integer.parseInt(((Node)o2).paixu);
				}
				return o1Int - o2Int;
	    }
	}  
	}
}
