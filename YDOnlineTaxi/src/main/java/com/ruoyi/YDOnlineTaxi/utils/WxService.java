package com.ruoyi.YDOnlineTaxi.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WxService {

    /**
     * 静态方法：给微信用户发送消息推送
     * @param openid 微信用户唯一标识
     * @param msg 发送的内容
     */
    public static void pushNotification(String openid, String msg) {
        //获取access_token
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx44a2da7b0d95d7e5&secret=b3258e011e2871232cab0d551e3bc600";
        JSONObject res = restTemplate.getForObject(url, JSONObject.class);
        String access_token;
        if (res != null) {
            access_token = (String) res.get("access_token");
        } else {
            return;
        }

        //发送post请求
        //构建发送内容
        JSONObject messageInfo = new JSONObject();
        //推送内容
        JSONObject pushThing2 = new JSONObject();
        pushThing2.put("value", msg);
        //推送时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String now_date = simpleDateFormat.format(date);
        JSONObject pushTime4 = new JSONObject();

        messageInfo.put("thing2", pushThing2);
        pushTime4.put("value", now_date);
        messageInfo.put("time4", pushTime4);
        JSONObject requestData = new JSONObject();
        //用户openid
        requestData.put("touser", openid);
        //模板消息
        requestData.put("template_id", "p2LNan5cw8gCYg8ddzXj-NC8cJkxJKYBnzu8AZC3Mlo");
        //需要发送的内容
        requestData.put("data", messageInfo);
        String send_url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token;

        //构建请求头
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestData.toJSONString(), headers);
        restTemplate.postForObject(send_url, httpEntity, JSONObject.class);
    }
}
