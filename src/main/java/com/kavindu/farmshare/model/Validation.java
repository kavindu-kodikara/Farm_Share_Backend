package com.kavindu.farmshare.model;

import java.util.regex.Pattern;

public class Validation {
    public static boolean mobileValidation(String mobile){
        return mobile.matches("^(070|071|072|074|075|076|077|078)\\d{7}$");
    }
}
