
function Validate() {
    if ($("#TxtTid").val() == "") {
        alert("请输入资源链接");
        $("#TxtTid").focus();
        return;
    }
    if ($("#TxtUserNick").val() == "") {
        alert("请输入提取密钥");
        $("#TxtUserNick").focus();
        return;
    }
    if ($("#authcode").val() == "") {
        alert("请输入验证码");
        $("#authcode").focus();
        return;
    }
    if ($("#authcode").val().toLowerCase() != $.cookie("ValidateCode")) {
        alert('验证码错误或已经过期');
        return;
    }
    else {
        gettypename();
    }
}

function gettypename() {

    var img = $("#progressImgage"); //进度条
    var mask = $("#maskOfProgressImage");
    $("#p_p").empty();
    var d = {
        "TxtTid": $('#TxtTid').val(), "TxtUserNick": $('#TxtUserNick').val(), "select": $('#select').val(), "Txtseller": $('#Txtseller').val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        contentType: "application/json;charset=utf-8", // 这句可不要忘了。
        url: "tiqu.aspx/sendtiqu",
        dataType: "json",
        data: JSON.stringify(d),  //这里需将d转换为字符串

        beforeSend: function (xhr) {
            img.show().css({
                "position": "fixed",
                "top": "40%",
                "left": "45%",
                "margin-top": function () { return -1 * img.height() / 2; },
                "margin-left": function () { return -1 * img.width() / 2; }
            });
            mask.show().css("opacity", "0.1");
        },
        success: function (result) {

            //获取返回实体类的值
            if (result.d != null) {
                $("#divContent").html(result.d);
            } else {
                $("#divContent").html("");
                $("#divContent").empty();
                alert("提取失败!没有找到发货内容，请联系卖家！");
              
            }

        },
        error: function (error) {
            alert(error.responseText);
        },
        complete: function (xhr) {
            img.hide();
            mask.hide();

        }
    });

}
function showandhide(v) {
    if (v == '1') {
        document.getElementById(2).style.display = 'none';
    }
    else {
        document.getElementById(2).style.display = '';
    }
}