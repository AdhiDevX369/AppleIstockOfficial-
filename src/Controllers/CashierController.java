package Controllers;

import DAO.ProductDAO;
import DAO.ProductDataDAO;
import DAO.SalesDAO;
import Models.Product;
import Models.ProductData;
import Models.User;
import java.util.List;

public class CashierController extends User {

    private ProductDataDAO productDataDAO;
    private SalesDAO salesDAO;
    private ProductDAO productDAO;

    public CashierController(int userId, String username, String password, String name, String address, String email, String mobile, String nic) {
        super(userId, username, password, "Cashier", name, address, email, mobile, nic);
        productDataDAO = new ProductDataDAO();
        salesDAO = new SalesDAO();
        productDAO = new ProductDAO();
    }

    // Cashier-specific functionalities can be added here
    // Example: methods for viewing products, creating invoices, adding sales, etc.
    // For instance:
    public List<ProductData> viewAllProductData() {
        return productDataDAO.viewAllProductData();
    }

    public List<Product> viewAllCategoryData() {
        return productDAO.getAllProducts();
    }

    public List<ProductData> getProductsByStatus(String status) {
        if ("sold".equals(status)) {
            return productDataDAO.getSoldProducts();
        } else if ("unsold".equals(status)) {
            return productDataDAO.getUnsoldProducts();
        } else {
            // Handle other cases or invalid status here
            // For example, return all products if the status is not recognized
            return productDataDAO.viewAllProductData(); // Return all products
        }
    }

    public List<ProductData> getSearchedData(String keyword) {
        return productDataDAO.searchProduct(keyword);
    }

    public int getProductId(String productName) {
        return productDataDAO.getProductIdByName(productName);
    }
    public boolean makeSale(int userId, int productId, int soldQuantity, double income, List<String> eminumList, String invoiceId) {
    // Assuming 'cashierController' is an instance of CashierController

    // Perform necessary operations to fetch data or generate required values

    // Call the method to create a sale
    return salesDAO.saleCreate(userId, productId, soldQuantity, income, eminumList, invoiceId);
}

    // Additional methods for handling sales-related tasks, etc.
}
