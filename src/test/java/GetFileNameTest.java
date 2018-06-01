import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2018/5/25.
 */
public class GetFileNameTest {
    @Test
    public void getFileNameTest() throws UnsupportedEncodingException {

//        String str = "http://dl.download.csdn.net/down11/20151210/76f51e3bf12a06b387823df7bd6d5f7f.rar?response-content-disposition=attachment%3Bfilename%2A%3D%22utf8%27%27%E5%8F%8C%E9%97%A8%E9%99%90%E6%A3%80%E6%B5%8B.rar%22&OSSAccessKeyId=9q6nvzoJGowBj4q1&Expires=1527147035&Signature=typNIFsvJ1Z8WboQSTABtxOQRjY%3D&user=test23423&sourceid=9343543&sourcescore=3&isvip=1&test23423&9343543";
        String str = "http://dl.download.csdn.net/down11/20151210/76f51e3bf12a06b387823df7bd6d5f7f.rar?response-content-disposition=attachment%3Bfilename%2A%3D%22utf8%27%27%E5%8F%8C%E9%97%A8%E9%99%90%E6%A3%80%E6%B5%8B.rar%22&OSSAccessKeyId=9q6nvzoJGowBj4q1&Expires=1527146590&Signature=rtIwg%2BmYEuvKPc6Np2jD3siTL3E%3D&user=test23423&sourceid=9343543&sourcescore=3&isvip=1&test23423&9343543";

        str = URLDecoder.decode(str, "utf-8");
        System.out.println(str.split("\"")[1]);
//        System.out.println(str);
//        Pattern p=Pattern.compile("\"(.*?)\"");
//        Matcher m=p.matcher(str);
//        System.out.println(m.group());
        System.out.println("utf8''Django架站的16堂課-活用Django+Web+Framework快速建構動態網站.rar".length());
    }
}
