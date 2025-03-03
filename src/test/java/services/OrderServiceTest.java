package services;

import controllers.OrderController;
import models.Order;
import models.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderController orderController;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder() {
        Order order = new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date());

        orderService.placeOrder(order);

        verify(orderController, times(1)).createOrder(order);
    }

    @Test
    public void testChangeOrderStatus() {
        orderService.changeOrderStatus(1, OrderStatus.SHIPPED);

        verify(orderController, times(1)).updateOrderStatus(1, OrderStatus.SHIPPED);
    }

    @Test
    public void testCancelOrder() {
        orderService.cancelOrder(1);

        verify(orderController, times(1)).deleteOrder(1);
    }

    @Test
    public void testFindOrderById() {
        Order order = new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date());
        when(orderController.getOrderById(1)).thenReturn(order);

        Order foundOrder = orderService.findOrderById(1);

        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getId());
        assertEquals(100.0, foundOrder.getTotal(), 0.01);
        assertEquals(OrderStatus.PENDING, foundOrder.getOrderStatus());
    }

    @Test
    public void testListAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date()));
        orders.add(new Order(2, null, null, 200.0, OrderStatus.SHIPPED, new Date()));
        when(orderController.getAllOrders()).thenReturn(orders);

        List<Order> allOrders = orderService.listAllOrders();

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
    public void testCalculateOrderTotalWithTax() {
        Order order = new Order(1, null, null, 100.0, OrderStatus.PENDING, new Date());
        when(orderController.calculateTotalWithTax(order, 0.1)).thenReturn(110.0);

        double totalWithTax = orderService.calculateOrderTotalWithTax(order, 0.1);

        assertEquals(110.0, totalWithTax, 0.01);
    }
}