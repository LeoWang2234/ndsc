import com.csdn.CsdnDownloader;
import com.wechat.exception.MyException;
import org.junit.Test;

/**
 * Created by cheng on 2018/5/29.
 */
public class GetRealAddressTest {
    @Test
    public void getRealAddressTest() throws MyException {
        System.out.println( CsdnDownloader.getCsdnDownloader().getRealAddress("https://download.csdn.net/download/alai986/2397235"));
        System.out.println( CsdnDownloader.getCsdnDownloader().getRealAddress("https://download.csdn.net/download/alai986/2397235"));
    }
}
