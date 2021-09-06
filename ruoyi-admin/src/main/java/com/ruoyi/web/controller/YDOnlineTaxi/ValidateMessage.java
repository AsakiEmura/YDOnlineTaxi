package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.utils.MD5Util;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/YDOnlineTaxi/message")
/*
TODO penpen
*/
public class ValidateMessage {
    @Autowired
    RedisCache redisUtils;

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public Map<String, Object> validate(@RequestBody Map<String, Object> data) {
        Map<String, Object> res = new HashMap<>();
        String code;
        String phone = data.get("phone").toString();
        try {
            code = data.get("code").toString();
        }
        catch ( Exception e ) {
            e.printStackTrace();
            res.put("msg", "30006");
            return res;
        }

        //判断是否是第一次发送验证码没填写
        String encodeStr = "message" + "youdeng" + phone;
        String md5 = MD5Util.getMD5(encodeStr);
        if (!redisUtils.hasKey(phone) && !redisUtils.hasKey(md5)) {
            res.put("msg", "30005");
            return res;
        }
        if (code.equals("")) {
            res.put("msg", "30004");
            return res;
        }
        boolean hasKey = redisUtils.hasKey(phone);
        if (!hasKey) {
            res.put("msg", "30003");
            return res;
        }
        Set<Object> codes = redisUtils.sGet(phone);
        try {
            if (codes != null) {
                for (Object messageCode : codes) {
                    if (messageCode.equals(code)) {
                        res.put("msg", "30000");
                        redisUtils.del(phone);
                    } else {
                        res.put("msg", "30001");
                    }
                    return res;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.put("msg", "30002");
        return res;
    }
}
