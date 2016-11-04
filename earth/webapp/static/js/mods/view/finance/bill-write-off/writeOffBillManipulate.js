define(function(require, exports, module) {
    var DialogView = require('component/dialogView');

    var ManipulateLogView = DialogView.extend({
        events: {
            'click .fold': 'onFoldClick'
        },
        manipultateLogTmpl: _.template($('#manipultateLogTmpl').html()),
        initialize: function() {
            ManipulateLogView.__super__.initialize.apply(this, arguments);

            var attr = this.model.getLog();
            var htm = this.manipultateLogTmpl(attr);
            this.showRichText(htm);
        },
        onFoldClick: function(e) {
            e.preventDefault();
            var tar = $(e.target);
            if(tar.hasClass('in')) {
                tar.text('收起');
                tar.removeClass('in');
                this.foldIn();
            }else {
                tar.text('展开');
                tar.addClass('in');
                this.foldOut();
            }
        },
        foldIn: function(len) {
            var logs = this.$('#logList .log-item');
            logs = logs.filter(function(index) {
                return index > 0;
            });
            logs.show();
        },
        foldOut: function(len) {
            var logs = this.$('#logList .log-item');
            logs = logs.filter(function(index) {
                return index > 0;
            });
            logs.hide();
        }
    });

    exports.ManipulateLogView = ManipulateLogView;

});