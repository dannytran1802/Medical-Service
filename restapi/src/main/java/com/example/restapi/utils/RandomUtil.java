package com.example.restapi.utils;

public class RandomUtil {

    public static final String alphas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String digits = "0123456789";

    public static final String CHARS = alphas.toUpperCase() + alphas.toLowerCase() + digits;

    public static String generateId(long text) {
        String format = "%1$06d";
        return String.format(format, text);
    }


    /**
     * Generate a random password
     */
    public static String generatePassword(int n) {
        // create StringBuffer size of AlphaNumericString
        StringBuilder s = new StringBuilder(n);
        int y;
        for (y = 0; y < n; y++) {
            // generating a random number
            int index = (int) (CHARS.length() * Math.random());
            // add Character one by one in end of s
            s.append(CHARS.charAt(index));
        }
        return s.toString();
    }

    /**
     * Generate a random number
     */
    public static String generateNumber(int n) {
        // create StringBuffer size of AlphaNumericString
        StringBuilder s = new StringBuilder(n);
        int y;
        for (y = 0; y < n; y++) {
            // generating a random number
            int index = (int) (digits.length() * Math.random());
            // add Character one by one in end of s
            s.append(digits.charAt(index));
        }
        return s.toString();
    }

}
