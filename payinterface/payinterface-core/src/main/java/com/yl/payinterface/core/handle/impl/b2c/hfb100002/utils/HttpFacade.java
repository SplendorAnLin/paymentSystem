package com.yl.payinterface.core.handle.impl.b2c.hfb100002.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Map;


/**
 * Http
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
abstract class HttpFacade {
    static final String METHOD_POST = "POST";
    static final String METHOD_GET = "GET";
    int DEFAULT_CONNECT_TIMEOUT = 18000;
    int DEFAULT_READ_TIMEOUT = 18000;
    protected String defaultCharset = "utf-8";


    /**
     * post 请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public abstract String post(String url, Map<Object, Object> params) throws Exception;

    /**
     * get 请求
     * @param url
     * @return
     * @throws Exception
     */
    public abstract String get(String url) throws Exception;



    String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (null == msg) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
    

    String getResponseCharset(String ctype) {
        String charset = defaultCharset;
        if (isNotNullOrEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (isNotNullOrEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public void setDefaultCharset(String defaultCharset){
        this.defaultCharset = defaultCharset;
    }
    
    private boolean isNullOrEmpty(String s){
    	return null == s || "".equals(s);
    }
    
    private boolean isNotNullOrEmpty(String s){
    	return !isNullOrEmpty(s);
    }

	public int getDEFAULT_CONNECT_TIMEOUT() {
		return DEFAULT_CONNECT_TIMEOUT;
	}

	public void setDEFAULT_CONNECT_TIMEOUT(int dEFAULT_CONNECT_TIMEOUT) {
		DEFAULT_CONNECT_TIMEOUT = dEFAULT_CONNECT_TIMEOUT;
	}

	public int getDEFAULT_READ_TIMEOUT() {
		return DEFAULT_READ_TIMEOUT;
	}

	public void setDEFAULT_READ_TIMEOUT(int dEFAULT_READ_TIMEOUT) {
		DEFAULT_READ_TIMEOUT = dEFAULT_READ_TIMEOUT;
	}
}