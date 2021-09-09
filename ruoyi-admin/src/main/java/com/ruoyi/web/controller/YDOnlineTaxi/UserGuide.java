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

/**
 * @author penpen
 */
@RestController
@RequestMapping("/YDOnlineTaxi/OrderUserGuide")
public class UserGuide {
    @Autowired
    private RedisCache redisUtils;

    /**
     * TODO penpen
     * 用户须知
     * @return 用户须知
     */
    @GetMapping("/getUserGuide")
    public String getUserGuide() {
        Map<String, Object> res = new HashMap<>();
        String guide;
        String key = "user";
        try {
            if (redisUtils.hasKey(key)) {
                guide = redisUtils.sGet(key).toString();
                return guide.substring(1, guide.length()-1);
            }else {
                return "请存入用户须知";
            }
        } catch (Exception e) {
            //存储失败
            res.put("msg", "40021");
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/updataUserGuide")
    public Map<String, Object> updataUserGuide(String data) {
        Map<String, Object> res = new HashMap<>();
        String guide;
        String key = "user";
        try {
            guide = data;
        }catch (Exception e) {
            e.printStackTrace();
            //输入数据错误
            res.put("msg", "40020");
            return res;
        }

        try {
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            //存入redis
            redisUtils.sSet(key, guide);
        } catch (Exception e) {
            //存储失败
            res.put("msg", "40021");
            e.printStackTrace();
        }
        return res;
    }
}
