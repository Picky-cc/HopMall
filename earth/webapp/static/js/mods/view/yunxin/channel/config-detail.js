define(function(require, exports, module) {
    var Highcharts = require('scaffold/highcharts');
    var TableContentView = require('baseView/tableContent');

    var root = global_config.root;

    Highcharts.setOptions({
        colors: ['#69c2e2', '#ed4407'],
        credits: {
            enabled: false // 禁用版权信息
        },
        chart: {
            marginTop: 50
        },
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

    var ConfigDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'change .filter-channel': 'getChannelData',
            'change .filter-turnover': 'getTurnoverData'
        },
        actions: {
            searchTradingVolume: root + '/paymentchannel/config/transactionDetail/searchTradingVolume'
        },
        initialize: function(paymentChannelUuid) {
            this.paymentChannelUuid = paymentChannelUuid;

            this.orderListView = new TableContentView({el: '.table-layout-detail'});

            this.turnoverChart = new Highcharts.Chart({
                chart: {
                    renderTo: this.$('#drawContainer1').get(0)
                },
                series: []
            });

            this.channelChart = new Highcharts.Chart({
                chart: {
                    renderTo: this.$('#drawContainer2').get(0)
                },
                series: []
            });

            this.on('response:channel', function(data) {
                if (this.channelChart.series == 0) {
                    this.channelChart.addSeries({
                        name: '付款通道费',
                        data: data[0]
                    });
                    this.channelChart.addSeries({
                        name: '付款交易额',
                        data: data[1]
                    });
                } else {
                    this.channelChart.series[0].setData(data[0]);
                    this.channelChart.series[1].setData(data[1]);
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

            $(document).on('toggle.aside', function(e) {
                this.turnoverChart.reflow();
                this.channelChart.reflow();
            }.bind(this));

            this.getChannelData();
            this.getTurnoverData();
        },
        getChannelData: function() {
            var self = this;
            var type = this.$('.filter-channel').val();

            setTimeout(function() {
                var data = [];

                self.trigger('response:channel', data);
            }, 500);
        },
        getTurnoverData: function() {
            var self = this;
            var opt = {
                url: this.actions.searchTradingVolume,
                dataType: 'json',
                data: {
                    time: this.$('.filter-turnover').val(),
                    paymentChannelUuid: this.paymentChannelUuid
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

    module.exports = ConfigDetailView;

});