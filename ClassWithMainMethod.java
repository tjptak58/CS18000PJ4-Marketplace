import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class ClassWithMainMethod {

    public static void main(String[] args) {
        /**
         * Everytime program runs create -
         * Read from marketplace file to create an array of products
         * Read from shoppingcart file to create an array of products
         *
         */
        ArrayList<Store> marketPlace = new ArrayList<>();
        ArrayList<Product> shoppingCart = new ArrayList<>();

        /*
         *Read from storeListFile in the beginning of the program to create store objects and add them to marketPlace
         *  ArrayList
         */



        String storeListFile = "storeListFile.txt";
        try {
            File f = new File(storeListFile);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String storeSeller = line.substring(line.indexOf(';'));
                String storeFilePath = line.substring(line.indexOf(';') + 1);
                File loopFile = new File(storeFilePath);
                FileReader loopfr = new FileReader(loopFile);
                BufferedReader loopbfr = new BufferedReader(loopfr);
                String innerLine = loopbfr.readLine();
                String sellerName = innerLine;
                innerLine = loopbfr.readLine();




                while (innerLine != null) {
                    String productName = innerLine.substring(0, innerLine.indexOf(';'));
                    innerLine = innerLine.substring(innerLine.indexOf(';') + 1);
                    String storeName = innerLine.substring(0, innerLine.indexOf(';'));
                    innerLine = innerLine.substring(innerLine.indexOf(';') + 1);
                    String description = innerLine.substring(0, innerLine.indexOf(';'));
                    innerLine = innerLine.substring(innerLine.indexOf(';') + 1);
                    String qtyString = innerLine.substring(0, innerLine.indexOf(';'));
                    int qty = Integer.parseInt(qtyString);
                    innerLine = innerLine.substring(innerLine.indexOf(';') + 1);
                    String priceString = innerLine.substring(0, innerLine.indexOf(';'));
                    double price = Double.parseDouble(priceString);
                    innerLine = innerLine.substring(innerLine.indexOf(';') + 1);
                    String unitsPurchasedString = innerLine.substring(0, innerLine.indexOf(';'));
                    int unitsPurchased = Integer.parseInt(unitsPurchasedString);
                    innerLine = innerLine.substring(innerLine.indexOf(';') + 1);
                    String customerListString = innerLine;
                    String[] customerList = customerListString.split(",");
                    ArrayList<String> customerArrayList = new ArrayList<>();
                    for (int i = 0; i < customerList.length; i++) {
                        customerArrayList.add(customerList[i]);
                    }
                    Product loopProduct = new Product(productName, storeName, description, qty, price, unitsPurchased
                            , customerArrayList);

                }
                //End of storeFile
                Store loopStore = new Store(sellerName, loopPro)





            }

        } catch (IOException e) {

        }
        //Has info in the format userName;filePathToStore


        boolean loggedInAsBuyer = true;
        boolean loggedInAsSeller = false;
        if (loggedInAsBuyer) {
            while (loggedInAsBuyer) {
                System.out.println("Welcome to the marketplace!");
                System.out.println("1. View product listings");
                System.out.println("2. Edit shopping cart");
                System.out.println("3. Logout");

            }


        }

        if (loggedInAsSeller) {
            while (loggedInAsSeller) {

            }

        }
    }
}
