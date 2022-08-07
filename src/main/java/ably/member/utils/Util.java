package ably.member.utils;
import java.util.Random;
import java.util.regex.Pattern;

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

    public static void checkPasswordValidation(String password) {
        boolean matches = Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", password);
        if (!matches) {
            throw new IllegalArgumentException("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
        }
    }
}
