import java.util.ArrayList;

/**
 * A product class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Product {
    /*
     * Name of the product
     * Must be a unique identifier for the product
     */
    private String productName;

    /*
     * Name of the store associated with it
     */
    private String storeName;

    /*
     * Description of the product
     */
    private String description;

    /*
     * Quantity of product left in stock
     */
    private int quantity;

    /*
     * Price of the product
     */
    private double price;

    /*
     * Number of units purchased
     */

    private int unitsPurchased;

    /*
     *ArrayList of users who have purchased the item
     */

    private ArrayList<String> customerList;

    public Product(String productName , String storeName , String description 
    , int quantity , double price, int unitsPurchased, ArrayList<String> customerList) {
        this.productName = productName;
        this.storeName = storeName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.unitsPurchased = unitsPurchased;
        this.customerList = customerList;
    }

    public Product(String productName , String storeName , String description 
    , int quantity , double price) {
        this.productName = productName;
        this.storeName = storeName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.unitsPurchased = 0;
        this.customerList = new ArrayList<String>();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUnitsPurchased() {
        return unitsPurchased;
    }

    public void setUnitsPurchased(int unitsPurchased) {
        this.unitsPurchased = unitsPurchased;
    }

    public ArrayList<String> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<String> customerList) {
        this.customerList = customerList;
    }

    //Added newline characters to toString methods

    public String toString() {
        String s = "";
        s = s + String.format("Product Name: %s \n", productName);
        s = s + String.format("Store Name: %s \n" , storeName);
        s = s + String.format("Description: %s \n" , description);
        s = s + String.format("Quantity: %s \n" , Integer.toString(quantity));
        s = s + String.format("Price: %s " , Double.toString(price));
        return s;

    }

    public String initialToString() {
        String s = "";
        s += String.format("Product Name: %s \n", productName);
        s += String.format("Store Name: %s \n" , storeName);
        s += String.format("Price: %s " , Double.toString(price));
        return s;

    }

    public String getStatistics() {
        String s = "";
        s = s + String.format("Units Purchased: %s \n" , Integer.toString(unitsPurchased));
        s = s + "Customer List: ";
        ArrayList<String> masterList = new ArrayList<>();
        //Edited method so that customer list prints out a list of customers who have purchased it along with the
        // number of units

        for (int i = 0; i < customerList.size(); i++) {
            if (masterList.indexOf(customerList.get(i)) == -1) {
                masterList.add(customerList.get(i));
            }

        }
        for (int j = 0; j < masterList.size(); j++) {
            int counter = 0;
            for (int k = 0; k < customerList.size(); k++) {
                if (masterList.get(j).equals(customerList.get(k))) {
                    counter++;
                }
            }

            if (counter > 1) {
                s += masterList.get(j) + ":" + counter + " units,";
            } else {
                s += masterList.get(j) + ":" + counter + "unit,";
            }

            return s.substring(0, s.length() - 1); //Removes string without comma at end

        }

        /**for (int i = 0 ; i < customerList.size() ; i++) {
            if (i == 0) {
                s = s + customerList.get(i);
            } else {
                s = s + "," + customerList.get(i);
            }
        } **/
        return s;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        if (product.productName.equals(productName) && product.storeName.equals(storeName) && product.description.equals(description) && product.quantity == quantity && product.price == price && product.unitsPurchased == unitsPurchased && product.customerList.equals(customerList)) {
            return true;
        } else {
            return false;
        }

    }

    public String fileString() {
        String s =
                this.getProductName() + ";" + this.storeName + ";" + this.description + ";" + this.quantity + ";" + this.price +
                ";" + this.unitsPurchased + ";" ;
        for (int i = 0; i < this.customerList.size(); i++) {
            s += this.customerList.get(i) + ",";
        }
        if (customerList.size() > 0) {
            return s.substring(0, s.length() - 1);

        } else {
            return s;
        }

    }
}