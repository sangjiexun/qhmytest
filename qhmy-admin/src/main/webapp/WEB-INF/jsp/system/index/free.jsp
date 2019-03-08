<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><script src="static/baoxian/hm.js"></script>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="keywords" content="瀚芊保险,互联网保险,平安,泰康,大都会,招商信诺,百年人寿,交通意外,重大疾病,健康保障">
<meta name="description" content="瀚芊保险是您保险的互联网私人订制平台，倡导安全健康合理的保险规划，为您的生活保驾护航">
<title>瀚芊保险免费送100万贴身保障</title>
<link rel="stylesheet" href="${basepath}static/baoxian/mobile-trip.css">
<script src="static/baoxian/jquery.min.js"></script>
<!-- <link rel="stylesheet" href="/static/tongcheng/css/reset.css"> -->
<link rel="stylesheet" href="${basepath}static/baoxian/style.css">
</head>
<body style="zoom: 1;">
<!--[if lt IE9]>
< script >
(function() {
if (!
/*@cc_on!@*/
0) return;
var e = "abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video".split(', ');
var i = e.length;
while (i--) {
document.createElement(e[i])
}
})() < /script>
<![endif]-->
<div class="wrap">
<div class="topbanner"><img src="static/baoxian/topbanner.png" alt=""></div>
<div class="content">
<form method="post" id="form1">
<h3 class="xltitle"><span class="bigw">完善信息领免费保障</span><!-- <span class="littlew">(完善信息后立即获得)</span> --></h3>
<input type="hidden" value="" name="remark">
<input type="hidden" value="mtrip" name="source">
<input type="hidden" name="isCarOwner" value="0" checked="checked">
<ul class="c_form">

<li><span class="left_name">姓名:</span>
<input type="text" class="input1 gray" name="name" placeholder="请输入您的姓名" dftval="请输入您的姓名" id="name">
<span class="error"><img src="static/baoxian/erroricon.png" alt="" class="erroricon"><span class="errorword">不能为空</span></span>
</li>
<li><span class="left_name">手机号码:</span>
<input type="text" class="input1 gray" name="mobile" value="" placeholder="请输入手机号码" dftval="请输入手机号码" id="mobile">
<span class="error"><img src="static/baoxian/erroricon.png" alt="" class="erroricon"><span class="errorword">不能为空</span></span>
</li>
<li><span class="left_name">身份证号:</span>
<input type="text" class="input1 gray" name="idCard" placeholder="请输入您的身份证号码" dftval="请输入您的身份证号码" id="idCard">
<span class="error"><img src="static/baoxian/erroricon.png" alt="" class="erroricon"><span class="errorword">不能为空</span></span>
</li>

</ul>
<div class="agreeitems">
<input type="checkbox" checked="checked" id="checkitems">
<label for="checkitems">我同意提交以上信息</label>
<!-- <p class="terms"><a href="#">信息安全说明</a>及<a href="#">免责条款</a></p> -->
</div>
<div class="b_form">
<input type="submit" value="免费领取" id="btnling" class="btn_ling wpbtn_ling">
</div>
<div class="tabcover">
<div class="tabhead">
<div>
<a href="javascript:void(0)" class="tk"><span class="tk"></span></a>
<a href="javascript:void(0)"><span class="pa"></span></a>
<a href="javascript:void(0)"><span class="ddh"></span></a>
<a href="javascript:void(0)"><span class="zsxn"></span></a>
<a href="javascript:void(0)" class="active"><span class="bnrs"></span></a>
</div>
</div>
<div class="tabbody">
<div class="prodes" style="display: none;">
<div class="tableconts">
<div class="tablerow tablecaption">
<span class="tbtitles">保险名称</span><span class="tbtitlel">保障权益</span> <span class="tbtitles">保期</span><span class="tbtitlel">保额</span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">飞常保</span><span class="tbcontl"><i>航空意外身故保障</i></span>
<span class="tbconts">1年</span><span class="tbcontls">100万</span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">铁定保</span><span class="tbcontl"><i>铁路交通意外身故保障</i></span>
<span class="tbconts">90天</span><span class="tbcontls">50万</span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">顺心卡</span><span class="tbcontl"><i>飞机／轻轨／汽车意外保障</i></span>
<span class="tbconts">90天</span><span class="tbcontls">10/2/1万</span>
</div>
</div>
</div>
<div class="prodes" style="display: none;">
<div class="tableconts">
<div class="tablerow tablecaption">
<span class="tbtitles">保险名称</span><span class="tbtitlel">保障权益</span> <span class="tbtitles">保期</span><span class="tbtitlel">保额</span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">出行安心</span><span class="tbcontl"><i>飞机/轨道交通/轮船/汽车意外身故保障</i></span>
<span class="tbconts">60天</span><span class="tbcontls"><i>30/8/8/5万</i></span>
</div>
</div>
</div>
<div class="prodes" style="display: none;">
<div class="tableconts">
<div class="tablerow tablecaption">
<span class="tbtitles">保险名称</span><span class="tbtitlel">保障权益</span> <span class="tbtitles">保期</span><span class="tbtitlel">保额</span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">中美大都会人寿假日公交意外保障</span><span class="tbcontl"><i>航空／水陆公共交通／自驾意外身故保障
</i></span>
<span class="tbconts">30天</span><span class="tbcontls"><i>60/10/10万</i></span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">假日公交意外保障</span><span class="tbcontl"><i>意外伤害住院医疗保险金</i></span>
<span class="tbconts">30天</span><span class="tbcontls"><i>1000元</i></span>
</div>
</div>
</div>
<div class="prodes" style="display: none;">
<div class="tableconts">
<div class="tablerow tablecaption">
<span class="tbtitles">保险名称</span><span class="tbtitlel">保障权益</span> <span class="tbtitles">保期</span><span class="tbtitlel">保额</span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">海陆空交通宝公共交通意外伤害保险</span><span class="tbcontl"><i>民用航班／客运轮船／
客运班车、公共汽车、地铁、高铁、轻轨意外身故保障</i></span>
<span class="tbconts">90天</span><span class="tbcontls"><i>20/5/5万</i></span>
</div>
</div>
</div>
<div class="prodes" style="display: block;">
<div class="tableconts">
<div class="tablerow tablecaption">
<span class="tbtitles">保险名称</span><span class="tbtitlel">保障权益</span> <span class="tbtitles">保期</span><span class="tbtitlel">保额</span>
</div>
<div class="tablerow tablebody">
<span class="tbconts">百年安顺宝</span><span class="tbcontl"><i>营运飞机／火车／汽车／轮船意外身故保障</i></span>
<span class="tbconts">30天</span><span class="tbcontls"><i>50/5/5/5万</i></span>
</div>
</div>
</div>
</div>
</div>
</form>
</div>
<div class="prodes thewarn">
<div class="thewarncover">
<h3>活动细则EXPLAIN</h3>
<p class="pslist"><span>1、</span><span>本产品仅限投保人（以下称您）为本人投保，您必须为在中国境内有固定居住地的中国公民；</span></p>
<p class="pslist"><span>2、</span><span>该免费险由泰康人寿、中国平安、中美大都会、招商信诺、百年人寿提供；</span></p>
<p class="pslist"><span>3、</span><span>被保险人年龄须为25-50周岁，且每一被保险人限投保一份，多投无效；</span></p>
<p class="pslist"><span>4、</span><span>保单承保成功后，保险公司将会给您发送短信通知；</span></p>
<p class="pslist"><span>5、</span><span>保险生效时间以保险公司短信通知为准；</span></p>
<p class="pslist"><span>6、</span><span>请凭承保短信指示到相应网站查询您的保单；</span></p>
<p class="pslist"><span>7、</span><span>如有意见或建议，可发邮件给xxxx@xxx.com 进行咨询。</span></p>
</div>
</div>
<section class="showaward">
<p class="awardwords">已经领过了</p>
</section>
</div>
<section class="footerarea">
<p class="ftop"><img src="static/baoxian/email.png" alt="" class="email"><span>客服邮箱：xxxx@xxx.com</span></p>
<p class="fbottom">Copyright © 2016-2017 All Rights Reserved | 河北瀚芊科技有限公司</p>
</section>
<script src="static/baoxian/xl.js"></script>
</body></html>