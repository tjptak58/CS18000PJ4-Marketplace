/**
 * A product class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Product {
    /*
     * Name of the product
     */
    private String productName;

    /*
     * Name of the store associated with it
     */
    private String storeName;

    /*
     * Description of the product
     */
    private String description;

    /*
     * Quantity of product left in stock
     */
    private int quantity;

    /*
     * Price of the product
     */
    private double price;

    public Product(String productName , String storeName , String description 
    , int quantity , double price) {
        this.productName = productName;
        this.storeName = storeName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Product() {
        productName = null;
        storeName = null;
        description = null;
        quantity = 0;
        price = 0.0;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}