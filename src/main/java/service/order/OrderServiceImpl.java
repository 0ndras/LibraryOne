package service.order;

import model.Order;
import repository.order.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByUsername(username).orElse(new ArrayList<>());
    }


    @Override
    public boolean save(Order order) {
        return orderRepository.save(order);
    }
}
