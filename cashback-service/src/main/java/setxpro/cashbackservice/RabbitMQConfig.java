package setxpro.cashbackservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    // Config first Queue
    // boas praticas de nomenclatura  orders.v1.order-created
    @Bean
    public Queue queueCashback() {
        return new Queue("orders.v1.order-created.generate-cashback");
    }

    // Connection Queue with the exchange
    @Bean
    public Binding binding() {
        Queue queue = new Queue("orders.v1.order-created.generate-cashback");
        FanoutExchange exchange = new FanoutExchange("orders.v1.order-created");
        return BindingBuilder.bind(queue).to(exchange);
    }

    // Convert Object in byte to JSON...
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Customize object template
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // Create queue when initialize
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // Create Listener for event -- spring initialize
    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(
            RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
