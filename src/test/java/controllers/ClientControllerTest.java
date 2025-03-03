package controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import daos.ClientDAO;
import models.Client;
import models.Order;

public class ClientControllerTest {

    @Mock
    private ClientDAO clientDAO;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddClient() throws SQLException {
        Client client = new Client(1, "John Doe", "john@example.com", null);

        clientController.addClient(client);

        verify(clientDAO, times(1)).addClient(client);
    }

    @Test
    public void testGetClientById() throws SQLException {
        Client client = new Client(1, "John Doe", "john@example.com", null);
        when(clientDAO.getClientById(1)).thenReturn(client);

        Client foundClient = clientController.getClientById(1);

        assertNotNull(foundClient);
        assertEquals(1, foundClient.getId());
        assertEquals("John Doe", foundClient.getName());
        assertEquals("john@example.com", foundClient.getEmail());
    }

    @Test
    public void testGetAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "John Doe", "john@example.com", null));
        clients.add(new Client(2, "Jane Doe", "jane@example.com", null));
        when(clientDAO.getAllClients()).thenReturn(clients);

        List<Client> allClients = clientController.getAllClients();

        assertNotNull(allClients);
        assertEquals(2, allClients.size());
        assertEquals(1, allClients.get(0).getId());
        assertEquals("John Doe", allClients.get(0).getName());
        assertEquals("john@example.com", allClients.get(0).getEmail());
        assertEquals(2, allClients.get(1).getId());
        assertEquals("Jane Doe", allClients.get(1).getName());
        assertEquals("jane@example.com", allClients.get(1).getEmail());
    }

    @Test
    public void testUpdateClient() throws SQLException {
        Client client = new Client(1, "Updated Name", "updated@example.com", null);

        clientController.updateClient(client);

        verify(clientDAO, times(1)).updateClient(client);
    }

    @Test
    public void testDeleteClient() throws SQLException {
        clientController.deleteClient(1);

        verify(clientDAO, times(1)).deleteClient(1);
    }

    @Test
    public void testGetClientOrderHistory() throws SQLException {
        List<Order> orderHistory = new ArrayList<>();
        orderHistory.add(new Order(1, null, null, 100.0, null, new Date()));
        when(clientDAO.getOrderHistoryByClientId(1)).thenReturn(orderHistory);

        List<Order> clientOrderHistory = clientController.getClientOrderHistory(1);

        assertNotNull(clientOrderHistory);
        assertEquals(1, clientOrderHistory.size());
        assertEquals(1, clientOrderHistory.get(0).getId());
        assertEquals(100.0, clientOrderHistory.get(0).getTotal(), 0.01);
    }
}