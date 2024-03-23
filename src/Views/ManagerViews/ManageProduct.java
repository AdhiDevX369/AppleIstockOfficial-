/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.ManagerViews;

import Controllers.ManagerController;
import Controllers.UserController;
import Models.Product;
import Models.ProductData;
import Models.User;
import Views.ManagerDashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adithya
 */
public class ManageProduct extends javax.swing.JFrame {

  private DefaultTableModel productTableModel;
  private DefaultTableModel productDataTableModel;

  private ProductData selectedProductData;
  private ManagerController manager;
  private UserController currentUser;
  private User user;

  public ManageProduct(User user) {
    initComponents();
    manager =
      new ManagerController(
        user.getUserId(),
        user.getUsername(),
        user.getPassword(),
        user.getName(),
        user.getAddress(),
        user.getEmail(),
        user.getMobile(),
        user.getNic()
      );
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    currentUser = new UserController();
    this.user = user;
    addWindowListener(
      new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          int confirmed = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to exit?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION
          );

          if (confirmed == JOptionPane.YES_OPTION) {
            dispose(); // Close the window
            // Optionally, perform actions to exit the application
          }
        }
      }
    );
    btnSearch.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Handle search logic
          String searchKeyword = txtSearch.getText();

          // Perform the product search
          List<ProductData> searchResults = manager.getSearchedData(
            searchKeyword
          );

          // Clear existing data from the table
          productDataTableModel.setRowCount(0);

          // Add the search results to the product table
          for (ProductData product : searchResults) {
            productDataTableModel.addRow(
              new Object[] {
                product.getProductName(),
                product.getProductId(),
                product.getCategory(),
                product.getEminum(),
                product.getStatus(),
                product.getPrice(),
              }
            );
          }
          txtSearch.setText(" ");
        }
      }
    );
    btnUpdate.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (selectedProductData != null) {
            // Set the text fields in the invoicePanel with the selected product's information
            txtProductId.setText(
              Integer.toString(selectedProductData.getProductId())
            );
            txtProductName.setText(selectedProductData.getProductName());
            txtEminum.setText(selectedProductData.getEminum());
            txtPrice.setText(Double.toString(selectedProductData.getPrice()));

            // Set the selected product's category in the ComboBox
            String productCategory = selectedProductData.getCategory();
            cmbCategory.setSelectedItem(productCategory);

            String productStatus = selectedProductData.getStatus();
            cmbStatus.setSelectedItem(productStatus);
          }
        }
      }
    );
    btnDone.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            int productId = Integer.parseInt(txtProductId.getText());
            String productName = txtProductName.getText();
            double productPrice = Double.parseDouble(txtPrice.getText());
            String eminum = txtEminum.getText();
            String productCategory = cmbCategory.getSelectedItem().toString();
            String productStatus = cmbStatus.getSelectedItem().toString();

            if (
              productName.isEmpty() ||
              productCategory.isEmpty() ||
              eminum.isEmpty()
            ) {
              // Display an error message if any essential field is empty
              JOptionPane.showMessageDialog(
                ManageProduct.this,
                "Please fill in all the required fields.",
                "Error",
                JOptionPane.ERROR_MESSAGE
              );
            } else if (productId <= 0 || productPrice <= 0) {
              // Display an error message if productId or productPrice is not positive
              JOptionPane.showMessageDialog(
                ManageProduct.this,
                "Product ID and Price must be positive numbers.",
                "Error",
                JOptionPane.ERROR_MESSAGE
              );
            } else {
              if (
                manager.updateProductData(
                  eminum,
                  productName,
                  productCategory,
                  productId,
                  productStatus,
                  productPrice
                )
              ) {
                // Product added successfully
                JOptionPane.showMessageDialog(
                  ManageProduct.this,
                  "Product Updated successfully.",
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE
                );
                // Clear the text fields and combo box
                clearAndRefresh();
                loadProductData();
                loadProductCategoryData();
              } else {
                // Display an error message if adding the product failed
                JOptionPane.showMessageDialog(
                  ManageProduct.this,
                  "Failed to Update the product.",
                  "Error",
                  JOptionPane.ERROR_MESSAGE
                );
              }
            }
          } catch (NumberFormatException ex) {
            // Handle the case where non-numeric input was entered for productId or productPrice
            JOptionPane.showMessageDialog(
              ManageProduct.this,
              "Please enter valid numbers for Product ID and Price.",
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );

    btnAdd.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            int productId = Integer.parseInt(txtProductId.getText());
            String productName = txtProductName.getText();
            double productPrice = Double.parseDouble(txtPrice.getText());
            String eminum = txtEminum.getText();
            String productCategory = cmbCategory.getSelectedItem().toString();
            String productStatus = cmbStatus.getSelectedItem().toString();

            if (
              productName.isEmpty() ||
              productCategory.isEmpty() ||
              eminum.isEmpty()
            ) {
              // Display an error message if any essential field is empty
              JOptionPane.showMessageDialog(
                ManageProduct.this,
                "Please fill in all the required fields.",
                "Error",
                JOptionPane.ERROR_MESSAGE
              );
            } else if (productId <= 0 || productPrice <= 0) {
              // Display an error message if productId or productPrice is not positive
              JOptionPane.showMessageDialog(
                ManageProduct.this,
                "Product ID and Price must be positive numbers.",
                "Error",
                JOptionPane.ERROR_MESSAGE
              );
            } else {
              if (
                manager.createProductData(
                  productName,
                  productId,
                  productCategory,
                  eminum,
                  productStatus,
                  productPrice
                )
              ) {
                // Product added successfully
                JOptionPane.showMessageDialog(
                  ManageProduct.this,
                  "Product Added successfully.",
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE
                );
                // Clear the text fields and combo box
                clearAndRefresh();
                loadProductData();
                loadProductCategoryData();
              } else {
                // Display an error message if adding the product failed
                JOptionPane.showMessageDialog(
                  ManageProduct.this,
                  "Failed to Add the product.",
                  "Error",
                  JOptionPane.ERROR_MESSAGE
                );
              }
            }
          } catch (NumberFormatException ex) {
            // Handle the case where non-numeric input was entered for productId or productPrice
            JOptionPane.showMessageDialog(
              ManageProduct.this,
              "Please enter valid numbers for Product ID and Price.",
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );

    btnDelete.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (selectedProductData != null) {
            String eminum = selectedProductData.getEminum();
            boolean removed = manager.deleteProductData(eminum);
            if (removed) {
              JOptionPane.showMessageDialog(
                ManageProduct.this,
                "Successfully Removed the item.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
              );
              loadProductData();
              loadProductCategoryData();
            } else {
              JOptionPane.showMessageDialog(
                ManageProduct.this,
                "Failed to remove the item.",
                "Error",
                JOptionPane.ERROR_MESSAGE
              );
            }
          }
        }
      }
    );

    productDataTableModel = new DefaultTableModel();
    productDataTableModel.addColumn("ProductName");
    productDataTableModel.addColumn("ProductID");
    productDataTableModel.addColumn("Category");
    productDataTableModel.addColumn("Eminum");
    productDataTableModel.addColumn("Status");
    productDataTableModel.addColumn("Price");

    productTableModel = new DefaultTableModel();
    productTableModel.addColumn("ProductID");
    productTableModel.addColumn("Category");
    productTableModel.addColumn("AvailableQTY");

    viewProductCategoryTable.setModel(productTableModel);
    productDataTable.setModel(productDataTableModel);

    // Load product data when the form is initialized
    productDataTable
      .getSelectionModel()
      .addListSelectionListener(
        new ListSelectionListener() {
          @Override
          public void valueChanged(ListSelectionEvent e) {
            if (
              !e.getValueIsAdjusting() &&
              productDataTable.getSelectedRow() != -1
            ) {
              int selectedRow = productDataTable.getSelectedRow();

              // Assuming the columns are in the order: ProductName, ProductID, Category, Eminum, Status, Price
              String productName = (String) productDataTable.getValueAt(
                selectedRow,
                0
              );
              int productId = (int) productDataTable.getValueAt(selectedRow, 1);
              String category = (String) productDataTable.getValueAt(
                selectedRow,
                2
              );
              String eminum = (String) productDataTable.getValueAt(
                selectedRow,
                3
              );
              String status = (String) productDataTable.getValueAt(
                selectedRow,
                4
              );
              double price = (double) productDataTable.getValueAt(
                selectedRow,
                5
              );

              // Create a ProductData instance from the selected data
              selectedProductData =
                new ProductData(
                  productName,
                  productId,
                  category,
                  eminum,
                  status,
                  price
                );
            }
          }
        }
      );

    loadProductData();
    loadProductCategoryData();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    manageProductContainer = new javax.swing.JPanel();
    manageProductNavContainer = new javax.swing.JPanel();
    jLabel4 = new javax.swing.JLabel();
    btnLogout = new javax.swing.JButton();
    lblOpenManageProduct = new javax.swing.JLabel();
    lblOpenManageeUser = new javax.swing.JLabel();
    lblOpenManageSale = new javax.swing.JLabel();
    lblOpenManageDashboard = new javax.swing.JLabel();
    managerProductBodyContainer = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    productDataTable = new javax.swing.JTable();
    jScrollPane2 = new javax.swing.JScrollPane();
    viewProductCategoryTable = new javax.swing.JTable();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    txtEminum = new javax.swing.JTextField();
    txtProductId = new javax.swing.JTextField();
    txtPrice = new javax.swing.JTextField();
    txtProductName = new javax.swing.JTextField();
    cmbCategory = new javax.swing.JComboBox<>();
    cmbStatus = new javax.swing.JComboBox<>();
    jPanel1 = new javax.swing.JPanel();
    jLabel8 = new javax.swing.JLabel();
    txtSearch = new javax.swing.JTextField();
    btnSearch = new javax.swing.JButton();
    btnUpdate = new javax.swing.JButton();
    btnDelete = new javax.swing.JButton();
    btnDone = new javax.swing.JButton();
    btnClear = new javax.swing.JButton();
    btnAdd = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("Manage Products");
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    manageProductContainer.setBackground(new java.awt.Color(0, 102, 102));

    manageProductNavContainer.setBackground(new java.awt.Color(51, 51, 51));

    jLabel4.setBackground(new java.awt.Color(51, 51, 51));
    jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel4.setText("MANAGER PRODUCTS");

    btnLogout.setBackground(new java.awt.Color(0, 102, 102));
    btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    btnLogout.setForeground(new java.awt.Color(255, 255, 255));
    btnLogout.setText("LogOut");
    btnLogout.addActionListener(
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          btnLogoutActionPerformed(evt);
        }
      }
    );

    lblOpenManageProduct.setIcon(
      new javax.swing.ImageIcon(
        getClass().getResource("/Views/img/productmanage.gif")
      )
    ); // NOI18N
    lblOpenManageProduct.setCursor(
      new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
    );
    lblOpenManageProduct.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          lblOpenManageProductMouseClicked(evt);
        }
      }
    );

    lblOpenManageeUser.setIcon(
      new javax.swing.ImageIcon(
        getClass().getResource("/Views/img/usermanage.gif")
      )
    ); // NOI18N
    lblOpenManageeUser.setCursor(
      new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
    );
    lblOpenManageeUser.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          lblOpenManageeUserMouseClicked(evt);
        }
      }
    );

    lblOpenManageSale.setIcon(
      new javax.swing.ImageIcon(
        getClass().getResource("/Views/img/salesmanage.gif")
      )
    ); // NOI18N
    lblOpenManageSale.setCursor(
      new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
    );
    lblOpenManageSale.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          lblOpenManageSaleMouseClicked(evt);
        }
      }
    );

    lblOpenManageDashboard.setIcon(
      new javax.swing.ImageIcon(getClass().getResource("/Views/img/home.gif"))
    ); // NOI18N
    lblOpenManageDashboard.setCursor(
      new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
    );
    lblOpenManageDashboard.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          lblOpenManageDashboardMouseClicked(evt);
        }
      }
    );

    managerProductBodyContainer.setBackground(
      new java.awt.Color(255, 255, 255)
    );

    productDataTable.setBackground(new java.awt.Color(153, 153, 153));
    productDataTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    productDataTable.setForeground(new java.awt.Color(0, 0, 0));
    productDataTable.setModel(
      new javax.swing.table.DefaultTableModel(
        new Object[][] {
          { null, null, null, null, null, null },
          { null, null, null, null, null, null },
          { null, null, null, null, null, null },
          { null, null, null, null, null, null },
        },
        new String[] {
          "ProductName",
          "ProductID",
          "Category",
          "Eminum",
          "Status",
          "Price",
        }
      ) {
        Class[] types = new Class[] {
          java.lang.String.class,
          java.lang.Integer.class,
          java.lang.String.class,
          java.lang.String.class,
          java.lang.String.class,
          java.lang.Double.class,
        };

        public Class getColumnClass(int columnIndex) {
          return types[columnIndex];
        }
      }
    );
    productDataTable.setGridColor(new java.awt.Color(153, 153, 153));
    productDataTable.setSelectionBackground(new java.awt.Color(0, 0, 0));
    productDataTable.setSelectionForeground(new java.awt.Color(153, 153, 153));
    productDataTable.setSelectionMode(
      javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION
    );
    jScrollPane1.setViewportView(productDataTable);

    viewProductCategoryTable.setBackground(new java.awt.Color(204, 204, 204));
    viewProductCategoryTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    viewProductCategoryTable.setForeground(new java.awt.Color(0, 0, 0));
    viewProductCategoryTable.setModel(
      new javax.swing.table.DefaultTableModel(
        new Object[][] {
          { null, null, null },
          { null, null, null },
          { null, null, null },
          { null, null, null },
        },
        new String[] { "ProductID", "Category", "AvailableQTY" }
      ) {
        Class[] types = new Class[] {
          java.lang.Integer.class,
          java.lang.String.class,
          java.lang.Integer.class,
        };

        public Class getColumnClass(int columnIndex) {
          return types[columnIndex];
        }
      }
    );
    viewProductCategoryTable.setSelectionBackground(
      new java.awt.Color(0, 0, 0)
    );
    jScrollPane2.setViewportView(viewProductCategoryTable);

    jLabel1.setBackground(new java.awt.Color(255, 255, 255));
    jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(0, 102, 102));
    jLabel1.setText("ProductID:");

    jLabel2.setBackground(new java.awt.Color(255, 255, 255));
    jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(0, 102, 102));
    jLabel2.setText("Product Name:");

    jLabel3.setBackground(new java.awt.Color(255, 255, 255));
    jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(0, 102, 102));
    jLabel3.setText("Product Category");

    jLabel5.setBackground(new java.awt.Color(255, 255, 255));
    jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(0, 102, 102));
    jLabel5.setText("Product Eminum:");

    jLabel6.setBackground(new java.awt.Color(255, 255, 255));
    jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(0, 102, 102));
    jLabel6.setText("Product Status:");

    jLabel7.setBackground(new java.awt.Color(255, 255, 255));
    jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(0, 102, 102));
    jLabel7.setText("Product Price");

    txtEminum.setBackground(new java.awt.Color(255, 255, 255));
    txtEminum.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    txtEminum.setForeground(new java.awt.Color(0, 0, 0));
    txtEminum.addActionListener(
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          txtEminumActionPerformed(evt);
        }
      }
    );

    txtProductId.setBackground(new java.awt.Color(255, 255, 255));
    txtProductId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    txtProductId.setForeground(new java.awt.Color(0, 0, 0));
    txtProductId.addActionListener(
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          txtProductIdActionPerformed(evt);
        }
      }
    );

    txtPrice.setBackground(new java.awt.Color(255, 255, 255));
    txtPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    txtPrice.setForeground(new java.awt.Color(0, 0, 0));
    txtPrice.addActionListener(
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          txtPriceActionPerformed(evt);
        }
      }
    );

    txtProductName.setBackground(new java.awt.Color(255, 255, 255));
    txtProductName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    txtProductName.setForeground(new java.awt.Color(0, 0, 0));

    cmbCategory.setBackground(new java.awt.Color(255, 255, 255));
    cmbCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    cmbCategory.setForeground(new java.awt.Color(0, 0, 0));
    cmbCategory.setModel(
      new javax.swing.DefaultComboBoxModel<>(
        new String[] {
          "Select",
          "iPhone",
          "AppleWatch",
          "iPad",
          "AirPods",
          "Mac",
        }
      )
    );

    cmbStatus.setBackground(new java.awt.Color(255, 255, 255));
    cmbStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    cmbStatus.setForeground(new java.awt.Color(0, 0, 0));
    cmbStatus.setModel(
      new javax.swing.DefaultComboBoxModel<>(
        new String[] { "Select", "sold", "unsold" }
      )
    );

    jPanel1.setBackground(new java.awt.Color(0, 0, 0));

    jLabel8.setBackground(new java.awt.Color(0, 0, 0));
    jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(255, 255, 255));
    jLabel8.setText("SEARCH PRODUCT");

    txtSearch.setBackground(new java.awt.Color(255, 255, 255));
    txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    txtSearch.setForeground(new java.awt.Color(0, 153, 153));

    btnSearch.setBackground(new java.awt.Color(0, 102, 102));
    btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    btnSearch.setText("SEARCH");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
      jPanel1
    );
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          jPanel1Layout
            .createSequentialGroup()
            .addContainerGap()
            .addComponent(
              jLabel8,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              125,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addGap(18, 18, 18)
            .addComponent(
              txtSearch,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              170,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addPreferredGap(
              javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
            )
            .addComponent(
              btnSearch,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              97,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addContainerGap(10, Short.MAX_VALUE)
        )
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          javax.swing.GroupLayout.Alignment.TRAILING,
          jPanel1Layout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
              jPanel1Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(
                  btnSearch,
                  javax.swing.GroupLayout.DEFAULT_SIZE,
                  javax.swing.GroupLayout.DEFAULT_SIZE,
                  Short.MAX_VALUE
                )
                .addGroup(
                  javax.swing.GroupLayout.Alignment.LEADING,
                  jPanel1Layout
                    .createParallelGroup(
                      javax.swing.GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(
                      txtSearch,
                      javax.swing.GroupLayout.PREFERRED_SIZE,
                      39,
                      javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(
                      jLabel8,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      Short.MAX_VALUE
                    )
                )
            )
            .addContainerGap()
        )
    );

    btnUpdate.setBackground(new java.awt.Color(0, 102, 204));
    btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    btnUpdate.setText("Update");

    btnDelete.setBackground(new java.awt.Color(255, 0, 0));
    btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    btnDelete.setForeground(new java.awt.Color(255, 255, 255));
    btnDelete.setText("Delete");

    btnDone.setBackground(new java.awt.Color(0, 102, 102));
    btnDone.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    btnDone.setText("Done");

    btnClear.setBackground(new java.awt.Color(51, 51, 51));
    btnClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    btnClear.setForeground(new java.awt.Color(255, 255, 255));
    btnClear.setText("Clear");
    btnClear.addActionListener(
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          btnClearActionPerformed(evt);
        }
      }
    );

    btnAdd.setBackground(new java.awt.Color(0, 102, 204));
    btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    btnAdd.setText("Add New Product");

    javax.swing.GroupLayout managerProductBodyContainerLayout = new javax.swing.GroupLayout(
      managerProductBodyContainer
    );
    managerProductBodyContainer.setLayout(managerProductBodyContainerLayout);
    managerProductBodyContainerLayout.setHorizontalGroup(
      managerProductBodyContainerLayout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          managerProductBodyContainerLayout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
              managerProductBodyContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(
                  managerProductBodyContainerLayout
                    .createSequentialGroup()
                    .addComponent(
                      jScrollPane1,
                      javax.swing.GroupLayout.PREFERRED_SIZE,
                      526,
                      javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.LEADING
                        )
                        .addGroup(
                          managerProductBodyContainerLayout
                            .createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(
                              managerProductBodyContainerLayout
                                .createParallelGroup(
                                  javax.swing.GroupLayout.Alignment.LEADING
                                )
                                .addComponent(
                                  jPanel1,
                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                  Short.MAX_VALUE
                                )
                                .addGroup(
                                  managerProductBodyContainerLayout
                                    .createSequentialGroup()
                                    .addGroup(
                                      managerProductBodyContainerLayout
                                        .createParallelGroup(
                                          javax.swing.GroupLayout.Alignment.LEADING
                                        )
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5)
                                    )
                                    .addPreferredGap(
                                      javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                      javax.swing.GroupLayout.DEFAULT_SIZE,
                                      Short.MAX_VALUE
                                    )
                                    .addGroup(
                                      managerProductBodyContainerLayout
                                        .createParallelGroup(
                                          javax.swing.GroupLayout.Alignment.TRAILING,
                                          false
                                        )
                                        .addComponent(
                                          txtProductName,
                                          javax.swing.GroupLayout.Alignment.LEADING
                                        )
                                        .addComponent(
                                          cmbCategory,
                                          javax.swing.GroupLayout.Alignment.LEADING,
                                          0,
                                          249,
                                          Short.MAX_VALUE
                                        )
                                        .addComponent(
                                          txtEminum,
                                          javax.swing.GroupLayout.Alignment.LEADING
                                        )
                                        .addComponent(txtProductId)
                                    )
                                    .addGap(41, 41, 41)
                                )
                                .addGroup(
                                  managerProductBodyContainerLayout
                                    .createSequentialGroup()
                                    .addGroup(
                                      managerProductBodyContainerLayout
                                        .createParallelGroup(
                                          javax.swing.GroupLayout.Alignment.LEADING
                                        )
                                        .addGroup(
                                          managerProductBodyContainerLayout
                                            .createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addGap(18, 18, 18)
                                            .addComponent(
                                              txtPrice,
                                              javax.swing.GroupLayout.PREFERRED_SIZE,
                                              290,
                                              javax.swing.GroupLayout.PREFERRED_SIZE
                                            )
                                        )
                                        .addGroup(
                                          managerProductBodyContainerLayout
                                            .createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(
                                              javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                            )
                                            .addComponent(
                                              cmbStatus,
                                              javax.swing.GroupLayout.PREFERRED_SIZE,
                                              290,
                                              javax.swing.GroupLayout.PREFERRED_SIZE
                                            )
                                        )
                                    )
                                    .addGap(0, 0, Short.MAX_VALUE)
                                )
                            )
                        )
                        .addGroup(
                          managerProductBodyContainerLayout
                            .createSequentialGroup()
                            .addPreferredGap(
                              javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
                            )
                            .addComponent(
                              btnDone,
                              javax.swing.GroupLayout.DEFAULT_SIZE,
                              160,
                              Short.MAX_VALUE
                            )
                            .addPreferredGap(
                              javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
                            )
                            .addComponent(
                              btnClear,
                              javax.swing.GroupLayout.PREFERRED_SIZE,
                              194,
                              javax.swing.GroupLayout.PREFERRED_SIZE
                            )
                            .addGap(0, 0, Short.MAX_VALUE)
                        )
                    )
                )
                .addGroup(
                  managerProductBodyContainerLayout
                    .createSequentialGroup()
                    .addComponent(
                      jScrollPane2,
                      javax.swing.GroupLayout.PREFERRED_SIZE,
                      315,
                      javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addPreferredGap(
                      javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.LEADING
                        )
                        .addComponent(
                          btnUpdate,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          199,
                          Short.MAX_VALUE
                        )
                        .addGroup(
                          managerProductBodyContainerLayout
                            .createSequentialGroup()
                            .addComponent(
                              btnDelete,
                              javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE,
                              Short.MAX_VALUE
                            )
                            .addPreferredGap(
                              javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
                            )
                            .addComponent(
                              btnAdd,
                              javax.swing.GroupLayout.PREFERRED_SIZE,
                              405,
                              javax.swing.GroupLayout.PREFERRED_SIZE
                            )
                        )
                    )
                    .addGap(0, 0, Short.MAX_VALUE)
                )
            )
            .addContainerGap()
        )
    );

    managerProductBodyContainerLayout.linkSize(
      javax.swing.SwingConstants.HORIZONTAL,
      new java.awt.Component[] { btnDelete, btnDone, btnUpdate }
    );

    managerProductBodyContainerLayout.setVerticalGroup(
      managerProductBodyContainerLayout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          javax.swing.GroupLayout.Alignment.TRAILING,
          managerProductBodyContainerLayout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
              managerProductBodyContainerLayout
                .createParallelGroup(
                  javax.swing.GroupLayout.Alignment.TRAILING,
                  false
                )
                .addGroup(
                  managerProductBodyContainerLayout
                    .createSequentialGroup()
                    .addComponent(
                      jPanel1,
                      javax.swing.GroupLayout.PREFERRED_SIZE,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addPreferredGap(
                      javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      Short.MAX_VALUE
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(
                          jLabel1,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          28,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(
                          txtProductId,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          28,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addGap(15, 15, 15)
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(jLabel2)
                        .addComponent(
                          txtProductName,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          22,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addPreferredGap(
                      javax.swing.LayoutStyle.ComponentPlacement.RELATED
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(
                          cmbCategory,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          30,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(
                          jLabel3,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          39,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addPreferredGap(
                      javax.swing.LayoutStyle.ComponentPlacement.RELATED
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(jLabel5)
                        .addComponent(
                          txtEminum,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          28,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addPreferredGap(
                      javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(
                          jLabel6,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          28,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(
                          cmbStatus,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          28,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addPreferredGap(
                      javax.swing.LayoutStyle.ComponentPlacement.RELATED
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.BASELINE
                        )
                        .addComponent(
                          jLabel7,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          28,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(
                          txtPrice,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          28,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                )
                .addComponent(
                  jScrollPane1,
                  javax.swing.GroupLayout.PREFERRED_SIZE,
                  290,
                  javax.swing.GroupLayout.PREFERRED_SIZE
                )
            )
            .addPreferredGap(
              javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
            )
            .addGroup(
              managerProductBodyContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(
                  jScrollPane2,
                  javax.swing.GroupLayout.PREFERRED_SIZE,
                  126,
                  javax.swing.GroupLayout.PREFERRED_SIZE
                )
                .addGroup(
                  managerProductBodyContainerLayout
                    .createSequentialGroup()
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.TRAILING,
                          false
                        )
                        .addComponent(
                          btnDone,
                          javax.swing.GroupLayout.Alignment.LEADING,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          45,
                          Short.MAX_VALUE
                        )
                        .addComponent(
                          btnUpdate,
                          javax.swing.GroupLayout.Alignment.LEADING,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          Short.MAX_VALUE
                        )
                        .addComponent(
                          btnClear,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          Short.MAX_VALUE
                        )
                    )
                    .addPreferredGap(
                      javax.swing.LayoutStyle.ComponentPlacement.RELATED
                    )
                    .addGroup(
                      managerProductBodyContainerLayout
                        .createParallelGroup(
                          javax.swing.GroupLayout.Alignment.LEADING,
                          false
                        )
                        .addComponent(
                          btnDelete,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          javax.swing.GroupLayout.DEFAULT_SIZE,
                          Short.MAX_VALUE
                        )
                        .addComponent(
                          btnAdd,
                          javax.swing.GroupLayout.PREFERRED_SIZE,
                          41,
                          javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                )
            )
            .addContainerGap(158, Short.MAX_VALUE)
        )
    );

    managerProductBodyContainerLayout.linkSize(
      javax.swing.SwingConstants.VERTICAL,
      new java.awt.Component[] { txtProductId, txtProductName }
    );

    javax.swing.GroupLayout manageProductNavContainerLayout = new javax.swing.GroupLayout(
      manageProductNavContainer
    );
    manageProductNavContainer.setLayout(manageProductNavContainerLayout);
    manageProductNavContainerLayout.setHorizontalGroup(
      manageProductNavContainerLayout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          manageProductNavContainerLayout
            .createSequentialGroup()
            .addContainerGap()
            .addComponent(
              lblOpenManageProduct,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              50,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel4)
            .addPreferredGap(
              javax.swing.LayoutStyle.ComponentPlacement.RELATED,
              javax.swing.GroupLayout.DEFAULT_SIZE,
              Short.MAX_VALUE
            )
            .addComponent(
              lblOpenManageDashboard,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              50,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(
              lblOpenManageeUser,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              50,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(
              lblOpenManageSale,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              50,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addGap(18, 18, 18)
            .addComponent(
              btnLogout,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              157,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addGap(19, 19, 19)
        )
        .addComponent(
          managerProductBodyContainer,
          javax.swing.GroupLayout.Alignment.TRAILING,
          javax.swing.GroupLayout.DEFAULT_SIZE,
          javax.swing.GroupLayout.DEFAULT_SIZE,
          Short.MAX_VALUE
        )
    );
    manageProductNavContainerLayout.setVerticalGroup(
      manageProductNavContainerLayout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          manageProductNavContainerLayout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
              manageProductNavContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                  manageProductNavContainerLayout
                    .createParallelGroup(
                      javax.swing.GroupLayout.Alignment.LEADING,
                      false
                    )
                    .addComponent(
                      lblOpenManageSale,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      Short.MAX_VALUE
                    )
                    .addComponent(
                      lblOpenManageeUser,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      Short.MAX_VALUE
                    )
                    .addComponent(
                      lblOpenManageProduct,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      Short.MAX_VALUE
                    )
                    .addComponent(
                      btnLogout,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      javax.swing.GroupLayout.DEFAULT_SIZE,
                      Short.MAX_VALUE
                    )
                    .addComponent(
                      lblOpenManageDashboard,
                      javax.swing.GroupLayout.Alignment.TRAILING
                    )
                )
                .addComponent(jLabel4)
            )
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(
              managerProductBodyContainer,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              javax.swing.GroupLayout.DEFAULT_SIZE,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addContainerGap(
              javax.swing.GroupLayout.DEFAULT_SIZE,
              Short.MAX_VALUE
            )
        )
    );

    javax.swing.GroupLayout manageProductContainerLayout = new javax.swing.GroupLayout(
      manageProductContainer
    );
    manageProductContainer.setLayout(manageProductContainerLayout);
    manageProductContainerLayout.setHorizontalGroup(
      manageProductContainerLayout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          manageProductContainerLayout
            .createSequentialGroup()
            .addComponent(
              manageProductNavContainer,
              javax.swing.GroupLayout.DEFAULT_SIZE,
              javax.swing.GroupLayout.DEFAULT_SIZE,
              Short.MAX_VALUE
            )
            .addContainerGap()
        )
    );
    manageProductContainerLayout.setVerticalGroup(
      manageProductContainerLayout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
          manageProductContainerLayout
            .createSequentialGroup()
            .addComponent(
              manageProductNavContainer,
              javax.swing.GroupLayout.PREFERRED_SIZE,
              javax.swing.GroupLayout.DEFAULT_SIZE,
              javax.swing.GroupLayout.PREFERRED_SIZE
            )
            .addGap(0, 0, Short.MAX_VALUE)
        )
    );

    getContentPane()
      .add(
        manageProductContainer,
        new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 500)
      );

    pack();
  } // </editor-fold>//GEN-END:initComponents

  private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnLogoutActionPerformed
    int confirmed = JOptionPane.showConfirmDialog(
      null,
      "Are you sure you want to Logout?",
      "Logout Confirmation",
      JOptionPane.YES_NO_OPTION
    );

    if (confirmed == JOptionPane.YES_OPTION) {
      currentUser.logout();
      dispose(); // Close the window
      // Optionally, perform actions to exit the application
    }
  } //GEN-LAST:event_btnLogoutActionPerformed

  private void lblOpenManageProductMouseClicked(
    java.awt.event.MouseEvent evt
  ) {} //GEN-FIRST:event_lblOpenManageProductMouseClicked //GEN-LAST:event_lblOpenManageProductMouseClicked

  private void lblOpenManageeUserMouseClicked(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblOpenManageeUserMouseClicked
    openManageUsersForm();
  } //GEN-LAST:event_lblOpenManageeUserMouseClicked

  private void lblOpenManageSaleMouseClicked(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblOpenManageSaleMouseClicked
    openManageSalesForm();
  } //GEN-LAST:event_lblOpenManageSaleMouseClicked

  private void lblOpenManageDashboardMouseClicked(
    java.awt.event.MouseEvent evt
  ) { //GEN-FIRST:event_lblOpenManageDashboardMouseClicked
    openManageDashboard();
  } //GEN-LAST:event_lblOpenManageDashboardMouseClicked

  private void txtEminumActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtEminumActionPerformed
    // TODO add your handling code here:
  } //GEN-LAST:event_txtEminumActionPerformed

  private void txtProductIdActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtProductIdActionPerformed
    // TODO add your handling code here:
  } //GEN-LAST:event_txtProductIdActionPerformed

  private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtPriceActionPerformed
    // TODO add your handling code here:
  } //GEN-LAST:event_txtPriceActionPerformed

  private void btnClearActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnClearActionPerformed
    clearAndRefresh();
  } //GEN-LAST:event_btnClearActionPerformed

  private void openManageUsersForm() {
    if ("Manager".equals(user.getPosition())) {
      // Open ManagerForm with the user object
      java.awt.EventQueue.invokeLater(() -> {
        new ManageUsers(user).setVisible(true);
        this.dispose();
      });
    } else {
      JOptionPane.showMessageDialog(
        null,
        "You Dont Have Access.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    } // Calls the UserController method to open Manage Users form
  }

  private void clearAndRefresh() {
    txtProductName.setText("");
    txtEminum.setText("");
    txtProductId.setText("");
    txtPrice.setText("");
    loadProductData();
    loadProductCategoryData();
    cmbCategory.setSelectedIndex(0);
    cmbStatus.setSelectedIndex(0);
    txtSearch.setText(" ");
  }

  private void openManageSalesForm() {
    if ("Manager".equals(user.getPosition())) {
      // Open ManagerForm with the user object
      java.awt.EventQueue.invokeLater(() -> {
        new ManageSales(user).setVisible(true);
        this.dispose();
      });
    } else {
      JOptionPane.showMessageDialog(
        null,
        "You Dont Have Access.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    } // Calls the UserController method to open Manage Users form
  }

  private void openManageDashboard() {
    if ("Manager".equals(user.getPosition())) {
      // Open ManagerForm with the user object
      java.awt.EventQueue.invokeLater(() -> {
        new ManagerDashboard(user).setVisible(true);
        this.dispose();
      });
    } else {
      JOptionPane.showMessageDialog(
        null,
        "You Dont Have Access.",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    } // Calls the UserController method to open Manage Users form
  }

  private void loadProductData() {
    List<ProductData> products = manager.viewAllProductData(); // Retrieve product data

    // Clear existing data from the table
    productDataTableModel.setRowCount(0);

    for (ProductData product : products) {
      // Add the product data to the table
      productDataTableModel.addRow(
        new Object[] {
          product.getProductName(),
          product.getProductId(),
          product.getCategory(),
          product.getEminum(),
          product.getStatus(),
          product.getPrice(),
        }
      );
    }
  }

  private void loadProductCategoryData() {
    List<Product> productCategories = manager.getAllProductCategories(); // Retrieve product data

    // Clear existing data from the table
    productTableModel.setRowCount(0);

    for (Product product : productCategories) {
      // Add the product data to the table
      productTableModel.addRow(
        new Object[] {
          product.getProductId(),
          product.getCategory(),
          product.getStockQuantity(),
        }
      );
    }
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
      java.util.logging.Logger
        .getLogger(ManageProduct.class.getName())
        .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger
        .getLogger(ManageProduct.class.getName())
        .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger
        .getLogger(ManageProduct.class.getName())
        .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger
        .getLogger(ManageProduct.class.getName())
        .log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(
      new Runnable() {
        public void run() {}
      }
    );
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnAdd;
  private javax.swing.JButton btnClear;
  private javax.swing.JButton btnDelete;
  private javax.swing.JButton btnDone;
  private javax.swing.JButton btnLogout;
  private javax.swing.JButton btnSearch;
  private javax.swing.JButton btnUpdate;
  private javax.swing.JComboBox<String> cmbCategory;
  private javax.swing.JComboBox<String> cmbStatus;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JLabel lblOpenManageDashboard;
  private javax.swing.JLabel lblOpenManageProduct;
  private javax.swing.JLabel lblOpenManageSale;
  private javax.swing.JLabel lblOpenManageeUser;
  private javax.swing.JPanel manageProductContainer;
  private javax.swing.JPanel manageProductNavContainer;
  private javax.swing.JPanel managerProductBodyContainer;
  private javax.swing.JTable productDataTable;
  private javax.swing.JTextField txtEminum;
  private javax.swing.JTextField txtPrice;
  private javax.swing.JTextField txtProductId;
  private javax.swing.JTextField txtProductName;
  private javax.swing.JTextField txtSearch;
  private javax.swing.JTable viewProductCategoryTable;
  // End of variables declaration//GEN-END:variables
}
