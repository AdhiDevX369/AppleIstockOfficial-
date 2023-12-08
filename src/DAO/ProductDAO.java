/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Controllers.DBController;
import Models.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Adithya
 */
public class ProductDAO {

    private DBController dbController;

    public ProductDAO() {
         dbController = DBController.getInstance();// Assuming you have a DatabaseController class
    }

    public boolean createProduct(String category, int stockQuantity, double price) {
        String query = "INSERT INTO Products (category, stock_quantity, price) VALUES (?, ?, ?)";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, stockQuantity);
            preparedStatement.setDouble(3, price);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., product created)

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dbController.getConnection();
            String query = "SELECT * FROM Products"; // Modify this query as per your database schema
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("productid"));
                product.setCategory(resultSet.getString("category"));
                product.setStockQuantity(resultSet.getInt("stock_quantity"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions
        }
        return products;
    }

    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM Products WHERE productid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setInt(1, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., product deleted)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }

    }

    public boolean updateProduct(int productId, String category, int stockQuantity) {
        String query = "UPDATE Products SET category = ?, stock_quantity = ?  WHERE productid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, stockQuantity);
            preparedStatement.setInt(3, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., product updated)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

}
