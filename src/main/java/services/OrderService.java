package services;

import controllers.OrderController;
import models.Order;
import models.OrderStatus;

import java.util.List;

public class OrderService {
    private OrderController orderController;

    public OrderService(OrderController orderController) {
        this.orderController = orderController;
    }

    public void placeOrder(Order order) {
        orderController.createOrder(order);
    }

    public void changeOrderStatus(int orderId, OrderStatus newStatus) {
        orderController.updateOrderStatus(orderId, newStatus);
    }

    public void cancelOrder(int orderId) {
        orderController.deleteOrder(orderId);
    }

    public Order findOrderById(int orderId) {
        return orderController.getOrderById(orderId);
    }

    public List<Order> listAllOrders() {
        return orderController.getAllOrders();
    }

    public double calculateOrderTotalWithTax(Order order, double taxRate) {
        return orderController.calculateTotalWithTax(order, taxRate);
    }
}
