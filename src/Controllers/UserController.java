/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.UserDAO;
import Models.User;
import Views.CashierDashboard;
import Views.ManagerDashboard;
import javax.swing.JOptionPane;

/**
 *
 * @author Adithya
 */
public class UserController {

    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    public boolean validateAndRedirect(String username, String password) {
        User authenticatedUser = userDAO.authenticateUser(username, password);

        if (authenticatedUser != null) {
            if (authenticatedUser instanceof CashierController) {
                openCashierDashboard(authenticatedUser);
                return true; // Successful login as Cashier
            } else if (authenticatedUser instanceof ManagerController) {
                openManagerDashboard(authenticatedUser);
                return true; // Successful login as Manager
            } else {
                JOptionPane.showMessageDialog(null, "Invalid user position.");
                // Handle unrecognized user position
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
            // Handle invalid credentials
        }
        return false; // Failed login attempt
    }

    private void openCashierDashboard(User user) {
        if (user instanceof CashierController) {
            CashierDashboard cashierDashboard = new CashierDashboard((CashierController) user);
            cashierDashboard.setVisible(true);
            // Open CashierDashboard GUI using cashierDashboardController
        } else {
            JOptionPane.showMessageDialog(null, "Invalid user type.");
            // Handle unexpected user type
        }
    }

    private void openManagerDashboard(User user) {
        if (user instanceof ManagerController) {
            ManagerDashboard managerDashboard = new ManagerDashboard((ManagerController) user);
            managerDashboard.setVisible(true);
            // Open ManagerDashboard GUI using managerDashboardController
        } else {
            JOptionPane.showMessageDialog(null, "Invalid user type.");
            // Handle unexpected user type
        }
    }
}
