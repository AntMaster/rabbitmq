package pers.zhangyu.rabbitmq.config.consumer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.zhangyu.rabbitmq.config.RabbitMqConfig;
import pers.zhangyu.rabbitmq.domain.User;
import pers.zhangyu.rabbitmq.utils.SerializeUtil;

/**
 * 消费者配置
 *
 * @author chenhf
 * @create 2017-10-30 下午3:14
 **/
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class TopicAmqpConfiguration2 {
    @Bean("topicTest2Container")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("TOPICTEST2");
        container.setMessageListener(exampleListener());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }


    @Bean("topicTest2Listener")
    public ChannelAwareMessageListener exampleListener() {
        return (message, channel) -> {
            User User = (pers.zhangyu.rabbitmq.domain.User) SerializeUtil.unserialize(message.getBody());
            System.out.println("TOPICTEST2：" + User.toString());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        };
    }

}