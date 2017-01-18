define(function (require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');

    var $ = jQuery;

    module.exports = TableContentView.extend({
        events: {
            'click .text-overflow': function(e) {
                if(window.getSelection) {
                    var range = document.createRange();
                    range.selectNode($(e.target)[0]);
                    window.getSelection().addRange(range); // 选择对象
                    document.execCommand("Copy"); // 执行浏览器复制命令
                }
            }   
        }
    });

});