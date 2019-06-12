function loginOut() {
    $.ajax({
        type: "post",
        url: "loginOut",
        data: {
            "userName": $("#userName").val()
        },
        success: function (data) {
            window.location.href = "toAppLogin";
        },
        error:function(){
            window.location.href = "toAppLogin";
        }
    })
}