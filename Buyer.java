import java.util.ArrayList;
import java.io.*;
/**
 * A buyer class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Buyer extends Person{

    /*
     * Items purchased by the buyer
     */
    private ArrayList<Product> purchased;

    public Buyer() {
        purchased = new ArrayList<Product>();
    }
    
    public Buyer(String username , String password, String filePath) {
        super(username, password, filePath);
        purchased = new ArrayList<Product>();
    }

    public ArrayList<Product> getPurchased() {
        return purchased;
    }

    public void addPurchased(Product product) {
        purchased.add(product);
    }

    /*
     * Gets all product in users.txt that have a name, description, or store that matches the search parameter
     */
    public ArrayList<Product> searchProducts(String searchParameter) {
        File f = new File("users.txt"); //GLOBAL FILE
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
            Object o = obj.readObject();
            while (o != null) {
                if (o instanceof Seller) {
                    Seller s = (Seller) o;
                    ArrayList<Store> stores = s.getStores();
                    for (Store store: stores) {
                        ArrayList<Product> product = store.getProducts();
                        for (Product p : product) {
                            if (p.getProductName().contains(searchParameter)) {
                                products.add(p);
                            } else if (p.getDescription().contains(searchParameter)) {
                                products.add(p);
                            } if (p.getStoreName().contains(searchParameter)) {
                                products.add(p);
                            } 
                        }
                    }
                }
                o = obj.readObject();
            }
            obj.close();
            return products;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Gets a product from the users.txt database given the 
     * Name of the the store and the product
     */
    public Product getProduct(String storeName , String productName) {
        File f = new File("users.txt"); //GLOBAL FILE
        try {
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
            Object o = obj.readObject();
            while (o != null) {
                if (o instanceof Seller) {
                    Seller s = (Seller) o;
                    ArrayList<Store> stores = s.getStores();
                    for (Store store: stores) {
                        if (store.getStoreName().equals(storeName)) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product product : products) {
                                if (product.getProductName().equals(productName)) {
                                    return product;
                                }
                            }
                        }
                    }
                }
                o = obj.readObject();
                obj.close();
            }
            return null;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Gets a store from the users.txt database given the 
     * Name of the the store
     */
    public Store getStore(String storeName) {
        File f = new File("users.txt"); //GLOBAL FILE
        try {
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
            Object o = obj.readObject();
            while (o != null) {
                if (o instanceof Seller) {
                    Seller s = (Seller) o;
                    ArrayList<Store> stores = s.getStores();
                    for (Store store: stores) {
                        if (store.getStoreName().equals(storeName)) {
                            return store;
                        }
                    }
                }
                o = obj.readObject();
                obj.close();
            }
            return null;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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
     * Prints out a string representation of a customer's purchase history
     * from sales.txt
     * 
     * Sales.txt is in format
     * (CustomerID,StoreName,ProductName,QuantityPurchased,Price)
     */
    public void viewHistory() {
        File f = new File("purchaseHistory.txt");  //GLOBAL FILE
        System.out.println("Purchase History:");
        System.out.println("(CustomerID,Store_Name,Product_Name,Quantity_Purchased,Price)");
        try {
            BufferedReader buf = new BufferedReader(new FileReader(f));
            String s = buf.readLine();
            while (s != null) {
                String[] split = s.split(",");
                if (split[0].equals(this.getUsername())) {
                    System.out.println(s);
                }
                s= buf.readLine();
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }


    /*
     * Adds a product to a user's shopping cart in the 
     * format (ProductName,StoreName)
     */
    public void addToCart(Product p) {
        try {
            FileWriter fw = new FileWriter("shoppingCart.txt" , true); //Local File
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
            FileWriter fw = new FileWriter("shoppingCart.txt" , false); //Local File
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("");
            pw.flush(); //Do I need both of these?
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Purchases a product from the store
     * 
     * Checks if the Store has enough to buy
     * Adds the product to the list of its purchased objects
     * Updates the purchase history
     * Update the users.txt file with the updated Sellers and Buyers after the purchase
     * Clears the cart
     */
    public void purchaseItem(String storeName , String productName, int quantity) {
        Product p = getProduct(storeName, productName); //Gets the product associated with this store and product name
        if (p.getQuantity() >= quantity) { //Checks there's enough in stock to buy
            purchased.add(p); //Adds product to list of purchased objects
            File f = new File("purchaseHistory.txt");  //GLOBAL FILE
            try {
                FileWriter fw = new FileWriter(f, true);   //updates the purchase history with info from the transaction
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                String output = this.getUsername() + "," + storeName + "," + productName + "," 
                + Integer.toString(quantity) + "," + Double.toString(p.getPrice());
                pw.print(output);
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            try {
                f = new File("users.txt"); //Changes the file to users file
                ArrayList<Object> objectList = new ArrayList<Object>();
                ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
                Object o = obj.readObject();
                while (o != null) {             //Reads all the objects from users
                    objectList.add(o);
                    o = obj.readObject();
                }
                obj.close();

                int index = 0;
                for (Object object : objectList) {
                    if (object instanceof Buyer) {     //If the object is a buyer 
                        Buyer b = (Buyer) object;
                        if (this.getUsername().equals(b.getUsername())) { //and it's the buyer doing the transaction
                          b.addPurchased(p); 
                          objectList.set(index,(Object) b);  //, replace the buyer with its updated version
                        }
                    } else if (o instanceof Seller) {  //If the object is a sesller
                        Seller s = (Seller) object;
                        ArrayList<Store> sStores = s.getStores();
                        boolean flag = false;
                        for (Store sStore : sStores) {
                            if (sStore.equals(storeName)) {
                                flag = true;
                            }
                        }
                        if (flag) {  //and the seller has the store we're looking for
                          s.makeSale(storeName, productName, quantity); //make the sale
                          objectList.set(index, s); //, replace the seller with its updated version
                    }
                    index++;
                    }
                }

                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));  //Repopulate users.txt
                for (Object ob : objectList) {
                    oos.writeObject(ob);
                }
                oos.close();


            } catch (Exception e) {
                e.printStackTrace();
            }






            clearCart();
        } else {
            System.out.println("Item is out of stock");
        }

    }

    






}