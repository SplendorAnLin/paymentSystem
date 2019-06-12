var fresh = (function ($) {
    return {
        initMiniRefreshs: function (index, url, createStartTime, lastday, callbacks) {
            var page = 1;
            var page_size = 10;
            var page_total = 10;

            var changeData = function ($page, $createStartTime, $lastday) {
                page = $page;
                createStartTime = $createStartTime;
                lastday = $lastday;
            }

            var miniRefreshArr = [];

            miniRefreshArr[index] = new MiniRefresh({
                container: '#minirefresh' + index,
                down: {
                    isAuto: false,
                    isAutoResetUpLoading:false,
                    callback: function () {
                        page = 1;
                        // 下拉事件
                        setTimeout(function () {
                            // 每次下拉刷新后，上拉的状态会被自动重置
                            $.ajax({
                                type: "post",
                                url: url,
                                data: {
                                    "currentPage": page,
                                    "pageSize": page_size,
                                    "startCompleteDate":createStartTime,  //时间
                                    "customerNo": $("#ownerId").val(),
                                    "userName": $("#userName").val(),
                                    "endCompleteDate": createStartTime

                                },
                                success: function (da) {
                                    if (da.responseCode == "000000") {
                                        page_size = 10;
                                        page = page;
                                        $("#listdata").html("");
                                        $("#amountnum").html(returnFloat(da.responseData.balance));
                                        if (da.responseData.request.length > 0) {
                                            for (var i = 0; i < da.responseData.request.length; i++) {
                                                callbacks(index, da, i);
                                            }
                                        }
                                        page++;
                                        miniRefreshArr[index].endDownLoading(da.responseData.request.length < 10 ? true : false);
                                        miniRefreshArr[index].endUpLoading(da.responseData.request.length < 10 ? true : false);
                                    } else {
                                        miniRefreshArr[index].endDownLoading(true);
                                        miniRefreshArr[index].endUpLoading(true);
                                        $("#responseMsg").html(da.responseMsg)
                                        $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");

                                    }
                                },

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
                                    "currentPage": page,
                                    "pageSize": page_size,
                                    "startCompleteDate": createStartTime,  //时间
                                    "customerNo": $("#ownerId").val(),
                                    "userName": $("#userName").val(),
                                    "endCompleteDate":  createStartTime,
                                },
                                success: function (da) {
                                    if (da.responseCode == "000000") {
                                        page_size = 10;
                                        page = page;
                                        $("#amountnum").html(returnFloat(da.responseData.balance));
                                        if (da.responseData.request.length > 0) {
                                            for (var i = 0; i < da.responseData.request.length; i++) {
                                                callbacks(index, da, i);
                                            }
                                        }
                                        //
                                        page++;
                                        miniRefreshArr[index].endUpLoading(da.responseData.request.length < 10 ? true : false);
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