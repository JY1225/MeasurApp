package lmsBase;

import java.security.*;
import SensorBase.*;

public class Md5
{
    public static String string2MD5(final String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (Exception e) {
            LMSLog.exception((Throwable)e);
            return "";
        }
        final char[] charArray = inStr.toCharArray();
        final byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; ++i) {
            byteArray[i] = (byte)charArray[i];
            if (i == 0) {
                byteArray[i] ^= 0x5A;
            }
            if (i == 1) {
                byteArray[i] ^= 0x5A;
            }
            if (i == 2) {
                byteArray[i] ^= 0x57;
            }
            if (i == 3) {
                byteArray[i] ^= 0x57;
            }
            if (i == 4) {
                byteArray[i] ^= 0x58;
            }
            if (i == 5) {
                byteArray[i] ^= 0x59;
            }
        }
        final byte[] md5Bytes = md5.digest(byteArray);
        final StringBuffer hexValue = new StringBuffer();
        for (int j = 0; j < md5Bytes.length; ++j) {
            final int val = md5Bytes[j] & 0xFF;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    public static String convertMD5(final String inStr) {
        final char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] ^= (char)(116 + (i << 1) + 97 * i);
        }
        final String s = new String(a);
        return s;
    }
}
