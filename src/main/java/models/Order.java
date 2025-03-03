package models;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Client client;
    private List<Product> products;
    private double total;
    private OrderStatus orderStatus;
    private Date date;

    public Order(int id, Client client, List<Product> products, double total, OrderStatus orderStatus, Date date) {
        this.id = id;
        this.client = client;
        this.products = products;
        this.total = total;
        this.orderStatus = orderStatus;
        this.date = date;
    }

    public int getId() { return id; }

    public Client getClient() { return client; }

    public List<Product> getProducts() { return products; }

    public double getTotal() { return total; }

    public OrderStatus getOrderStatus() { return orderStatus; }

    public Date getDate() { return date; }

    public void setId(int id) { this.id = id; }

    public void setClient(Client client) { this.client = client; }

    public void setProducts(List<Product> products) { this.products = products; }

    public void setTotal(double total) { this.total = total; }

    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    public void setDate(Date date) { this.date = date; }
}