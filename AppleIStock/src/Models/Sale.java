package Models;

import java.util.Date;

public class Sale {

    private int saleId;
    private int productId;
    private int userId;
    private String nic;
    private Date saleDate;
    private int saleQuantity;
    private double salePrice;
    private String invoice_number;

    // Constructors, getters, and setters
    public Sale(int saleId, int productId, int userId, String nic, Date saleDate, int saleQuantity, double salePrice, String invoice_number) {
        this.saleId = saleId;
        this.productId = productId;
        this.userId = userId;
        this.nic = nic;
        this.saleDate = saleDate;
        this.saleQuantity = saleQuantity;
        this.salePrice = salePrice;
        this.invoice_number =invoice_number;
    }

    // Add getters and setters for all attributes
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getInvoiceNumber() {
        return invoice_number;
    }

    public void setInvoiceNumber(String invoice_number) {
        this.invoice_number = invoice_number;
    }

}
