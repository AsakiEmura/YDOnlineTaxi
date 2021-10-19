package com.ruoyi.YDOnlineTaxi.utils.RabbitMQ;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


/**
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    //管理员专用
    public static final String DIRECT_EXCHANGE_NAME = "direct.exchange";
    public static final String DIRECT_QUEUE_NAME = "direct.queue.admin";
    public static final String DIRECT_ROUTINGKEY_NAME = "direct.routingkey";

    //超时订单专用
    public static final String DELAY_EXCHANGE_NAME_EXPIRED = "delayed.exchange.expired";
    public static final String DELAY_ROUTINGKEY_NAME_EXPIRED = "delayed.routingkey.expired";

    //微信小程序推送订单转用
    public static final String DELAY_EXCHANGE_NAME = "delayed.exchange";

    public static final String DELAY_QUEUE_NAME_GOLD = "delayed.queue.gold";
    public static final String DELAY_QUEUE_NAME_DIAMOND = "delayed.queue.diamond";
    public static final String DELAY_QUEUE_NAME_KING = "delayed.queue.king";
    public static final String DELAY_QUEUE_NAME_DEAD = "delayed.queue.dead";


    public static final String DELAY_ROUTINGKEY_NAME_G = "delayed.routingkey.gold";
    public static final String DELAY_ROUTINGKEY_NAME_D = "delayed.routingkey.diamond";
    public static final String DELAY_ROUTINGKEY_NAME_K = "delayed.routingkey.king";
    public static final String DELAY_ROUTINGKEY_NAME_DEAD = "delayed.routingkey.dead";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template;
        template = new RabbitTemplate(connectionFactory());
        return template;
    }

    @Bean
    public CustomExchange expiredExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "topic");
        /**
         * 1.交换机名称
         * 2.交换机类型
         * 3.是否需要持久化
         * 4.是否需要自动删除
         * 5.其他参数
         */
        return new CustomExchange(DELAY_EXCHANGE_NAME_EXPIRED, "x-delayed-message", false, false, args);
    }

    @Bean
    public Binding bindingExpired(@Qualifier("queue_Dead") Queue queue_Dead, @Qualifier("expiredExchange") CustomExchange expiredExchange) {
        return BindingBuilder
                .bind(queue_Dead)
                .to(expiredExchange)
                .with(RabbitMQConfig.DELAY_ROUTINGKEY_NAME_EXPIRED)
                .noargs();
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "topic");
        /**
         * 1.交换机名称
         * 2.交换机类型
         * 3.是否需要持久化
         * 4.是否需要自动删除
         * 5.其他参数
         */
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue queue_G() {
        return new Queue(DELAY_QUEUE_NAME_GOLD, true); //队列持久
    }

    @Bean
    public Queue queue_D() {
        return new Queue(DELAY_QUEUE_NAME_DIAMOND, true); //队列持久
    }

    @Bean
    public Queue queue_K() {
        return new Queue(DELAY_QUEUE_NAME_KING, true); //队列持久
    }

    @Bean
    public Queue queue_Dead() {
        return new Queue(DELAY_QUEUE_NAME_DEAD, true); //队列持久
    }


    @Bean
    public Binding bindingG(@Qualifier("queue_G") Queue queue_G, @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder
                .bind(queue_G)
                .to(delayedExchange)
                .with(RabbitMQConfig.DELAY_ROUTINGKEY_NAME_G)
                .noargs();
    }

    @Bean
    public Binding bindingD(@Qualifier("queue_D") Queue queue_D, @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder
                .bind(queue_D)
                .to(delayedExchange)
                .with(RabbitMQConfig.DELAY_ROUTINGKEY_NAME_D)
                .noargs();
    }

    @Bean
    public Binding bindingK(@Qualifier("queue_K") Queue queue_K, @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder
                .bind(queue_K)
                .to(delayedExchange)
                .with(RabbitMQConfig.DELAY_ROUTINGKEY_NAME_K)
                .noargs();
    }

    @Bean
    public Binding bindingDead(@Qualifier("queue_Dead") Queue queue_Dead, @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder
                .bind(queue_Dead)
                .to(delayedExchange)
                .with(RabbitMQConfig.DELAY_ROUTINGKEY_NAME_DEAD)
                .noargs();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE_NAME, true);
    }

    @Bean
    public Binding bindingDirect(@Qualifier("directQueue") Queue directQueue, @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder
                .bind(directQueue)
                .to(directExchange)
                .with(RabbitMQConfig.DIRECT_ROUTINGKEY_NAME);
    }

//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        //加载处理消息A的队列
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        //设置接收多个队列里面的消息，这里设置接收队列A
//        // 假如想一个消费者处理多个队列里面的信息可以如下设置：
//        // container.setQueues(queueA(),queueB(),queueC());
//        container.setQueues(queue_D(), queue_K(), queue_G());
//        container.setExposeListenerChannel(true);
//        //设置最大的并发的消费者数量
//        container.setMaxConcurrentConsumers(10);
//        //最小的并发消费者的数量
//        container.setConcurrentConsumers(1);
//        //设置确认模式手工确认
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                /**
//                 * 通过basic.qos方法设置prefetch_count=1，
//                 * 这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
//                 * 换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它
//                 * */
//                channel.basicQos(1);
//                byte[] body = message.getBody();
//                logger.debug("接收处理队列A当中的消息:" + new String(body));
//                /**
//                 * 为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
//                 * 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，
//                 * 然后RabbitMQ才会将消息删除。
//                 * */
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        });
//        return container;
//    }
}