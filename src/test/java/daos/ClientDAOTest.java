package daos;

import models.Client;
import models.Order;
import models.OrderStatus;
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

public class ClientDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ClientDAO clientDAO;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddClient() throws SQLException {
        Client client = new Client(1, "John Doe", "john@example.com", null);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        clientDAO.addClient(client);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetClientById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("email")).thenReturn("john@example.com");

        //getOrderHistoryByClientId
        when(connection.prepareStatement("SELECT * FROM orders WHERE client_id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getDouble("total")).thenReturn(100.0);
        when(resultSet.getString("status")).thenReturn("PENDING");

        Client client = clientDAO.getClientById(1);

        assertNotNull(client);
        assertEquals(1, client.getId());
        assertEquals("John Doe", client.getName());
        assertEquals("john@example.com", client.getEmail());
    }

    @Test
    public void testGetAllClients() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("name")).thenReturn("John Doe").thenReturn("Jane Doe");
        when(resultSet.getString("email")).thenReturn("john@example.com").thenReturn("jane@example.com");

        //getOrderHistoryByClientId
        PreparedStatement orderHistoryStmt = mock(PreparedStatement.class);
        ResultSet orderHistoryRs = mock(ResultSet.class);
        when(connection.prepareStatement("SELECT * FROM orders WHERE client_id = ?")).thenReturn(orderHistoryStmt);
        when(orderHistoryStmt.executeQuery()).thenReturn(orderHistoryRs);
        when(orderHistoryRs.next()).thenReturn(true).thenReturn(false);
        when(orderHistoryRs.getInt("id")).thenReturn(1);
        when(orderHistoryRs.getDouble("total")).thenReturn(100.0);
        when(orderHistoryRs.getString("status")).thenReturn("PENDING");

        List<Client> clients = clientDAO.getAllClients();

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals(1, clients.get(0).getId());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("john@example.com", clients.get(0).getEmail());
        assertEquals(2, clients.get(1).getId());
        assertEquals("Jane Doe", clients.get(1).getName());
        assertEquals("jane@example.com", clients.get(1).getEmail());
    }

    @Test
    public void testUpdateClient() throws SQLException {
        Client client = new Client(1, "Updated Name", "updated@example.com", null);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        clientDAO.updateClient(client);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteClient() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        clientDAO.deleteClient(1);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetOrderHistoryByClientId() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSet.getDouble("total")).thenReturn(100.0).thenReturn(200.0);
        when(resultSet.getString("status")).thenReturn("PENDING").thenReturn("SHIPPED");

        List<Order> orderHistory = clientDAO.getOrderHistoryByClientId(1);

        assertNotNull(orderHistory);
        assertEquals(2, orderHistory.size());
        assertEquals(1, orderHistory.get(0).getId());
        assertEquals(100.0, orderHistory.get(0).getTotal(), 0.01);
        assertEquals(OrderStatus.PENDING, orderHistory.get(0).getOrderStatus());
        assertEquals(2, orderHistory.get(1).getId());
        assertEquals(200.0, orderHistory.get(1).getTotal(), 0.01);
        assertEquals(OrderStatus.SHIPPED, orderHistory.get(1).getOrderStatus());
    }
}