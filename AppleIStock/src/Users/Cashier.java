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

public class Cashier extends User {

    public Cashier(int userId, String username, String password) {
        super(userId, username, password, "Cashier");
    }

    // Cashier-specific methods
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

    public boolean sellProduct(int productId, int userId, int quantity, double price) {
        Connection connection = null;
        PreparedStatement sellStatement = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();

            // Prepare a SQL statement to insert a record into the sales table
            String insertSalesSQL = "INSERT INTO sales (product_id, userid, sale_date, sale_quantity) VALUES (?, ?, NOW(), ?)";
            sellStatement = connection.prepareStatement(insertSalesSQL);
            sellStatement.setInt(1, productId);
            sellStatement.setInt(2, userId);
            sellStatement.setInt(3, quantity);

            // Disable auto-commit to ensure transaction consistency
            connection.setAutoCommit(false);

            // Execute the sales record insertion
            sellStatement.executeUpdate();

            // Commit the transaction
            connection.commit();

            // Update the product quantity
            boolean updateSuccess = updateProductQuantity(productId, quantity);

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
                    Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public boolean updateProductQuantity(int productId, int quantity) {
        Connection connection = null;
        PreparedStatement updateStatement = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();

            // Prepare a SQL statement to update the product quantity
            String updateProductSQL = "UPDATE product SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
            updateStatement = connection.prepareStatement(updateProductSQL);
            updateStatement.setInt(1, quantity);
            updateStatement.setInt(2, productId);

            // Execute the product quantity update
            updateStatement.executeUpdate();

            return true; // Update was successful
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
                    Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
