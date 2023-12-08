/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Controllers.DBController;

/**
 *
 * @author Adithya
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    // Other methods for CRUD operations on Sales
}
