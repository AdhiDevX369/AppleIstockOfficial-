/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Controllers.CashierController;
import Controllers.UserController;
import Models.Product;
import Models.ProductData;
import Models.SaleData;
import Models.User;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.slf4j.Logger;

/**
 *
 * @author Adithya
 */
public class CashierDashboard extends javax.swing.JFrame {

    private DefaultTableModel productDataTableModel;
    private DefaultTableModel productTableModel;
    private Product selectedProduct;
    private ProductData selectedProductData;
    private double totalBill;
    private int totalQuantity;
    private DefaultListModel<String> invoiceListModel;
    private CashierController cashier;
    private UserController currentUser;

    /**
     * Creates new form CashierDashboard
     */
    public CashierDashboard(User user) {
        initComponents();
        invoiceListModel = new DefaultListModel<>();

        cashier = new CashierController(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getEmail(), user.getMobile(), user.getNic());
        currentUser = new UserController();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?", "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    System.exit(0); // Close the window
                    // Optionally, perform actions to exit the application
                }
            }
        });

        btnSerch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle search logic
                String searchKeyword = txtSearch.getText();

                // Perform the product search
                List<ProductData> searchResults = cashier.getSearchedData(searchKeyword);

                // Clear existing data from the table
                productDataTableModel.setRowCount(0);

                // Add the search results to the product table
                for (ProductData product : searchResults) {
                    productDataTableModel.addRow(new Object[]{
                        product.getProductName(),
                        product.getProductId(),
                        product.getCategory(),
                        product.getEminum(),
                        product.getStatus(),
                        product.getPrice()
                    });
                }
            }
        });

        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = cmbFilterProduct.getSelectedIndex();
                String status = null;

                if (value == 0) {
                    status = "sold";
                } else if (value == 1) {
                    status = "unsold";
                } else if (value == 2) {
                    status = "all";
                } else {
                    String errorMessage = "Invalid selection. Please try again.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if an invalid selection is made
                }

                // Perform the product search based on the selected status
                List<ProductData> filterResults = cashier.getProductsByStatus(status);

                // Clear existing data from the table
                productDataTableModel.setRowCount(0);

                // Add the search results to the product table
                for (ProductData product : filterResults) {
                    productDataTableModel.addRow(new Object[]{
                        product.getProductName(),
                        product.getProductId(),
                        product.getCategory(),
                        product.getEminum(),
                        product.getStatus(),
                        product.getPrice()
                    });
                }
            }
        });

        btnAddList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProductData != null) {
                    // Set the text fields in the invoicePanel with the selected product's information
                    txtProductName.setText(selectedProductData.getProductName());
                    txtQty.setText("1");
                    txtEminum.setText(selectedProductData.getEminum());
                    txtPrice.setText(Double.toString(selectedProductData.getPrice()));

                } else {
                    // Show an error message or handle the case where no product is selected
                    JOptionPane.showMessageDialog(null, "Please select a product before adding it to the list.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve data from text fields
                String productName = txtProductName.getText();
                String priceText = txtPrice.getText();
                String qtyText = txtQty.getText();
                String eminum = txtEminum.getText();

                if (productName.isEmpty() || priceText.isEmpty() || qtyText.isEmpty() || eminum.isEmpty()) {
                    // Display a message to the user that some fields are empty
                    JOptionPane.showMessageDialog(CashierDashboard.this, "First Select Products.", "Missing Products", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the actionPerformed method
                }

                double price;
                int quantity;

                try {
                    price = Double.parseDouble(priceText);
                    quantity = Integer.parseInt(qtyText);
                } catch (NumberFormatException ex) {
                    // Display a message to the user that price and quantity must be valid numbers
                    JOptionPane.showMessageDialog(CashierDashboard.this, "Please enter valid numbers for Price and Quantity.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the actionPerformed method
                }

                if (quantity <= 0) {
                    // Display a message to the user that quantity must be greater than 0
                    JOptionPane.showMessageDialog(CashierDashboard.this, "Product Is Out Of Stock", "Out Of Stock", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the actionPerformed method
                }

                // Calculate the total bill and quantity
                double productPrice = quantity * price;
                totalBill += calculatePrice();
                totalQuantity += quantity;

                // Add the product information to the invoice list
                String productInfo = "Product Name: " + productName + " | "
                        + "Eminumber: " + eminum + " | "
                        + "Price: $" + price + " | "
                        + "Quantity: " + quantity + " | "
                        + "Full Price: " + productPrice;

                invoiceListModel.addElement(productInfo); // Add to the model

                // Update the total bill and quantity displayed on the form
                lblTotalBill.setText("Total Bill: $" + totalBill);
                lblTotalQty.setText("Total Quantity: " + totalQuantity);

                // Optionally, you can clear the text fields
                txtProductName.setText("");
                txtPrice.setText("");
                txtQty.setText("");
                txtEminum.setText("");

            }
        });

        btnRemoveList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = invoiceList.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Retrieve the selected item from the list
                    String removedItem = invoiceListModel.getElementAt(selectedIndex);

                    // Extract the quantity and price of the removed item
                    int quantityRemoved = extractQuantityFromProductInfo(removedItem);

                    // Update the total bill and quantity
                    totalQuantity -= quantityRemoved;
                    totalBill -= calculatePrice();

                    // Remove the selected item from the list
                    invoiceListModel.remove(selectedIndex);

                    // Update the total bill and quantity displayed on the form
                    lblTotalBill.setText("Total Bill: $" + totalBill);
                    lblTotalQty.setText("Total Quantity: " + totalQuantity);
                }
            }
        });

        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, SaleData> saleDataMap = new HashMap<>();
                if (invoiceListModel.isEmpty()) {
                    // Show a message using JOptionPane
                    JOptionPane.showMessageDialog(CashierDashboard.this, "The invoice list is empty. Please add products before printing the invoice.", "Empty Invoice List", JOptionPane.WARNING_MESSAGE);
                } else {
                    boolean allSalesSuccessful = true;

                    try {
                        // Define the PDF file path and name
                        String pdfFileName = "invoice.pdf";
                        PdfWriter pdfWriter = new PdfWriter(pdfFileName);
                        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                        Document document = new Document(pdfDocument);

                        // Use embedded Arial font
                        PdfFont font = PdfFontFactory.createFont();
                        float fontSize = 12;
                        float headerFontSize = 20;
                        float midFontSize = 14;
                        // Create a table for product details with headers
                        Table productTable = new Table(UnitValue.createPercentArray(new float[]{20, 10, 10, 10, 10}));
                        productTable.setWidth(UnitValue.createPercentValue(100));
                        // Add table headers
                        productTable.addHeaderCell("Product Name");
                        productTable.addHeaderCell("Eminumber");
                        productTable.addHeaderCell("Quantity");
                        productTable.addHeaderCell("Price per Unit");
                        productTable.addHeaderCell("Total Price");

                        String invoice_number = generateInvoiceNumber();

                        for (int i = 0; i < invoiceListModel.size(); i++) {
                            String productInfo = invoiceListModel.get(i);
                            String[] productData = productInfo.split("\\|");
                            String productName = productData[0].trim().split(":")[1].trim();
                            String eminum = productData[1].trim().split(":")[1].trim();
                            double price = Double.parseDouble(productData[2].trim().split(":")[1].trim().replace("$", ""));
                            int quantity = Integer.parseInt(productData[3].trim().split(":")[1].trim());

                            double productPrice = price * quantity;
                            // Get the product ID for the sold product (You may need to fetch it from the database)
                            int productId = getpId(productName);

                            // Add product details to the table
                            productTable.addCell(productName);
                            productTable.addCell(eminum);
                            productTable.addCell(String.valueOf(quantity));
                            productTable.addCell("$" + price);
                            productTable.addCell("$" + productPrice);

                            SaleData saleData = saleDataMap.getOrDefault(productId, new SaleData(productId, 0, 0.0, eminum)); // Define SaleData structure
                            saleData.incrementQuantity(quantity); // Update quantity
                            saleData.addToTotalPrice(productPrice);
                            saleData.addEminum(eminum);// Update total price
                            saleDataMap.put(productId, saleData); // Store updated SaleData
                        }

                        // Create sale records for grouped products
                        for (SaleData saleData : saleDataMap.values()) {
                            int productId = saleData.getProductId();
                            int quantity = saleData.getQuantity();
                            double totalPrice = saleData.getTotalPrice();

                            // Sell the product and update its quantity
                            if (!cashier.makeSale(user.getUserId(), productId, quantity, totalPrice, saleData.getEminumList(), invoice_number)) {
                                allSalesSuccessful = false;
                                break;
                            }
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = sdf.format(new Date()); // Get the current date and time
                        // Add the invoice header with styled text
                        Paragraph invoiceHeader = new Paragraph()
                                .setFont(font)
                                .setFontSize(headerFontSize)
                                .add("BILL INVOICE")
                                .add("\nInvoiceNo: " + invoice_number)
                                .add("\n------------------------------- ")
                                .setTextAlignment(TextAlignment.CENTER);

                        Paragraph invoiceMid = new Paragraph()
                                .setFont(font)
                                .setFontSize(midFontSize)
                                .add("\nDate: " + formattedDate)
                                .add("\nContact No: +94 712 494 420")
                                .add("\nEmail: info@adhistore.com")
                                .add("\nCompany: ADHI STORE")
                                .add("\n-----------------------")
                                .setTextAlignment(TextAlignment.CENTER);

                        Paragraph lowerInvoice = new Paragraph()
                                .setFont(font)
                                .setFontSize(fontSize)
                                .add("\n Total Price: $" + totalBill)
                                .add("\nCashier: " + user.getName())
                                .add("\n")
                                .setTextAlignment(TextAlignment.LEFT);

                        Paragraph end = new Paragraph()
                                .setFont(font)
                                .setFontSize(midFontSize)
                                .add("\n***** THANK YOU COME AGAIN *****")
                                .setTextAlignment(TextAlignment.CENTER);

                        // Add product details to the PDF document with styled text
                        document.add(invoiceHeader);
                        document.add(invoiceMid);
                        document.add(productTable);
                        document.add(lowerInvoice);
                        document.add(end);

                        document.close(); // Close the PDF document

                        if (allSalesSuccessful) {
                            // Successfully sold products and updated quantities
                            loadProductData(); // Update the product table
                            invoiceListModel.clear(); // Clear the invoice list
                            lblTotalBill.setText(" ");
                            lblTotalQty.setText(" ");

                            // Show a success message
                            JOptionPane.showMessageDialog(CashierDashboard.this, "Sales records added and product table updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Handle sale failure
                            // You can show an error message here
                            JOptionPane.showMessageDialog(CashierDashboard.this, "Failed to add sales records and update product table.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (FileNotFoundException ex) {
                        Logger logger = org.slf4j.LoggerFactory.getLogger(CashierDashboard.class.getName());
                        logger.error("Error occurred: ", ex);
                    } catch (java.io.IOException ex) {
                        Logger logger = org.slf4j.LoggerFactory.getLogger(CashierDashboard.class.getName());
                        logger.error("Error occurred: ", ex);
                    }

                }
            }
        });

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
        invoiceList.setModel(invoiceListModel);
        // Load product data when the form is initialized
        loadProductData();
        loadProductCategoryData();

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateAndTimeLabel();
            }
        });
        timer.start();
        loadUserData();

        productDataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && productDataTable.getSelectedRow() != -1) {
                    int selectedRow = productDataTable.getSelectedRow();

                    // Assuming the columns are in the order: ProductName, ProductID, Category, Eminum, Status, Price
                    String productName = (String) productDataTable.getValueAt(selectedRow, 0);
                    int productId = (int) productDataTable.getValueAt(selectedRow, 1);
                    String category = (String) productDataTable.getValueAt(selectedRow, 2);
                    String eminum = (String) productDataTable.getValueAt(selectedRow, 3);
                    String status = (String) productDataTable.getValueAt(selectedRow, 4);
                    double price = (double) productDataTable.getValueAt(selectedRow, 5);

                    // Create a ProductData instance from the selected data
                    selectedProductData = new ProductData(productName, productId, category, eminum, status, price);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        cashierContainer = new javax.swing.JPanel();
        sideBar = new javax.swing.JPanel();
        lblProfile = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        lblHeader = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        navBar = new javax.swing.JPanel();
        DashboardPane = new javax.swing.JTabbedPane();
        viewProductData = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productDataTable = new javax.swing.JTable();
        downPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        viewProductCategoryTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        invoiceList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        btnAddList = new javax.swing.JButton();
        lblQtyAdd = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        btnDone = new javax.swing.JButton();
        lblPrice = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        txtProductName = new javax.swing.JTextField();
        lblProductName = new javax.swing.JLabel();
        txtEminum = new javax.swing.JTextField();
        lblEminum = new javax.swing.JLabel();
        lblAvailableCategory = new javax.swing.JLabel();
        btnRefreshCtegory = new javax.swing.JButton();
        btnRemoveList = new javax.swing.JButton();
        btnBuy = new javax.swing.JButton();
        btnClearList = new javax.swing.JButton();
        lblTotalBill = new javax.swing.JLabel();
        lblTotalQty = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSerch = new javax.swing.JButton();
        cmbFilterProduct = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        lblWelcom = new javax.swing.JLabel();

        jButton2.setText("jButton2");

        jButton9.setText("jButton9");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CashierDashboard");

        cashierContainer.setBackground(new java.awt.Color(0, 102, 102));

        sideBar.setBackground(new java.awt.Color(0, 0, 0));

        lblProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfile.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adithya\\Downloads\\ezgif.com-resize.gif")); // NOI18N

        btnHome.setBackground(new java.awt.Color(0, 153, 153));
        btnHome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setText("HOME");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(0, 153, 153));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(0, 153, 153));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("LogOut");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lblHeader.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(0, 153, 153));
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("CASHIER");

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(0, 102, 102));
        lblName.setText("Name:");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(0, 102, 102));
        lblEmail.setText("Email");

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(0, 102, 102));
        lblUsername.setText("Username:");

        lblData.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblData.setForeground(new java.awt.Color(0, 102, 102));
        lblData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblData.setText("YOUR DETAILS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout sideBarLayout = new javax.swing.GroupLayout(sideBar);
        sideBar.setLayout(sideBarLayout);
        sideBarLayout.setHorizontalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sideBarLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
        );
        sideBarLayout.setVerticalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sideBarLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHome, btnRefresh});

        navBar.setBackground(new java.awt.Color(0, 0, 0));
        navBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewProductData.setBackground(new java.awt.Color(0, 102, 102));
        viewProductData.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        productDataTable.setBackground(new java.awt.Color(153, 153, 153));
        productDataTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        productDataTable.setForeground(new java.awt.Color(0, 0, 0));
        productDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ProductName", "ProductID", "Category", "Eminum", "Status", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        productDataTable.setGridColor(new java.awt.Color(153, 153, 153));
        productDataTable.setSelectionBackground(new java.awt.Color(0, 0, 0));
        productDataTable.setSelectionForeground(new java.awt.Color(153, 153, 153));
        productDataTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(productDataTable);

        viewProductData.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 220));

        downPanel.setBackground(new java.awt.Color(0, 102, 102));

        viewProductCategoryTable.setBackground(new java.awt.Color(204, 204, 204));
        viewProductCategoryTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewProductCategoryTable.setForeground(new java.awt.Color(0, 0, 0));
        viewProductCategoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ProductID", "Category", "AvailableQTY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        viewProductCategoryTable.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(viewProductCategoryTable);

        invoiceList.setBackground(new java.awt.Color(204, 204, 204));
        invoiceList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        invoiceList.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(invoiceList);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        btnAddList.setBackground(new java.awt.Color(0, 102, 204));
        btnAddList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddList.setForeground(new java.awt.Color(255, 255, 255));
        btnAddList.setText("Add");

        lblQtyAdd.setBackground(new java.awt.Color(255, 255, 255));
        lblQtyAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblQtyAdd.setForeground(new java.awt.Color(255, 255, 255));
        lblQtyAdd.setText("Qty:");

        txtPrice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtPrice.setForeground(new java.awt.Color(0, 0, 0));

        btnDone.setBackground(new java.awt.Color(0, 153, 153));
        btnDone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDone.setForeground(new java.awt.Color(255, 255, 255));
        btnDone.setText("Done");

        lblPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPrice.setText("Price:");

        txtQty.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtQty.setForeground(new java.awt.Color(0, 0, 0));

        txtProductName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtProductName.setForeground(new java.awt.Color(0, 0, 0));

        lblProductName.setBackground(new java.awt.Color(255, 255, 255));
        lblProductName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductName.setForeground(new java.awt.Color(255, 255, 255));
        lblProductName.setText("ProductName");

        txtEminum.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtEminum.setForeground(new java.awt.Color(0, 0, 0));

        lblEminum.setBackground(new java.awt.Color(255, 255, 255));
        lblEminum.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEminum.setForeground(new java.awt.Color(255, 255, 255));
        lblEminum.setText("Eminum");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblEminum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEminum, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblQtyAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPrice)
                        .addGap(12, 12, 12)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(txtPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddList, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddList, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblQtyAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPrice))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrice))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEminum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEminum, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtPrice, txtQty});

        lblAvailableCategory.setBackground(new java.awt.Color(204, 204, 204));
        lblAvailableCategory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAvailableCategory.setForeground(new java.awt.Color(255, 255, 255));
        lblAvailableCategory.setText("Available Categories");

        btnRefreshCtegory.setBackground(new java.awt.Color(0, 102, 204));
        btnRefreshCtegory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefreshCtegory.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshCtegory.setText("Refresh Categoy");
        btnRefreshCtegory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshCtegoryActionPerformed(evt);
            }
        });

        btnRemoveList.setBackground(new java.awt.Color(255, 102, 102));
        btnRemoveList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRemoveList.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveList.setText("Remove");

        btnBuy.setBackground(new java.awt.Color(0, 153, 153));
        btnBuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuy.setForeground(new java.awt.Color(255, 255, 255));
        btnBuy.setText("Buy");

        btnClearList.setBackground(new java.awt.Color(255, 0, 0));
        btnClearList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClearList.setForeground(new java.awt.Color(255, 255, 255));
        btnClearList.setText("Clear");
        btnClearList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearListActionPerformed(evt);
            }
        });

        lblTotalBill.setBackground(new java.awt.Color(0, 0, 0));
        lblTotalBill.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalBill.setForeground(new java.awt.Color(255, 255, 255));

        lblTotalQty.setBackground(new java.awt.Color(0, 0, 0));
        lblTotalQty.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalQty.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout downPanelLayout = new javax.swing.GroupLayout(downPanel);
        downPanel.setLayout(downPanelLayout);
        downPanelLayout.setHorizontalGroup(
            downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(downPanelLayout.createSequentialGroup()
                        .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblAvailableCategory)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnRefreshCtegory, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(downPanelLayout.createSequentialGroup()
                                .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(downPanelLayout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(lblTotalQty, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblTotalBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnClearList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRemoveList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(btnBuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        downPanelLayout.setVerticalGroup(
            downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, downPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(downPanelLayout.createSequentialGroup()
                        .addComponent(lblAvailableCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshCtegory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(downPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRemoveList)
                            .addComponent(lblTotalBill))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(downPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnClearList)
                            .addComponent(lblTotalQty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );

        viewProductData.add(downPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 750, 310));

        DashboardPane.addTab("", viewProductData);

        navBar.add(DashboardPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 760, 580));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("Search Product:");
        navBar.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, 25));

        txtSearch.setBackground(new java.awt.Color(102, 102, 102));
        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(0, 153, 204));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        navBar.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 200, 25));

        btnSerch.setBackground(new java.awt.Color(0, 0, 0));
        btnSerch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSerch.setForeground(new java.awt.Color(0, 153, 204));
        btnSerch.setText("Search");
        btnSerch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSerchActionPerformed(evt);
            }
        });
        navBar.add(btnSerch, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 106, 25));

        cmbFilterProduct.setBackground(new java.awt.Color(102, 102, 102));
        cmbFilterProduct.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cmbFilterProduct.setForeground(new java.awt.Color(0, 153, 255));
        cmbFilterProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filter Produc By Sold", "Filter Produc By Unsold", "Filter All Products" }));
        navBar.add(cmbFilterProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 200, 23));

        btnFilter.setBackground(new java.awt.Color(0, 0, 0));
        btnFilter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFilter.setForeground(new java.awt.Color(0, 153, 204));
        btnFilter.setText("Filter");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        navBar.add(btnFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, 106, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adithya\\Downloads\\list.png")); // NOI18N
        jLabel4.setMaximumSize(new java.awt.Dimension(64, 6));
        navBar.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 63));

        lblDateTime.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblDateTime.setForeground(new java.awt.Color(255, 153, 0));
        lblDateTime.setText("Date:");
        navBar.add(lblDateTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 290, 25));

        lblWelcom.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblWelcom.setForeground(new java.awt.Color(0, 153, 153));
        lblWelcom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcom.setText("WELCOME CASHIER DASHBOARD");
        navBar.add(lblWelcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 310, -1));

        javax.swing.GroupLayout cashierContainerLayout = new javax.swing.GroupLayout(cashierContainer);
        cashierContainer.setLayout(cashierContainerLayout);
        cashierContainerLayout.setHorizontalGroup(
            cashierContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cashierContainerLayout.createSequentialGroup()
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cashierContainerLayout.setVerticalGroup(
            cashierContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cashierContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cashierContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cashierContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cashierContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnSerchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSerchActionPerformed

    }//GEN-LAST:event_btnSerchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadProductData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnRefreshCtegoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshCtegoryActionPerformed
        loadProductCategoryData();
    }//GEN-LAST:event_btnRefreshCtegoryActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        DashboardPane.setSelectedIndex(0);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnClearListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearListActionPerformed
        loadProductData(); // Update the product table
        invoiceListModel.clear(); // Clear the invoice list
        lblTotalBill.setText(" ");
        lblTotalQty.setText(" ");
    }//GEN-LAST:event_btnClearListActionPerformed

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
    private void loadProductData() {

        List<ProductData> products = cashier.viewAllProductData(); // Retrieve product data

        // Clear existing data from the table
        productDataTableModel.setRowCount(0);

        for (ProductData product : products) {
            // Add the product data to the table
            productDataTableModel.addRow(new Object[]{
                product.getProductName(),
                product.getProductId(),
                product.getCategory(),
                product.getEminum(),
                product.getStatus(),
                product.getPrice()
            });
        }
    }

    private void loadProductCategoryData() {

        List<Product> productCategories = cashier.viewAllCategoryData(); // Retrieve product data

        // Clear existing data from the table
        productTableModel.setRowCount(0);

        for (Product product : productCategories) {
            // Add the product data to the table
            productTableModel.addRow(new Object[]{
                product.getProductId(),
                product.getCategory(),
                product.getStockQuantity()
            });
        }
    }

    private void loadUserData() {
        lblName.setText("Name: " + cashier.getName());
        lblUsername.setText("Username " + cashier.getUsername());
        lblEmail.setText("Email: " + cashier.getEmail());
    }

    private void updateDateAndTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define your desired date and time format
        String formattedDate = sdf.format(new Date()); // Get the current date and time

        lblDateTime.setText("Date and Time: " + formattedDate); // Update the label text
    }

    private double calculatePrice() {
        return selectedProductData.getPrice();
    }

    public String generateInvoiceNumber() {
        // Format the current date and time to include in the invoice number
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(new Date());

        // Generate a random number (you can replace this with a more sophisticated logic)
        int randomNumber = (int) (Math.random() * 1000);

        // Combine the formatted date and random number to create the invoice number
        String invoiceNumber = "INV" + formattedDate + String.format("%03d", randomNumber);

        // Take only the last 5 characters to ensure the total length is 5
        invoiceNumber = invoiceNumber.substring(Math.max(0, invoiceNumber.length() - 5));

        return invoiceNumber;
    }

    private int extractQuantityFromProductInfo(String productInfo) {
        int quantity = 0;
        String[] infoParts = productInfo.split("\\|");
        for (String part : infoParts) {
            if (part.contains("Quantity:")) {
                quantity = Integer.parseInt(part.replace("Quantity:", "").trim());
                break;
            }
        }
        return quantity;
    }

    private int getpId(String name) {

        return cashier.getProductId(name);
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
            java.util.logging.Logger.getLogger(CashierDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CashierDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CashierDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CashierDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane DashboardPane;
    private javax.swing.JButton btnAddList;
    private javax.swing.JButton btnBuy;
    private javax.swing.JButton btnClearList;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRefreshCtegory;
    private javax.swing.JButton btnRemoveList;
    private javax.swing.JButton btnSerch;
    private javax.swing.JPanel cashierContainer;
    private javax.swing.JComboBox<String> cmbFilterProduct;
    private javax.swing.JPanel downPanel;
    private javax.swing.JList<String> invoiceList;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAvailableCategory;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEminum;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblQtyAdd;
    private javax.swing.JLabel lblTotalBill;
    private javax.swing.JLabel lblTotalQty;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblWelcom;
    private javax.swing.JPanel navBar;
    private javax.swing.JTable productDataTable;
    private javax.swing.JPanel sideBar;
    private javax.swing.JTextField txtEminum;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTable viewProductCategoryTable;
    private javax.swing.JPanel viewProductData;
    // End of variables declaration//GEN-END:variables
}
