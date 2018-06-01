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
    <div class="title">获取文档成功</div>
    <a href=" <%
            String test=(String)request.getAttribute("resourceUrl");
            out.print(test);

        %>" id="resourcelink"  referrerPolicy="no-referrer" /> <button class="new-btn-login" id="subBtn" type="button">若没有自动下载，请点击这里</button>
    </a>


    </div>
</div>
<script type="text/javascript">
    $(function(){
//        window.location.href = document.getElementById("resourcelink").getAttribute("href");
//        window.navigate(document.getElementById("resourcelink").getAttribute("href"));

        $("#subBtn").trigger("click");
    });
</script>

</body>
</html>