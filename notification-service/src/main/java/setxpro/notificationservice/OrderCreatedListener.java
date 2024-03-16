package setxpro.notificationservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    @RabbitListener(queues = "orders.v1.order-created.send-notification")
    public void onOrderCreated(OrderCreatedEvent event) {
        System.out.println("ID received: " + event.getId());
        System.out.println("Value received: " + event.getValue());
    }
}
