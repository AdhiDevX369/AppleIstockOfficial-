package Controller;

// Import the User and Product classes from the Models package to use them in this code
import Models.User;
import Models.Product;

// Import the necessary classes for working with a SQL database
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Import ArrayList and List to work with collections of data
import java.util.ArrayList;
import java.util.List;

// Import the necessary classes for logging and handling exceptions
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerController extends User {

    public ManagerController(int userId, String username, String password, String name, String address, String email, String phone, String nic) {
        // Call the constructor of the User class, passing the provided parameters
        super(userId, username, password, "Manager", name, address, email, phone, nic);
        // The "super" keyword is used to call the constructor of the superclass (User in this case).
    }

    //* Manager Product Management
    // Add a new product to the database
    public boolean addProduct(String productName, double price, String category, int stockQuantity) {
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to insert a new product
            String query = "INSERT INTO product (name, category, price, stock_quantity) VALUES ( ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the product to be inserted// Set the parameters for the product to be inserted
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, stockQuantity);

            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();

            // Check if the product was added successfully and return a boolean value
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Return false in case of an error
            return false;
        }
    }

    // Update the price of an existing product
    public void updateProduct(String productName, double newPrice) {
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to update the price of a product
            String query = "UPDATE product SET price = ? WHERE product_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, productName);

            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve details of all products in the database
    public List<Product> viewProductDetails() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to select all products
            String query = "SELECT * FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("name");
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

    // Search Product Data
    public List<Product> searchProduct(String keyword) {
        List<Product> searchResults = new ArrayList<>();

        // Implement the search logic to query the database based on the keyword
        try {
            // Get a database connection
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to search for the keyword in relevant columns (e.g., name, price, category)
            String query = "SELECT product_id, name, price, category, stock_quantity FROM product "
                    + "WHERE name LIKE ? OR price LIKE ? OR category LIKE ? ";

            // Set search keywords in the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");

            // Execute the SQL query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                // Retrieve product details
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

    // Remove Product from Database  
    public boolean removeProductFromDatabase(int productId) {

        //Variables for connection
        Connection connection = null;
        PreparedStatement removeProductStatement = null;
        PreparedStatement removeSalesStatement = null;

        try {
            //Get Database Connction
            connection = DatabaseController.getInstance().getConnection();

            // Disable auto-commit to ensure transaction consistency
            connection.setAutoCommit(false);

            // Prepare a SQL statement to select sales records for the product
            String selectSalesSQL = "SELECT sale_id FROM sales WHERE product_id = ?";
            removeSalesStatement = connection.prepareStatement(selectSalesSQL);
            removeSalesStatement.setInt(1, productId);

            // Retrieve product details
            ResultSet salesResultSet = removeSalesStatement.executeQuery();

            // Collect the sale IDs for the related sales records
            List<Integer> saleIds = new ArrayList<>();
            while (salesResultSet.next()) {
                saleIds.add(salesResultSet.getInt("sale_id"));
            }

            // Prepare a SQL statement to delete the related sales records
            String removeSalesSQL = "DELETE FROM sales WHERE sale_id = ?";
            removeSalesStatement = connection.prepareStatement(removeSalesSQL);

            // Delete the related sales records
            for (int saleId : saleIds) {
                removeSalesStatement.setInt(1, saleId);
                removeSalesStatement.executeUpdate();
            }

            // Prepare a SQL statement to delete the product
            String removeProductSQL = "DELETE FROM product WHERE product_id = ?";
            removeProductStatement = connection.prepareStatement(removeProductSQL);
            removeProductStatement.setInt(1, productId);

            // Delete the product
            removeProductStatement.executeUpdate();

            // Commit the transaction
            connection.commit();

            return true; // Deletion was successful
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
            return false; // Deletion failed
        } finally {
            // Close the prepared statements and connection
            if (removeProductStatement != null) {
                try {
                    removeProductStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (removeSalesStatement != null) {
                try {
                    removeSalesStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Update Product Details
    public boolean updateCurrentProduct(int productId, String productName, double price, String category, int stockQuantity) {
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // Prepare an SQL statement to update the product information
            String updateQuery = "UPDATE product SET name = ?, category = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            // Set the parameters for the update statement
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, stockQuantity);
            preparedStatement.setInt(5, productId);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();

            // Check if the product was updated successfully and return a boolean value
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Return false in case of an error
            return false;
        }
    }

    // Manager User Management    
    //Create New Cashier
    public boolean addUser(String username, String password, String name, String address, String email, String phone, String nic, String position) {
        try {
            //Get Connection
            Connection connection = DatabaseController.getInstance().getConnection();

            // Check if the username is unique
            String checkQuery = "SELECT * FROM user WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);

            ResultSet resultSet = checkStatement.executeQuery();

            // If the username is not already in use, create the user
            if (!resultSet.next()) {
                String insertQuery = "INSERT INTO user (username, password, name, address, email, phone, nic, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, phone);
                preparedStatement.setString(7, nic);
                preparedStatement.setString(8, position);

                int rowsAffected = preparedStatement.executeUpdate();

                preparedStatement.close();

                // Check if the user was added successfully and return a boolean value
                return rowsAffected > 0;
            } else {
                // Username is already in use
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Return false in case of an error
            return false;
        }
    }

    // Search for Cashiers based on a keyword
    public List<User> searchCashiers(String keyword) {
        List<User> searchResults = new ArrayList<>();

        // Implement the search logic to query the database based on the keyword
        try {
            // Get Connection
            Connection connection = DatabaseController.getInstance().getConnection();

            // Modify your SQL query to search for Cashiers with the keyword in relevant columns
            String query = "SELECT userid, username, password, name, address, email, phone, nic, position FROM user "
                    + "WHERE (name LIKE ? OR username LIKE ? OR nic LIKE ? OR address LIKE ? ) "
                    + "AND position = 'Cashier'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("userid");
                String userFullName = resultSet.getString("name");
                String password = resultSet.getString("password");
                String username = resultSet.getString("username");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String position = resultSet.getString("position");
                String nic = resultSet.getString("nic");
                // Create a User object and add it to the search results
                User user = new User(userId, username, password, position, userFullName, address, email, phone, nic);
                searchResults.add(user);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    // Retrieve a list of Cashiers
    public List<User> viewCashierDetails() {
        List<User> cashiers = new ArrayList<>();

        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            String query = "SELECT * FROM user WHERE position = 'Cashier'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("userid");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String position = resultSet.getString("position");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String nic = resultSet.getString("nic");

                User user = new User(userId, username, password, position, name, address, email, phone, nic);
                cashiers.add(user);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cashiers;
    }

    // Remove a user (Cashier) from the database
    public boolean removeUser(int userId) {
        Connection connection = null;
        PreparedStatement removeUserStatement = null;
        PreparedStatement removeSalesStatement = null;

        try {
            connection = DatabaseController.getInstance().getConnection();

            // Disable auto-commit to ensure transaction consistency
            connection.setAutoCommit(false);

            // Prepare a SQL statement to select sales records related to the user
            String selectSalesSQL = "SELECT sale_id FROM sales WHERE userid = ?";
            removeSalesStatement = connection.prepareStatement(selectSalesSQL);
            removeSalesStatement.setInt(1, userId);

            ResultSet salesResultSet = removeSalesStatement.executeQuery();

            // Collect the sale IDs for the related sales records
            List<Integer> saleIds = new ArrayList<>();
            while (salesResultSet.next()) {
                saleIds.add(salesResultSet.getInt("sale_id"));
            }

            // Prepare a SQL statement to delete the related sales records
            String removeSalesSQL = "DELETE FROM sales WHERE sale_id = ?";
            removeSalesStatement = connection.prepareStatement(removeSalesSQL);

            // Delete the related sales records
            for (int saleId : saleIds) {
                removeSalesStatement.setInt(1, saleId);
                removeSalesStatement.executeUpdate();
            }

            // Prepare a SQL statement to delete the user
            String removeUserSQL = "DELETE FROM user WHERE userid = ?";
            removeUserStatement = connection.prepareStatement(removeUserSQL);
            removeUserStatement.setInt(1, userId);

            // Delete the user
            removeUserStatement.executeUpdate();

            // Commit the transaction
            connection.commit();

            return true; // Deletion was successful
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
            return false; // Deletion failed
        } finally {
            // Close the prepared statements and connection
            if (removeUserStatement != null) {
                try {
                    removeUserStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (removeSalesStatement != null) {
                try {
                    removeSalesStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Update Cashier's Data 
    public boolean updateUserData(int userId, String username, String password, String position, String name, String address, String email, String phone, String nic) {
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // Prepare an SQL statement to update the user information
            String updateQuery = "UPDATE user SET username = ?, password = ?,position = ?, name = ?, address = ?, email = ?, phone = ?, nic = ? WHERE userid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            // Set the parameters for the update statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, position);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, phone);
            preparedStatement.setString(8, nic);
            preparedStatement.setInt(9, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();

            // Check if the user was updated successfully and return a boolean value
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Return false in case of an error
            return false;
        }
    }

}
