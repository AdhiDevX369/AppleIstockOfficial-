package Models;

public abstract class Product {
    private int productId;
    private String name;
    private String category;
    private double price;
    int stockQuantity;

    public Product(int productId, String name, double price, String category, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters for product attributes
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setQuantity(int quantity) {
        this.stockQuantity = quantity;
    }
    
    
}
