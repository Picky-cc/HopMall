define(function(require, exports, module) {
    var Backdrop = require('./backdrop');
    
    var $ = jQuery,
        $body = $('body'),
        loadingImg = global_const.loadingImg;

    var tempStr = '<div class="inner">' +
                    '<div class="bd">' +
                      '<img class="current" src="" alt="">' +
                      '<a href="javascript: void 0;" class="btn prev"></a>' +
                      '<a href="javascript: void 0;" class="btn next"></a>' +
                    '</div>' +
                    '<div class="ft">' +
                      '<ul class="imgs">' +
                      '</ul>' +
                    '</div>' + 
                '</div>';

    var Gallery = Backbone.View.extend({
        tagName: 'div',
        className: 'gallery',
        template: _.template(tempStr),
        events: {
            'click .prev': 'prevItem',
            'click .next': 'nextItem',
            'click .item': 'selectItem',
            'click': 'fadeOut'
        },
        initialize: function (options) {
            $.extend(this, { itemImgWidth: 82 }, options);
            if(this.imgs) {
                this.setPhotos(this.imgs);
            }
            this.removalItem = [];

            // this.initBackdropView();
        },
        fadeOut: function (e) {
            if(e.target !== this.el) return;
            var self = this;
            this.$el.fadeOut(150, function () {
                self.$el.detach();
            });
        },
        open: function () {
            $body.append(this.$el);
            this.$el.show();
        },
        initBackdropView: function () {
            this.backdropView = new Backdrop();
            this.backdropView.open();
            this.listenTo(this.backdropView, 'close:backdrop', this.remove);
        },
        setPhotos: function (imgs) {
            this.render(imgs);
            this.imgs = imgs;
        },
        render: function (imgs) {
            if(imgs.length>0) {
                this.$el.html(this.template());
                this.imgListEl = this.$('.imgs');
                this.currentShowEl = this.$('.current');
                this.currentShowEl.attr('src', imgs[0]);

                var str = $.map(imgs, function (item) {
                    return '<li class="item"><a><img src="'+item+'" alt=""></a></li>';
                });
                this.imgListEl
                    .width(imgs.length*this.itemImgWidth)
                    .html(str.join(''))
                    .children().first().addClass('z-active');
            }else {
                this.$el.html('<div class="nomore">没有更多</div>');
            }
        },
        waitting: function () {
            this.$el.html('').append(loadingImg.clone());
            return this;
        },
        isSpillRightBoundry: function (tar) {
            var posi = tar.position();
            var maxWidth = this.$('.ft').width();
            return (posi.left + this.itemImgWidth) > maxWidth;
        },
        isGetLeftBoundry: function (tar) {
            return tar.position().left === 0;
        },
        prevItem: function (e) {
            var currentSmall = this.$('.item.z-active');
            var prev = currentSmall.prev();
            if(this.removalItem.length>0 || prev.length>0) {
                if(this.isGetLeftBoundry(currentSmall) && this.removalItem.length>0) {
                    prev = this.removalItem.shift().prependTo(this.imgListEl);
                }
                this._selectItem(prev, currentSmall);
            }
        },
        nextItem: function (e) {
            var currentSmall = this.$('.item.z-active');
            var next = currentSmall.next();
            if(next.length>0) {
                if(this.isSpillRightBoundry(currentSmall)) {
                    var children = this.imgListEl.children();
                    this.removalItem.push(children.eq(0).remove());
                    this.removalItem.push(children.eq(1).remove());
                }else if(this.isSpillRightBoundry(next)) {
                    this.removalItem.push(this.imgListEl.children().first().remove());
                }
                this._selectItem(next, currentSmall);
            }
        },
        selectItem: function (e) {
            this._selectItem($(e.currentTarget), this.$('.item.z-active'));
        },
        _selectItem: function (tar, former) {
            former.removeClass('z-active');
            tar.addClass('z-active');
            var src = tar.find('img').attr('src');
            this.currentShowEl.attr('src', src);
        }
    });

    function getPhotos (url, params, callback) {
        if($.isFunction(params)) {
            callback = params;
            params={};
        }
        $.getJSON(url, params, function (resp) {
            resp.code == 0 ? callback(resp.data.imgs) : callback([]);
        });
        
        // setTimeout(function () {
        //     callback(['http://p6.qhimg.com/t017cc02aedc6adf161.jpg', 'http://www.pptbz.com/pptpic/UploadFiles_6909/201206/2012062620093440.jpg', 'http://img10.3lian.com/c1/newpic/09/11/14.jpg', 'http://p6.qhimg.com/t017cc02aedc6adf161.jpg', 'http://www.pptbz.com/pptpic/UploadFiles_6909/201206/2012062620093440.jpg', 'http://img10.3lian.com/c1/newpic/09/11/14.jpg', 'http://p6.qhimg.com/t017cc02aedc6adf161.jpg', 'http://www.pptbz.com/pptpic/UploadFiles_6909/201206/2012062620093440.jpg', 'http://img10.3lian.com/c1/newpic/09/11/14.jpg', 'http://p6.qhimg.com/t017cc02aedc6adf161.jpg', 'http://www.pptbz.com/pptpic/UploadFiles_6909/201206/2012062620093440.jpg', 'http://img10.3lian.com/c1/newpic/09/11/14.jpg']);
        //     // callback([]);
        // }, 1000);
    }

    $(document).on('click', '.show-gallery', function (e) {
        var galleryView;
        var curTar = $(e.currentTarget);
        var config = curTar.data();

        if(!config.action && !config.imgurls) 
            return;

        if(galleryView = curTar.data('galleryObj')) {
            galleryView.open();
        }else {
            galleryView = new Gallery();
            curTar.data('galleryObj', galleryView);


            if(config.action) {
                getPhotos(config.action, function (list) {
                    galleryView.setPhotos(list);
                    galleryView.open();
                });
            }else if(config.imgurls) {
                galleryView.setPhotos(config.imgurls.split(/\s*,\s*/));
                galleryView.open();
            }
        }

    });

    module.exports = Gallery;
});