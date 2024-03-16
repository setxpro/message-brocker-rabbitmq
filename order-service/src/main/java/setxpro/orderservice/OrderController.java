package setxpro.orderservice;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/v1/orders")
public class OrderController {
    @Autowired
    private OrderRepository orders;

    @Autowired
    private RabbitTemplate rabbitTemplate; // Get up messages

    @PostMapping
    public Order create(@RequestBody Order order) {
        orders.save(order); // After save new sale
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getValue());
        // Received in bytes
        rabbitTemplate.convertAndSend("orders.v1.order-created", "", event); // add exchange -- routerKey can be empty
       // rabbitTemplate.convertAndSend("orders.v1.order-created.generate-cashback", event); // Convert event to JSON from config Bean
       // rabbitTemplate.convertAndSend("orders.v1.order-created.send-notification", event); // Convert event to JSON from config Bean
        return order;
    }

    @GetMapping
    public Collection<Order> list() {
        return orders.findAll();
    }

    @GetMapping("{id}")
    public Order findById(@PathVariable Long id) {
        return orders.findById(id).orElseThrow();
    }

    @PutMapping("{id}/pay")
    public Order pay(@PathVariable Long id) {
        Order order = orders.findById(id).orElseThrow();
        order.markAsPaid();
        return orders.save(order);
    }
}
