<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.net.URLEncoder" %>
<html><head>
    <meta name="keywords" content="">
    <meta name="referrer" content="never">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>

    <title>成功</title>

</head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<body>
<div class="box">
    <div class="title">
        获取资源成功，已开始自动下载<br>
        若没有开始下载，请稍等片刻后去邮箱查看<br>
        我们已经向你的邮箱发送了一个备份

    </div>
    <a href="<%
            String test=(String)request.getAttribute("resourceUrl");
            out.print(test);

        %>" id="resourcelink"  referrerPolicy="no-referrer" />
    </a>


</div>

</div>
<script type="text/javascript">

    $(function(){
        var link = document.getElementById("resourcelink").getAttribute("href");
        var arg ='\u003cscript\u003etop.location.replace("'+link+'")\u003c/script\u003e';
        var iframe = document.createElement('iframe');
        iframe.src='javascript:window.name;';
        iframe.name=arg;
        document.body.appendChild(iframe);
        document.body.appendChild(document.createElement('iframe')).src='javascript:"<script>top.location.replace(\''+link+'\')<\/script>"';
    });

</script>

</body>
</html>