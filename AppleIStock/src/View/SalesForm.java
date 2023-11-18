/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.ManagerController;
import Controller.SalesController;
import Models.Sale;
import Models.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adithya
 */
public class SalesForm extends javax.swing.JFrame {

    private DefaultTableModel salesTableModel;
    private Sale selectedSales;
    private int selectedSalesId;
    private SalesController Sale;
    private ManagerController manager;
    private User user;

    public SalesForm(User user) {
        initComponents();
        manager = new ManagerController(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getEmail(), user.getPhone(), user.getNic());
        Sale = new SalesController();
        this.user = user;

        // Initialize your product table model
        salesTableModel = new DefaultTableModel();

        salesTableModel.addColumn(
                "Sales ID");
        salesTableModel.addColumn(
                "Product ID");
        salesTableModel.addColumn(
                "User ID");
        salesTableModel.addColumn(
                "NIC");
        salesTableModel.addColumn(
                "Sale Date");
        salesTableModel.addColumn(
                "Sale QTY");
        salesTableModel.addColumn(
                "Sale Price");
        salesTableModel.addColumn(
                "Invoice Number");
        

        // Set the table model for your sales table
        viewSales.setModel(salesTableModel);
        viewSalesDaily.setModel(salesTableModel);
        // Load Sales data when the form is initialized
        loadProductData();

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateAndTimeLabel();
            }
        });

        viewSales.getSelectionModel()
                .addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e
                    ) {
                        if (!e.getValueIsAdjusting() && viewSales.getSelectedRow() != -1) {
                            int selectedRow = viewSales.getSelectedRow();

                            // Get the product data from the selected row
                            int salesId = (int) viewSales.getValueAt(selectedRow, 0);
                            int productId = (int) viewSales.getValueAt(selectedRow, 1);
                            int userId = (int) viewSales.getValueAt(selectedRow, 2);// Assuming the first column contains product ID
                            String nic = (String) viewSales.getValueAt(selectedRow, 3); // Assuming the second column contains product name
                            Date saleDate = (Date) viewSales.getValueAt(selectedRow, 4); // Assuming the third column contains price
                            int salesQty = (int) viewSales.getValueAt(selectedRow, 5); // Assuming the fourth column contains quantity
                            double price = (double) viewSales.getValueAt(selectedRow, 6);
                            String invoiceNumber = (String) viewSales.getValueAt(selectedRow, 7);// Assuming the fifth column contains category

                            // Create a Product instance from the selected data
                            selectedSales = new Sale(salesId, productId, userId, nic, saleDate, salesQty, price, invoiceNumber) {
                            };
                        }
                    }
                }
                );

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SearchPanel = new javax.swing.JPanel();
        lblDateAndTime = new javax.swing.JLabel();
        tbSalesPane = new javax.swing.JTabbedPane();
        monthlyPanel = new javax.swing.JPanel();
        txtInvoiceNo = new javax.swing.JTextField();
        lblInvoiceNumber = new javax.swing.JLabel();
        btnGetData = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        viewSales = new javax.swing.JTable();
        dailySalesPanel = new javax.swing.JPanel();
        lblDateData = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDateData = new javax.swing.JTextField();
        lblQtyData = new javax.swing.JLabel();
        txtQtyData = new javax.swing.JTextField();
        txtIncomeData = new javax.swing.JTextField();
        lblIncomeData = new javax.swing.JLabel();
        lblSellerData = new javax.swing.JLabel();
        txtSellerrData = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        viewSalesDaily = new javax.swing.JTable();
        btnProductViewForm = new javax.swing.JButton();
        btnCashierData = new javax.swing.JButton();
        btnCheckSale = new javax.swing.JButton();
        salePanelLeft = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnViewDaylySales = new javax.swing.JButton();
        btnGetMontthlySales = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnLogout3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SALES MANGER");
        setName("form"); // NOI18N

        SearchPanel.setBackground(new java.awt.Color(0, 102, 204));

        lblDateAndTime.setBackground(new java.awt.Color(0, 102, 204));
        lblDateAndTime.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDateAndTime.setForeground(new java.awt.Color(255, 255, 255));
        lblDateAndTime.setText("Date:");

        javax.swing.GroupLayout SearchPanelLayout = new javax.swing.GroupLayout(SearchPanel);
        SearchPanel.setLayout(SearchPanelLayout);
        SearchPanelLayout.setHorizontalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDateAndTime, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SearchPanelLayout.setVerticalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDateAndTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbSalesPane.setBackground(new java.awt.Color(0, 102, 204));
        tbSalesPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        monthlyPanel.setBackground(new java.awt.Color(255, 255, 255));
        monthlyPanel.setForeground(new java.awt.Color(0, 102, 255));

        txtInvoiceNo.setBackground(new java.awt.Color(255, 255, 255));
        txtInvoiceNo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtInvoiceNo.setForeground(new java.awt.Color(0, 0, 0));
        txtInvoiceNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInvoiceNoActionPerformed(evt);
            }
        });

        lblInvoiceNumber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInvoiceNumber.setForeground(new java.awt.Color(0, 102, 255));
        lblInvoiceNumber.setText("Enter Your Invoice Number");

        btnGetData.setBackground(new java.awt.Color(0, 0, 0));
        btnGetData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGetData.setForeground(new java.awt.Color(0, 102, 255));
        btnGetData.setText("Get Data");
        btnGetData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetDataActionPerformed(evt);
            }
        });

        viewSales.setBackground(new java.awt.Color(255, 255, 255));
        viewSales.setForeground(new java.awt.Color(0, 102, 204));
        viewSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sales ID", "Product ID", "User ID", "NIC", "Sale Date", "Sale QTY", "Sale Price", "Invoice Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(viewSales);

        javax.swing.GroupLayout monthlyPanelLayout = new javax.swing.GroupLayout(monthlyPanel);
        monthlyPanel.setLayout(monthlyPanelLayout);
        monthlyPanelLayout.setHorizontalGroup(
            monthlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyPanelLayout.createSequentialGroup()
                .addGroup(monthlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(monthlyPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblInvoiceNumber)
                        .addGap(18, 18, 18)
                        .addComponent(txtInvoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGetData, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, monthlyPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)))
                .addContainerGap())
        );
        monthlyPanelLayout.setVerticalGroup(
            monthlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(monthlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, monthlyPanelLayout.createSequentialGroup()
                        .addComponent(lblInvoiceNumber)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, monthlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtInvoiceNo)
                        .addComponent(btnGetData, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190))
        );

        tbSalesPane.addTab("Invoice Check", monthlyPanel);

        dailySalesPanel.setBackground(new java.awt.Color(255, 255, 255));

        lblDateData.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDateData.setForeground(new java.awt.Color(0, 102, 204));
        lblDateData.setText("Date:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 204));

        txtDateData.setBackground(new java.awt.Color(255, 255, 255));
        txtDateData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDateData.setForeground(new java.awt.Color(0, 102, 204));
        txtDateData.setText("YYYY-MM-DD");

        lblQtyData.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblQtyData.setForeground(new java.awt.Color(0, 102, 204));
        lblQtyData.setText("Sold Quantity:");

        txtQtyData.setBackground(new java.awt.Color(255, 255, 255));
        txtQtyData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtQtyData.setForeground(new java.awt.Color(0, 102, 204));

        txtIncomeData.setBackground(new java.awt.Color(255, 255, 255));
        txtIncomeData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtIncomeData.setForeground(new java.awt.Color(0, 102, 204));

        lblIncomeData.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblIncomeData.setForeground(new java.awt.Color(0, 102, 204));
        lblIncomeData.setText("Income:");

        lblSellerData.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSellerData.setForeground(new java.awt.Color(0, 102, 204));
        lblSellerData.setText("Most Seller:");

        txtSellerrData.setBackground(new java.awt.Color(255, 255, 255));
        txtSellerrData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSellerrData.setForeground(new java.awt.Color(0, 102, 204));

        viewSalesDaily.setBackground(new java.awt.Color(255, 255, 255));
        viewSalesDaily.setForeground(new java.awt.Color(0, 102, 204));
        viewSalesDaily.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sales ID", "Product ID", "User ID", "NIC", "Sale Date", "Sale QTY", "Sale Price", "Invoice Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(viewSalesDaily);

        btnProductViewForm.setBackground(new java.awt.Color(0, 0, 0));
        btnProductViewForm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnProductViewForm.setForeground(new java.awt.Color(0, 102, 204));
        btnProductViewForm.setText("Check Products");
        btnProductViewForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductViewFormActionPerformed(evt);
            }
        });

        btnCashierData.setBackground(new java.awt.Color(0, 0, 0));
        btnCashierData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCashierData.setForeground(new java.awt.Color(0, 102, 204));
        btnCashierData.setText("Check Cashier Data");
        btnCashierData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCashierDataActionPerformed(evt);
            }
        });

        btnCheckSale.setBackground(new java.awt.Color(0, 0, 0));
        btnCheckSale.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCheckSale.setForeground(new java.awt.Color(0, 102, 204));
        btnCheckSale.setText("Cheeck Sales");
        btnCheckSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dailySalesPanelLayout = new javax.swing.GroupLayout(dailySalesPanel);
        dailySalesPanel.setLayout(dailySalesPanelLayout);
        dailySalesPanelLayout.setHorizontalGroup(
            dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dailySalesPanelLayout.createSequentialGroup()
                .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dailySalesPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE))
                    .addGroup(dailySalesPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dailySalesPanelLayout.createSequentialGroup()
                                .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblQtyData)
                                    .addComponent(lblIncomeData)
                                    .addComponent(lblSellerData))
                                .addGap(34, 34, 34)
                                .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dailySalesPanelLayout.createSequentialGroup()
                                        .addComponent(txtSellerrData, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCheckSale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(dailySalesPanelLayout.createSequentialGroup()
                                        .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtQtyData, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDateData, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtIncomeData, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnProductViewForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnCashierData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(dailySalesPanelLayout.createSequentialGroup()
                                .addComponent(lblDateData)
                                .addGap(144, 144, 144)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        dailySalesPanelLayout.setVerticalGroup(
            dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dailySalesPanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dailySalesPanelLayout.createSequentialGroup()
                        .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDateData)
                            .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtDateData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblQtyData)
                            .addComponent(txtQtyData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIncomeData)
                            .addComponent(txtIncomeData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(dailySalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSellerData)
                            .addComponent(txtSellerrData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dailySalesPanelLayout.createSequentialGroup()
                        .addComponent(btnProductViewForm, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCashierData, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckSale, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbSalesPane.addTab("Daily Sales", dailySalesPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(tbSalesPane))
                    .addComponent(SearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbSalesPane)
                .addContainerGap())
        );

        salePanelLeft.setBackground(new java.awt.Color(255, 255, 255));
        salePanelLeft.setAutoscrolls(true);
        salePanelLeft.setFocusCycleRoot(true);

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));
        jPanel3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Niagara Solid", 0, 24)); // NOI18N
        jLabel1.setText("WELCOME");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Niagara Solid", 0, 24)); // NOI18N
        jLabel2.setText("To ISTOCK");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Niagara Solid", 0, 36)); // NOI18N
        jLabel3.setText("Sales Dashboard");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(28, 28, 28))
        );

        btnViewDaylySales.setBackground(new java.awt.Color(0, 0, 0));
        btnViewDaylySales.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnViewDaylySales.setForeground(new java.awt.Color(0, 102, 204));
        btnViewDaylySales.setText("Daily Sale");
        btnViewDaylySales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDaylySalesActionPerformed(evt);
            }
        });

        btnGetMontthlySales.setBackground(new java.awt.Color(0, 0, 0));
        btnGetMontthlySales.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGetMontthlySales.setForeground(new java.awt.Color(0, 102, 204));
        btnGetMontthlySales.setText("Monthly Sales");
        btnGetMontthlySales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetMontthlySalesActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(0, 0, 0));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(0, 102, 204));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnLogout3.setBackground(new java.awt.Color(0, 0, 0));
        btnLogout3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout3.setForeground(new java.awt.Color(0, 102, 204));
        btnLogout3.setText("LogOut");
        btnLogout3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogout3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout salePanelLeftLayout = new javax.swing.GroupLayout(salePanelLeft);
        salePanelLeft.setLayout(salePanelLeftLayout);
        salePanelLeftLayout.setHorizontalGroup(
            salePanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salePanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(salePanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewDaylySales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGetMontthlySales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        salePanelLeftLayout.setVerticalGroup(
            salePanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salePanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(124, 124, 124)
                .addComponent(btnViewDaylySales, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGetMontthlySales, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(salePanelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(salePanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadProductData() {
        List<Sale> salesList = Sale.getAllSales(); // Retrieve product data

        // Clear existing data from the table
        salesTableModel.setRowCount(0);

        for (Sale sales : salesList) {
            // Add the product data to the table
            salesTableModel.addRow(new Object[]{
                sales.getSaleId(),
                sales.getProductId(),
                sales.getUserId(),
                sales.getNic(),
                sales.getSaleDate(),
                sales.getSaleQuantity(),
                sales.getSalePrice(),
                sales.getInvoiceNumber()
            });
        }
    }

    private void loadDDailyata() {
        String dateData = txtDateData.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = dateFormat.parse(dateData);
        } catch (ParseException ex) {
            Logger.getLogger(SalesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.util.Date utilDate = date; // Your java.util.Date object
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        List<Sale> dailySalesList = Sale.getSalesByDate(sqlDate); // Retrieve product data

        // Clear existing data from the table
        salesTableModel.setRowCount(0);

        for (Sale sales : dailySalesList) {
            // Add the product data to the table
            salesTableModel.addRow(new Object[]{
                sales.getSaleId(),
                sales.getProductId(),
                sales.getUserId(),
                sales.getNic(),
                sales.getSaleDate(),
                sales.getSaleQuantity(),
                sales.getSalePrice(),
                sales.getInvoiceNumber()
            });
        }
    }

    // Utility method to format currency
    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return "$" + decimalFormat.format(amount);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    private void updateDateAndTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define your desired date and time format
        String formattedDate = sdf.format(new Date()); // Get the current date and time

        lblDateAndTime.setText("Date and Time: " + formattedDate); // Update the label text
    }

    private void btnViewDaylySalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDaylySalesActionPerformed
        tbSalesPane.setSelectedIndex(1);
        loadProductData();
    }//GEN-LAST:event_btnViewDaylySalesActionPerformed

    private void btnGetMontthlySalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetMontthlySalesActionPerformed
        tbSalesPane.setSelectedIndex(0);
        loadProductData();
    }//GEN-LAST:event_btnGetMontthlySalesActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnLogout3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogout3ActionPerformed
        LoginForm login = new LoginForm();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogout3ActionPerformed

    private void btnProductViewFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductViewFormActionPerformed
        {
            if ("Manager".equals(user.getPosition())) {
                // Open ManagerForm with the user object
                java.awt.EventQueue.invokeLater(() -> {
                    new ManagerForm(user).setVisible(true);
                    this.dispose();
                });
            } else {
                JOptionPane.showMessageDialog(null, "You Dont Have Access.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnProductViewFormActionPerformed

    private void txtInvoiceNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInvoiceNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInvoiceNoActionPerformed

    private void btnGetDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetDataActionPerformed
        // Handle search logic
        String searchKeyword = txtInvoiceNo.getText();

        // Assuming salesController is an instance of SalesController
        List<Sale> searchResults = Sale.getSalesByInvoiceNumber(searchKeyword);

        // Clear existing data from the table
        salesTableModel.setRowCount(0);

        // Check if the search results list is not empty
        if (!searchResults.isEmpty()) {
            for (Sale searchResult : searchResults) {
                // Add each search result to the table
                salesTableModel.addRow(new Object[]{
                    searchResult.getSaleId(),
                    searchResult.getProductId(),
                    searchResult.getUserId(),
                    searchResult.getNic(),
                    searchResult.getSaleDate(),
                    searchResult.getSaleQuantity(),
                    searchResult.getSalePrice(),
                    searchResult.getInvoiceNumber()
                });
            }
        } else {
            // Display a message or handle the case where no result is found
            JOptionPane.showMessageDialog(SalesForm.this, "Failed to find the Invoice.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnGetDataActionPerformed

    private void btnCheckSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSaleActionPerformed
        // Get the date data from the text field
        String dateData = txtDateData.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(dateData);
            java.util.Date utilDate = date; // Your java.util.Date object
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            // Call the static method from Sale class to get daily sales data
            Map<String, Object> dailySalesData = Sale.getDailySales(sqlDate);

            // Update the form components with the retrieved data
            if (dailySalesData != null) {
                // Assuming txtDateData, txtQtyData, txtIncomeData, txtSellerrData are your form components
                txtDateData.setText(dateData);

                int totalQuantity = (int) dailySalesData.get("totalQuantity");
                double totalSalesPrice = (double) dailySalesData.get("totalSalesPrice");
                String mostSellerUserName = (String) dailySalesData.get("mostSellerUserName");

                txtQtyData.setText(Integer.toString(totalQuantity));
                txtIncomeData.setText(formatCurrency(totalSalesPrice));
                txtSellerrData.setText(mostSellerUserName);
                loadDDailyata();
            } else {
                // Handle the case where no data is found
                JOptionPane.showMessageDialog(this, "No sales data found for the given date.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Handle parsing exception if the input string is not in the expected format
        }
    }//GEN-LAST:event_btnCheckSaleActionPerformed

    private void btnCashierDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCashierDataActionPerformed
          {
            if ("Manager".equals(user.getPosition())) {
                // Open ManagerForm with the user object
                java.awt.EventQueue.invokeLater(() -> {
                    new ManageUserForm(user).setVisible(true);
                    this.dispose();
                });
            } else {
                JOptionPane.showMessageDialog(null, "You Dont Have Access.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCashierDataActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JButton btnCashierData;
    private javax.swing.JButton btnCheckSale;
    private javax.swing.JButton btnGetData;
    private javax.swing.JButton btnGetMontthlySales;
    private javax.swing.JButton btnLogout3;
    private javax.swing.JButton btnProductViewForm;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnViewDaylySales;
    private javax.swing.JPanel dailySalesPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblDateAndTime;
    private javax.swing.JLabel lblDateData;
    private javax.swing.JLabel lblIncomeData;
    private javax.swing.JLabel lblInvoiceNumber;
    private javax.swing.JLabel lblQtyData;
    private javax.swing.JLabel lblSellerData;
    private javax.swing.JPanel monthlyPanel;
    private javax.swing.JPanel salePanelLeft;
    private javax.swing.JTabbedPane tbSalesPane;
    private javax.swing.JTextField txtDateData;
    private javax.swing.JTextField txtIncomeData;
    private javax.swing.JTextField txtInvoiceNo;
    private javax.swing.JTextField txtQtyData;
    private javax.swing.JTextField txtSellerrData;
    private javax.swing.JTable viewSales;
    private javax.swing.JTable viewSalesDaily;
    // End of variables declaration//GEN-END:variables
}
