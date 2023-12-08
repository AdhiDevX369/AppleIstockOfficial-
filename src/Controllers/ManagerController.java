package Controllers;

import DAO.ProductDataDAO;
import DAO.ProductDAO;
import DAO.SalesDAO;
import DAO.UserDAO;
import Models.Product;
import Models.ProductData;
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

    // Manager-specific functionalities can be added here
    // Example: methods to manage products, sales, and users
    // For instance:
    public List<ProductData> viewAllProductData() {
        return productDataDAO.viewAllProductData();
    }

    // Additional methods for managing products, sales, users, etc.
}
