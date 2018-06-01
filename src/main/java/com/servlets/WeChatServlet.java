package com.servlets;

import com.csdn.CsdnDownloader;
import com.mail.MailSender;
import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.NetUtils;
import com.wechat.exception.MyException;
import com.wechat.message.TextMessage;
import com.wechat.utils.MessageUtil;
import com.wechat.utils.PraseString;
import org.dom4j.DocumentException;

/**
 * 新手接入
 * 本次接入设定的访问URL为：http://www.weixin4j.org/api/公众号名称
 *
 * @author weixin4j<weixin4j@ansitech.com>
 */
public class WeChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        //1.验证消息真实性
        //获取微信公众号
        String gongzonghao = path.substring(path.lastIndexOf("/"));
        //如果获取不到公众号，则向服务器发生错误信息
        if (gongzonghao == null) {
            response.getWriter().write("error");
        } else {
            //根据公众号，算出对应的Token,然后进行验证
            gongzonghao = gongzonghao.substring(1);
            //获取配置文件配置的Token
            //成为开发者验证
            String signature = request.getParameter("signature");   // 微信加密签名
            String timestamp = request.getParameter("timestamp");   // 时间戳
            String nonce = request.getParameter("nonce");           // 随机数
            String echostr = request.getParameter("echostr");       //
            //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
            response.getWriter().write(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
        获取公众号发来的消息
         */
        Map<String, String> map = null;
        try {
            map = MessageUtil.parseXml(request);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        String openid = map.get("FromUserName"); //用户 openid
        String mpid = map.get("ToUserName");   //公众号原始 ID
        TextMessage txtmsg = new TextMessage();
        txtmsg.setToUserName(openid);
        txtmsg.setFromUserName(mpid);
        txtmsg.setCreateTime(new Date().getTime());
        txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);


        String respXML = "";
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        try {
            if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                // 如果不是我发的，则直接返回
                if (!map.get("FromUserName").equals("op4PbwXThNWRq51yaZgjFVJx_WQ8")) {
                    txtmsg.setContent("哎吆，我看不懂呢");
                    sendMessage(response, txtmsg);
                } else {
                    String content = map.get("Content").trim();
                    /*
                        拿到前端传入到邮箱地址和资源地址
                     */
                    String mail = PraseString.getMail(content);
                    txtmsg.setContent("邮箱解析成功：" + mail);

                    String address = PraseString.getResourceAddress(content);
                    txtmsg.setContent("资源地址解析成功：" + address + "\n邮箱解析成功：" + mail + "\n" + "已经进入下载流程");
                    sendMessage(response, txtmsg);


                    // 下载开始
                    Map<String, String> dirAndNameSize = CsdnDownloader.getCsdnDownloader().getRealAddressAndDownload(address);
                    String fileFullPath = dirAndNameSize.get("downloadFilePath") + dirAndNameSize.get("fileName");
                    NetUtils.getWithParam("文件已经下载："+CsdnDownloader.times+"--" + fileFullPath + "\n 文件大小：" + dirAndNameSize.get("size") + " M");
                    /*
                    下载成功，发送邮件给用户 文件大于 40 M 时选择链接发送
                     */
                    MailSender.getMailSender().send(dirAndNameSize, mail);
                    NetUtils.getWithParam("文件已经发送成功：" + fileFullPath);

                }
            }
        } catch (MyException e) {
            NetUtils.getWithParam("发生错误：" + e.getMsg());
        } catch (Exception e) {
            NetUtils.getWithParam("发生未知错误：" + e.getMessage());
        }
    }

    private void sendMessage(HttpServletResponse response, TextMessage txtmsg) {
        String respXML = MessageUtil.textMessageToXml(txtmsg);
        // 文本消息
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(respXML);
        out.flush();
        out.close();
    }
}