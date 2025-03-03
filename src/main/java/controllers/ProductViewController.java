package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Product;
import services.ProductService;

public class ProductViewController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    private ProductService productService;
    private ObservableList<Product> productList;

    public void setProductService(ProductService productService) {
        this.productService = productService;
        loadProducts();
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productList = FXCollections.observableArrayList();
        productTableView.setItems(productList);
    }

    private void loadProducts() {
        try {
            productList.setAll(productService.listAllProducts());
        } catch (Exception e) {
            System.err.println("Error al cargar los productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct() {
        if (validateInput()) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            Product product = new Product(0, name, price, stock);
            productService.addProduct(product);
            productList.add(product);
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productService.removeProduct(selectedProduct.getId());
            productList.remove(selectedProduct);
        }
    }

    @FXML
    private void handleUpdateProduct() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (!nameField.getText().isEmpty()) {
                selectedProduct.setName(nameField.getText());
            }
            if (!priceField.getText().isEmpty()) {
                selectedProduct.setPrice(Double.parseDouble(priceField.getText()));
            }
            if (!stockField.getText().isEmpty()) {
                selectedProduct.setStock(Integer.parseInt(stockField.getText()));
            }
            productService.updateProduct(selectedProduct);
            productTableView.refresh();
        }
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (priceField.getText() != null && !priceField.getText().isEmpty()) {
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Precio no válido (debe ser un número)!\n";
            }
        }
        if (stockField.getText() != null && !stockField.getText().isEmpty()) {
            try {
                Integer.parseInt(stockField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Stock no válido (debe ser un número entero)!\n";
            }
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