package controllers;

import daos.ProductDAO;
import models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() throws SQLException {
        Product product = new Product(1, "Product1", 10.0, 100);

        productController.addProduct(product);

        verify(productDAO, times(1)).addProduct(product);
    }

    @Test
    public void testGetProductById() throws SQLException {
        Product product = new Product(1, "Product1", 10.0, 100);
        when(productDAO.getProductById(1)).thenReturn(product);

        Product foundProduct = productController.getProductById(1);

        assertNotNull(foundProduct);
        assertEquals(1, foundProduct.getId());
        assertEquals("Product1", foundProduct.getName());
        assertEquals(10.0, foundProduct.getPrice(), 0.01);
        assertEquals(100, foundProduct.getStock());
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 100));
        products.add(new Product(2, "Product2", 20.0, 200));
        when(productDAO.getAllProducts()).thenReturn(products);

        List<Product> allProducts = productController.getAllProducts();

        assertNotNull(allProducts);
        assertEquals(2, allProducts.size());
        assertEquals(1, allProducts.get(0).getId());
        assertEquals("Product1", allProducts.get(0).getName());
        assertEquals(10.0, allProducts.get(0).getPrice(), 0.01);
        assertEquals(100, allProducts.get(0).getStock());
        assertEquals(2, allProducts.get(1).getId());
        assertEquals("Product2", allProducts.get(1).getName());
        assertEquals(20.0, allProducts.get(1).getPrice(), 0.01);
        assertEquals(200, allProducts.get(1).getStock());
    }

    @Test
    public void testUpdateProduct() throws SQLException {
        Product product = new Product(1, "UpdatedProduct", 15.0, 150);

        productController.updateProduct(product);

        verify(productDAO, times(1)).updateProduct(product);
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        productController.deleteProduct(1);

        verify(productDAO, times(1)).deleteProduct(1);
    }
}