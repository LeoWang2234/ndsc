package com.wechat.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wechat.exception.*;

/**
 * Created by cheng on 2018/5/25.
 */
public class PraseString {
    public static String getMail(String string) throws MyException {
        try {
            String mail_reg = "([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})";
            Pattern mail_pattern = Pattern.compile(mail_reg);
            Matcher mail_matcher = mail_pattern.matcher(string);
            mail_matcher.find();
            return mail_matcher.group();
        } catch (Exception e) {
            throw new MyException("获取邮箱失败:" + e.getMessage());
        }

    }

    public static String getResourceAddress(String string) throws MyException {
        try {
            String url_reg = "[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
            Pattern url_pattern = Pattern.compile(url_reg);
            Matcher url_matcher = url_pattern.matcher(string);
            url_matcher.find();
            return url_matcher.group();
        } catch (Exception e) {
            throw new MyException("获取下载地址失败:" + e.getMessage());
        }

    }
}
