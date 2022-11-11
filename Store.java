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


    public Store(String sellerName , String storeName , String filePath) {
        this.sellerName = sellerName;
        this.storeName = storeName;
        this.filePath = filePath;
        products = new ArrayList<Product>();

    }

    public Store(String sellerName, String storeName , String filePath, ArrayList<Product> products) {
        this.sellerName = sellerName;
        this.storeName = storeName;
        this.filePath = filePath;
        this.products = products;

    }

    public Store() {
        sellerName = null;
        storeName = null;
        filePath = null;
        products = null;
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


}