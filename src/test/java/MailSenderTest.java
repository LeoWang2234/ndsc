import com.mail.MailSender;
import com.wechat.exception.MyException;
import org.junit.Test;

/**
 * Created by cheng on 2018/5/26.
 */
public class MailSenderTest {
    @Test
    public void sendMailTest() throws MyException {
//        MailSender.getMailSender().send("/Users/cheng/Downloads/", "877217445@qq.com", "谁说菜鸟不会数据分析（全套三本）.zip");
    }

    @Test
    public void sendPlainMailTest() throws MyException {
//        MailSender.getMailSender().sendPlainMail("877217445@qq.com","utf8''Django架站的16堂課-活用Django+Web+Framework快速建構動態網站.rar");
        MailSender.getMailSender().sendPlainMailAfterRename("877217445@qq.com", "utf8''Django架站的16堂課-活用Django+Web+Framework快速建構動態網站.rar");
    }

    @Test
    public void testSplit() {
        String name = "utf8''Django架站的16堂課-活用Django+Web+Framework快速建構動態網站.rar";
        String[] strs = name.split("\\.");
        System.out.println(strs);
    }
}
