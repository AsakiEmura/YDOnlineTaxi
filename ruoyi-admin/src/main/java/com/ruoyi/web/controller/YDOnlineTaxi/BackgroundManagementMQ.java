package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.utils.RabbitMQ.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Service
@RequestMapping("/BackgroundManagementMQ")
public class BackgroundManagementMQ {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("/wait_audit")
    public void waitAudit(@RequestBody Map<String, Object> data) {
        String driverPhoneNumber = null;
        try {
            driverPhoneNumber = data.get("driverPhoneNumber").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME, RabbitMQConfig.DIRECT_ROUTINGKEY_NAME, driverPhoneNumber + " 待审核,请刷新页面查看");
    }

}
