/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Adithya
 */
import java.util.Date;

public class Sales {
    private int salesId;
    private int userId;
    private int productId;
    private int soldQuantity;
    private double income;
    private int invoiceId;
    private String eminum;
    private Date issuedDate;

    // Constructors, getters, setters

    public Sales(int salesId, int userId, int productId, int soldQuantity, double income, int invoiceId, String eminum, Date issuedDate) {
        this.salesId = salesId;
        this.userId = userId;
        this.productId = productId;
        this.soldQuantity = soldQuantity;
        this.income = income;
        this.invoiceId = invoiceId;
        this.eminum = eminum;
        this.issuedDate = issuedDate;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getEminum() {
        return eminum;
    }

    public void setEminum(String eminum) {
        this.eminum = eminum;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
    
}
