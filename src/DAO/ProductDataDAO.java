package DAO;

import Controllers.DBController;
import Models.ProductData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDataDAO {

    private DBController dbController;

    public ProductDataDAO() {
        dbController = DBController.getInstance(); // Assuming you have a DatabaseController class
    }

    public List<ProductData> viewAllProductData() {
        List<ProductData> productDataList = new ArrayList<>();

        try (Connection connection = dbController.getConnection(); Statement statement = connection.createStatement();) {

            String query = "SELECT * FROM ProductData";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                ProductData productData = new ProductData();
                productData.setProductName(resultSet.getString("productname"));
                productData.setProductId(resultSet.getInt("productid"));
                productData.setCategory(resultSet.getString("category"));
                productData.setEminum(resultSet.getString("eminum"));
                productData.setStatus(resultSet.getString("status"));
                productData.setPrice(resultSet.getDouble("price"));

                // Set other attributes based on column names in the table
                productDataList.add(productData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return productDataList;
    }

    // Other methods follow the same try-with-resources pattern...
    public List<ProductData> searchProduct(String keyword) {
        List<ProductData> productList = new ArrayList<>();
        String query = "SELECT productname, productid, category, eminum, status, price "
                + "FROM ProductData "
                + "WHERE productname LIKE ? OR category LIKE ? OR eminum LIKE ? OR price LIKE ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                preparedStatement.setString(i, searchPattern);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProductData productData = new ProductData();
                productData.setProductName(resultSet.getString("productname"));
                productData.setProductId(resultSet.getInt("productid"));
                productData.setCategory(resultSet.getString("category"));
                productData.setEminum(resultSet.getString("eminum"));
                productData.setStatus(resultSet.getString("status"));
                productData.setPrice(resultSet.getDouble("price"));

                productList.add(productData);
            }

            // Close the ResultSet here since it's no longer needed
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return productList;
    }

    public List<ProductData> getUnsoldProducts() {
        List<ProductData> unsoldProducts = new ArrayList<>();
        String query = "SELECT * FROM ProductData WHERE status = 'unsold'";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                // Create ProductData object and populate its fields
                ProductData product = new ProductData();
                product.setProductId(resultSet.getInt("productid"));
                product.setProductName(resultSet.getString("productname"));
                product.setCategory(resultSet.getString("category"));
                product.setEminum(resultSet.getString("eminum"));
                product.setStatus(resultSet.getString("status"));
                product.setPrice(resultSet.getDouble("price"));

                // Add the unsold product to the list
                unsoldProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return unsoldProducts;
    }

    public List<ProductData> getSoldProducts() {
        List<ProductData> SoldProducts = new ArrayList<>();
        String query = "SELECT * FROM ProductData WHERE status = 'sold'";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                // Create ProductData object and populate its fields
                ProductData product = new ProductData();
                product.setProductId(resultSet.getInt("productid"));
                product.setProductName(resultSet.getString("productname"));
                product.setCategory(resultSet.getString("category"));
                product.setEminum(resultSet.getString("eminum"));
                product.setStatus(resultSet.getString("status"));
                product.setPrice(resultSet.getDouble("price"));

                // Add the sold product to the list
                SoldProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return SoldProducts;
    }

    public boolean createProductData(String productName, int productId, String category, String eminum, String status, double price) {
        String query = "INSERT INTO ProductData (productname,productid, category, eminum, status, price) VALUES (?, ?, ?, ?,?,?)";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, productId);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, eminum);
            preparedStatement.setString(5, status);
            preparedStatement.setDouble(6, price);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., product data created)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public boolean deleteProductData(String eminum) {
        String query = "DELETE FROM ProductData WHERE eminum = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setString(1, eminum);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., product data deleted)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public boolean updateProductData(String eminum, String newProductName, String newCategory, int newProductId, String newStatus) {
        String query = "UPDATE ProductData SET productid = ?, productname = ?, category = ?, status = ? WHERE eminum = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setInt(1, newProductId);
            preparedStatement.setString(2, newProductName);
            preparedStatement.setString(3, newCategory);
            preparedStatement.setString(4, newStatus);
            preparedStatement.setString(5, eminum);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., product data updated)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public int getProductIdByName(String productName) {
        String query = "SELECT productid FROM productdata WHERE productname = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("productid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions here
        }
        return -1; // Return -1 or any appropriate value to indicate failure or product not found
    }
}

// Other methods for CRUD operations on ProductData

