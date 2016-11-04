define(function(require, exports, module) {

    var PayStageItemModel = Backbone.Model.extend({
        initialize: function (attr, options) {
            this.sourceModel = options.sourceModel || {};
            this.options = options;
        },
        getContractDeadLine: function () {
            return this.sourceModel.contractBasicInfo.toJSON();
        },
        isEqualContractStartDate: function () {
            var deadLine = this.getContractDeadLine();

            var msg = '缴租第一阶段的开始日期必须等于合同开始日期';

            if(deadLine.effectiveTime !== this.get('startDate')) {
                this.trigger('payperiod:validate', false, msg);
                return false;
            }else {
                this.trigger('payperiod:validate', true);
                return true;
            }
        },
        isEqualContractEndDate: function () {
            var deadLine = this.getContractDeadLine();

            var msg = '缴租最后阶段的结束日期必须等于合同结束日期';

            if(deadLine.maturityTime !== this.get('endDate')) {
                this.trigger('payperiod:validate', false, msg);
                return false;
            }else {
                this.trigger('payperiod:validate', true);
                return true;
            }
        },
        validateStageDate: function () {
            var result = this.toJSON();
            var deadLine = this.getContractDeadLine();
            var effectiveTimeValid = deadLine.effectiveTime && new Date(deadLine.effectiveTime) > new Date(result.startDate);
            var maturityTimeValid = deadLine.maturityTime && new Date(deadLine.maturityTime) < new Date(result.endDate);

            var msg = '缴租阶段应在合同期限之内';

            if(effectiveTimeValid || maturityTimeValid) {
                this.trigger('payperiod:validate', false, msg);
                return false;
            }else {
                this.trigger('payperiod:validate', true);
                return true;
            }
        }
    });

    var ExtraRuleItemModel = Backbone.Model.extend({
        getAllClauseTypes: function() {
            var obj = JSON.parse($('#supplementaryNameAliasMap').val() || '{}');
            var res = [];
            for(var prop in obj) {
                res.push({
                    value: prop,
                    name: obj[prop]
                });
            }
            return res;
        },
        getSelectedClauseTypes: function() {
            var data = this.collection.toJSON();
            return data;
        },
        getRemainClauseTypes: function() {
            var supplementaryTerms = this.getSelectedClauseTypes() || [];
            var allRules = this.getAllClauseTypes();
            var mine = this.get('clauseType');
            var filters = _.filter(allRules, function(item) {
                if(item.value === mine) {
                    // 要包括自己
                    return true;
                }else {
                    var exist = _.find(supplementaryTerms, {
                        clauseType: item.value
                    });
                    return !exist;
                }
            });
            return filters;
        }
    });

    var LeasingContractModel = Backbone.Model.extend({
        urlRoot: './',
        idAttribute: 'businessContractUuid',
        initialize: function (attr, options) {
            this.subjectMatterInfo = new Backbone.Model();
            this.contractBasicInfo = new Backbone.Model();
            this.renterInfo = new Backbone.Model();
            this.paymentInfo = new Backbone.Model();
            this.attachmentInfo = new Backbone.Model();

            // var self = this;
            // this.listenTo(this.subjectMatterInfo, 'change', function (model) {
            //     self.set('subjectMatterInfo', model.toJSON());
            // });
            // this.listenTo(this.contractBasicInfo, 'change', function (model) {
            //     self.set('contractBasicInfo', model.toJSON());
            // });
            // this.listenTo(this.renterInfo, 'change', function (model) {
            //     self.set('renterInfo', model.toJSON());
            // });
            // this.listenTo(this.paymentInfo, 'change', function (model) {
            //     self.set('paymentInfo', model.toJSON());
            // });
            // this.listenTo(this.attachmentInfo, 'change', function (model) {
            //     self.set('attachmentInfo', model.toJSON());
            // });
        },
        parse: function (resp) {
            if(resp.code != 0) {
                // popupTip.show(resp.message);
                return;
            }else {
                var data = resp.data;
                this.fillInternalModel(data);
                return data;
            }
        },
        fillInternalModel: function (data) {
            data.paymentInfo && (data.paymentInfo.supplementaryTermInfo = data.supplementaryTermInfo);

            this.subjectMatterInfo.set(data.subjectMatterInfo);
            this.contractBasicInfo.set(data.contractBasicInfo);
            this.renterInfo.set(data.renterInfo);
            this.paymentInfo.set(data.paymentInfo);
            this.attachmentInfo.set(data.attachmentInfo);
        },
        saveAll: function (options) {
            var paymentInfo = this.paymentInfo.toJSON();
            var supplementaryTermInfo = paymentInfo.supplementaryTermInfo;
            paymentInfo.supplementaryTermInfo = null;

            this.set({
                subjectMatterInfo: this.subjectMatterInfo.toJSON(),
                contractBasicInfo: this.contractBasicInfo.toJSON(),
                renterInfo: this.renterInfo.toJSON(),
                attachmentInfo: this.attachmentInfo.toJSON(),
                paymentInfo: paymentInfo,
                supplementaryTermInfo: supplementaryTermInfo
            });
            this.save({}, options);
        }
    });

    exports.LeasingContractModel = LeasingContractModel;
    exports.PayStageItemModel = PayStageItemModel;
    exports.ExtraRuleItemModel = ExtraRuleItemModel;

});