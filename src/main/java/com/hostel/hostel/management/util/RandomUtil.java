package com.hostel.hostel.management.util;

import java.security.SecureRandom;
import java.util.UUID;

public final class RandomUtil {

    private static final SecureRandom RANDOM=new SecureRandom();
    private static final String ALPHANUM="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private RandomUtil(){}

    public static String generateKey(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String generatePassword(){
        return generateRandomString(12);
    }

    public static String generateRandomString(int length){
        StringBuilder sb=new StringBuilder(length);
        for (int i = 0; i <length; i++) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }


}
