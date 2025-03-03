package daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import models.OrderDetail;

public class OrderDetailDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private OrderDetailDAO orderDetailDAO;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrderDetail() throws SQLException {
        OrderDetail orderDetail = new OrderDetail(1, 1, 1, 2, 20.0);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        orderDetailDAO.addOrderDetail(orderDetail);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetOrderDetailById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("order_id")).thenReturn(1);
        when(resultSet.getInt("product_id")).thenReturn(1);
        when(resultSet.getInt("quantity")).thenReturn(2);
        when(resultSet.getDouble("price")).thenReturn(20.0);

        OrderDetail orderDetail = orderDetailDAO.getOrderDetailById(1);

        assertNotNull(orderDetail);
        assertEquals(1, orderDetail.getId());
        assertEquals(1, orderDetail.getOrderId());
        assertEquals(1, orderDetail.getProductId());
        assertEquals(2, orderDetail.getQuantity());
        assertEquals(20.0, orderDetail.getPrice(), 0.01);
    }

    @Test
    public void testGetAllOrderDetails() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSet.getInt("order_id")).thenReturn(1).thenReturn(2);
        when(resultSet.getInt("product_id")).thenReturn(1).thenReturn(2);
        when(resultSet.getInt("quantity")).thenReturn(2).thenReturn(3);
        when(resultSet.getDouble("price")).thenReturn(20.0).thenReturn(30.0);

        List<OrderDetail> orderDetails = orderDetailDAO.getAllOrderDetails();

        assertNotNull(orderDetails);
        assertEquals(2, orderDetails.size());
        assertEquals(1, orderDetails.get(0).getId());
        assertEquals(1, orderDetails.get(0).getOrderId());
        assertEquals(1, orderDetails.get(0).getProductId());
        assertEquals(2, orderDetails.get(0).getQuantity());
        assertEquals(20.0, orderDetails.get(0).getPrice(), 0.01);
        assertEquals(2, orderDetails.get(1).getId());
        assertEquals(2, orderDetails.get(1).getOrderId());
        assertEquals(2, orderDetails.get(1).getProductId());
        assertEquals(3, orderDetails.get(1).getQuantity());
        assertEquals(30.0, orderDetails.get(1).getPrice(), 0.01);
    }

    @Test
    public void testUpdateOrderDetail() throws SQLException {
        OrderDetail orderDetail = new OrderDetail(1, 1, 1, 2, 25.0);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        orderDetailDAO.updateOrderDetail(orderDetail);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteOrderDetail() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        orderDetailDAO.deleteOrderDetail(1);

        verify(preparedStatement, times(1)).executeUpdate();
    }
}