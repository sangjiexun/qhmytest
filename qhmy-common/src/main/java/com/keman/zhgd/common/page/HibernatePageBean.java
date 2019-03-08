package com.keman.zhgd.common.page;

/**
 * 此类主要用于Hibernate与Struts架构中对List进行subList的分页显示
 * 
 * @author Administrator
 * 
 */
public class HibernatePageBean {

	private int currentPage; // 当前页数
	private int totalRow; // 总记录数
	private int pageStartRow; // 开始的记录位置
	private int pageRecord; // 每页显示的记录数
	private int totalPages = 0; // 总页数
	private int toRecord; // 当前应该显示到得位置
	private int startYm;//开始页码  为前台页面显示设置的变量
	private int endym;//结束页码 为前台分页样式显示设置的变量
	
	public static final String navTab = "navTab";
	public static final String dialog = "dialog";
	
	/*
	 * 为DMZ框架返回变量
	 * targetType，totalCount，numPerPage，pageNumShown，currentPage
	 */
	private String targetType = "navTab";//navTab或dialog，用来标记是navTab上的分页还是dialog上的分页
	private String pageNumShown = "5";//页标数字多少个
	

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(String pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public HibernatePageBean() {

	}

	/**
	 * 构造函数
	 * 
	 * @param pageRecord
	 *            每页显示数
	 * @param totalRow
	 *            总行数
	 */
	public HibernatePageBean(int pageRecord, int totalRow) {
		this.totalRow = totalRow;
		this.pageRecord = pageRecord;
		pageStartRow = 0;

		this.totalPages = this.countTotalPages();
		this.currentPage = 1;
		this.countToRecord();

	}

	public void jumpTo(int pageSize) {
		this.pageStartRow = (pageSize - 1) * pageRecord;
		this.currentPage = pageSize;
	}

	/**
	 * 做首页显示处理
	 * 
	 */
	public void firstPage() {
		this.currentPage = 1;
		pageStartRow = 0;
		this.countToRecord();
	}

	/**
	 * 做下一页的处理
	 * 
	 */
	public void nextPage() {
		if (currentPage == totalPages) { // 如果这一页是最后一页
			currentPage = totalPages;
			this.toRecord = totalRow;
		} else { // 这一页不是最后一页
			this.currentPage = this.currentPage + 1;
			this.countToRecord();
		}
		this.pageStartRow = currentPage * pageRecord - pageRecord;
	}

	/**
	 * 做上一页的处理
	 * 
	 */
	public void previousPage() {
		if (currentPage == 1) {
			currentPage = 1;
			this.pageStartRow = 0;
			this.countToRecord();
		} else {
			currentPage = currentPage - 1;
			this.pageStartRow = currentPage * pageRecord - pageRecord;
			this.countToRecord();
		}
	}

	/**
	 * 做最后一页处理
	 * 
	 */
	public void lastpage() {
		this.currentPage = totalPages;
		this.pageStartRow = currentPage * pageRecord - pageRecord;
		this.countToRecord();
	}

	/**
	 * 统计总页面数
	 * 
	 * @return
	 */
	public int countTotalPages() {
		int totalPage = 0;
		if (totalRow % pageRecord == 0) {
			if (totalRow < pageRecord) {
				totalPage = 1;
			} else {
				totalPage = totalRow / pageRecord;
			}
		} else {
			totalPage = totalRow / pageRecord + 1;
		}
		return totalPage;
	}

	/**
	 * 对应该到达的记录数位置做简单判断函数。 即判断总记录数和当前页应该显示到的记录数
	 * 
	 */
	public void countToRecord() {
		if (totalRow >= currentPage * pageRecord) { // 如果总页数大于当前页显示到的记录数
			this.toRecord = currentPage * pageRecord;
		} else {
			this.toRecord = totalRow;
		}
	}

	/**
	 * 设置开始的记录数
	 * 
	 * @param pageStartRow
	 */
	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	/**
	 * 得到开始的记录数
	 * 
	 * @return
	 */
	public int getPageStartRow() {
		return pageStartRow;
	}

	/**
	 * 得到每一页面显示的记录数
	 * 
	 * @return
	 */
	public int getPageRecord() {
		return pageRecord;
	}

	/**
	 * 设置第一页面要显示的记录数11111
	 * 
	 * @param pageRecord
	 */
	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setToRecord(int toRecord) {
		this.toRecord = toRecord;
	}

	public int getToRecord() {
		return toRecord;
	}

	/**
	 * 解决struts架构中线程不安全问题
	 * 
	 * @param currentPage
	 * @param totalRow
	 * @param pageStartRow
	 * @param pageRecord
	 */
	public HibernatePageBean(int currentPage, int totalRow, int pageStartRow,
			int pageRecord) {
		this.currentPage = currentPage;
		this.totalRow = totalRow;
		this.pageStartRow = pageStartRow;
		this.pageRecord = pageRecord;
		this.totalPages = this.countTotalPages();
	}

	public int getStartYm() {
		return startYm;
	}

	public void setStartYm(int start) {
		this.startYm = start;
	}

	public int getEndym() {
		return endym;
	}

	public void setEndym(int endym) {
		this.endym = endym;
	}
	
	//计算前台页码
	public void jisuanQiantaiyema(){
		int avg=5;
		int dangqian = this.currentPage;
		int zys = this.totalPages;
		int start = 0;
		if (dangqian%avg==0) {
			start = (dangqian/avg-1)*avg+1;
		}else {
			start = dangqian/avg*avg+1;
		}
		int end = start+avg-1;
		if (end>zys) {
			end = zys;
		}
		this.setStartYm(start);
		this.setEndym(end);
	}
	

	/**
	 * 返回sql语句
	 * @param string
	 * @return
	 */
	public String getFenyeSql(String sql) {
		int kaishiweizhi = this.pageStartRow;//开始位置
		int endIndex = kaishiweizhi + pageRecord;  
        String endSql = "select * from (select rOraclePageSQL.*,ROWNUM as currentRow from (" +  
        	sql + ") rOraclePageSQL where rownum <=" + endIndex + ") where currentRow>" + kaishiweizhi;  
		
		return endSql;
	}
}
