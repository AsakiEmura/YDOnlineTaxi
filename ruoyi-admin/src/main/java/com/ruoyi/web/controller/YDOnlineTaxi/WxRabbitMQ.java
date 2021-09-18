package com.ruoyi.web.controller.YDOnlineTaxi;

import com.rabbitmq.client.Channel;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.domain.WxWithDrivers;
import com.ruoyi.YDOnlineTaxi.service.IOrderInformationService;
import com.ruoyi.YDOnlineTaxi.service.WxWithDriversService;
import com.ruoyi.YDOnlineTaxi.utils.RSAEncrypt;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.Producer.RabbitMQProducer;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import com.ruoyi.YDOnlineTaxi.utils.WxService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class WxRabbitMQ {
    @Autowired
    private WxWithDriversService wxWithDriversService;

    @Autowired
    private IOrderInformationService orderInformationService;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<String> getOpenIdList(String driverLevel) {
        List<String> openIdList = wxWithDriversService.selectOpenIdByDriverLevel(driverLevel);
        try {
            for (String openId : openIdList) {
                WxWithDrivers wxWithDrivers = wxWithDriversService.selectAllByOpenId(openId);
                int times = wxWithDrivers.getPushTimes() - 1;
                wxWithDrivers.setPushTimes(times);
                wxWithDriversService.updateByOpenId(wxWithDrivers);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return openIdList;
    }

    @RabbitListener(queues = RabbitMQConfig.DELAY_QUEUE_NAME_KING)
    public void consumerK(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        //获取对应等级的openid列表,并且次数减一
        List<String> openIdList = getOpenIdList("王者");
        if (openIdList != null) {
            for (String openId : openIdList) {
                WxService.pushNotification(openId, new String(message.getBody()));
            }
            logger.debug("王者级司机已收到信息{}", msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            logger.error("王者级司机的个数为0,或者王者级司机没有推送次数大于1的");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.DELAY_QUEUE_NAME_DIAMOND)
    public void consumerD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        //获取对应等级的openid列表,并且次数减一
        List<String> openIdList = getOpenIdList("钻石");
        if (openIdList != null) {
            for (String openId : openIdList) {
                WxService.pushNotification(openId, msg);
            }
            logger.debug("钻石级司机已收到信息{}", msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            logger.error("钻石级司机的个数为0,或者钻石级司机没有推送次数大于1的");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }

    }

    @RabbitListener(queues = RabbitMQConfig.DELAY_QUEUE_NAME_GOLD)
    public void consumerG(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        //获取对应等级的openid列表,并且次数减一
        List<String> openIdList = getOpenIdList("黄金");
        if (openIdList != null) {
            for (String openId : openIdList) {
                String openIdDe = "";
                try {
                    openIdDe = RSAEncrypt.decrypt(openId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ("".equals(openIdDe))
                    return;
                WxService.pushNotification(openIdDe, new String(message.getBody()));
            }
            logger.debug("黄金级司机已收到信息{}", msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            logger.error("黄金级司机的个数为0,或者黄金级司机没有推送次数大于1的");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.DELAY_QUEUE_NAME_DEAD)
    public void consumerDead(Message message, Channel channel) throws IOException {
        //获取对应等级的openid列表,并且次数减一
        String orderId = new String(message.getBody());
        OrderInformation orderInformation = orderInformationService.selectOrderInformationByOrderId(orderId);
        //获取订单当前状态
        String orderStatus = orderInformation.getOrderStatus();
        //待派单,已重新派,已超时单代表当前已超时
        if (orderStatus.equals("待派单") || orderStatus.equals("已重新派单") || orderStatus.equals("已超时")) {
            orderInformation.setOrderStatus("已超时");
            if (orderInformation.getExpireTime() > 30) {
                orderInformation.setExpireTime(orderInformation.getExpireTime() + 1);
            } else {
                orderInformation.setExpireTime(30);
            }
            orderInformationService.updateOrderInformation(orderInformation);
            rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME_EXPIRED, RabbitMQConfig.DELAY_ROUTINGKEY_NAME_EXPIRED, orderId, 60 * 1000);

        } else if (orderStatus.equals("重新派单")) {
            orderInformation.setOrderStatus("已重新派单");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            rabbitMQProducer.sendMsg(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_QUEUE_NAME_DEAD, orderId, 40 * 60 * 1000);
        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}