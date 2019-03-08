package com.fh.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>标题:ImportErrorEnum</p>
 * <p>描述:导入错误原因</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月17日 上午2:43:27
 */
public enum ImportErrorEnum {
	
	not_find_item("找不到缴费项目"),
	no_avail_data("无效数据,行值为全部空"),
	core_data_empty("关键值为空"),
	schoolname_data_empty("合作学校名称不能为空"),
	schoolname_data_exist("系统中已存在相同的合作学校名称"),
	idcard_data_empty("身份证号为空"),
	not_find_student("找不到学生档案"),
	not_bm_student("该学生还未报名本项目"),
	not_find_stuList("该学生没有对应名单"),
	not_jianmian_over("减免金额大于当前费用"),
	not_jianmian_error("减免金额不合法"),
	not_total_error("缴费总额输入不合法"),
	not_cash_error("现金输入不合法"),
	not_card_error("银行卡金额输入不合法"),
	not_wx_error("微信金额输入不合法"),
	not_zfb_error("支付宝金额输入不合法"),
	not_tt_error("电汇金额输入不合法"),
	card_error("银行卡号输入不合法"),
	exists_find_stuPayList("该学生已有缴费记录"),
	has_trans_record("已有缴费记录"),
	has_item_list("已存在缴费名单"),
	update_fail("更新失败"),
	insert_fail("插入失败"),
	no_difference("选择的缴费项目与文件中缴费项目不一致"),
	no_difference_grade("入学年份与导入项目入学年份不一致"),
	no_difference_classType("班型与导入项目班型不一致"),
	stunotexistinlist("请先导入缴费名单"),
	jffb_difference("发布项目名称与文件不一致"),
	jffbje_fail("直减金额不能大于发布金额"),
	jffbje_fail2("优惠金额不能大于费用"),
	not_find_stu("根据身份证号找不到学生档案"),
	pay_mode_fail("费用类型输入有误"),
	pay_money_fail("费用金额保留两位小数，且为正数"),
	pay_money_error("费用金额不是数字"),
	stualreadyexisted("学生信息已存在"),
	stusfzalreadyexisted("学生身份证号已存在"),
	stuIphoneError("学生手机号格式不正确"),
	yzbmphoneError("邮政编码格式不正确"),
	jtcylxdhError("家庭成员联系电话格式不正确"),
	xuehaoError("学号只能输入数字和字母且不能超过20位"),
	stuIdCardError("学生身份证号格式不正确"),
	wenlikeError("文理科格式不正确"),
	toudangchengjiError("投档成绩只能输入整数或保留一位的小数"),
	lkfsError("联考分数只能输入大于0的整数或小数(最多保留两位小数)"),
	stunotexist("学生信息不存在"),
	dzxexist("该学生已缴过带子项的住宿费"),
	stuxhalreadyexisted("学生学号已存在"),
	deptnameerror("所属组织名称有误"),
	deptBianMaerror("院校专业编码有误"),
	payitemnameerror("项目名称错误"),
	paymoneywrong("各项金额与总金额不一致"),
	itemStateerror("项目审核状态不是待审核和审核成功"),
	payitemstatuserror("缴费项目状态不是审核成功或已结束"),
	stustatuserror("学生在学状态填写错误"),
	isposterror("学生是否寄读填写错误"),
	xuejistatuserror("学籍状态填写错误"),
	stucard_cffail("表中存在相同身份证号的数据"),
	schoolname_cffail("表中存在相同学校名称的数据"),
	stuxuehao_cffail("表中存在相同学号的数据"),
	stu_flags_error("学生特殊标记填写错误"),
	stuspecial_flags_error("学生特长填写错误"),
	money_input_error("金额小数点后最多为两位数"),
	grade_input_error("入学年份信息输入有误"),
	xuehaorepeat_input_error("系统中已存在相同学号的学生"),
	banxing_input_error("班型信息输入有误"),
	bxrnsfn_input_error("同一身份证号、入学年份、班型的学生系统中已经存在"),
	bxrnsfn_add_error("表中存在同一身份证号、入学年份、班型的学生"),
	yjgrade_input_error("预交年份信息输入有误"),
	yjbanxing_input_error("预交班型信息输入有误"),
	yjbxnf_input_error("预交年份和预交班型必须同时有值"),
	yjsfznfbx_input_error("此身份证号的学生已经存在相同预交年份和预交班型"),
	ljqhtj_input_error("了解强化途径信息输入有误"),
	whkxx_input_error("文化课学校输入有误"),
	cengci_input_error("学生类型信息输入有误"),
	pici_input_error("批次信息输入有误"),
	jinxiaonianfen_input_error("进校年份输入有误"),
	teacheralreadyexisted("教职工信息已存在"),
	bindalipaystatuserror("支付宝绑定状态输入有误"),
	gendererror("性别输入有误"),
	jffbje_fail_number("优惠金额必须为数字"),
	jieshaoren_input_error("介绍人不存在"),
	zhaoshengren_input_error("招生人不存在"),
	banji_input_error("班级输入有误"),
	xueji_input_error("学籍类型输入有误"),
	kelei_input_error("科类输入有误"),
	stubirth_input_error("学生生源输入有误"),
	jihua_input_error("计划性质输入有误"),
	luqudeptBianMaerror("录取专业编码有误"),
	laiyuan_input_error("学生来源有误"),
	laiyuan_name_exist("学生生源源已存在"),
	xingbie_input_error("性别输入有误"),
	stualreadyhasstu("学生宿舍信息重复"),
	dormnamewrong("宿舍信息填写有误"),
	stucannotmatchdormxingbie("学生性别和宿舍性别不一致"),
	stuinfochongfu("学生信息重复"),
	exceldormchongfu("excel中宿舍信息重复"),
	gradeIsNotExisted("入学年份不存在"),
	userIsNotExisted("班主任(用户)不存在"),
	classIsExisted("此班级已经存在"),
	stuIdCardIsNotExist("不存在此身份证号的学生"),
	deptType_input_error("组织类型输入有误"),
	deptNoAlreadyExistd("部门编码已存在"),
	excelExistSameDeptNo("表中存在相同组织编码的数据"),
	parent_dept_no_not_exist("上级组织编码不存在"),
	grade_data_empty("入学年份不能为空"),
	department_data_empty("院校专业编码不能为空"),
	class_data_empty("班级名称不能为空"),
	username_data_empty("班主任不能为空"),
	nation_name_exist("民族名称已存在"),
	nation_name_error("民族名称输入有误"),
	political_name_error("政治面貌输入有误"),
	family_name_error("家庭成员关系输入有误"),
	accountnature_name_error("户口性质输入有误"),
	healthstatus_name_error("健康状况输入有误"),
	pkzk_name_error("是否贫困输入有误"),
	sxbs_name_error("升学标识输入有误"),
	xslx_name_error("学生类型输入有误"),
	xuezhi_name_error("学制输入有误"),
	zcxjsj_name_error("注册学籍时间格式有误"),
	zhsxxms_name_error("自何时学习美术时间格式有误"),
	gyrxsj_name_error("高一入学时间格式有误"),
	laiyuan_exits_error("该学生生源名称在表格中重复"),
	rxsh_name_error("入学时间格式有误"),
	nation_exits_error("该民族名称在表格中重复"),
	stu_source_error("学生生源输入有误"),
	ruxueshijian_error("入学时间非有效日期"),
	jiduzhuangtai_error("寄读状态输入有误"),
	remarks_error("备注长度不能超过500"),
	loaninput_error("贷款金额只能是数字,大于0,不大于5位数的整数或小数(最多保留两位小数)"),
	loangreat_error("贷款金额不能大于欠费金额"),
	dingjin_error("定金只能是数字,大于0的整数或小数(最多保留两位小数)"),
	jhrlxdh_error("监护人电话格式不正确"),
	bzrdh_error("班主任电话格式不正确"),
	kemus_error("已学过科目信息输入有误"),
	xkmarks_error("校考成绩信息输入有误"),
	;
	
	private String value;

	ImportErrorEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return this.name();
	}

	/**
	 * 根据value找name
	 * 
	 * @param value
	 * @return
	 */
	public static String getNameByValue(String value) {
		ImportErrorEnum[] lanmuUrlMobans = values();
		for (ImportErrorEnum XmZt : lanmuUrlMobans) {
			if (XmZt.getValue().equals(value)) {
				return XmZt.getName();
			}
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getValueList() {
		ImportErrorEnum[] lanmuUrlMobans = values();
		List list = new ArrayList();
		for (ImportErrorEnum obj : lanmuUrlMobans) {
			list.add(obj.getValue());
		}
		return list;
	}
	
}
