package Users;

import Database.DatabaseConnection;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager extends User {

    public Manager(int userId, String username, String password) {
        super(userId, username, password, "Manager");
    }

    public void createAccount(String newUsername, String newPassword) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Check if the username is unique (assuming 'username' is unique in the 'user' table)
            String checkQuery = "SELECT * FROM user WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, newUsername);

            // If the username is not already in use, create the account
            if (!checkStatement.executeQuery().next()) {
                String insertQuery = "INSERT INTO user (username, password, position) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, newUsername);
                preparedStatement.setString(2, newPassword);
                preparedStatement.setString(3, "Cashier");

                int rowsAffected = preparedStatement.executeUpdate();

                preparedStatement.close();

                if (rowsAffected > 0) {
                    System.out.println("Account created successfully.");
                } else {
                    System.out.println("Account creation failed.");
                }
            } else {
                System.out.println("Username is already in use.");
            }

            checkStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addProduct(String productName, double price, String category, int stockQuantity) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            String query = "INSERT INTO product (name, category, price, stock_quantity) VALUES ( ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, stockQuantity);

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

    public void updateProduct(String productName, double newPrice) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            String query = "UPDATE product SET price = ? WHERE product_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, productName);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> viewProductDetails() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

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

    public List<Product> searchProduct(String keyword) {
        List<Product> searchResults = new ArrayList<>();

        // Implement the search logic to query the database based on the keyword
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

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

    public boolean removeProductFromDatabase(int productId) {
        Connection connection = null;
        PreparedStatement removeProductStatement = null;
        PreparedStatement removeSalesStatement = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();

            // Disable auto-commit to ensure transaction consistency
            connection.setAutoCommit(false);

            // Prepare a SQL statement to select sales records for the product
            String selectSalesSQL = "SELECT sale_id FROM sales WHERE product_id = ?";
            removeSalesStatement = connection.prepareStatement(selectSalesSQL);
            removeSalesStatement.setInt(1, productId);

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
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (removeSalesStatement != null) {
                try {
                    removeSalesStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean updateCurrentProduct(int productId, String productName, double price, String category, int stockQuantity) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

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

    public void analyzeSales() {
        // Implementation to analyze sales
    }

}
