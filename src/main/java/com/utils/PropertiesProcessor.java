package com.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by cheng on 2018/5/3.
 */
public class PropertiesProcessor {
    private static Properties prop;
    private static Long lastModified = 0l;
    private static String filepath = PropertiesProcessor.class.getClassLoader().getResource("zhanghao.properties").getPath();
    private static void init() {
        prop = new Properties();
        System.out.println(filepath);
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(filepath);
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getProperty(String key) {
        if (prop == null || isPropertiesModified()) {
            init();
        }
        String value = prop.get(key).toString();
        return value;
    }


    /**
     * 设置键值
     */
    public static void setValue(String key, String value){
        if (prop == null || isPropertiesModified()) {
            init();
        }
        ///保存属性到b.properties文件
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream(filepath, false);
            prop.setProperty(key, value);
            prop.store(oFile, "The New properties file");
            oFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 判断是否存在某个key
    public static boolean hasKey(String key) {
        if (prop == null || isPropertiesModified()) {
            init();
        }
        return prop.containsKey(key);
    }


    //判断是否被修改过
    public static boolean isPropertiesModified() {
        boolean returnValue = false;
        File file = new File(PropertiesProcessor.class.getClassLoader().getResource("zhanghao.properties").getPath());
        if (file.lastModified() > lastModified) {
            lastModified=file.lastModified();
            returnValue = true;
        }
        return returnValue;
    }

    public static void main(String[] args) {
        init();
        String username = getProperty("username");
        System.out.println(username);
    }

}
