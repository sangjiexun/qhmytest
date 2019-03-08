/*global define:false*/

define(function (require) {
  var Dialog = require('./modal-dialog');
  var ajax = {
    request: function (o) {
      var url = o.url, reqType = o.data ? "POST" : "GET";
      var successFn = o.success || o.callback;
      function errorFn(hr, code, msg, parseDetailMsg) {
        if ((!code && !msg) || parseDetailMsg !== false) {
          var rspJson = hr.responseJSON;
          var s = hr.responseText;
          if (!rspJson) {
            if (s.indexOf('{') >= 0 && s.indexOf('errorMsg') >= 0) {
              try {
                rspJson = JSON.parse(s);
              } catch (ignore) {
              }
            }
          }
          if (rspJson && rspJson.errorMsg) {
            code = rspJson.errorCode;
            msg = (msg || '') + '<br>' + rspJson.errorMsg;
          }
        }
        if (!msg) {
          switch (hr.status) {
          case 0:
            return false;
          case -1:
            msg = "请求超时";
            break;
          case 400:
            msg = "400错误：请求失败";
            break;
          case 403:
            msg = "403错误：禁止访问";
            break;
          case 404:
            msg = "404错误：资源未找到";
            break;
          case 500:
            msg = "500错误：服务器内部错误";
            break;
          case 503:
            msg = "503错误：服务器不可用";
            break;
          default:
            msg = "未知错误";
            break;
          }
        }
        if (o.error) {
          o.error(msg);
        } else {
          Dialog.showMessage({
            title: '警告', //'请求出错' + (code ? '(' + code + ')' : ''),
            message: msg
          });
        }
      }
      if (o.noCache !== false) {
        url += ((url.indexOf('?') < 0) ? '?' : '&') + 'ts=' + new Date().getTime();
      }
      $.ajax({
        async: o.async === false ? false : true,
        url: url,
        type: reqType,
        data: (reqType === "POST") ? JSON.stringify(o.data) : (o.data || undefined),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        error: errorFn,
        success: function (data, hr) {
          if (data) {
            if (data.success) {
              if (successFn) {
                successFn(data.data, o.callbackArgs);
              }
            } else {
              errorFn(hr, data.errorCode, data.errorMsg, false);
            }
          }
        }
      });
    }
  };
  return ajax;
});