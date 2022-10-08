package com.admin.firstproject.util;

public class Code {
    public static final String CATEGORY_CODE = "CAT";
    public static final int CATEGORY_LENGTH = 6;

    public static String generateCode(String prefix, long current, int maxLength) {
        String complement =  completeZero(prefix, maxLength - (prefix.length() + String.valueOf(current).length()));
        return complement + current;
    }

    private static String completeZero(String text, int quantity) {
        return text + "0".repeat(Math.max(0, quantity));
    }

    public static void main(String[] args) {
        System.out.println(generateCode("CAT", 2, 6));
    }

}
