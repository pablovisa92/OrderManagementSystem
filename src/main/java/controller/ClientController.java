package controller;

import dao.ClientDAO;
import model.Client;
import model.Order;

import java.sql.SQLException;
import java.util.List;

public class ClientController {
    private ClientDAO clientDAO;

    public ClientController(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void addClient(Client client) {
        try {
            clientDAO.addClient(client);
            System.out.println("Cliente agregado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al agregar el cliente.");
            e.printStackTrace();
        }
    }

    public Client getClientById(int clientId) {
        try {
            return clientDAO.getClientById(clientId);
        } catch (SQLException e) {
            System.err.println("Error al obtener el cliente por ID.");
            e.printStackTrace();
            return null;
        }
    }

    public List<Client> getAllClients() {
        try {
            return clientDAO.getAllClients();
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes.");
            e.printStackTrace();
            return null;
        }
    }

    public void updateClient(Client client) {
        try {
            clientDAO.updateClient(client);
            System.out.println("Cliente actualizado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar el cliente.");
            e.printStackTrace();
        }
    }

    public void deleteClient(int clientId) {
        try {
            clientDAO.deleteClient(clientId);
            System.out.println("Cliente eliminado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar el cliente.");
            e.printStackTrace();
        }
    }

    public List<Order> getClientOrderHistory(int clientId) {
        try {
            return clientDAO.getOrderHistoryByClientId(clientId);
        } catch (SQLException e) {
            System.err.println("Error al obtener el historial de pedidos del cliente.");
            e.printStackTrace();
            return null;
        }
    }
}