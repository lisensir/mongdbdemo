package org.lisen.mongdbdemo.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConf {

    //Direct
    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    //Topic
    @Bean(name="message")
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean(name="messages")
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("message") Queue message, TopicExchange exchange) {
        return BindingBuilder.bind(message).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages") Queue messages, TopicExchange exchange) {
        return BindingBuilder.bind(messages).to(exchange).with("topic.#");
    }

    //Fanout
    @Bean(name="AMessage")
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean(name="BMessage")
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean(name="CMessage")
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingFanoutExchangeA(@Qualifier("AMessage") Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingFanoutExchangeB(@Qualifier("BMessage") Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingFanoutExchangeC(@Qualifier("CMessage") Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

}
