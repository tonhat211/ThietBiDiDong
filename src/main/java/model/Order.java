package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int id;
    private double money;
    private int userID;
    private String address;
    private LocalDateTime dateSet;
    private LocalDateTime dateFinish;
    private int status;

    public Order() {
    }

    public Order(double money, int userID, String address) {
        this.money = money;
        this.userID = userID;
        this.address = address;
    }

    public Order(int id, double money, int userID, String address, LocalDateTime dateSet, LocalDateTime dateFinish, int status) {
        this.id = id;
        this.money = money;
        this.userID = userID;
        this.address = address;
        this.dateSet = dateSet;
        this.dateFinish = dateFinish;
        this.status = status;
    }

    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDateTime dateFinish) {
        this.dateFinish = dateFinish;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getDateSet() {
        return dateSet;
    }

    public void setDateSet(LocalDateTime dateSet) {
        this.dateSet = dateSet;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
