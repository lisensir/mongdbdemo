package org.lisen.mongdbdemo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String s) {
                if(ack) {
                    System.out.println("############################################################");
                } else {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        });
    }

    public void directSend() {
        //template.convertAndSend("queue", "hello rabbit mq");
        //CorrelationData
        rabbitTemplate.convertAndSend("queue", "hello rabbit mq");
    }

    public void topicSendMessage() {
        rabbitTemplate.convertAndSend("exchange", "topic.message", "topic message info");
    }

    public void topicSendMessages() {
        rabbitTemplate.convertAndSend("exchange", "topic.messages", "topic messages info");
    }

    public void fanoutSendMessage() {
        rabbitTemplate.convertAndSend("fanoutExchange","", "fanout message");
    }

}
