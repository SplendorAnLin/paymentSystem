
package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;

import org.bouncycastle.util.encoders.Base64;

import java.io.*;

public class Base64Utils {
    private static final int CACHE_SIZE = 1024;

    public Base64Utils() {
    }

    public static byte[] decode(String base64) throws Exception {
        return Base64.decode(base64.getBytes());
    }

    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.encode(bytes));
    }

    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        if (file.exists()) {
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream(2048);
                byte[] cache = new byte[1024];

                int nRead1;
                while ((nRead1 = in.read(cache)) != -1) {
                    out.write(cache, 0, nRead1);
                    out.flush();
                }
                data = out.toByteArray();
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        }

        return data;
    }

    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        ByteArrayInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new ByteArrayInputStream(bytes);
            File destFile = new File(filePath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            destFile.createNewFile();
            out = new FileOutputStream(destFile);
            byte[] cache = new byte[1024];

            int nRead1;
            while ((nRead1 = in.read(cache)) != -1) {
                out.write(cache, 0, nRead1);
                out.flush();
            }

        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }
}
