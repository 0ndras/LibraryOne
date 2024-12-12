package repository.order;

import model.Order;
import model.builder.OrderBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    private final Connection connection;

    public OrderRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<List<Order>> findAllByUsername(String username) {
        String sql = "SELECT * FROM `order` WHERE employeeUsername = ?";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders.isEmpty() ? Optional.empty() : Optional.of(orders);
    }

    @Override
    public boolean save(Order order) {
        String sql = "INSERT INTO `order` VALUES (null, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, order.getEmployeeUsername());
            preparedStatement.setString(2, order.getBookTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(order.getSaleDate()));

            int rowsInserted = preparedStatement.executeUpdate();

            return (rowsInserted != 1) ? false : true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return new OrderBuilder()
                .setId(resultSet.getLong("id"))
                .setEmployeeUsername(resultSet.getString("employeeUsername"))
                .setBookTitle(resultSet.getString("bookTitle"))
                .setSaleDate(resultSet.getDate("saleDate").toLocalDate())
                .build();
    }
}
