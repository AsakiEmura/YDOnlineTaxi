package com.ruoyi.YDOnlineTaxi.utils.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.ruoyi.YDOnlineTaxi.utils.SessionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired(required = false)
    private MQProperties mqProperties;


    public void waitAudit(String driverPhoneNumber) {
        rabbitTemplate.convertAndSend(mqProperties.getDefaultExchange(), mqProperties.getRouteKey(), driverPhoneNumber + ",不用审核");
    }

    @RabbitListener(queues = "${mq.queue}")
    public void receive(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        LOGGER.info("消费内容为：{}", payload);
        RabbitMQUtils.askMessage(channel, tag, LOGGER);
        SessionPool.sendMessage(payload);
    }

}
