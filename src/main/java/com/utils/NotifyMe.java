package com.utils;

/**
 * Created by cheng on 2018/5/3.
 */
public class NotifyMe {
    public static void notifyMe(final String pay_no,final String status) {

        new Thread(){
            @Override
            public void run() {
                String urlNameString = "https://wenkubao.cc/?GetScore&id=" + PropertiesProcessor.getProperty("username") + "&pw=" + PropertiesProcessor.getProperty("password");
                String string  = NetUtils.get(urlNameString);
                String score = string.split(",")[0].split(":")[1];

                String res = NetUtils.getWithParam("剩余积分:"
                        + score + "___订单编号:" + pay_no + "__状态:" + status);
            }
        }.start();

    }

    public static void main(String[] args) {
        notifyMe("3","4");
        notifyMe("1","2");

    }

}
