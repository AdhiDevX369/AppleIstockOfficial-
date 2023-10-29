/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Models.Product;
import Controller.ManagerController;
import Models.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adithya
 */
public class ManagerForm extends javax.swing.JFrame {

    private DefaultTableModel productTableModel;
    private Product selectedProduct;
    private int selectedProductId;
    private ManagerController manager;

    public ManagerForm(User user) {
        initComponents();
        // Create an instance of the User class with user data
        manager = new ManagerController(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getEmail(), user.getPhone(), user.getNic());
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle search logic
                String searchKeyword = txtSearch.getText();
                List<Product> searchResults = manager.searchProduct(searchKeyword);

                // Clear existing data from the table
                productTableModel.setRowCount(0);

                for (Product product : searchResults) {
                    // Add the search results to the table
                    productTableModel.addRow(new Object[]{
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getCategory()
                    });
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct != null) {
                    // Set the class-level variable with the selected product's product_id
                    selectedProductId = selectedProduct.getProductId();

                    // Set the text fields in the invoicePanel with the selected product's information
                    txtProductName.setText(selectedProduct.getName());
                    txtProductPrice.setText(Double.toString(selectedProduct.getPrice()));
                    txtProductQty.setText(Integer.toString(selectedProduct.getStockQuantity()));

                    // Set the selected product's category in the ComboBox
                    String productCategory = selectedProduct.getCategory();
                    cmbCategory.setSelectedItem(productCategory);
                }
            }
        });
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct != null) {
                    int productId = selectedProduct.getProductId();
                    boolean removed = manager.removeProductFromDatabase(productId);
                    if (removed) {
                        JOptionPane.showMessageDialog(ManagerForm.this, "Successfully Removed the item.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ManagerForm.this, "Failed to remove the item.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        BtnViewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Manager".equals(user.getPosition())) {
                    // Open ManagerForm with the user object
                    java.awt.EventQueue.invokeLater(() -> {
                        new ManageUserForm(user).setVisible(true);
                    });
                }else{
                     JOptionPane.showMessageDialog(null, "You Dont Have Access.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                }
            }
            );
        // Initialize your product table model
        productTableModel  = new DefaultTableModel();

            productTableModel.addColumn (
                    

            "Product ID");
            productTableModel.addColumn (
                    

            "Product Name");
            productTableModel.addColumn (
                    

            "Price");
            productTableModel.addColumn (
                    

            "Qty");
            productTableModel.addColumn (
                    

            "Category");

        // Set the table model for your product table
            viewProductTable.setModel (productTableModel);

            // Load product data when the form is initialized
            loadProductData();

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateDateAndTimeLabel();
                }
            });

            timer.start ();

            viewProductTable.getSelectionModel () 
                .addListSelectionListener(new ListSelectionListener() {
                    @Override
                public void valueChanged
                (ListSelectionEvent e
                
                    ) {
                        if (!e.getValueIsAdjusting() && viewProductTable.getSelectedRow() != -1) {
                        int selectedRow = viewProductTable.getSelectedRow();

                        // Get the product data from the selected row
                        int productId = (int) viewProductTable.getValueAt(selectedRow, 0); // Assuming the first column contains product ID
                        String productName = (String) viewProductTable.getValueAt(selectedRow, 1); // Assuming the second column contains product name
                        double price = (double) viewProductTable.getValueAt(selectedRow, 2); // Assuming the third column contains price
                        int quantity = (int) viewProductTable.getValueAt(selectedRow, 3); // Assuming the fourth column contains quantity
                        String category = (String) viewProductTable.getValueAt(selectedRow, 4); // Assuming the fifth column contains category

                        // Create a Product instance from the selected data
                        selectedProduct = new Product(productId, productName, price, category, quantity) {
                        };
                    }
                }
            }
        );
            

    }

    private void loadProductData() {
        List<Product> products = manager.viewProductDetails(); // Retrieve product data

        // Clear existing data from the table
        productTableModel.setRowCount(0);

        for (Product product : products) {
            // Add the product data to the table
            productTableModel.addRow(new Object[]{
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory()
            });
        }
    }

    private void updateDateAndTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define your desired date and time format
        String formattedDate = sdf.format(new Date()); // Get the current date and time

        lblDateAndTime.setText("Date and Time: " + formattedDate); // Update the label text
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        managerDPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        SearchPanel = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblDateAndTime = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        viewProductTable = new javax.swing.JTable();
        manageEditPanel = new javax.swing.JPanel();
        lblProductName = new javax.swing.JLabel();
        lblProductQty = new javax.swing.JLabel();
        lblProductPrice = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        txtProductPrice = new javax.swing.JTextField();
        txtProductQty = new javax.swing.JTextField();
        txtProductName = new javax.swing.JTextField();
        cmbCategory = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        panelManageBtn = new javax.swing.JPanel();
        BtnViewUser = new javax.swing.JButton();
        btnViewSales = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manager Dashboard");

        managerDPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setAutoscrolls(true);
        jPanel1.setFocusCycleRoot(true);

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Niagara Solid", 0, 24)); // NOI18N
        jLabel1.setText("WELCOME");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Niagara Solid", 0, 24)); // NOI18N
        jLabel2.setText("To ISTOCK");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Niagara Solid", 0, 36)); // NOI18N
        jLabel3.setText("Manager Dashboard");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLogout.setBackground(new java.awt.Color(0, 0, 0));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(0, 102, 204));
        btnLogout.setText("LogOut");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        SearchPanel.setBackground(new java.awt.Color(0, 102, 204));

        txtSearch.setBackground(new java.awt.Color(255, 255, 255));
        txtSearch.setForeground(new java.awt.Color(0, 0, 0));
        txtSearch.setText("Search Product");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(0, 0, 0));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(0, 102, 204));
        btnSearch.setText("Search");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        SearchPanelLayout.setVerticalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(txtSearch)
                    .addComponent(lblDateAndTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        viewProductTable.setBackground(new java.awt.Color(255, 255, 255));
        viewProductTable.setForeground(new java.awt.Color(0, 102, 204));
        viewProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Product_ID", "Product Name", "Price", "Qty", "Category"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(viewProductTable);

        manageEditPanel.setBackground(new java.awt.Color(255, 255, 255));
        manageEditPanel.setForeground(new java.awt.Color(0, 0, 0));

        lblProductName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductName.setForeground(new java.awt.Color(0, 0, 0));
        lblProductName.setText("Product Name:");

        lblProductQty.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductQty.setForeground(new java.awt.Color(0, 0, 0));
        lblProductQty.setText("Product QTY:");

        lblProductPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductPrice.setForeground(new java.awt.Color(0, 0, 0));
        lblProductPrice.setText("Product Price:");

        lblCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCategory.setForeground(new java.awt.Color(0, 0, 0));
        lblCategory.setText("Category:");

        txtProductPrice.setBackground(new java.awt.Color(255, 255, 255));
        txtProductPrice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtProductPrice.setForeground(new java.awt.Color(0, 0, 0));

        txtProductQty.setBackground(new java.awt.Color(255, 255, 255));
        txtProductQty.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtProductQty.setForeground(new java.awt.Color(0, 0, 0));

        txtProductName.setBackground(new java.awt.Color(255, 255, 255));
        txtProductName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtProductName.setForeground(new java.awt.Color(0, 0, 0));

        cmbCategory.setBackground(new java.awt.Color(255, 255, 255));
        cmbCategory.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbCategory.setForeground(new java.awt.Color(0, 0, 0));
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category", "iPhone", "Apple Watch", "iPad", "AirPods", "Mac" }));

        btnUpdate.setBackground(new java.awt.Color(0, 102, 204));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(0, 102, 204));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd.setText("Add New");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout manageEditPanelLayout = new javax.swing.GroupLayout(manageEditPanel);
        manageEditPanel.setLayout(manageEditPanelLayout);
        manageEditPanelLayout.setHorizontalGroup(
            manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageEditPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(manageEditPanelLayout.createSequentialGroup()
                            .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblProductQty)
                                .addComponent(lblCategory))
                            .addGap(30, 30, 30)
                            .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtProductQty)
                                .addComponent(cmbCategory, 0, 200, Short.MAX_VALUE)))
                        .addGroup(manageEditPanelLayout.createSequentialGroup()
                            .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblProductName)
                                .addComponent(lblProductPrice))
                            .addGap(18, 18, 18)
                            .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(txtProductName))))
                    .addGroup(manageEditPanelLayout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        manageEditPanelLayout.setVerticalGroup(
            manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageEditPanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductName)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductPrice)
                    .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductQty)
                    .addComponent(txtProductQty, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategory)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(manageEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelManageBtn.setBackground(new java.awt.Color(255, 255, 255));

        BtnViewUser.setBackground(new java.awt.Color(0, 0, 0));
        BtnViewUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnViewUser.setForeground(new java.awt.Color(0, 102, 204));
        BtnViewUser.setText("View User");

        btnViewSales.setBackground(new java.awt.Color(0, 0, 0));
        btnViewSales.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnViewSales.setForeground(new java.awt.Color(0, 102, 204));
        btnViewSales.setText("View Sales");

        btnEdit.setBackground(new java.awt.Color(0, 0, 0));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(0, 102, 204));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnRemove.setBackground(new java.awt.Color(0, 0, 0));
        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(0, 102, 204));
        btnRemove.setText("Remove");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 204));
        jLabel4.setText("Manage Your Products Here");

        javax.swing.GroupLayout panelManageBtnLayout = new javax.swing.GroupLayout(panelManageBtn);
        panelManageBtn.setLayout(panelManageBtnLayout);
        panelManageBtnLayout.setHorizontalGroup(
            panelManageBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelManageBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelManageBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemove, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnViewUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewSales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelManageBtnLayout.setVerticalGroup(
            panelManageBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelManageBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnViewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout managerDPanelLayout = new javax.swing.GroupLayout(managerDPanel);
        managerDPanel.setLayout(managerDPanelLayout);
        managerDPanelLayout.setHorizontalGroup(
            managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managerDPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(managerDPanelLayout.createSequentialGroup()
                        .addComponent(manageEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelManageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        managerDPanelLayout.setVerticalGroup(
            managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(managerDPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelManageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manageEditPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(managerDPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(managerDPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadProductData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Get the values from the text fields and combo box
        String productName = txtProductName.getText();
        double productPrice = Double.parseDouble(txtProductPrice.getText());
        int productQty = Integer.parseInt(txtProductQty.getText());
        String productCategory = cmbCategory.getSelectedItem().toString(); // Get the selected category

        // Check if the fields are empty
        if (productName.isEmpty() || productCategory.isEmpty() || productPrice <= 0 || productQty <= 0) {
            // Display an error message if any field is empty or if price or quantity is not positive
            JOptionPane.showMessageDialog(null, "Please fill in all the fields with valid data.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Call your addProduct method to add the product to the database        
            if (manager.addProduct(productName, productPrice, productCategory, productQty)) {
                // Product added successfully
                JOptionPane.showMessageDialog(null, "Product added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the text fields and combo box
                txtProductName.setText("");
                txtProductPrice.setText("");
                txtProductQty.setText("");
                cmbCategory.setSelectedIndex(0); // Set the combo box to the first item (iPhone)
                loadProductData();
            } else {
                // Display an error message if adding the product failed
                JOptionPane.showMessageDialog(null, "Failed to add the product.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// Get the selected product's information (you should have code for this)
        int productId = selectedProductId; // Replace this with how you retrieve the selected product's ID
        String productName = txtProductName.getText();
        double productPrice = Double.parseDouble(txtProductPrice.getText());
        String productCategory = cmbCategory.getSelectedItem().toString();
        int productQty = Integer.parseInt(txtProductQty.getText());

        // Call your updateCurrentProduct method
        if (manager.updateCurrentProduct(productId, productName, productPrice, productCategory, productQty)) {
            // Product updated successfully
            JOptionPane.showMessageDialog(null, "Product updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Clear the text fields and combo box
            txtProductName.setText("");
            txtProductPrice.setText("");
            txtProductQty.setText("");
            cmbCategory.setSelectedIndex(0);
            loadProductData();
        } else {
            // Display an error message if updating the product failed
            JOptionPane.showMessageDialog(null, "Failed to update the product.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
       LoginForm login = new LoginForm();
       login.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnViewUser;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnViewSales;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblDateAndTime;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProductPrice;
    private javax.swing.JLabel lblProductQty;
    private javax.swing.JPanel manageEditPanel;
    private javax.swing.JPanel managerDPanel;
    private javax.swing.JPanel panelManageBtn;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtProductQty;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTable viewProductTable;
    // End of variables declaration//GEN-END:variables
}
