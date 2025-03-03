package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderStatusTest {

    @Test
    public void testOrderStatusValues() {
        OrderStatus[] statuses = OrderStatus.values();
        assertEquals(3, statuses.length);
        assertEquals(OrderStatus.PENDING, statuses[0]);
        assertEquals(OrderStatus.SHIPPED, statuses[1]);
        assertEquals(OrderStatus.DELIVERED, statuses[2]);
    }

    @Test
    public void testOrderStatusValueOf() {
        assertEquals(OrderStatus.PENDING, OrderStatus.valueOf("PENDING"));
        assertEquals(OrderStatus.SHIPPED, OrderStatus.valueOf("SHIPPED"));
        assertEquals(OrderStatus.DELIVERED, OrderStatus.valueOf("DELIVERED"));
    }
}