package pers.zhangyu.rabbitmq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zhangyu.rabbitmq.domain.User;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMqSenderTest {


    @Autowired
    private RabbitMqSender rabbitMqSender;


    @Test
    public void confirm() {
        //rabbitMqSender.confirm();
    }

    @Test
    public void sendRabbitmqDirect() {
        rabbitMqSender.sendRabbitmqDirect("TESTQUEUE1", "2");
    }

    @Test
    public void sendRabbitmqTopic() {

        rabbitMqSender.sendRabbitmqTopic("lazy.1.2", "1");
        //rabbitMqSender.sendRabbitmqTopic("lazy.TEST.2", "123");

    }
}