package Models;
public class Product {
    public Product() {
       }
    private int productId;
    private String category;
    private int stockQuantity;
    private double price;
    // Constructors, getters, setters
    public Product(int productId, String category, int stockQuantity, double price) {
        this.productId = productId;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }
    public int getProductId() {
        return productId;
    }
    public String getCategory() {
        return category;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public double getPrice() {
        return price;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
