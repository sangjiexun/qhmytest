package com.keman.zhgd.common.orgtree;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户所属组织树层级结构树
 * @author Administrator
 *
 */
public class OrgTree {
	
	private int bianmaLength = 6;
	
	/**
	 * 1、用户上级直属组织
	 * 2、用户所有根组织（企业节点，即总公司节点）
	 * 使用list存储，0就是根节点,size-1就是直属组织
	 */
	private List<OrgNode> list = new ArrayList<OrgNode>();
	
	
	public OrgTree(String bianma){
		initOrgTree(bianma);
	}
	
	/**
	 * 通过组织编码初始化树  
	 */
	public void initOrgTree(String bianma){
		
		if (bianma == null || "".equals(bianma.trim()) || "null".equals(bianma.trim())) {
			return;
		}
		bianma = bianma.trim();
		
		//E00001
		if (bianma.length()<=bianmaLength) {
			addNode(bianma);
		}else {
			String prix = null;
			prix = bianma.substring(bianmaLength);
			
			if (prix.length()%4!=0) {
				throw new RuntimeException(bianma+",编码不合法!");
			}else {
				String mainBianma = bianma.substring(0, bianmaLength);//主编码
				//添加根节点
				addNode(mainBianma);
				
				for (int i = 0; i < prix.length()/4; i++) {
					addNode(bianma.substring(0, bianmaLength + (i+1)*4 ));
				}
				
			}
		}
	}
	
	
	private void addNode(String bianma){
		OrgNode orgNode = new OrgNode();
		orgNode.setBianma(bianma);//
		list.add(orgNode);
	}
	
	
	/**
	 * 获取企业节点
	 */
	public OrgNode getTopOrgNode(){
		return list.get(0);
	}
	
	/**
	 * 获取最后一级节点对象OrgNode
	 * @return
	 */
	public OrgNode getLastOrgNode(){
		return list.get(list.size()-1);
	}
	
	protected void display() {
		for (OrgNode orgNodes : list) {
			System.out.println(orgNodes.getBianma());
		}
	}

	/**
	 * 根据层级返回OrgNode节点对象 ,从1开始
	 * @param level
	 * @return
	 * @throws RuntimeException
	 */
	public OrgNode getOrgNodeByLevel(int level) throws RuntimeException{
		if (level == 0) {
			throw new RuntimeException("level必须大于0");
		}
		
		if (level>list.size()) {
			throw new RuntimeException("level不能超过树层级大小");
		}
		
		if (list.size()>0) {
			return list.get(level-1);
		}else {
			return list.get(0);
		}
	}
	
	public List<OrgNode> getList() {
		return list;
	}

	public void setList(List<OrgNode> list) {
		this.list = list;
	}
	
	/**
	 * 返回层级数量
	 * @return
	 */
	public int getLevelSize(){
		return this.list.size();
	}

	public static void main(String[] args) {
		OrgTree orgTree = new OrgTree("E018420003");
//		orgTree.display();
		
		System.out.println(orgTree.getLastOrgNode().getBianma());
		System.out.println(orgTree.getTopOrgNode().getBianma());
//		System.out.println(orgTree.getOrgNodeByLevel(4).getBianma());
		
	}
	
}
