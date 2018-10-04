package com.authenticationservice.config;

import com.common.rabbitmq.RabbitMQRouting;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange personExchange() {
        return new DirectExchange(RabbitMQRouting.Exchange.PERSON.name());
    }

    @Bean
    public Queue authenticationPersonCreateQueue() {
        return new Queue("authenticationPersonCreateQueue");
    }

    @Bean
    public Binding bindingPersonCreated(DirectExchange personExchange, Queue authenticationPersonCreateQueue) {
        return BindingBuilder.bind(authenticationPersonCreateQueue).to(personExchange)
                .with(RabbitMQRouting.Person.CREATE);
    }

    @Bean
    public Queue authenticationPersonDeleteQueue() {
        return new Queue("authenticationPersonDeleteQueue");
    }

    @Bean
    public Binding bindingPersonDeleted(DirectExchange personExchange, Queue authenticationPersonDeleteQueue) {
        return BindingBuilder.bind(authenticationPersonDeleteQueue).to(personExchange)
                .with(RabbitMQRouting.Person.DELETE);
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(jacksonConverter());
        return factory;
    }
}
