+function($) {
  'use strict'
  
  var Preupload = function(element) {
    
    this.$element = $(element);
    this.$prefile = $(element).find('.preupload-file');
    this.$prelink = $(element).find('.preupload-link');
    this.$relink = $(element).find('.preupload-relink');
    this.reader = new FileReader();
    
    if(this.$prelink) this.$prelink.on('click.earth.upload', $.proxy(this.upload, this));
    if(this.$relink) this.$relink.on('click.earth.reupload', $.proxy(this.reupload, this));
  };
  
  Preupload.ALLOWEDTYPES = ['jpg', 'png', 'gif', 'bmp'];
  
  Preupload.prototype.constructor = function() {
    this.$prefile.css('display', 'none');
  };
  
  Preupload.prototype.readFile = function(file) {
    this.reader.readAsDataURL(file.files[0]);
  };
  
  Preupload.prototype.upload = function() {
    var that = this;
    
    this.$prefile.click().off('change.earth.reupload').on('change.earth.upload', function(e) {
      
      e.stopImmediatePropagation();
      
      if(!that.checkType($(this).val())) {
        that.showWrongTypeErrorMessage();
        return $(this).val('');
      } else {
        that.clearErrorMessage();
      }
      
      that.reader.onload = function(e) {
        var $image = $('<img src="">');
        var $cover = $('<div class="card-cover">' + 
                         '<a href="javascript: void(0)" class="preupload-relink" data-target="#hidden-file-input" title="重新上传">' + 
                           '<i class="glyphicon glyphicon-folder-open"></i>' +
                         '</a>' + 
                       '</div>');
        $image.attr('src', this.result);
        that.$element.find('.earth-pay-card').removeClass('new').append($image, $cover).css({ 'width': '70%', 'height': 'auto' });
        that.$prelink.hide();
        that.$relink = that.$element.find('a.preupload-relink');
        that.$relink.on('click.earth.reupload', $.proxy(that.reupload, that));
        
        e.stopPropagation();
      };
      that.readFile(this);
    });
    
  };
  
  Preupload.prototype.reupload = function() {
    var that = this;
    this.$prefile.click().off('change.earth.upload').on('change.earth.reupload', function(e) {
      
      e.stopImmediatePropagation();
      
      if(!that.checkType($(this).val())) {
        that.showWrongTypeErrorMessage();
        return that.refresh();
      } else {
        that.clearErrorMessage();
      }
      
      that.reader.onload = function(e) {
        var $image = that.$element.find('.earth-pay-card > img').attr('src', this.result);
        e.stopPropagation();
      };
      that.readFile(this);
      
    });
  };
  
  Preupload.prototype.checkType = function(filename) {
    if($.inArray(filename.split('.').pop().toLowerCase() ,Preupload.ALLOWEDTYPES) == -1) return false;
    return true;
  };
  
  Preupload.prototype.showWrongTypeErrorMessage = function() {
    this.$element.append('<p class="no-bottom-margin text-danger">请上传类型为jpg，png，gif或bmp的图片！</p>');
  };
  
  Preupload.prototype.clearErrorMessage = function() {
    this.$element.find('.text-danger').remove();
  };
  
  Preupload.prototype.refresh = function() {
    this.$prefile.val('');
    if(!this.$prelink) {
      this.$prelink.show();
    } else {
      this.$prelink = $('<a href="javascript: void(0)" class="preupload-link" data-target="#hidden-file-input" title="上传图标"><i class="glyphicon glyphicon-plus" ></i></a>')
                      .appendTo(this.$element.find('.earth-pay-card'));
      this.$prelink.on('click.earth.upload', $.proxy(this.upload, this));
    }
    this.$element.find('.earth-pay-card').addClass('new').css({ 'width': '100%', 'height': '64px' }).children('img, .card-cover').remove();
  };
  
  var old = $.fn.preupload;
  
  $.fn.preupload = function() {
    return this.each(function() {
      
      var $this = $(this);
      var data = $this.data('earth.preupload');
      if(!data) $this.data('earth.preupload', (data = new Preupload(this)));
      
      data.constructor();
      
    });
  };
  
  $.fn.preupload.Constructor = Preupload;
  
  $.fn.preupload.noConflict = function() {
    $.fn.preupload = old;
    return this;
  };
  
}(jQuery);