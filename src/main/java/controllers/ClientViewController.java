package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Client;
import models.Order;
import services.ClientService;

import java.util.List;

public class ClientViewController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    private ClientService clientService;
    private ObservableList<Client> clientList;

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
        loadClients();
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        clientList = FXCollections.observableArrayList();
        clientTableView.setItems(clientList);
    }

    private void loadClients() {
        try {
            clientList.setAll(clientService.listAllClients());
        } catch (Exception e) {
            System.err.println("Error al cargar los clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddClient() {
        if (validateInput()) {
            String name = nameField.getText();
            String email = emailField.getText();
            Client client = new Client(0, name, email, null);
            clientService.registerClient(client);
            clientList.add(client);
        }
    }

    @FXML
    private void handleDeleteClient() {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            clientService.removeClient(selectedClient.getId());
            clientList.remove(selectedClient);
        }
    }

    @FXML
    private void handleViewOrderHistory() {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            List<Order> orderHistory = clientService.getClientOrderHistory(selectedClient.getId());
            showOrderHistory(orderHistory);
        }
    }

    private void showOrderHistory(List<Order> orderHistory) {
        // Implementa la lógica para mostrar el historial de pedidos del cliente
        // Puedes abrir una nueva ventana o mostrar una alerta con la información
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Historial de Compras");
        alert.setHeaderText("Historial de Compras del Cliente");
        StringBuilder content = new StringBuilder();
        for (Order order : orderHistory) {
            content.append("Pedido ID: ").append(order.getId())
                    .append(", Total: ").append(order.getTotal())
                    .append(", Estado: ").append(order.getOrderStatus())
                    .append("\n");
        }
        alert.setContentText(content.toString());
        alert.showAndWait();
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            errorMessage += "Nombre no válido!\n";
        }
        if (emailField.getText() == null || emailField.getText().isEmpty()) {
            errorMessage += "Correo Electrónico no válido!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos no válidos");
            alert.setHeaderText("Por favor, corrija los campos no válidos");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}