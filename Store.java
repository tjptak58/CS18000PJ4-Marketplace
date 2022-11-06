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
    private ArrayList<String> saleHistory;

    public Store(String storeName) {
        this.storeName = storeName;
        products = new ArrayList<Product>();
        saleHistory = new ArrayList<String>();
    }

    public Store(String storeName, ArrayList<Product> products , ArrayList<String> saleHistory) {
        this.storeName = storeName;
        this.products = products;
        this.saleHistory = saleHistory;
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

    public ArrayList<String> getSaleHistory() {
        return saleHistory;
    }

    public void setSaleHistory(ArrayList<String> saleHistory) {
        this.saleHistory = saleHistory;
    }
}