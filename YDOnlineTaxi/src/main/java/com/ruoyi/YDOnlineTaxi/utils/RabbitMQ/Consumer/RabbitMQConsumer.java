package com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component

public class RabbitMQConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public void process(String content) {
        logger.info("接收处理队列A当中的消息： " + content);
    }

}
