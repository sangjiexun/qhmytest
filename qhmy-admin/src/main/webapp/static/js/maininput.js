define(function(require) {
    var View = require('Sparrow/View'),
        ListView = require('Sparrow/ListView'),
        Tree = require('Sparrow/TreeView'),
        formHelper = require('Sparrow/formHelper'),
        ajax = require('Sparrow/ajax'),
        Dialog = require('Sparrow/ModalDialog');

    var productData = $.cookie('productData') ? JSON.parse($.cookie('productData')) : null;

    var encrypt = function(data) {
        var o = new Date().getTime();
        var a = Math.round(Math.random() * 14) + 1;
        var s = o + '#|,|#' + data;
        var len = s.length;
        var buf = '';
        for (var i = 0; i < len; i++) {
            var x = (s.charCodeAt(i) ^ a).toString(16);
            if (x.length == 1)
                x = '000' + x;
            else if (x.length == 2)
                x = '00' + x;
            else if (x.length == 3)
                x = '0' + x;
            buf += x;
        }
        return a.toString(16) + buf;
    };

    var setCaption = function() {
        var smsButton = $('.btn-info')[0];
        window.smsCounter--;
        smsButton.innerHTML = window.smsCounter + '秒后重新获取';
        if (window.smsCounter < 1) {
            clearInterval(window.smsTimer);
            window.smsTimer = null;
            smsButton.innerHTML = '获取验证码';
            smsButton.disabled = '';
        }
    }

    var appendShim = function(el, h) {
        var shim = $('#ifr_shim');

        if (shim.length > 0) {
            shim.height(h);
            shim.appendTo(el);
        }
    }

    function createHeader() {
        var dlgChangePwd = new Dialog({
            width: '500px',
            height: '200px',
            bodyContentUrl: View.resolveHtmlUrl(require, '/html/frame/change-pwd.html'),
            footerHTML: '<button type="button" class="btn btn-ok btn-success">确定</button>' + '<button type="button" class="btn btn-cancel btn-default" data-dismiss="modal">取消</button>',
            title: '修改密码',
            listeners: [
                //确定
                ['click', '.btn-success',
                    function(event, elm, dlg) {
                        //检测新密码与确认密码是否相同，不同则提示
                        if (formHelper.verify(dlg.element) === false) return false;
                        var newPassword = $('.lbr-user-info-new-pwd').val();
                        var newPasswordConfirm = $('.lbr-user-info-new-pwd-again').val();
                        if (newPassword != newPasswordConfirm) {
                            //退出登录
                            Dialog.showMessage({
                                title: '警告',
                                message: '新密码与确认密码不相同。'
                            });
                            return;
                        }
                        //修改密码
                        ajax.request({
                            url: '/pmpp/services/user/change-pwd?old=' + encrypt($('.lbr-user-info-old-pwd').val()) + '&new=' + encrypt(newPassword),
                            success: function(result) {
                                dlg.hide();
                                Dialog.showMessage({
                                    title: '成功',
                                    message: '密码修改成功。'
                                });
                            }
                        });
                    }
                ]
            ],
            showDialog: function(o) {
                this.setTitle(o.title);
                $('.lbr-user-info-old-pwd,.lbr-user-info-new-pwd,.lbr-user-info-new-pwd-again').val('');
                this.show();
                appendShim(this.contentElm, 320);
            }
        });

        var dlgCodeLogin = new Dialog({
            width: '380px',
            height: '270px',
            bodyContentUrl: View.resolveHtmlUrl(require, '/html/frame/code.html'),
            footerHTML: '<button type="button" class="btn btn-cancel btn-default" data-dismiss="modal">关闭</button>',
            title: 'APP扫码登录',
            showDialog: function(o) {
                this.setTitle(o.title);
                this.get("#image-login-code").attr("src", "/identity/qrcode.img?v=" + (new Date().getTime()));
                this.show();
                appendShim(this.contentElm, 380);
            },
		   listeners: [
			//点击修改手机号
				['click', '.code-refresh',
					function(event, elm, dlg) {
						 dlg.get("#image-login-code").attr("src", "/identity/qrcode.img?v=" + (new Date().getTime()));
					}
				]
			]
        });
        var dlgUserInfo = new Dialog({
            width: '500px',
            height: '250px',
            bodyContentUrl: View.resolveHtmlUrl(require, '/html/frame/user-info.html'),
            footerHTML: '<button type="button" class="btn btn-ok btn-success">确定</button>' + '<button type="button" class="btn btn-cancel btn-default" data-dismiss="modal">取消</button>' + '<button type="button" class="btn btn-danger btn-change-phone" style="display:none">更换手机号</button>' + '<button type="button" class="btn btn-default btn-return" style="display:none">返回</button>',
            title: '个人设置',
            listeners: [
                //点击修改手机号
                ['click', '.btn-user-info-edit-phone',
                    function(event, elm, dlg) {
                        dlg.get('.lbr-user-info-new-phone').val('');
                        dlg.get('.lbr-sms-code').val('');
                        dlg.get('.lbr-user-info-main-view,.btn-success,.btn-cancel').css('display', 'none');
                        dlg.get('.lbr-user-info-edit-phone,.btn-change-phone,.btn-return').css('display', '');
                    }
                ],
                //获取验证码
                ['click', '.btn-info',
                    function(event, elm, dlg) {
                        if (formHelper.verify('.lbr-user-info-mobile') === false) return false;

                        if ($('.lbr-user-info-orgin-phone')[0].innerHTML == $('.lbr-user-info-new-phone').val()) {
                            Dialog.showMessage({
                                title: '提示',
                                message: '新手机号与旧手机号不能相同。'
                            });
                            return;
                        }

                        ajax.request({
                            url: '/pmpp/services/org/getSMSCode?mobile=' + $('.lbr-user-info-new-phone').val(),
                            success: function(result) {
                                window.smsIndex = result;
                                $('.btn-info')[0].disabled = 'disabled';
                                window.smsCounter = 60;
                                window.smsTimer = setInterval(setCaption, 1000);
                            }
                        });
                    }
                ],
                //返回
                ['click', '.btn-return',
                    function(event, elm, dlg) {
                        dlg.get('.lbr-user-info-main-view,.btn-success,.btn-cancel').css('display', '');
                        dlg.get('.lbr-user-info-edit-phone,.btn-change-phone,.btn-return').css('display', 'none');
                    }
                ],
                //更改手机号
                ['click', '.btn-change-phone',
                    function(event, elm, dlg) {
                        if (formHelper.verify(dlg.element) === false) return false;

                        if ($('.lbr-user-info-orgin-phone')[0].innerHTML == $('.lbr-user-info-new-phone').val()) {
                            Dialog.showMessage({
                                title: '提示',
                                message: '新手机号与旧手机号不能相同。'
                            });
                            return;
                        }

                        if (!window.smsIndex) {
                            Dialog.showMessage({
                                title: '提示',
                                message: '请先获取手机验证码。'
                            });
                            return;
                        }

                        var index = window.smsIndex;
                        var data = {
                            mobile: $('.lbr-user-info-new-phone').val(),
                            code: $('.lbr-sms-code').val()
                        };
                        ajax.request({
                            url: '/pmpp/services/user/update-mobile',
                            data: data,
                            success: function(result) {
                                window.smsIndex = null;
                                $('.lbr-user-info-orgin-phone')[0].innerHTML = $('.lbr-user-info-new-phone').val();
                                $('.lbr-user-info-phone')[0].innerHTML = $('.lbr-user-info-new-phone').val();
                                dlg.get('.lbr-user-info-main-view').css('display', '');
                                dlg.get('.lbr-user-info-edit-phone').css('display', 'none');
                                dlg.get('.btn-success').css('display', '');
                                dlg.get('.btn-cancel').css('display', '');
                                dlg.get('.btn-change-phone').css('display', 'none');
                                dlg.get('.btn-return').css('display', 'none');
                                Dialog.showMessage({
                                    title: '成功',
                                    message: '手机号修改成功。'
                                });
                            }
                        });
                    }
                ],
                //确定
                ['click', '.btn-success',
                    function(event, elm, dlg) {
                        var data = dlg.getData();
                        if ($.cookie('tenantManager') == 'true') {
                            var phone = $('.lbr-user-info-link-tel').val();
                            if ($.trim(phone)) {
                                if (!mobileIsValid($.trim(phone))) {
                                    Dialog.showHint('请输入有效的11位手机号码。', 'red-bg');
                                    return false;
                                }
                            }
                            var data = {
                                mobile: data.phone,
                                code: "tenantManager"
                            };
                            ajax.request({
                                url: '/pmpp/services/user/update-mobile',
                                data: data,
                                success: function(result) {
                                    dlg.hide();
                                    Dialog.showMessage({
                                        title: '成功',
                                        message: '管理员信息修改成功。'
                                    });
                                }
                            });
                        } else {
                            var name = $('.lbr-lbr-user-info-name').val();
                            if (!$.trim(name)) {
                                Dialog.showHint('真实姓名 不能为空。', 'red-bg');
                                return false;
                            }
                            ajax.request({
                                url: '/pmpp/services/user/update',
                                data: data,
                                success: function(result) {
                                    dlg.hide();
                                    header.get('.userName').html(data.name);
                                    Dialog.showMessage({
                                        title: '成功',
                                        message: '用户信息修改成功。'
                                    });
                                }
                            });
                        }
                    }
                ]
            ],
            showDialog: function(o) {
                var me = this;
                this.setTitle(o.title);
                ajax.request({
                    url: '/pmpp/services/user/current',
                    success: function(result) {
                        if (window.userType > 0) {
                            $('.group-sex').css('display', 'none');
                        }
                        if ($.cookie('tenantManager') == 'true') {
                            $('.btn-user-info-edit-phone').css('display', 'none');
                            $('.lbr-name').html('帐户名称');
                            $('.lbr-lbr-user-info-name').attr('disabled', true);
                            $('.group-sex').css('display', 'none');
                            $('.group-link-tel').css('display', '');
                        }
                        me.get('.lbr-user-info-new-phone').val('');
                        me.get('.lbr-sms-code').val('');
                        me.get('.lbr-user-info-main-view,.btn-success,.btn-cancel').css('display', '');
                        me.get('.lbr-user-info-edit-phone,.btn-change-phone,.btn-return').css('display', 'none');
                        $('.lbr-user-info-orgin-phone')[0].innerHTML = (result.phone.length == 11) ? result.phone : '未绑定';
                        $('.lbr-user-info-phone')[0].innerHTML = ($.cookie('tenantManager') == 'true') ? result.account : ((result.phone.length == 11) ? result.phone : '未绑定');
                        $('.lbr-lbr-user-info-name').val(result.name);
                        $('.lbr-user-info-link-tel').val(result.linkTel);                        
                        me.show();
                        appendShim(me.contentElm, 370);
                    }
                });
            },
            getData: function() {
                var result = {
                    name: $('.lbr-lbr-user-info-name').val(),
                    gender: 1
                };
                if ($.cookie('tenantManager') == 'true')
                    result.phone = $('.lbr-user-info-link-tel').val();
                return result;
            }
        });
        var header = new View({
            element: '.ct-page-header',
            contentUrl: (productData && productData.contentUrl) ? productData.contentUrl : '/html/frame/header.html',          
            renderContent: function() {
                var me = this;
                $(this.element).addClass('navbar navbar-default ct-box-shadow-small');
                if (!productData || !productData.hideOrgTree) {
                    this.orgTree = new Tree({
                        element: this.getOne('.lbr-change-project'),
                        itemTemplate: '<li><a class="text-overflow" title="{{item.name}}" style="cursor: pointer;{{if item.active}}color:#006DFF;' +
                                      '{{else if !item.canSelect}}cursor:not-allowed;color:#aaaaaa;{{/if}}' + 
                                      'white-space: nowrap; padding: 10px 0 10px {{item.__level * 20 + 10}}px;width:100%;">' + 
                                      '<span class="glyphicon glyphicon-{{if (item.type==1)}}{{if item.refer}}link node-ref {{if item.active}}active{{/if}}{{else}}'+
                                      'flag{{/if}}{{else}}folder-open{{/if}}" {{if item.type==1}}style="color:#ea8010;"{{/if}}></span>' + 
                                      '<span>{{item.name}}</span></a></li>',
                        canSelect: true,
                        itemSelector: 'li',
                        service: {
                            load: function(callback) {
                                ajax.request({
                                    url: '/pmpp/services/org/navigate-org-tree',
                                    success: function(result) {
                                        if ($.isArray(result)) {
                                            var setActive = function(node) {
                                                var refer=parseInt($.cookie('refer'));
                                                if (node.id == $.cookie("org")) {
                                                    // if(node.type==1 && refer==true && !!!node.refer||(refer!=true && !!node.refer)) return;
                                                    node.active = true;
                                                    $('.lbr-cur-project-name').html(node.name);
                                                } else if (node.childNodes) {
                                                    for (var i = 0; i < node.childNodes.length; i++) {
                                                        setActive(node.childNodes[i]);
                                                    }
                                                }
                                            }

                                            for (var i = 0; i < result.length; i++) {
                                                setActive(result[i]);
                                            }

                                            callback(result); 
                                            me.appendFilterInput();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    this.orgTree.on('beforeselect', function(elm, rec) {
                        if (!rec.canSelect || rec.active) return false;
                    }).on('itemselect', function(elm, rec) {
                        $.cookie('bureau',rec.bureau===true?1:0);
                        $.cookie('refer',rec.refer===true?1:0);
                        window.location.href = $.cookie('rootURL') + 'index.jsp?orgId=' + rec.id +'&ts=' + new Date().getTime();
                    });
                    this.initOrgItemEvent(this.orgTree);

                    $('.nav-menu-item-more,.ct-page-header .dropdown').on('shown.bs.dropdown', function(e) {                      
                            var el = arguments[0].target.children[1],
                                sh = el.scrollHeight,
                                h = $(el).height();//用offsetHeight可能比较大

                            appendShim(el, sh > h ? sh : h);                       
                    });

                    this.orgList=new ListView({
                        element: document.querySelector('.list-org_nav'),
                        itemSelector: 'li',
                        emptyTemplate: '<li class="ct-list-empty">{{emptyText}}</li>',
                        itemTemplate: '<li><a class="text-overflow" style="cursor: pointer;{{if item.active}}color:#006DFF;' +
                                      '{{else if !item.canSelect}}cursor:not-allowed;color:#aaaaaa;{{/if}}' + 
                                      'white-space: nowrap;padding: 10px 0 10px 10px;width:100%;">' + 
                                      '{{if item.type!=-1}}<span class="glyphicon glyphicon-{{if item.type==1}}{{if item.refer}}link{{if item.active}} active{{/if}}{{else}}'+
                                      'flag{{/if}}{{else}}folder-open{{/if}}" {{if item.type==1}}style="color:#ea8010;{{/if}}"></span>{{/if}}' + 
                                      '<span title="{{item.name}}">{{item.name}}</span><span title="{{item.parentName}}" style="float:right;margin-right:10px;color:#aaa;">{{item.parentName}}</span></a></li>',
                        canSelect: true,                        
                        paging: false
                    });

                    this.initOrgItemEvent(this.orgList);
                }
                this.on('click', '.link-user-setting', function(e) {
                    dlgUserInfo.showDialog({
                        title: ($.cookie('tenantManager') == 'true') ? '管理员设置' : '个人设置'
                    });
                }).on('click', '.link-user-change-pwd', function(e) {
                    dlgChangePwd.showDialog({
                        title: '修改密码'
                    });
                }).on('click', '.link-login-mobileapp', function(e) {
                    dlgCodeLogin.showDialog({
                        title: 'APP扫码登录'
                    });
                }).on('click', '.link-help', function(e) {
                    window.open(productData.helpURL ? productData.helpURL : '/static/labor/servicehelp/index.html');
                }).on('click', '.link-change-product', function(e) {
                    document.location.replace('/pmpp');
                }).on('click', '.link-user-login-out', function(e) {
                    //退出登录
                    Dialog.confirm({
                        title: '退出登录',
                        message: '确定要退出登录吗？',
                        callback: function() {
                             if (window.sessionStorage){//处理局指项目退出后不清空，重新登录导航节点选中
                                // var refer=$.cookie('refer');
                                // var bureau=$.cookie('bureau');
                                sessionStorage.clear();
                                // if(refer!=null) $.cookie('refer',refer);
                                // if(bureau!=null) $.cookie('bureau',bureau);
                            }

                            window.location.replace("/identity/logout.do");
                        }
                    });
                    appendShim(Dialog.confirmDialog.contentElm, 220);
                });

                if (productData && productData.hideOrgTree)
                    this.get('.navbar-left').css('display', 'none');

                ajax.request({
                    url: '/pmpp/services/user/current-user-name',
                    success: function(result) {
                        me.get('.userName').html(result);
                    }
                });

                if (!productData || !productData.hideOrgTree){
                    this.orgTree.load();
                }

                if (productData){
                    if(productData.productTextLogo){
                        $('.ct-text-logo').html(productData.productTextLogo);
                    }

                    
                    $('.service-qq').html(productData.qq||''); 

                    if(productData.showDownloadPlugin===true){
                        $('.nav.help li:hidden').css('display','');
                    }                   
                }

                if (!$.cookie("canselectproduct")) {
                    $('.li-change-product').css('display', 'none');
                    $('.lbr-change-product').css('display', 'none');
                }
            },
            initOrgItemEvent:function(el){
                    el.on('itemselect', function(elm, rec) {
                        if (!rec||!rec.canSelect || rec.active) return false;

                        if(rec.type==-1){event.stopPropagation();event.preventDefault();}
                        $.cookie('bureau',rec.bureau===true?1:0);
                        $.cookie('refer',rec.refer===true?1:0);
                        window.location.href = $.cookie('rootURL') + 'index.jsp?orgId=' + rec.id +'&ts=' + new Date().getTime();
                    });
            },
            appendFilterInput:function(){
                // document.querySelector('.lbr-change-project').insertAdjacentHTML('afterBegin','<li style="padding: 0 10px;">'+
                //              </li>');
                
                var me=this,inputEl=$('.input-org_filter');

                inputEl.on('click',function(e){
                    e.stopPropagation();
                    e.preventDefault();
                    e.stopImmediatePropagation()
                    e.cancelBubble=true;
                });
                inputEl.on('keyup',function(e){
                    var key=$.trim(e.target.value),
                        list=$('.list-org_nav'),
                        tree=$('.lbr-change-project'),
                        emptyEl=$('.ct-list-empty'),
                        orgs,prjOrgs,data;

                    if(key){
                        tree.hide();
                        list.show();

                        orgs=[];
                        prjOrgs=[];
                        me.filterOrg(new RegExp('(.*?)'+key+'(.*?)','i'),null,orgs,prjOrgs);

                        if(prjOrgs.length>0){
                            prjOrgs=prjOrgs.sort(function(a,b){return a.name.localeCompare(b.name);});
                            prjOrgs.unshift({type:-1,canSelect:false,name:'项目'});                          
                            data=prjOrgs;
                        }
                        if(orgs.length>0){
                            orgs=orgs.sort(function(a,b){return a.name.localeCompare(b.name);});
                            orgs.unshift({type:-1,canSelect:false,name:'组织'});                           
                            data = data?data.concat(orgs):orgs;
                        }
                        me.orgList.load(data||[]);
                    }else{
                        tree.show();
                        list.hide();
                        emptyEl.hide();
                    }
                });
            },
            filterOrg:function(reg,parent,orgs,prjOrgs){
               var data=parent && parent.childNodes||this.orgTree.model.items;

               for(var i=0,l=data.length;i<l;i++){
                    if(reg.test(data[i].name)){
                        data[i].parentName=parent?parent.name:'';
                        if(data[i].type==0){
                            orgs.push(data[i]);
                        }else{
                            prjOrgs.push(data[i]);
                        }                        
                    }

                    if(data[i].childNodes){
                        this.filterOrg(reg,data[i],orgs,prjOrgs);
                    }
               }
            }
        });
    }

    function createNavbar(activeMenu) {
        var elm = $('.ct-page-navbar')[0];
        if (!elm) return;

        elm.style.zIndex=6;
        var navBar = new ListView({
                element: elm,
                itemTemplate: '<li><a href="{{item.url}}">{{item.text}}</a></li>',
                canSelect: true,
                emptyText: null,
                renderContent: function() {
                    if (!this.element) return;

                    $(this.element).addClass('nav navbar-nav');
                    ListView.prototype.renderContent.call(this);

                    var arr = [{
                        text: '首页',
                        url: '/labor/index.jsp'
                    }];

                    if (productData && productData.moduleList){
                        arr = productData.moduleList;
                    }

                    if (arr) {//如果存在菜单，则创建下拉菜单节点（此处没有判断现有菜单宽度是否超出内容区大小）
                        this.load(arr);
                        elm.insertAdjacentHTML('beforeEnd', '<li class="dropdown nav-menu-item-more" style="display:none;float:right;right:10px;">' + 
                            '<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="true">'+
                            '<span class="glyphicon glyphicon-th-list"></span><span class="caret"></span></a>'+
                            '<ul class="dropdown-menu" data-role="menu" style="left:-200px;width:250px;"></ul></li>');
                    
                        setTimeout(resizeMenu,10);
                        
                        var tid;

                        window.addEventListener('resize', function() {
                            if (tid) {
                                clearTimeout(tid);
                            }
                            tid = setTimeout(resizeMenu, 200);
                        });
                }
            },
            selectMenu: function(menuText) {
                for (var i = 0, len = this.model.items.length; i < len; i++) {
                    if (this.model.items[i].text === menuText) {
                        this.selectItemUI(i);
                        return;
                    }
                }
            }
        });
    activeMenu && navBar.selectMenu(activeMenu);
}

    function resizeMenu() {//调整导航菜单展现形式，如果无法都显示，出现下拉菜单
        var aw = 0,//从左至右计算菜单宽度和
            items = $('.ct-page-navbar>li:not(.nav-menu-item-more)'),
            l = items.length;

        if (!window.__menuWidths) {//缓存页面打开时导航菜单宽度，页面大小改变时使用
            window.__menuWidths = {}
            for (var i = 0; i < l; i++) {
                window.__menuWidths[i] = items[i].offsetWidth;
            }
        }

        $('.nav-menu-item-more .dropdown-menu').html('');
        $('.nav-menu-item-more').hide();

        items.each(function(i, item) {
            var w = window.__menuWidths[i] || 0,
                sw= $('.ct-page-body').width();

            if ((aw + w) >= sw) {//如果菜单宽度和超出页面内容滚动宽度，显示下拉菜单
                $('.nav-menu-item-more').show();
                var sIndex;
                var dropmenu = $('.nav-menu-item-more .dropdown-menu');

                if ((aw + $('.nav-menu-item-more').width()) > sw) {//算出添加到下拉菜单起始位置
                    sIndex = i - 1;
                } else {
                    sIndex = i;
                }

                var child, href;
                for (var j = sIndex; j < l; j++) {
                    if (/(active)/.test(items[j].className)) {//如果是当前打开页面菜单，则把上一菜单隐藏
                        items[sIndex - 1].style.display = 'none';
                        child = items[sIndex - 1].childNodes[0];
                    } else {
                        items[j].style.display = 'none';
                        child = items[j].childNodes[0];
                    }

                    href = child.getAttribute('href');
                    dropmenu.append('<li><a href="' + href + '" style="line-height:40px;border:none!important;">' + child.innerHTML + '</a></li>');
                }

                return false;
            } else {
                aw += w;
                if (item.style.display == 'none') {
                    item.style.display = '';
                }
            }
        });
    }

var suggestDlg = new Dialog({
                    width: '500px',
                    height: '250px',
                    bodyHTML: '<div style="text-align:center;margin-bottom:20px;font-size:16px;font-weight:bold;">建议使用下方的浏览器进行访问:</div>' +
                                '<div style="display: block;text-align: center;">' +
                                '<div style="display:inline-block;margin-right:30px;"><a href="http://www.google.cn/chrome/" target="blank"><img src="/images/chrome.png" style="width: 100px;height:100px;"></a>' +
                                '<div class="link_word"><a href="http://www.google.cn/chrome/" target="blank">Chrome 36及以上</a></div> </div>' +
                                '<div style="display:inline-block;"><a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" target="blank">' +
                                '<img src="/images/ie9.png" style="width: 100px;height:100px;"></a>' +
                                '<div class="link_word"><a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" target="blank">' +
                                '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IE9 及以上</a></div>' +
                                '</div></div><div style="display:none;bottom: 0px;position: absolute;right: 5px;">' +
                                '<span>7天之内不再提示&nbsp;<input type="checkbox" class="chk-browsersuggest"/></span></div>',
                    footerHTML: '<button type="button" class="btn btn-ok btn-success">确定</button>' +
                                '<button type="button" class="btn btn-cancel btn-default" data-dismiss="modal">取消</button>',
                    title: '提示',
                    listeners: [
                        ['click', '.btn-success',
                            function(event, elm, dlg) {
                                // if ($('.chk-browsersuggest')[0].checked) {
                                //   $.cookie('nottipperweek', 1, {
                                //     expires: 7,
                                //     path:'/'
                                //   });
                                // } else {
                                //   $.cookie('nottipperweek', null);
                                // }
                                dlg.hide();
                            }
                        ]
                    ]
});

var PageFrame = View.extend({
    element: document.body,
    renderContent: function() {
        createHeader();
        createNavbar(this.activeMenu);
        this.setActiveItem(0);
        this.checkBrowserInfo();
        if(window.expiredProducts){
            Dialog.showHint(window.expiredProducts,'red-bg',5000);
            delete window.expiredProducts;
        }
    },
    checkBrowserInfo: function() {
        var browserInfo = $.fn.isIE(),
            preferred = true;

        if (browserInfo === 8) {
            preferred = false;
        } else if (browserInfo == false) {
            browserInfo = /(.*?)(Chrome\/(\d+))\./i.exec(navigator.userAgent);

            if (browserInfo == null || parseInt(browserInfo[3]) < 36) {
                preferred = false;
            }
        }

        if (preferred == false && $.cookie('suggested') !== '1') {
            appendShim(suggestDlg.contentElm, 380);
            suggestDlg.show();
            $.cookie('suggested', 1);
        }
    },
    setActiveItem: function(idx) {
        $('.ct-page-body').css('display', 'none');
        $('.ct-page-body:eq(' + idx + ')').css('display', 'block');
    }
});
//增加检查权限的（静态）方法
PageFrame.$checkAuthority = function(fnCode) {
    var authorised = false;
    var arr = productData.authorisedFunctions;
    for (var k = 0; k < arr.length; k++) {
        if (fnCode === arr[k].code) {
            authorised = true;
            break;
        }
    }
    return authorised;
}; $('.ct-stat-grid .ct-body').on('scroll', function(e) {
    var jElm = $(this),
        scrollX = this.scrollLeft;
    var b = $('.ct-header', this.parentNode)[0];
    b && (b.scrollLeft = scrollX);
});

Number.prototype.toFixed = function(d) {
    var s = this + "";
    if (!d) d = 0;
    if (s.indexOf(".") == -1) s += ".";
    s += new Array(d + 1).join("0");
    if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$").test(s)) {
        var s = "0" + RegExp.$2,
            pm = RegExp.$1,
            a = RegExp.$3.length,
            b = true;
        if (a == d + 2) {
            a = s.match(/\d/g);
            if (parseInt(a[a.length - 1]) > 4) {
                for (var i = a.length - 2; i >= 0; i--) {
                    a[i] = parseInt(a[i]) + 1;
                    if (a[i] == 10) {
                        a[i] = 0;
                        b = i != 1;
                    } else break;
                }
            }
            s = a.join("").replace(new RegExp("(\\d+)(\\d{" + d + "})\\d$"), "$1.$2");

        }
        if (b) s = s.substr(1);
        return (pm + s).replace(/\.$/, "");
    }
    return this + "";
};

function fixNumber(num, n) // 四舍五入保留n位小数
{
    num = parseFloat(num);
    if (!isNaN(num)) {
        num = num.toFixed(n);
        numArr = num.split('.');
        numArr[1] = numArr[1] ? numArr[1].replace(/0*$/gi, '') : '';
        if (numArr[1]) {
            num = numArr.join('.');
        } else {
            num = numArr[0];
            num = parseFloat(num);
        }
        return num;
    } else {
        return 0;
    }
}

template.helper('fixNumber', fixNumber);
return PageFrame;
});