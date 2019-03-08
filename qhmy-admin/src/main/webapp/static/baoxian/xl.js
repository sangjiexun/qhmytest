function showaward(str) {
    console.log($(".showaward p").outerWidth(true) + "ldfdlfkjdlf" + $(".showaward p").width())
    $(".showaward p").html(str);
    $(".showaward").show();
    setTimeout(function() {
        $(".showaward").hide();
    }, 2000)
}
/**
    * 身份证校验 * @type {
        Object
} */
var idCardNoUtil = {
    /* 省, 直辖市代码表 */
    provinceAndCitys: {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外"
    },

    /* 每位加权因子 */
    powers: ["7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"],

    /* 第18位校检码 */
    parityBit: ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"],

    /* 性别 */
    genders: {
        male: "男",
        female: "女"
    },

    /* 校验地址码 */
    checkAddressCode: function(addressCode) {
        var check = /^[1-9]\d{5}$/.test(addressCode);
        if (!check) return false;
        if (idCardNoUtil.provinceAndCitys[parseInt(addressCode.substring(0, 2))]) {
            return true;
        } else {
            return false;
        }
    },

    /*校验日期码*/
    checkBirthDayCode: function(birDayCode) {
        var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
        if (!check) return false;
        var yyyy = parseInt(birDayCode.substring(0, 4), 10);
        var mm = parseInt(birDayCode.substring(4, 6), 10);
        var dd = parseInt(birDayCode.substring(6), 10);
        var xdata = new Date(yyyy, mm - 1, dd);
        if (xdata > new Date()) {
            return false; //生日不能大于当前日期
        } else if ((xdata.getFullYear() == yyyy) && (xdata.getMonth() == mm - 1) && (xdata.getDate() == dd)) {
            return true;
        } else {
            return false;
        }
    },

    /*计算校检码*/
    getParityBit: function(idCardNo) {
        var id17 = idCardNo.substring(0, 17);
        /*加权 */
        var power = 0;
        for (var i = 0; i < 17; i++) {
            power += parseInt(id17.charAt(i), 10) * parseInt(idCardNoUtil.powers[i]);
        }
        /*取模*/
        var mod = power % 11;
        return idCardNoUtil.parityBit[mod];
    },

    /*验证校检码*/
    checkParityBit: function(idCardNo) {
        var parityBit = idCardNo.charAt(17).toUpperCase();
        if (idCardNoUtil.getParityBit(idCardNo) == parityBit) {
            return true;
        } else {
            return false;
        }
    },

    /*校验15位或18位的身份证号码*/
    checkIdCardNo: function(idCardNo) {
        //15位和18位身份证号码的基本校验
        var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
        if (!check) return false;
        //判断长度为15位或18位
        if (idCardNo.length == 15) {
            return idCardNoUtil.check15IdCardNo(idCardNo);
            return false;
        } else if (idCardNo.length == 18) {
            return idCardNoUtil.check18IdCardNo(idCardNo);
        } else {
            return false;
        }
    },

    //校验15位的身份证号码
    check15IdCardNo: function(idCardNo) {
        //15位身份证号码的基本校验
        var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(idCardNo);
        if (!check) return false;
        //校验地址码
        var addressCode = idCardNo.substring(0, 6);
        check = idCardNoUtil.checkAddressCode(addressCode);
        if (!check) return false;
        var birDayCode = '19' + idCardNo.substring(6, 12);
        //校验日期码
        return idCardNoUtil.checkBirthDayCode(birDayCode);
    },

    //校验18位的身份证号码
    check18IdCardNo: function(idCardNo) {
        //18位身份证号码的基本格式校验
        var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(idCardNo);
        if (!check) return false;
        //校验地址码
        var addressCode = idCardNo.substring(0, 6);
        check = idCardNoUtil.checkAddressCode(addressCode);
        if (!check) return false;
        //校验日期码
        var birDayCode = idCardNo.substring(6, 14);
        check = idCardNoUtil.checkBirthDayCode(birDayCode);
        if (!check) return false;
        //验证校检码
        return idCardNoUtil.checkParityBit(idCardNo);
    },

    formateDateCN: function(day) {
        var yyyy = day.substring(0, 4);
        var mm = day.substring(4, 6);
        var dd = day.substring(6);
        return yyyy + '-' + mm + '-' + dd;
    },

    //获取信息
    getIdCardInfo: function(idCardNo) {
        var idCardInfo = {
            address: "", //地域
            gender: "", //性别
            birthday: "" //出生日期(yyyy-mm-dd)
        };
        if (idCardNo.length == 15) {
            var aday = '19' + idCardNo.substring(6, 12);
            idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
            if (parseInt(idCardNo.charAt(14)) % 2 == 0) {
                idCardInfo.gender = idCardNoUtil.genders.female;
            } else {
                idCardInfo.gender = idCardNoUtil.genders.male;
            }
        } else if (idCardNo.length == 18) {
            var aday = idCardNo.substring(6, 14);
            idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
            if (parseInt(idCardNo.charAt(16)) % 2 == 0) {
                idCardInfo.gender = idCardNoUtil.genders.female;
            } else {
                idCardInfo.gender = idCardNoUtil.genders.male;
            }
        }
        var adr = idCardNo.substring(0, 2);
        idCardInfo.address = idCardNoUtil.provinceAndCitys[adr];

        return idCardInfo;
    },

    /*18位转15位*/
    getId15: function(idCardNo) {
        if (idCardNo.length == 15) {
            return idCardNo;
        } else if (idCardNo.length == 18) {
            return idCardNo.substring(0, 6) + idCardNo.substring(8, 17);
        } else {
            return null;
        }
    },

    /*15位转18位*/
    getId18: function(idCardNo) {
        if (idCardNo.length == 15) {
            var id17 = idCardNo.substring(0, 6) + '19' + idCardNo.substring(6);
            var parityBit = idCardNoUtil.getParityBit(id17);
            return id17 + parityBit;
        } else if (idCardNo.length == 18) {
            return idCardNo;
        } else {
            return null;
        }
    }
};

function ismail(mail) {
    var re = new RegExp(/^(\w)+((\.|-|_)\w+)*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail);
    if (re) {
        var idx = mail.indexOf('@taikang.com');
        if (idx > -1) {
            var username = mail.substring(0, idx);
            if (username.indexOf(".") > -1)
                return false;
        }

        var idx2 = mail.indexOf('@taikanglife.com');
        if (idx2 > -1) {
            var username = mail.substring(0, idx);
            if (username.indexOf(".") > -1)
                return false;
        }
    }

    return re;
}

function isPhoneNumber(obj) {
    var that = {};
    that.result = "n";
    var controlObj = $.trim(obj);
    if (controlObj.length == 0 || controlObj == null || controlObj == undefined) {
        that.result = "n";
    } else {
        var reg = /^0*(86)*(13|15|14|17|18)\d{9}$/;
        if (!reg.test(controlObj)) {
            that.result = "x";
        } else {
            that.result = "t";
        }
    }
    return that;
}

function isName(obj) {
    var that = {};
    that.result = "n";
    var controlObj = $.trim(obj);
    if (controlObj.length == 0 || controlObj == null || controlObj == undefined) {
        that.result = "n";
    } else if (controlObj.length < 2 || controlObj.length > 50) {
        that.result = "l";
    } else {
        var reg = /^[A-z\u4e00-\u9fa5]+$/;
        if (!reg.test(controlObj)) {
            that.result = "x";
        } else {
            that.result = "t";
        }
    }
    return that;
}
var emptyw = {
    name: "请填写姓名",
    mobile: "请填写手机号",
    idcard: "请填写身份证号",
    email: "请填写邮箱",
    code: "请填写兑换码"
}
var errors = {
    name: "请正确填写姓名",
    mobile: "请填正确写手机号",
    idcard: "请正确填写身份证号",
    email: "请正确填写邮箱",
    code: "请正确填写兑换码"
}
$(function() {
    $("input").on("focus", function() {
        return false;
    })
    $("#name").on("blur input propertychange", function(event) {
        event.preventDefault();
        event.stopPropagation();
        if ($(this).val() == "") {
            $(this).parent().find(".error").show().find(".errorword").text(emptyw.name);
            return;
        } else {
            $(this).parent().find(".error").hide();
        }

    });
    $("#mobile").on("blur input propertychange", function(event) {
        event.preventDefault();
        event.stopPropagation();
        if ($(this).val() == "") {
            $(this).parent().find(".error").show().find(".errorword").text(emptyw.mobile);
            return;
        } else {
            $(this).parent().find(".error").hide();
        }

    });
    $("#idCard").on("blur input propertychange", function(event) {
        event.preventDefault();
        event.stopPropagation();
        if ($(this).val() == "") {
            $(this).parent().find(".error").show().find(".errorword").text(emptyw.idcard);
            return;
        } else {
            $(this).parent().find(".error").hide();
        }

    });
    $("#email").on("blur input propertychange", function(event) {
        event.preventDefault();
        event.stopPropagation();
        if ($(this).val() == "") {
            $(this).parent().find(".error").show().find(".errorword").text(emptyw.email);
            return;
        } else {
            $(this).parent().find(".error").hide();
        }

    });
    $("#code").on("blur input propertychange", function(event) {
        event.preventDefault();
        event.stopPropagation();
        if ($(this).val() == "") {
            $(this).parent().find(".error").show().find(".errorword").text(emptyw.code);
            return;
        } else {
            $(this).parent().find(".error").hide();
        }

    });
    $("#checkitems").on('click', function() {
        console.log("xxx")
        if (!$(this).prop("checked")) {
            $("#btnling").prop("disabled", true).css("background", "#ccc");
        } else {
            $("#btnling").prop("disabled", false).css("background", "#d42a43");
        }
    });
    var showemail = true;

    function checkand() {
        if ($("#name").val() == "") {
            $("#name").parent().find(".error").show().find(".errorword").text(emptyw.name);
            console.log($("#name").parent().find(".error").find(".errorword").text())
            return;
        } else {
            $("#name").parent().find(".error").hide();
        }
        if (isName($("#name").val()).result != "t") {
            $("#name").parent().find(".error").show().find(".errorword").text(errors.name);
            return;
        } else {
            $("#name").parent().find(".error").hide();
        }
        if ($("#mobile").val() == "") {
            $("#mobile").parent().find(".error").show().find(".errorword").text(emptyw.mobile);
            return;
        } else {
            $("#mobile").parent().find(".error").hide();
        }
        if (isPhoneNumber($("#mobile").val()).result != "t") {
            $("#mobile").parent().find(".error").show().find(".errorword").text(errors.mobile);
            return;
        } else {
            $("#mobile").parent().find(".error").hide();
        }
        if ($("#idCard").val() == "") {
            $("#idCard").parent().find(".error").show().find(".errorword").text(emptyw.idcard);
            return;
        } else {
            $("#idCard").parent().find(".error").hide();
        }
        if (idCardNoUtil.checkIdCardNo($("#idCard").val()) != true) {
            console.log(idCardNoUtil.checkIdCardNo($("#idCard").val()))
            $("#idCard").parent().find(".error").show().find(".errorword").text(error.idcard);
            return;
        } else {
            $("#idCard").parent().find(".error").hide();
        }
        if (showemail) {
            if ($("#email").val() == "") {
                $("#email").parent().find(".error").show().find(".errorword").text(emptyw.email);
                return;
            } else {
                $("#email").parent().find(".error").hide();
            }
            if (!ismail($("#email").val())) {
                $("#email").parent().find(".error").show().find(".errorword").text(error.email);
                return;
            } else {
                $("#email").parent().find(".error").hide();
            }
        }

        if (!$("#checkitems").prop("checked")) {
            showaward("请选择同意提交以上信息");
            return;
        }
        return true;
    }
    $("#form1").submit(function() {

        if (checkand()) {
            //return true;
        } else {
            return false;
        }

    });
})
$(".tabhead").find("div").eq(0).find("a").click(function(event) {
    if ($(this).hasClass('active')) {
        return;
    }
    $(this).addClass('active').siblings("a").removeClass('active');
    $(".tabbody").find(".prodes").eq($(this).index()).show().siblings().hide();
});