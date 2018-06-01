package com.csdn;

import com.utils.PropertiesProcessor;
import com.wechat.exception.MyException;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CsdnDownloader {

    private String downloadFilePath = PropertiesProcessor.getProperty("csdn_download_file_path");
    // 用来记录今天一功被调用了多少次接口了
    public static int times = 0;

    public static String startDay = String.valueOf(Calendar.getInstance().get(Calendar.DATE));

    private static CsdnDownloader csdnDownloader;

    private CsdnDownloader() {

    }

    public static CsdnDownloader getCsdnDownloader() {
        if (null == csdnDownloader) {
            return new CsdnDownloader();
        }else {
            return csdnDownloader;
        }
    }

    public  Map<String,String> getRealAddressAndDownload(String resourceUrl) throws MyException {
        String realAddress = this.getRealAddress(resourceUrl);// 输入CSDN的用户名，和密码
        // 返回下载后文件的存放地址，供发邮件时添加附件使用
        return this.getFileDownload(realAddress);

    }

    /**
     * 由真实的资源地址链接，下载得到资源
     *
     * @param realAddress
     * @throws Exception
     */
    public Map<String,String> getFileDownload(String realAddress) throws MyException {
        HashMap<String, String> map = new HashMap();
        try {
            URL resource_real_url = new URL(realAddress);
            URLConnection conn = resource_real_url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            httpURLConnection.connect();
            InputStream in = httpURLConnection.getInputStream();
            String fileName = URLDecoder.decode(realAddress, "utf-8").split("\"")[1];
            if (fileName == null || fileName.equals("")) {
                throw new MyException("文件名字解析失败：" + fileName);
            }
            File file = new File(downloadFilePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength = in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }
            out.flush();
//        return downloadFilePath + fileName;
            String sizeString = httpURLConnection.getHeaderField("Content-Length");
            map.put("size", (Integer.valueOf(sizeString).intValue() / (1024 * 1024)) + "");
            map.put("downloadFilePath", downloadFilePath);
            map.put("fileName", fileName);
        } catch (Exception e) {
            throw new MyException("得到真实地址后下载失败: " + e.getMessage());

        }
        return map;
    }

    /**
     * 由下载链接，得到真实得资源地址链接
     *
     * @param resourceUrl
     * @return
     * @throws Exception
     */
    public String getRealAddress(String resourceUrl) throws MyException {
        times += 1;
        Calendar cal=Calendar.getInstance();
        String nowDay;
        nowDay = String.valueOf(cal.get(Calendar.DATE));
        // 新的一天开始了，下载计数器重置为 0
        if (!nowDay.equals(startDay)) {
            times = 0;
        }
        String url = resourceUrl;
        String[] url_segs = url.split("/");
        String lastPart = url_segs[url_segs.length - 1];
        char[] charsOfLastPart = lastPart.toCharArray();
        int i = 0;
        String resourceId = "";
        while (i < charsOfLastPart.length && Character.isDigit(charsOfLastPart[i])) {
            resourceId = resourceId + charsOfLastPart[i];
            i++;
        }
        Response resource_download = null;
        String resource_url = "https://download.csdn.net/index.php/vip_download/download_client/" + resourceId;
        try {
            if (times % 2 == 0) {
                resource_download = getResponse1(url, resource_url);
            }else {
                resource_download = getResponse2(url, resource_url);
            }
        } catch (Exception e) {
            throw new MyException("获取真实下载地址失败" + e.getMessage());
        }
        return resource_download.header("Location");
    }

    private Response getResponse2(String url, String resource_url) throws IOException {
        Response resource_download;
        resource_download = Jsoup.connect(resource_url)
                .method(Method.GET).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .header("Referer", url)
                .cookie("BT", "1527138124880")
                .cookie("AU", "B72")
                .cookie("UN", "test23423")
                .cookie("UserNick", "test23423")
                .cookie("UserInfo", "JH%2B6%2FG9RDitrX%2BMooC4kfVcd3fTxpUoLNPFz8oa9CWwaa0PVUt3eklbzZuR8Y8doN1XGfFiUAsu6awDDwAv7O7pUEG0bqGXiNlh7HJpfSihlWEqMomM8WkN%2BmhDRLBGcUOckDHJqMSIiPRWanw%2FboQ%3D%3D")
                .cookie("UserName", "test23423")
                .cookie("CASTGC", "TGT-34958-frTb9jHk5SWOBKxBq3NhZREVcFfpKBlWOi5gFhJGL43cQbFb1i-passport.csdn.net")
//                    .cookies(resource_page.cookies())
                .followRedirects(false)
                .execute();
        return resource_download;
    }
    private Response getResponse1(String url, String resource_url) throws IOException {
        Response resource_download;
        resource_download = Jsoup.connect(resource_url)
                .method(Method.GET).header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .header("Referer", url)
                .cookie("BT", "1527138124880")
                .cookie("AU", "B72")
                .cookie("UN", "test23423")
                .cookie("UserNick", "test23423")
                .cookie("UserInfo", "JH%2B6%2FG9RDitrX%2BMooC4kfVcd3fTxpUoLNPFz8oa9CWwaa0PVUt3eklbzZuR8Y8doN1XGfFiUAsu6awDDwAv7O7pUEG0bqGXiNlh7HJpfSihlWEqMomM8WkN%2BmhDRLBGcUOckDHJqMSIiPRWanw%2FboQ%3D%3D")
                .cookie("UserName", "test23423")
                .cookie("CASTGC", "TGT-34958-frTb9jHk5SWOBKxBq3NhZREVcFfpKBlWOi5gFhJGL43cQbFb1i-passport.csdn.net")
//                    .cookies(resource_page.cookies())
                .followRedirects(false)
                .execute();
        return resource_download;
    }

    private void getFileName() throws UnsupportedEncodingException {

        String str = "http://dl.download.csdn.net/down11/20151210/76f51e3bf12a06b387823df7bd6d5f7f.rar?response-content-disposition=attachment%3Bfilename%2A%3D%22utf8%27%27%E5%8F%8C%E9%97%A8%E9%99%90%E6%A3%80%E6%B5%8B.rar%22&OSSAccessKeyId=9q6nvzoJGowBj4q1&Expires=1527147035&Signature=typNIFsvJ1Z8WboQSTABtxOQRjY%3D&user=test23423&sourceid=9343543&sourcescore=3&isvip=1&test23423&9343543";
        System.out.println(URLDecoder.decode(str, "utf-8"));
        System.out.println(str);
    }

    public static void main(String[] args) throws MyException {
    }

}