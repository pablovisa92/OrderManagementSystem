package services;

import controllers.ClientController;
import models.Client;
import models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientController clientController;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterClient() {
        Client client = new Client(1, "John Doe", "john@example.com", null);

        clientService.registerClient(client);

        verify(clientController, times(1)).addClient(client);
    }

    @Test
    public void testFindClientById() {
        Client client = new Client(1, "John Doe", "john@example.com", null);
        when(clientController.getClientById(1)).thenReturn(client);

        Client foundClient = clientService.findClientById(1);

        assertNotNull(foundClient);
        assertEquals(1, foundClient.getId());
        assertEquals("John Doe", foundClient.getName());
        assertEquals("john@example.com", foundClient.getEmail());
    }

    @Test
    public void testListAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "John Doe", "john@example.com", null));
        clients.add(new Client(2, "Jane Doe", "jane@example.com", null));
        when(clientController.getAllClients()).thenReturn(clients);

        List<Client> allClients = clientService.listAllClients();

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
    public void testUpdateClientInfo() {
        Client client = new Client(1, "Updated Name", "updated@example.com", null);

        clientService.updateClientInfo(client);

        verify(clientController, times(1)).updateClient(client);
    }

    @Test
    public void testRemoveClient() {
        clientService.removeClient(1);

        verify(clientController, times(1)).deleteClient(1);
    }

    @Test
    public void testGetClientOrderHistory() {
        List<Order> orderHistory = new ArrayList<>();
        orderHistory.add(new Order(1, null, null, 100.0, null, new Date()));
        when(clientController.getClientOrderHistory(1)).thenReturn(orderHistory);

        List<Order> clientOrderHistory = clientService.getClientOrderHistory(1);

        assertNotNull(clientOrderHistory);
        assertEquals(1, clientOrderHistory.size());
        assertEquals(1, clientOrderHistory.get(0).getId());
        assertEquals(100.0, clientOrderHistory.get(0).getTotal(), 0.01);
    }
}