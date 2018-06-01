import com.utils.PropertiesProcessor;
import org.junit.Test;

/**
 * Created by cheng on 2018/5/28.
 */
public class PropProcessorTest {
    @Test
    public void writeTest() {
//        PropertiesProcessor.setValue("pass_tokens", "876");
        PropertiesProcessor.setValue("123", "456");
        System.out.println(PropertiesProcessor.getProperty("pass_tokens"));
        System.out.println(PropertiesProcessor.getProperty("url"));
        System.out.println(PropertiesProcessor.getProperty("downloadUrl"));
    }

    @Test
    public void hasKeyTest() {
        System.out.println(PropertiesProcessor.hasKey("url"));
        System.out.println(PropertiesProcessor.hasKey("hjl"));
    }
}
