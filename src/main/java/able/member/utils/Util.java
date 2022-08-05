package able.member.utils;
import java.util.Random;

public class Util {
    public static String createRandomNum() {
        String randomNumber;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i=0; i<6; i++) {
            int createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
            sb.append(createNum);
        }
        randomNumber = sb.toString();
        sb.setLength(0);

        return randomNumber;
    }
}
