// init.js
// 暴露全局变量

define(function (require, exports, module) {
    
    // 中文映射表
    window.ConstZHMap = {
        SubmitStatus: {
            First: "初次提交",
            Second: "再次提交"
        },
        ProjectStatus: {
            Open: "融资中",
            Close: "已关闭"
        },
        TimelineStatus: {
            Created: "未提交",
            WaitAudit: "待审核",
            Auditing: "审核中",
            WaitModify: "待修改",
            Close: "已关闭"
        }
    };

    window.global_validateHelper=function () {
        var WrongDataTypeResult={
            message: '格式不正确',
            result: false
        };
        var par=$('<div>');
        var obj={};
        var toNumber=Number;

        obj.dataTypeValidateFunc={
            string: function(val) {
                return val !=="" ? true : WrongDataTypeResult;
            },
            number: function(val) {
                return /\d+/.test(val) ? true : WrongDataTypeResult;
            },
            date: function(val) {
                var reg=/^(\d{4})[-\/\.](\d{1,2})[-\/\.](\d{1,2})$/;
                var match=reg.exec(val);
                if(!match) return WrongDataTypeResult;
                var year=toNumber(match[1]),
                    month=toNumber(match[2]),
                    day=toNumber(match[3]);
                var d=new Date(year, month-1, day);
                if(d.getFullYear() !== year 
                    || d.getMonth()+1 !== month 
                    || d.getDate() !== day) {
                    return WrongDataTypeResult;
                }
                return true;
            },
            money: function(val) {
                var reg = /^([+]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d{2})?$/;
                return reg.test(val) ? true : WrongDataTypeResult;
            }
        };

        obj.pack=function (val, type, flag, displayMode) {
            par.html('');
            var el;
            if(displayMode === 'edit') {
                if(type === 'date') {
                    var str = '<div data-toggle="datetimepicker" class="imitate-datetimepicker-input date input-group full" data-accuratetominute="">' +
                            '    <input class="form-control datetimepicker-form-control" name="" size="16" type="text" value="'+ val +'" readonly>' +
                            '    <span class="input-group-addon invisibility"><span class="glyphicon glyphicon-calendar"></span></span>' + 
                            '    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>' +
                            '</div>';
                    // var str = '<span class="form-control imitate-datepicker-input full">'
                    //             +   '<span data-toggle="datetimepicker" class="real-val val">'+ val +'</span><span class="clear clear-related-date">×</span>'
                    //         + '</span>';
                    el = $(str);
                }else{
                    el=$('<input type="text" class="form-control real-val" value="'+ val +'">');
                }
            }else if(displayMode === 'noedit') {
                el = $('<span class="form-control">'+val+'</span>')
            }
            par.append(el);
            flag !== true && (el.addClass('form-control-error'), par.append($('<div class="error"></div>').html(flag.message || '字段有误')));
            return par.html();
        };

        /*
         * @return 正确：true  错误：对象，{message: '...', result: false}
         */
        obj.validate=function (val, type, displayMode) {
            var flag = type in this.dataTypeValidateFunc ? this.dataTypeValidateFunc[type](val) : true;
            return flag;
        };

        obj.validateAndPack=function (val, type, displayMode) {
            val=$.trim(val);
            var flag = this.validate(val, type, displayMode);
            var str = this.pack(_.escape(val), type, flag, displayMode);
            return str;
        };

        return obj;
    }();

});
