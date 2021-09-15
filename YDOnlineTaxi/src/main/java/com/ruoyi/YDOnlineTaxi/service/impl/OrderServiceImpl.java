package com.ruoyi.YDOnlineTaxi.service.impl;

import com.ruoyi.YDOnlineTaxi.domain.OrderInformation;
import com.ruoyi.YDOnlineTaxi.service.OrderService;
import com.ruoyi.YDOnlineTaxi.constant.enums.OrderStatus;
import com.ruoyi.YDOnlineTaxi.constant.enums.OrderStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, OrderInformation> persister;

    private OrderInformationServiceImpl orderInformationService;


    private Map<String, OrderInformation> orders = new HashMap<>();

    public OrderInformation create() {
        OrderInformation order = new OrderInformation();
        order.setOrderStatus(OrderStatus.WAIT_DISPATCHED.toString());
        order.setOrderId(orderInformationService.randomID());
        orders.put(order.getOrderId(), order);
        return order;
    }

    public OrderInformation dispatch(String orderId) {
        OrderInformation order = orders.get(orderId);
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.DISPATCHED).setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 支付失败, 状态异常，订单号：" + orderId);
        }
        return orders.get(orderId);
    }

    public OrderInformation audit(String orderId) {
        OrderInformation order = orders.get(orderId);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试发货，订单号：" + orderId);
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.AUDITED).setHeader("order", order).build(), orders.get(orderId))) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 发货失败，状态异常，订单号：" + orderId);
        }
        return orders.get(orderId);
    }

    public OrderInformation payment(String orderId) {
        OrderInformation order = orders.get(orderId);

        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED).setHeader("order", order).build(), orders.get(orderId))) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 收货失败，状态异常，订单号：" + orderId);
        }
        return orders.get(orderId);
    }

    public Map<String, OrderInformation> getOrders() {
        return orders;
    }

    /**
     * 发送订单状态转换事件
     *
     * @param message
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(Message<OrderStatusChangeEvent> message, OrderInformation order) {
        boolean result = false;
        try {
            orderStateMachine.start();
            //尝试恢复状态机状态
            persister.restore(orderStateMachine, order);
            //添加延迟用于线程安全测试
            Thread.sleep(1000);
            result = orderStateMachine.sendEvent(message);
            //持久化状态机状态
            persister.persist(orderStateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}
