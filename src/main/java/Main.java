import controllers.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DataBaseConnection;
import daos.ClientDAO;
import daos.OrderDAO;
import daos.ProductDAO;
import controllers.ClientController;
import controllers.OrderController;
import controllers.ProductController;
import services.ClientService;
import services.OrderService;
import services.ProductService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {
    private static ClientService clientService;
    private static OrderService orderService;
    private static ProductService productService;
    private static Connection connection;

    @Override
    public void start(Stage primaryStage) {
        try {
            connection = DataBaseConnection.getConnection();
            // Inicializar DAOs
            ClientDAO clientDAO = new ClientDAO(connection);
            OrderDAO orderDAO = new OrderDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);

            // Inicializar Controladores
            ClientController clientController = new ClientController(clientDAO);
            OrderController orderController = new OrderController(orderDAO);
            ProductController productController = new ProductController(productDAO);

            // Inicializar Servicios
            clientService = new ClientService(clientController);
            orderService = new OrderService(orderController);
            productService = new ProductService(productController);

            // Cargar la vista principal
            showMainView(primaryStage);

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    private void showMainView(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();

            // Pasar los servicios a la vista principal si es necesario
            MainViewController controller = loader.getController();
            controller.setClientService(clientService);
            controller.setOrderService(orderService);
            controller.setProductService(productService);

            primaryStage.setTitle("Sistema de Gestión de Pedidos");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la vista principal.");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}