define(function(require, exports, module) {
    var path = require('scaffold/path');
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    var baseFormView = require('baseView/baseFormView');
    var FormDialogView = baseFormView.FormDialogView;

    var dialogView = new DialogView();
    var $ = jQuery;
    var root = global_config.root;

    var AddQuestionnaireView = FormDialogView.extend({
        template: _.template($('#addQuestionnaire').html()),
        id: 'addQuesDialog',
        className: 'modal fade form-modal',
        events: {
            'change  select': 'onclickChangetype'
        },
        initialize: function(opt) {
            this.categorys = opt.categorys;

            AddQuestionnaireView.__super__.initialize.apply(this, arguments);

            this.defineValidator();
        },
        render: function() {
            var data = this.model.toJSON();
            data.categorys = this.categorys;
            this.$el.html(this.template(data));
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    type: 'required'
                },
                ignore: ':disabled'
            });
        },
        validate: function() {
            return this.validator.form();
        },
        submitHandler: function(e) {
            e.preventDefault();
            if (!this.validate()) return;
            this.save();
            this.hide();
        },
        onclickChangetype: function(e) {
            var type = $(e.target).val();
            if (type == "自定义类别") {
                this.$("input").removeAttr("disabled");
            } else {
                this.$("input").attr("disabled", true);
            }
        }

    });

    var EditQuestionnaireView = Backbone.View.extend({
        el: '.form-wrapper',
        events: {
            'click .btn-questionnaire': 'onClickAddquestionnaire',
            'click .del-questionnaire': 'onClickDelquestionnaire',
            'click .submit': 'onclickSubmit'
        },
        quesItemTmpl: _.template($("#ItemQuestionnaire").html()),
        initialize: function(customerUuid) {
            this.customerUuid = customerUuid;
            try {
                this.categorys = JSON.parse(this.$('[name=categorys]').val()) || [];
            } catch (err) {
                this.categorys = [];
            }
        },
        onClickAddquestionnaire: function(e) {
            e.preventDefault();

            var self = this;
            var model = new Backbone.Model();
            var addQuestionnaireView = new AddQuestionnaireView({
                model: model,
                categorys: this.categorys
            });

            addQuestionnaireView.show();

            model.once('change', function() {
                var data = model.toJSON();
                var htm = self.quesItemTmpl(data);
                var bd;
                $(".hd").each(function() {
                    var $el = $(this);
                    if ($el.text() === data.type || $el.text() === data.text) {
                        bd = $el.parent().find(".bd");
                    }
                });

                if (bd) {
                    bd.append(htm);
                } else {
                    var fieldset = '<fieldset class="register-info fieldset-group"><h5 class="hd questionnaire-list"></h5>' + htm + '<div class="bd"></div></fieldset>';
                    self.$(".submit").parents("fieldset").before(fieldset);
                    self.$("fieldset:last").prev().find("h5").text(data.text);
                    self.categorys.push(data.text);
                }
            });

        },
        onClickDelquestionnaire: function(e) {
            e.preventDefault();
            var item = $(e.target).parents(".filed-block");
            var answer = item.find("input").val();
            if (answer) {
                dialogView.show("该问题已有答案，是否继续删除？");
                this.listenTo(dialogView, 'goahead', function(e) {
                    item.remove();
                    dialogView.hide();
                });
            } else {
                item.remove();
            }

        },
        validate: function() {
            var flag = true;
            var els = this.$('.multiline-input');
            els.each(function() {
                if($(this).val().trim() === '') {
                    $(this).addClass('error');
                    flag = false;
                }else {
                    $(this).removeClass('error');
                }
            });
            return flag;
        },
        onclickSubmit: function(e) {
            e.preventDefault();
            if(!this.validate()) return;
            this.save();
        },
        save: function() {
            var questionnaire = {};
            questionnaire.definition = $(".questionnaire-tittle").parent().find("textarea").val();
            questionnaire.qaMap = [];

            this.$(".questionnaire-list").each(function() {
                var queslist = {};
                queslist.type = $(this).text();
                queslist.qas = [];
                $(this).parent().find(".field-value").each(function() {
                    var detail = {};
                    detail.question = $(this).find("textarea").val();
                    detail.answer = $(this).find("input").val();
                    queslist.qas.push(detail);
                });
                questionnaire.qaMap.push(queslist);
            });

            var opt = {
                url: root + '/preloan/customer/configure',
                data: {
                    k: 'questionnaire',
                    questionnaire: JSON.stringify(questionnaire),
                    customerUuid: this.customerUuid
                },
                type: 'post',
                dataType: 'JSON'
            };
            var self = this;
            opt.success = function(resp) {
                if (resp.code == 0) {
                    popupTip.show('成功，正在跳转');
                    setTimeout(function() {
                        location.href = root + '/preloan/customer?k=questionnaire&customerUuid=' + self.customerUuid
                    }, 1500);
                } else {
                    popupTip.show(resp.message);
                }
            };
            $.ajax(opt);
        }
    });

    module.exports = EditQuestionnaireView;
});