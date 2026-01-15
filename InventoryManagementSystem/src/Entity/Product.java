package Entity;

public class Product {
    private String productId;
    private String productName;
    private double productPrice;
    private int productQuantity;

    // Creating Constructor
    public Product(String productId, String productName, double productPrice, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    //  Getter Methods
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    // Setter Methods
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    // Convert product to CSV format
    public String toCSV() {
        return productId + "," + productName + "," + productPrice + "," + productQuantity;
    }

    // Create product from CSV line
    public static Product fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length == 4) {
            return new Product(parts[0], parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]));
        }
        return null;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + productName + 
               ", Price: " + productPrice +" BDT," +"Quantity: " + productQuantity;
    }
}