package pers.zhangyu.rabbitmq.config.exchange;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.zhangyu.rabbitmq.config.RabbitMqConfig;
import pers.zhangyu.rabbitmq.enums.RabbitMqEnum;

/**
 * 用于配置交换机和队列对应关系
 * 新增消息队列应该按照如下步骤
 * 1、增加queue bean，参见queueXXXX方法
 * 2、增加queue和exchange的binding
 *
 * @author chenhf
 * @create 2017-10-23 上午10:33
 **/

@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class RabbitMqExchangeConfig {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqExchangeConfig.class);


    //-----------------------------------------------------------------exchange------------------------------------------------------------------------------------------------

    /**
     * 直连型交换机
     */
    @Bean
    DirectExchange directExchange(RabbitAdmin rabbitAdmin) {
        DirectExchange directExchange = new DirectExchange(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode());
        rabbitAdmin.declareExchange(directExchange);
        logger.debug("完成直连型交换机bean实例化");
        return directExchange;
    }


    /**
     * 主题型交换机
     */
    @Bean
    TopicExchange topicExchange(RabbitAdmin rabbitAdmin) {
        TopicExchange topicExchange = new TopicExchange(RabbitMqEnum.Exchange.CONTRACT_TOPIC.getCode());
        rabbitAdmin.declareExchange(topicExchange);
        logger.debug("完成主题型交换机bean实例化");
        return topicExchange;
    }

    //-----------------------------------------------------------------queue------------------------------------------------------------------------------------------------

    @Bean
    Queue directQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqEnum.QueueName.TESTQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("测试队列实例化完成");
        return queue;
    }


    @Bean
    Queue topicQueue1(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqEnum.QueueName.TOPICTEST1.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("话题测试队列1实例化完成");
        return queue;
    }


    @Bean
    Queue topicQueue2(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqEnum.QueueName.TOPICTEST2.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("话题测试队列2实例化完成");
        return queue;
    }

    //----------------------------------------------------------------binding-------------------------------------------------------------------------------------------------
    //binding exchange queue
    @Bean
    Binding bindingQueueTest(Queue directQueue, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(directQueue).to(directExchange).with(RabbitMqEnum.QueueEnum.TESTQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("测试队列与直连型交换机绑定完成");
        return binding;
    }

    //topic binding1
    @Bean
    Binding bindingQueueTopicTest1(Queue topicQueue1, TopicExchange topicExchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(topicQueue1).to(topicExchange).with(RabbitMqEnum.QueueEnum.TESTTOPICQUEUE1.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("测试队列与话题交换机1绑定完成");
        return binding;
    }

    //topic binding2
    @Bean
    Binding bindingQueueTopicTest2(Queue topicQueue2, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(topicQueue2).to(exchange).with(RabbitMqEnum.QueueEnum.TESTTOPICQUEUE2.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("测试队列与话题交换机2绑定完成");
        return binding;
    }


}
