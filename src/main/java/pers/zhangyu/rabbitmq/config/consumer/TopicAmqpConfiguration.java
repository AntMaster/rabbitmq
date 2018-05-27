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

/**
 * 消费者配置
 *
 * @author chenhf
 *  2017-10-30 下午3:14
 **/
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class TopicAmqpConfiguration {

    @Bean("topicContainer")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("TOPICTEST1");
        container.setMessageListener(exampleListener1());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }


    @Bean("topicListener")
    public ChannelAwareMessageListener exampleListener1() {
        return (message, channel) -> {
            //User User = (pers.zhangyu.rabbitmq.domain.User) SerializeUtil.unserialize(message.getBody());
            String name = new String(message.getBody());
            System.out.println("TOPICTEST1：" + name);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        };
    }


}