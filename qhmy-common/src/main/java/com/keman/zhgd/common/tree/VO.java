package com.keman.zhgd.common.tree;

import java.util.ArrayList;
import java.util.List;


public class VO /*implements Comparable<VO>*/{
         private String id;
         private String name;
         private String parentId;
         private String state;
         private String isXM;
         private String paixu;
         private String type;
         
		public String getIsXM() {
			return isXM;
		}
		public void setIsXM(String isXM) {
			this.isXM = isXM;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		@Override
		public String toString() {
			return "VO [id=" + id + ", name=" + name + ", parentId=" + parentId+ ", state=" + state+ ", isXM=" + isXM+ ", type=" + type
					+ ",paixu="+paixu+"]";
		}
		/*@Override
		public int compareTo(VO o) {
			Collator collator = Collator.getInstance(Locale.CHINA);
			CollationKey key1 = collator.getCollationKey(o.getName());  
            CollationKey key2 = collator.getCollationKey(this.getName());  
            return key2.compareTo(key1); 
		}*/
         
         public VO(String id, String name, String parentId, String state,
				String isXM,String paixu) {
			super();
			this.id = id;
			this.name = name;
			this.parentId = parentId;
			this.state = state;
			this.isXM = isXM;
			this.paixu = paixu;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public VO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public static void main(String[] args) {
			List<VO> list = new ArrayList<>();
			VO vo1 = new VO("1","王宁","1","","","1"); 
			VO vo2 = new VO("1","刘宁","1","","","2"); 
			VO vo3 = new VO("1","张宁","1","","","3"); 
			VO vo4 = new VO("1","啊宁","1","","","4"); 
			VO vo5 = new VO("1","合宁","1","","","5"); 
			VO vo6 = new VO("1","此宁","1","","","6");
			list.add(vo1);
			list.add(vo2);
			list.add(vo3);
			list.add(vo4);
			list.add(vo5);
			list.add(vo6);
			list.add(vo1);
//			Collections.sort(list);
			System.out.println(list);
		}
		public String getPaixu() {
			return paixu;
		}
		public void setPaixu(String paixu) {
			this.paixu = paixu;
		}
         
}
