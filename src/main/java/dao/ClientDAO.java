package dao;

import model.Client;
import model.Order;
import model.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public void addClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar el cliente");
            e.printStackTrace();
        }
    }

    public Client getClientById(int clientId) throws SQLException {
        String query = "SELECT * FROM clients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    List<Order> orderHistory = getOrderHistoryByClientId(clientId); // Obtener el historial de pedidos
                    return new Client(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            orderHistory // Asignar el historial de pedidos
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el cliente por ID");
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int clientId = rs.getInt("id");
                List<Order> orderHistory = getOrderHistoryByClientId(clientId); // Obtener el historial de pedidos
                clients.add(new Client(
                        clientId,
                        rs.getString("name"),
                        rs.getString("email"),
                        orderHistory // Asignar el historial de pedidos
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes");
            e.printStackTrace();
        }
        return clients;
    }

    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE clients SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setInt(3, client.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el cliente");
            e.printStackTrace();
        }
    }

    public void deleteClient(int clientId) throws SQLException {
        String query = "DELETE FROM clients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el cliente");
            e.printStackTrace();
        }
    }

    public List<Order> getOrderHistoryByClientId(int clientId) throws SQLException {
        List<Order> orderHistory = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE client_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderHistory.add(new Order(
                        rs.getInt("id"),
                        new Client(clientId),
                        new ArrayList<>(), // Deberías obtener los productos de otra tabla
                        rs.getDouble("total"),
                        OrderStatus.valueOf(rs.getString("status"))
                ));
            }
        }
        return orderHistory;
    }
}
