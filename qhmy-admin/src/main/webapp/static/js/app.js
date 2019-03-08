define(function (require) {
    var ajax = require('Sparrow/ajax');
    var Observable = require('Sparrow/Observable');
    var cache = require('/lib/common/cloudt/1.0.0/js/cache');
    var basUrl = '/labor/services';
    var app = new Observable({
        clearCache: function () {
            cache.clear();
        },
        getAllProjects: function (callback, args) {
            var me = this;
            ajax.request({
                url: basUrl + '/home/getAllProjects?deptId=' + $.cookie("org"),
                success: function (data) {
                    if (callback) callback(data, args);
                }
            });
        },
        getCache: function (pre, fun, property, projectId, restGroup, param) {
            var key = pre + (projectId ? projectId : "");
            //if (!cache.loaded(key)) {
            ajax.request({
                async: false,
                url: basUrl + '/' + ( restGroup ? restGroup : 'worker') + '/' + fun + '?ver=' + cache.counter(key) + (param ? param : "") + (projectId ? '&projectId=' + projectId : ""),
                success: function (data) {
                    if (data) {
                        if (property == 'dicts' && data[property] && data[property].length > 0) {
                            var filterDicts = [];
                            for (var i = 0; i < data[property].length; i++) {
                                if (data[property][i].deleted == 0) {
                                    filterDicts.push(data[property][i]);
                                }
                            }
                            data[property] = filterDicts;
                        }
                        cache.counter(key, data.counter);
                        cache.data(key, data[property], property);
                    }
                }
            });
            //}
            return cache.data(key);
        },
        getWorkerTypeDictCache: function () {
            return this.getCache('dictWorkerType', 'getDictCache', 'dicts', null, "labordic", "&type=TypeOfWork");
        },
        getWorkTypeListCache: function () {
            var workTypes = this.getWorkerTypeDictCache();
            var list = [];
            for (var key in workTypes) {
                list.push(workTypes[key]);
            }
            return list;
        },
        geCertTypeDictCache: function () {
            return this.getCache('dictCertType', 'getDictCache', 'dicts', null, "labordic", "&type=TypeOfCert");
        },
        getCertListCache: function () {
            var workTypes = this.geCertTypeDictCache();
            var list = [];
            for (var key in workTypes) {
                list.push(workTypes[key]);
            }
            return list;
        },
        age: function (id) {
            if (!id || (typeof(id) != "string") || (id.length != 18))
                return 0;

            var today = new Date();
            today = today.getFullYear() + ((today.getMonth() < 9) ? '0' + (today.getMonth() + 1) : (today.getMonth() + 1)) + ((today.getDate() < 10) ? '0' + today.getDate() : today.getDate());
            var birthDay = id.substr(6, 8);
            var calDay = today.substr(0, 4) + birthDay.substr(4, 4);

            var result = (new Date()).getFullYear() - parseInt(birthDay.substr(0, 4));
            if (calDay > today)
                result--;
            return result;
        }
    });
    return app;
});