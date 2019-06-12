package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;

import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by ruanxin on 2017/7/11.
 */
public class RpmVerifyService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RSASignUtil.class);

    /**
     * 验证加签字段
     *
     * @param signMap
     * @param service
     * @throws Exception
     */
    public void execute(Map<String, String> signMap, String service) throws Exception {
        if (service == null) {
            throw new ServiceNotSpecifiedException("The service is not specified");
        }

        SignFieldEnum signFieldEnum = SignFieldEnum.SignFieldMap.get(service);
        //必填字段
        String notEmptyfields = SignFieldEnum.COMMON + signFieldEnum.getNotEmptyFields();
        List<String> notEmptySignFields = Arrays.asList(notEmptyfields.split("\\|"));

        Map<String, String> notEmptySignFieldsMap = new HashMap<>();
        for (String field : notEmptySignFields) {
            notEmptySignFieldsMap.put(field, field);
        }
        //可空字段
        String emptyFields = signFieldEnum.getEmptyFields();
        List<String> emptySignFields = Arrays.asList(emptyFields.split("\\|"));
        Map<String, String> emptySignFieldsMap = new HashMap<>();
        for (String field : emptySignFields) {
            if (!("".equals(field) || field == null)) {
                emptySignFieldsMap.put(field, field);
            }
        }
        executeVerify(signMap, notEmptySignFieldsMap, emptySignFieldsMap);
    }

    /**
     * @param signMap        加签字段
     * @param notEmptyFields 非空字段
     * @param emptyFields    可空字段
     */
    public void executeVerify(Map<String, String> signMap, Map<String, String> notEmptyFields, Map<String, String> emptyFields) throws Exception {
        StringBuilder warnMessage = new StringBuilder();
        for (String ef : emptyFields.keySet()) {
            if (!signMap.containsKey(ef)) {
                warnMessage.append(ef).append("=?&");
                continue;
            }
            signMap.remove(ef);
        }
        if (warnMessage.length() > 0){
            String res = warnMessage.substring(0,warnMessage.length() - 1).toString();
            log.warn("The empty fields are not filled:[{}]", res);
        }

        List<String> message = new ArrayList<>();
        for (String nef : notEmptyFields.keySet()) {
            if (!signMap.containsKey(nef)) {
                message.add(nef);
                continue;
            }
            signMap.remove(nef);
        }
        Map<Integer, List<String>> messageMap = new HashMap<>();
        if (!message.isEmpty()) {
            messageMap.put(MrpException.MRPCODE, message);
        }
        //多传
        List<String> ext = new ArrayList<>();
        if (!signMap.isEmpty()) {
            for (String extFileds : signMap.keySet()) {
                ext.add(extFileds);
            }
            messageMap.put(MrpException.MPPCODE, ext);
        }
//        if (!messageMap.isEmpty()) {
//            throw new MrpException(messageMap);
//        }
    }
}
