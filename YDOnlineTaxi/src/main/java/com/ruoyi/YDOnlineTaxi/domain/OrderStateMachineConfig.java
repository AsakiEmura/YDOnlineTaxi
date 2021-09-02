package com.ruoyi.YDOnlineTaxi.domain;

import com.ruoyi.YDOnlineTaxi.utils.OrderStatus;
import com.ruoyi.YDOnlineTaxi.utils.OrderStatusChangeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * 订单状态机配置
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {
    /**
     * 配置状态
     *
     * @param states
     * @throws Exception
     */
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_DISPATCH)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态转换事件关系
     *
     * @param transitions
     * @throws Exception
     */
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStatus.WAIT_DISPATCH).target(OrderStatus.WAIT_AUDIT).event(OrderStatusChangeEvent.DISPATCHED)
                .and()
                .withExternal().source(OrderStatus.WAIT_AUDIT).target(OrderStatus.WAIT_PAYMENT).event(OrderStatusChangeEvent.AUDITED)
                .and()
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.FINISH).event(OrderStatusChangeEvent.PAYED);
    }

    /**
     * 持久化配置
     * 实际使用中，可以配合redis等，进行持久化操作
     *
     * @return
     */
    @Bean
    public DefaultStateMachinePersister persister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object, Object, OrderInformation>() {
            @Override
            public void write(StateMachineContext<Object, Object> context, OrderInformation orderInformation) throws Exception {
                //此处并没有进行持久化操作
            }

            @Override
            public StateMachineContext<Object, Object> read(OrderInformation orderInformation) throws Exception {
                //此处直接获取order中的状态，其实并没有进行持久化读取操作
                return new DefaultStateMachineContext(orderInformation.getOrderStatus(), null, null, null);
            }
        });
    }
}
