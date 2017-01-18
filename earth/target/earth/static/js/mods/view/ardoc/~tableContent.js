define(function (require, exports, module) {
    var util = require('scaffold/util');
    var PageControl=require('component/pageControl');
    
    var $ = jQuery;
    var extractParamFromDifferentDom = util.extractValueFromDifferentDom;

    // backbone不能合并父级的events和initialize，默认只会覆盖，所以需要自己调用
    var TableContentView = Backbone.View.extend({
        template: _.template($('#tableFieldTmpl').html() || ''),
        el: '.content',
        events: {
            'click #lookup': '_queryByParams',
            'click .prev': 'getPrevListData',
            'click .next': 'getNextListData'
        },
        initialize: function () {
            this.lookupParamsEl = this.$(".lookup-params");
            this.tableListEl = this.$('.data-list'); 
            this.queryAction = this.tableListEl.data('action');

            this.initPageControl();
        },
        initPageControl: function () {
            var options = {
                url: this.queryAction,
                pageIndex: 1,
                pageRecordNum: 12
            };
            this.pageControl = new PageControl(options);
            this.pageControl.on('next:pagecontrol prev:pagecontrol redirect:pagecontrol', _.bind(this.refreshTableDataList, this));
        },
        setPageControlOptions: function (options) {
            this.pageControl.setOptions(options);
        },
        getFirstPageData: function () {
            this.pageControl.redirect(1, null);
        },
        collectParams: function () {
            var paramsItems=this.lookupParamsEl.find('.item'),
                inputEl,
                resultParams={};

            for(var i=0, len=paramsItems.length; i<len; i++) {
                inputEl=$(paramsItems[i]).find('.form-control');
                $.extend(resultParams, extractParamFromDifferentDom(inputEl));
            }

            return resultParams;
        },
        getPrevListData: function (e) {
            e.preventDefault();
            var params=this.collectParams();
            this.pageControl.prev(params);
        },
        getNextListData: function (e) {
            e.preventDefault();
            var params=this.collectParams();
            this.pageControl.next(params);
        },
        _queryByParams: function () {
            var params=this.collectParams();
            this.queryByParams(params);
        },
        refreshTableDataList: function (data) {
            // 可由子视图继承
            if(!this.template) return;
            var htm;
            if(data.length<1) {
                htm = '<tr><td style="text-align: center;" colspan="100">没有更多数据</td></tr>';
            }else{
                htm = this.template({list: data});
            }
            this.tableListEl.find('tbody').html(htm);
        },
        queryByParams: function (params) {
            // 可由子视图继承
            var params = this.collectParams();
            this.pageControl.redirect(1, params);
        },
        refresh: function () {
            var params=this.collectParams();
            this.pageControl.refresh(params);
        }
    });

    // http://danhough.com/blog/backbone-view-inheritance/
    // 默认的extend后覆盖父亲的events，这里进行合并
    // 事件可以一级一级继承下来
    TableContentView.extend=function (protoProps) {
        var view = Backbone.View.extend.apply(this, arguments);
        view.prototype.events=_.extend({}, this.prototype.events, protoProps.events);
        return view;
    };

    module.exports = TableContentView;
});