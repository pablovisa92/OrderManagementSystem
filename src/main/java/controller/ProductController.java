package controller;

import dao.ProductDAO;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
    private ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void addProduct(Product product) {
        try {
            productDAO.addProduct(product);
            System.out.println("Producto agregado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al agregar el producto.");
            e.printStackTrace();
        }
    }

    public Product getProductById(int productId) {
        try {
            return productDAO.getProductById(productId);
        } catch (SQLException e) {
            System.err.println("Error al obtener el producto por ID.");
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los productos.");
            e.printStackTrace();
            return null;
        }
    }

    public void updateProduct(Product product) {
        try {
            productDAO.updateProduct(product);
            System.out.println("Producto actualizado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto.");
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        try {
            productDAO.deleteProduct(productId);
            System.out.println("Producto eliminado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto.");
            e.printStackTrace();
        }
    }
}