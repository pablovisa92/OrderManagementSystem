package daos;

import models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ProductDAO productDAO;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() throws SQLException {
        Product product = new Product(1, "Product1", 10.0, 100);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        productDAO.addProduct(product);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetProductById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Product1");
        when(resultSet.getDouble("price")).thenReturn(10.0);
        when(resultSet.getInt("stock")).thenReturn(100);

        Product product = productDAO.getProductById(1);

        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Product1", product.getName());
        assertEquals(10.0, product.getPrice(), 0.01);
        assertEquals(100, product.getStock());
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("name")).thenReturn("Product1").thenReturn("Product2");
        when(resultSet.getDouble("price")).thenReturn(10.0).thenReturn(20.0);
        when(resultSet.getInt("stock")).thenReturn(100).thenReturn(200);

        List<Product> products = productDAO.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(1, products.get(0).getId());
        assertEquals("Product1", products.get(0).getName());
        assertEquals(10.0, products.get(0).getPrice(), 0.01);
        assertEquals(100, products.get(0).getStock());
        assertEquals(2, products.get(1).getId());
        assertEquals("Product2", products.get(1).getName());
        assertEquals(20.0, products.get(1).getPrice(), 0.01);
        assertEquals(200, products.get(1).getStock());
    }

    @Test
    public void testUpdateProduct() throws SQLException {
        Product product = new Product(1, "UpdatedProduct", 15.0, 150);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        productDAO.updateProduct(product);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        productDAO.deleteProduct(1);

        verify(preparedStatement, times(1)).executeUpdate();
    }
}