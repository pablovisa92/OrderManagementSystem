package controllers;

import daos.OrderDAO;
import models.Order;
import models.OrderStatus;


import java.sql.SQLException;
import java.util.List;

public class OrderController {
    private OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void createOrder(Order order) {
        try {
            orderDAO.createOrder(order);
            System.out.println("Pedido creado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear el pedido.");
            e.printStackTrace();
        }
    }

    public void updateOrderStatus(int orderId, OrderStatus newStatus) {
        try {
            Order order = orderDAO.getOrder(orderId);
            if (order != null) {
                order.setOrderStatus(newStatus);
                orderDAO.updateOrder(order);
                System.out.println("Estado del pedido actualizado exitosamente.");
            } else {
                System.err.println("Pedido no encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado del pedido.");
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        try {
            orderDAO.deleteOrder(orderId);
            System.out.println("Pedido eliminado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido.");
            e.printStackTrace();
        }
    }

    public Order getOrderById(int orderId) {
        try {
            return orderDAO.getOrder(orderId);
        } catch (SQLException e) {
            System.err.println("Error al obtener el pedido por ID.");
            e.printStackTrace();
            return null;
        }
    }

    public List<Order> getAllOrders() {
        try {
            return orderDAO.getAllOrders();
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los pedidos.");
            e.printStackTrace();
            return null;
        }
    }

    public double calculateTotalWithTax(Order order, double taxRate) {
        return orderDAO.calculateTotalWithTax(order, taxRate);
    }
}