package com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.Consumer;

import com.rabbitmq.client.Channel;
import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

@Component

public class RabbitMQConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public void process(String content) {
        logger.debug("接收处理队列A当中的消息： " + content);
    }




}
