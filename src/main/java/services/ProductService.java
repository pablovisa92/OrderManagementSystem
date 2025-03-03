package services;

import controllers.ProductController;
import models.Product;

import java.util.List;

public class ProductService {
    private ProductController productController;

    public ProductService(ProductController productController) {
        this.productController = productController;
    }

    public void addProduct(Product product) {
        productController.addProduct(product);
    }

    public void updateProduct(Product product) {
        productController.updateProduct(product);
    }

    public void removeProduct(int productId) {
        productController.deleteProduct(productId);
    }

    public List<Product> listAllProducts() {
        return productController.getAllProducts();
    }

    public Product findProductById(int productId) {
        return productController.getProductById(productId);
    }
}