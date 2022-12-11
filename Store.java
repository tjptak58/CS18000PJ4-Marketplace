import java.io.File;
import java.io.IOException;
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

    private ArrayList<Purchase> purchaseLog;

    private String filePathToPurchaseLog;

    //Constructor for a new store
    public Store(String sellerName , String storeName , String filePath, String filePathToPurchaseLog) {
        this.sellerName = sellerName;
        this.storeName = storeName;
        this.filePath = filePath;
        this.filePathToPurchaseLog = filePathToPurchaseLog;
        try {
            File f = new File (filePath);
            if (!f.exists()) {
                f.createNewFile();
            }
            File pf = new File (filePathToPurchaseLog);
            if (!pf.exists()) {
                pf.createNewFile();
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        products = new ArrayList<Product>();
        storeRevenue = 0;
        purchaseLog = new ArrayList<Purchase>();

    }
    
    //Constructor for an existing store
    public Store(String sellerName, String storeName , String filePath, ArrayList<Product> products, double storeRevenue, ArrayList<Purchase> purchaseLog, String filePathToPurchaseLog) {
        this.sellerName = sellerName;
        this.storeName = storeName;
        this.filePath = filePath;
        this.products = products;
        this.storeRevenue = storeRevenue;
        this.purchaseLog = purchaseLog;
        this.filePathToPurchaseLog = filePathToPurchaseLog;

    }

    public Store() {
        sellerName = null;
        storeName = null;
        filePath = null;
        products = null;
        storeRevenue = 0;
        purchaseLog = null;
        filePathToPurchaseLog = null;
    }

    public ArrayList<Purchase> getPurchaseLog() {
        return purchaseLog;
    }

    public String getFilePathToPurchaseLog() {
        return filePathToPurchaseLog;
    }

    public void setFilePathToPurchaseLog(String filePathToPurchaseLog) {
        this.filePathToPurchaseLog = filePathToPurchaseLog;
    }

    public void setPurchaseLog(ArrayList<Purchase> purchaseLog) {
        this.purchaseLog = purchaseLog;
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

    public void addPurchaseLog(Purchase newPurchase) {
        this.purchaseLog.add(newPurchase);
    }

    //Create a getNumberOfSales method that sums up number of units sold for each product in that store
    public boolean equals(Store s) {
        boolean flag = true;
        if (s.storeName.equals(this.storeName)) {
            if (s.sellerName.equals(this.sellerName)) {
                if (s.filePath.equals(this.filePath)) {
                    if (s.storeName.equals(this.storeName)) {
                        for (int i = 0 ; i < products.size() ; i++) {
                            if (! (s.getProducts().get(i).equals(this.getProducts().get(i)))) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}