$.ajax({
    type: "post",
    url: "weeklySales",
    data: {
        "ownerId": $("#ownerId").val(),
        "userName": $("#userName").val()
    },

    beforeSend:function(){$("#loadingToast").fadeIn("fast")},
    success: function (data) {
        if (data) {
            if (data.responseCode == "NULL" || data.responseCode == "FALSE" || data.responseCode == "ERROR") {
                $("#responseMsg").html(data.responseMsg)
                $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
                return false;
            }
            else if (data.responseCode == "AFRESH") {
                $("#responseMsg").html(data.responseMsg)
                $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
                window.location.href = "toAppLogin";
                return false;
            }
            var money = new Array();
            var time = new Array();
            var moneyb = new Array();
            var methodb = new Array();
            var jsons = [];
            var color = [];
            $("#moneyall,#datashow").html(data.responseData.sumWeek.amount);
            $("#moneyNum").html(data.responseData.sumWeek.counts);

            if (data.responseData.summaryGraph) {
                if (data.responseData.summaryGraph.length > 0) {
                    if (data.responseData.summaryGraph.length>0 && data.responseData.summaryGraph ){
                    for (var i = 0; i < data.responseData.summaryGraph.length; i++) {
                        money[i] = data.responseData.summaryGraph[i].amount;
                        var timestr = data.responseData.summaryGraph[i].times.substring(4);
                        var timeS = timestr.split("").join(",").split(",");
                        time[i] = timeS[0] + timeS[1] + "-" + timeS[2] + timeS[3];
                    }
                        var max_data = Math.max.apply(null, money);
                        tongji(money, time,max_data);
                    }
                    else{
                        $("#main").html("暂无数据！");
                    }
                    if (data.responseData.week.length>0 && data.responseData.week ){
                        for (var i = 0; i < data.responseData.week.length; i++) {
                            moneyb[i] = data.responseData.week[i].amount;
                            methodb[i] = data.responseData.week[i].paytype_CN;
                            color[i] = data.responseData.week[i].paytype_COLOR;
                            jsons.push({
                                value: moneyb[i],
                                name: methodb[i]
                            });
                        }
                        pie_chart(moneyb, methodb, color, jsons);
                    }
                    else {
                        $("#pie_chart").html("暂无数据");
                    }
                } else {
                }
            }
        }
    },
    error:function(){
        console.log("ajax error")
    },
    complete:function(){$("#loadingToast").fadeOut("fast")}
});

function tongji(money, time,max_data) {
    var myChart = echarts.init(document.getElementById('main'));
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a}：{b} <br/>总收益： {c}元 ",
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                color: "#0070e3",
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['销量']
        },
        xAxis: {
            data: time,
            splitLine: {
                show: false
            },
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#a5a5a5'
                }
            },
            axisLine: {
                lineStyle: {
                    color: '#a5a5a5'
                }
            },
            axisTick: {
                show: false
            }
        },
        yAxis: {
            splitLine: {
                show: false
            },
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#a5a5a5'
                }
            },
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#a5a5a5',
                    show: false
                }
            },
            axisTick: {
                show: true
            },
            max:max_data+10,
        },
        series: [{
            name: '时间',
            type: 'bar',
            data: money,
            itemStyle: {
                normal: {
                    borderWidth :2,
                    borderColor :'Rgba(30,152,257,0.7)',
                    label: {
                        show: false
                    },
                    labelLine: {
                        show: false
                    },

                    color: "Rgba(30,152,257,0.7)",
                    barBorderRadius: [6, 6, 0, 0],
                    lineStyle: {
                        color: '#f5bf58',
                        width: 4
                    },
                    textStyle: {
                        color: '#a5a5a5'
                    }
                }
            },
            barWidth: 13,

        }]

    };

    myChart.setOption(option);

}

function pie_chart(money, method, color, jsons) {
    var myChart = echarts.init(document.getElementById('pie_chart'));
    option = {
        title: {
            text: '',
            subtext: '',
            left: '15',
            color: '#5a5a5a',
            //字体风格,'normal','italic','oblique'
            fontStyle: 'italic',
            top: "10",
            //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
            fontWeight: 'lighter',
            //字体系列
            fontFamily: 'pingfengSC',
            //字体大小
            fontSize: 15
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a}<br/>{b} :总收益 {c}元 ({d}%)"
        },
        legend: {
            orient: "vertical",
            data: method,
            top: "15%",
            right: 5
        },
        toolbox: {
            show: false,
            feature: {
                mark: {
                    show: false
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            width: '20%',
                            funnelAlign: 'right',

                        }
                    }
                },
                restore: {
                    show: true
                },
                saveAsImage: {
                    show: true
                }
            }
        },
        calculable: true,

        series: [{
            type: 'pie',
            name: "来源",
            radius: ['15%', '40%'],
            //			center: ['50%', '50%'],
            //			selectedMode: 'single',
            data: jsons,
            itemStyle: {
                normal: {
                    label: {
                        show: true
                    },
                    labelLine: {
                        show: true
                    }
                },
                emphasis: {
                    shadowBlur: 4,
                    shadowOffsetX: 4,
                    shadowColor: 'rgba(0, 0, 0, 0.2)',
                    label: {
                        show: false,
                        position: 'right',
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                }
            },
        }],
        color: color
    };

    myChart.setOption(option);
}