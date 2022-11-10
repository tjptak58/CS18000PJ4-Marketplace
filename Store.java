import java.util.ArrayList;
/**
 * A store class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Store {
    /*
     * the name of the store
     */
    private String storeName;

    /*
     * A list of products associated with the store
     */
    private ArrayList<Product> products;

    /*
     * A sale history of the store including
     * customer information and revenue
     */


    public Store(String storeName) {
        this.storeName = storeName;
        products = new ArrayList<Product>();
    }

    public Store(String storeName, ArrayList<Product> products , ArrayList<String> saleHistory) {
        this.storeName = storeName;
        this.products = products;
    }

    public Store() {
        storeName = null;
        products = null;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    /*
     * Changes the quanity of a product int the store
     */
    public void sale(String productName , int quantity) {
        for (Product product : products) {
            if (product.getProductName().equals(productName)) {
                product.setQuantity(product.getQuantity() - quantity);
            }
        }
    }
}