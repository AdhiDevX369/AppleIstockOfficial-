package Controller;

import Models.User;        // Import the User model, which represents user information.
import Models.Product;     // Import the Product model, which represents product information.

import java.sql.Connection;        // Import the Connection class from the Java SQL package for database connections.
import java.sql.PreparedStatement;    // Import the PreparedStatement class for executing parameterized SQL queries.
import java.sql.ResultSet;        // Import the ResultSet class for retrieving query results.
import java.sql.SQLException;     // Import the SQLException class for handling database-related exceptions.
import java.util.ArrayList;        // Import the ArrayList class for creating dynamic arrays.
import java.util.List;            // Import the List interface for working with lists of objects.
import java.util.logging.Level;    // Import the Level class from the logging package to specify log levels.
import java.util.logging.Logger;    // Import the Logger class for logging messages and exceptions.


public class CashierController extends User {

    public CashierController(int userId, String username, String password, String name, String address, String email, String phone, String nic) {
        // Call the constructor of the User class, passing the provided parameters
        super(userId, username, password, "Cashier", name, address, email, phone, nic);
        // The "super" keyword is used to call the constructor of the superclass (User in this case).
    }

    // CashierController-specific methods For Products
    // Method to view product details
    public List<Product> viewProductDetails() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            String query = "SELECT * FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("name"); // Modified column name
                double price = resultSet.getDouble("price");
                String category = resultSet.getString("category");
                int stockQuantity = resultSet.getInt("stock_quantity");
                // Create a Product object and add it to the list
                Product product = new Product(productId, productName, price, category, stockQuantity) {
                };
                products.add(product);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Method to search for products
    public List<Product> searchProduct(String keyword) {
        List<Product> searchResults = new ArrayList<>();

        // Implement the search logic to query the database based on the keyword
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // Modify your SQL query to search for the keyword in relevant columns (e.g., name, price, category)
            String query = "SELECT product_id, name, price, category, stock_quantity FROM product "
                    + "WHERE name LIKE ? OR price LIKE ? OR category LIKE ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String category = resultSet.getString("category");
                int stockQuantity = resultSet.getInt("stock_quantity");
                // Create a Product object and add it to the search results
                Product product = new Product(productId, productName, price, category, stockQuantity) {
                };
                searchResults.add(product);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    // Method to update product quantity
    public boolean updateProductQuantity(int productId, int quantity) {
        Connection connection = null;
        PreparedStatement updateStatement = null;

        try {
            connection = DatabaseController.getInstance().getConnection();

            // Start by checking if there are enough quantities in stock before updating
            String checkQuantitySQL = "SELECT stock_quantity FROM product WHERE product_id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuantitySQL);
            checkStatement.setInt(1, productId);

            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                int currentQuantity = resultSet.getInt("stock_quantity");

                if (currentQuantity < quantity) {
                    // Insufficient stock
                    return false;
                }
            } else {
                // Product not found
                return false;
            }

            // Prepare a SQL statement to update the product quantity
            String updateProductSQL = "UPDATE product SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
            updateStatement = connection.prepareStatement(updateProductSQL);
            updateStatement.setInt(1, quantity);
            updateStatement.setInt(2, productId);

            // Execute the product quantity update
            int rowsAffected = updateStatement.executeUpdate();

            return rowsAffected > 0; // Update was successful if rows were affected
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions
            return false; // Update failed
        } finally {
            // Close the prepared statements and connection
            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Method to sell a product
    public boolean sellProduct(int productId, int userId, String nic, int quantity, double price) {
        Connection connection = null;
        PreparedStatement sellStatement = null;

        // Update the product quantity
        boolean updateSuccess = updateProductQuantity(productId, quantity);

        try {
            connection = DatabaseController.getInstance().getConnection();

            // Prepare a SQL statement to insert a record into the sales table
            String insertSalesSQL = "INSERT INTO sales (product_id, userid, sale_date, nic, sale_quantity, sale_price) VALUES (?, ?, NOW(), ?, ?, ?)";
            sellStatement = connection.prepareStatement(insertSalesSQL);
            sellStatement.setInt(1, productId);
            sellStatement.setInt(2, userId);
            sellStatement.setString(3, nic);
            sellStatement.setInt(4, quantity);            
            sellStatement.setDouble(5, price);
            

            // Disable auto-commit to ensure transaction consistency
            connection.setAutoCommit(false);

            // Execute the sales record insertion
            sellStatement.executeUpdate();

            // Commit the transaction
            connection.commit();

            if (!updateSuccess) {
                // Handle the case where the product quantity update failed
                return false;
            }

            return true; // Sale was successful
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions
            try {
                if (connection != null) {
                    // Rollback the transaction if an error occurs
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return false; // Sale failed
        } finally {
            // Close the prepared statements and connection
            if (sellStatement != null) {
                try {
                    sellStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CashierController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Method to get product ID by product name
    public int getProductIdByName(String productName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getInstance().getConnection();

            // Prepare a SQL statement to retrieve the product ID by product name
            String query = "SELECT product_id FROM product WHERE name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("product_id");
            }

            // Return -1 to indicate that the product was not found
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 in case of an error
        } finally {
            // Close the result set, prepared statement, and connection
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

}
