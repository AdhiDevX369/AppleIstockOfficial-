/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

// Import the controller class responsible for managing the application
import Controller.ManagerController;

// Import the User model class, which likely represents user information
import Models.User;

// Import necessary classes for handling actions and events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import a class for formatting dates
import java.text.SimpleDateFormat;

// Import a class for working with dates
import java.util.Date;

// Import a list data structure to manage collections of data
import java.util.List;

// Import classes for displaying dialog boxes and user notifications
import javax.swing.JOptionPane;

// Import a class for handling time-related events using timers
import javax.swing.Timer;

// Import classes for handling selection events in a table
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Import a class for managing and displaying tabular data in GUI components
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adithya
 */
public class ManageUserForm extends javax.swing.JFrame {

    private DefaultTableModel userTableModel;
    private User selectedUser;
    private int selectedUserId;
    private ManagerController manager;

    public ManageUserForm(User user) {
        manager = new ManagerController(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getEmail(), user.getPhone(), user.getNic());
        initComponents();

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedUser != null) {
                    // Set the class-level variable with the selected user's user ID
                    selectedUserId = selectedUser.getUserId();

                    // Set the text fields with the selected user's information
                    txtSetUsername.setText(selectedUser.getUsername());
                    txtSetFullname.setText(selectedUser.getName());
                    txtSetPassword.setText(selectedUser.getPassword());
                    txtSetEmaill.setText(selectedUser.getEmail());
                    txtSetAddress.setText(selectedUser.getAddress());
                    txtSetNIC.setText(selectedUser.getNic());
                    txtSetPhone.setText(selectedUser.getPhone());

                    // Set the selected user's position in the ComboBox
                    String userPosition = selectedUser.getPosition();
                    cmbPosition.setSelectedItem(userPosition);
                }
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle user search logic
                String searchKeyword = txtSearch.getText();

                List<User> searchResults = manager.searchCashiers(searchKeyword); // Assuming manager has a method called searchUser

                // Clear existing data from the table
                userTableModel.setRowCount(0);

                for (User user : searchResults) {
                    // Add the search results to the table
                    userTableModel.addRow(new Object[]{
                        user.getUserId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getName(),
                        user.getPosition(),
                        user.getAddress(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getNic()
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
                "Position");
        userTableModel.addColumn(
                "Address");
        userTableModel.addColumn(
                "Email");
        userTableModel.addColumn(
                "Phone No");
        userTableModel.addColumn(
                "NIC");

        // Set the table model for your product table
        viewUserTable.setModel(userTableModel);
        viewUserTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && viewUserTable.getSelectedRow() != -1) {
                    int selectedRow = viewUserTable.getSelectedRow();

                    // Get the user data from the selected row
                    int userId = (int) viewUserTable.getValueAt(selectedRow, 0); // Assuming the first column contains User ID
                    String username = (String) viewUserTable.getValueAt(selectedRow, 1); // Assuming the second column contains username
                    String password = (String) viewUserTable.getValueAt(selectedRow, 2); // Assuming the third column contains password
                    String name = (String) viewUserTable.getValueAt(selectedRow, 3); // Assuming the fourth column contains name
                    String position = (String) viewUserTable.getValueAt(selectedRow, 4); // Assuming the fifth column contains position
                    String address = (String) viewUserTable.getValueAt(selectedRow, 5); // Assuming the sixth column contains address
                    String email = (String) viewUserTable.getValueAt(selectedRow, 6); // Assuming the seventh column contains email
                    String phone = (String) viewUserTable.getValueAt(selectedRow, 7); // Assuming the eighth column contains phone number
                    String nic = (String) viewUserTable.getValueAt(selectedRow, 8); // Assuming the ninth column contains NIC

                    // Create a User instance from the selected data
                    selectedUser = new User(userId, username, password, position, name, address, email, phone, nic);
                }
            }
        });

        loadUserData();
        setLogingData();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateAndTimeLabel();
            }
        });

        timer.start();

    }

    private void loadUserData() {
        List<User> users = manager.viewCashierDetails(); // Retrieve user data

        // Clear existing data from the table
        userTableModel.setRowCount(0);

        for (User user : users) {
            // Add the user data to the table
            userTableModel.addRow(new Object[]{
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getPosition(),
                user.getAddress(),
                user.getEmail(),
                user.getPhone(),
                user.getNic()
            });
        }

    }

    private void updateDateAndTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define your desired date and time format
        String formattedDate = sdf.format(new Date()); // Get the current date and time

        lblDateAndTime.setText("Date and Time: " + formattedDate); // Update the label text
    }

    private void setLogingData() {
        lblLoggedUserName.setText("Username: " + manager.getUsername());
        lblLoggedFulllName.setText("Full Name: " + manager.getName());
        lblLoggedEmail.setText("Email: " + manager.getEmail());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userManagerPanel = new javax.swing.JPanel();
        managerDPanel = new javax.swing.JPanel();
        SearchPanel = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblDateAndTime = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        viewUserTable = new javax.swing.JTable();
        panelManageBtn = new javax.swing.JPanel();
        btnViewSales = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        commonPannel = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblLoggedUserName = new javax.swing.JLabel();
        lblLoggedFulllName = new javax.swing.JLabel();
        lblLoggedEmail = new javax.swing.JLabel();
        lblSetPassword = new javax.swing.JLabel();
        txtSetPassword = new javax.swing.JPasswordField();
        lblSetNIC = new javax.swing.JLabel();
        txtSetNIC = new javax.swing.JTextField();
        lblSetFullname = new javax.swing.JLabel();
        txtSetFullname = new javax.swing.JTextField();
        txtSetUsername = new javax.swing.JTextField();
        lblSetUsername = new javax.swing.JLabel();
        lblSetAddress = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSetAddress = new javax.swing.JTextArea();
        lblSetEmail = new javax.swing.JLabel();
        txtSetEmaill = new javax.swing.JTextField();
        lblSetPosition = new javax.swing.JLabel();
        cmbPosition = new javax.swing.JComboBox<>();
        lblSetPhone = new javax.swing.JLabel();
        txtSetPhone = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        managerDPanel.setBackground(new java.awt.Color(255, 255, 255));

        SearchPanel.setBackground(new java.awt.Color(0, 102, 204));

        txtSearch.setBackground(new java.awt.Color(255, 255, 255));
        txtSearch.setForeground(new java.awt.Color(0, 0, 0));
        txtSearch.setText("Search User");
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
                .addGap(32, 32, 32)
                .addComponent(lblDateAndTime, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        viewUserTable.setBackground(new java.awt.Color(255, 255, 255));
        viewUserTable.setForeground(new java.awt.Color(0, 102, 204));
        viewUserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "User ID", "Username", "Password", "Name", "Position", "Address", "Email", "Phone No", "NIC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(viewUserTable);

        panelManageBtn.setBackground(new java.awt.Color(0, 102, 255));

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
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelManageBtnLayout = new javax.swing.GroupLayout(panelManageBtn);
        panelManageBtn.setLayout(panelManageBtnLayout);
        panelManageBtnLayout.setHorizontalGroup(
            panelManageBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelManageBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(btnViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelManageBtnLayout.setVerticalGroup(
            panelManageBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelManageBtnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelManageBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelManageBtnLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnRemove, btnViewSales});

        commonPannel.setBackground(new java.awt.Color(0, 102, 204));
        commonPannel.setForeground(new java.awt.Color(0, 0, 0));

        btnRefresh.setBackground(new java.awt.Color(0, 0, 0));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(0, 102, 204));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(0, 0, 0));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(0, 102, 204));
        btnLogout.setText("Back To Products");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("MANAGE YOUR USERS");

        lblLoggedUserName.setBackground(new java.awt.Color(255, 255, 255));
        lblLoggedUserName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLoggedUserName.setForeground(new java.awt.Color(255, 255, 255));
        lblLoggedUserName.setText("Username: ");

        lblLoggedFulllName.setBackground(new java.awt.Color(255, 255, 255));
        lblLoggedFulllName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLoggedFulllName.setForeground(new java.awt.Color(255, 255, 255));
        lblLoggedFulllName.setText("Full Name: ");

        lblLoggedEmail.setBackground(new java.awt.Color(255, 255, 255));
        lblLoggedEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLoggedEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblLoggedEmail.setText("Email:");

        javax.swing.GroupLayout commonPannelLayout = new javax.swing.GroupLayout(commonPannel);
        commonPannel.setLayout(commonPannelLayout);
        commonPannelLayout.setHorizontalGroup(
            commonPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commonPannelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(commonPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(commonPannelLayout.createSequentialGroup()
                        .addGroup(commonPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLoggedUserName)
                            .addComponent(lblLoggedFulllName)
                            .addComponent(lblLoggedEmail))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, commonPannelLayout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(57, 57, 57))
        );
        commonPannelLayout.setVerticalGroup(
            commonPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, commonPannelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblLoggedUserName)
                .addGap(18, 18, 18)
                .addComponent(lblLoggedFulllName)
                .addGap(18, 18, 18)
                .addComponent(lblLoggedEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblSetPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetPassword.setForeground(new java.awt.Color(0, 0, 0));
        lblSetPassword.setText("Set Password: ");

        txtSetPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtSetPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSetPassword.setForeground(new java.awt.Color(0, 0, 0));

        lblSetNIC.setBackground(new java.awt.Color(255, 255, 255));
        lblSetNIC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetNIC.setForeground(new java.awt.Color(0, 0, 0));
        lblSetNIC.setText("NIC:");

        txtSetNIC.setBackground(new java.awt.Color(255, 255, 255));
        txtSetNIC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSetNIC.setForeground(new java.awt.Color(0, 0, 0));

        lblSetFullname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetFullname.setForeground(new java.awt.Color(0, 0, 0));
        lblSetFullname.setText("Full Name:");

        txtSetFullname.setBackground(new java.awt.Color(255, 255, 255));
        txtSetFullname.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSetFullname.setForeground(new java.awt.Color(0, 0, 0));

        txtSetUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtSetUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSetUsername.setForeground(new java.awt.Color(0, 0, 0));

        lblSetUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetUsername.setForeground(new java.awt.Color(0, 0, 0));
        lblSetUsername.setText("Username:");

        lblSetAddress.setBackground(new java.awt.Color(255, 255, 255));
        lblSetAddress.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetAddress.setForeground(new java.awt.Color(0, 0, 0));
        lblSetAddress.setText("Address:");

        txtSetAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtSetAddress.setColumns(20);
        txtSetAddress.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSetAddress.setForeground(new java.awt.Color(0, 0, 0));
        txtSetAddress.setRows(5);
        jScrollPane1.setViewportView(txtSetAddress);

        lblSetEmail.setBackground(new java.awt.Color(255, 255, 255));
        lblSetEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetEmail.setForeground(new java.awt.Color(0, 0, 0));
        lblSetEmail.setText("Email");

        txtSetEmaill.setBackground(new java.awt.Color(255, 255, 255));
        txtSetEmaill.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSetEmaill.setForeground(new java.awt.Color(0, 0, 0));

        lblSetPosition.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetPosition.setForeground(new java.awt.Color(0, 0, 0));
        lblSetPosition.setText("Position: ");

        cmbPosition.setBackground(new java.awt.Color(255, 255, 255));
        cmbPosition.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbPosition.setForeground(new java.awt.Color(0, 0, 0));
        cmbPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Poition", "Cashier" }));

        lblSetPhone.setBackground(new java.awt.Color(255, 255, 255));
        lblSetPhone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSetPhone.setForeground(new java.awt.Color(0, 0, 0));
        lblSetPhone.setText("Phone");

        txtSetPhone.setBackground(new java.awt.Color(255, 255, 255));
        txtSetPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSetPhone.setForeground(new java.awt.Color(0, 0, 0));

        btnAdd.setBackground(new java.awt.Color(0, 102, 204));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd.setText("Add New");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 102, 204));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout managerDPanelLayout = new javax.swing.GroupLayout(managerDPanel);
        managerDPanel.setLayout(managerDPanelLayout);
        managerDPanelLayout.setHorizontalGroup(
            managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(managerDPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(managerDPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(managerDPanelLayout.createSequentialGroup()
                        .addComponent(commonPannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(managerDPanelLayout.createSequentialGroup()
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSetEmail)
                                    .addComponent(lblSetAddress)
                                    .addComponent(lblSetPassword)
                                    .addComponent(lblSetFullname)
                                    .addComponent(lblSetUsername))
                                .addGap(26, 26, 26)
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSetUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(txtSetFullname, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSetPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txtSetEmaill, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(managerDPanelLayout.createSequentialGroup()
                                        .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSetNIC)
                                            .addComponent(lblSetPhone)
                                            .addComponent(lblSetPosition))
                                        .addGap(98, 98, 98)
                                        .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSetPhone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSetNIC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbPosition, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, managerDPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap())))
                            .addGroup(managerDPanelLayout.createSequentialGroup()
                                .addComponent(panelManageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        managerDPanelLayout.setVerticalGroup(
            managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managerDPanelLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commonPannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(managerDPanelLayout.createSequentialGroup()
                        .addComponent(panelManageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(managerDPanelLayout.createSequentialGroup()
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSetUsername)
                                    .addComponent(txtSetUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSetNIC)
                                    .addComponent(txtSetNIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSetFullname)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtSetFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSetPhone)
                                        .addComponent(txtSetPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSetPassword)
                                    .addComponent(txtSetPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSetPosition)
                                    .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSetEmaill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSetEmail))
                                .addGroup(managerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(managerDPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(managerDPanelLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(lblSetAddress))))))))
        );

        javax.swing.GroupLayout userManagerPanelLayout = new javax.swing.GroupLayout(userManagerPanel);
        userManagerPanel.setLayout(userManagerPanelLayout);
        userManagerPanelLayout.setHorizontalGroup(
            userManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userManagerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(managerDPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        userManagerPanelLayout.setVerticalGroup(
            userManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userManagerPanelLayout.createSequentialGroup()
                .addComponent(managerDPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userManagerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(userManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadUserData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // Get the selected user's information (you should have code for this)
        int userId = selectedUserId; // Replace this with how you retrieve the selected user's ID
        String username = txtSetUsername.getText();
        String password = txtSetPassword.getText();
        String name = txtSetFullname.getText();
        String address = txtSetAddress.getText();
        String email = txtSetEmaill.getText();
        String phone = txtSetPhone.getText();
        String nic = txtSetNIC.getText();
        String position = cmbPosition.getSelectedItem().toString();

        // Call your updateCurrentUser method (you need to implement this method)
        if (manager.updateUserData(userId, username, password, position, name, address, email, phone, nic)) {
            // User updated successfully
            JOptionPane.showMessageDialog(null, "User updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear the text fields and combo box
            txtSetUsername.setText("");
            txtSetPassword.setText("");
            txtSetFullname.setText("");
            txtSetAddress.setText("");
            txtSetEmaill.setText("");
            txtSetPhone.setText("");
            txtSetNIC.setText("");
            cmbPosition.setSelectedIndex(0);

            // Reload user data
            loadUserData();
        } else {
            // Display an error message if updating the user failed
            JOptionPane.showMessageDialog(null, "Failed to update the user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Retrieve user input from the text fields and combo box
        String username = txtSetUsername.getText();
        String password = new String(txtSetPassword.getPassword()); // Password field
        String name = txtSetFullname.getText();
        String address = txtSetAddress.getText();
        String email = txtSetEmaill.getText();
        String phone = txtSetPhone.getText();
        String nic = txtSetNIC.getText();
        String position = cmbPosition.getSelectedItem().toString();

        // Check if any of the required fields are empty
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Call the addUser method in ManagerController to add the user
            if (manager.addUser(username, password, name, address, email, phone, nic, position)) {
                JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the text fields and set the combo box to its default value
                txtSetUsername.setText("");
                txtSetPassword.setText("");
                txtSetFullname.setText("");
                txtSetAddress.setText("");
                txtSetEmaill.setText("");
                txtSetPhone.setText("");
                txtSetNIC.setText("");
                cmbPosition.setSelectedIndex(0);

                // Update the user table with the new user's data
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add the user. Username may already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (selectedUser != null) {
            int userId = selectedUser.getUserId();

            if (manager.removeUser(userId)) {
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
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
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
            java.util.logging.Logger.getLogger(ManageUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnViewSales;
    private javax.swing.JComboBox<String> cmbPosition;
    private javax.swing.JPanel commonPannel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDateAndTime;
    private javax.swing.JLabel lblLoggedEmail;
    private javax.swing.JLabel lblLoggedFulllName;
    private javax.swing.JLabel lblLoggedUserName;
    private javax.swing.JLabel lblSetAddress;
    private javax.swing.JLabel lblSetEmail;
    private javax.swing.JLabel lblSetFullname;
    private javax.swing.JLabel lblSetNIC;
    private javax.swing.JLabel lblSetPassword;
    private javax.swing.JLabel lblSetPhone;
    private javax.swing.JLabel lblSetPosition;
    private javax.swing.JLabel lblSetUsername;
    private javax.swing.JPanel managerDPanel;
    private javax.swing.JPanel panelManageBtn;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextArea txtSetAddress;
    private javax.swing.JTextField txtSetEmaill;
    private javax.swing.JTextField txtSetFullname;
    private javax.swing.JTextField txtSetNIC;
    private javax.swing.JPasswordField txtSetPassword;
    private javax.swing.JTextField txtSetPhone;
    private javax.swing.JTextField txtSetUsername;
    private javax.swing.JPanel userManagerPanel;
    private javax.swing.JTable viewUserTable;
    // End of variables declaration//GEN-END:variables
}
