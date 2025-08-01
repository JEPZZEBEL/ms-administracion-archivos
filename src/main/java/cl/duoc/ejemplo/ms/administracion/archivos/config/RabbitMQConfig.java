package cl.duoc.ejemplo.ms.administracion.archivos.config;

import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration

public class RabbitMQConfig {

	public static final String MAIN_QUEUE = "myQueue";
	public static final String DLX_QUEUE = "dlx-queue";
	public static final String MAIN_EXCHANGE = "myExchange";
	public static final String DLX_EXCHANGE = "dlx-exchange";
	public static final String DLX_ROUTING_KEY = "dlx-routing-key";
	public static final String BOLETA_QUEUE = "boletaQueue";
	public static final String BOLETA_DLQ = "boletaDLQ";


	@Bean
	Jackson2JsonMessageConverter messageConverter() {

		return new Jackson2JsonMessageConverter();
	}

	@Bean
	CachingConnectionFactory connectionFactory() {

		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		return factory;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setDefaultRequeueRejected(false);
		factory.setErrorHandler(error -> {
			System.err.println("Error en el consumidor: " + error.getMessage());
			// Aquí podrías enviar a DLQ o manejar el error de otra forma
		});
		return factory;
	}


	@Bean
	Queue myQueue() {

		return new Queue(MAIN_QUEUE, true, false, false,
				Map.of("x-dead-letter-exchange", DLX_EXCHANGE, "x-dead-letter-routing-key", DLX_ROUTING_KEY));
	}

	@Bean
	Queue dlxQueue() {

		return new Queue(DLX_QUEUE);
	}

	@Bean
	Queue boletaDLQ() {
		return new Queue(BOLETA_DLQ, true);
	}

	@Bean
	Queue boletaQueue() {
		return new Queue(BOLETA_QUEUE, true, false, false,
				Map.of("x-dead-letter-exchange", "", "x-dead-letter-routing-key", BOLETA_DLQ));
	}

	@Bean
	DirectExchange myExchange() {

		return new DirectExchange(MAIN_EXCHANGE);
	}

	@Bean
	DirectExchange dlxExchange() {

		return new DirectExchange(DLX_EXCHANGE);
	}

	@Bean
	Binding binding(Queue myQueue, DirectExchange myExchange) {

		return BindingBuilder.bind(myQueue).to(myExchange).with("");
	}

	@Bean
	Binding dlxBinding() {

		return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY);
	}
}
