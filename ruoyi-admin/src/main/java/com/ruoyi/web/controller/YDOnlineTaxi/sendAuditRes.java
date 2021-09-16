package com.ruoyi.web.controller.YDOnlineTaxi;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


/**
 * @author Ma
 */
public class sendAuditRes {

    public static void main(String[] args) {
        getAccessToken("oLQhU5fwZctlTA19fhTxvVRBc9Po", "王鹏","330182200105034016", "不通过","因为王鹏是畜生");
    }

    /**
     * 静态方法：推送审核结果
     *
     * @param openid 微信唯一标识
     * @param name   司机姓名
     * @param idCard 司机身份证
     * @param status 审核结果
     * @param text   备注
     */
    public static void getAccessToken(String openid, String name, String idCard, String status, String text) {
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

        //构建发送内容 ----------------------------
        JSONObject messageInfo = new JSONObject();
        //姓名
        JSONObject pushName3 = new JSONObject();
        pushName3.put("value", name);
        //身份证
        JSONObject pushIdCard = new JSONObject();
        pushIdCard.put("value", idCard);
        //审核结果
        JSONObject pushStatus = new JSONObject();
        pushStatus.put("value", status);
        //备注
        JSONObject pushText = new JSONObject();
        pushText.put("value", text);

        messageInfo.put("name3", pushName3);
        messageInfo.put("character_string2", pushIdCard);
        messageInfo.put("thing1", pushStatus);
        messageInfo.put("thing4", pushText);
        JSONObject requestData = new JSONObject();
        //用户openid
        requestData.put("touser", openid);
        //模板消息
        requestData.put("template_id", "fYji7j2hXmaqnm9JgYQA_lS-2eNqig-KSpxaxlHykPA");
        //需要发送的内容
        requestData.put("data", messageInfo);
        String send_url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token;

        //构建请求头
        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestData.toJSONString(), headers);
        JSONObject jsonObject = restTemplate.postForObject(send_url, httpEntity, JSONObject.class);
        System.out.println(jsonObject);
    }
}
