/**
 * Created by scrollbar on 2015/6/22.

 CloudTFileServiceClient.remove(id) 移除
 setKey(id, key) 修改文件名称
 getAccessUrl(key, expireMinutes) 获取访问url
 persistByKey(keys) 持久化

 */
define(function (require) {
    var CloudTFileServiceClient = require("/lib/common/cloudt/1.0.0/js/file-service.js"),
        Observable = require('Sparrow/Observable'),
        Dialog = require('Sparrow/ModalDialog');

    CloudTFileServiceClient.setProductType("labor");
    CloudTFileServiceClient.setTempUploadFlag(true);
    var downUrl = '/download.jsp?productType=labor&key=';
    var defaultAddCtrl = '.lbr-file-add-view',
        defaultListCtrl = '.lbr-file-upload-list';

    function getListHTML(data, readOnly) {
        var t = [];
        for (var i = 0; i < data.length; i++) {
            var attachSize = "";
            if (data[i].size != undefined && data[i].size != null) {
                attachSize = data[i].size;
                var num = 0;
                while (attachSize >= 1024) {
                    attachSize = attachSize / 1024;
                    num++;
                }
                if (jQuery.isNumeric(attachSize) && attachSize.toFixed) {
                    attachSize = attachSize.toFixed(2);
                }
                if (attachSize > 0) {
                    if (num == 1) {
                        attachSize = '(' + attachSize + 'KB)';
                    } else if (num == 2) {
                        attachSize = '(' + attachSize + 'MB)';
                    }
                } else {
                    attachSize = '(0MB)';
                }
            }
            var fileId = data[i].fileId;
            var title = data[i].fileName + attachSize;
            var fileName = data[i].fileName + '<span class="text-muted">' + attachSize + '</span>';
            var flag=false;
            //增加对照片的过滤，如果是照片就显示连接，点击会放大照片
            var imgSuffix=["jpg","JPG","BMP","bmp","GIF","gif","jpeg","JPEG","png","PNG"];
            for(var j=0;j<imgSuffix.length;j++) {
                if (fileName.indexOf(imgSuffix[j]) > 0 && data[i].name != "") {
                    flag=true;
                    break;
                }
            }
            var htm ='';
            //如果是照片，增加A标签，设置data-lightbox="example"，以及href,点击后会放大照片
            if(flag){
                htm = '<div class="lbr-div-file-opt" style="margin:5px">' +
                    ' <div class="form-group text-overflow" title="' + title +
                    '" style="width:170px;margin-right:0">' +
                '<a data-lightbox="example" href="/download.jsp?productType=labor&key='+encodeURIComponent(data[i].name)+'">' + fileName + '</a></div>' + (data[i].id ? ' <div class="form-group"><a href="#" data-value="' + data[i].name +
                    '" class="lbr-file-opt-down">下载</a></div>' : '') + (!readOnly ? ' <div class="form-group"><a href="#" ' + (data[i].fileId ? 'data-new=1 data-id=' + fileId : 'data-new=0 data-id=' + data[i].id) + ' data-value="' + data[i].name + '" class="lbr-file-opt-del">删除</a></div>' : '') + '</div>';
            }else{
                htm = '<div class="lbr-div-file-opt" style="margin:5px">' +
                    ' <div class="form-group text-overflow" title="' + title +
                    '" style="width:170px;margin-right:0">' + fileName + '</div>' + (data[i].id ? ' <div class="form-group"><a href="#" data-value="' + data[i].name +
                    '" class="lbr-file-opt-down">下载</a></div>' : '') + (!readOnly ? ' <div class="form-group"><a href="#" ' + (data[i].fileId ? 'data-new=1 data-id=' + fileId : 'data-new=0 data-id=' + data[i].id) + ' data-value="' + data[i].name + '" class="lbr-file-opt-del">删除</a></div>' : '') + '</div>';
            }

            t.push(htm)
        }

        return t.join('');
    };

    var attachView = Observable.extend({
        serviceClient: CloudTFileServiceClient,
        allowRepeated: true,
        readOnly: null,
        config: {}, //记录附件控件配置
        moduleCode: 'labor',
        attachments: [],
        newAttachments: [],
        attachevent: ['filechange', 'filechanged', 'complete', 'error', 'success', 'removefile'],
        setModule: function (moduleCode) {
            this.moduleCode = moduleCode;
        },
        setReadOnly: function (readOnly) {
            this.readOnly = readOnly;
        },
        getAttachments: function () {
            return this.attachments;
        },
        clear: function () {
            this.attachments = [];
            this.newAttachments = [];
            this.appandAttach([], true);
        },
        //config:{addCtrl: '', listCtrl: '', fileAccepts:''}
        init: function (config, continueAdd) {
            var me = this,
                ctAttach;


            me.config = {
                addCtrl: defaultAddCtrl,
                listCtrl: defaultListCtrl
            };
            if (config) {
                if (config.addCtrl) me.config.addCtrl = config.addCtrl;
                if (config.listCtrl) me.config.listCtrl = config.listCtrl;
            }

            //权限控制
            var readOnly = me.readOnly;
            if (readOnly == null) {
                me.readOnly = _pageReadOnly == 'true' || $.cookie("allAppReadOnly")=="true";
            }

            me.attachments = [];
            if (!continueAdd)
                me.newAttachments = [];

            ctAttach = $(me.config.addCtrl);
            if ($.trim(ctAttach.html()) == "") { //根据父节内容点判断是否已经加载过
                me.config.moduleName = me.moduleCode;
                me.config.fileAccepts = config && config.fileAccepts ? config.fileAccepts : '*';
                me.config.selectFileClass = "file";
                me.config.allowRepeated = me.allowRepeated;
                me.container = CloudTFileServiceClient.getUploadContainer(me.config)
                ctAttach.append(me.container.uploadContentHtml);
                /*event{
                 *   files：文件列表
                 *       var fileItem = {
                 *       id: id,    自动生成的唯一标识
                 *           key:""     目标文件名称
                 *           size:0
                 *       };
                 *   }*/
                CloudTFileServiceClient.addEventListener("fileChanged", this.appandAttach, this);

                CloudTFileServiceClient.addEventListener("complete", function () {
                    var me = this;
                    //保存数据后，在持久化
                    CloudTFileServiceClient.successCallback.call(this, function () {
                        var sourceKeys = [],
                            attachments = me.newAttachments;
                        for (var i = 0; i < attachments.length; i++) {
                            if (attachments[i].isUpload)
                                sourceKeys.push(attachments[i].key);
                        }
                        CloudTFileServiceClient.persistByKey(sourceKeys, function (data) {
                            if (data.success) {
                                var errorFileNames = '';
                                for (var j = 0; j < data.fail_keys.length; j++) {
                                    var fk = data.fail_keys[j];
                                    for (var key in fk) {
                                        for (var i = 0; i < attachments.length; i++) {
                                            if (attachments[i].isUpload && key == attachments[i].key) {
                                                errorFileNames += ',' + attachments[i].fileName;
                                            }
                                        }
                                    }
                                }
                                if (errorFileNames.length > 0) {
                                    alert(errorFileNames.substr(1));
                                } else {
                                    me.fire("complete", this);
                                }
                            } else {
                                alert(data.msg);
                            }
                        });
                    });
                }, this);
                /*error -> 上传时出错事件
                 *event{
                 *   id: "12134567890",  文件标识
                 *   msg: "",       错误信息
                 *}*/
                CloudTFileServiceClient.addEventListener("error", function (e) {
                    //alert(e.msg);
                    this.fire("error", this);
                }, this);
                /*success -> 上传成功事件
                 *event{
                 *   id: "12134567890",  文件标识,
                 *   target_key:"" 目标文件名称
                 *}*/
                CloudTFileServiceClient.addEventListener("success", function (e) {
                    var attachments = me.newAttachments;
                    for (var i = 0; i < attachments.length; i++) {
                        if (attachments[i].fileId == e.id) {
                            attachments[i].name = e.target_key;
                            attachments[i].key = e.key;
                            attachments[i].isUpload = true;
                            break;
                        }
                    }
                    this.fire("success", e, this);
                }, this);

                me.on('filechange', function (t, data, isEdit) {
                    if (!isEdit) {
                        var toMessage = false;
                        if (data.files) {
                            for (var i = 0; i < data.files.length; i++) {
                                var fileId = data.files[i].id;
                                var size = data.files[i].size;
                                if (size > 5 * 1024 * 1024) {
                                    CloudTFileServiceClient.remove(fileId);
                                    toMessage = true;
                                }
                            }
                        }
                        if (toMessage) {
                            $(t.config.addCtrl + ' .file').val('');
                            Dialog.showHint('上传文件不能大于5M');
                            return false;
                        }
                    }
                });
            }
        },
        upload: function (callback) {
            var uploaded = false;
            if (this.newAttachments && this.newAttachments.length > 0 /*&& CloudTFileServiceClient.__files.size() > 0*/) {
                //记录保存数据回调函数
                var container = CloudTFileServiceClient.__containers.get(this.container.containerId);
                if (container && container.files.size() > 0) {
                    CloudTFileServiceClient.successCallback = callback;
                    CloudTFileServiceClient.upload({containerId: this.container.containerId});
                    uploaded = true;
                }
            }
            if (!uploaded && callback)
                callback.call(this);
        },
        appandAttach: function (data, isEdit, dlg) {
            var me = this;
            if (me.fire('filechange', this, data, isEdit) == false) {
                return;
            }
            if (!isEdit) {
                if (data.files) {
                    for (var i = 0; i < data.files.length; i++) {
                        var pos = data.files[i].key.lastIndexOf('.');
                        //显示列表与success事件里找文件用fileId，在持久化用key，下载文件用name，真难用啊
                        var attach = {
                            fileId: data.files[i].id,
                            name: '', //在success事件里赋值
                            key: '', //在success事件里赋值
                            fileName: data.files[i].key,
                            fileType: data.files[i].key.substr(pos + 1),
                            size: data.files[i].size,
                            isUpload: false,
                            containerId: me.container.containerId
                        };
                        me.attachments.push(attach);
                        me.newAttachments.push(attach);
                    }
                }
            } else {
                for (var i = 0; i < data.length; i++) {
                    data[i].containerId = me.container.containerId;
                    me.attachments.push(data[i]);
                }
            }

            var listElm = dlg ? dlg.get(this.config.listCtrl) : $(this.config.listCtrl);
            var attachList = [];
            if (me.attachments) {
                for (var i = 0; i < me.attachments.length; i++) {
                    if (me.attachments[i].containerId == me.container.containerId) {
                        attachList.push(me.attachments[i]);
                    }
                }
            }
            var htm = getListHTML(attachList, me.readOnly);
            $(listElm)[0].innerHTML = "";
            $(listElm).prepend($(htm))
            $(listElm).find("a").on("click", function (e) {
                var t = $(e.currentTarget);
                switch (e.currentTarget.innerHTML) {
                    case '下载':
                        var key = t.data('value');
                        if (key) {
                            key = encodeURIComponent(key);
                            window.open(downUrl + key);
                        }
                        break;
                    case '删除':
                        var id = t.data('id');
                        var isNew = t.data('new');
                        var list = me.attachments;
                        for (var i = 0; i < list.length; i++) {
                            if (isNew == 1 && list[i].fileId == id) {

                                for (var j = 0, n = 0; j < list.length; j++) {
                                    if (list[j] != list[i]) {
                                        list[n++] = list[j]
                                    }
                                }
                                list.length -= 1
                                break;
                            } else if (isNew == 0 && list[i].id == id) {
                                for (var j = i; j < list.length - 1; j++) {
                                    list[j] = list[j + 1];
                                }
                                list.length -= 1
                                break;
                            }
                        }
                        if (isNew == 1) {
                            var list = me.newAttachments;
                            for (var i = 0; i < list.length; i++) {
                                if (list[i].fileId == id) {
                                    for (var j = 0, n = 0; j < list.length; j++) {
                                        if (list[j] != list[i]) {
                                            list[n++] = list[j]
                                        }
                                    }
                                    list.length -= 1
                                    break;
                                }
                            }
                        }
                        if (me.isOpen)
                            me.fire('removeonefile', me, id);
                        var elm = t.parents('.lbr-div-file-opt')[0];
                        $(elm).remove();
                        //如果是新增附件，删除时要删除附件
                        if (id) CloudTFileServiceClient.remove(id);
                        me.fire('removefile', me, id);
                        break;
                }
            });
            me.fire('filechanged', me, data, isEdit);
            if (me.isOpen)
                me.fire('fileChangeAndUpload', this, data, isEdit);

        },
        addEventListener: function (eventName, handler) {
            for (var i = 0; i < this.attachevent.length; i++) {
                if (eventName == this.attachevent[i]) {
                    this.on(eventName, handler, this);
                }
            }
        }
    });
    return attachView;
});
