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

    public Seller(String username , String password) {
        super(username , password);
        stores = new ArrayList<Store>();
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }
}