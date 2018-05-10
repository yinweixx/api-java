package com.anyun.cloud.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.UUID;
import java.io.UnsupportedEncodingException;

public class Base64Utils {
    private final static Logger LOGGER= LoggerFactory.getLogger(Base64Utils.class);
    // Encode 加密
    public static  String   getEncoder(String str) {
        try {
            return  Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        }catch(UnsupportedEncodingException e){
            LOGGER.debug("Error",e.getMessage());
            return null;
        }
    }

    // Decode 解密
    public static String  getDecode(String str) {
        try {
            byte[] base64decodedBytes = Base64.getDecoder().decode(str);
            return new String(base64decodedBytes, "utf-8");
        }catch(UnsupportedEncodingException e){
            LOGGER.debug("Error",e.getMessage());
            return null;
        }
    }
}
