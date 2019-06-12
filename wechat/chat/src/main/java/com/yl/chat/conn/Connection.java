package com.yl.chat.conn;

import com.alibaba.fastjson.JSONObject;
import com.yl.chat.popj.AccessToken;
import com.yl.chat.popj.JsonResult;
import com.yl.chat.popj.WechatResult;
import com.yl.chat.utils.ConvertJsonUtils;
import com.yl.chat.utils.MyX509TrustManager;
import com.yl.chat.utils.WStringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Connection {

    private static int default_connTime = 5000;
    private static int default_readTime = 5000;
    private static int default_upload_readTime = 10 * 1000;
    protected static String default_charset = "utf-8";
    // 获取access_token的路径
    private String token_path = "https://api.weixin.qq.com/cgi-bin/token";
    // 获取微信服务器ip
    private String backIp_path = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
    // 上传多媒体的path
    private String up_media_path = "http://file.api.weixin.qq.com/cgi-bin/media/upload";

    /**
     * http请求
     *
     * @param method      请求方法GET/POST
     * @param path        请求路径
     * @param timeout     连接超时时间 默认为5000
     * @param readTimeout 读取超时时间 默认为5000
     * @param data        数据
     * @return
     */
    public static String defaultConnection(String method, String path, int timeout,
                                           int readTimeout, String data) throws Exception {
        String result = "";
        URL url = new URL(path);
        if (url != null) {
            HttpURLConnection conn = getConnection(method, url);
            conn.setConnectTimeout(timeout == 0 ? default_connTime : timeout);
            conn.setReadTimeout(readTimeout == 0 ? default_readTime
                    : readTimeout);
            if (data != null && !"".equals(data)) {
                OutputStream output = conn.getOutputStream();
                output.write(data.getBytes(default_charset));
                output.flush();
                output.close();
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                result = inputToStr(input);
                input.close();
                conn.disconnect();
            }
        }
        return result;
    }

    /**
     * 根据url的协议选择对应的请求方式 例如 http://www.baidu.com 则使用http请求,https://www.baidu.com
     * 则使用https请求
     *
     * @param method 请求的方法
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(String method, URL url)
            throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext context = null;
            try {
                context = SSLContext.getInstance("SSL", "SunJSSE");
                context.init(new KeyManager[0],
                        new TrustManager[]{new MyX509TrustManager()},
                        new java.security.SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(context.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

    /**
     * 上传网络图片
     *
     * @param method    请求方法 GET/POST
     * @param path      api的路径
     * @param mediaPath 待上传的image/voide/music 的path
     * @param connTime  连接时间 默认为5000
     * @param readTime  读取时间 默认为5000
     * @param data      POST提交的数据
     * @return
     */
    public String HttpUploadMedia(String method, String path, String mediaPath,
                                  int connTime, int readTime, String data) throws Exception {
        String result = "";
        URL url = new URL(path);
        if (url != null) {
            String boundary = "----";
            HttpURLConnection conn = getConnection(method, url);
            conn.setConnectTimeout(connTime == 0 ? default_connTime : connTime);
            conn.setReadTimeout(readTime == 0 ? default_upload_readTime
                    : readTime);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            OutputStream output = conn.getOutputStream();
            URL mediaUrl = new URL(mediaPath);
            if (mediaUrl != null) {
                HttpURLConnection mediaConn = (HttpURLConnection) mediaUrl
                        .openConnection();
                mediaConn.setDoOutput(true);
                mediaConn.setRequestMethod("GET");
                mediaConn.setConnectTimeout(connTime == 0 ? default_connTime
                        : connTime);
                mediaConn
                        .setReadTimeout(readTime == 0 ? default_upload_readTime
                                : readTime);
                String connType = mediaConn.getHeaderField("Content-Type");
                // 获得文件扩展String fileExt = getFileExt(connType);
                String fileExt = getFileExt(connType);
                output.write(("--" + boundary + "\r\n").getBytes());
                output.write(("Content-Disposition: form-data; name=\"media\"; filename=\""
                        + getFileName(mediaPath) + "\"\r\n").getBytes());
                output.write(("Content-Type: " + fileExt + "\r\n\r\n")
                        .getBytes());
                BufferedInputStream inputStream = new BufferedInputStream(
                        mediaConn.getInputStream());
                byte[] array = new byte[8076];
                int size = 0;
                while ((size = (inputStream.read(array))) != -1) {
                    output.write(array, 0, size);
                }
                output.write(("\r\n----" + boundary + "--\r\n").getBytes());
                output.close();
                inputStream.close();
                mediaConn.disconnect();
                // 获取输入流
                InputStream input = conn.getInputStream();
                result = inputToStr(input);
            }
        }
        return result;
    }

    /**
     * 默认的下载素材方法
     *
     * @param method   http方法 POST/GET
     * @param apiPath  api路径
     * @param savePath 素材需要保存的路径
     * @return 是否下载成功 Reuslt.success==true 表示下载成功
     */
    public WechatResult downMeaterMetod(TreeMap<String, String> params,
                                        String method, String apiPath, String savePath) {
        WechatResult result = new WechatResult();
        try {
            apiPath = setParmas(params, apiPath, "");
            URL url = new URL(apiPath);
            HttpURLConnection conn = getConnection(method, url);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String contentType = conn.getContentType();
                result = ContentType(contentType, conn, savePath);
            } else {
                result.setObj(conn.getResponseCode() + " "
                        + conn.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据返回的头信息返回具体信息
     *
     * @param contentType ContentType请求头信息
     * @return Result.type==1 表示文本消息,
     */
    private WechatResult ContentType(String contentType, HttpURLConnection conn,
                                     String savePath) {
        WechatResult result = new WechatResult();
        try {
            if (conn != null) {
                InputStream input = conn.getInputStream();
                if (contentType.equals("image/gif")) { // gif图片
                    result = InputStreamToMedia(input, savePath, "gif");
                } else if (contentType.equals("image/jpeg")) { // jpg图片
                    result = InputStreamToMedia(input, savePath, "jpg");
                } else if (contentType.equals("image/jpg")) { // jpg图片
                    result = InputStreamToMedia(input, savePath, "jpg");
                } else if (contentType.equals("image/png")) { // png图片
                    result = InputStreamToMedia(input, savePath, "png");
                } else if (contentType.equals("image/bmp")) { // bmp图片
                    result = InputStreamToMedia(input, savePath, "bmp");
                } else if (contentType.equals("audio/x-wav")) { // wav语音
                    result = InputStreamToMedia(input, savePath, "wav");
                } else if (contentType.equals("audio/x-ms-wma")) { // wma语言
                    result = InputStreamToMedia(input, savePath, "wma");
                } else if (contentType.equals("audio/mpeg")) { // mp3语言
                    result = InputStreamToMedia(input, savePath, "mp3");
                } else if (contentType.equals("text/plain")) { // 文本信息
                    String str = inputToStr(input);
                    result.setObj(str);
                } else if (contentType.equals("application/json")) { // 返回json格式的数据
                    String str = inputToStr(input);
                    result.setObj(str);
                } else { // 此处有问题
                    String str = inputToStr(input);
                    result.setObj(str);
                }
            } else {
                result.setObj("conn is null!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 将字符流转换为图片文件
     *
     * @param input    字符流
     * @param savePath 图片需要保存的路径
     * @param 类型       jpg/png等
     * @return
     */
    private WechatResult InputStreamToMedia(InputStream input, String savePath,
                                            String type) {
        WechatResult result = new WechatResult();
        try {
            File file = null;
            file = new File(savePath);
            String paramPath = file.getParent(); // 路径
            String fileName = file.getName(); //
            String newName = fileName.substring(0, fileName.lastIndexOf("."))
                    + "." + type;// 根据实际返回的文件类型后缀
            savePath = paramPath + "\\" + newName;
            if (!file.exists()) {
                File dirFile = new File(paramPath);
                dirFile.mkdirs();
            }
            file = new File(savePath);
            FileOutputStream output = new FileOutputStream(file);
            int len = 0;
            byte[] array = new byte[1024];
            while ((len = input.read(array)) != -1) {
                output.write(array, 0, len);
            }
            output.flush();
            output.close();
            result.setSuccess(true);
            result.setObj("save success!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setObj(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setObj(e.getMessage());
        }
        return result;
    }

    /**
     * 上传本地图片
     *
     * @param method
     *            请求的方法 POST/GET
     * @param path
     *            api路径
     * @param mediaPath
     *            媒体的本地路径, 例如: E:\test.jpg
     * @param connTime
     *            请求连接最长时间
     * @param readTime
     *            返回数据最长时间
     * @param data
     *            post提交的数据
     * @return
     */
    /*
	 * public String uploadNativeImage(String method,String path,String
	 * mediaPath,int connTime,int readTime,String data){ String result =""; try
	 * { URL url = new URL(path); String boundary = "----"; if(url!=null){
	 * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 * conn.setDoInput(true); conn.setDoOutput(true); conn.setUseCaches(false);
	 * conn.setRequestMethod(method);
	 * conn.setConnectTimeout(connTime==0?default_connTime:connTime);
	 * conn.setReadTimeout(readTime==0?default_readTime:readTime); OutputStream
	 * output = conn.getOutputStream(); File file = new File(mediaPath);
	 * if(file.exists()){ FileInputStream inputStrem = new
	 * FileInputStream(file); StringBuffer buffer = new StringBuffer(); byte[]
	 * array = new byte[1024]; int len = 0;
	 * while((len=inputStrem.read(array))!=-1){
	 * 
	 * } }else{ result="file not's exist"; } } } catch (MalformedURLException e)
	 * { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * return result; }
	 */

    /**
     * 默认上传媒体的方法
     *
     * @param method    上次方法 默认为GET
     * @param path      api路径
     * @param mediaPath 图片路径
     * @param data      输出参数
     * @return
     */
    public String defaultUploadImg(String urlPath, TreeMap<String, String> map,
                                   String method, String mediaPath, String data) {
        String result = "";
        try {
            String url = setParmas((TreeMap<String, String>) map, urlPath, "");
            result = HttpUploadMedia(
                    (method == null || "".equals(method)) ? "GET" : method,
                    url, mediaPath, default_connTime, default_upload_readTime,
                    data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 上传多媒体消息的基础类()
     *
     * @param map       参数集合
     * @param method    请求的方法, GET/POST
     * @param mediaPath 待上次多媒体的路径
     * @param data      post传送的参数
     * @return
     */
    public String BasicUploadImg(TreeMap<String, String> map, String method,
                                 String mediaPath, String data) {
        String result = "";
        try {
            String url = setParmas((TreeMap<String, String>) map,
                    up_media_path, "");
            result = HttpUploadMedia(
                    (method == null || "".equals(method)) ? "GET" : method,
                    url, mediaPath, default_connTime, default_upload_readTime,
                    data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 上传素材的基本方法
     *
     * @param params    url中的需要添加的参数集合
     * @param method    请求的方法 post/get 等
     * @param apiPath   api的路径,有上传临时素材,有上传永久素材,该值不一样
     * @param mediaPath 多媒体上传的路径
     * @param data      post需要提交的参数
     * @return 上传是否成功返回的json格式字符串
     */
    public String uploadMediaMethod(TreeMap<String, String> params,
                                    String method, String apiPath, String mediaPath, String data) {
        String reuslt = "";
        try {
            String url = setParmas(params, apiPath, "");
            method = (method == null || "".equals(method) ? "GET" : method);
            reuslt = HttpUploadMedia(method, url, mediaPath, default_connTime,
                    default_upload_readTime, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reuslt;
    }

    /**
     * 将输入流转换为字符串
     *
     * @param input 输入流
     * @return 转换后的字符串
     */
    public static String inputToStr(InputStream input) {
        String result = "";
        if (input != null) {
            byte[] array = new byte[1024];
            StringBuffer buffer = new StringBuffer();
            try {
                for (int index; (index = (input.read(array))) > -1; ) {
                    buffer.append(new String(array, 0, index, default_charset));
                }
                result = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = "";
            }
        }
        return result;
    }

    /**
     * 设置参数
     *
     * @param map     参数map
     * @param path    需要赋值的path
     * @param charset 编码格式 默认编码为utf-8
     * @return 已经赋值好的url 只需要访问即可
     */
    public static String setParmas(Map<String, String> map, String path, String charset)
            throws Exception {
        String result = "";
        boolean hasParams = false;
        if (path != null && !"".equals(path)) {
            if (map != null && map.size() > 0) {
                StringBuilder builder = new StringBuilder();
                Set<Entry<String, String>> params = map.entrySet();
                for (Entry<String, String> entry : params) {
                    String key = entry.getKey().trim();
                    String value = entry.getValue().trim();
                    if (hasParams) {
                        builder.append("&");
                    } else {
                        hasParams = true;
                    }
                    charset = (charset != null && !"".equals(charset) ? charset : default_charset);
                    builder.append(key).append("=").append(URLDecoder.decode(value, charset));
                }
                result = builder.toString();
            }
        }
        // 此时的url待改进
        return doUrlPath(path, result).toString();
    }

    /**
     * 设置连接参数
     *
     * @param path 路径
     * @return
     */
    private static URL doUrlPath(String path, String query) throws Exception {
        URL url = new URL(path);
        if (WStringUtils.StringIsEmpty(path)) {
            return url;
        }
        if (WStringUtils.StringIsEmpty(url.getQuery())) {
            if (path.endsWith("?")) {
                path += query;
            } else {
                path = path + "?" + query;
            }
        } else {
            if (path.endsWith("&")) {
                path += query;
            } else {
                path = path + "&" + query;
            }
        }
        return new URL(path);
    }

    /**
     * 默认的http请求执行方法,返回
     *
     * @param method 请求的方法 POST/GET
     * @param path   请求path 路径
     * @param map    请求参数集合
     * @param data   输入的数据 允许为空
     * @return
     */
    public static String HttpDefaultExecute(String method, String path,
                                            Map<String, String> map, String data) {
        String result = "";
        try {
            String url = setParmas((TreeMap<String, String>) map, path, "");
            result = defaultConnection(method, url, default_connTime,
                    default_readTime, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 默认的https执行方法,返回
     *
     * @param method 请求的方法 POST/GET
     * @param path   请求path 路径
     * @param map    请求参数集合
     * @param data   输入的数据 允许为空
     * @return
     */
    public String HttpsDefaultExecute(String method, String path,
                                      Map<String, String> map, String data) {
        String result = "";
        try {
            String url = setParmas((TreeMap<String, String>) map, path, "");
            result = defaultConnection(method, url, default_connTime, default_readTime, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取授权token
     *
     * @param key    应用key
     * @param secret 应用密匙
     * @return json格式的字符串
     */
    public String getAccessToken(String key, String secret) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("grant_type", "client_credential");
        map.put("appid", key);
        map.put("secret", secret);
        String result = HttpDefaultExecute("GET", token_path, map, "");
        return result;
    }


    /**
     * 获取授权信息
     *
     * @param key    appkey
     * @param secret secret
     * @return result.success==true时,result.obj为accessToken对象 否则为 result.obj=错误提示信息
     * result.msg= 原始返回数据
     */
    public WechatResult getAccessTokenObject(String key, String secret) {
        WechatResult result = new WechatResult();
        String jsonData = getAccessToken(key, secret);
        JSONObject object = JSONObject.parseObject(jsonData);
        if (object.containsKey("access_token")) {
            AccessToken token = ConvertJsonUtils.jsonToJavaObject(jsonData, AccessToken.class);
            result.setSuccess(true);
            result.setObj(token);
        } else {
            result.setObj(jsonData);
        }
        result.setMsg(jsonData);
        return result;
    }

    /**
     * 获取微信服务ip地址
     *
     * @param token
     * @return
     */
    public String getCallBackIp(String token) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("access_token", token);
        String result = HttpDefaultExecute("GET", backIp_path, map, "");
        return result;
    }

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param ContentType 内容类型
     * @return
     */
    private String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType)) {
            fileExt = ".jpg";
        } else if ("audio/mpeg".equals(contentType)) {
            fileExt = ".mp3";
        } else if ("audio/amr".equals(contentType)) {
            fileExt = ".amr";
        } else if ("video/mp4".equals(contentType)) {
            fileExt = ".mp4";
        } else if ("video/mpeg4".equals(contentType)) {
            fileExt = ".mp4";
        } else if ("image/png".equals(contentType)) {
            fileExt = ".png";
        }

        return fileExt;
    }

    /**
     * 文件路径
     *
     * @param mediaUrl url 例如
     *                 http://su.bdimg.com/static/superplus/img/logo_white_ee663702
     *                 .png
     * @return logo_white_ee663702.png
     */
    private String getFileName(String mediaUrl) {
        String result = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1,
                mediaUrl.length());
        return result;
    }

    /**
     * 将json格式的字符串转换为Result
     *
     * @param jsonResult 如果result.success=true result.obj==AccessToken 否则为错误提示信息
     * @return result.msg 为最原始的json格式数据
     */
    public static WechatResult ConvertToken(String jsonResult) {
        WechatResult result = new WechatResult();
        if (jsonResult != null && !"".equals(jsonResult)) {
            JSONObject json = JSONObject.parseObject(jsonResult);
            if (json.containsKey("access_token") && json.containsKey("expires_in")) { // 表示成功!
                AccessToken token = JSONObject.parseObject(jsonResult, AccessToken.class);
                result.setObj(token);
                result.setSuccess(true);
            } else {
                result.setObj(jsonResult);
            }
        }
        result.setMsg(jsonResult);
        return result;
    }

    /**
     * 将json字符串转换为JsonResult对象
     *
     * @param jsonResult
     * @return
     */
    public JsonResult ConvertResult(String jsonResult) {
        JsonResult result = null;
        if (jsonResult != null && !"".equals(jsonResult)) {
            JSONObject jsonObj = JSONObject.parseObject(jsonResult);
            if (jsonObj.containsKey("errcode") && jsonObj.containsKey("errmsg")) { // success
                result = JSONObject.toJavaObject(jsonObj, JsonResult.class);
            }
        }
        return result;
    }
}
