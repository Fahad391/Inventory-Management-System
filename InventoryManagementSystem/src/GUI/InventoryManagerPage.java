package GUI;

import Entity.Product;
import FileIO.FileIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryManagerPage extends JFrame {
    private JTextField idField, nameField, priceField, quantityField, searchField;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton addBtn, updateBtn, deleteBtn;

    public InventoryManagerPage() {
        setTitle("Inventory Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // Set background color
        getContentPane().setBackground(new Color(100, 130, 145));

        // Add components
        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel(), BorderLayout.CENTER);

        loadAllProducts();
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(100, 130, 145));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 10, 15));
        inputPanel.setBackground(new Color(100, 130, 145));
        inputPanel.setMaximumSize(new Dimension(250, 220));

        // ID Field
        JPanel idPanel = new JPanel(new BorderLayout(5, 5));
        idPanel.setBackground(new Color(100, 130, 145));
        JLabel idLabel = new JLabel("Id:");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 30));
        idPanel.add(idLabel, BorderLayout.NORTH);
        idPanel.add(idField, BorderLayout.CENTER);

        // Product Name Field
        JPanel namePanel = new JPanel(new BorderLayout(5, 5));
        namePanel.setBackground(new Color(100, 130, 145));
        JLabel nameLabel = new JLabel("Product:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameField, BorderLayout.CENTER);

        // Price Field
        JPanel pricePanel = new JPanel(new BorderLayout(5, 5));
        pricePanel.setBackground(new Color(100, 130, 145));
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(200, 30));
        pricePanel.add(priceLabel, BorderLayout.NORTH);
        pricePanel.add(priceField, BorderLayout.CENTER);

        // Quantity Field
        JPanel qtyPanel = new JPanel(new BorderLayout(5, 5));
        qtyPanel.setBackground(new Color(100, 130, 145));
        JLabel qtyLabel = new JLabel("Qty:");
        qtyLabel.setForeground(Color.WHITE);
        qtyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(200, 30));
        qtyPanel.add(qtyLabel, BorderLayout.NORTH);
        qtyPanel.add(quantityField, BorderLayout.CENTER);

        inputPanel.add(idPanel);
        inputPanel.add(namePanel);
        inputPanel.add(pricePanel);
        inputPanel.add(qtyPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        buttonPanel.setBackground(new Color(100, 130, 145));
        buttonPanel.setMaximumSize(new Dimension(250, 70));

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");

        // Style buttons
        styleButton(addBtn);
        styleButton(updateBtn);
        styleButton(deleteBtn);

        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        leftPanel.add(inputPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createVerticalGlue());

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(new Color(100, 130, 145));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setBackground(new Color(100, 130, 145));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        
        // Add key listener for real-time search
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchProducts();
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // Table panel
        String[] columns = {"id", "product_name", "price", "stock"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(25);
        productTable.setFont(new Font("Arial", Font.PLAIN, 12));
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        productTable.getTableHeader().setBackground(new Color(80, 110, 125));
        productTable.getTableHeader().setForeground(Color.WHITE);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    priceField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    quantityField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBackground(Color.WHITE);

        rightPanel.add(searchPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        return rightPanel;
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(70, 30));
        button.setBackground(new Color(220, 220, 220));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void addProduct() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (FileIO.productIdExists(id)) {
                JOptionPane.showMessageDialog(this, "Product ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            if (price < 0 || quantity < 0) {
                JOptionPane.showMessageDialog(this, "Price and Quantity must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = new Product(id, name, price, quantity);
            if (FileIO.saveProduct(product)) {
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                clearFields();
                loadAllProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price or quantity format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            if (price < 0 || quantity < 0) {
                JOptionPane.showMessageDialog(this, "Price and Quantity must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = new Product(id, name, price, quantity);
            if (FileIO.updateProduct(id, product)) {
                JOptionPane.showMessageDialog(this, "Product updated successfully!");
                clearFields();
                loadAllProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price or quantity format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Product ID to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this product?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (FileIO.deleteProduct(id)) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                clearFields();
                loadAllProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchProducts() {
        String searchTerm = searchField.getText().trim();
        
        if (searchTerm.isEmpty()) {
            loadAllProducts();
            return;
        }

        List<Product> results = FileIO.searchProducts(searchTerm);
        displayProducts(results);
    }

    private void loadAllProducts() {
        List<Product> products = FileIO.readAllProducts();
        displayProducts(products);
    }

    private void displayProducts(List<Product> products) {
        tableModel.setRowCount(0);
        for (Product product : products) {
            Object[] row = {
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity()
            };
            tableModel.addRow(row);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        searchField.setText("");
        productTable.clearSelection();
    }
}