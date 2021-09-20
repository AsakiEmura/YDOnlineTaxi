package com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.Producer;

import com.rabbitmq.client.Channel;
import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import com.ruoyi.common.utils.uuid.UUID;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public final class RabbitMQProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        /**
         * * true：
         * * 交换机无法将消息进行路由时，会将该消息返回给生产者
         * * false：
         * * 如果发现消息无法进行路由，则直接丢弃
         * */
        rabbitTemplate.setMandatory(true); //设置回退消息交给谁处理
        rabbitTemplate.setReturnCallback(this);
    }

    private final com.sun.org.slf4j.internal.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.debug("消息{},被交换机{}退回，退回原因:{},路由key:{}", new String(message.getBody()), exchange, replyText, routingKey);
    }


    /**
     * 交换机确认回调方法
     * 1.发消息,交换机接收到了进行回调
     * 1.1 correlationData 保存回调消息的ID及其相关消息
     * 1.2交换机收到消息 true
     * 1.3 reason null
     * 2.发消息,交换机接收失败了回调
     * 2.1 correlationData 保存回调消息的ID及相关信息
     * 2.2 交换机收到消息 False
     * 2.3 reason 没收到的原因
     *
     * @param correlationData
     * @param ack
     * @param reason
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String reason) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            logger.debug("交换机已收到ID为:{}的消息", id);
        } else {
            logger.debug("交换机未到ID为:{}的消息", id);
        }

    }

    public static void askMessage(Channel channel, long tag, final org.slf4j.Logger logger) {
        askMessage(channel, tag, logger, false);
    }

    public static void askMessage(Channel channel, long tag, final org.slf4j.Logger logger, boolean multiple) {
        try {
            channel.basicAck(tag, multiple);
        } catch (IOException e) {
            logger.error("RabbitMQ，IO异常，异常原因为：" + e.getMessage());
        }
    }

    public static void rejectMessage(Channel channel, long tag, final org.slf4j.Logger logger) {
        rejectMessage(channel, tag, logger, false, false);
    }

    public static void rejectAndBackMQ(Channel channel, long tag, final org.slf4j.Logger logger) {
        rejectMessage(channel, tag, logger, false, true);
    }

    public static void rejectMessage(Channel channel, long tag, final org.slf4j.Logger logger, boolean multiple, boolean request) {
        try {
            channel.basicNack(tag, multiple, request);
        } catch (IOException e) {
            logger.error("RabbitMQ，IO异常，异常原因为：{}", e.getMessage());
        }
    }

    public void sendMsg(String exchangeName,String routingKey,String message,Integer delayTime) {
        CorrelationData correlationData = new CorrelationData(UUID.fastUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, msg ->{
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }
}