/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Adithya
 */
public class Mac extends Product {

    private String model;

    public Mac(int productId, String name, double price, int stockQuantity, String model) {
        super(productId, name, price, "Apple Watch", stockQuantity);
        this.model = model;
    }

}
