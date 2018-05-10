package com.anyun.cloud.common;

import org.hashids.Hashids;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
public class HashIdGenerator {
    private static final int RES_ID_LENGTH = 12;
    private static final long RES_ID_ENCODING = 1234567890;

    public static String generate(String name) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = name.getBytes();
        for (byte b : bytes) {
            sb.append(EncryptUtils.byteHEX(b));
        }
        Hashids hashids = new Hashids(sb.toString(), RES_ID_LENGTH);
        String hash = hashids.encode(RES_ID_ENCODING);
        return hash;
    }
}
