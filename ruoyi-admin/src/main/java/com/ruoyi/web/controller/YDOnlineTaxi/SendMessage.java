package com.ruoyi.web.controller.YDOnlineTaxi;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.YDOnlineTaxi.config.MessageConfig;
import com.ruoyi.YDOnlineTaxi.utils.MobileUtil;
import com.ruoyi.YDOnlineTaxi.utils.RedisUtils;
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
    private RedisUtils redisUtils;

    @Autowired
    private MobileUtil mobileUtil;

    /**
     * 榛子平台，用于测试
     *
     * @param phone 手机号
     * @return 结果
     */
    @PostMapping("/sendMessage")
    public Map<String, Object> send(@RequestParam("phone") String phone) {
        Map<String, Object> res = new HashMap<>();
        //手机号码校验
        boolean isMobile = MobileUtil.isPhoneLegal(phone);
        if (!isMobile) {
            res.put("status", 401);
            res.put("msg", "手机号格式错误");
            return res;
        }
        //间隔时间校验
        boolean send = mobileUtil.canSend(phone);
        if (!send) {
            res.put("status", 403);
            res.put("msg", "发送间隔小于60秒");
            return res;
        }

        try {
            boolean isMore = mobileUtil.isTotalFive(phone);
            if (isMore) {
                res.put("status", 402);
                res.put("msg", "今日短信次数超过5次");
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
                res.put("msg", "发送成功");
                res.put("status", 200);
                //验证码存入redis,时间为10分钟
                //判断redis中是否有该用户手机号码key，有的话先删除
                if (redisUtils.hasKey(phone))
                    redisUtils.del(phone);
                //存入redis并设置时间为10分钟
                long count = redisUtils.sSetAndTime(phone, 60 * 10, verifyCode);
                if (count == 0)
                    res.put("error", "时间未知");
                else {
                    res.put("your_time", redisUtils.getExpire(phone));
                    res.put("success", "有效时间10分钟");
                }
            } else {
                res.put("msg", "发送失败");
                res.put("status", 400);
            }
        } catch (Exception e) {
            res.put("msg", "发送失败");
            res.put("status", 400);
            e.printStackTrace();
        }
        return res;
    }
}
