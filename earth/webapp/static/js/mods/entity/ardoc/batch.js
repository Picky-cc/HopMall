define(function (require, exports, module) {
    var $ = jQuery;
    var root = global_config.root;

    var ARBatchModel = Backbone.Model.extend({
        initialize: function () {
            
        }
    }, {
        isFinish: function (params, callback) {
            var url = root + '/loan/audit/isfinish';
            $.getJSON(url, params, function (resp) {
                callback && callback(resp);
            });
        },
        close: function (params, callback) {
            var url = root + '/loan/audit/close-batch';
            $.getJSON(url, params, function (resp) {
                callback && callback(resp);
            });
        },
        refute: function (params, callback) {
            var url = root + '/loan/audit/reject-batch';
            $.getJSON(url, params, function (resp) {
                callback && callback(resp);
            });
        }
    });

    module.exports = ARBatchModel;
});