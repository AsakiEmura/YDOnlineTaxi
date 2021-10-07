package com.ruoyi.YDOnlineTaxi.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * 极光推送工具类
 *
 * @Author: XHM
 */
public class JPushUtils {
    /**
     * 推送给设备标识参数的用户
     * @param registrationId 设备id标识
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     */
    public static void sendToRegistrationId(List<String> registrationId, String notification_title, String msg_title, String msg_content) {
        JPushClient jPushClient = new JPushClient("ed580a48e069d5330622b7ba","f7ddd3a23e68c965cfba7b09");
        try {
            PushPayload pushPayload= JPushUtils.buildPushObject_all_alias_alertWithTitle(registrationId,notification_title,msg_title,msg_content);
            jPushClient.sendPush(pushPayload);
            //TODO 写日志
        } catch (APIConnectionException | APIRequestException e) {
            //TODO 写日志
            e.printStackTrace();
        }

    }
    private static PushPayload buildPushObject_all_alias_alertWithTitle(List<String> registrationId, String notification_title, String msg_title, String msg_content) {
        return PushPayload.newBuilder()
                //指定要推送的平台
                .setPlatform(Platform.android())
                //指定推送的接收对象通过id区分
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(msg_content)
                                .setTitle(notification_title)
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .build())
                .setOptions(Options.newBuilder()
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    public static void main(String[] args) {
        List<String> registrationIds = new ArrayList<>();
        registrationIds.add("170976fa8a46ce9a6e8");
        sendToRegistrationId(registrationIds,"订单导入通知","订单导入通知","今天大批量订单导入，请查看");
    }
}
