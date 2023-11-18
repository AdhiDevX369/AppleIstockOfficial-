package View;

// Models for product and user data
import Models.Product;
import Models.User;

// Event handling and listeners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Controller for the Cashier application
import Controller.CashierController;

// Date and time formatting
import java.text.SimpleDateFormat;
import java.util.Date;

// Collections for managing data
import java.util.List;

// DefaultListModel for maintaining the invoice list
import javax.swing.DefaultListModel;

// User interface components
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

// iText PDF library for PDF generation
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

// Handling file exceptions
import java.io.FileNotFoundException;

// Logging and error handling
import java.util.logging.Level;
import java.util.logging.Logger;

public class CashierForm extends javax.swing.JFrame {

    private DefaultTableModel productTableModel;
    private Product selectedProduct;
    private double totalBill;
    private int totalQuantity;
    private DefaultListModel<String> invoiceListModel;
    private CashierController cashier;

    public CashierForm(User user) {
        initComponents();
        invoiceListModel = new DefaultListModel<>();
        cashier = new CashierController(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getEmail(), user.getPhone(), user.getNic());
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle refresh logic
                loadProductData();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle search logic
                String searchKeyword = txtSearch.getText();

                // Perform the product search
                List<Product> searchResults = cashier.searchProduct(searchKeyword);

                // Clear existing data from the table
                productTableModel.setRowCount(0);

                // Add the search results to the product table
                for (Product product : searchResults) {
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

        btnAddProductList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct != null) {
                    // Set the text fields in the invoicePanel with the selected product's information
                    txtProductName.setText(selectedProduct.getName());
                    txtPrice.setText(Double.toString(selectedProduct.getPrice()));
                    txtQty.setText("1"); // You can set the initial quantity as needed

                    // Update the total bill and quantity displayed on the form
                    lblBill.setText("Total Bill: $" + totalBill);
                    lblQty.setText("Total Quantity: " + totalQuantity);
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
                if (productName.isEmpty() || priceText.isEmpty() || qtyText.isEmpty()) {
                    // Display a message to the user that some fields are empty
                    JOptionPane.showMessageDialog(CashierForm.this, "First Select Products.", "Missing Products", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the actionPerformed method
                }

                double price;
                int quantity;

                try {
                    price = Double.parseDouble(priceText);
                    quantity = Integer.parseInt(qtyText);
                } catch (NumberFormatException ex) {
                    // Display a message to the user that price and quantity must be valid numbers
                    JOptionPane.showMessageDialog(CashierForm.this, "Please enter valid numbers for Price and Quantity.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the actionPerformed method
                }

                if (quantity <= 0) {
                    // Display a message to the user that quantity must be greater than 0
                    JOptionPane.showMessageDialog(CashierForm.this, "Product Is Out Of Stock", "Out Of Stock", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the actionPerformed method
                }

                // Calculate the total bill and quantity
                double productPrice = quantity * price;
                totalBill += calculatePrice(quantity);
                totalQuantity += quantity;

                // Add the product information to the invoice list
                String productInfo = "Product Name: " + productName + " | "
                        + "Price: $" + price + " | "
                        + "Quantity: " + quantity + " | "
                        + "Full Price: " + productPrice;
                updateProductQuantity();
                invoiceListModel.addElement(productInfo); // Add to the model

                // Update the total bill and quantity displayed on the form
                lblBill.setText("Total Bill: $" + totalBill);
                lblQty.setText("Total Quantity: " + totalQuantity);

                // Optionally, you can clear the text fields
                txtProductName.setText("");
                txtPrice.setText("");
                txtQty.setText("");
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
                    String productName = extractProductNameFromProductInfo(removedItem);

                    // Update the total bill and quantity
                    totalQuantity -= quantityRemoved;
                    totalBill -= calculatePrice(quantityRemoved);

                    // Update the table with the new quantity
                    updateTableWithNewQuantity(productName, quantityRemoved);

                    // Remove the selected item from the list
                    invoiceListModel.remove(selectedIndex);

                    // Update the displayed total bill and quantity
                    lblBill.setText("Total Bill: $" + totalBill);
                    lblQty.setText("Total Quantity: " + totalQuantity);
                }
            }
        });
        btnPrintInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (invoiceListModel.isEmpty()) {
                    // Show a message using JOptionPane
                    JOptionPane.showMessageDialog(CashierForm.this, "The invoice list is empty. Please add products before printing the invoice.", "Empty Invoice List", JOptionPane.WARNING_MESSAGE);
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
                        Table productTable = new Table(UnitValue.createPercentArray(new float[]{40, 20, 20, 20}));
                        productTable.setWidth(UnitValue.createPercentValue(100));
                        // Add table headers
                        productTable.addHeaderCell("Product Name");
                        productTable.addHeaderCell("Quantity");
                        productTable.addHeaderCell("Price per Unit");
                        productTable.addHeaderCell("Total Price");
                        String invoice_number = generateInvoiceNumber();
                        for (int i = 0; i < invoiceListModel.size(); i++) {
                            String productInfo = invoiceListModel.get(i);
                            String[] productData = productInfo.split("\\|");
                            String productName = productData[0].trim().split(":")[1].trim();
                            double price = Double.parseDouble(productData[1].trim().split(":")[1].trim().replace("$", ""));
                            int quantity = Integer.parseInt(productData[2].trim().split(":")[1].trim());

                            double productPrice = price * quantity;
                            // Get the product ID for the sold product (You may need to fetch it from the database)
                            int productId = getProductIdByName(productName);

                            // Add product details to the table
                            productTable.addCell(productName);
                            productTable.addCell(String.valueOf(quantity));
                            productTable.addCell("$" + price);
                            productTable.addCell("$" + productPrice);

                            // Sell the product and update its quantity
                            if (!cashier.sellProduct(productId, user.getUserId(), user.getNic(), quantity, productPrice, invoice_number)) {
                                allSalesSuccessful = false;
                                break; // Stop selling products and break the loop if one sale fails
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
                            lblBill.setText(" ");
                            lblQty.setText(" ");

                            // Show a success message
                            JOptionPane.showMessageDialog(CashierForm.this, "Sales records added and product table updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Handle sale failure
                            // You can show an error message here
                            JOptionPane.showMessageDialog(CashierForm.this, "Failed to add sales records and update product table.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(CashierForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (java.io.IOException ex) {
                        Logger.getLogger(CashierForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateAndTimeLabel();
            }
        });
        timer.start();
        // Initialize your product table model
        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Product ID");
        productTableModel.addColumn("Product Name");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Qty");
        productTableModel.addColumn("Category");

        // Set the table model for your product table
        viewProductTable.setModel(productTableModel);
        invoiceList.setModel(invoiceListModel);
        // Load product data when the form is initialized
        loadProductData();

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle search logic
                String searchKeyword = txtSearch.getText();

                List<Product> searchResults = cashier.searchProduct(searchKeyword);

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
        viewProductTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
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
        });

    }

public static String generateInvoiceNumber() {
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



    private void updateProductQuantity() {
        if (selectedProduct != null) {
            int currentRow = -1;

            // Calculate the new quantity based on your specific operation
            int newQuantity = selectedProduct.getStockQuantity() - Integer.parseInt(txtQty.getText());; // Your calculation here

            // Find the row of the selected product in the table
            for (int row = 0; row < productTableModel.getRowCount(); row++) {
                int productId = (int) productTableModel.getValueAt(row, 0);
                if (productId == selectedProduct.getProductId()) {
                    currentRow = row;
                    break;
                }
            }

            if (currentRow != -1) {
                // Update the table with the new quantity (as an integer value)
                productTableModel.setValueAt(newQuantity, currentRow, 3); // Replace ... with the correct column index
            }
        }
    }

    private void loadProductData() {

        List<Product> products = cashier.viewProductDetails(); // Retrieve product data

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

    private String extractProductNameFromProductInfo(String productInfo) {
        String productName = "";
        String[] infoParts = productInfo.split("\\|");
        for (String part : infoParts) {
            if (part.contains("Product Name:")) {
                productName = part.replace("Product Name:", "").trim();
                break;
            }
        }
        return productName;
    }

// Helper method to update the table with the new quantity
    private void updateTableWithNewQuantity(String productName, int quantityRemoved) {
        for (int row = 0; row < productTableModel.getRowCount(); row++) {
            String productInTable = (String) productTableModel.getValueAt(row, 1); // Assuming the second column contains product name
            if (productInTable.equals(productName)) {
                int currentQuantity = (int) productTableModel.getValueAt(row, 3); // Assuming the fourth column contains quantity
                int newQuantity = currentQuantity + quantityRemoved;
                productTableModel.setValueAt(newQuantity, row, 3); // Update the quantity in the table
                break;
            }
        }
    }

    private double calculatePrice(int quantity) {
        return quantity * selectedProduct.getPrice();
    }

    private int getProductIdByName(String productName) {
        // You need to implement a method to retrieve the product ID by product name
        // This method should query the database or use any other appropriate method
        // Here, I'm assuming you have a method named getProductIdByName in your CashierController class
        // that returns the product ID based on the product name.

        int productId = cashier.getProductIdByName(productName);
        return productId;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cashierDPanel = new javax.swing.JPanel();
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
        printInvoicePanel = new javax.swing.JPanel();
        btnPrintInvoice = new javax.swing.JButton();
        btnAddProductList = new javax.swing.JButton();
        btnRemoveList = new javax.swing.JButton();
        lblPrintInvoice = new javax.swing.JLabel();
        invoicePanel = new javax.swing.JPanel();
        txtProductName = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        btnDone = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        invoiceList = new javax.swing.JList<>();
        lblBill = new javax.swing.JLabel();
        lblQty = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cashier Dashboard");

        cashierDPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
        jLabel3.setText("Cashier Dashboard");

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
                .addContainerGap(23, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
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

        printInvoicePanel.setBackground(new java.awt.Color(0, 102, 204));

        btnPrintInvoice.setBackground(new java.awt.Color(0, 0, 0));
        btnPrintInvoice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPrintInvoice.setForeground(new java.awt.Color(0, 102, 204));
        btnPrintInvoice.setText("Confirm & Print");

        btnAddProductList.setBackground(new java.awt.Color(0, 0, 0));
        btnAddProductList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddProductList.setForeground(new java.awt.Color(0, 102, 204));
        btnAddProductList.setText("ADD TO LIST");

        btnRemoveList.setBackground(new java.awt.Color(0, 0, 0));
        btnRemoveList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRemoveList.setForeground(new java.awt.Color(0, 102, 204));
        btnRemoveList.setText("Remove");

        lblPrintInvoice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPrintInvoice.setText("Print Invoice");

        invoicePanel.setBackground(new java.awt.Color(0, 102, 204));
        invoicePanel.setForeground(new java.awt.Color(255, 255, 255));

        txtProductName.setBackground(new java.awt.Color(255, 255, 255));
        txtProductName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtProductName.setForeground(new java.awt.Color(0, 102, 204));
        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        txtPrice.setBackground(new java.awt.Color(255, 255, 255));
        txtPrice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtPrice.setForeground(new java.awt.Color(0, 102, 204));
        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        txtQty.setBackground(new java.awt.Color(255, 255, 255));
        txtQty.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtQty.setForeground(new java.awt.Color(0, 102, 204));
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        btnDone.setBackground(new java.awt.Color(0, 204, 51));
        btnDone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDone.setForeground(new java.awt.Color(0, 0, 0));
        btnDone.setText("Done");

        btnCancel.setBackground(new java.awt.Color(255, 102, 102));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(0, 0, 0));
        btnCancel.setText("Cancel");

        invoiceList.setBackground(new java.awt.Color(0, 102, 204));
        invoiceList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        invoiceList.setForeground(new java.awt.Color(255, 255, 255));
        invoiceList.setToolTipText("");
        jScrollPane3.setViewportView(invoiceList);

        javax.swing.GroupLayout invoicePanelLayout = new javax.swing.GroupLayout(invoicePanel);
        invoicePanel.setLayout(invoicePanelLayout);
        invoicePanelLayout.setHorizontalGroup(
            invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invoicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(invoicePanelLayout.createSequentialGroup()
                        .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                .addContainerGap())
        );
        invoicePanelLayout.setVerticalGroup(
            invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invoicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDone)
                    .addComponent(btnCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
        );

        lblBill.setBackground(new java.awt.Color(0, 102, 204));
        lblBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBill.setForeground(new java.awt.Color(255, 255, 255));

        lblQty.setBackground(new java.awt.Color(0, 102, 204));
        lblQty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblQty.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout printInvoicePanelLayout = new javax.swing.GroupLayout(printInvoicePanel);
        printInvoicePanel.setLayout(printInvoicePanelLayout);
        printInvoicePanelLayout.setHorizontalGroup(
            printInvoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printInvoicePanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(printInvoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(printInvoicePanelLayout.createSequentialGroup()
                        .addComponent(invoicePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(printInvoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRemoveList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddProductList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrintInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                    .addGroup(printInvoicePanelLayout.createSequentialGroup()
                        .addComponent(lblPrintInvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblBill, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblQty, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        printInvoicePanelLayout.setVerticalGroup(
            printInvoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printInvoicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(printInvoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrintInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBill)
                    .addComponent(lblQty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(printInvoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(printInvoicePanelLayout.createSequentialGroup()
                        .addComponent(btnAddProductList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrintInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(printInvoicePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(invoicePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout cashierDPanelLayout = new javax.swing.GroupLayout(cashierDPanel);
        cashierDPanel.setLayout(cashierDPanelLayout);
        cashierDPanelLayout.setHorizontalGroup(
            cashierDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cashierDPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cashierDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(SearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(printInvoicePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        cashierDPanelLayout.setVerticalGroup(
            cashierDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(cashierDPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(printInvoicePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cashierDPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cashierDPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadProductData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        LoginForm login = new LoginForm();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JButton btnAddProductList;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrintInvoice;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveList;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel cashierDPanel;
    private javax.swing.JList<String> invoiceList;
    private javax.swing.JPanel invoicePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBill;
    private javax.swing.JLabel lblDateAndTime;
    private javax.swing.JLabel lblPrintInvoice;
    private javax.swing.JLabel lblQty;
    private javax.swing.JPanel printInvoicePanel;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTable viewProductTable;
    // End of variables declaration//GEN-END:variables
}
