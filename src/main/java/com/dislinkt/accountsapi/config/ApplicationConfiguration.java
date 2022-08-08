package com.dislinkt.accountsapi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue createAccountRegistrationPostsQueue() {
        return new Queue("q.account-registration-posts");
    }

    @Bean
    public Queue createAccountRegistrationChatQueue() {
        return new Queue("q.account-registration-chat");
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @Bean
    public Declarables createRegistrationSchema(){
        return new Declarables(
                new FanoutExchange("x.account-registration"),
                new Queue("q.account-registration-posts" ),
                new Queue("q.account-registration-chat"),
                new Binding("q.account-registration-posts", Binding.DestinationType.QUEUE,
                        "x.account-registration", "account-registration-posts", null),
                new Binding("q.account-registration-chat", Binding.DestinationType.QUEUE,
                        "x.account-registration", "account-registration-chat", null));

    }
}
