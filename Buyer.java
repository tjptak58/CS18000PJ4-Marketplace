import java.util.ArrayList;
import java.io.*;
/**
 * A buyer class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Buyer extends Person{

    public Buyer() {
    }
    
    public Buyer(String username , String password, String filePath) {
        super(username, password, filePath);
    }

    /*
     * Gets all product in users.txt that have a name, description, or store that matches the search parameter
     */
    public ArrayList<Product> searchProducts(String searchParameter , ArrayList<Product> market) {
        ArrayList<Product> products = new ArrayList<Product>();
        for (Product p : market) {
            if (p.getProductName().contains(searchParameter)) {
                products.add(p);
            } else if (p.getDescription().contains(searchParameter)) {
                products.add(p);
            } if (p.getStoreName().contains(searchParameter)) {
                products.add(p);
            } 
        }
        return products;
    }

    /*
     * Sorts an Arraylist of products based on price
     * Sorts highest price to lowest if highToLow = true    NOT SURE IF I EVEN NEED THIS
     * if it's false, sorts lowest to highest
     */
    public ArrayList<Product> sortPrice(ArrayList<Product> products , boolean highToLow) {
        ArrayList<Product> output = new ArrayList<Product>();
        ArrayList<Product> p = products;
        while (p.size() > 0) {
            double max = 0;
            int index = 0;
            for (int i = 0 ; i < p.size() ; i++) {
                double price = p.get(i).getPrice();
                if (price >= max) {
                    max = price;
                    index = i;
                }
            }
            output.add(p.get(index));
            p.remove(index);
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<Product> reversed = new ArrayList<Product>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    /*
     * Sorts an Arraylist of products based on quantity
     * Sorts highest price to lowest if highToLow = true
     * if it's false, sorts lowest to highest
     */
    public ArrayList<Product> sortQuantity(ArrayList<Product> products , boolean highToLow) {
        ArrayList<Product> output = new ArrayList<Product>();
        ArrayList<Product> p = products;
        while (p.size() > 0) {
            int max = 0;
            int index = 0;
            for (int i = 0 ; i < p.size() ; i++) {
                int quantity = p.get(i).getQuantity();
                if (quantity >= max) {
                    max = quantity;
                    index = i;
                }
            }
            output.add(p.get(index));
            p.remove(index);
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<Product> reversed = new ArrayList<Product>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    /*
     * Adds a product to a user's shopping cart in the 
     * format (ProductName,StoreName)
     */
    public void addToCart(Product p) {
        try {
            FileWriter fw = new FileWriter(this.getFilePath(), true); //Local File
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(p.getProductName() + "," + p.getStoreName());
            pw.flush(); //Do I need both of these?
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Clears the shopping cart text file
     */
    public void clearCart() {
        try {
            FileWriter fw = new FileWriter(this.getFilePath() , false); //Local File
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("");
            pw.flush(); //Do I need both of these?
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}