package com.ruoyi.YDOnlineTaxi.utils;

import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListenerImpl {

    @OnTransition(source = "WAIT_DISPATCH", target = "WAIT_AUDIT")
    public boolean dispatchTransition(Message<OrderStatusChangeEvent> message) {
        OrderInformation orderInformation = (OrderInformation) message.getHeaders().get("orderInformation");
        orderInformation.setOrderStatus(OrderStatus.WAIT_AUDIT);
        System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_AUDIT", target = "WAIT_PAYMENT")
    public boolean auditTransition(Message<OrderStatusChangeEvent> message) {
        OrderInformation orderInformation = (OrderInformation) message.getHeaders().get("orderInformation");
        orderInformation.setOrderStatus(OrderStatus.WAIT_PAYMENT);
        System.out.println("发货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_PAYMENT", target = "FINISH")
    public boolean paymentTransition(Message<OrderStatusChangeEvent> message) {
        OrderInformation orderInformation = (OrderInformation) message.getHeaders().get("orderInformation");
        orderInformation.setOrderStatus(OrderStatus.FINISH);
        System.out.println("收货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }
}
