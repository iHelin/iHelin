package me.ianhe.isite;

import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.jms.Queue;

/**
 * 启动类
 *
 * @author linhe2
 * @since 2018/5/12 16:53
 */
@EnableJms
@EnableAsync
@EnableScheduling
@EnableWebSecurity
@SpringBootApplication
@MapperScan("me.ianhe.isite.dao")
public class MyApplication {

    @Bean
    public Queue article() {
        return new ActiveMQQueue("article");
    }

    @Bean
    public Queue mail() {
        return new ActiveMQQueue("mail");
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

}
