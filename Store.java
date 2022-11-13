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
    private String sellerName;

    /*
     * Name of the store
     * Must be a unique identifier
     */
    private String storeName;

    /*
     * Filepath to store file
     */
    private String filePath;

    /*
     * A list of products associated with the store
     */
    private ArrayList<Product> products;

    /*
     * A sale history of the store including
     * customer information and revenue
     */
    private double storeRevenue;


    public Store(String sellerName , String storeName , String filePath) {
        this.sellerName = sellerName;
        this.storeName = storeName;
        this.filePath = filePath;
        products = new ArrayList<Product>();
        storeRevenue = 0;

    }

    public Store(String sellerName, String storeName , String filePath, ArrayList<Product> products, double storeRevenue) {
        this.sellerName = sellerName;
        this.storeName = storeName;
        this.filePath = filePath;
        this.products = products;
        this.storeRevenue = storeRevenue;

    }

    public Store() {
        sellerName = null;
        storeName = null;
        filePath = null;
        products = null;
        storeRevenue = 0;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
    
    public void removeProduct(Product p) {
        products.remove(p);
    }

    public double getStoreRevenue() {
        return storeRevenue;
    }

    public void setStoreRevenue(double storeRevenue) {
        this.storeRevenue = storeRevenue;
    }

    //Create a getNumberOfSales method that sums up number of units sold for each product in that store


}
