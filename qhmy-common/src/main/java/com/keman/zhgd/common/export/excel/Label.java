package com.keman.zhgd.common.export.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * excel Label对象
 * @author Administrator
 *
 */
public class Label {
	
	public Label(){
		this.labelName = "";
		this.sonLabelList = new ArrayList<Label>();
	}
	
	public Label(String labelName){
		this.labelName = labelName;
		this.sonLabelList = new ArrayList<Label>();
	}
	
	private String labelName;
	
	private List<Label> sonLabelList;
	
	private ExcelTableHeard tableHeardInfo;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public List<Label> getSonLabelList() {
		return sonLabelList;
	}

	public void setSonLabelList(List<Label> sonLabelList) {
		this.sonLabelList = sonLabelList;
	}

	public ExcelTableHeard getTableHeardInfo() {
		return tableHeardInfo;
	}

	public void setTableHeardInfo(ExcelTableHeard tableHeardInfo) {
		this.tableHeardInfo = tableHeardInfo;
	}

	/**
	 * 添加子label
	 * @param label
	 */
	public Label addLabel(Label label){
		this.sonLabelList.add(label);
		return this;
	}
	
}
