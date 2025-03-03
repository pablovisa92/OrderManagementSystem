package controllers;

import daos.OrderDAO;
import models.Order;
import models.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class OrderControllerTest {

    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() throws SQLException {
        Order order = new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date());

        orderController.createOrder(order);

        verify(orderDAO, times(1)).createOrder(order);
    }

    @Test
    public void testUpdateOrderStatus() throws SQLException {
        Order order = new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date());
        when(orderDAO.getOrder(1)).thenReturn(order);

        orderController.updateOrderStatus(1, OrderStatus.SHIPPED);

        assertEquals(OrderStatus.SHIPPED, order.getOrderStatus());
        verify(orderDAO, times(1)).updateOrder(order);
    }

    @Test
    public void testDeleteOrder() throws SQLException {
        orderController.deleteOrder(1);

        verify(orderDAO, times(1)).deleteOrder(1);
    }

    @Test
    public void testGetOrderById() throws SQLException {
        Order order = new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date());
        when(orderDAO.getOrder(1)).thenReturn(order);

        Order foundOrder = orderController.getOrderById(1);

        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getId());
        assertEquals(100.0, foundOrder.getTotal(), 0.01);
        assertEquals(OrderStatus.PENDING, foundOrder.getOrderStatus());
    }

    @Test
    public void testGetAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date()));
        orders.add(new Order(2, null, null, 200.0, OrderStatus.SHIPPED, new Date()));
        when(orderDAO.getAllOrders()).thenReturn(orders);

        List<Order> allOrders = orderController.getAllOrders();

        assertNotNull(allOrders);
        assertEquals(2, allOrders.size());
        assertEquals(1, allOrders.get(0).getId());
        assertEquals(100.0, allOrders.get(0).getTotal(), 0.01);
        assertEquals(OrderStatus.PENDING, allOrders.get(0).getOrderStatus());
        assertEquals(2, allOrders.get(1).getId());
        assertEquals(200.0, allOrders.get(1).getTotal(), 0.01);
        assertEquals(OrderStatus.SHIPPED, allOrders.get(1).getOrderStatus());
    }

    @Test
    public void testCalculateTotalWithTax() {
        Order order = new Order(1, null, null, 100.0, OrderStatus.PENDING,new Date());
        when(orderDAO.calculateTotalWithTax(order, 0.1)).thenReturn(110.0);

        double totalWithTax = orderController.calculateTotalWithTax(order, 0.1);

        assertEquals(110.0, totalWithTax, 0.01);
    }
}