package com.yl.chat.conn;

import com.alibaba.fastjson.JSONObject;
import com.yl.chat.popj.WechatResult;
import com.yl.chat.result.MsgResult;
import com.yl.chat.result.SendStatus;
import com.yl.chat.result.UploadNews;

import java.util.List;
import java.util.TreeMap;


/**
 * 发送消息的工具类
 *
 * @author
 */
public class MessageConn extends Connection {
    // 上传图文消息素材的path
    private String uploadnewsPath = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
    // 按分组进行群发
    private String sendall = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
    // 按照openid进行群发消息
    private String sendByopenId = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
    // 删除群发消息
    private String deleteSendMsg = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";
    // 预览接口
    private String previewPath = "https://api.weixin.qq.com/cgi-bin/message/mass/preview";
    // 查询群发消息的发送状态
    private String getSendState = "https://api.weixin.qq.com/cgi-bin/message/mass/get";

    // 获取发送视频的media_id 注意此时与基础的media 不一致
    private String uploadVideoPath = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo";

    //**********************templet 消息*****************/
    //设置行业模板
    private String setTelepleApi = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry";
    //获取模板id
    private String addTemplate = "https://api.weixin.qq.com/cgi-bin/template/api_add_template";
    //发送模板消息
    private String sendTemplatemsg = "https://api.weixin.qq.com/cgi-bin/message/template/send";

		/*https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
*/

    /**
     * 设置模板
     *
     * @param accessToken 授权token
     * @param id1         模板类型 1
     * @param id2         模板类型 2
     * @return MsgResult.errcode==0&&MsgResult.errmsg==ok表示设置成功
     */
    public MsgResult setTempleate(String accessToken, String id1, String id2) {
        MsgResult resultObj = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        //post data
        TreeMap<String, String> dataParams = new TreeMap<String, String>();
        dataParams.put("industry_id1", id1);
        dataParams.put("industry_id2", id2);
        String data = JSONObject.toJSONString(dataParams);
        String result = HttpsDefaultExecute("POST", setTelepleApi, params, data);
        resultObj = ConverResult(result);
        return resultObj;
    }

    /**
     * 获得模板ID
     *
     * @param accessToken    授权token
     * @param templetShrotId 需在后台的模板消息中设置
     * @return MsgResult.errcode==0&&MsgResult.errmsg==ok,template_id模板消息的id 表示设置成功
     */
    public MsgResult getTemplateId(String accessToken, String templetShrotId) {
        MsgResult resultObj = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        //post data
        TreeMap<String, String> postParams = new TreeMap<String, String>();
        postParams.put("template_id_short", templetShrotId);
        String data = JSONObject.toJSONString(postParams);
        String result = HttpsDefaultExecute("POST", addTemplate, params, data);
        resultObj = ConverResult(result);
        return resultObj;
    }

    /**
     * 发送模板消息
     *
     * @param accessToken 授权token
     * @param data        发送的数据
     * @return
     */
    protected MsgResult sendTemplate(String accessToken, String data) {
        MsgResult resultObj = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        String result = HttpsDefaultExecute("POST", sendTemplatemsg, params, data);
        resultObj = ConverResult(result);
        return resultObj;
    }

    /**
     * 上传视频,获取对应的media_id  注意此时的media与基础的media不一致
     *
     * @param accessToken
     * @return UploadNews
     */
    public UploadNews uploadVideo(String accessToken, String data) {
        UploadNews news = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        String result = HttpsDefaultExecute("POST", uploadVideoPath, params, data);
        news = JSONObject.parseObject(result, UploadNews.class);
        return news;
    }

    /**
     * 获取群发消息的状态
     *
     * @param accessToken
     * @param msgId
     * @return
     */
    public SendStatus getSendState(String accessToken, String msgId) {
        SendStatus status = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        // post params
        TreeMap<String, String> dataParams = new TreeMap<String, String>();
        dataParams.put("msg_id", msgId);
        String data = JSONObject.toJSONString(dataParams);
        String result = HttpsDefaultExecute("POST", getSendState, params, data);
        status = JSONObject.parseObject(result, SendStatus.class);
        return status;
    }

    /**
     * 预览文本消息
     *
     * @param accessToken 授权token
     * @param openId      需要接收消息的OpenId
     * @param content     文本消息的内容
     */
    public MsgResult prepareText(String accessToken, String openId, String content) {
        MsgResult resultObj = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", openId);
        params.put("msgtype", "text");
        //content params
        TreeMap<String, String> contentParams = new TreeMap<String, String>();
        contentParams.put("content", content);
        params.put("text", contentParams);
        String data = JSONObject.toJSONString(params);
        String result = prepareView(accessToken, data);
        resultObj = ConverResult(result);
        return resultObj;
    }

    /**
     * 预览图文消息
     *
     * @param accessToken 授权token
     * @param openId      需要接收消息的OpenId
     * @param mediaId     预览图文消息 mediaId
     */
    public MsgResult prepareNews(String accessToken, String openId, String mediaId) {
        MsgResult resultObj = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", openId);
        params.put("msgtype", "mpnews");
        //content params
        TreeMap<String, String> contentParams = new TreeMap<String, String>();
        contentParams.put("media_id", mediaId);
        params.put("mpnews", contentParams);
        String data = JSONObject.toJSONString(params);
        String result = prepareView(accessToken, data);
        resultObj = ConverResult(result);
        return resultObj;
    }

    /**
     * 预览语言消息
     *
     * @param accessToken 授权token
     * @param openId      需要接收消息的OpenId
     * @param mediaId     预览语言消息 mediaId
     */
    public MsgResult prepareVoice(String accessToken, String openId, String mediaId) {
        MsgResult resultObj = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", openId);
        params.put("msgtype", "voice");
        //content params
        TreeMap<String, String> contentParams = new TreeMap<String, String>();
        contentParams.put("media_id", mediaId);
        params.put("voice", contentParams);
        String data = JSONObject.toJSONString(params);
        String result = prepareView(accessToken, data);
        resultObj = ConverResult(result);
        return resultObj;
    }

    /**
     * 预览图片消息
     *
     * @param accessToken 授权token
     * @param openId      需要接收消息的OpenId
     * @param mediaId     预览图片消息 mediaId
     */
    public MsgResult prepareImage(String accessToken, String openId, String mediaId) {
        MsgResult resultObj = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", openId);
        params.put("msgtype", "image");
        //content params
        TreeMap<String, String> contentParams = new TreeMap<String, String>();
        contentParams.put("media_id", mediaId);
        params.put("image", contentParams);
        String data = JSONObject.toJSONString(params);
        String result = prepareView(accessToken, data);
        resultObj = ConverResult(result);
        return resultObj;
    }

    /**
     * 预览视频消息
     *
     * @param accessToken 授权token
     * @param openId      需要接收消息的OpenId
     * @param mediaId     预览视频消息 mediaId
     */
    public MsgResult prepareVideo(String accessToken, String openId, String mediaId) {
        MsgResult resultObj = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", openId);
        params.put("msgtype", "mpvideo");
        //content params
        TreeMap<String, String> contentParams = new TreeMap<String, String>();
        contentParams.put("media_id", mediaId);
        params.put("mpvideo", contentParams);
        String data = JSONObject.toJSONString(params);
        String result = prepareView(accessToken, data);
        resultObj = ConverResult(result);
        return resultObj;
    }


    /**
     * 预览接口
     *
     * @param accessToken 授权token
     * @param data        预览的接口
     * @return
     */
    private String prepareView(String accessToken, String data) {
        String result = "";
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        result = HttpsDefaultExecute("POST", previewPath, params, data);
        return result;
    }

    /**
     * 删除群发消息
     *
     * @param accssToken 授权token
     * @param msgId      群发消息的id
     * @return { "errcode":0, "errmsg":"ok" }
     */
    public MsgResult deleteSend(String accessToken, String msgId) {
        MsgResult resutlObj = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        // POST params
        TreeMap<String, String> dataParams = new TreeMap<String, String>();
        dataParams.put("msg_id", msgId);
        String data = JSONObject.toJSONString(dataParams);
        String result = HttpsDefaultExecute("POST", deleteSendMsg, params, data);
        resutlObj = ConverResult(result);
        return resutlObj;
    }

    /**
     * 通过openid 群发 图文消息
     *
     * @param list    待接收消息的openid 集合
     * @param mediaId 图文消息的id
     * @return MsgResult 对象
     */
    public MsgResult sendByOpenIdOfNews(String accessToken, List<String> list, String mediaId) {
        MsgResult result = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", list);
        //list Params
        TreeMap<String, String> medaidParams = new TreeMap<String, String>();
        medaidParams.put("media_id", mediaId);
        params.put("mpnews", medaidParams);
        params.put("msgtype", "mpnews");
        String data = JSONObject.toJSONString(params, true);
        result = sendByOpenid(accessToken, data);
        return result;
    }

    /**
     * 通过 openid 群发文字消息
     *
     * @param accessToken 授权token
     * @param list        待接口消息的openId 集合
     * @param content     需要发送的文本内容
     * @return MsgResult
     */
    public MsgResult sendByOpenIdOfText(String accessToken, List<String> list, String content) {
        MsgResult result = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", list);
        //content Params
        TreeMap<String, String> contentParams = new TreeMap<String, String>();
        contentParams.put("content", content);
        //
        params.put("text", contentParams);
        params.put("msgtype", "text");
        String data = JSONObject.toJSONString(params, true);
        result = sendByOpenid(accessToken, data);
        return result;
    }

    /**
     * 通过 openid 群发语音消息
     *
     * @param accessToken 授权token
     * @param list        待接口消息的openId 集合
     * @param content     语言消息的mediaId
     * @return MsgResult
     */
    public MsgResult sendByOpenIdOfVoice(String accessToken, List<String> list, String mediaId) {
        MsgResult result = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", list);
        //list Params
        TreeMap<String, String> medaidParams = new TreeMap<String, String>();
        medaidParams.put("media_id", mediaId);
        params.put("voice", medaidParams);
        params.put("msgtype", "voice");
        String data = JSONObject.toJSONString(params, true);
        result = sendByOpenid(accessToken, data);
        return result;
    }

    /**
     * 通过 openid 群发图片消息
     *
     * @param accessToken 授权token
     * @param list        待接口消息的openId 集合
     * @param content     图片消息的mediaId
     * @return MsgResult
     */
    public MsgResult sendByOpenIdOfImage(String accessToken, List<String> list, String mediaId) {
        MsgResult result = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", list);
        //list Params
        TreeMap<String, String> medaidParams = new TreeMap<String, String>();
        medaidParams.put("media_id", mediaId);
        params.put("image", medaidParams);
        params.put("msgtype", "image");
        String data = JSONObject.toJSONString(params, true);
        result = sendByOpenid(accessToken, data);
        return result;
    }

    /**
     * 通过openid 群发
     *
     * @param accessToken 授权token
     * @param list        待接口消息的 openId 集合
     * @param mediaId     mediaId
     * @param title       图文消息的标题
     * @param description 图文消息的描述
     * @return
     */
    public MsgResult sendByOpendIdOfVideo(String accessToken, List<String> list, String mediaId, String title, String description) {
        MsgResult result = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("media_id", mediaId);
        params.put("title", title);
        params.put("description", description);
        //
        String dataParams = JSONObject.toJSONString(params);
        UploadNews news = uploadVideo(accessToken, dataParams);
        if (news != null) {
            TreeMap<String, Object> trparams = new TreeMap<String, Object>();
            trparams.put("touser", list);
            //list Params
            TreeMap<String, String> medaidParams = new TreeMap<String, String>();
            medaidParams.put("media_id", mediaId);
            medaidParams.put("title", title);
            medaidParams.put("description", description);
            trparams.put("video", medaidParams);
            trparams.put("msgtype", "video");
            String data = JSONObject.toJSONString(params, true);
            result = sendByOpenid(accessToken, data);
        }
        return result;
    }

    /**
     * 通过openid列表进行群发
     *
     * @param accessToken
     * @param data
     * @return
     */
    private MsgResult sendByOpenid(String accessToken, String data) {
        MsgResult msgResult = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        //
        String result = HttpsDefaultExecute("POST", sendByopenId, params, data);
        msgResult = ConverResult(result);
        return msgResult;
    }

    /**
     * 进行群组发送
     *
     * @param accessToken 授权token
     * @param data        需要发送的json格式的数据
     * @return
     */
    private MsgResult sendall(String accessToken, String data) {
        MsgResult msgResult = null;
        // url的参数
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        String result = HttpsDefaultExecute("POST", sendall, params, data);
        msgResult = ConverResult(result);
        return msgResult;
    }

    /**
     * 群发消息发送文本消息时的post数据
     *
     * @param is_to_all 值为true或false，选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
     * @param groupId   群发到的分组的group_id 如果is_to_all==true 则可不填
     * @param coentent  发送的文本的内容
     * @return MsgResult 对象
     */
    public MsgResult postSendallText(String accessToken, boolean is_to_all, int groupId, String content) {
        TreeMap<String, Object> obj = new TreeMap<String, Object>();
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("is_to_all", is_to_all);
        params.put("group_id", groupId);
        obj.put("filter", params);
        //
        TreeMap<String, Object> newsParasm = new TreeMap<String, Object>();
        newsParasm.put("content", content);
        obj.put("text", newsParasm);
        obj.put("msgtype", "text");
        //
        String jsonData = JSONObject.toJSONString(obj, false);
        MsgResult result = sendall(accessToken, jsonData);
        return result;
    }

    /**
     * 群组消息发送的是文本消息
     *
     * @param is_to_all 如果==true,则将该消息发送给所有用户,选择false将用户发送为groupid的数据
     * @param groupId   如果is_to_all==true则该参数可不填,如果为false 则为groupId
     * @param mediaId   多媒体id
     * @return MsgResult 对象
     */
    public MsgResult postSendallNews(String accessToken, boolean is_to_all, int groupId, String mediaId) {
        TreeMap<String, Object> obj = new TreeMap<String, Object>();
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("is_to_all", is_to_all);
        params.put("group_id", groupId);
        obj.put("filter", params);
        //
        TreeMap<String, Object> newsParasm = new TreeMap<String, Object>();
        newsParasm.put("media_id", mediaId);
        obj.put("mpnews", newsParasm);
        obj.put("msgtype", "mpnews");
        //
        String jsonData = JSONObject.toJSONString(obj, false);
        MsgResult result = sendall(accessToken, jsonData);
        return result;
    }

    /**
     * 群组发送语音消息
     *
     * @param accessToken 授权tokne
     * @param is_to_all   是否对所有用户发送  ==true表示向所有用户发送 ==false表示向groupid 发送
     * @param groupId     分组id
     * @param mediaId     语言消息的mediaid
     * @return
     */
    public MsgResult postSendAllVoice(String accessToken, boolean is_to_all, int groupId, String mediaId) {
        TreeMap<String, Object> obj = new TreeMap<String, Object>();
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("is_to_all", is_to_all);
        params.put("group_id", groupId);
        obj.put("filter", params);
        //
        TreeMap<String, Object> newsParasm = new TreeMap<String, Object>();
        newsParasm.put("media_id", mediaId);
        obj.put("voice", newsParasm);
        obj.put("msgtype", "voice");
        //
        String jsonData = JSONObject.toJSONString(obj, false);
        MsgResult result = sendall(accessToken, jsonData);
        return result;
    }

    /**
     * 群组发送图片消息
     *
     * @param accessToken 授权token
     * @param is_to_all   是否对所有用户发送  ==true表示向所有用户发送 ==false表示向groupid 发送
     * @param groupId     分组id
     * @param mediaId     图片消息的mediaId
     * @return
     */
    public MsgResult postSendAllImage(String accessToken, boolean is_to_all, int groupId, String mediaId) {
        TreeMap<String, Object> obj = new TreeMap<String, Object>();
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("is_to_all", is_to_all);
        params.put("group_id", groupId);
        obj.put("filter", params);
        //
        TreeMap<String, Object> newsParasm = new TreeMap<String, Object>();
        newsParasm.put("media_id", mediaId);
        obj.put("image", newsParasm);
        obj.put("msgtype", "image");
        //
        String jsonData = JSONObject.toJSONString(obj, false);
        MsgResult result = sendall(accessToken, jsonData);
        return result;
    }

    /**
     * 群发视频消息
     *
     * @param accessToken 授权token
     * @param is_to_all   是否发送全部用户  true表示发送全不用户 false表示发送给指定用户
     * @param gourpId     分组id
     * @param mediaId     基础结果中获取得到的消息mediaId
     * @param title       视频的标题
     * @param descrption  视频的描述
     * @return
     */
    public MsgResult postSendAllVideo(String accessToken, boolean is_to_all, int groupId, String mediaId, String title, String descrption) {
        MsgResult result = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("media_id", mediaId);
        params.put("title", title);
        params.put("description", descrption);
        String dataParams = JSONObject.toJSONString(params);
        UploadNews news = uploadVideo(accessToken, dataParams);
        if (news != null) {
            TreeMap<String, Object> obj = new TreeMap<String, Object>();
            TreeMap<String, Object> paramsObj = new TreeMap<String, Object>();
            paramsObj.put("is_to_all", is_to_all);
            paramsObj.put("group_id", groupId);
            obj.put("filter", params);
            //
            TreeMap<String, Object> newsParasm = new TreeMap<String, Object>();
            newsParasm.put("media_id", news.getMedia_id());
            obj.put("mpvideo", newsParasm);
            obj.put("msgtype", "mpvideo");
            //
            String jsonData = JSONObject.toJSONString(obj, false);
            result = sendall(accessToken, jsonData);
        }
        return result;
    }


    /**
     * 将上传图文素材返回的接口转换为对象
     *
     * @param jsonData json格式的字符串
     * @return result.isSuccess 则返回成功信息,可强制转换为UploadNews 对象
     */
    private WechatResult ConvertNews(String jsonData) {
        WechatResult result = new WechatResult();
        if (jsonData != null && !"".equals(jsonData)) {
            JSONObject json = JSONObject.parseObject(jsonData);
            if (json.containsKey("type") && json.containsKey("media_id")) {
                UploadNews news = JSONObject.parseObject(jsonData, UploadNews.class);
                result.setObj(news);
                result.setSuccess(true);
            } else {
                result.setObj(jsonData);
            }
        } else {
            result.setObj("Convert Content is null!");
        }
        return result;
    }

    /**
     * 将json格式的字符串转换为  MsgResult 对象
     *
     * @param data
     * @return
     */
    private MsgResult ConverResult(String data) {
        MsgResult result = null;
        if (data != null && !"".equals(data)) {
            result = JSONObject.parseObject(data, MsgResult.class);
        }
        return result;
    }

}
