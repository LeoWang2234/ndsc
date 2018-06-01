/**
 * Created by cheng on 2018/5/25.
 */

import com.wechat.exception.MyException;
import com.wechat.utils.PraseString;
import org.junit.Test;

import java.util.regex.*;

public class RegxTest {

    @Test
    public void testRegx() throws MyException {
//        String str = "https://download.csdn.net/download/l_badluck/10159774\n" +
//                "邮箱：chenpengkevin@163.com";
//        String str = "877217445@qq.com https://download.csdn.net/download/wangqian4934/756332";
        String str = "879990850@qq.com  + https://download.csdn.net/download/liubing8609/10046490";
//        String str = "邮箱：1396359531@qq.com  需下载：https://download.csdn.net/download/nerocho/8348071";
//        String str = "https://download.csdn.net/download/fuminshen/3828030\n" +
//                "\n" +
//                "邮箱yg070520@sina.com";
//        String str = "资源地址解析成功：https://download.csdn.net/download/whicun/1566897\n" +
//                "邮箱解析成功：chenpengkevin@163.com\n" +
//                "已经进入下载流程";
//        String str = "2661665548@qq.com https://download.csdn.net/download/u012090149/6465897";
//        String str = "sdfsdfsd23232323@baidu.com2323sdf@sdfs.comddd";
//        String mail_reg = "([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})";
//        Pattern mail_pattern = Pattern.compile(mail_reg);
//        Matcher mail_matcher = mail_pattern.matcher(str);
//        while (mail_matcher.find()) {
//            System.out.println(mail_matcher.group());
//        }
//
//        String url_reg = "[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
//        Pattern url_pattern = Pattern.compile(url_reg);
//        Matcher url_matcher = url_pattern.matcher(str);
//
//        while (url_matcher.find()) {
//            System.out.println(url_matcher.group());
//        }

        String mail = PraseString.getMail(str);
        System.out.println(mail);
        String url = PraseString.getResourceAddress(str);
        System.out.println(url);

    }
}
