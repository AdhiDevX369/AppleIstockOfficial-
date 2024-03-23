package Controllers;

import DAO.ProductDataDAO;
import DAO.ProductDAO;
import DAO.SalesDAO;
import DAO.UserDAO;
import Models.Product;
import Models.ProductData;
import Models.Sales;
import Models.User;
import java.util.List;


public class ManagerController extends User {

    private ProductDataDAO productDataDAO;
    private ProductDAO productDAO;
    private SalesDAO salesDAO;
    private UserDAO userDAO;

    public ManagerController(int userId, String username, String password, String name, String address, String email, String phone, String nic) {
        super(userId, username, password, "Manager", name, address, email, phone, nic);
        productDataDAO = new ProductDataDAO();
        productDAO = new ProductDAO();
        salesDAO = new SalesDAO();
        userDAO = new UserDAO();
    }

    //ProductManagement
    public List<ProductData> viewAllProductData() {
        return productDataDAO.viewAllProductData();
    }

    public List<Product> getAllProductCategories() {
        return productDAO.getAllProducts();
    }

    public boolean createProductCategory(String category, int stockQuantity, double price) {
        return productDAO.createProduct(category, stockQuantity, price);
    }

    public boolean deleteProductCategory(int productId) {
        return productDAO.deleteProduct(productId);
    }

    public boolean updateProduct(int productId, String category, int stockQuantity) {
        return productDAO.updateProduct(productId, category, stockQuantity);
    }

    public boolean createProductData(String productName, int productId, String category, String eminum, String status, double price) {
        return productDataDAO.createProductData(productName, productId, category, eminum, status, price);
    }

    public boolean deleteProductData(String eminum) {
        return productDataDAO.deleteProductData(eminum);
    }

    public boolean updateProductData(String eminum, String newProductName, String newCategory, int newProductId, String newStatus, double newPrice) {
        return productDataDAO.updateProductData(eminum, newProductName, newCategory, newProductId, newStatus, newPrice);
    }

    public int getProductIdByName(String productName) {
        return productDataDAO.getProductIdByName(productName);
    }

    public List<ProductData> getSearchedData(String keyword) {
        return productDataDAO.searchProduct(keyword);
    }

    public List<ProductData> getProductsByStatus(String status) {
        if ("sold".equals(status)) {
            return productDataDAO.getSoldProducts();
        } else if ("unsold".equals(status)) {
            return productDataDAO.getUnsoldProducts();
        }
        if ("all".equals(status)) {
            return productDataDAO.viewAllProductData();
        } else {
            // Handle other cases or invalid status here
            // For example, return all products if the status is not recognized
            return productDataDAO.viewAllProductData(); // Return all products
        }
    }

    //UserManagement
    public List<User> getCashiers() {
        return userDAO.retrieveCashierUsers();
    }

    public List<User> getAllUsers() {
        return userDAO.viewAllUserData();
    }

    public List<User> getSearchUsers(String keyword) {
        return userDAO.searchUserData(keyword);
    }

    public boolean createUser(String username, String password, String firstName, String lastName, String mobile,
            String email, String street, String city, String country, String position, String nic) {
        return userDAO.createUser(username, password, firstName, lastName, mobile, email, street, city, country, position, nic);
    }

    public boolean updateUser(String username, String newPassword, String newFirstName, String newLastName,
            String newMobile, String newEmail, String newStreet, String newCity,
            String newCountry, String newNic) {
        return userDAO.updateUser(username, newPassword, newFirstName, newLastName, newMobile, newEmail, newStreet, newCity, newCountry, newNic);
    } // Additional methods for managing products, sales, users, etc.

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    //ManageSales
    public User getMostSalesUser() {
        return salesDAO.getMostSalesUser();
    }

    public String getFirstNameByID(int keyword) {
        return userDAO.getFirstNameByID(keyword);
    }

    public String getLastNameByID(int keyword) {

        return userDAO.getLastNameByID(keyword);
    }

    public String getStreetByID(int keyword) {

        return userDAO.getStreetByID(keyword);
    }

    public String getCityByID(int keyword) {

        return userDAO.getCityByID(keyword);
    }

    public String getCountryByID(int keyword) {

        return userDAO.getCountryByID(keyword);
    }

    public List<Sales> viewAllSales() {
        return salesDAO.viewAllSales();
    }
    public List<Sales> searchSalesByInvoiceId(String invoiceId){
       return salesDAO.searchSalesByInvoiceId(invoiceId);
    }


}
