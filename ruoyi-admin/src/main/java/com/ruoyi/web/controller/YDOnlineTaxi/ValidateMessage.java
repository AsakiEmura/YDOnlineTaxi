package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.utils.MD5Util;
import com.ruoyi.YDOnlineTaxi.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class ValidateMessage {
    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public Map<String, Object> validate(@RequestParam("code") String code,
                                        @RequestParam("phone") String phone) {
        Map<String, Object> res = new HashMap<>();
        //判断是否是第一次发送验证码没填写
        String encodeStr = "message" + "youdeng" + phone;
        String md5 = MD5Util.getMD5(encodeStr);
        if (!redisUtils.hasKey(phone) && !redisUtils.hasKey(md5)) {
            res.put("status", 404);
            res.put("msg", "请点击发送验证码");
            return res;
        }
        if (code.equals("")) {
            res.put("status", 407);
            res.put("msg", "请输入验证码");
            return res;
        }
        boolean hasKey = redisUtils.hasKey(phone);
        if (!hasKey) {
            res.put("status", 408);
            res.put("msg", "验证码过期");
            return res;
        }
        Set<Object> codes = redisUtils.sGet(phone);
        try {
            if (codes != null) {
                for (Object messageCode : codes) {
                    if (messageCode.equals(code)) {
                        res.put("status", 200);
                        res.put("msg", "验证成功");
                        redisUtils.del(phone);
                    } else {
                        res.put("status", 405);
                        res.put("msg", "验证码错误");
                    }
                    return res;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.put("status", 406);
        res.put("msg", "未知错误");
        return res;
    }
}
