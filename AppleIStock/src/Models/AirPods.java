/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class AirPods extends Product {

    private String model;

    public AirPods(int productId, String name, double price, int stockQuantity, String model) {
        super(productId, name, price, "Apple Watch", stockQuantity);
        this.model = model;
    }

   
}
