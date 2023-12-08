/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adithya
 */
public class SaleData {

    private int productId;
    private int quantity;
    private double totalPrice;
    private List<String> eminumList;

    public SaleData(int productId, int quantity, double totalPrice, String eminum) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.eminumList = new ArrayList<>();
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<String> getEminumList() {
        return eminumList;
    }

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void addToTotalPrice(double price) {
        this.totalPrice += price;
    }

    public void addEminum(String eminum) {
        eminumList.add(eminum);
    }
}
