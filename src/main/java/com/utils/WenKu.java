package com.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by cheng on 2018/5/2.
 */
public class WenKu {

//    public static void main(String[] args) throws MalformedURLException {
//        //发送 POST 请求
//        String sr= WenKu.sendPost("https://wenkubao.cc/default.aspx",
//                "username=cdxna&password=6359&txtUrl=https://wk.baidu.com/view/69d8f83c52d380eb62946de4?pcf=2&from=singlemessage&isappinstalled=0#1");
//        System.out.println(sr);
//
//        downloadNet();
//    }

    public static void downloadNet() throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL("http://39.108.149.27:8888/doc/%e8%bd%a6%e8%be%86%e8%b0%83%e5%ba%a6%e6%96%b9%e6%b3%95.ppt");

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("/Users/cheng/Desktop/1.ppt");

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getResourceUrl(String url) {
        String username = PropertiesProcessor.getProperty("username");
        String password = PropertiesProcessor.getProperty("password");
        String wenku_url = url;
        String param = "username=" + username + "&password=" + password + "&txtUrl=" + wenku_url;
        return sendPost("https://wenkubao.cc/default.aspx", param);
    }

    private static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            return "。。服务器异常。。";
//            System.out.println("发送 POST 请求出现异常！"+e);
//            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
