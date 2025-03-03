package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
    private Client client;
    private List<Order> orderHistory;

    @BeforeEach
    public void setUp() {
        orderHistory = new ArrayList<>();
        client = new Client(1, "Pablo Smith", "pablo@mail.com", orderHistory);
    }

    @Test
    public void testGetId() {
        assertEquals(1, client.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Pablo Smith", client.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("pablo@mail.com", client.getEmail());
    }

    @Test
    public void testSetId() {
        client.setId(2);
        assertEquals(2, client.getId());
    }

    @Test
    public void testSetName() {
        client.setName("Pablo Smith");
        assertEquals("Pablo Smith", client.getName());
    }

    @Test
    public void testSetEmail() {
        client.setEmail("pablo@mail.com");
        assertEquals("pablo@mail.com", client.getEmail());
    }

    @Test
    public void testGetOrderHistory() {
        assertEquals(orderHistory, client.getOrderHistory());
    }

    @Test
    public void testSetOrderHistory() {
        List<Order> newOrderHistory = new ArrayList<>();
        client.setOrderHistory(newOrderHistory);
        assertEquals(newOrderHistory, client.getOrderHistory());
    }
}