define(function (require) {
    var ajax = require('./ajax.js');
    if (window.$CloudTFileServiceClient) {
        return window.$CloudTFileServiceClient;
    }
    var fsClient = window.$CloudTFileServiceClient = {
            __events: new EventMap(),
            __rootAddress: "/",
            __productType: "",
            __isTmpUpload: false,
            __containerConfigs: new CloudTMap(),
            __containers: new CloudTMap(),
            /**
             * 是否支持html5特性
             * @returns {boolean}
             */
            supportHtml5: function () {
                if (window.FormData) {
                    var xhr = null;
                    if (window.ActiveXObject) {
                        xhr = new ActiveXObject("Microsoft.XMLHTTP");
                    } else if (window.XMLHttpRequest) {
                        xhr = new XMLHttpRequest();
                    }
                    if (xhr && xhr.upload) {
                        return true;
                    }
                }
                return false;
            },
            /**
             * 设置是否上传临时文件
             * @param flag true时为临时文件
             */
            setTempUploadFlag: function (flag) {
                this.__isTmpUpload = flag;
            },
            /**
             * 设置产品名称 通过产品名称获取相应存储地址
             * @param productType 产品名称
             */
            setProductType: function (productType) {
                this.__productType = productType;
            },
            /*
             *注册事件
             *统一传递事件对象参数
             *  fileChanged -> 选择文件事件,传递
             *event{
             *   files：文件列表
             *       var fileItem = {
             *           id: id,    自动生成的唯一标识
             *           path:"",   文件名称
             *           key:""     目标文件名称
             *           size:0
             *       };
             *   }

             *  uploading -> 开始上传
             *event{
             *   id: "12134567890",  文件标识
             *}

             *  error -> 上传时出错事件
             *event{
             *   id: "12134567890",  文件标识
             *   msg: "",       错误信息
             *}

             success -> 上传成功事件
             *event{
             *   id: "12134567890",  文件标识,
             *   key:"" ,如果是临时存储，则此值是临时文件key
             *   target_key:"" 目标文件名称
             *}

             * 下面事件只有支持HTML5特性是有效
             *   progress -> 上传进度事件
             *event{
             *   id: "test123",  文件标识
             *   loaded: 106,    已经加载的数据大小
             *   total: 1036698, 文件总大小
             *   target_event: e 原始事件对象
             *}

             *  totalProgress -> 上传进度事件
             *event{
             *   loaded: 106,    已经加载的数据大小
             *   total: 1036698, 所有文件总大小
             *}

             *  abort -> 取消
             *event{
             *   id: "test123",  文件标识
             *   msg: 106,       错误信息
             *   target_event: 1036698, 原始事件对象
             *}

             *  complete -> 所有文件上传完成事件
             */
            addEventListener: function (type, listener, scope) {
                if (!type || !listener) {
                    return;
                }

                if (null === this.__events) {
                    this.__events = new CloudTMap();
                }
                if (!scope) scope = this;
                this.__events.put(type.toLowerCase(), listener, scope);
            },
            __getContainerByFileId: function (id) {
                var containerSize = this.__containers.size();
                if (containerSize <= 0) {
                    return null;
                }
                for (i = 0; i < containerSize; i++) {
                    var container = this.__containers.element(i).value;
                    if (container.files.containsKey(id)) {
                        return container;
                    }
                }
                return null;
            },
            /**
             * 获取上传容器配置
             *
             * @param config
             * @returns {*}
             * @private
             */
            __getContainerConfig: function (config) {
                if (!config) {
                    config = {fileAccepts: "*", selectFileClass: "", moduleName: ""};
                }
                else {
                    if (!config.fileAccepts) {
                        config.fileAccepts = "*";
                    }
                    if (!config.selectFileClass) {
                        config.selectFileClass = "";
                    }
                    if (!config.moduleName) {
                        config.moduleName = "";
                    }
                    if (undefined === config.isPublic || null === config.isPublic) {
                        config.isPublic = false;
                    }

                    if (undefined === config.allowRepeated || null === config.allowRepeated) {
                        config.allowRepeated = false;
                    }
                }
                var containerId = (new Date()).getTime() + "_" + Math.ceil(Math.random() * 100);
                config.containerId = containerId;
                config.isUploading = false;
                this.__containerConfigs.put(containerId, config);
                return config;
            },
            /**
             * 根据容器ID获取容器配置
             * @param containerId
             * @returns {*}
             * @private
             */
            __getContainerConfigById: function (containerId) {
                return this.__containerConfigs.get(containerId);
            },
            /**
             * 生成上传文件KEY
             * @param key
             * @param containerConfig
             * @returns {*}
             * @private
             */
            __getFileKey: function (key, containerConfig) {
               /* if (containerConfig && containerConfig.allowRepeated === true) {
                    key = (new Date()).getTime() + "/" + key;
                }*/
                return key;
            },
            /*打印控制台信息*/
            __printLog: function (content) {
                //console.log(content);
            },
            /**
             * 获取事件
             * @param type
             * @returns {*}
             * @private
             */
            __getEventListener: function (type, config) {
                return this.__events.getCondition(type.toLowerCase(), config);
            },
            /*创建http请求对象*/
            __createXMLHttpRequest: function () {
                if (window.ActiveXObject) { //如果是IE浏览器
                    return new ActiveXObject("Microsoft.XMLHTTP");
                } else if (window.XMLHttpRequest) { //非IE浏览器
                    return new XMLHttpRequest();
                }
            },
            /**
             * 执行回调
             * @param type
             * @param e
             * @private
             */
            __executeCallback: function (type, e) {
                var config = e && e.config ? e.config : null;
                var eventMap = this.__getEventListener(type, config)
                if (eventMap && eventMap.handler) {
                    var handler = eventMap.handler;
                    var scope = eventMap.scope ? eventMap.scope : this;
                    handler.call(scope, e);
                }
            }
        }
        ;


    ajax.request({
        url: window.$CloudTFileServiceClient.__rootAddress + "services/fs/type",
        async: false,
        success: function (data) {
            if (data.type === "oss") {
                require("./fs-oss.js");
            }
            else {
                require("./fs-native.js");
            }
        },
        error: function (data) {
            require("./fs-native.js");
        }
    });


    return fsClient;
});

function CloudTMap() {
    this.elements = new Array();
    //获取MAP元素个数
    this.size = function () {
        return this.elements.length;
    };
    //判断MAP是否为空
    this.isEmpty = function () {
        return (this.elements.length < 1);
    };
    //删除MAP所有元素
    this.clear = function () {
        this.elements = new Array();
    };
    //向MAP中增加元素（key, value)
    this.put = function (key, value) {
        this.elements.push({
            key: key,
            value: value
        });
    };
    //删除指定KEY的元素，成功返回True，失败返回False
    this.remove = function (key) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };
    this.update = function (key, value) {
        this.remove(key);
        this.put(key, value);
    };
    //获取指定KEY的元素值VALUE，失败返回NULL
    this.get = function (key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    };
    //获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
    this.element = function (index) {
        if (index < 0 || index >= this.elements.length) {
            return null;
        }
        return this.elements[index];
    };
    //判断MAP中是否含有指定KEY的元素
    this.containsKey = function (key) {
        var contains = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == key) {
                    contains = true;
                }
            }
        } catch (e) {
            contains = false;
        }
        return contains;
    };
    //判断MAP中是否含有指定VALUE的元素
    this.containsValue = function (value) {
        var contains = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value == value) {
                    contains = true;
                }
            }
        } catch (e) {
            contains = false;
        }
        return contains;
    };
    //获取MAP中所有VALUE的数组（ARRAY）
    this.values = function () {
        var arr = new Array();
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    };
    //获取MAP中所有KEY的数组（ARRAY）
    this.keys = function () {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    };
};

function EventMap() {
    var cloudtMap = new CloudTMap();

    //向MAP中增加元素（key, value)
    cloudtMap.put = function (key, value, scope) {
        var item = {
            key: key,
            value: value,
            scope: scope ? scope : null
        }
        this.elements.push(item);
    };
    cloudtMap.update = function (key, value, scope) {
        this.remove(key);
        this.put(key, value, scope);
    };
    //获取指定KEY的元素值VALUE，失败返回NULL
    cloudtMap.get = function (key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == key) {
                    var item = {
                        eventName: key,
                        handler: this.elements[i].value,
                        scope: this.elements[i].scope
                    }
                    return item;
                }
            }
        } catch (e) {
            return null;
        }
    };
    cloudtMap.getCondition = function (key, config) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                var el = this.elements[i];
                if (el.key == key) {
                    if (!config || !config.addCtrl) {
                        var item = {
                            eventName: key,
                            handler: el.value,
                            scope: el.scope
                        };
                        return item;
                    }
                    else {
                        var scope = el.scope;
                        if (config && config.addCtrl && scope && scope.config && scope.config.addCtrl) {
                            if (config.addCtrl == scope.config.addCtrl) {
                                var item = {
                                    eventName: key,
                                    handler: el.value,
                                    scope: scope
                                };
                                return item;
                            }
                        }
                    }
                }
            }
        } catch (e) {
            return null;
        }
    };
    return cloudtMap;
};


