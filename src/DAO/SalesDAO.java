package DAO;

import Controllers.DBController;
import Models.Sales;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    private DBController dbController;

    public SalesDAO() {
        dbController = DBController.getInstance();
    }

    public boolean saleCreate(int userId, int productId, int soldQuantity, double income, List<String> eminumList, String invoiceid) {
        String query = "INSERT INTO Sales (userid, productid, soldquantity, income, eminum, invoiceid) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, soldQuantity);
            preparedStatement.setDouble(4, income);

            // Join eminum values from the list into a single string separated by commas
            String eminums = String.join(",", eminumList);
            preparedStatement.setString(5, eminums);

            preparedStatement.setString(6, invoiceid);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., sale created)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public User getMostSalesUser() {
        User topSalesUser = null;

        String query = "SELECT u.firstname, u.lastname, u.userid, u.nic, SUM(s.income) AS total_income "
                + "FROM Users u "
                + "JOIN Sales s ON u.userid = s.userid "
                + "GROUP BY u.firstname, lastname, u.userid, u.nic "
                + "ORDER BY total_income DESC "
                + "LIMIT 1";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                int userId = resultSet.getInt("userid");
                String nic = resultSet.getString("nic");
                double totalIncome = resultSet.getDouble("total_income");

                // Create a User object with the retrieved data
                String fullname = firstname + " " + lastname;
                topSalesUser = new User(userId, fullname, "BestPerfomer", nic);
                topSalesUser.setTotalIncome(totalIncome); // Set the total income to the User object
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return topSalesUser;
    }

    public List<Sales> viewAllSales() {
        List<Sales> SaleDataList = new ArrayList<>();

        try (Connection connection = dbController.getConnection(); Statement statement = connection.createStatement();) {

            String query = "SELECT s.salesid, s.userid, s.productid, s.soldquantity, s.income, s.eminum, s.invoiceid, i.issued_date "
                    + "FROM Sales s "
                    + "INNER JOIN Invoice i ON s.invoiceid = i.invoiceid"; // Adjust the JOIN condition based on your schema

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Sales SalesData = new Sales();
                SalesData.setSalesId(resultSet.getInt("salesid"));
                SalesData.setUserId(resultSet.getInt("userid"));
                SalesData.setProductId(resultSet.getInt("productid"));
                SalesData.setSoldQuantity(resultSet.getInt("soldquantity"));
                SalesData.setIncome(resultSet.getDouble("income"));
                SalesData.setEminum(resultSet.getString("eminum"));
                SalesData.setInvoiceId(resultSet.getString("invoiceid"));
                SalesData.setIssuedDate(resultSet.getDate("issued_date")); // Assuming issuedDate is a Date type

                // Set other attributes based on column names in the tables
                SaleDataList.add(SalesData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return SaleDataList;
    }

    public List<Sales> searchSalesByInvoiceId(String invoiceId) {
        List<Sales> saleDataList = new ArrayList<>();

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT s.salesid, s.userid, s.productid, s.soldquantity, s.income, s.eminum, s.invoiceid, i.issued_date "
                + "FROM Sales s "
                + "INNER JOIN Invoice i ON s.invoiceid = i.invoiceid "
                + "WHERE s.invoiceid = ?")) {

            preparedStatement.setString(1, invoiceId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Sales salesData = new Sales();
                salesData.setSalesId(resultSet.getInt("salesid"));
                salesData.setUserId(resultSet.getInt("userid"));
                salesData.setProductId(resultSet.getInt("productid"));
                salesData.setSoldQuantity(resultSet.getInt("soldquantity"));
                salesData.setIncome(resultSet.getDouble("income"));
                salesData.setEminum(resultSet.getString("eminum"));
                salesData.setInvoiceId(resultSet.getString("invoiceid"));
                salesData.setIssuedDate(resultSet.getDate("issued_date")); // Assuming issuedDate is a Date type

                // Set other attributes based on column names in the tables
                saleDataList.add(salesData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return saleDataList;
    }

}
// Other methods for CRUD operations on Sales

