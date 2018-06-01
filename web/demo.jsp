<%@ page import="com.utils.PropertiesProcessor" %>
<html>
<head>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        充值
    </title>
    <style>


        .input_text {
            padding: 10px 10px;
            border: 1px solid #d5d9da;
            border-radius: 5px;
            box-shadow: 0 0 5px #e8e9eb inset;
            width: 100px;
            font-size: 1em;
            outline: 0;
        }

        .input_text:focus {
            border: 1px solid #b9d4e9;
            border-top-color: #b6d5ea;
            border-bottom-color: #b8d4ea;
            box-shadow: 0 0 5px #b9d4e9;
        }

        .button {
            color: #666;
            background-color: #EEE;
            border-color: #EEE;
            font-weight: 300;
            font-size: 16px;
            font-family: "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
            text-decoration: none;
            text-align: center;
            line-height: 40px;
            height: 40px;
            padding: 0 40px;
            margin: 0;
            display: inline-block;
            appearance: none;
            cursor: pointer;
            border: none;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            -webkit-transition-property: all;
            transition-property: all;
            -webkit-transition-duration: .3s;
            transition-duration: .3s;
        }

        .button-primary {
            background-color: #1B9AF7;
            border-color: #1B9AF7;
            color: #FFF;
        }

        .button-primary:visited:visited {
            color: #FFF;
        }

        .button-primary:hover, .button-primary:focus {
            background-color: #4cb0f9;
            border-color: #4cb0f9;
            color: #FFF;
        }

        .button-pill {
            border-radius: 200px;
        }

        .alipay_select {
            width: 120px;
            background: url(http://codepay.fateqq.com/img/alipay.jpg) no-repeat 14px 0;
        }

        .qqpay_select {
            width: 130px;
            background: url(http://codepay.fateqq.com/img/qqpay.jpg) no-repeat 14px 0;
        }

        .wechat_select {
            width: 120px;
            background: url(http://codepay.fateqq.com/img/weixin.jpg) no-repeat 16px 0;

        }

        .type_select {
            float: left;
            padding: 1px;
            margin: 5px 5px 0px 0px;

            border: 1px solid #80C5FF;
            color: #0061F3;
            font-size: 13px;
            padding: 5px;
            margin-left: 0px;
            float: left;
            padding-left: 2px;
            padding-right: 20px;
            padding-top: 14px;
            height: 20px;
        }

        p{
            color: RED;
            float: left;
            font-weight: bold;

        }
        .top_line{
            text-align: center;
            color: green;
            float: left;
            font-weight: bold;
        }
    </style>
</head>
<body>
<form name="form1" id="form1" method="get" action="codepay.jsp">
    <div>
        <table width="550" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#ffffff">
            <tbody>
            <tr>
                <td colspan="2">
                    <div align="center"><strong>百度文库下载</strong></div>
                </td>
            </tr>

            <%
                String maintenance = PropertiesProcessor.getProperty("maintenance");
                if (maintenance.equals("true")) {
                    response.sendRedirect("/maintenance.jsp");
                }
            %>

            <!--          注释以下代码 可禁止自己输入价格-->
               <%--<tr>--%>
            <%--<td>--%>
            <%--<div align="right">金额：</div>--%>
            <%--</td>--%>

            <%--<td>--%>

            <%--<input name="price" id="price" type="text" value=""   class="input_text"> 元</td>--%>
            <%--</tr>--%>
            <!--              注释结束位置            -->
            <tr>
                <td>
                    <div align="right">文库链接：</div>
                </td>

                <td><input name="param" id="param" type="text" value="" class="input_text"
                           style=" width:200px;"></td>

            </tr>
            <tr>
                <td>
                    <div align="right">支付：</div>
                </td>
                <td><label>
                    <div class="type_select alipay_select">
                        <input type="radio" name="type" value="1" checked="checked">
                    </div>
                </label>
                    <%--<label>--%>
                    <%--<div class="type_select wechat_select">--%>
                    <%--<input type="radio" name="type" value="3">--%>
                    <%--</div>--%>
                    <%--</label>--%>
                    <%--<label>--%>
                    <%--<div class="qqpay_select type_select">--%>
                    <%--<input type="radio" name="type" value="2">--%>
                    <%--</div>--%>
                    <%--</label>--%>
                </td>
            </tr>

            <tr>
                <td>
                    <div align="center"></div>
                </td>
                <td><label>
                    <input type="submit" id="Submit" class="button button-pill button-primary"
                           value="完成支付后下载">

                </label></td>
                <td></td>

            </tr>

            <tr>
                <td></td>
                <td></td>
            </tr>
            <tr>

                <td colspan="2" align="left">
                    <p class="top_line">&nbsp;&nbsp;&nbsp;本站支持 PDF, PPT, DOC 等文档格式下载，但是不支持付费文档</p>
                    <p> 完成支付后系统自动下载，若无法下载请联系客服退款 QQ 2434861414</p>
                    <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;因个人操作失误造成的损失，恕不理赔</p>
                </td>

            </tr>


            <%--<tr>--%>
            <%--<td>--%>
            <%--<p align="center">--%>
            <%--完成支付后，系统自动下载，<br>若无法下载，请联系客服退款--%>

            <%--</p>--%>
            <%--</td>--%>
            <%--</tr>--%>
            </tbody>
        </table>
    </div>
</form>

<%--<form id="login" name="login" method ="post"  action="https://openlanguage.com/accounts/login">--%>

    <%--<p>用户名：<input id="txtUserName" name="email" type="text" /></p>  <!--用户名文本框-->--%>
    <%--<p>密　码：<input id="txtPWD" name="password" type="password" /></p>                     <!--密码文本框-->--%>
    <%--<p><input id="subLogin"  name ="subLogin" type="submit" value="提交" /></p><!--提交按钮-->--%>

<%--</form>--%>
<script src="http://codepay.fateqq.com/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
    var type = document.getElementsByName('type');
    var price = document.getElementById('price');
    var money = document.getElementById('money');
    var FormSubmit = document.getElementById('Submit');
    for (var i = 0; i < type.length; i++) {
        type[i].onclick = function () {
            switch (parseInt(this.value)) {
                case 1:
                    FormSubmit.value = '支付宝支付';
                    break;
                case 2:
                    FormSubmit.value = 'QQ钱包支付';
                    break;
                case 3:
                    FormSubmit.value = '微信支付';
                    break;
                default:
                    FormSubmit.value = '支付宝支付';
            }
        }
    }
    $(".w-pay-money").click(function () {
        $(".w-pay-money").removeClass('w-pay-money-selected');
        $(this).addClass('w-pay-money-selected');
        price.value = $(this).attr('data');
        money.value = $(this).attr('data');
    });

    FormSubmit.onclick = function (event) {
//        alert("preventDefault!");
        var event = event || window.event;
        event.preventDefault(); // 兼容标准浏览器
        var link = document.getElementsByName("param")[0];
        if (trim(link.value).length == 0) {
            alert("请输入文档链接");
            return false;
        } else {
            window.event.returnValue = true;
        }
//        window.event.returnValue = false; // 兼容IE6~8
    };

    function trim(str) {

        return str.replace(/(^\s*)|(\s*$)/g, "");

    }
</script>
</body>
</html>
