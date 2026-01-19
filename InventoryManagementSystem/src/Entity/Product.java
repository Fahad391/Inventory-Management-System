package Entity;

public class Product {
    private String productId;
    private String productName;
    private double productPrice;
    private int productQuantity;

    public Product(String productId, String productName, double productPrice, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    
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

  
    public String toCSV() {
        return productId + "," + productName + "," + productPrice + "," + productQuantity;
    }

  
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