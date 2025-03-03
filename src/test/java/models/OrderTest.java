package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    private Order order;
    private Client client;
    private List<Product> products;

    @BeforeEach
    public void setUp() {
        client = new Client(1, "Pablo Smith", "pablo@mail.com", new ArrayList<>());
        products = new ArrayList<>();
        order = new Order(1, client, products, 100.0, OrderStatus.PENDING, new Date());
    }

    @Test
    public void testGetId() {
        assertEquals(1, order.getId());
    }

    @Test
    public void testGetClient() {
        assertEquals(client, order.getClient());
    }

    @Test
    public void testGetProducts() {
        assertEquals(products, order.getProducts());
    }

    @Test
    public void testGetTotal() {
        assertEquals(100.0, order.getTotal());
    }

    @Test
    public void testGetOrderStatus() {
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
    }

    @Test
    public void testSetId() {
        order.setId(2);
        assertEquals(2, order.getId());
    }

    @Test
    public void testSetClient() {
        Client newClient = new Client(2, "Pablo Smith", "pablo@mail.com", new ArrayList<>());
        order.setClient(newClient);
        assertEquals(newClient, order.getClient());
    }

    @Test
    public void testSetProducts() {
        List<Product> newProducts = new ArrayList<>();
        order.setProducts(newProducts);
        assertEquals(newProducts, order.getProducts());
    }

    @Test
    public void testSetTotal() {
        order.setTotal(200.0);
        assertEquals(200.0, order.getTotal());
    }

    @Test
    public void testSetOrderStatus() {
        order.setOrderStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getOrderStatus());
    }
}