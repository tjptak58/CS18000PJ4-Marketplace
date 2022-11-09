import java.util.ArrayList;
/**
 * A seller class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Seller extends Person{
    /*
     * List of stores
     */
    private ArrayList<Store> stores;

    public Seller(String username , String password, String filePath) {
        super(username , password, filePath);
        stores = new ArrayList<Store>();
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void createStore(String storeName) {
        Store store = new Store(storeName);
        stores.add(store);
    }

    public void createProduct(String productName , String storeName , String description 
    , int quantity , double price) {
        if (stores.size() == 0) {
            createStore(storeName);
            Store s = stores.get(0);
            Product p = new Product(productName, storeName, description, quantity, price);
            s.addProduct(p);
        } else { 
            Store s = stores.get(0);
            Product p = new Product(productName, storeName, description, quantity, price);
            s.addProduct(p);
        }
    }

    public void editProduct(Product p, String productName , String storeName , String description 
    , int quantity , double price) {
        if (! (productName == null)){
            p.setProductName(productName);
        }
        if (! (storeName == null)){
            p.setStoreName(storeName);
        }
        if (! (description == null)){
            p.setDescription(description);
        }
        if (! (quantity == -1)){
            p.setQuantity(quantity);
        } 
        if (! (price == -1.0)) {
            p.setPrice(price);
        }
    }
}