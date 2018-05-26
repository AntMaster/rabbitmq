package pers.zhangyu.rabbitmq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.zhangyu.rabbitmq.enums.RabbitMqEnum;

import java.util.UUID;

/**
 * rabbitmq发送消息工具类
 *
 * @author chenhf
 * @create 2017-10-26 上午11:10
 **/

@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    private RabbitTemplate rabbitTemplate;

    /**
     * 如果需要在生产者需要消息发送后的回调，需要对rabbitTemplate设置ConfirmCallback对象，
     * 由于不同的生产者需要对应不同的ConfirmCallback，如果rabbitTemplate设置为单例bean，则所有的rabbitTemplate
     * 实际的ConfirmCallback为最后一次申明的ConfirmCallback。
     * <p>
     * <p>
     * <p>
     * 构造方法注入
     */
    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 回调函数
     *
     * @param correlationData
     * @param b
     * @param s
     */

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            logger.info("回调成功");
        } else {
            logger.error("回调失败");
        }
        logger.info("confirm: " + correlationData.getId());
    }

    /**
     *  convertAndSend(String exchange, String routingKey, final Object object, CorrelationData correlationData)
     * exchange:交换机名称
     * routingKey:路由关键字
     * object:发送的消息内容
     * correlationData:消息ID
     *
     */

    /**
     * 发送到 指定routekey的指定queue
     *
     * @param routeKey
     * @param obj
     */
    public void sendRabbitmqDirect(String routeKey, Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("send: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), routeKey, obj, correlationData);
    }

    /**
     * 所有发送到Topic Exchange的消息被转发到所有关心RouteKey中指定Topic的Queue上
     *
     * @param routeKey
     * @param obj
     */
    public void sendRabbitmqTopic(String routeKey, Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("send: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitMqEnum.Exchange.CONTRACT_TOPIC.getCode(), routeKey, obj, correlationData);
    }
}