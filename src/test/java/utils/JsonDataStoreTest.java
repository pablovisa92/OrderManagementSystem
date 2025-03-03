package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Client;
import models.Order;
import models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class JsonDataStoreTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JsonDataStore jsonDataStore;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveClients() throws IOException {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "John Doe", "john@example.com", null));

        jsonDataStore.saveClients(clients);

        verify(objectMapper, times(1)).writeValue(new File("clients.json"), clients);
    }

    @Test
    public void testLoadClients() throws IOException {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "John Doe", "john@example.com", null));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(clients);

        List<Client> loadedClients = jsonDataStore.loadClients();

        assertNotNull(loadedClients);
        assertEquals(1, loadedClients.size());
        assertEquals(1, loadedClients.get(0).getId());
        assertEquals("John Doe", loadedClients.get(0).getName());
        assertEquals("john@example.com", loadedClients.get(0).getEmail());
    }

    @Test
    public void testSaveOrders() throws IOException {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, null, null, 100.0, null, null));

        jsonDataStore.saveOrders(orders);

        verify(objectMapper, times(1)).writeValue(new File("orders.json"), orders);
    }

    @Test
    public void testLoadOrders() throws IOException {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, null, null, 100.0, null, null));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(orders);

        List<Order> loadedOrders = jsonDataStore.loadOrders();

        assertNotNull(loadedOrders);
        assertEquals(1, loadedOrders.size());
        assertEquals(1, loadedOrders.get(0).getId());
        assertEquals(100.0, loadedOrders.get(0).getTotal(), 0.01);
    }

    @Test
    public void testSaveProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 100));

        jsonDataStore.saveProducts(products);

        verify(objectMapper, times(1)).writeValue(new File("products.json"), products);
    }

    @Test
    public void testLoadProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 100));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(products);

        List<Product> loadedProducts = jsonDataStore.loadProducts();

        assertNotNull(loadedProducts);
        assertEquals(1, loadedProducts.size());
        assertEquals(1, loadedProducts.get(0).getId());
        assertEquals("Product1", loadedProducts.get(0).getName());
        assertEquals(10.0, loadedProducts.get(0).getPrice(), 0.01);
        assertEquals(100, loadedProducts.get(0).getStock());
    }
}