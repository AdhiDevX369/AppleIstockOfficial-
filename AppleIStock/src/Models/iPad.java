package Models;

public class iPad extends Product {

    private String model;

    public iPad(int productId, String name, double price, int stockQuantity, String model) {
        super(productId, name, price, "iPad", stockQuantity);
        this.model = model;
    }
  
}
