jQuery.cookie = function(name, value, options) {
	var bSupportLocalStorage = window.sessionStorage ? true : false;
	if (bSupportLocalStorage && (!options || !options.expires)) {
		if (value === undefined)
			return sessionStorage.getItem(name);
		else if (value === null)
			sessionStorage.removeItem(name);
		else
			sessionStorage.setItem(name, value);
	} else if (typeof value != 'undefined') { // name and value given, set cookie
		if (bSupportLocalStorage)
			sessionStorage.setItem(name, value);
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE  
		}
		// CAUTION: Needed to parenthesize options.path and options.domain  
		// in the following expressions, otherwise they evaluate to undefined  
		// in the packed version for some reason...  
		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else { // only name given, get cookie  
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?  
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};

function initContext(contextString) {
	var context = JSON.parse(contextString);
	$.cookie('org', context.org);
	$.cookie('manager', context.manager);
	$.cookie('orgName', context.orgName);
	$.cookie('rootOrgId', context.rootOrgId);
	$.cookie('project', context.projectId ? context.projectId : null);
	$.cookie('env', context.projectId ? 'project' : 'company'); //该 cookie 将不再维护，也不要使用，判断是否为项目，请使用 isProject
	$.cookie('rights', context.rights);
	if (context.productCodes)
		$.cookie('appName', context.productCodes.join(','));
	var appCodes = [];
	var expiredProducts = '';
	var stopProducts = '';
	var prdStatus = {};
	if (context.apps) {
		var app;
		if(context.projectId){
			prdStatus['orgId']=context.orgId;
		}
		for (var i = 0; i < context.apps.length; i++) {
			app = context.apps[i];
			prdStatus[app.productCode]=app.status;
			if (app.status == 0) {
				appCodes.push(app.productCode);
			}
			if (context.projectId) {
				if (app.status == 2) {
					expiredProducts += '【' + app.productName + '】';
				} else if (app.status == 1) {
					stopProducts += '【' + app.productName + '】';
				}
			}
		}

		if (expiredProducts) {
			window.expiredProducts = expiredProducts + '已过期';
		} 
		if (stopProducts) {
			window.stopProducts = stopProducts + '已停用';
		}
	}

	$.cookie('prdStatus', JSON.stringify(prdStatus));
	$.cookie('appCodes', appCodes.join(','));
	
	//var ar = context.allAppReadOnly, vpc = context.validProductCodes;
	//$.cookie('allAppReadOnly', (ar === "true" || ar === true || !vpc || !vpc.length) ? true : null);
}

function isProject() {
	return $.cookie('project') ? true : false;
}

function inRight(rights, checkIndex) {
	var userRights = $.cookie('rights');
	if (checkIndex) {
		for (var i = 0; i < userRights.length; i++)
			if (userRights[i].indexOf(rights) > -1)
				return true;
	} else {
		for (var i = 0; i < userRights.length; i++)
			for (var j = 0; j < rights.length; j++)
				if (userRights[i] == rights[j])
					return true;
	}
	return false;
}