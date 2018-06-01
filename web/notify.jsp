<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.math.*" %>
<%@ page import="com.utils.WenKu" %>
<%@ page import="com.utils.PropertiesProcessor" %>
<%@ page import="com.utils.PrintUtils" %>
<%@ page import="com.utils.NotifyMe" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    /**
     *验证通知 处理自己的业务
     */

    System.out.println(PropertiesProcessor.getProperty("username"));
    String key = PropertiesProcessor.getProperty("key");
    Date day=new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 先验证文库地址是否合法，不合法直接返回，合法再继续
    String wenku_url = request.getParameter("param"); // 文库中的 url 地址
    if ((!wenku_url.contains("https://wenku")) && (!wenku_url.contains("https://wk"))) {
        String message = "文库地址不合法<br>"+wenku_url+"<br>请检查地址，或联系 Q 2434861414 退款 ";
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
        return;
    }

    ; //记得更改 http://codepay.fateqq.com 后台可设置
    Map<String, String> params = new HashMap<String, String>(); //申明hashMap变量储存接收到的参数名用于排序
    Map requestParams = request.getParameterMap(); //获取请求的全部参数
    String valueStr = ""; //申明字符变量 保存接收到的变量
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
        String name = (String) iter.next();
        String[] values = (String[]) requestParams.get(name);
        valueStr = values[0];
        //乱码解决，这段代码在出现乱码时使用。如果sign不相等也可以使用这段代码转化
        //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
        params.put(name, valueStr);//增加到params保存
    }
    List<String> keys = new ArrayList<String>(params.keySet()); //转为数组
    Collections.sort(keys); //重新排序
    String prestr = "";
    String sign = params.get("sign"); //获取接收到的sign 参数

    for (int i = 0; i < keys.size(); i++) { //遍历拼接url 拼接成a=1&b=2 进行MD5签名
        String key_name = keys.get(i);
        String value = params.get(key_name);
        if (value == null || value.equals("") || key_name.equals("sign")) { //跳过这些 不签名
            continue;
        }
        if (prestr.equals("")) {
            prestr = key_name + "=" + value;
        } else {
            prestr = prestr + "&" + key_name + "=" + value;
        }
    }
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update((prestr + key).getBytes());
    String mySign = new BigInteger(1, md.digest()).toString(16).toLowerCase();
    if (mySign.length() != 32) {
        mySign = "0" + mySign;
    }

    // 手机端文库地址规范一下
    if (wenku_url.contains("https://wk")) {
        wenku_url = wenku_url.replace("https://wk", "https://wenku");
    }
    if (mySign.equals(sign)) {
        //编码要匹配 编码不一致中文会导致加密结果不一致
        //参数合法处理业务
        String pay_no = request.getParameter("pay_no");//流水号
        String resourceUrl = "";
        if (pay_no != null && pay_no != "") {
            resourceUrl = WenKu.getResourceUrl(wenku_url);
//            resourceUrl = "http://localhost:8080/test.wmv";
        }
        if (resourceUrl.contains("https://wkbos.bdimg.com/") || resourceUrl.contains("39.108.149.27")) {
            request.setAttribute("resourceUrl", resourceUrl);
            request.getRequestDispatcher("/WEB-INF/download.jsp").forward(request, response);
            NotifyMe.notifyMe(pay_no,"成功订单");
        } else {
            String message = PrintUtils.printToUser("下载失败！" +
                    "<br>订单编号: "
                    + pay_no + "<br>文章地址: " + wenku_url + " <br> " + df.format(day));
            request.setAttribute("message", message);
            request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
            NotifyMe.notifyMe(pay_no,resourceUrl);
        }
    } else {
        String message = PrintUtils.printToUser("支付出现问题" + "<br>文章地址: " + wenku_url + " <br> " + df.format(day));
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        NotifyMe.notifyMe("","支付出现问题");
    }
%>