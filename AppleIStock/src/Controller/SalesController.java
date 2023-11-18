package Controller;

import Models.Sale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;

public class SalesController {

    // Method to get all sales records
    public List<Sale> getAllSales() {
        List<Sale> salesList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getInstance().getConnection();
            String query = "SELECT * FROM sales";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Create Sale object and set its attributes based on the result set
                Sale sale = new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("userid"),
                        resultSet.getString("nic"),
                        resultSet.getDate("sale_date"),
                        resultSet.getInt("sale_quantity"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getString("invoice_number")
                );

                // Add Sale object to the list
                salesList.add(sale);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return salesList;
    }

    public List<Sale> getSalesByInvoiceNumber(String invoiceNumber) {
        List<Sale> salesList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getInstance().getConnection();
            String query = "SELECT * FROM sales WHERE invoice_number = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, invoiceNumber);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Create Sale object and set its attributes based on the result set
                Sale sale = new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("userid"),
                        resultSet.getString("nic"),
                        resultSet.getDate("sale_date"),
                        resultSet.getInt("sale_quantity"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getString("invoice_number")
                );

                // Add Sale object to the list
                salesList.add(sale);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return salesList;
    }

    public Map<String, Object> getDailySales(Date date) {
        Map<String, Object> dailySalesData = new HashMap<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getInstance().getConnection();
            String query = "SELECT * FROM sales WHERE DATE(sale_date) = ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, (java.sql.Date) date);
            resultSet = statement.executeQuery();

            int totalQuantity = 0;
            double totalSalesPrice = 0;
            String mostSellerUserName = null;
            int maxQuantity = 0;

            while (resultSet.next()) {
                // Create Sale object and set its attributes based on the result set
                Sale sale = new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("userid"),
                        resultSet.getString("nic"),
                        resultSet.getDate("sale_date"),
                        resultSet.getInt("sale_quantity"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getString("invoice_number")
                );

                // Update total quantity and total sales price
                totalQuantity += sale.getSaleQuantity();
                totalSalesPrice += sale.getSalePrice();

                // Update most seller user name
                int currentQuantity = sale.getSaleQuantity();
                if (currentQuantity > maxQuantity) {
                    maxQuantity = currentQuantity;
                    mostSellerUserName = sale.getNic();
                }
            }

            // Add the calculated data to the map
            dailySalesData.put("totalQuantity", totalQuantity);
            dailySalesData.put("totalSalesPrice", totalSalesPrice);
            dailySalesData.put("mostSellerUserName", mostSellerUserName);

        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the resources
            // (Omitted for brevity)
        }

        return dailySalesData;
    }

    public List<Sale> getSalesByDate(Date date) {
        List<Sale> dailySalesList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getInstance().getConnection();
            String query = "SELECT * FROM sales WHERE DATE(sale_date) = ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, (java.sql.Date) date);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Create Sale object and set its attributes based on the result set
                Sale sale = new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("userid"),
                        resultSet.getString("nic"),
                        resultSet.getDate("sale_date"),
                        resultSet.getInt("sale_quantity"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getString("invoice_number")
                );

                // Add Sale object to the list
                dailySalesList.add(sale);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return dailySalesList;

    }
}
