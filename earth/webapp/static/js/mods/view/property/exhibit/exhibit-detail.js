define(function(require, exports, module) {
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');

    var $ = jQuery,
        root = global_config.root;

    var PublishDetailView =Backbone.View.extend({
    	el: '.static-info-wrapper',
    	actions: {
            'publish': root + '/subject-matter/exhibit/publish',
            'rollback': root+ '/subject-matter/exhibit/rollback',
        },
        events: {
    		'click .publish': 'onClickPublish',
            'click .rollback': 'onClickRollback'
    	},
        initialize: function (subjectMatterUuid) {
            this.subjectMatterUuid = subjectMatterUuid;
        },

        onClickPublish:function (e) {
            e.preventDefault();
            var uuid = this.subjectMatterUuid;
            if(uuid) {
                this.postJSON('publish', {
                    leasingSubjectMatterUuid: uuid
                }, function(resp) {
                    popupTip.show('成功');
                    var timer = setTimeout(function () {
                        location.reload();
                    }, 1000);
                });
            }
        },

        onClickRollback:function (e) {
            e.preventDefault();
            var uuid = this.subjectMatterUuid;
            if(uuid) {
                this.postJSON('rollback', {
                    leasingSubjectMatterUuid: uuid
                }, function(resp) {
                    popupTip.show('成功');
                    var timer = setTimeout(function () {
                        location.reload();
                    }, 1000);
                });
            }

        },
        postJSON: function(actionName, data, success) {
            var self = this;
            var action = this.actions[actionName];

            var opts = {
                url: action,
                type: 'post',
                data: data
            };

            opts.success = function(resp) {
                resp = JSON.parse(resp);
                if(resp.code != 0) {
                    popupTip.show(resp.message);
                }else {
                    success && success(resp);
                    
                }
            };

            $.ajax(opts);
        },
    });

    exports.PublishDetailView = PublishDetailView;

});