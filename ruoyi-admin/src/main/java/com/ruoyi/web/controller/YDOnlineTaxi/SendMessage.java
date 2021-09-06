package com.ruoyi.web.controller.YDOnlineTaxi;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.YDOnlineTaxi.config.MessageConfig;
import com.ruoyi.YDOnlineTaxi.utils.MobileUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/YDOnlineTaxi/message")
public class SendMessage {
    @Autowired
    private RedisCache redisUtils;

    @Autowired
    private MobileUtil mobileUtil;

    /**
     * TODO penpen
     * 榛子平台，用于测试
     *
     * @param data 手机号
     * @return 结果
     */
    @PostMapping("/sendMessage")
    public Map<String, Object> send(@RequestBody Map<String, Object> data) {
        Map<String, Object> res = new HashMap<>();
        String phone;
        try {
            phone = data.get("phone").toString();
        }catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "30012");
            return res;
        }
        //手机号码校验
        boolean isMobile = MobileUtil.isPhoneLegal(phone);
        if (!isMobile) {
            res.put("msg", "30007");
            return res;
        }
        //间隔时间校验
        boolean send = mobileUtil.canSend(phone);
        if (!send) {
            res.put("msg", "30008");
            return res;
        }

        try {
            boolean isMore = mobileUtil.isTotalFive(phone);
            if (isMore) {
                res.put("msg", "30009");
                return res;
            }
            JSONObject json;
            //生成6位验证码
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            //发送短信
            ZhenziSmsClient client = new ZhenziSmsClient(MessageConfig.apiUrl, MessageConfig.appId, MessageConfig.appSecret);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phone);
            params.put("templateId", MessageConfig.templateId);
            String[] templateParams = {verifyCode, "10"};
            params.put("templateParams", templateParams);
            String result = client.send(params);
            json = JSONObject.parseObject(result);
            if ((int) json.get("code") == 0) {
                res.put("msg", "30010");
                //验证码存入redis,时间为10分钟
                //判断redis中是否有该用户手机号码key，有的话先删除
                if (redisUtils.hasKey(phone)) {
                    redisUtils.del(phone);
                }
                //存入redis并设置时间为10分钟
                redisUtils.sSetAndTime(phone, 60 * 10, verifyCode);
            } else {
                res.put("msg", "30011");
            }
        } catch (Exception e) {
            res.put("msg", "30011");
            e.printStackTrace();
        }
        return res;
    }
}
