import java.util.ArrayList;
import java.io.*;
/**
 * A buyer class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Buyer extends Person{

    private ArrayList<Product> shoppingCart; 
    private ArrayList<Product> purchased;
    private String cart;
    private String history;


    public Buyer() {
    }
    
    public Buyer(String username , String password, String email, String cart , String history) {
        super(username, password, email);
        this.cart = cart;
        this.history = history;
        this.shoppingCart = new ArrayList<Product>();
        this.purchased = new ArrayList<Product>();

        try {
            File f = new File (cart); //ADDED THIS 11/13/22
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedReader buf = new BufferedReader(new FileReader(f));
            String s = buf.readLine();
            while (true) {
                if (s == null) {
                    break;
                } else {
                    String[] split = s.split(";");
                    shoppingCart.add(new Product(split[0], split[1], split[2], Integer.parseInt(split[3]), Double.parseDouble(split[4])));
                    s = buf.readLine();
                }  
            }
            buf.close();

            File fh = new File(history);
            if (!fh.exists()) {
                fh.createNewFile();
            }

            BufferedReader buftwo = new BufferedReader(new FileReader(fh));
            s = buftwo.readLine();
            while (true) {
                if (s == null) {
                    break;
                } else {
                    String[] split = s.split(";");
                    purchased.add(new Product(split[0], split[1], split[2], Integer.parseInt(split[3]), Double.parseDouble(split[4])));
                    s = buftwo.readLine();
                }  
            }
            buftwo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public ArrayList<Product> getPurchased() {
        return purchased;
    }

    /*
     * Gets all product in users.txt that have a name, description, or store that matches the search parameter
     */
    public static ArrayList<Product> searchProducts(String searchParameter , ArrayList<Product> market) {
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
    public static ArrayList<Product> sortPrice(ArrayList<Product> products , boolean highToLow) {
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
    public static ArrayList<Product> sortQuantity(ArrayList<Product> products , boolean highToLow) {
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
     * Adds a product to the shopping cart of a user
     */

    public boolean addToCart(Product product) {
        int available = product.getQuantity();
        if (available - product.getQuantity() >= 0) {
            shoppingCart.add(product);
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(new File(cart) , true));
                pw.print(product.getProductName() + ";");
                pw.print(product.getStoreName() + ";");
                pw.print(product.getDescription() + ";");
                pw.print(Integer.toString(product.getQuantity()) + ";");
                pw.print(Double.toString(product.getPrice()) + "\n");
                pw.flush();
                pw.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }

    /*
     * Removes a product from the shopping cart of the user
     */
    public void removeFromCart(Product product) {
        product.setQuantity(product.getQuantity() + 1);

        for (int i = 0 ; i < shoppingCart.size() ; i++) {
            if (shoppingCart.get(i).getProductName().equals(product.getProductName())) {
                shoppingCart.remove(i);
            }
        }
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File(cart) , false));
            for (int i = 0; i < shoppingCart.size(); i++) {
                pw.print(shoppingCart.get(i).getProductName() + ";");
                pw.print(shoppingCart.get(i).getStoreName() + ";");
                pw.print(shoppingCart.get(i).getDescription() + ";");
                pw.print(shoppingCart.get(i).getQuantity() + ";");
                pw.print(shoppingCart.get(i).getPrice() + "\n");
                pw.flush();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Writes the purchase history of a buyer to a file 
     * Specified as a parameter
     */
    public boolean exportHistory(String path) {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(new File(history)));
            PrintWriter pw = new PrintWriter(new FileWriter(new File(path) , true));           
            String s = buf.readLine();
            while (true) {
                if (s == null) {
                    break;
                } else {
                    String[] split = s.split(";");
                    Product p = new Product(split[0], split[1], split[2], Integer.parseInt(split[3]), Double.parseDouble(split[4]));
                    pw.println(p.toString());
                    s = buf.readLine();
                }  
            }
            buf.close();
            pw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Updates a user's purchase history when they purcahse an item
     * Adds the product to ArrayList<Product> purchase and writes to the 
     * history file.
     */
    public void purchase(Product p) {
        p.setUnitsPurchased(p.getUnitsPurchased() + p.getQuantity()); //ADDED THIS LINE 11/12/22
        purchased.add(p);
        ArrayList<String> curList = p.getCustomerList();
        curList.add(this.getUsername());
        p.setCustomerList(curList);
        shoppingCart.clear();
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File(history) , false));
            for (Product product : purchased) {
                pw.print(product.getProductName() + ";");
                pw.print(product.getStoreName() + ";");
                pw.print(product.getDescription() + ";");
                pw.print(Integer.toString(product.getQuantity()) + ";");
                pw.print(Double.toString(product.getPrice()) + "\n");
                pw.flush();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<Product> newShoppingCart) {
        this.shoppingCart = newShoppingCart;
    }

    public void setPathToCart(String cart) {
        this.cart = cart;
    }

    public String getCart() {
        return cart;
    }

    public String getHistory() {
        return history;
    }

    public void setPathToHistory(String history) {
        this.history = history;
    }


}
