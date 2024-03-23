/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Sales;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

    public class SalesStatistics {

    public SalesStatistics() {
    }

    // Method to calculate total sales
    public double calculateTotalSales(List<Sales> salesList) {
        double totalSales = 0;
        for (Sales sale : salesList) {
            totalSales += sale.getIncome();
        }
        return totalSales;
    }

    // Method to find the most sold product
    public int findMostSoldProduct(List<Sales> salesList) {
        int maxSoldQuantity = 0;
        int mostSoldProductId = -1;

        for (Sales sale : salesList) {
            if (sale.getSoldQuantity() > maxSoldQuantity) {
                maxSoldQuantity = sale.getSoldQuantity();
                mostSoldProductId = sale.getProductId();
            }
        }
        return mostSoldProductId;
    }

    // Method to find the lowest sold product
    public int findLowestSoldProduct(List<Sales> salesList) {
        int minSoldQuantity = Integer.MAX_VALUE;
        int lowestSoldProductId = -1;

        for (Sales sale : salesList) {
            if (sale.getSoldQuantity() < minSoldQuantity) {
                minSoldQuantity = sale.getSoldQuantity();
                lowestSoldProductId = sale.getProductId();
            }
        }
        return lowestSoldProductId;
    }

    // Method to calculate the total quantity sold
    public int calculateTotalQuantitySold(List<Sales> salesList) {
        int totalQuantity = 0;
        for (Sales sale : salesList) {
            totalQuantity += sale.getSoldQuantity();
        }
        return totalQuantity;
    }

    public double calculateTotalSalesByDate(List<Sales> salesList, Date targetDate) {
        double totalSales = 0;
        for (Sales sale : salesList) {
            if (sale.getIssuedDate().equals(targetDate)) {
                totalSales += sale.getIncome();
            }
        }
        return totalSales;
    }

    // Method to calculate total sold quantity for a given date
    public int calculateTotalSoldQuantityByDate(List<Sales> salesList, Date targetDate) {
        int totalQuantity = 0;
        for (Sales sale : salesList) {
            if (sale.getIssuedDate().equals(targetDate)) {
                totalQuantity += sale.getSoldQuantity();
            }
        }
        return totalQuantity;
    }

    // Method to calculate total income for a given date
    public double calculateTotalIncomeByDate(List<Sales> salesList, Date targetDate) {
        double totalIncome = 0;
        for (Sales sale : salesList) {
            if (sale.getIssuedDate().equals(targetDate)) {
                totalIncome += sale.getIncome();
            }
        }
        return totalIncome;
    }
}
