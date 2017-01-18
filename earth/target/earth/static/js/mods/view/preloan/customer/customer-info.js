define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FormDialogView = require('view/baseFormView').FormDialogView;
    var DialogView = require('component/dialogView');
    var execOnceBeforeDone = require('scaffold/util').execOnceBeforeDone;

    var loadingImgEl = global_const.loadingImg.clone();
    
    var $ = jQuery;
    var root = global_config.root;

    var AuditDialogView = FormDialogView.extend({
        template: _.template($('#auditDialogTmpl').html() || ''),
        action: root + '/preloan/sales-projects/audit',
        initialize: function(customerUuid) {
            AuditDialogView.__super__.initialize.apply(this, arguments);

            this.validator = this.$('.form').validate();
            this.customerUuid = customerUuid;
            
        },
        validate: function() {
            return this.validator.form();
        },
        submitHandler: function() {
            if(this.validate()) {
                var attr = this.extractDomData();
                attr.customerUuid = this.customerUuid;
                var success = function(resp) {
                    resp = JSON.parse(resp);
                    popupTip.show(resp.code == 0 ? '审核成功' : resp.message);
                    popupTip.once('closedialog', function () {
                        location.reload();
                    });
                };
                $.post(this.action, attr, success);
                this.hide();
            }
        }
    });


    function projectInit(el, opts) {
        el.on('click', '.submit', function(e) {
            e.preventDefault();

            var customerUuid = $(this).data('customer-uuid');
            var opts = {
                url: root + '/preloan/customer/apply-project',
                type: 'post',
                data: {
                    customerUuid: customerUuid
                },
                dataType: 'json'
            };

            opts.success = function(resp) {
                if (resp.code == 0) {
                	popupTip.show('提交立项成功，请耐心等待审核！');
                	popupTip.once('closedialog', function () {
                        location.reload();
                    });
                } else {
                    popupTip.show(resp.message);
                }
            };
            
            $.ajax(opts);
        });


        el.on('click', '.audit', function(e) {
            e.preventDefault();
            var dialog = new AuditDialogView(opts.customerUuid);
            dialog.show();
        });
    }

    function questionnaireInit(el, opts) {
        if($(".alert").html()){
            setInterval(function(){ 
               $(".alert").fadeOut("5000");
               },1000); 
        }
        el.on('click', '.arrow', function(e) {
            e.preventDefault();
            var $item = $(e.target).parents('.item');
            $item.toggleClass('expand');

        });
    }

    function otherInit(el, opts) {

    }

    function commerceInit(el, opts) {
        var isUpdating = false;
    	 
        var confirmDialog = new DialogView({
            title:'查询工商信息',
            bodyInnerTxt: '首次(仅限)工商信息查询为计费检索，是否继续？'
        });
        
        confirmDialog.on('goahead',function ($target, opts) {
            isUpdating = true;

            $target.parent().append(loadingImgEl);

            var success = opts.success;

            opts.success = function(resp) {
                // setTimeout(()=>{
                    isUpdating = false;
                    success(resp);
                // }, 3000);
            };

        	$.ajax(opts);

        	confirmDialog.hide();
        });
        	

        el.on('click', '.queryEnterpriseCredit', function(e) {
            e.preventDefault();

            if(isUpdating) return;
            
            var $target = $(e.target);

            var opts = {
                url: root + '/preloan/enterprise-credit',
                data: {
                    name: $target.data("name")
                },
                dataType: 'JSON'
            };
            
            opts.success = function(resp) {
                if (resp.code != 0) {
                    popupTip.show(resp.message || '未查询到该企业或该企业不存在！');
                } else {
                    var regNo = resp.data.regNo;
                    location.href= root + '/preloan/customer?k=commerce&regNo=' + regNo;
                }
                loadingImgEl.remove();
            };
            
            confirmDialog.show($target, opts);
        });
        
    }

    function duediligenceInit(el, opts) {
        var form = $("form");
        $("select").change(function(){
            form.submit();
        });
    }

    exports.init = function(opts) {
        var map = {
            project: projectInit,
            questionnaire: questionnaireInit,
            other: otherInit,
            commerce: commerceInit,
            duediligence: duediligenceInit
        };

        var fn = map[opts.k];

        if (typeof fn === 'function') {
            var el = $('.' + opts.k);
            fn(el, opts);
        }

    };

});