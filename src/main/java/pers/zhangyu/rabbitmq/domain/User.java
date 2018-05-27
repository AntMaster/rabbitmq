package pers.zhangyu.rabbitmq.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class User {


    private String name;

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return "123";
    }
}
