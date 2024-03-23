package DAO;

import Controllers.CashierController;
import Controllers.DBController;
import Controllers.ManagerController;
import Models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adithya
 */
public class UserDAO {

    private DBController dbController;
    private static User authenticatedUser = null;

    public UserDAO() {
        dbController = DBController.getInstance(); // Assuming you have a DatabaseController class        
    }

    public User authenticateUser(String username, String password) {

        String query = "SELECT username, password, CONCAT(firstName, ' ', lastName) AS fullname, "
                + "CONCAT(street, ', ', city, ', ', country) AS address, "
                + "userid, position, mobile, email, nic FROM Users "
                + "WHERE username = ? AND password = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("userid");
                    String userPosition = resultSet.getString("position");
                    String name = resultSet.getString("fullname");
                    String address = resultSet.getString("address");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("mobile");
                    String nic = resultSet.getString("nic");

                    if ("Cashier".equals(userPosition)) {
                        authenticatedUser = new CashierController(userId, username, password, name, address, email, phone, nic);
                    } else if ("Manager".equals(userPosition)) {
                        authenticatedUser = new ManagerController(userId, username, password, name, address, email, phone, nic);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return authenticatedUser;
    }

    public boolean createUser(String username, String password, String firstName, String lastName, String mobile,
            String email, String street, String city, String country, String position, String nic) {
        String query = "INSERT INTO Users (username, password, firstName, lastName, mobile, email, street, city, country, position, nic) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, mobile);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, street);
            preparedStatement.setString(8, city);
            preparedStatement.setString(9, country);
            preparedStatement.setString(10, position);
            preparedStatement.setString(11, nic);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., user created)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public List<User> searchUserData(String keyword) {
        List<User> userSearchDataList = new ArrayList<>();
        String query = "SELECT userid, username, password, CONCAT(firstName, ' ', lastName) AS fullname, "
                + "CONCAT(street, ', ', city, ', ', country) AS address, "
                + "mobile, email, nic FROM Users "
                + "WHERE username = ? OR firstName = ? OR lastName = ? OR street = ?OR city = ?OR country = ? OR email = ? OR mobile = ? OR nic = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 1; i <= 9; i++) {
                preparedStatement.setString(i, keyword);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User searchUserData = new User();
                searchUserData.setUserId(resultSet.getInt("userid"));
                searchUserData.setUsername(resultSet.getString("username"));
                searchUserData.setPassword(resultSet.getString("password"));
                searchUserData.setName(resultSet.getString("fullname"));
                searchUserData.setAddress(resultSet.getString("address"));
                searchUserData.setMobile(resultSet.getString("mobile"));
                searchUserData.setEmail(resultSet.getString("email"));
                searchUserData.setNic(resultSet.getString("nic"));
                userSearchDataList.add(searchUserData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return userSearchDataList;
    }

    public String getFirstNameByID(int keyword) {
        String firstName = null;
        String query = "SELECT firstName FROM Users WHERE userid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                firstName = resultSet.getString("firstName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return firstName;
    }

    public String getLastNameByID(int keyword) {
        String lastName = null;
        String query = "SELECT lastName FROM Users WHERE userid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastName = resultSet.getString("lastName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return lastName;
    }

    public String getStreetByID(int keyword) {
        String street = null;
        String query = "SELECT street FROM Users WHERE userid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                street = resultSet.getString("street");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return street;
    }

    public String getCityByID(int keyword) {
        String city = null;
        String query = "SELECT city FROM Users WHERE userid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                city = resultSet.getString("city");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return city;
    }

    public String getCountryByID(int keyword) {
        String country = null;
        String query = "SELECT country FROM Users WHERE userid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                country = resultSet.getString("country");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return country;
    }

    public List<User> retrieveCashierUsers() {
        List<User> cashierUsers = new ArrayList<>();
        String query = "SELECT userid, username, password, CONCAT(firstName, ' ', lastName) AS fullname, "
                + "CONCAT(street, ', ', city, ', ', country) AS address, "
                + "mobile, email, nic FROM Users WHERE position = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "Cashier");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User cashierUser = new User();
                cashierUser.setUserId(resultSet.getInt("userid"));
                cashierUser.setUsername(resultSet.getString("username"));
                cashierUser.setPassword(resultSet.getString("password"));
                cashierUser.setName(resultSet.getString("fullname"));
                cashierUser.setAddress(resultSet.getString("address"));
                cashierUser.setMobile(resultSet.getString("mobile"));
                cashierUser.setEmail(resultSet.getString("email"));
                cashierUser.setNic(resultSet.getString("nic"));
                cashierUsers.add(cashierUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return cashierUsers;
    }

    public List<User> viewAllUserData() {
        List<User> allUserDataList = new ArrayList<>(); // List to hold data for all users
        String query = "SELECT userid, username, password, CONCAT(firstName, ' ', lastName) AS fullname, "
                + "CONCAT(street, ', ', city, ', ', country) AS address, "
                + "mobile, email, nic FROM Users";

        try (Connection connection = dbController.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User userData = new User();
                userData.setUserId(resultSet.getInt("userid"));
                userData.setUsername(resultSet.getString("username"));
                userData.setPassword(resultSet.getString("password"));
                userData.setName(resultSet.getString("fullname"));
                userData.setAddress(resultSet.getString("address"));
                userData.setMobile(resultSet.getString("mobile"));
                userData.setEmail(resultSet.getString("email"));
                userData.setNic(resultSet.getString("nic"));
                allUserDataList.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return allUserDataList;
    }

    public boolean updateUser(String username, String newPassword, String newFirstName, String newLastName,
            String newMobile, String newEmail, String newStreet, String newCity,
            String newCountry, String newNic) {
        String query = "UPDATE Users SET password = ?, firstName = ?, lastName = ?, "
                + "mobile = ?, email = ?, street = ?, city = ?, country = ?, nic = ? "
                + "WHERE username = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newFirstName);
            preparedStatement.setString(3, newLastName);
            preparedStatement.setString(4, newMobile);
            preparedStatement.setString(5, newEmail);
            preparedStatement.setString(6, newStreet);
            preparedStatement.setString(7, newCity);
            preparedStatement.setString(8, newCountry);
            preparedStatement.setString(9, newNic);
            preparedStatement.setString(10, username);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., user updated)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public boolean deleteUser(int userid) {
        String query = "DELETE FROM Users WHERE userid = ?";

        try (Connection connection = dbController.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userid);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (i.e., user deleted)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    public static void logout() {
        authenticatedUser = null; // Reset the current user upon logout
    }
    // Other methods for CRUD operations on Users
}
