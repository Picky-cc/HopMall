define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;

    var CreateBillView = FormDialogView.extend({
        template: _.template($('#showCreateBillTmpl').html(), {
            variable: 'obj'
        }),
        className: 'modal fade form-modal',
        events: {
            'change [name=keyWord]': 'onChangeKeyWord',
        },
        actions: {
            save: '../billing-plan/leasing-bill',
            query_contract_info: './query-simplified-contract-info',
            query_entry_type_bill_type_info: './query-entry-type-bill-type-info'
        },
        initialize: function(options) {
            CreateBillView.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({});
        },
        validate: function() {
            return this.validator.form();
        },
        extractDomData: function() {
            var attr = this._extractDomData(this.$('.real-value'), true);
            return attr;
        },
        submitHandler: function(e) {
            if (!this.validate()) return;
            this.save();
            this.hide();
        },
        save: function() {
            var self = this;
            var attr = this.extractDomData();
            var model = this.model;

            var opt = {
                url: this.actions.save,
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(attr),
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    attr.billingPlanNumber = resp.data.billingPlanNumber;
                    model.set(attr);
                } else {
                    popupTip.show(resp.message);
                }
            };

            $.ajax(opt);
        },
        render: function() {
            var data = this.model.toJSON();

            $.extend(data, this.fetchEntryTypeAndBillType());

            data.simplifedContractInfoList = this.syncFetchSimplifiedContractInfo(data['subjectMatterSourceNo']);

            this.$el.html(this.template(data));
        },
        onChangeKeyWord: function() {
            var self = this;

            var callback = function(resp) {
                var simplifedContractInfoList = resp.data.list;
                var options = [];

                for (var i in simplifedContractInfoList) {
                    var contractInfo = simplifedContractInfoList[i];
                    options.push('<option value="' + contractInfo.businessContractUuid + '">' + contractInfo.contractName + '</option>')

                }

                self.$("[name=contractUuid]").html(options.join());
            };

            this.fetchSimplifiedContractInfo(true, callback);
        },
        syncFetchSimplifiedContractInfo: function(subjectMatterSourceNo) {
            var list = [];

            var callback = function(resp) {
                list = resp.data.list;
            };

            this.fetchSimplifiedContractInfo(false, callback, subjectMatterSourceNo);

            return list;
        },
        fetchSimplifiedContractInfo: function(async, callback, keyWord) {
            var opt = {
                url: this.actions.query_contract_info,
                type: 'get',
                async: async,
                dataType: 'JSON',
                data: {
                    appId: this.model.get('appId'),
                    keyWord: async ? $('[name="keyWord"]').val() : keyWord
                }
            };

            opt.success = function(resp) {
                callback && callback(resp);
            };

            $.ajax(opt);
        },
        fetchEntryTypeAndBillType: function() {
            var result = {};

            var opt = {
                url: this.actions.query_entry_type_bill_type_info,
                type: 'get',
                async: false,
                data: {
                    appId: this.model.get('appId')
                },
                dataType: 'JSON',
            };

            opt.success = function(resp) {
                var entryTypeMap = resp.data.entryType;
                var billTypeMap = resp.data.billType;
                result['entryTypeMap'] = entryTypeMap;
                result['billTypeMap'] = billTypeMap;
            };

            $.ajax(opt);

            return result;
        }
    });

    module.exports = CreateBillView;

});