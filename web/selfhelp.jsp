<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!-- saved from url=(0021)http://47.98.195.111/ -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=7,9,10,11">
    <meta name="renderer" content="webkit">
    <title>CSDN 积分自助下载-激活码兑换</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
    <meta content="max-age=30" http-equiv="Cache-Control">
    <meta http-equiv="X-UA-Compatible" content="IE=7,8,9,10,11">
    <meta content="CSDN下载,CSDN积分,CSDN下载器,分享,离线下载" name="keywords">
    <meta content="" name="description">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.v3.1.1_c7e4b08.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/global_fb72937.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/activationCode_097410c.css">
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/tiqu.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js" type="text/javascript"></script>
    <script>
        function ReFresh() {
            var urldomain = "${pageContext.request.contextPath}";
            document.getElementById("authImg").src = urldomain + "/csdn/valicode.do?rn=" + Math.random() * 1000;
        }
        function checkEmpty() {//
            var cdkeyTxt = document.getElementById('cdkey');
            var mailTxt = document.getElementById('mail');
            var downTxt = document.getElementById('downurl');
            var valiTxt = document.getElementById('valicode');

            var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");

            // 检查通行证
            if (cdkeyTxt.value == "") {
                document.getElementById("cdkey-error").style.visibility = "visible";
//                document.getElementById("cdkey-error").innerHTML = "该项为必填";
                return false;
            } else {
                document.getElementById("cdkey-error").style.visibility = "hidden";
            }

            if (mailTxt.value == "") {
                document.getElementById("mail-error").style.visibility = "visible";
                return false;
            } else if (!reg.test(mailTxt.value)) {
                document.getElementById("mail-error").innerHTML = "请输入正确邮件地址";
                document.getElementById("mail-error").style.visibility = "visible";
                return false;
            } else {
                document.getElementById("mail-error").style.visibility = "hidden";
            }


            // 检查下载链接
            if (downTxt.value == "") {
                document.getElementById("down-error").style.visibility = "visible";
                return false;
            } else {
                document.getElementById("down-error").style.visibility = "hidden";
            }

            var link = downTxt.value
            if (link.indexOf('https://download.csdn.net/') == -1) {
                document.getElementById("down-error").innerHTML = "地址错误 栗子: https://download.csdn.net/download/zhaosi/1234567";
                document.getElementById("down-error").style.visibility = "visible";
                return false;
            } else if((link.split('/').length)!=6){
                document.getElementById("down-error").innerHTML = "地址错误 栗子: https://download.csdn.net/download/zhaosi/1234567";
                document.getElementById("down-error").style.visibility = "visible";
                return false;
            }else {
                document.getElementById("down-error").style.visibility = "hidden";
            }


            // 检查验证码
//            if (valiTxt.value == "") {
//                return false;
//            }
            document.getElementById("btn").innerHTML="获取资源中。。";
            document.getElementById("btn").isDisabled = true;
            document.getElementById("btn").style="background-color:#999"
//            document.getElementsByClassName("activation-zone-but").style.backgroundColor='#999'


        }
    </script>
</head>

<body class="buyCenter">
<div class="main-content">
    <div class="container">

        <div class="login-tip">
            <p class="hidden act-suc"><em class="success-icon"></em>恭喜，激活成功！</p>
        </div>
        <div class="hidden success-info">
            <div node-type="suc-content" isvip="" issvip=""></div>
            <em class="underscore"></em>
            <p>接下来您可以:<span class="user-todo" node-type="continue">继续激活</span>|<span class="user-todo"
                                                                                     node-type="record">查看激活记录</span>｜<span
                    class="user-todo" node-type="checkVip">查看会员特权</span>
            </p>
        </div>
        <form method="POST" action="${pageContext.request.contextPath}/csdn/selfhelp.do" id="myform">
            <div class="feature-zone"> <!--  feature-zone start -->
                <span class="content-tip">&nbsp;&nbsp;通行证和资源地址务必正确无误</span>

                <div class="code-zone">
                    <span>通&nbsp;&nbsp;行&nbsp;&nbsp;证：</span>
                    <input type="text" style="width:150px" name="cdkey" id="cdkey" value="${cdkey}">
                    <span class="hidden err" id="result" style="visibility: visible; display:inline">${cdkey_message}</span>
                </div>
                <span class="hidden err" id="cdkey-error" >该项为必填项</span>

                <div class="code-zone">
                    <span>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</span>
                    <input type="text" style="width:180px" name="mail" id="mail" value="${mail}">
                </div>
                <span class="hidden err" id="mail-error">请输入正确的邮件地址</span>

                <div class="code-zone">
                    <span>资源地址：</span>
                    <input type="text" maxlength="350" style="width:480px" name="downurl" id="downurl" value="${downurl}">
                </div>
                <span class="hidden err" id="down-error">请输入CSDN资源下载地址</span>

                <%--<span class="content-tip2">请输入验证码，字母不区分大小写</span>--%>
                <div class="code-zone">
                    <%--<span>验&nbsp;&nbsp;证&nbsp;&nbsp;码：</span>--%>
                    <%--<input type="text" maxlength="4" node-type="validate-code" required="required" id="valicode" name="valicode">--%>
                    <%--<img class="validateImg" id="authImg" alt="验证码" title="验证码" src="${pageContext.request.contextPath}/csdn/valicode.do"--%>
                         <%--onclick="javascript:ReFresh();"--%>
                         <%--align="absmiddle" style="cursor: pointer" alt="看不清楚，换一张" title="看不清楚，换一张">--%>
                    <%--<span class="changeImg" node-type="changeOther" onclick="javascript:ReFresh();">换一张</span>--%>
                </div>
                <%--<span class="hidden err" id="validate-err" style="visibility: visible">${valicode_message}</span>--%>
            </div> <!--  feature-zone end -->

            <div class="activation-zone hidden"></div>
            <div class="activation-zone-but">
                    <button class="right-now" onclick="return checkEmpty()" id="btn">立即下载</button>
                    <%--<span class="hidden err" id="result" style="visibility: visible; display:inline">${cdkey}</span>--%>
            </div>
        </form>
    </div>
</div>

<div style="position:absolute;top:0px;right:20px"><a href="#"><img src="${pageContext.request.contextPath}/img/attention.png"></a></div>
<div class="use-warning">
    <p class="use-tips">通行证使用说明:</p>
    <ul>
        <li>1. 通行证 和 下载链接 务必正确；</li>
        <li>2. 程序可实现直接在线下载，全天无人值守；</li>
        <li>3. 一个通行证下载一次 如有需要下载多个资源，请分多次兑换；</li>
        <li>4. 高峰期下载可能会略有延迟 请耐心等待；</li>
        <li>5. 如有疑问，请联系客服。</li>
    </ul>
</div>

<em class="long underscore"></em>
<div class="vip-footer">
    <div>©2018&nbsp; 本站仅供方便各界IT人士学习参考，切勿用于非法用途，否则后果自负。</div>
</div>


</body>
</html>