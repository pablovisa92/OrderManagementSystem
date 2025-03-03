package services;

import controllers.ClientController;
import models.Client;
import models.Order;

import java.util.List;

public class ClientService {
    private ClientController clientController;

    public ClientService(ClientController clientController) {
        this.clientController = clientController;
    }

    public void registerClient(Client client) {
        clientController.addClient(client);
    }

    public Client findClientById(int clientId) {
        return clientController.getClientById(clientId);
    }

    public List<Client> listAllClients() {
        return clientController.getAllClients();
    }

    public void updateClientInfo(Client client) {
        clientController.updateClient(client);
    }

    public void removeClient(int clientId) {
        clientController.deleteClient(clientId);
    }

    public List<Order> getClientOrderHistory(int clientId) {
        return clientController.getClientOrderHistory(clientId);
    }
}