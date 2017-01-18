define(function (require, exports, module) {

    var $ = jQuery;
    var root = global_config.root;

    var default_options = {
        defSelectProvince: null,
        defSelectCity: null,
        defSelectDistrict: null,
        actions: {
            provinceUrl: root+'/area/getProvinceList',
            cityUrl: root+'/area/getCityList',
            districtUrl: root+'/area/getDistrictList'
        }
    };

    function getList (url, params, callback) {
        if($.isFunction(params)) {
            callback = params;
            params = {};
        }
        $.getJSON(url, params, function (resp) {
            resp.code == 0 ? callback(resp.data) : console.warn(resp.message);
        });
    }

    function generateHtml (list, type) {
        var result = '<option value="">请选择</option>';
        for(var i = 0, len = list.length; i<len; i++) {
            var item = list[i];
            result += '<option value="'+ item.code +'">'+ item.name +'</option>';
        }
        return result;
    }

    function selectOptionDom (el, code) {
        if(code == null) return;
        var options = el.get(0).options;
        for(var i=options.length-1; i>=0; i--) {
            if(options[i].value == code) {
                options[i].selected = true;
                break;
            }
        }
    }

    function clearSelectInput (selectInput) {
        var result = '<option value="">请选择</option>';
        if($.isArray(selectInput)) {
            $.each(selectInput, function (index, item) {
               $(item).html(result); 
            });
        }else {
            selectInput.html(result);
        }
    }

    // 拿值：
    // value存在el的value中，有data('value')取得；
    // 视图实例.getValue();
    var AreaSelectView = Backbone.View.extend({
        // el: $('.area-select'), // 注意这会找到一个页面中所有的地区选择控件，可以在生成实例的时候传入el覆盖
        events: {
            'change .province-select': 'getCity',
            'change .city-select': 'getDistrict',
            'change select': 'updateValue'
        },
        initialize: function (options) {
            this.provinceSelEl = this.$('.province-select');
            this.citySelEl = this.$('.city-select');
            this.districtSelEl = this.$('.district-select');

            var htmlOptions = function (obj) {
                return {
                    defSelectProvince: obj.default_provincecode,
                    defSelectCity: obj.default_citycode,
                    defSelectDistrict: obj.default_areacode,
                };
            }(this.$el.data());
            this.options = $.extend({}, default_options, htmlOptions, options);

            this.initialSelected();
        },
        initialSelected: function () {
            var options = this.options;
            this.refresh(options.defSelectProvince, options.defSelectCity, options.defSelectDistrict);
        },
        updateValue: function () {
            var province = this.provinceSelEl.val();
            var city = this.citySelEl.val();
            var district = this.districtSelEl.val();

            this.setValue(province, city, district);
        },
        setValue: function (province, city, district) {
            var arr = [];
            if(this.provinceSelEl.length>0) {
                arr.push(province);
            }
            if(this.citySelEl.length>0) {
                arr.push(city);
            }
            if(this.districtSelEl.length > 0) {
                arr.push(district);
            }
            this.value = arr.join('-');
            this.$el.data('value', this.value);
        },
        getValue: function () {
            return this.value;
        },
        refresh: function (provinceCode, cityCode, districtCode) {
            var self = this;
            var _selOptDom = selectOptionDom;

            this.setValue(provinceCode, cityCode, districtCode);

            // 省其实没必要再刷新了
            if(this.provinceSelEl.length>0) {
                self._getProvince(function () {
                    _selOptDom(self.provinceSelEl, provinceCode);
                });
            }

            if(provinceCode && this.citySelEl.length>0) {
                self._getCity(provinceCode, function () {
                    _selOptDom(self.citySelEl, cityCode);
                });
            }

            if(provinceCode && cityCode && this.districtSelEl.length > 0) {
                self._getDistrict(cityCode, function () {
                    _selOptDom(self.districtSelEl, districtCode);
                });
            }
        },
        _getProvince: function (callback) {
            var self = this;
            getList(this.options.actions.provinceUrl, function (data) {
                self.provinceSelEl.html(generateHtml(data.provinceList, 'province'));
                callback && callback();
            });
        },
        _getCity: function (provinceCode, callback) {
            var self = this;
            getList(this.options.actions.cityUrl, {provinceCode: provinceCode}, function (data) {
                self.citySelEl.html(generateHtml(data.cityList, 'city'));
                callback && callback();
            });
        },
        getCity: function (e) {
            if(this.citySelEl.length < 1) return;

            clearSelectInput([this.citySelEl, this.districtSelEl]);
            var curSelectProvince = $(e.target).val();
            if(curSelectProvince) {
                this._getCity(curSelectProvince);
            }
        },
        _getDistrict: function (cityCode, callback) {
            var self = this;
            getList(this.options.actions.districtUrl, {cityCode: cityCode}, function (data) {
                self.districtSelEl.html(generateHtml(data.districtList, 'district'));
                callback && callback();
            });
        },
        getDistrict: function (e) {
            if(this.districtSelEl.length < 1) return;

            clearSelectInput(this.districtSelEl);
            var curSelectCity = $(e.target).val();
            if(curSelectCity) {
                this._getDistrict(curSelectCity);
            }
        }
    });

    module.exports = AreaSelectView;


});