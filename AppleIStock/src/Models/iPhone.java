package Models;

public class iPhone extends Product {

    private String model;

    public iPhone(int productId, String name, double price, int stockQuantity, String model) {
        super(productId, name, price, "iPhone", stockQuantity);
        this.model = model;
    }
  
}
