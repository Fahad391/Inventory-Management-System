package FileIO;

import Entity.Product;
import java.io.*;
import java.util.*;

public class FileIO {
    private static final String FILE_PATH = "E:\\InventoryManagementSystem\\src\\Data\\products.txt";

    public static void initializeFile() {
        File directory = new File("E:\\InventoryManagementSystem\\src\\Data");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
    }

    public static boolean saveProduct(Product product) {
        initializeFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(product.toCSV());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error saving product: " + e.getMessage());
            return false;
        }
    }

    public static List<Product> readAllProducts() {
        List<Product> products = new ArrayList<>();
        initializeFile();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Product product = Product.fromCSV(line);
                    if (product != null) {
                        products.add(product);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading products: " + e.getMessage());
        }

        return products;
    }

    public static List<Product> searchProducts(String searchTerm) {
        List<Product> results = new ArrayList<>();
        List<Product> allProducts = readAllProducts();
        String lowerSearchTerm = searchTerm.toLowerCase();

        for (Product product : allProducts) {
            if (product.getProductId().toLowerCase().contains(lowerSearchTerm) ||
                product.getProductName().toLowerCase().contains(lowerSearchTerm)) {
                results.add(product);
            }
        }

        return results;
    }

    public static boolean updateProduct(String searchId, String searchName, Product updatedProduct) {
        List<Product> products = readAllProducts();
        boolean found = false;

        for (int i = 0; i < products.size(); i++) {
  
            if (products.get(i).getProductId().equals(searchId) || 
                products.get(i).getProductName().equalsIgnoreCase(searchName)) {
                products.set(i, updatedProduct);
                found = true;
                break;
            }
        }

        if (found) {
            return saveAllProducts(products);
        }
        return false;
    }
    public static boolean deleteProduct(String productId) {
        List<Product> products = readAllProducts();
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));

        if (removed) {
            return saveAllProducts(products);
        }
        return false;
    }

    private static boolean saveAllProducts(List<Product> products) {
        initializeFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                writer.write(product.toCSV());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
            return false;
        }
    }
    public static boolean productIdExists(String productId) {
        List<Product> products = readAllProducts();
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }
}