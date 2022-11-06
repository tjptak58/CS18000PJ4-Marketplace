import java.util.ArrayList;
/**
 * A buyer class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Buyer extends Person{
    /*
     * List of the products a person has purchased
     */
    ArrayList<Product> purchased;

    /*
     * List of products in a person's shopping cart
     */
    ArrayList<Product> shoppingCart;

    public Buyer() {
        purchased = new ArrayList<Product>();
        shoppingCart = new ArrayList<Product>();
    }
    
    public Buyer(String username , String password) {
        super(username, password);
        purchased = new ArrayList<Product>();
        shoppingCart = new ArrayList<Product>();
    }

    public ArrayList<Product> getPurchased() {
        return purchased;
    }

    public void setPurchased(ArrayList<Product> purchased) {
        this.purchased = purchased;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}