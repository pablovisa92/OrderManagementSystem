package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import models.Client;
import models.Order;
import models.OrderStatus;
import models.Product;
import services.ClientService;
import services.OrderService;
import services.ProductService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderViewController {

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private ComboBox<Product> productComboBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TableView<Order> orderTableView;

    @FXML
    private TableColumn<Order, String> clientColumn;

    @FXML
    private TableColumn<Order, String> productsColumn;

    @FXML
    private TableColumn<Order, Double> totalColumn;

    @FXML
    private TableColumn<Order, OrderStatus> statusColumn;

    private OrderService orderService;
    private ClientService clientService;
    private ProductService productService;
    private ObservableList<Order> orderList;
    private ObservableList<Product> selectedProducts;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
        loadOrders();
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
        clientComboBox.setItems(FXCollections.observableArrayList(clientService.listAllClients()));

        clientComboBox.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client client) {
                return client != null ? client.getName() : "";
            }

            @Override
            public Client fromString(String string) {
                return clientComboBox.getItems().stream()
                        .filter(client -> client.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
        productComboBox.setItems(FXCollections.observableArrayList(productService.listAllProducts()));

        productComboBox.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                return product != null ? product.getName() : "";
            }

            @Override
            public Product fromString(String string) {
                return productComboBox.getItems().stream()
                        .filter(product -> product.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    @FXML
    private void initialize() {
        clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getName()));
        productsColumn.setCellValueFactory(cellData -> {
            List<String> productDescriptions = cellData.getValue().getProducts().stream()
                    .collect(Collectors.groupingBy(product -> product, Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> entry.getKey().getName() + " (x" + entry.getValue() + ")")
                    .collect(Collectors.toList());
            return new SimpleStringProperty(String.join(", ", productDescriptions));
        });
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        orderList = FXCollections.observableArrayList();
        orderTableView.setItems(orderList);

        selectedProducts = FXCollections.observableArrayList();
    }

    private void loadOrders() {
        try {
            orderList.setAll(orderService.listAllOrders());
        } catch (Exception e) {
            System.err.println("Error al cargar los pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProductToOrder() {
        Product selectedProduct = productComboBox.getSelectionModel().getSelectedItem();
        String quantityText = quantityTextField.getText();
        if (selectedProduct != null && quantityText != null && !quantityText.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityText);
                for (int i = 0; i < quantity; i++) {
                    selectedProducts.add(selectedProduct);
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "La cantidad debe ser un número válido.");
            }
        }
    }

    @FXML
    private void handlePlaceOrder() {
        Client selectedClient = clientComboBox.getSelectionModel().getSelectedItem();
        if (selectedClient != null && !selectedProducts.isEmpty()) {
            double total = selectedProducts.stream().mapToDouble(Product::getPrice).sum();
            Date date = new Date(); // Obtener la fecha actual
            Order order = new Order(0, selectedClient, selectedProducts, total, OrderStatus.PENDING, date);
            orderService.placeOrder(order);
            orderList.add(order);
            selectedProducts.clear();
        } else {
            showAlert("Error", "Debe seleccionar un cliente y al menos un producto.");
        }
    }

    @FXML
    private void handleUpdateOrderStatus() {
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setOrderStatus(OrderStatus.SHIPPED); // Ejemplo de actualización de estado
            orderService.changeOrderStatus(selectedOrder.getId(), selectedOrder.getOrderStatus());
            orderTableView.refresh();
        }
    }

    @FXML
    private void handleDeleteOrder() {
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderService.cancelOrder(selectedOrder.getId());
            orderList.remove(selectedOrder);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}