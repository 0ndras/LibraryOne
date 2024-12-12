package service.order;

import model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAllByUsername(String username);
    boolean save(Order order);
}
