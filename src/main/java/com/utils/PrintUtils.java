package com.utils;

/**
 * Created by cheng on 2018/5/3.
 */
public class PrintUtils {
    public static String printToUser(String message) {
        String out = "";
        out = "<p style=\"color:red;font-size:16px;\">" + message + "</p><br><p>截图以上文字并联系客服 Q 2434861414</p>";
        return out;
    }
}
