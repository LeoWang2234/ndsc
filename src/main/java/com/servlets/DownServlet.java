package com.servlets;

import com.utils.PropertiesProcessor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

/**
 * 文件作为附件发送时，若是太大，则自动转换为下载链接，让用户自行下载
 */
public class DownServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("name");
        fileName = URLDecoder.decode(fileName, "UTF-8");
        String downloadFilePath = PropertiesProcessor.getProperty("csdn_download_file_path");
        String fileFullPath = downloadFilePath + fileName;

        File file = new File(fileFullPath);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            BufferedInputStream bs = new BufferedInputStream(is);
            ServletOutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            response.setHeader("Content-Disposition", "attachment; filename="+new String(fileName.getBytes("GBK"),"ISO8859_1"));
            byte[] len = new byte[1024 * 2];
            int read = 0;
            while ((read = is.read(len)) != -1) {
                bos.write(len, 0, read);
                System.out.println("read---" + read);
            }
            bos.flush();
            bos.close();
            is.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
