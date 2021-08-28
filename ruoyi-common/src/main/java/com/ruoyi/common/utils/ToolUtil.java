package com.ruoyi.common.utils;

import java.util.Random;

public class ToolUtil {

    /**
     * 获取随机字符,自定义长度
     *
     * @author zmj
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
