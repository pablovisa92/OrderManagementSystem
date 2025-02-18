package service;

import controller.ProductController;
import model.Product;

import java.util.List;

public class ProductService {
    private ProductController productController;

    public ProductService(ProductController productController) {
        this.productController = productController;
    }

    public void addProduct(Product product) {
        productController.addProduct(product);
    }

    public Product findProductById(int productId) {
        return productController.getProductById(productId);
    }

    public List<Product> listAllProducts() {
        return productController.getAllProducts();
    }

    public void updateProductInfo(Product product) {
        productController.updateProduct(product);
    }

    public void removeProduct(int productId) {
        productController.deleteProduct(productId);
    }
}