package daos;

import models.Client;
import models.Order;
import models.OrderStatus;
import models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public void createOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (client_id, total, status, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getClient().getId());
            stmt.setDouble(2, order.getTotal());
            stmt.setString(3, order.getOrderStatus().name());
            stmt.setTimestamp(4, new Timestamp(order.getDate().getTime()));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                addOrderDetails(orderId, order.getProducts());
            }
        }
    }

    private void addOrderDetails(int orderId, List<Product> products) throws SQLException {
        String query = "INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Product product : products) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, product.getId());
                stmt.setInt(3, 1); // Asumiendo cantidad 1 por simplicidad
                stmt.setDouble(4, product.getPrice());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public Order getOrder(int id) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                List<Product> products = getProductsByOrderId(id);
                return new Order(
                        rs.getInt("id"),
                        new Client(rs.getInt("client_id")),
                        products,
                        rs.getDouble("total"),
                        OrderStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("date")
                );
            }
        }
        return null;
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                double total = rs.getDouble("total");
                String status = rs.getString("status");
                Date date = rs.getDate("date");

                List<Product> products = getProductsByOrderId(orderId);
                Order order = new Order(
                        orderId,
                        new Client(clientId),
                        products,
                        total,
                        OrderStatus.valueOf(status),
                        date
                );
                orders.add(order);
            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE orders SET client_id = ?, total = ?, status = ?, date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, order.getClient().getId());
            stmt.setDouble(2, order.getTotal());
            stmt.setString(3, order.getOrderStatus().name());
            stmt.setTimestamp(4, new Timestamp(order.getDate().getTime()));
            stmt.setInt(5, order.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteOrder(int id) throws SQLException {
        String query = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Product> getProductsByOrderId(int orderId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.price, p.stock, od.quantity, od.price FROM order_detail od JOIN products p ON od.product_id = p.id WHERE od.order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                products.add(product);
            }
        }
        return products;
    }

    public double calculateTotalWithTax(Order order, double taxRate) {
        double total = order.getTotal();
        return total + (total * taxRate);
    }
}