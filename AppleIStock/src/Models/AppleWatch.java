package Models;

public class AppleWatch extends Product {

    private String model;

    public AppleWatch(int productId, String name, double price, int stockQuantity, String model) {
        super(productId, name, price, "Apple Watch", stockQuantity);
        this.model = model;
    }
   
}
