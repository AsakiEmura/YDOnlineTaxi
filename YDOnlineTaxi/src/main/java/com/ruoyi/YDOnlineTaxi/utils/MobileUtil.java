package com.ruoyi.YDOnlineTaxi.utils;

import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

//手机号码验证工具类

@RestController
public class MobileUtil {
    @Autowired
    private RedisCache redisUtils;
    /**
     * ^ 匹配输入字符串开始的位置
     * \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
     * $ 匹配输入字符串结尾的位置
     */
    private static final Pattern HK_PATTERN = Pattern.compile("^(5|6|8|9)\\d{7}$");
    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
    private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]+");

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = CHINA_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {

        Matcher m = HK_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否是正整数的方法
     */
    public static boolean isNumeric(String string) {
        return NUM_PATTERN.matcher(string).matches();
    }

    //获取今天剩余秒数
    public static long getDayRemainingTime() {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
        long overTime = 0;
        try {
            overTime = (now - (sdfOne.parse(sdfOne.format(now)).getTime())) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return overTime;
    }
    //设置每次发送间隔60秒
    public boolean canSend(String phone) {
        String md5 = MD5Util.getMD5(phone);
        boolean hasKey = redisUtils.hasKey(md5);
        if (!hasKey) {
            redisUtils.sSetAndTime(md5,60,"youdeng");
            return true;
        }
        return false;
    }
    /**
     * 检查该手机号一天发送短信的次数，防止恶意发送
     *
     * @param phone 手机号
     * @return 是否超过5次
     */
    public boolean isTotalFive(String phone) {
        String encodeStr = "message" + "youdeng" + phone;
        String md5 = MD5Util.getMD5(encodeStr);
        boolean hasKey = redisUtils.hasKey(md5);
        if (!hasKey) {
            //设置今天使用短信一次
            redisUtils.sSetAndTime(md5, getDayRemainingTime(), 1);
            return false;
        }
        //有值的情况
        Set<Object> counts = redisUtils.sGet(md5);
        int phoneNum = 0;
        if (counts != null) {
            for (Object count : counts) {
                phoneNum = (int) count;
                //超过5次
                if (phoneNum >= 5) {
                    return true;
                }
                break;
            }
            //没有超过5次,次数加一
            redisUtils.del(md5);
            redisUtils.sSetAndTime(md5, getDayRemainingTime(), ++phoneNum);
            return false;
        }
        return true;//其他异常情况都收不到验证码
    }
}
