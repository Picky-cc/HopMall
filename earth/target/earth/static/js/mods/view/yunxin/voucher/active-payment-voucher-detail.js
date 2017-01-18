define(function(require, exports, module) {
    var Pagination = require('component/pagination');
    var VoucherModel = require('./voucher-model').VoucherModel;
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var FileUploadPlugin = require('component/fileUpload');


    // var ActiveVoucherResourceDialog = FormDialogView.extend({
    //     className: 'modal fade form-modal large-form-modal in',
    //     template: _.template($('#activeVoucherResourceTmpl').html(), {
    //         variable: 'data'
    //     })
    // });

    var ActiveRepaymentDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'click .edit-note': 'onClickEditNote',
            'click .save-note': 'onClickSaveNote',
            'click .invalid': 'onClickInvalid',
            'click .writeoff': 'onClickWriteOff',
            'click .voucherResource': 'onClickVoucherResource'
        },
        initialize: function(detailId) {
            this.initModel(detailId);
            Pagination.find('.voucher-query-list', this.el);
        },
        initModel: function(detailId) {
            this.voucherModel = new VoucherModel({
                detailId: detailId
            });

            this.listenTo(this.voucherModel, 'model:activePaymentInvalid', function(resp) {
                if (resp.code == 0) {
                    location.reload();
                } else {
                    popupTip.show(resp.message);
                }
            });
            this.listenTo(this.voucherModel, 'model:updateActiveVoucherComment', function(resp) {
                if (resp.code == 0) {
                    location.reload();
                } else {
                    popupTip.show(resp.message);
                }
            });
            // this.listenTo(this.voucherModel, 'model:voucherResource', function(resp) {
            //     if (resp.code == 0) {
            //         console.log(resp.data.paths);
            //         if (_.isEmpty(resp.data.paths)) {
            //             popupTip.show('暂无原始图片凭证');
            //             return;
            //         }
            //         var model = new Backbone.Model(resp.data);
            //         var view = new ActiveVoucherResourceDialog({
            //             model: model
            //         });
            //         view.show();
            //     } else {
            //         popupTip.show(resp.message);
            //     }
            // });
        },
        onClickEditNote: function(e) {
            e.preventDefault();
            var noteContent = $('.note-content');

            $('.note-input').val(noteContent.text() == '无' ? '' : noteContent.text());
            $('.note-show').toggleClass('hide');
            $('.note-edit').toggleClass('hide');
        },
        onClickSaveNote: function(e) {
            e.preventDefault();
            var noteStr = $('.note-input').val();

            this.voucherModel.updateActiveVoucherComment(noteStr);
        },
        onClickInvalid: function(e) {
            e.preventDefault();
            this.voucherModel.activePaymentInvalid();
        },
        onClickWriteOff: function(e) {
            e.preventDefault();
        },
        onClickVoucherResource: function(e) {
        }
    });
    module.exports = ActiveRepaymentDetailView;
});