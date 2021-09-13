package com.ruoyi.YDOnlineTaxi.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mq")
@PropertySource(value = {"classpath:RabbitMQConfig.yml"})
public class MQProperties {
    private String defaultExchange;
    private String routeKey;
    private String queue;


    public String getDefaultExchange() {
        return defaultExchange;
    }

    @Value("${defaultExchange}")
    public void setDefaultExchange(String defaultExchange) {
        this.defaultExchange = defaultExchange;
    }

    public String getRouteKey() {
        return routeKey;
    }

    @Value("${routeKey}")
    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getQueue() {
        return queue;
    }

    @Value("${queue}")
    public void setQueue(String queue) {
        this.queue = queue;
    }
}
