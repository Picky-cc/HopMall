define(function(require, exports, module) {
    var win = window;
    var doc = document;

    // 仍出全局使用的插件，库
    // ==================================================
    require('scaffold/jquery.validate');
    require('component/dashboard');
    win.Gallery = require('component/gallery'); // 照片墙
    var Pagination = win.Pagination = require('component/pagination');
    var validateUtil = require('scaffold/util').validateUtil;


    // 加载系统日志
    // ==================================================
    Pagination.find('.sys-log', this.el);

    // 全局事件
    // ==================================================
    // 上传图片的删除
    $(doc).on('mouseenter', '.upload-imgs .item', function(e) {
        var tar = $(e.currentTarget);
        tar.append('<span class="delete"></span>');
    }).on('mouseleave', '.upload-imgs .item', function(e) {
        var tar = $(e.currentTarget);
        tar.find('.delete').remove();
    }).on('click', '.upload-imgs .delete, .upload-files .delete', function(e) {
        e.preventDefault();
        var tar = $(e.currentTarget);
        if (tar.is('.disabled')) return;
        tar.parent().remove();
        $(doc).trigger('delete.fileitem', tar);
    });

    // 扩展按钮的显示隐藏
    $(doc).on('mouseover', '.create-leasing', function(e) {
        $('.extend-btns').show();
    }).on('mouseleave', '.dropbtns', function(e) {
        $('.extend-btns').hide();
    });

    // 全局的ajax按钮的loading效果
    $(document)
        .ajaxStart(function(e) {
            var el = e.target.activeElement;
            if ($(el).is('[data-loading-text]')) {
                document.__loadingBtn__ = el;
                $(el).button('loading');
            }
        })
        .ajaxComplete(function() {
            var el = document.__loadingBtn__;
            $(el).button('reset');
        });

    // 添加jquery的校验规则
    // ==================================================
    (function(validator, $) {
        var regExps = validateUtil.regExps;

        validator.addMethod('mobile', function(value, element) {
            return this.optional(element) || regExps.mobile.test(value);
        }, '请输入合法手机号');

        validator.addMethod('phoneExt', function(value, element) {
            return this.optional(element) || regExps.phoneExt.test(value);
        }, '请输入合法固话或分机号');

        validator.addMethod('mPhoneExt', function(value, element) {
            return this.optional(element) || regExps.phoneExt.test(value) || regExps.mobile.test(value);
        }, '请输入正确的联系方式');

        validator.addMethod('positiveInteger', function(value, element) {
            return this.optional(element) || regExps.positiveInteger.test(value);
        }, '请输入正整数');

        validator.addMethod('positiveNumber', function(value, element) {
            return this.optional(element) || (+value) > 0;
        }, '请输入正数');

        validator.addMethod('chineseCharactor', function(value, element) {
            return this.optional(element) || regExps.chineseCharactor.test(value);
        }, '请输入汉字字符');

        validator.addMethod('excludeChineseCharactor', function(value, element) {
            return this.optional(element) || !regExps.chineseCharactor.test(value);
        }, '不能包含汉字字符');

        validator.addMethod('numberAndAlphabet', function(value, element) {
            return this.optional(element) || regExps.numberAndAlphabet.test(value);
        }, '只支持英文字母和数字');

        validator.addMethod('IDCard', function(value, element) {
            return this.optional(element) || validateUtil.isIDCardValid(value);
        }, '请输入正确的身份证号');

        validator.addMethod('nonNegativeNumber', function(value, element) {
            return this.optional(element) || (+value) >= 0;
        }, '请输入非负数');

        validator.addMethod('nonNegativeInteger', function(value, element) {
            return this.optional(element) || regExps.nonNegativeInteger.test(value) > 0;
        }, '请输入非负正数');

        validator.addMethod('money', function(value, element) {
            return this.optional(element) || regExps.money.test(value) > 0;
        }, '请输入合法的金额');

        validator.addMethod('maxDate', function(value, element, maxDate) {
            var cur = new Date(value);
            var max = new Date(maxDate);
            return this.optional(element) || cur <= max;
        }, jQuery.validator.format('不能大于 {0}'));

        validator.addMethod('minDate', function(value, element, minDate) {
            var cur = new Date(value);
            var min = new Date(minDate);
            return this.optional(element) || cur <= min;
        }, jQuery.validator.format('不能小于 {0}'));

        validator.addMethod('minRelatedElement', function(value, element, relatedElement) {
            if (this.optional(element)) return true;

            var $relatedElement = typeof relatedElement === 'string' ? $(relatedElement) : relatedElement;
            var relatedValue = $relatedElement.val();

            if (value && relatedValue) {
                return +value <= +relatedValue;
            }

            return true;
        }, '不得大于关联元素的值');

        validator.addMethod('maxRelatedElement', function(value, element, relatedElement) {
            if (this.optional(element)) return true;

            var $relatedElement = typeof relatedElement === 'string' ? $(relatedElement) : relatedElement;
            var relatedValue = $relatedElement.val();

            if (value && relatedValue) {
                return +value >= +relatedValue;
            }

            return true;
        }, '不得小于关联元素的值');

    })(jQuery.validator, jQuery);


    // 定义中文状态命名空间
    // ==================================================

    // 银行对账流水
    (function(DCFConst) {
        DCFConst.AuditStatus = {
            CREATE: '待对账',
            ISSUING: '异常',
            ISSUED: '成功',
            CLOSE: '已取消'
        };

        DCFConst.VoucherStatus = {
            CREATED: '已建',
            ISSUED: '已制证',
            LAPSE: '凭证作废'
        };

        DCFConst.SettleStatus = {
            UNPAID: '未支付',
            OUTSTANDING: '未付清',
            PAID: '已付清',
            EXCESS: '已超额'
        };

        DCFConst.SettlementModesStatus = {
            LETTER_OF_CREDIT: '信用',
            LETTER_OF_GUARANTEE: '保函',
            COLLECTION: '托收',
            REMITTANCE: '汇付',
            POINT_OF_SALE: 'POS',
            ELECTRONIC_PAYMENT: '电子支付',
            ACCEPTANCE: '承兑汇票',
            CHEQUE: '支票',
            PROMISSORY_NOTE: '本票',
            OFF_SHEET: '表外',
            DEPOSITE_RECEIVED: '预收款'
        };
    })(win.DebitCashFlowConst = {});

    // 物业状态
    (function(ProConst) {
        ProConst.PropertyStatus = {

            '0': '下游待租',
            '1': '下游已租',
            '2': '施工维护',
            '3': '上游清退'
        };

        ProConst.Orientation = {
            '0': '朝东',
            '1': '朝南',
            '2': '朝西',
            '3': '朝北',
            '4': '南北通透'
        };

        ProConst.CredentialsType = {
            '0': '身份证',
            '1': '护照',
            '2': '港澳通行证',
            '3': '警官证',
            '4': '台胞证'
        };
    })(win.PropertyConst = {});

    // 审计单对流水 对账
    (function(BConst) {
        BConst.RepaymentAuditStatus = {
            CREATE: '待对账',
            ISSUING: '部分对账',
            ISSUED: '对账成功',
            CLOSE: '对账失败'
        };

        BConst.RepaymentStatus = {
            CREATE: '待回款',
            APPLYING: '申请回款',
            ISSUING: '回款中',
            SUCCESS: '回款成功',
            FAILED: '回款失败'
        };

        BConst.ContractPaymentType = {
            NOT_PAY: '0',
            NEED_PAY: '1'
        };
    })(win.BillConst = {});

    // 租约详情
    (function(LeasingConst) {
        LeasingConst.PaymentStatus = {
            NOTDUE: '待收',
            OVERDUE: '欠收',
            CLEAR: '已收'
        };

    })(win.LeasingConst = {});
});