package daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import models.Client;
import models.Order;
import models.OrderStatus;
import models.Product;

public class OrderDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private PreparedStatement addOrderDetailsStmt;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private OrderDAO orderDAO;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() throws SQLException {
        Client client = new Client(1, "John Doe", "john@example.com", null);
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 100));
        Order order = new Order(1, client, products, 10.0, OrderStatus.PENDING, new Date());

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        // Mock para addOrderDetails
        when(connection.prepareStatement("INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)")).thenReturn(addOrderDetailsStmt);

        orderDAO.createOrder(order);

        verify(preparedStatement, times(1)).executeUpdate();
        verify(addOrderDetailsStmt, times(1)).executeBatch();
    }

    @Test
    public void testGetOrder() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("client_id")).thenReturn(1);
        when(resultSet.getDouble("total")).thenReturn(100.0);
        when(resultSet.getString("status")).thenReturn("PENDING");

        // Simular el resultado de getProductsByOrderId
        PreparedStatement productStmt = mock(PreparedStatement.class);
        ResultSet productRs = mock(ResultSet.class);
        when(connection.prepareStatement("SELECT p.id, p.name, p.price, p.stock, od.quantity, od.price FROM order_detail od JOIN products p ON od.product_id = p.id WHERE od.order_id = ?")).thenReturn(productStmt);
        when(productStmt.executeQuery()).thenReturn(productRs);
        when(productRs.next()).thenReturn(true).thenReturn(false);
        when(productRs.getInt("id")).thenReturn(1);
        when(productRs.getString("name")).thenReturn("Product1");
        when(productRs.getDouble("price")).thenReturn(10.0);
        when(productRs.getInt("stock")).thenReturn(100);

        Order order = orderDAO.getOrder(1);

        assertNotNull(order);
        assertEquals(1, order.getId());
        assertEquals(1, order.getClient().getId());
        assertEquals(100.0, order.getTotal(), 0.01);
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
    }

    @Test
    public void testGetAllOrders() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSet.getInt("client_id")).thenReturn(1).thenReturn(2);
        when(resultSet.getDouble("total")).thenReturn(100.0).thenReturn(200.0);
        when(resultSet.getString("status")).thenReturn("PENDING").thenReturn("SHIPPED");

        // Simular el resultado de getProductsByOrderId
        PreparedStatement productStmt = mock(PreparedStatement.class);
        ResultSet productRs = mock(ResultSet.class);
        when(connection.prepareStatement("SELECT p.id, p.name, p.price, p.stock, od.quantity, od.price FROM order_detail od JOIN products p ON od.product_id = p.id WHERE od.order_id = ?")).thenReturn(productStmt);
        when(productStmt.executeQuery()).thenReturn(productRs);
        when(productRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(productRs.getInt("id")).thenReturn(1).thenReturn(2);
        when(productRs.getString("name")).thenReturn("Product1").thenReturn("Product2");
        when(productRs.getDouble("price")).thenReturn(10.0).thenReturn(20.0);
        when(productRs.getInt("stock")).thenReturn(100).thenReturn(200);

        List<Order> orders = orderDAO.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals(1, orders.get(0).getId());
        assertEquals(1, orders.get(0).getClient().getId());
        assertEquals(100.0, orders.get(0).getTotal(), 0.01);
        assertEquals(OrderStatus.PENDING, orders.get(0).getOrderStatus());
        assertEquals(2, orders.get(1).getId());
        assertEquals(2, orders.get(1).getClient().getId());
        assertEquals(200.0, orders.get(1).getTotal(), 0.01);
        assertEquals(OrderStatus.SHIPPED, orders.get(1).getOrderStatus());
    }

    @Test
    public void testUpdateOrder() throws SQLException {
        Client client = new Client(1, "John Doe", "john@example.com", null);
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 100));
        Order order = new Order(1, client, products, 10.0, OrderStatus.PENDING, new Date());

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        orderDAO.updateOrder(order);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteOrder() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        orderDAO.deleteOrder(1);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetProductsByOrderId() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("name")).thenReturn("Product1").thenReturn("Product2");
        when(resultSet.getDouble("price")).thenReturn(10.0).thenReturn(20.0);
        when(resultSet.getInt("stock")).thenReturn(100).thenReturn(200);

        List<Product> products = orderDAO.getProductsByOrderId(1);

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(1, products.get(0).getId());
        assertEquals("Product1", products.get(0).getName());
        assertEquals(10.0, products.get(0).getPrice(), 0.01);
        assertEquals(100, products.get(0).getStock());
        assertEquals(2, products.get(1).getId());
        assertEquals("Product2", products.get(1).getName());
        assertEquals(20.0, products.get(1).getPrice(), 0.01);
        assertEquals(200, products.get(1).getStock());
    }

    @Test
    public void testCalculateTotalWithTax() {
        Client client = new Client(1, "John Doe", "john@example.com", null);
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 100));
        Order order = new Order(1, client, products, 100.0, OrderStatus.PENDING, new Date());

        double totalWithTax = orderDAO.calculateTotalWithTax(order, 0.1);

        assertEquals(110.0, totalWithTax, 0.01);
    }
}