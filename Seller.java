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

    public Seller(String username , String password, String email, String filePath) {
        super(username , password, email, filePath);
        stores = new ArrayList<Store>();
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void createStore(String sellerName , String storeName) {
        Store store = new Store(sellerName , storeName);
        stores.add(store);
    }

    public void createProduct(String productName , String sellerName, String storeName , String description 
    , int quantity , double price, int unitsPurchased, ArrayList<String> customerList) {
        if (stores.size() == 0) {
            createStore(sellerName , storeName);
            Store s = stores.get(0);
            Product p = new Product(productName, storeName, description, quantity, price , unitsPurchased , customerList);
            s.addProduct(p);
        } else {
            int count = 0; 
            for (Store s : stores) {
                if (storeName.equals(s.getStoreName())) {
                    break;
                }
                count++;
            }
            Product p = new Product(productName, storeName, description, quantity, price , unitsPurchased , customerList);
            stores.get(count).addProduct(p);
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