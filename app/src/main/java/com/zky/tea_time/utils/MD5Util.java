package com.zky.tea_time.utils;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Util {
    public static final MD5Util INSTANCE;

    @NotNull
    public static String md5Encoding(@NotNull String content) {
        String re_md5 = new String();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            Charset var5 = Charset.forName("UTF-8");
            byte[] var10000 = content.getBytes(var5);
            byte[] var10 = var10000;
            md.update(var10);
            byte[] b = md.digest();
            StringBuffer buf = new StringBuffer("");
            int offset = 0;

            for(int var8 = b.length; offset < var8; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            String var13 = buf.toString();
            re_md5 = var13;
        } catch (NoSuchAlgorithmException var11) {
            var11.printStackTrace();
        }

        return re_md5;
    }

    static {
        MD5Util var0 = new MD5Util();
        INSTANCE = var0;
    }
}