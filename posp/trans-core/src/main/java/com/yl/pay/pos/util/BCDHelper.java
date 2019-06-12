package com.yl.pay.pos.util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public class BCDHelper {
    public BCDHelper() {
    }

    private static char ConvertHexChar(char ch) {
        return ch >= 48 && ch <= 57?(char)(ch - 48):(ch >= 65 && ch <= 70?(char)(ch - 65 + 10):(ch >= 97 && ch <= 102?(char)(ch - 97 + 10):'\uffff'));
    }

    public static int byte2int(byte val) {
        return val >= 0?val:val + 256;
    }

    public static byte[] StrToBCD(String str) {
        return StrToBCD(str, str.length());
    }

    public static byte[] StrToBCD(String str, int numlen) {
        if(numlen % 2 != 0) {
            ++numlen;
        }

        while(str.length() < numlen) {
            str = "0" + str;
        }

        byte[] bStr = new byte[str.length() / 2];
        char[] cs = str.toCharArray();
        boolean i = false;
        int iNum = 0;

        for(int var7 = 0; var7 < cs.length; var7 += 2) {
            boolean iTemp = false;
            int var8;
            if(cs[var7] >= 48 && cs[var7] <= 57) {
                var8 = cs[var7] - 48 << 4;
            } else {
                if(cs[var7] >= 97 && cs[var7] <= 102) {
                    cs[var7] = (char)(cs[var7] - 32);
                }

                var8 = cs[var7] - 48 - 7 << 4;
            }

            if(cs[var7 + 1] >= 48 && cs[var7 + 1] <= 57) {
                var8 += cs[var7 + 1] - 48;
            } else {
                if(cs[var7 + 1] >= 97 && cs[var7 + 1] <= 102) {
                    cs[var7 + 1] = (char)(cs[var7 + 1] - 32);
                }

                var8 += cs[var7 + 1] - 48 - 7;
            }

            bStr[iNum] = (byte)var8;
            ++iNum;
        }

        return bStr;
    }

    public static byte[] stringToBcd(String src, int numlen) {
        int inum = 0;
        if(numlen % 2 > 0) {
            return null;
        } else {
            byte[] dst = new byte[numlen / 2];

            for(int i = 0; i < numlen; i += 2) {
                char hghch = ConvertHexChar(src.charAt(i));
                char lowch = ConvertHexChar(src.charAt(i + 1));
                dst[inum++] = (byte)(hghch * 16 + lowch);
            }

            return dst;
        }
    }

    public static char[] asciiToBcd(String src) {
        int inum = 0;
        String str = src.trim().replaceAll(" ", "");
        int numlen = str.length();
        if(numlen % 2 > 0) {
            return null;
        } else {
            char[] dst = new char[numlen / 2];

            for(int i = 0; i < numlen; i += 2) {
                char hghch = ConvertHexChar(str.charAt(i));
                char lowch = ConvertHexChar(str.charAt(i + 1));
                dst[inum++] = (char)(hghch * 16 + lowch);
            }

            return dst;
        }
    }

    public static String bcdToString(byte[] bcdNum, int offset, int numlen) {
        int len = numlen;
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < len; ++i) {
            sb.append(Integer.toHexString((bcdNum[i + offset] & 240) >> 4));
            sb.append(Integer.toHexString(bcdNum[i + offset] & 15));
        }

        return sb.toString().toUpperCase();
    }

    public static String hex2DebugHexString(byte[] b, int len) {
        int[] x = new int[len];
        String[] y = new String[len];
        StringBuilder str = new StringBuilder();

        for(int j = 0; j < len; ++j) {
            x[j] = b[j] & 255;

            for(y[j] = Integer.toHexString(x[j]); y[j].length() < 2; y[j] = "0" + y[j]) {
                ;
            }

            str.append(y[j]);
            str.append(" ");
        }

        return (new String(str)).toUpperCase();
    }

    public static byte[] intToBytes(int value) {
        byte[] src = new byte[]{(byte)(value & 255), (byte)(value >> 8 & 255), (byte)(value >> 16 & 255), (byte)(value >> 24 & 255)};
        return src;
    }

    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[]{(byte)(value >> 24 & 255), (byte)(value >> 16 & 255), (byte)(value >> 8 & 255), (byte)(value & 255)};
        return src;
    }

    public static int bytesToInt(byte[] src, int offset) {
        int value = src[offset] & 255 | (src[offset + 1] & 255) << 8 | (src[offset + 2] & 255) << 16 | (src[offset + 3] & 255) << 24;
        return value;
    }

    public static int bytesToInt2(byte[] src, int offset) {
        int value = (src[offset] & 255) << 24 | (src[offset + 1] & 255) << 16 | (src[offset + 2] & 255) << 8 | src[offset + 3] & 255;
        return value;
    }

    public static boolean memcmp(byte[] src, int srcposition, byte[] des, int desposition, int len) {
        for(int i = 0; i < len; ++i) {
            if(src[srcposition + i] != des[desposition + i]) {
                return false;
            }
        }

        return true;
    }

    public static void memset(byte[] src, byte des, int length) {
        for(int i = 0; i < src.length; ++i) {
            src[i] = des;
        }

    }

    public static void memcpy(byte[] des, byte[] src, int length) {
        System.arraycopy(src, 0, des, 0, length);
    }

    public static boolean ifContain(byte[] src, byte des) {
        for(int i = 0; i < src.length; ++i) {
            if(src[i] == des) {
                return true;
            }
        }

        return false;
    }
}
