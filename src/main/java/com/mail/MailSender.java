package com.mail;


import com.utils.NetUtils;
import com.utils.NotifyMe;
import com.utils.PropertiesProcessor;
import com.wechat.exception.MyException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSender {
    private static MailSender mailSender;
    private MailSender() {

    }
    public static MailSender getMailSender() {
        if (mailSender == null) {
            return new MailSender();
        } else {
            return mailSender;
        }
    }
    private String host = "smtp.163.com"; // smtp服务器
    private String from = "wangcheng2234@163.com"; // 发件人地址
    private String to = ""; // 收件人地址
    private String affixPath = PropertiesProcessor.getProperty("csdn_download_file_path");
    ; // 附件地址
    private String pwd = ""; // 密码
    private String subject = "测试邮件"; // 邮件标题
    private String taoBao = "https://item.taobao.com/item.htm?spm=a230r.1.14.103.74a1bd22snEtmj&id=569855704313&ns=1&abbucket=13#detail";

    public void send(Map<String,String> dirAndNameSize, String to) throws MyException {

        if (Integer.valueOf(dirAndNameSize.get("size")) > 50) {
            MailSender.getMailSender().sendPlainMail(to, dirAndNameSize.get("fileName"));
        }else {
            MailSender.getMailSender().sendNormal(dirAndNameSize, to);
        }
    }

    private void sendNormal(Map<String, String> dirAndNameSize, String to) throws MyException {

        String affixName = dirAndNameSize.get("fileName");
        Properties props = new Properties();
        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
//        session.setDebug(true);
        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));
            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 加载标题
            message.setSubject(affixName);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();

            ///邮件的内容
//            StringBuffer sb = new StringBuffer("<span style=\"color:#FF0000;font-weight:bold;\">" +
//                    "附件是您需要的csdn资源，欢迎再次光临我的taobao小店" +
//                    "</span>" +
//                    "<br>");
//            sb.append("<a href=\"" + taoBao);
//            sb.append("\"</a>" + taoBao + "<br>");
//            contentPart.setContent(sb.toString(), "text/html;charset=utf-8");

            contentPart.setContent("<span style=\"color:#FF0000;font-weight:bold;\">附件是您需要的csdn资源，欢迎再次光临我的taobao小店</span></br>", "text/html;charset=utf-8");

            multipart.addBodyPart(contentPart);
            // 添加附件
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(affixPath + affixName);
            // 添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            // 添加附件的标题
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            messageBodyPart.setFileName(MimeUtility.encodeText(affixName));
            multipart.addBodyPart(messageBodyPart);

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(host, from, pwd);
            // 把邮件发送出去

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            ///邮件的内容, 休息两秒后，转链接发送，发送太快了可能会出错
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            // 要是正常发送也出现错误，那么就转到 改名字后重新发送
            sendPlainMail(to, affixName);
        }
    }

    public void sendPlainMail(String to, String affixName) throws MyException {
        Properties props = new Properties();
        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
        props.put("username", from);
        props.put("password", pwd);
        // 用刚刚设置好的props对象构建一个session
        Session session = getSession();
        // Instantiate a message
        Message msg = new MimeMessage(session);
        try {
            doSendAction(to, affixName, props, msg);
        } catch (Exception e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            sendPlainMailAfterRename(to, affixName);
        }

    }

    public void sendPlainMailAfterRename(String to, String affixName) throws MyException {
        System.out.println(affixName.split("."));
        String type = affixName.split("\\.")[affixName.split("\\.").length - 1];
        String subject = "Z" + (int) ((Math.random() * 9 + 1) * 100000);
        File file = new File(affixPath + affixName); //指定文件名及路径
        String newName = subject + "." + type;
        file.renameTo(new File(newName)); //改名
        Properties props = new Properties();
        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
        props.put("username", from);
        props.put("password", pwd);
        // 用刚刚设置好的props对象构建一个session
        Session session = getSession();
        // Instantiate a message
        Message msg = new MimeMessage(session);
        try {
            doSendAction(to, newName, props, msg);
        } catch (Exception e) {
            throw new MyException("邮件发送出了点问题，改名后再次发送失败：" + e.getMessage());
        }

    }

    private void doSendAction(String to, String newName, Properties props, Message msg) throws MessagingException, UnsupportedEncodingException {
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(newName);
        msg.setSentDate(new Date());
        // 有人说抄送给自己一份不会出错，我先试试
        msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(props.getProperty("username")));

        String downloadUrl = PropertiesProcessor.getProperty("downloadUrl");
        ///邮件的内容
        StringBuffer sb = new StringBuffer("<span style=\"color:#FF0000;font-weight:bold;\">" +
                "因为附件过大，请点击如下下载链接进行下载，若无法点击，请复制到地址栏打开" +
                "</span>" +
                "<br>");
        sb.append("<a href=\"" + downloadUrl + "?name=" + URLEncoder.encode(newName, "UTF-8"));
        sb.append("\"</a>" + downloadUrl + "?name=" + URLEncoder.encode(newName, "UTF-8") + "<br>");
        String content = sb.toString();
        msg.setContent(content, "text/html;charset=utf-8");
        Transport.send(msg);
    }


    private static Session getSession() {

        final String FROM = "wangcheng2234@163.com";//发件人的email
        final String PWD = "";//发件人密码
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");//设置服务器地址
        props.put("mail.store.protocol", "smtp");//设置协议
        props.put("mail.smtp.port", 25);//设置端口
        props.put("mail.smtp.auth", true);
        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }

        };
        Session session = Session.getInstance(props, authenticator);
        return session;
    }

    public static void main(String[] args) {
        MailSender cn = new MailSender();
        /**
         * 设置smtp服务器以及邮箱的帐号和密码
         * 用QQ 邮箱作为发生者不好使 （原因不明）
         * 163 邮箱可以，但是必须开启  POP3/SMTP服务 和 IMAP/SMTP服务
         * 因为程序属于第三方登录，所有登录密码必须使用163的授权码
         */
//        cn.send();
        NotifyMe.notifyMe("1", "2");

    }
}
