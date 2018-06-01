import java.util.Random;

/**
 * Created by cheng on 2018/5/30.
 */
public class GenerateTokens {
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 20; i++) {
            sb.append("T" + (int) ((Math.random() * 9 + 1) * 100000));
            sb.append(";");
        }
        System.out.println(sb.toString());
    }
}
