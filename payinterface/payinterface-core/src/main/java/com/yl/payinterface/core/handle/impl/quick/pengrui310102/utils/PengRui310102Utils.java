package com.yl.payinterface.core.handle.impl.quick.pengrui310102.utils;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 全时优服 综合工具
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/27
 */
public class PengRui310102Utils {

    private static Logger logger = LoggerFactory.getLogger(PengRui310102Utils.class);

    private static String[] address = ("重庆大厦,黑龙江路,十梅庵街,遵义路,湘潭街,瑞金广场,仙山街,仙山东路,仙山西大厦,白沙河路,赵红广场,机场路," +
            "民航街,长城南路,流亭立交桥,虹桥广场,长城大厦,礼阳路,风岗街,中川路,白塔广场,兴阳路,文阳街,绣城路,河城大厦,锦城广场,崇阳街,华城路," +
            "康城街,正阳路,和阳广场,中城路,江城大厦,顺城路,安城街,山城广场,春城街,国城路,泰城街,德阳路,明阳大厦,春阳路,艳阳街,秋阳路,硕阳街," +
            "青威高速,瑞阳街,丰海路,双元大厦,惜福镇街道,夏庄街道,古庙工业园,中山街,太平路,广西街,潍县广场,博山大厦,湖南路,济宁街,芝罘路,易州广场," +
            "荷泽四路,荷泽二街,荷泽一路,荷泽三大厦,观海二广场,广西支街,观海一路,济宁支街,莒县路,平度广场,明水路,蒙阴大厦,青岛路,湖北街,江宁广场," +
            "郯城街,天津路,保定街,安徽路,河北大厦,黄岛路,北京街,莘县路,济南街,宁阳广场,日照街,德县路,新泰大厦,荷泽路,山西广场,沂水路,肥城街," +
            "兰山路,四方街,平原广场,泗水大厦,浙江路,曲阜街,寿康路,河南广场,泰安路,大沽街,红山峡支路,西陵峡一大厦,台西纬一广场,台西纬四街,台西纬二路," +
            "西陵峡二街,西陵峡三路,台西纬三广场,台西纬五路,明月峡大厦,青铜峡路,台西二街,观音峡广场,瞿塘峡街,团岛二路,团岛一街,台西三路,台西一大厦," +
            "郓城南路,团岛三街,刘家峡路,西藏二街,西藏一广场,台西四街,三门峡路,城武支大厦,红山峡路,郓城北广场,龙羊峡路,西陵峡街,台西五路,团岛四街," +
            "石村广场,巫峡大厦,四川路,寿张街,嘉祥路,南村广场,范县路,西康街,云南路,巨野大厦,西江广场,鱼台街,单县路,定陶街,滕县路,钜野广场,观城路," +
            "汶上大厦,朝城路,滋阳街,邹县广场,濮县街,磁山路,汶水街,西藏路,城武大厦,团岛路,南阳街,广州路,东平街,枣庄广场,贵州街,费县路,南海大厦,登州路," +
            "文登广场,信号山支路,延安一街,信号山路,兴安支街,福山支广场,红岛支大厦,莱芜二路,吴县一街,金口三路,金口一广场,伏龙山路,鱼山支街,观象二路," +
            "吴县二大厦,莱芜一广场,金口二街,海阳路,龙口街,恒山路,鱼山广场,掖县路,福山大厦,红岛路,常州街,大学广场,龙华街,齐河路,莱阳街,黄县路,张店大厦," +
            "祚山路,苏州街,华山路,伏龙街,江苏广场,龙江街,王村路,琴屿大厦,齐东路,京山广场,龙山路,牟平街,延安三路,延吉街,南京广场,东海东大厦,银川西路," +
            "海口街,山东路,绍兴广场,芝泉路,东海中街,宁夏路,香港西大厦,隆德广场,扬州街,郧阳路,太平角一街,宁国二支路,太平角二广场,天台东一路,太平角三大厦," +
            "漳州路一路,漳州街二街,宁国一支广场,太平角六街,太平角四路,天台东二街,太平角五路,宁国三大厦,澳门三路,江西支街,澳门二路,宁国四街,大尧一广场," +
            "咸阳支街,洪泽湖路,吴兴二大厦,澄海三路,天台一广场,新湛二路,三明北街,新湛支路,湛山五街,泰州三广场,湛山四大厦,闽江三路,澳门四街,南海支路,吴兴三广场," +
            "三明南路,湛山二街,二轻新村镇,江南大厦,吴兴一广场,珠海二街,嘉峪关路,高邮湖街,湛山三路,澳门六广场,泰州二路,东海一大厦,天台二路,微山湖街,洞庭湖广场," +
            "珠海支街,福州南路,澄海二街,泰州四路,香港中大厦,澳门五路,新湛三街,澳门一路,正阳关街,宁武关广场,闽江四街,新湛一路,宁国一大厦,王家麦岛,澳门七广场," +
            "泰州一路,泰州六街,大尧二路,青大一街,闽江二广场,闽江一大厦,屏东支路,湛山一街,东海西路,徐家麦岛函谷关广场,大尧三路,晓望支街,秀湛二路,逍遥三大厦," +
            "澳门九广场,泰州五街,澄海一路,澳门八街,福州北路,珠海一广场,宁国二路,临淮关大厦,燕儿岛路,紫荆关街,武胜关广场,逍遥一街,秀湛四路,居庸关街,山海关路," +
            "鄱阳湖大厦,新湛路,漳州街,仙游路,花莲街,乐清广场,巢湖街,台南路,吴兴大厦,新田路,福清广场,澄海路,莆田街,海游路,镇江街,石岛广场,宜兴大厦,三明路," +
            "仰口街,沛县路,漳浦广场,大麦岛,台湾街,天台路,金湖大厦,高雄广场,海江街,岳阳路,善化街,荣成路,澳门广场,武昌路,闽江大厦,台北路,龙岩街,咸阳广场,宁德街," +
            "龙泉路,丽水街,海川路,彰化大厦,金田路,泰州街,太湖路,江西街,泰兴广场,青大街,金门路,南通大厦,旌德路,汇泉广场,宁国路,泉州街,如东路,奉化街,鹊山广场," +
            "莲岛大厦,华严路,嘉义街,古田路,南平广场,秀湛路,长汀街,湛山路,徐州大厦,丰县广场,汕头街,新竹路,黄海街,安庆路,基隆广场,韶关路,云霄大厦,新安路,仙居街," +
            "屏东广场,晓望街,海门路,珠海街,上杭路,永嘉大厦,漳平路,盐城街,新浦路,新昌街,高田广场,市场三街,金乡东路,市场二大厦,上海支路,李村支广场,惠民南路," +
            "市场纬街,长安南路,陵县支街,冠县支广场,小港一大厦,市场一路,小港二街,清平路,广东广场,新疆路,博平街,港通路,小港沿,福建广场,高唐街,茌平路,港青街," +
            "高密路,阳谷广场,平阴路,夏津大厦,邱县路,渤海街,恩县广场,旅顺街,堂邑路,李村街,即墨路,港华大厦,港环路,馆陶街,普集路,朝阳街,甘肃广场,港夏街,港联路," +
            "陵县大厦,上海路,宝山广场,武定路,长清街,长安路,惠民街,武城广场,聊城大厦,海泊路,沧口街,宁波路,胶州广场,莱州路,招远街,冠县路,六码头,金乡广场,禹城街," +
            "临清路,东阿街,吴淞路,大港沿,辽宁路,棣纬二大厦,大港纬一路,贮水山支街,无棣纬一广场,大港纬三街,大港纬五路,大港纬四街,大港纬二路,无棣二大厦,吉林支路," +
            "大港四街,普集支路,无棣三街,黄台支广场,大港三街,无棣一路,贮水山大厦,泰山支路,大港一广场,无棣四路,大连支街,大港二路,锦州支街,德平广场,高苑大厦,长山路," +
            "乐陵街,临邑路,嫩江广场,合江路,大连街,博兴路,蒲台大厦,黄台广场,城阳街,临淄路,安邱街,临朐路,青城广场,商河路,热河大厦,济阳路,承德街,淄川广场,辽北街," +
            "阳信路,益都街,松江路,流亭大厦,吉林路,恒台街,包头路,无棣街,铁山广场,锦州街,桓台路,兴安大厦,邹平路,胶东广场,章丘路,丹东街,华阳路,青海街,泰山广场,周村大厦," +
            "四平路,台东西七街,台东东二路,台东东七广场,台东西二路,东五街,云门二路,芙蓉山村,延安二广场,云门一街,台东四路,台东一街,台东二路,杭州支广场,内蒙古路," +
            "台东七大厦,台东六路,广饶支街,台东八广场,台东三街,四平支路,郭口东街,青海支路,沈阳支大厦,菜市二路,菜市一街,北仲三路,瑞云街,滨县广场,庆祥街,万寿路,大成大厦," +
            "芙蓉路,历城广场,大名路,昌平街,平定路,长兴街,浦口广场,诸城大厦,和兴路,德盛街,宁海路,威海广场,东山路,清和街,姜沟路,雒口大厦,松山广场,长春街,昆明路,顺兴街," +
            "利津路,阳明广场,人和路,郭口大厦,营口路,昌邑街,孟庄广场,丰盛街,埕口路,丹阳街,汉口路,洮南大厦,桑梓路,沾化街,山口路,沈阳街,南口广场,振兴街,通化路,福寺大厦," +
            "峄县路,寿光广场,曹县路,昌乐街,道口路,南九水街,台湛广场,东光大厦,驼峰路,太平山,标山路,云溪广场,太清路").split(",");

    private static String[] provinceCity = {"北京市,北京市,", "江苏省,南京市,", "浙江省,金华市,", "福建省,厦门市,", "山东省,烟台市,", "河南省,信阳市,", "湖北省,武汉市,",
            "湖北省,恩施土家族苗族自治州,", "湖南省,长沙市,", "广东省,深圳市,", "广西,桂林市,", "重庆市,重庆市,", "四川省,成都市,", "贵州省,六盘水市,", "陕西省,延安市,",
            "广东省,茂名市,", "湖南省,湘潭市,", "江西省,上饶市,", "浙江省,嘉兴市,", "内蒙古,呼和浩特市,"};

    private static String[] bankInfo = {"102,ICBC", "103,ABC", "104,BOC", "105,CCB", "201,CDB", "202,EXIMBANK", "203,ADBC", "301,BCOM",
            "302,CITIC", "303,CEB", "304,HXB", "305,CMBC", "306,CGB", "307,PAB", "308,CMB", "309,CIB", "310,SPDB", "313,BIN", "315,EGBANK",
            "316,CZB", "403,PSBC", "501,HSBC", "502,HKBEA", "503,NCBCHINA", "504,HANGSENG", "783,SDB", "905,UNIONPAY", "317,BOB", "318,HCCB",
            "319,NJCB", "314,BRCB"};

    public static JSONObject execute(String key, String url, String api, String dataJson, String partnerNo, String orderId) {
        logger.info("订单号：{}, api:{}, 请求报文:{}", orderId, api, dataJson);
        String signKey = key.substring(16);
        String dataKey = key.substring(0, 16);
        String sign = DigestUtils.shaHex(dataJson + signKey);
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("encryptData", encode(PengRui310102AESUtils.encode(dataJson, dataKey)));
            params.put("signData", sign);
            params.put("orderId", orderId);
            params.put("partnerNo", partnerNo);
            params.put("ext", "EZPZ");
            logger.info("请求报文:{}", JsonUtils.toJsonString(params));
            String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, url + api, params, false, "UTF-8", 300000);
            logger.info("返回报文密文:{}", respInfo);
            JSONObject res = JSONObject.fromObject(respInfo);
            String result = PengRui310102AESUtils.decode(decode(res.getString("encryptData")), dataKey);
            logger.info("返回报文明文:{}", result);
            String signature = res.getString("signature");
            String reSign = DigestUtils.shaHex(result + signKey);
            if (signature != null && signature.equals(reSign)) {
                return JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            logger.info("订单号：{},api:{},下单报错:{}", orderId, api, e);
        }
        return null;
    }

    /**
     * 银行代码匹配
     */
    public static String[] getBankInfo(String bankCode, String bankName){
        String[] info = {"905", "UNIONPAY", "中国银联"};
        String[] res = null;
        for (int i = 0; i < bankInfo.length ; i++) {
            res = bankInfo[i].split(",");
            if (bankCode.equals(res[1])){
                info = new String[]{res[0], res[1], bankName};
            }
        }
        return info;
    }


    /**
     * Token 获取
     */
    public static String getToken(String tokenType, String api, String url, String key, String partnerNo){
        String token = "";
        String orderId = getOrderIdByUUIdTwo();
        Date reqDate = new Date();
        JSONObject dto = new JSONObject();
        dto.put("head", head(reqDate, orderId, api, partnerNo));
        dto.put("tokenType", tokenType);
        JSONObject res = execute(key, url, api, dto.toString(), partnerNo, orderId);
        if (res.containsKey("head") && res.getJSONObject("head").getString("respCode").equals("000000")){
            JSONObject head = res.getJSONObject("head");
            token = res.getString("token");
        }
        return token;
    }

    /**
     * 报文头组装
     *
     * @param now
     * @param orderId
     * @param txnCode
     * @param partnerNo
     * @return
     */
    public static JSONObject head(Date now, String orderId, String txnCode, String partnerNo) {
        JSONObject head = new JSONObject();
        head.put("version", "1.0.0");
        head.put("charset", "UTF-8");
        head.put("partnerNo", partnerNo);
        head.put("txnCode", txnCode);
        head.put("orderId", orderId);
        head.put("reqDate", formatDate(now, "yyyyMMdd"));
        head.put("reqTime", formatDate(now, "yyyyMMddHHmmss"));
        return head;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * UUID
     *
     * @return
     */
    public static String getOrderIdByUUId() {
        int machineId = 6;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return machineId + String.format("%011d", hashCodeV);
    }

    /**
     * 查询订单号获取
     *
     * @return
     */
    public static String getOrderIdByUUIdTwo() {
        String order = "";
        order = getOrderIdByUUId();
        order += getOrderIdByUUId();
        return order;
    }

    /**
     * 使用Base64加密算法加密字符串 return
     */
    public static String encode(byte[] plainBytes) {
        byte[] b = plainBytes;
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        b = base64.encode(b);
        String s = new String(b, StandardCharsets.UTF_8);
        return s;
    }

    /**
     * 使用Base64解密算法解密字符串 return
     */
    public static byte[] decode(String encodeStr) {
        byte[] b = encodeStr.getBytes(StandardCharsets.UTF_8);
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        b = base64.decode(b);
        return b;
    }

    /**
     * 获取随机地址
     *
     * @return res[0] 省份 res[1]城市 res[2]地址
     */
    public static String[] getAddr() {
        int index = getNum(0, address.length - 1);
        String city = provinceCity[getNum(0, provinceCity.length - 1)];
        String first = address[index];
        String second = String.valueOf(getNum(11, 150)) + "号";
        String third = getNum(1, 20) + "-" + getNum(1, 10);
        String res = city + first + second + third;
        return res.split(",");
    }

    /**
     * 订单号处理
     */
    public static String getInterfaceRequestId(String requestId) {
        requestId = requestId.replaceAll("TD", "");
        return requestId.replaceAll("-", "");
    }

    /**
     * 订单号还原
     */
    public static String resInterfaceRequestId(String requestId) {
        String q = requestId.substring(8);
        String h = requestId.substring(0, 8);
        return "TD-" + h + "-" + q;
    }

    /**
     * 随机数获取
     */
    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }
}