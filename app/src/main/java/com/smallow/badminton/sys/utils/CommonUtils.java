package com.smallow.badminton.sys.utils;

import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by smallow on 15/12/5.
 */
public class CommonUtils {

    public static void checkVisibility(View v, int visibility) {
        if (v == null) {
            return;
        }
        if (v.getVisibility() == visibility) {
            return;
        }
        v.setVisibility(visibility);
    }

    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }
}
