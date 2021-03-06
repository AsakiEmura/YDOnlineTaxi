package com.ruoyi.YDOnlineTaxi.utils;

import org.springframework.util.DigestUtils;


/**
 * MD5工具类
 *
 * @author pibigstar
 */
public class MD5Util {
    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";

    /**
     * 生成md5
     *
     * @return 加密之后的字符串
     */
    public static String getMD5(String str) {
        String base = str + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}


