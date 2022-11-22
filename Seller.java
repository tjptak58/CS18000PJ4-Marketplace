import java.util.ArrayList;
import java.io.*;

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

    private String filePath;

    public Seller(String username , String password, String email, String filePath) {
        super(username , password, email);
        this.filePath = filePath;
        stores = new ArrayList<Store>();
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /*
     * Creates a new store for a seller and adds that store
     * to an arraylist of its current stores
     */
    public void createStore(String sellerName , String storeName, String filePath) {
        Store store = new Store(sellerName , storeName , filePath);
        stores.add(store);
    }

    public void createStore(Store s) {
        stores.add(s);
    }

    /*
     * Allows a seller to edit a product in one of their stores
     */
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
    
    /*
     * Deletes a product in a store
     */
    public void deleteProduct(Product p, String storeName) {
        for (Store s : stores) {
            if (storeName.equals(s.getStoreName())) {
                s.removeProduct(p);
            }
        }
    }

    /*
     * Deletes a store in stores
     */
    public void deleteStore(Store st) {
        for (Store s : stores) {
            if (st.getStoreName().equals(s.getStoreName())) {
                stores.remove(s);
            }
        }
    }
    
    /*
     * Returns an arraylist of prodcuts thata have been purcahsed
     * in a store
     */
    public static ArrayList<Product> listSales(ArrayList<Product> Store) {
        ArrayList<Product> sales = new ArrayList<Product>();
        for (Product p : Store) {
            if (p.getCustomerList() != null)
                sales.add(p);
        }
        return sales;
    }
   
    
    /*
     * add to the buyer.txt and seller.txt via CSV file
     * one for export as well
     * 
     * In the format productName,storeName,description 
    ,quantity,price
     * 
     */
    public ArrayList<Product> importProducts(String path) {
        try {
            var output = new ArrayList<Product>();
            BufferedReader buf = new BufferedReader(new FileReader(new File(path)));
            String s = buf.readLine();
            while (true) {
                if (s == null) {
                    break;
                } else {
                    String[] split = s.split(";");
                    output.add(new Product(split[0], split[1], split[2], Integer.parseInt(split[3]), Double.parseDouble(split[4])));
                    s = buf.readLine();
                }  
            }
            return output;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //Changed return type from void to boolean 11/13/22
    public boolean exportProducts(String path , ArrayList<Product> products) {
        try {
            var output = new ArrayList<Product>();
            PrintWriter pw = new PrintWriter(new FileWriter(new File(path)));
            for (Product product : products) {
                pw.print(product.getProductName() + ";");
                pw.print(product.getStoreName() + ";");
                pw.print(product.getDescription() + ";");
                pw.print(Integer.toString(product.getQuantity()) + ",");
                pw.print(Double.toString(product.getPrice()) + "\n");

            }
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addStore(Store store) {
        this.stores.add(store);
    }
}
