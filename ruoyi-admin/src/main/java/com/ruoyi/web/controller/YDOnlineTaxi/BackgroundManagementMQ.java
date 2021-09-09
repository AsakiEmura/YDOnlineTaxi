package com.ruoyi.web.controller.YDOnlineTaxi;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/BackgroundManagementMQ")
public class BackgroundManagementMQ {

    private RabbitTemplate rabbitTemplate;
}
