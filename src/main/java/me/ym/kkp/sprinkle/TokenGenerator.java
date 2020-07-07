package me.ym.kkp.sprinkle;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TokenGenerator {

    static final String RANDOM_CHAR_LIST = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    //3자리 고유 난수 토큰 생성
    public String generate() throws NoSuchAlgorithmException{
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        StringBuilder sb = new StringBuilder(3);

        for (int i = 0; i < 3; i ++) {
            sb.append(RANDOM_CHAR_LIST.charAt(secureRandom.nextInt(RANDOM_CHAR_LIST.length())));
        }

        return sb.toString();
    }

}
