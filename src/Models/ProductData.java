/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Adithya
 */
public class ProductData {

    private String productName;
    private int productId;
    private String category;
    private String eminum;
    private String status;
    private double price;

    // Constructors, getters, setters
    public ProductData(String productName, int productId, String category, String eminum, String status, double price) {
        this.productName = productName;
        this.productId = productId;
        this.category = category;
        this.eminum = eminum;
        this.status = status;
        this.price =price;
    }

    public ProductData() {
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public String getEminum() {
        return eminum;
    }

    public String getStatus() {
        return status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEminum(String eminum) {
        this.eminum = eminum;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
