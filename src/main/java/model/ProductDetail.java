package model;

import java.sql.Date;
import java.util.List;

public class ProductDetail {
    private int id;
    private int productID;
    private String color;
    private int ram;
    private int rom;
    private double price;
    private int qty;

    public ProductDetail() {
    }

    public ProductDetail(int id, int productID, String color, int ram, int rom, double price, int qty) {
        this.id = id;
        this.productID = productID;
        this.color = color;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
