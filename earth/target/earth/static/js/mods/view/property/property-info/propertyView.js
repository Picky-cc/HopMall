define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var DialogView = require('component/dialogView');


    var PropertyDetailView = Backbone.View.extend({
        el:'.static-info-wrapper',
        events:{
            'click .clear':'onClickClearProperty'
        },
        initialize:function (uuid) {
            this.subjectMatterUuid = uuid;
        },

        onClickClearProperty:function (e) {
            e.preventDefault();
            var subjectMatterUuid = this.subjectMatterUuid;
            var root = global_config.root;
            var url = root+'/subject-matter/property/checkout/'+subjectMatterUuid;
            var callBack = function (resp) {
                var resp = JSON.parse(resp);
                popupTip.show(resp.message);
                popupTip.once('closedialog', function() {
                    location.reload();
                });
            };
            var options = {
                url: url,
                type: 'post',
                success: callBack
            };

            $.ajax(options);
        }
    });

    exports.PropertyDetailView = PropertyDetailView;

});