package model;

import java.util.List;

public class Client {
    private int id;
    private String name;
    private String email;
    private List<Order> orderHistory;


    public Client(int id, String name, String email, List<Order> orderHistory) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.orderHistory = orderHistory;
    }

    public Client(int client_id) {
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }


}
