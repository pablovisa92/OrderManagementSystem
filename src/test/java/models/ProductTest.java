package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product(1, "Product 1", 999.99, 10);
    }

    @Test
    public void testGetId() {
        assertEquals(1, product.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Product 1", product.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(999.99, product.getPrice());
    }

    @Test
    public void testGetStock() {
        assertEquals(10, product.getStock());
    }

    @Test
    public void testSetId() {
        product.setId(2);
        assertEquals(2, product.getId());
    }

    @Test
    public void testSetName() {
        product.setName("Product 2");
        assertEquals("Product 2", product.getName());
    }

    @Test
    public void testSetPrice() {
        product.setPrice(499.99);
        assertEquals(499.99, product.getPrice());
    }

    @Test
    public void testSetStock() {
        product.setStock(20);
        assertEquals(20, product.getStock());
    }
}