define(function(require, exports, module) {
    var DialogView = require('./dialogView');

    // var PopupTip = DialogView.extend({
    //     initialize: function () {
    //         PopupTip.__super__.initialize.apply(this, arguments);
    //         this.$('.goahead').hide();
    //     }
    // });

    module.exports = new DialogView({
        cancelBtnTxt: '关闭',
        bodyInnerTxt: '系统繁忙请稍候重试',
        excludeGoahed: true
    });

});