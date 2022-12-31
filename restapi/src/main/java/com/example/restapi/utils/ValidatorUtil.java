package com.example.restapi.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidatorUtil {

    public HashMap<String, String> toErrors(List<FieldError> fieldErrors) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (fieldErrors != null) {
            for (FieldError fieldError : fieldErrors) {
                hashMap.put(fieldError.getField(), fieldError.getCode());
            }
        }

        return hashMap;
    }

    // not null and length > 0
    public static boolean isEmpty(String text) {
        if (text == null || text.isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean checkPhone(String phone) {
        String regexStr = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

}
