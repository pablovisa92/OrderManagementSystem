package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import services.ClientService;
import services.OrderService;
import services.ProductService;

public class MainViewController {

    @FXML
    private Button clientManagementButton;

    @FXML
    private Button orderManagementButton;

    @FXML
    private Button productManagementButton;

    private ClientService clientService;
    private OrderService orderService;
    private ProductService productService;

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @FXML
    private void handleClientManagement() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ClientView.fxml"));
        Parent root = loader.load();

        ClientViewController controller = loader.getController();
        controller.setClientService(clientService);

        stage.setTitle("Gestión de Clientes");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleOrderManagement() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OrderView.fxml"));
        Parent root = loader.load();

        OrderViewController controller = loader.getController();
        controller.setOrderService(orderService);
        controller.setClientService(clientService);
        controller.setProductService(productService);

        stage.setTitle("Gestión de Pedidos");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleProductManagement() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProductView.fxml"));
        Parent root = loader.load();

        ProductViewController controller = loader.getController();
        controller.setProductService(productService);

        stage.setTitle("Gestión de Productos");
        stage.setScene(new Scene(root));
        stage.show();
    }
}