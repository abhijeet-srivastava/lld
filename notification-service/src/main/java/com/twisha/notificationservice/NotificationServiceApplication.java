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
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableRabbit
public class NotificationServiceApplication {

	public static final String QUEUE_MESSAGES_DLQ = "notifications-dlq";
	public static final String QUEUE_MESSAGES = "notifications";
	public static final String EXCHANGE_MESSAGES_DLQ = "notifications-exchange-dlq";

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

	@Configuration
	class RabbitMQConfig {
		@Bean
		public Queue myQueue() {
			return new Queue("notifications", false);
		}
	}

	@Bean
	public Queue messageQueue() {
		return QueueBuilder.durable(QUEUE_MESSAGES)
				.withArgument("x-dead-letter-exchange", EXCHANGE_MESSAGES_DLQ)
				.withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
				.build();
	}

	@Bean
	public Queue dlq() {
		return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
	}

	@Bean
	public DirectExchange dlxExchange() {
		return new DirectExchange(EXCHANGE_MESSAGES_DLQ);
	}

	@Bean
	public Binding dlqBinding() {
		return BindingBuilder.bind(dlq()).to(dlxExchange()).with(QUEUE_MESSAGES_DLQ);
	}


}
