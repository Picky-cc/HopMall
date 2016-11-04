define(function(require, exports, module) {
    var Highcharts = require('scaffold/highcharts');

    var root = global_config.root;

    Highcharts.setOptions({
        credits: {
            enabled: false // 禁用版权信息
        },
        legend: {
            align: 'right',
            verticalAlign: 'top',
            y: -10
        },
        tooltip: {
            enabled: false
        }
    });

    var EfficentAnalysisView = Backbone.View.extend({
        el: '.content',
        events: {
            'change .filter-channel': 'getChannelData',
            'change .filter-turnover': 'getTurnoverData'
        },
        actions: {
            tradingVolumeTrend: root + '/paymentchannel/efficentanalysis/tradingVolumeTrend',
            statistics: root + '/paymentchannel/efficentanalysis/statistics'
        },
        initialize: function() {
            $(document).on('toggle.aside', function(e) {
                this.turnoverChart.reflow();
                this.channelChart.reflow();
            }.bind(this));

            this.initTurnoverChart();
            this.initChannelChart();

            this.getChannelData();
            this.getTurnoverData();
        },
        initTurnoverChart: function() {
            this.turnoverChart = new Highcharts.Chart({
                chart: {
                    renderTo: this.$('.turnover .draw').get(0),
                    marginTop: 50
                },
                colors: ['#69c2e2', '#ed4407'],
                title: {
                    text: null
                },
                plotOptions: {
                    line: {
                        marker: {
                            symbol: 'circle'
                        }
                    }
                },
                xAxis: {
                    lineColor: '#dedede',
                    tickLength: 0,
                    labels: {
                        enabled: false
                    }
                },
                yAxis: {
                    lineWidth: 1,
                    tickInterval: 10,
                    lineColor: '#dedede',
                    gridLineColor: '#cdcdcd',
                    gridLineDashStyle: 'Dash',
                    title: {
                        text: null // 隐藏坐标轴标题
                    }
                }
            });

            this.on('response:turnover', function(data) {
                if (this.turnoverChart.series == 0) {
                    this.turnoverChart.addSeries({
                        name: '付款交易额',
                        data: data.creditAmountList
                    });
                    this.turnoverChart.addSeries({
                        name: '收款交易额',
                        data: data.debitAmountList
                    });
                } else {
                    this.turnoverChart.series[0].setData(data.creditAmountList);
                    this.turnoverChart.series[1].setData(data.debitAmountList);
                }
            });
        },
        initChannelChart: function() {
            var charts = this.channelChart = [];
            var container = this.$('.channel .bd');
            var toParse = function(obj) {
                var arr = [];

                if ($.isEmptyObject(obj)) {
                    obj['SUCCESS'] = 0;
                    obj['FAIL'] = 0;
                    obj['TIME_OUT'] = 0;
                    obj['DOING'] = 0;
                    obj['CREATE'] = 1;
                }

                arr.push(['成功', obj['SUCCESS']]);
                arr.push(['失败', obj['FAIL']]);
                arr.push(['异常', obj['TIME_OUT']]);
                arr.push(['处理中', obj['DOING']]);
                arr.push(['等待处理', obj['CREATE']]);
                return arr;
            };
            var defOpts = {
                chart: {
                    type: 'pie'
                },
                colors: ['#90ed7d', '#f04f76', '#f7a35c', '#7cb5ec', '#434348'],
                title: {
                    text: '',
                    verticalAlign: 'bottom',
                    y: -20,
                    style: {
                        fontSize: '12px'
                    }
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        size: 150,
                        dataLabels: {
                            enabled: false
                        }
                    }
                },
                series: []
            };

            this.channelChart.reflow = function() {
                for (var i = 0; i < this.length; i++) {
                    this[i].reflow();
                }
            };

            this.$('.channel').on('click', '[data-legend-type]', function(e) {
                var legendType = $(e.currentTarget).data('legend-type');
                var visible;

                $(charts).each(function(j, f) {
                    $(this.series[0].data).each(function(k, z) {
                        if (z.name == legendType) {
                            visible = !z.visible;
                            z.setVisible(visible);
                        }
                    });
                });

                visible ? $(e.currentTarget).removeClass('invisible') : $(e.currentTarget).addClass('invisible');
            });

            this.on('response:channel', function(data) {
                if (this.channelChart.length == 0) {
                    for (var i = 0; i < data.length; i++) {
                        var chart;
                        var dom = $('<div class="draw" />');
                        var opt = $.extend(true, {}, defOpts, {
                            chart: {
                                renderTo: dom.get(0)
                            }
                        });

                        container.append(dom);
                        chart = new Highcharts.Chart(opt);
                        this.channelChart.push(chart);
                    }
                }

                for (var i = 0; i < data.length; i++) {
                    var chart = this.channelChart[i];
                    var item = data[i];
                    var arrData = toParse(item.data);
                    if (chart.series.length == 0) {
                        chart.addSeries({data: arrData});
                    } else {
                        chart.series[0].setData(arrData);
                    }
                    chart.setTitle({text: item.paymentChannelName});
                }
            });
        },
        getChannelData: function() {
            var self = this;
            var opt = {
                url: this.actions.statistics,
                dataType: 'json',
                data: {
                    type: this.$('.filter-channel').val()
                }
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    self.trigger('response:channel', resp.data.list);
                }
            };

            $.ajax(opt);
        },
        getTurnoverData: function() {
            var self = this;
            var opt = {
                url: this.actions.tradingVolumeTrend,
                dataType: 'json',
                data: {
                    time: this.$('.filter-turnover').val()
                }
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    self.trigger('response:turnover', resp.data);
                }
            };

            $.ajax(opt);
        }
    });

    module.exports = EfficentAnalysisView;

});