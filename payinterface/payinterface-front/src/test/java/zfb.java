import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.front.utils.AESUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class zfb {

    public static void main(String[] args) {
//        Map<String, String> req = new HashMap<>();
//        req.put("content", "100");
//        req.put("timestamp", String.valueOf(System.currentTimeMillis()));
//        req = AESUtils.encryption("7DBEE48EA606E45D5EAFB71D8E6DE37F", JsonUtils.toJsonString(req), "13001157766@163.com");
//
//        try {
//            HttpClientUtils.send(HttpClientUtils.Method.POST, "http://pay.feiyijj.com/payinterface-front/collectionCodeNotify/complete", req, true, "UTF-8");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }

    public static void getFiles(String path) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文     件：" + tempList[i]);
            }
        }
    }
}