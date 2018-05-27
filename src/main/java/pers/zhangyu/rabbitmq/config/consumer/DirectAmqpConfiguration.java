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
 * <p>
 * springboot注解方式监听队列，无法手动指定回调，所以采用了实现ChannelAwareMessageListener接口，重写onMessage来进行手动回调
 **/
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class DirectAmqpConfiguration {

    /**
     * 直连消费者通过设置User的name来测试回调，分别发两条消息，一条UserName为1，一条为2，查看控制台中队列中消息是否被消费
     */

    @Bean("queueContainer")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("TESTQUEUE");
        container.setMessageListener(exampleListener());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }

    @Bean("queueListener")
    public ChannelAwareMessageListener exampleListener() {
        return (message, channel) -> {
            //通过设置User的name来测试回调，分别发两条消息，一条UserName为1，一条为2，查看控制台中队列中消息是否被消费
            //User User = (User) SerializeUtil.unserialize(message.getBody());

            String name = new String(message.getBody());
            if ("2".equals(name)) {
                System.out.println(name);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }

            if ("1".equals(name)) {
                System.out.println(name);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }

        };
    }
}
