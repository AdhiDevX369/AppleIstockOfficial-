/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.ManagerViews;

import Controllers.ManagerController;
import Controllers.UserController;
import Models.User;
import Views.ManagerDashboard;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adithya
 */
public class ManageUsers extends javax.swing.JFrame {

    private DefaultTableModel userTableModel;
    private User selectedUser;
    private int selectedUserId;
    private ManagerController manager;
    private UserController currentUser;
    private User user;
    private boolean isValidationEnabled = true;

    public ManageUsers(User user) {
        initComponents();
        manager = new ManagerController(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getEmail(), user.getMobile(), user.getNic());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        currentUser = new UserController();
        this.user = user;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?", "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose(); // Close the window
                    // Optionally, perform actions to exit the application
                }
            }
        });
        btnAddUser.setEnabled(false);
        btnUpdateUser.setEnabled(false);

        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSetPasswordKeyReleased(evt);
            }
        });

        btnSearchUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle search logic
                String searchKeyword = txtSearch.getText();

                // Perform the product search
                List<User> searchResults = manager.getSearchUsers(searchKeyword);

                // Clear existing data from the table
                userTableModel.setRowCount(0);

                // Add the search results to the product table
                for (User searched : searchResults) {
                    userTableModel.addRow(new Object[]{
                        searched.getUserId(),
                        searched.getUsername(),
                        searched.getPassword(),
                        searched.getName(),
                        searched.getAddress(),
                        searched.getEmail(),
                        searched.getMobile(),
                        searched.getNic()
                    });
                }
            }
        });

        userTableModel = new DefaultTableModel();
        userTableModel.addColumn(
                "User ID");
        userTableModel.addColumn(
                "Username");
        userTableModel.addColumn(
                "Password");
        userTableModel.addColumn(
                "Name");
        userTableModel.addColumn(
                "Address");
        userTableModel.addColumn(
                "Email");
        userTableModel.addColumn(
                "Phone No");
        userTableModel.addColumn(
                "NIC");

        // Set the table model for your product table
        userTable.setModel(userTableModel);
        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                int selectedRow = userTable.getSelectedRow();

                if (selectedRow >= 0 && selectedRow < userTable.getRowCount()) {
                    int userId = (int) userTable.getValueAt(selectedRow, 0);
                    String username = (String) userTable.getValueAt(selectedRow, 1);
                    String password = (String) userTable.getValueAt(selectedRow, 2);
                    String name = (String) userTable.getValueAt(selectedRow, 3);
                    String address = (String) userTable.getValueAt(selectedRow, 4); // Adjust column index
                    String email = (String) userTable.getValueAt(selectedRow, 5);   // Adjust column index
                    String phone = (String) userTable.getValueAt(selectedRow, 6);   // Adjust column index
                    String nic = (String) userTable.getValueAt(selectedRow, 7);     // Adjust column index

                    selectedUser = new User(userId, username, password, "Cashier", name, address, email, phone, nic);
                }
            }
        });
        btnGetUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedUser != null) {
                    // Set the text fields in the invoicePanel with the selected product's information
                    int selectedUserId = selectedUser.getUserId();
                    String firstName = manager.getFirstNameByID(selectedUserId);
                    String lastName = manager.getLastNameByID(selectedUserId);
                    String street = manager.getStreetByID(selectedUserId);
                    String city = manager.getCityByID(selectedUserId);
                    String country = manager.getCountryByID(selectedUserId);

                    txtUsername.setText(selectedUser.getUsername());
                    txtPassword.setText(selectedUser.getPassword());
                    txtFName.setText(firstName);
                    txtLName.setText(lastName);
                    txtStreet.setText(street);
                    txtCity.setText(city);
                    txtCountry.setText(country);
                    txtEmail.setText(selectedUser.getEmail());
                    txtMobile.setText(selectedUser.getMobile());
                    String Position = selectedUser.getPosition();
                    txtNic.setText(selectedUser.getNic());
                    cmbPosition.setSelectedItem(Position);

                }
            }
        });

        loadUserData();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        manageUserContainer = new javax.swing.JPanel();
        managerBodyContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        crudUserPanel = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        lblUsername1 = new javax.swing.JLabel();
        lblUsername3 = new javax.swing.JLabel();
        lblUsername4 = new javax.swing.JLabel();
        lblUsername5 = new javax.swing.JLabel();
        lblUsername6 = new javax.swing.JLabel();
        lblUsername7 = new javax.swing.JLabel();
        lblUsername8 = new javax.swing.JLabel();
        lblUsername9 = new javax.swing.JLabel();
        lblUsername10 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername = new javax.swing.JTextField();
        txtFName = new javax.swing.JTextField();
        txtLName = new javax.swing.JTextField();
        txtStreet = new javax.swing.JTextField();
        txtCity = new javax.swing.JTextField();
        txtCountry = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtMobile = new javax.swing.JTextField();
        cmbPosition = new javax.swing.JComboBox<>();
        btnAddUser = new javax.swing.JButton();
        btnUpdateUser = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblAlert = new javax.swing.JLabel();
        lblUsername11 = new javax.swing.JLabel();
        txtNic = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnGetUser = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearchUser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        manageUserNavContainer1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lblOpenManageProduct = new javax.swing.JLabel();
        lblOpenManageeUser = new javax.swing.JLabel();
        lblOpenManageSale = new javax.swing.JLabel();
        lblOpenManageDashboard = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manage User");

        manageUserContainer.setBackground(new java.awt.Color(0, 102, 102));
        manageUserContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        managerBodyContainer.setBackground(new java.awt.Color(255, 255, 255));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "UserID", "Username", "Password", "Name", "Address", "Mobile", "Email", "Nic"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(userTable);

        crudUserPanel.setBackground(new java.awt.Color(0, 0, 0));
        crudUserPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username");
        crudUserPanel.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        lblUsername1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername1.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername1.setText("Password");
        crudUserPanel.add(lblUsername1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, 20));

        lblUsername3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername3.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername3.setText("First Name");
        crudUserPanel.add(lblUsername3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lblUsername4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername4.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername4.setText("Last Name");
        crudUserPanel.add(lblUsername4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        lblUsername5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername5.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername5.setText("Street");
        crudUserPanel.add(lblUsername5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        lblUsername6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername6.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername6.setText("City");
        crudUserPanel.add(lblUsername6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        lblUsername7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername7.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername7.setText("Country");
        crudUserPanel.add(lblUsername7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        lblUsername8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername8.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername8.setText("Email");
        crudUserPanel.add(lblUsername8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        lblUsername9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername9.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername9.setText("Mobile");
        crudUserPanel.add(lblUsername9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        lblUsername10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername10.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername10.setText("Position");
        crudUserPanel.add(lblUsername10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 140, -1));

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 260, -1));

        txtFName.setBackground(new java.awt.Color(255, 255, 255));
        txtFName.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        txtFName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFNameActionPerformed(evt);
            }
        });
        crudUserPanel.add(txtFName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 260, -1));

        txtLName.setBackground(new java.awt.Color(255, 255, 255));
        txtLName.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtLName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 260, -1));

        txtStreet.setBackground(new java.awt.Color(255, 255, 255));
        txtStreet.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtStreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 260, -1));

        txtCity.setBackground(new java.awt.Color(255, 255, 255));
        txtCity.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 140, -1));

        txtCountry.setBackground(new java.awt.Color(255, 255, 255));
        txtCountry.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 140, -1));

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 140, -1));

        txtMobile.setBackground(new java.awt.Color(255, 255, 255));
        txtMobile.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtMobile, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 140, -1));

        cmbPosition.setBackground(new java.awt.Color(255, 255, 255));
        cmbPosition.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbPosition.setForeground(new java.awt.Color(0, 102, 102));
        cmbPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cashier" }));
        cmbPosition.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 51), new java.awt.Color(0, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(cmbPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 140, -1));

        btnAddUser.setBackground(new java.awt.Color(0, 102, 204));
        btnAddUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(255, 255, 255));
        btnAddUser.setText("ADD");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });
        crudUserPanel.add(btnAddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 110, 50));

        btnUpdateUser.setBackground(new java.awt.Color(0, 153, 153));
        btnUpdateUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdateUser.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateUser.setText("UPDATE");
        btnUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserActionPerformed(evt);
            }
        });
        crudUserPanel.add(btnUpdateUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 110, 56));

        btnClear.setBackground(new java.awt.Color(255, 51, 51));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("CLEAR");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        crudUserPanel.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 110, 55));
        crudUserPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 380, -1));

        lblAlert.setText(" ");
        crudUserPanel.add(lblAlert, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 380, -1));

        lblUsername11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername11.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername11.setText("NIC");
        crudUserPanel.add(lblUsername11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        txtNic.setBackground(new java.awt.Color(255, 255, 255));
        txtNic.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        crudUserPanel.add(txtNic, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 140, -1));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        btnDelete.setBackground(new java.awt.Color(255, 0, 51));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnGetUser.setBackground(new java.awt.Color(0, 102, 102));
        btnGetUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGetUser.setText("Check");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGetUser)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDelete, btnGetUser});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(btnGetUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        btnSearchUser.setBackground(new java.awt.Color(0, 102, 102));
        btnSearchUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearchUser.setText("SEARCH");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search Cashier");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout managerBodyContainerLayout = new javax.swing.GroupLayout(managerBodyContainer);
        managerBodyContainer.setLayout(managerBodyContainerLayout);
        managerBodyContainerLayout.setHorizontalGroup(
            managerBodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managerBodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(managerBodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(crudUserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
        );
        managerBodyContainerLayout.setVerticalGroup(
            managerBodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managerBodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(crudUserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        manageUserContainer.add(managerBodyContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 980, 430));

        manageUserNavContainer1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("MANAGE USERS");

        btnLogout.setBackground(new java.awt.Color(0, 102, 102));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("LogOut");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lblOpenManageProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/img/productmanage.gif"))); // NOI18N
        lblOpenManageProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOpenManageProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOpenManageProductMouseClicked(evt);
            }
        });

        lblOpenManageeUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/img/usermanage.gif"))); // NOI18N
        lblOpenManageeUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOpenManageeUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOpenManageeUserMouseClicked(evt);
            }
        });

        lblOpenManageSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/img/salesmanage.gif"))); // NOI18N
        lblOpenManageSale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOpenManageSale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOpenManageSaleMouseClicked(evt);
            }
        });

        lblOpenManageDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/img/home.gif"))); // NOI18N
        lblOpenManageDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOpenManageDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOpenManageDashboardMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout manageUserNavContainer1Layout = new javax.swing.GroupLayout(manageUserNavContainer1);
        manageUserNavContainer1.setLayout(manageUserNavContainer1Layout);
        manageUserNavContainer1Layout.setHorizontalGroup(
            manageUserNavContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageUserNavContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblOpenManageeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 429, Short.MAX_VALUE)
                .addComponent(lblOpenManageDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOpenManageProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOpenManageSale, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        manageUserNavContainer1Layout.setVerticalGroup(
            manageUserNavContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageUserNavContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageUserNavContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageUserNavContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblOpenManageSale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblOpenManageeUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblOpenManageProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblOpenManageDashboard, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel4))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        manageUserContainer.add(manageUserNavContainer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(manageUserContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(manageUserContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void validateFields() {
        if (isValidationEnabled) {
            return; // Skip validation if the flag is false
        }
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String country = txtCountry.getText();
        String email = txtEmail.getText();
        String mobile = txtMobile.getText();
        String nic = txtNic.getText();
        String position = cmbPosition.getSelectedItem().toString();

        boolean areFieldsFilled = !username.isEmpty() && !password.isEmpty()
                && !fName.isEmpty() && !lName.isEmpty() && !street.isEmpty() && !city.isEmpty() && !nic.isEmpty()
                && !country.isEmpty() && !email.isEmpty() && !mobile.isEmpty() && !position.isEmpty();

        boolean isPasswordValid = isValidPassword(password);

        if (areFieldsFilled && isPasswordValid) {
            btnAddUser.setEnabled(true);
            btnUpdateUser.setEnabled(true);
            lblAlert.setText("All are Good");
            lblAlert.setForeground(Color.GREEN);
        } else {
            btnAddUser.setEnabled(false);
            btnUpdateUser.setEnabled(false);
            lblAlert.setText("Please fill in all required fields and ensure a valid password.");
            lblAlert.setForeground(Color.RED);
        }
    }

    private void txtSetPasswordKeyReleased(java.awt.event.KeyEvent evt) {
        String password = new String(txtPassword.getPassword());
        if (isValidPassword(password)) {
            btnAddUser.setEnabled(true);
            btnUpdateUser.setEnabled(true);
            lblAlert.setText("All are Good.");
            lblAlert.setForeground(Color.GREEN);
        } else {
            btnAddUser.setEnabled(false);
            btnUpdateUser.setEnabled(false);
            lblAlert.setText("Password Need 8 CaracterLong.");
            lblAlert.setForeground(Color.RED);
        }
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[a-zA-Z].*");
    }


    private void lblOpenManageDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOpenManageDashboardMouseClicked
        openManageDashboard();
    }//GEN-LAST:event_lblOpenManageDashboardMouseClicked

    private void lblOpenManageSaleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOpenManageSaleMouseClicked
        openManageSalesForm();
    }//GEN-LAST:event_lblOpenManageSaleMouseClicked

    private void lblOpenManageeUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOpenManageeUserMouseClicked

    }//GEN-LAST:event_lblOpenManageeUserMouseClicked

    private void lblOpenManageProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOpenManageProductMouseClicked
        openManageProductsForm();
    }//GEN-LAST:event_lblOpenManageProductMouseClicked

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to Logout?", "Logout Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            currentUser.logout();
            dispose(); // Close the window
            // Optionally, perform actions to exit the application
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void txtFNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFNameActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed

        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String country = txtCountry.getText();
        String email = txtEmail.getText();
        String mobile = txtMobile.getText();
        String nic = txtNic.getText();
        String position = cmbPosition.getSelectedItem().toString();
        boolean areFieldsFilled = !username.isEmpty() && !password.isEmpty()
                && !fName.isEmpty() && !lName.isEmpty() && !street.isEmpty() && !city.isEmpty() && !nic.isEmpty()
                && !country.isEmpty() && !email.isEmpty() && !mobile.isEmpty() && !position.isEmpty();
        // Example: If validation passes, add the user or perform additional actions
        if (areFieldsFilled) {
            if (manager.createUser(username, password, fName, lName, mobile, email, street, city, country, position, nic)) {
                JOptionPane.showMessageDialog(ManageUsers.this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                btnAddUser.setEnabled(false);
                btnAddUser.setEnabled(false);
                clearAndReloadData();
            } else {
                JOptionPane.showMessageDialog(ManageUsers.this, "Failed to add the user. Username may already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(ManageUsers.this, "Fill Required all fields with valid password to add the user", "Error", JOptionPane.ERROR_MESSAGE);
            clearAndReloadData();
        }

    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserActionPerformed
        isValidationEnabled = true;
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String country = txtCountry.getText();
        String email = txtEmail.getText();
        String mobile = txtMobile.getText();
        String nic = txtNic.getText();
        String position = cmbPosition.getSelectedItem().toString();

        boolean areFieldsFilled = !username.isEmpty() && !password.isEmpty()
                && !fName.isEmpty() && !lName.isEmpty() && !street.isEmpty() && !city.isEmpty() && !nic.isEmpty()
                && !country.isEmpty() && !email.isEmpty() && !mobile.isEmpty() && !position.isEmpty();
        // Example: If validation passes, add the user or perform additional actions
        if (areFieldsFilled) {
            if (manager.updateUser(username, password, fName, lName, mobile, email, street, city, country, nic)) {
                JOptionPane.showMessageDialog(ManageUsers.this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear the text fields and set the combo box to its default value
                // Update the user table with the new user's data
                btnAddUser.setEnabled(false);
                btnUpdateUser.setEnabled(false);
                clearAndReloadData();
            } else {
                JOptionPane.showMessageDialog(ManageUsers.this, "Failed to add the user. Username may already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(ManageUsers.this, "Fill Required all fields with valid password to add the user", "Error", JOptionPane.ERROR_MESSAGE);
            clearAndReloadData();
        }

    }//GEN-LAST:event_btnUpdateUserActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearAndReloadData();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedUser != null) {
            int userId = selectedUser.getUserId();

            if (manager.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "User removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the selected user and update the user table
                selectedUser = null;
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove the user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void loadUserData() {

        List<User> Cashiers = manager.getCashiers(); // Retrieve product data

        // Clear existing data from the table
        userTableModel.setRowCount(0);

        for (User cashier : Cashiers) {
            // Add the product data to the table
            userTableModel.addRow(new Object[]{
                cashier.getUserId(),
                cashier.getUsername(),
                cashier.getPassword(),
                cashier.getName(),
                cashier.getAddress(),
                cashier.getEmail(),
                cashier.getMobile(),
                cashier.getNic()
            });
        }
    }

    private void openManageProductsForm() {
        if ("Manager".equals(user.getPosition())) {
            // Open ManagerForm with the user object
            java.awt.EventQueue.invokeLater(() -> {
                new ManageProduct(user).setVisible(true);
                this.dispose();
            });
        } else {
            JOptionPane.showMessageDialog(null, "You Dont Have Access.", "Error", JOptionPane.ERROR_MESSAGE);
        }// Calls the UserController method to open Manage Users form
    }

    private void openManageSalesForm() {
        if ("Manager".equals(user.getPosition())) {
            // Open ManagerForm with the user object
            java.awt.EventQueue.invokeLater(() -> {
                new ManageSales(user).setVisible(true);
                this.dispose();
            });
        } else {
            JOptionPane.showMessageDialog(null, "You Dont Have Access.", "Error", JOptionPane.ERROR_MESSAGE);
        }// Calls the UserController method to open Manage Users form
    }

    private void openManageDashboard() {
        if ("Manager".equals(user.getPosition())) {
            // Open ManagerForm with the user object
            java.awt.EventQueue.invokeLater(() -> {
                new ManagerDashboard(user).setVisible(true);
                this.dispose();
            });
        } else {
            JOptionPane.showMessageDialog(null, "You Dont Have Access.", "Error", JOptionPane.ERROR_MESSAGE);
        }// Calls the UserController method to open Manage Users form
    }

    private void clearAndReloadData() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtNic.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtCountry.setText("");
        txtEmail.setText("");
        txtMobile.setText("");
        cmbPosition.setSelectedIndex(0);
        lblAlert.setText(" ");
        btnAddUser.setEnabled(false);
        btnUpdateUser.setEnabled(false);
        loadUserData();
    }

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
            java.util.logging.Logger.getLogger(ManageUsers.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageUsers.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageUsers.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageUsers.class
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
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnGetUser;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSearchUser;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JComboBox<String> cmbPosition;
    private javax.swing.JPanel crudUserPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAlert;
    private javax.swing.JLabel lblOpenManageDashboard;
    private javax.swing.JLabel lblOpenManageProduct;
    private javax.swing.JLabel lblOpenManageSale;
    private javax.swing.JLabel lblOpenManageeUser;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsername1;
    private javax.swing.JLabel lblUsername10;
    private javax.swing.JLabel lblUsername11;
    private javax.swing.JLabel lblUsername3;
    private javax.swing.JLabel lblUsername4;
    private javax.swing.JLabel lblUsername5;
    private javax.swing.JLabel lblUsername6;
    private javax.swing.JLabel lblUsername7;
    private javax.swing.JLabel lblUsername8;
    private javax.swing.JLabel lblUsername9;
    private javax.swing.JPanel manageUserContainer;
    private javax.swing.JPanel manageUserNavContainer1;
    private javax.swing.JPanel managerBodyContainer;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtCountry;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFName;
    private javax.swing.JTextField txtLName;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtNic;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStreet;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
