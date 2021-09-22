package com.ruoyi.YDOnlineTaxi.utils.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.ruoyi.YDOnlineTaxi.utils.SessionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.DIRECT_QUEUE_NAME)
    public void receive(Message message, Channel channel) throws IOException {
        LOGGER.info("消费内容为：{}", new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        SessionPool.sendMessage(new String(message.getBody()));
    }

}
