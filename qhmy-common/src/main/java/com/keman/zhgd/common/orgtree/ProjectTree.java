package com.keman.zhgd.common.orgtree;

public class ProjectTree {
	
	private OrgTree orgTree;
	
	/**
	 * 项目编码
	 */
	private String bianma;
	
	/**
	 * 项目ID
	 */
	private String id;
	
	/**
	 * 项目编码,去掉E开头的组织编码，如：P0001
	 */
	private String bianma_head;

	public OrgTree getOrgTree() {
		return orgTree;
	}

	public void setOrgTree(OrgTree orgTree) {
		this.orgTree = orgTree;
	}
	
	
	
	public String getBianma() {
		return bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBianma_head() {
		return bianma_head;
	}

	public void setBianma_head(String bianma_head) {
		this.bianma_head = bianma_head;
	}

	public ProjectTree(String bianma){
		init(bianma);
	}
	
	public void init(String bianma){
		if (bianma == null || "".equals(bianma.trim()) || "null".equals(bianma.trim())) {
			throw new RuntimeException("项目编码为空");
		}
		bianma = bianma.trim();
		
		if (bianma.length()<=5) {
			throw new RuntimeException("项目编码不符合标准");
		}
		setBianma(bianma);
		setBianma_head(bianma.substring(0, 5));
		
		String orgBianma = bianma.substring(5);
		
		System.out.println(orgBianma);
		
		this.orgTree = new OrgTree(orgBianma);
		
	}
	
	public static void main(String[] args) {
		
		String bianma = "P0001E0000100020005";
		
		ProjectTree projectTree = new ProjectTree(bianma);
		
		System.out.println(projectTree.getBianma_head());
		
		System.out.println(projectTree.getOrgTree().getLevelSize());//层级深度
		
		System.out.println(projectTree.getOrgTree().getOrgNodeByLevel(1).getBianma());//获取顶级编码
		
		System.out.println(projectTree.getOrgTree().getOrgNodeByLevel(2).getBianma());//获取第二级编码
		
		System.out.println(projectTree.getOrgTree().getOrgNodeByLevel(3).getBianma());
		
	}
	
}
