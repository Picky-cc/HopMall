define(function(require, exports, module) {
    var $ = jQuery;

    var line = [
        '    <div class="status-part">',
        '        <div class="status-line"></div>',
        '    </div>',
    ].join("");

    var status = [
        '    <div class="status-part">',
        '        <div class="status-note">',
        '           <div class="status-left status-pre">',
        '                <h4 class="font-bold exit-lease">退租</h4>',
        '                <h5 class="status-left-time"></h5>',
        '           </div>',
        '           <a class="status-detail-href" href="">',
        '               <div class="status-detail status-detail-left">',
        '                  <h4><span class="exit-lease">退租:</span><span class="status-left-time"></span></h4>',
        '               </div>',
        '           </a>',
        '        </div>',
        '        <div class="status-line"></div>',
        '        <div class="status-note">',
        '           <div class="status-right status-pre">',
        '                <h4 class="font-bold">租出</h4>',
        '                <h5 class="status-right-time"></h5>',
        '           </div>',
        '           <a class="status-detail-href" href="">',
        '               <div class="status-detail status-detail-right">',
        '                   <h4><span class="font-bold lease-start"></span>&nbsp;&nbsp;<span>出租给:<span class="status-detail-name"></span></h4>',
        '                   <h4>合同租期：<span class="status-time"></span></h4>',
        '               </div>',
        '           </a>',
        '        </div>',
        '    </div>',
    ].join("");
    var root= global_config.root;
    var TimelineView = Backbone.View.extend({
        el: '.static-info-wrapper',
        events: {
            'mouseover .rented-point': "onMouseoverShow",
            'mouseover .status-pre': "onMousepreleaveShow",
            'mouseleave .status-detail-gray': "onMouseleaveHide",
            'click .create-leasing': "onClickCreate"
        },
        onClickCreate:function (e) {

        },
        onMouseoverShow: function(e) {
            var target = this.$(e.target);
            if (target.prev().children().length > 1) {
                target.prev().find(".status-detail").show();
                target.prev().find(".status-pre").hide();
            } else {
                target.next().find(".status-detail").show();
                target.next().find(".status-pre").hide();
            }
        },
        onMousepreleaveShow:function(e){
            var target = this.$(e.currentTarget);
            target.next().find(".status-detail").show();
            target.hide();
        },
        onMouseleaveHide: function(e) {
            var target = this.$(e.currentTarget);
            target.prev().unbind();
            target.parent().prev().show();
            target.hide();
        },

        getConfig: function() {
            var data = JSON.parse($("#contractRecordList").val());

            return data;
        },

        initialize: function() {
            this.data = this.getConfig();
            this.render();
            if(this.$(".time-line-axis").children().length>0){
                this.$(".time-line-wrapper").show();
            }
            this.setRentalrates();
        },

        render: function() {
            var rental = this.data.businessContractInfo;
            this.addNorentalLine();
            for (var i = 0; i < rental.length; i++) {
                if (rental[i].contractLifeCycleEnum == 'Executing') {
                    this.addGreenLeaseStatus(rental[i].startDateStr, rental[i].endDateStr, rental[i].appendixRentername,rental[i].businessContractUuid);
                    this.addNorentalLine();
                } else if (rental[i].contractLifeCycleEnum == "Complete" ) {
                    this.addGreyLeaseStatus(rental[i].startDateStr,rental[i].retreatDateStr, rental[i].endDateStr, rental[i].appendixRentername,rental[i].businessContractUuid);
                    this.addNorentalLine();
                } else if (rental[i].contractLifeCycleEnum == ""){
                    this.addGreyLeaseStatus(rental[i].startDateStr,'',rental[i].endDateStr,rental[i].appendixRentername,rental[i].businessContractUuid);
                    this.addNorentalLine();
                }
            }
        },
        // 虚线段
        addNorentalLine: function() {
            var $htm = $(line);
            $htm.find(".status-line").addClass("dashed-line");
            $(".time-line-axis").append($htm);
        },
        // 实线段
        addRentalLine: function() {
            var $htm = $(line);
            $htm.find(".status-line").addClass("solid-line").height('50px');
            $(".time-line-axis").append($htm);
        },
        // 正在出租段
        addGreenLeaseStatus: function(starttime, endtime, name,id) {
            var StarTIME = starttime.split('-').join('/');
            var EndTIME = endtime.split('-').join('/');
            var href = root+'/business-contract/leasing-contract-view/'+id;
            var startTime = new Date(StarTIME);
            var endTime = new Date(EndTIME);
            var today = new Date("2018/5/1");

            var LeftDate = (endTime.getTime() - today.getTime()) / 172800000;
            var Nowdate = (today.getTime() - startTime.getTime()) / 172800000;
            var point1 = '<span class="point point-small point-dashed-green"></span>';
            var point2 = '<span class="point point-small point-green"></span>';

            var $htm = $(status);
            $htm.find('.status-line').before(point1).after(point2);
            $htm.find('.status-detail-left').addClass("status-detail-green").find(".exit-lease").html("预计退租:");
            $htm.find(".status-left-time").html(EndTIME);
            $htm.find('.status-detail-right').addClass("status-detail-green").find(".status-detail-name").html(name);
            $htm.find('.lease-start').html(StarTIME);
            $htm.find('.status-time').html(StarTIME+'-'+EndTIME);
            $htm.find('.status-pre').remove();
            $htm.find('.status-line').addClass("solid-line solid-line-green").height(Nowdate + 'px');
            $htm.find('.status-line').before('<div class="status-line solid-line leftDate"></div>');
            $htm.find('.leftDate').height(LeftDate + 'px');
            $htm.find('.status-detail-href').attr('href',href);
            $(".time-line-axis").append($htm);
        },
        // 租约实心段
        addGreyLeaseStatus: function(starttime, realendtime, endtime, name,id) {
            var StarTIME = starttime.split('-').join('/');
            var EndTIME = endtime.split('-').join('/');
            var RealendTIME = realendtime.split('-').join('/');
            var href = root+'/business-contract/leasing-contract-view/'+id;
            var startTime = new Date(StarTIME);
            var endTime = new Date(EndTIME);
            var date = (endTime.getTime() - startTime.getTime()) /172800000;

            var $htm = $(status);
            var point = '<span class="point point-small rented-point"></span>';
            $htm.find('.status-line').before(point).after(point).addClass("solid-line").height(date + 'px');
            $htm.find('.status-right-time').html(StarTIME);
            if(realendtime<endtime){
                $htm.find(".exit-lease").html("提前退租");
            }else if(RealendTIME == ''){
                $htm.find(".exit-lease").html("预计退租");
            }
            $htm.find('.lease-start').html(StarTIME);
            $htm.find('.status-left-time').html(RealendTIME);
            $htm.find('.status-detail-left').addClass("status-detail-gray");
            $htm.find('.status-detail-right').addClass("status-detail-gray").find(".status-detail-name").html(name);
            $htm.find('.status-time').html(StarTIME+'-'+EndTIME);
            $htm.find('.status-detail-href').attr('href',href);
            $(".time-line-axis").append($htm);
        },

        setStartEndtime: function (starttime,endtime) {
            this.$(".endhouse-time").html(starttime);
            this.$(".gethouse-time").html(endtime);
        },

        setRentalrates: function() {
            var rented = 0;
            var endtime = new Date(this.$(".endhouse-time").html());
            var gettime = new Date(this.$(".gethouse-time").html());
            var alltime = endtime.getTime()-gettime.getTime();
            var today = (new Date("2019/5/1")).getTime();/*改成当日时间*/
            var arr = this.data.businessContractInfo;
            for(var i=0;i<arr.length;i++){
                if(arr[i].contractLifeCycleEnum == "Executing"){
                    var start = arr[i].startDate;
                    var time = today-start;
                    rented = rented + time;
                }else {
                    var leasedtime = arr[i].retreatDate-arr[i].startDate;
                    rented = rented + leasedtime;
                }
            }
            var setRentalrates = parseInt((rented/alltime).toFixed(2)*100);
            this.$(".rental-rates").html(setRentalrates+'%');
        },

    });

    exports.TimelineView = TimelineView;

});