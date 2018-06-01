package com.servlets;

import com.csdn.CsdnDownloader;
import com.mail.MailSender;
import com.utils.NetUtils;
import com.utils.PropertiesProcessor;
import com.wechat.exception.MyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * Created by cheng on 2018/5/28.
 */
public class SeflHelpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<String, String[]> map = request.getParameterMap();

//        String post_valicode = map.get("valicode")[0].toString().toLowerCase().trim();
        String cdkey = map.get("cdkey")[0].toString().trim();
        final String downurl = map.get("downurl")[0].toString().trim();
        final String mail = map.get("mail")[0].toString().trim();

//        HttpSession session = request.getSession();
//        String valicode = "";
//        try {
//
//            valicode = session.getAttribute("valicode").toString().toLowerCase();
//        } catch (NullPointerException e) {
//            request.setAttribute("valicode_message", "验证码过期，请刷新页面并请重新填写");
//            request.setAttribute("cdkey", cdkey);
//            request.setAttribute("downurl", downurl);
//            request.setAttribute("mail", mail);
//            request.getRequestDispatcher("/selfhelp.jsp").forward(request, response);
//        }
//
////         首先验证验证码是否正确，若是不正确，直接返回原页面，重新填写
//        if (post_valicode == null || (!valicode.toLowerCase().equals(post_valicode))) {
//            request.setAttribute("valicode_message", "验证码错误，请重新填写");
//            request.setAttribute("cdkey", cdkey);
//            request.setAttribute("downurl", downurl);
//            request.setAttribute("mail", mail);
//            request.getRequestDispatcher("/selfhelp.jsp").forward(request, response);
//            return;
//        }
//        session.setAttribute("valicode", "refresh");

//         这个 token 已经被这个资源绑定，允许重复下载
//        if (PropertiesProcessor.hasKey(cdkey) && PropertiesProcessor.getProperty(cdkey).equals(downurl)) {
//            try {
//                String realUrl = CsdnDownloader.getCsdnDownloader().getRealAddress(downurl);
//                System.out.println(realUrl);
//                request.setAttribute("resourceUrl", realUrl);
//                // 在这里新开一个线程，完成下载到服务器，并发送邮件等操作
//                nsycSendMail(mail, realUrl);
//                request.getRequestDispatcher("/WEB-INF/download2.jsp").forward(request, response);
//                return;
//            } catch (MyException e) {
//                NetUtils.getWithParam("重复下载时出现异常：通行证" + cdkey);
//                throw new ServletException();
//            }
//        }

        //         这个 token 已经被这个资源绑定，不允许重复下载
        if (PropertiesProcessor.hasKey(cdkey)) {
            request.setAttribute("mail", mail);
            request.setAttribute("downurl", downurl);
            request.setAttribute("cdkey_message", "这是一个用过的通行证");
            request.getRequestDispatcher("/selfhelp.jsp").forward(request, response);
            return;
        }

        // 验证通行证是否正确，若是不正确，直接返回
        String[] tokens = PropertiesProcessor.getProperty("pass_tokens").split(";");
        List<String> list = Arrays.asList(tokens);
        ArrayList<String> arrayList = new ArrayList<String>(list);
        LinkedList tokensList = new LinkedList(arrayList);

        Iterator<String> it = tokensList.iterator();
        while (it.hasNext()) {
            String x = it.next();
            if (x.equals(cdkey)) {
                try {
//                    String realUrl = CsdnDownloader.getCsdnDownloader().getRealAddress("");
                    String realUrl = CsdnDownloader.getCsdnDownloader().getRealAddress(downurl);
//                    String realUrl = "http://www.baidu.com";
                    if (realUrl != null) {
                        PropertiesProcessor.setValue(x, downurl);
                        it.remove();
                        tokens = (String[]) tokensList.toArray(new String[tokensList.size()]);
                        String new_tokens = String.join(";", tokens);
                        PropertiesProcessor.setValue("pass_tokens", new_tokens);
                    }else {
                        NetUtils.getWithParam("通行证：" + x + " 获取真实下载地址失败");
                        request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                        return;
                    }
                    request.setAttribute("resourceUrl", realUrl);
//                    request.setAttribute("resourceUrl", "http://www.baidu.com");
                    NetUtils.getWithParam("新的订单完成：token:" + x +"--第 "+CsdnDownloader.times+" 次调用");
                    // 在这里新开一个线程，完成下载到服务器，并发送邮件等操作
                    //Send the message
                    nsycSendMail(mail, realUrl);
                    request.getRequestDispatcher("/WEB-INF/download3.jsp").forward(request, response);
                    return;
                    // todo
                } catch (Exception e) {
                    // todo
                    NetUtils.getWithParam("获取下载链接时出现错误：通行证:" + x + "\n 地址:" + downurl + "\n" + e.getMessage());
                    throw new ServletException();
                }
            }
        }
        // 通行证验证不通过，则返回，并不下载
        request.setAttribute("mail", mail);
        request.setAttribute("downurl", downurl);
        request.setAttribute("cdkey_message", "无效的通行证，请重新填写");
        request.getRequestDispatcher("/selfhelp.jsp").forward(request, response);

    }

    private void nsycSendMail(final String mail, final String realUrl) {
        //Send the message
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null == realUrl) {
                        NetUtils.getWithParam("无法下载并发送邮件，因为资源真实地址为空");
                        return;
                    }
                    // 下载开始
                    Map<String, String> dirAndNameSize = CsdnDownloader.getCsdnDownloader().getFileDownload(realUrl);
                    String fileFullPath = dirAndNameSize.get("downloadFilePath") + dirAndNameSize.get("fileName");
                    NetUtils.getWithParam("文件已经下载：" + fileFullPath + "\n 文件大小：" + dirAndNameSize.get("size") + " M");
                    MailSender.getMailSender().send(dirAndNameSize, mail);
                    NetUtils.getWithParam("文件已经发送成功：" + fileFullPath);
                } catch (MyException e) {
                    NetUtils.getWithParam("文件异步发送失败：" + e.getMessage());
                }

            }
        }).start();
    }
}
