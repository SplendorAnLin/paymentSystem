package com.yl.payinterface.core.test;

import com.yl.payinterface.core.BaseTest;
import com.yl.payinterface.core.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class CommonTest extends BaseTest {

    @Test
    public void recognition() {
        System.out.println(CommonUtils.recognition("6215583202002031321"));
    }

    @Test
    public void cnapsCode() {
        System.out.println(CommonUtils.cnapsCode("ICBC"));
    }

    public static void main(String[] args) throws MalformedURLException {
        BufferedReader in = null;
        URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?location=30.607908,114.265442&output=json&ak=G6esLnML8F2BwhzezstPt2MRzL0IkgRG&pois=0");
        try {
            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            System.out.println(str);
            ObjectMapper mapper = new ObjectMapper();
            if (StringUtils.isNotEmpty(str)) {
                JsonNode jsonNode = mapper.readTree(str);
                jsonNode.findValue("status").toString();
                JsonNode resultNode = jsonNode.findValue("result");
                JsonNode locationNode = resultNode.findValue("formatted_address");
                System.out.println(locationNode);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}