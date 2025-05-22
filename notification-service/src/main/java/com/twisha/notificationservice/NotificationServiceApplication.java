package com.twisha.notificationservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableRabbit
public class NotificationServiceApplication {

	public static final String QUEUE_MESSAGES_DLQ = "baeldung-messages-queue";
	public static final String QUEUE_MESSAGES = "baeldung-messages-queue";
	public static final String EXCHANGE_MESSAGES = "baeldung-messages-exchange";

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.example.com");
		mailSender.setPort(567);
		mailSender.setUsername("email-user");
		mailSender.setPassword("********");
		return mailSender;
	}

	@Bean
	public Queue messageQueue() {
		return QueueBuilder.durable(QUEUE_MESSAGES)
				.withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
				.build();
	}

	@Bean
	public DirectExchange messageExchange() {
		return new DirectExchange(EXCHANGE_MESSAGES);
	}

	public Binding bindingMessage() {
		return BindingBuilder.bind(messageQueue()).to(messageExchange()).with(QUEUE_MESSAGES);
	}


}
