package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import models.Client;
import models.Order;
import models.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonDataStore {
    private static final String CLIENTS_FILE_PATH = "clients.json";
    private static final String ORDERS_FILE_PATH = "orders.json";
    private static final String PRODUCTS_FILE_PATH = "products.json";
    private ObjectMapper objectMapper;

    public JsonDataStore(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void saveClients(List<Client> clients) throws IOException {
        objectMapper.writeValue(new File(CLIENTS_FILE_PATH), clients);
    }

    public List<Client> loadClients() throws IOException {
        return objectMapper.readValue(new File(CLIENTS_FILE_PATH), new TypeReference<List<Client>>() {});
    }

    public void saveOrders(List<Order> orders) throws IOException {
        objectMapper.writeValue(new File(ORDERS_FILE_PATH), orders);
    }

    public List<Order> loadOrders() throws IOException {
        return objectMapper.readValue(new File(ORDERS_FILE_PATH), new TypeReference<List<Order>>() {});
    }

    public void saveProducts(List<Product> products) throws IOException {
        objectMapper.writeValue(new File(PRODUCTS_FILE_PATH), products);
    }

    public List<Product> loadProducts() throws IOException {
        return objectMapper.readValue(new File(PRODUCTS_FILE_PATH), new TypeReference<List<Product>>() {});
    }
}