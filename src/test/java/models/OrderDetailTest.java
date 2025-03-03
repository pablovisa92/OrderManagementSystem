package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDetailTest {
    private OrderDetail orderDetail;

    @BeforeEach
    public void setUp() {
        orderDetail = new OrderDetail(1, 101, 202, 2, 50.0);
    }

    @Test
    public void testGetId() {
        assertEquals(1, orderDetail.getId());
    }

    @Test
    public void testGetOrderId() {
        assertEquals(101, orderDetail.getOrderId());
    }

    @Test
    public void testGetProductId() {
        assertEquals(202, orderDetail.getProductId());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(2, orderDetail.getQuantity());
    }

    @Test
    public void testGetPrice() {
        assertEquals(50.0, orderDetail.getPrice());
    }

    @Test
    public void testSetId() {
        orderDetail.setId(2);
        assertEquals(2, orderDetail.getId());
    }

    @Test
    public void testSetOrderId() {
        orderDetail.setOrderId(102);
        assertEquals(102, orderDetail.getOrderId());
    }

    @Test
    public void testSetProductId() {
        orderDetail.setProductId(203);
        assertEquals(203, orderDetail.getProductId());
    }

    @Test
    public void testSetQuantity() {
        orderDetail.setQuantity(3);
        assertEquals(3, orderDetail.getQuantity());
    }

    @Test
    public void testSetPrice() {
        orderDetail.setPrice(60.0);
        assertEquals(60.0, orderDetail.getPrice());
    }
}