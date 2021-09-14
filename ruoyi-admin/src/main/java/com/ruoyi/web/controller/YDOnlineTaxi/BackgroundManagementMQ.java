package com.ruoyi.web.controller.YDOnlineTaxi;

import com.ruoyi.YDOnlineTaxi.Properties.MQProperties;
import com.ruoyi.common.core.domain.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired(required = false)
    private MQProperties mqProperties;


    @RequestMapping("/wait_audit")
    public void waitAudit(@RequestBody Map<String, Object> data) {
        String driverPhoneNumber = null;
        try {
            driverPhoneNumber = data.get("driverPhoneNumber").toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend(mqProperties.getDefaultExchange(), mqProperties.getRouteKey(), driverPhoneNumber + " 待审核,请刷新页面查看");
    }

}
