var fresh = (function ($) {
    return {
        initMiniRefreshs: function (index, url, createStartTime, callbacks) {
            var page = 1;
            var page_size = 10;
            var page_total = 10;

            var changeData = function ($page, $createStartTime) {
                page = $page;
                createStartTime = $createStartTime;
            }

            var miniRefreshArr = [];

            miniRefreshArr[index] = new MiniRefresh({
                container: '#minirefresh' + index,
                down: {
                  //  isAutoResetUpLoading:false,
                    callback: function () {
                        page = 1;
                        // 下拉事件
                        setTimeout(function () {
                            // 每次下拉刷新后，上拉的状态会被自动重置
                            $.ajax({
                                type: "post",
                                url: url,
                                data: {
                                    "pageCode": page,
                                    "showCount": page_size,
                                    "createStartTime": createStartTime,  //时间
                                    "customerNo": $("#ownerId").val(),
                                    "userName": $("#userName").val(),
                                    "createEndTime": createStartTime
                                },
                                success: function (da) {
                                    if (da.responseCode == "000000") {
                                        page_size = 10;
                                        page = page;
                                        $("#listdata").html("");
                                        if (da.responseData.resmap.length > 0) {
                                            for (var i = 0; i < da.responseData.resmap.length; i++) {
                                                callbacks(index, da, i);
                                            }
                                        }
                                            miniRefreshArr[index].endDownLoading(da.responseData.resmap.length < 10 ? true : false);
                                            miniRefreshArr[index].endUpLoading(da.responseData.resmap.length < 10 ? true : false);
                                            page++;
                                    } else {
                                        miniRefreshArr[index].endDownLoading(true);
                                        miniRefreshArr[index].endUpLoading(true);
                                        $("#responseMsg").html(da.responseMsg)
                                        $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
                                    }
                                }
                            });
                        }, 1000);

                    },
                },
                up: {
                    callback: function () {
                        // 上拉事件
                        setTimeout(function () {
                            $.ajax({
                                type: "post",
                                url: url,
                                data: {
                                    "pageCode": page,
                                    "showCount": page_size,
                                    "createStartTime": createStartTime,  //时间
                                    "customerNo": $("#ownerId").val(),
                                    "userName": $("#userName").val(),
                                    "createEndTime": createStartTime
                                },
                                success: function (da) {
                                    if (da.responseCode == "000000") {
                                        page_size = 10;
                                        page = page;
                                        if (da.responseData.resmap.length > 0) {
                                            for (var i = 0; i < da.responseData.resmap.length; i++) {
                                                callbacks(index, da, i);
                                            }
                                        }
                                        //
                                            miniRefreshArr[index].endUpLoading(da.responseData.resmap.length < 10 ? true : false);
                                            page++;
                                    } else {
                                        $("#responseMsg").html(da.responseMsg)
                                        $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
                                    }
                                }
                            });
                        }, 1000);
                    }
                }
            });
            return {
                miniRefreshArr: miniRefreshArr,
                changeData: changeData
            };
        }
    }
})(jQuery);