package repository.order;

import model.Book;
import model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<List<Order>> findAllByUsername(String username);
    boolean save(Order order);
}
