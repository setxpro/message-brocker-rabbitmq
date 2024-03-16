package setxpro.cashbackservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    @RabbitListener(queues = "orders.v1.order-created.generate-cashback")
    public void onOrderCreated(OrderCreatedEvent event) {
        System.out.println("ID received: " + event.getId());
        System.out.println("Value received: " + event.getValue());
    }
}
