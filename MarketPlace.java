import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Main method for project that implements the marketplace
 *
 * @author
 *
 * @verion 11/14/22
 */

public class MarketPlace {
    /**
     *Static string that stores the username of the logged in buyer or seller
     */
    static String user;

    /**
     * Static booleans to determine whether a buyer or seller has logged in
     */
    static boolean loggedInAsBuyer;
    static boolean loggedInAsSeller;



    public void deleteBuyer(ArrayList<Buyer> buyers, String usernameBuyer, String buyerPassword, String email,
                            String cart, String history) {
        Buyer buyer = new Buyer(usernameBuyer, buyerPassword, email, cart, history);
        for (int i = 0; i < buyers.size(); i++) {
            if (buyer.getUsername().equals(usernameBuyer)) {
                buyers.remove(buyer);
            }
        }
    }


    /**
     * Method to print out the contents of marketplace
     * @author Rohan,
     * @param superListOfProducts
     */
    public  static void printMarketPlace (ArrayList<Product> superListOfProducts) {
        for (int i = 0; i <superListOfProducts.size(); i++) {
            System.out.println("--------------------");
            System.out.println("Product Number " + (i + 1));
            System.out.println(superListOfProducts.get(i).toString());


        }

    }



    /*
     * Main method
     */

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        ArrayList<String> sellerArrayList = new ArrayList<>();
        ArrayList<String> buyerArrayList = new ArrayList<>();
        HashMap<String, String> sellerLogin = new HashMap<String, String>();
        HashMap<String, String> buyerLogin = new HashMap<String, String>();
        ArrayList<String> usernameAndPasswordSeller = new ArrayList<>();
        ArrayList<String> usernameAndPasswordBuyer = new ArrayList<>();
        /**
         * Login System
         * @author
         */


        System.out.println("Welcome");
        boolean flag;
        boolean loginFailed;
        boolean usernameAlreadyExists;
        boolean loginSuccessful = false;
        boolean usernameIsWrong = false;
        boolean passwordIsWrong = false;
        // if you create an account then go back to beginning and
        // ask if they want to create a new account or login with existing one
        do {

            usernameAndPasswordSeller.clear();
            usernameAndPasswordBuyer.clear();
            try {
                BufferedReader bfr = new BufferedReader(new FileReader("seller.txt"));
                String line = "";
                while ((line = bfr.readLine()) != null) {
                    usernameAndPasswordSeller.add(line);
                }
                bfr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader bfr = new BufferedReader(new FileReader("buyer.txt"));
                String line = "";
                while ((line = bfr.readLine()) != null) {
                    usernameAndPasswordBuyer.add(line);
                }
                bfr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = false;
            loginFailed = false;
            usernameAlreadyExists = false;
            loginSuccessful = false;
            usernameIsWrong = false;
            passwordIsWrong = false;


            // ask if they have an exisiting or new account

            System.out.println("Would you like you login or create a new account?");
            System.out.println("1.Login\n2.Create new account");
            String newOrExisting = scanner.nextLine();
            // if 2 create a new account and ask if they are sellerArrayList or buyer
            if (newOrExisting.equals("2")) {
                System.out.println("Are you a Seller or Buyer");
                String sellOrBuy = scanner.nextLine();
                // if they are sellerArrayList ask username
                // password and what stores they would like to sell through
                if (sellOrBuy.equalsIgnoreCase("Seller")) {
                    String usernameSeller = "";
                    do {
                        usernameAlreadyExists = false;
                        System.out.println("Enter your new username");
                        usernameSeller = scanner.nextLine();
                        // if this is empty then there are no accounts put in yet
                        if (!usernameAndPasswordSeller.isEmpty()) {
                            // check if there is already a username that exists
                            for (int i = 0; i < usernameAndPasswordSeller.size(); i++) {
                                if (usernameAndPasswordSeller.get(i).substring(0,
                                        usernameAndPasswordSeller.get(i).indexOf(";")).equals(usernameSeller)) {
                                    System.out.println("Error: Username already exists. Pick a new username");
                                    usernameAlreadyExists = true;
                                }
                            }
                        }

                    } while (usernameAlreadyExists);
                    System.out.println("Enter your new password");
                    String sellerPassword = scanner.nextLine();
                    sellerLogin.put(usernameSeller, sellerPassword);
                    System.out.println("Enter your e-mail");
                    String email = scanner.nextLine();
                    System.out.println("What is the filepath for the statistics of the seller");
                    String statisticsFilepath = scanner.nextLine();

                    Collections.addAll(sellerArrayList, usernameSeller,
                            sellerPassword, email, statisticsFilepath);
                    // Seller seller = new Seller(usernameSeller, password);


                    System.out.println("Account made!");

                    try {
                        FileOutputStream fos = new FileOutputStream("seller.txt", true);
                        PrintWriter pw = new PrintWriter(fos);
                        //BufferedWriter bfw = new BufferedWriter
                        //        (new FileWriter("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Seller.txt"));
                        pw.write(usernameSeller + ";" + sellerPassword + ";"
                                + email + ";" + statisticsFilepath +
                                "\n");
                        pw.flush();
                        pw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else if (sellOrBuy.equalsIgnoreCase("Buyer")) {
                    String usernameBuyer = "";
                    do {
                        usernameAlreadyExists = false;
                        System.out.println("Enter your new username");
                        usernameBuyer = scanner.nextLine();
                        // if this is empty then there are no accounts put in yet
                        if (!(usernameAndPasswordBuyer.size() == 0)) {


                            // check if there is already a username that exists
                            for (int i = 0; i < usernameAndPasswordBuyer.size(); i++) {
                                if (usernameAndPasswordBuyer.get(i).substring(0,
                                        usernameAndPasswordBuyer.get(i).indexOf(";")).equals(usernameBuyer)) {
                                    System.out.println("Error: Username already exists. Pick a new username");
                                    usernameAlreadyExists = true;
                                }
                            }
                        }

                    } while (usernameAlreadyExists);
                    System.out.println("Enter your new password");
                    String buyerPassword = scanner.nextLine();
                    sellerLogin.put(usernameBuyer, buyerPassword);
                    System.out.println("Enter your e-mail");
                    String email = scanner.nextLine();
                    System.out.println("What is the filepath to the purchase history of this account?");
                    String purchaseHistoryFilepath = scanner.nextLine();
                    System.out.println("What is the filepath to the shopping cart for this account?");
                    String shoppingCartFilepath = scanner.nextLine();
                    System.out.println("Account made!");

                    Collections.addAll(buyerArrayList, usernameBuyer,
                            buyerPassword, email,
                            purchaseHistoryFilepath, shoppingCartFilepath);
                    // Seller seller = new Seller(usernameSeller, password);




                    try {
                        FileOutputStream fos = new FileOutputStream("buyer.txt", true);
                        PrintWriter pw = new PrintWriter(fos);

                        pw.write(usernameBuyer + ";" + buyerPassword + ";" +
                                email + ";" + shoppingCartFilepath + ";" +
                                purchaseHistoryFilepath + "\n");
                        pw.flush();
                        pw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                flag = true;
                // if 1 is input then you try to login

            } else if (newOrExisting.equals("1")) {

                System.out.println("Do you want to login as a Seller or Buyer");
                String sellerOrBuyer = scanner.nextLine();
                if (sellerOrBuyer.equalsIgnoreCase("Seller")) {
                    if (usernameAndPasswordSeller.size() == 1) {
                        System.out.println("Enter your username");
                        String username = scanner.nextLine();
                        user = username;
//                    // of the array list username, password. If 0 index which is username
//                   contains username go to next step



                        if (usernameAndPasswordSeller.get(0).substring(0,
                                usernameAndPasswordSeller.get(0).indexOf(";")).equals(username)) {

                            System.out.println("Enter your password");
                            String password = scanner.nextLine();



                            String[] split = usernameAndPasswordSeller.get(0).split(";");
                            String passwordTrim = split[1].trim();


                            if (passwordTrim.equals(password)) {
                                // System.out.println("Login successful!");
                                loginFailed = false;
                                flag = false;
                                loggedInAsSeller = true;
                                loginSuccessful = true;


                                //boolean goIntoElse=false;
                            } else {
                                //   System.out.println("Error: Password is incorrect");
                                loginFailed = true;
                                passwordIsWrong = true;

                            }

                        } else {
                            // System.out.println("Username not found");
                            loginFailed = true;
                            usernameIsWrong = true;


                        }


                        // System.out.println("hi");
                        if (loginSuccessful)
                            usernameIsWrong = false;
                        loginFailed = false;
                        if (passwordIsWrong) {
                            System.out.println("Error: Password is incorrect");
                            usernameIsWrong=false;
                        }
                        if (usernameIsWrong) {
                            System.out.println("Username not found");
                        }

                        if (loginSuccessful) {
                            System.out.println("Login successful!");
                        }
                    } else if (usernameAndPasswordSeller.size() > 1) {
                        System.out.println("Enter your username");
                        String username = scanner.nextLine();
                        user = username;
//                    // of the array list username, password. If 0 i
//                    ndex which is username contains username go to next step
                        for (int i = 0; i < usernameAndPasswordSeller.size(); i++) {


                            if (usernameAndPasswordSeller.get(i).substring(0,
                                    usernameAndPasswordSeller.get(i).indexOf(";")).equals(username)) {

                                System.out.println("Enter your password");
                                String password = scanner.nextLine();
                                for (int j = 1; j < usernameAndPasswordSeller.size(); j++) {


                                    String[] split = usernameAndPasswordSeller.get(i).split(";");
                                    String passwordTrim = split[1].trim();


                                    if (passwordTrim.equals(password)) {
                                        // System.out.println("Login successful!");
                                        loginFailed = false;
                                        flag = false;
                                        loggedInAsSeller = true;
                                        loginSuccessful = true;


                                        //boolean goIntoElse=false;
                                    } else {
                                        //   System.out.println("Error: Password is incorrect");
                                        loginFailed = true;
                                        passwordIsWrong = true;

                                    }
                                }
                            } else {
                                // System.out.println("Username not found");
                                loginFailed = true;
                                usernameIsWrong = true;


                            }

                        }
                        // System.out.println("hi");
                        if (loginSuccessful)
                            usernameIsWrong = false;
                        loginFailed = false;
                        if (passwordIsWrong) {
                            System.out.println("Error: Password is incorrect");
                            usernameIsWrong=false;
                        }
                        if (usernameIsWrong) {
                            System.out.println("Username not found");
                        }

                        if (loginSuccessful) {
                            System.out.println("Login successful!");
                        }
                    }

                } else if (sellerOrBuyer.equalsIgnoreCase("Buyer")) {
                    if (usernameAndPasswordBuyer.size() == 1) {
                        System.out.println("Enter your username");
                        String username = scanner.nextLine();
                        user = username;

//                    // of the array list username,
//                    password. If 0 index which is username
//                    contains username go to next step



                        if (usernameAndPasswordBuyer.get(0).substring(0,
                                usernameAndPasswordBuyer.get(0).indexOf(";")).equals(username)) {

                            System.out.println("Enter your password");
                            String password = scanner.nextLine();


                            String[] split = usernameAndPasswordBuyer.get(0).split(";");
                            String passwordTrim = split[1].trim();


                            if (passwordTrim.contains(password)) {
                                // System.out.println("Login successful!");
                                loginFailed = false;
                                flag = false;
                                loggedInAsBuyer = true;
                                loginSuccessful = true;


                                //boolean goIntoElse=false;
                            } else {
                                //   System.out.println("Error: Password is incorrect");
                                loginFailed = true;
                                passwordIsWrong = true;

                            }

                        } else {
                            // System.out.println("Username not found");
                            loginFailed = true;
                            usernameIsWrong = true;


                        }


                        if (loginSuccessful)
                            usernameIsWrong = false;
                        loginFailed = false;
                        if (usernameIsWrong) {
                            System.out.println("Username not found");
                        }
                        if (passwordIsWrong) {
                            System.out.println("Error: Password is incorrect");
                        }
                        if (loginSuccessful) {
                            System.out.println("Login successful!");
                        }
                    } else if (usernameAndPasswordBuyer.size() > 1) {

                        System.out.println("Enter your username");
                        String username = scanner.nextLine();
                        user = username;

//                    // of the array list username, password. If 0 index which is username contains username go to next step
                        for (int i = 0; i < usernameAndPasswordBuyer.size(); i++) {


                            if (usernameAndPasswordBuyer.get(i).substring(0,
                                    usernameAndPasswordBuyer.get(i).indexOf(";")).equals(username)) {

                                System.out.println("Enter your password");
                                String password = scanner.nextLine();
                                for (int j = 1; j < usernameAndPasswordBuyer.size(); j++) {

                                    String[] split = usernameAndPasswordBuyer.get(i).split(";");
                                    String passwordTrim = split[1].trim();


                                    if (passwordTrim.equals(password)) {
                                        // System.out.println("Login successful!");
                                        loginFailed = false;
                                        flag = false;
                                        loggedInAsBuyer = true;
                                        loginSuccessful = true;


                                        //boolean goIntoElse=false;
                                    } else {
                                        //   System.out.println("Error: Password is incorrect");
                                        loginFailed = true;
                                        passwordIsWrong = true;

                                    }
                                }
                            } else {
                                // System.out.println("Username not found");
                                loginFailed = true;
                                usernameIsWrong = true;


                            }

                        }
                        if (loginSuccessful)
                            usernameIsWrong = false;
                        loginFailed = false;
                        if (usernameIsWrong) {
                            System.out.println("Username not found");
                        }
                        if (passwordIsWrong) {
                            System.out.println("Error: Password is incorrect");
                        }
                        if (loginSuccessful) {
                            System.out.println("Login successful!");
                        }
                    }

                }
            }
            // for when account is created so user can go back to beginning
        } while (flag || !loginSuccessful);



        /**
         * Login part of program returns a static string user
         * which is username of buyer if logged in as buyer
         * or is username of seller if logged in as seller
         *
         * Login part also sets loggedInAsBuyer or loggedInAsSeller to true accordingly
         *
         *
         *
         */

        //ArrayList of Stores that stores information from storeListFile
        ArrayList<Store> marketPlace = new ArrayList<>();


        /**
         *Read from storeListFile in the beginning of the program to create
         * store objects and add them to marketPlace
         */

        String storeListFile = "storeListFile.txt";
        try {
            File f = new File(storeListFile);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String sellerName = line.substring(0, line.indexOf(';'));
                line = line.substring(line.indexOf(';') + 1);
                String storeName = line.substring(0, line.indexOf(';'));
                line = line.substring(line.indexOf(';') + 1);
                double storeRevenue = Double.parseDouble(line.substring(0, line.indexOf(';')));
                line = line.substring(line.indexOf(';') + 1);
                String storeFilePath = line;
                File loopFile = new File(storeFilePath);
                if (!loopFile.exists()) {
                    loopFile.createNewFile();
                }
                FileReader loopfr = new FileReader(loopFile);
                BufferedReader loopbfr = new BufferedReader(loopfr);

                String innerLine = loopbfr.readLine();
                ArrayList<Product> productsInStore = new ArrayList<>();



                while (innerLine != null) {
                    String productName = innerLine.substring(0, innerLine.indexOf(';'));
                    innerLine = innerLine.substring(innerLine.indexOf(';') + 1);
                    String storeNameOfProduct = innerLine.substring(0, innerLine.indexOf(';'));
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

                    Product loopProduct = new Product(productName, storeNameOfProduct, description, qty, price, unitsPurchased
                            , customerArrayList);
                    productsInStore.add(loopProduct);
                    innerLine = loopbfr.readLine();

                }
                //End of storeFile
                Store loopStore = new Store(sellerName, storeName, storeFilePath, productsInStore, storeRevenue );
                marketPlace.add(loopStore);
                line = bfr.readLine();






            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an error while running the program!");

        }

        /**
         * Creating an ArrayList of products to implement features and printout to terminal
         */
        ArrayList<Product> superListOfProducts = new ArrayList<>();
        for (int i = 0; i < marketPlace.size(); i++ ) {
            for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                superListOfProducts.add(marketPlace.get(i).getProducts().get(j));
            }
        }


        /*
         *Read from buyer.txt to create an array of buyer objects
         */
        ArrayList<Buyer> buyerList = new ArrayList<>();
        try {
            File f = new File("buyer.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String[] lineArray = line.split(";");
                String username = lineArray[0];
                String password = lineArray[1];
                String email = lineArray[2];
                String filePathToShoppingCart = lineArray[3];
                String filePathToPurchaseHistory = lineArray[4];
                //Initialize new buyer object
                Buyer buyer = new Buyer(username, password, email, filePathToShoppingCart, filePathToPurchaseHistory);
                buyerList.add(buyer);
                line = bfr.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an error while running the program!");

        }


        ArrayList<Seller> sellerList = new ArrayList<>();
        try {
            File f = new File("seller.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String[] lineArray = line.split(";");
                Seller loopSeller = new Seller(lineArray[0], lineArray[1], lineArray[2], lineArray[3]);
                sellerList.add(loopSeller);
                line = bfr.readLine();





            }



        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an error while running the program!");
        }

        for (int i = 0; i < marketPlace.size(); i++) {
            for (int j = 0; j < sellerList.size(); j++) {
                if (marketPlace.get(i).getSellerName().equals(sellerList.get(j).getUsername())) {
                    sellerList.get(j).addStore(marketPlace.get(i));

                }

            }
        }


        if (loggedInAsBuyer) {
            /**for (int i = 0; i < buyerList.size(); i ++) {
                if (buyerList.get(i).getUsername().equals(user)) {
                    buyerList.get(i).modify();
                }
            } //Processing buyer objects in place --  format **/
            System.out.println("Welcome to the marketplace!");
            while (loggedInAsBuyer) {

                System.out.println("1. View product listings");
                System.out.println("2. View shopping cart");
                System.out.println("3. View previously purchased items");
                System.out.println("4. Edit your account info");
                System.out.println("5. Contact a seller");
                System.out.println("6. Logout");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {

                    int secondChoice = -1;
                    while (secondChoice != 7) {
                        MarketPlace.printMarketPlace(superListOfProducts);
                        System.out.println("----------------");
                        System.out.println("1. Select a product");
                        System.out.println("2. Sort by price from high to low");
                        System.out.println("3. Sort by price from low to high");
                        System.out.println("4. Sort by quantity from high to low");
                        System.out.println("5. Sort by quantity from low to high");
                        System.out.println("6. Search for a specific product");
                        System.out.println("7. Go back");
                        secondChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (secondChoice == 1) {
                            System.out.println("Enter the product number :");
                            int productNumber = scanner.nextInt();
                            scanner.nextLine();
                            if (productNumber > marketPlace.size()) {
                                System.out.println("Invalid input!");
                            } else {
                                System.out.println(superListOfProducts.get(productNumber - 1).toString());
                                System.out.println("1. Add to cart");
                                System.out.println("2. Go back");
                                int purchaseChoice = scanner.nextInt();
                                scanner.nextLine();
                                if (purchaseChoice == 1) {
                                    //Add to cart
                                    for (int i = 0; i < buyerList.size(); i++) {
                                        if (buyerList.get(i).getUsername().equals(user)) {
                                            if (buyerList.get(i).addToCart(superListOfProducts.get(productNumber - 1))) {
                                                System.out.println("Added to cart!");
                                            } else {
                                                System.out.println("This item is out of stock!");
                                            }
                                        }
                                    }

                                }

                            }


                        } else if (secondChoice == 2) {
                            superListOfProducts = Buyer.sortQuantity(superListOfProducts, true );
                        } else if (secondChoice == 3) {
                            superListOfProducts = Buyer.sortQuantity(superListOfProducts, false);
                        } else if (secondChoice == 4) {
                            superListOfProducts = Buyer.sortPrice(superListOfProducts, true);
                        } else if (secondChoice == 5) {
                            superListOfProducts = Buyer.sortPrice(superListOfProducts, false);
                        } else if (secondChoice == 6) {
                            System.out.println("Enter the search word");
                            String searchParam = scanner.nextLine();
                            ArrayList<Product> matchArray = Buyer.searchProducts(searchParam, superListOfProducts);
                            if (matchArray.size() == 0) {
                                System.out.println("No products found!");
                            } else {
                                for (int i = 0; i < matchArray.size(); i++) {
                                    System.out.println("----------------");
                                    System.out.println("Product Number " + (i + 1));
                                    System.out.println(matchArray.get(i).initialToString());
                                }
                                int specSearchChoice = -1;
                                while (specSearchChoice != 2) {
                                    System.out.println("1. Select a product");
                                    System.out.println("2. Go back");
                                    specSearchChoice = scanner.nextInt();
                                    scanner.nextLine();
                                    if (specSearchChoice == 1) {
                                        System.out.println("Enter the product number:");
                                        int productNum = scanner.nextInt();
                                        scanner.nextLine();
                                        if (productNum > matchArray.size()) {
                                            System.out.println("Invalid input");
                                        } else {
                                            System.out.println(matchArray.get(productNum - 1).toString());
                                            System.out.println("1. Add to cart");
                                            System.out.println("2. Go back");
                                            int specCartChoice = scanner.nextInt();
                                            if (specCartChoice == 1) {
                                                for (int i = 0; i < superListOfProducts.size(); i++) {
                                                    if (superListOfProducts.get(i).getProductName().equals(matchArray.get(productNum - 1).getProductName())) {
                                                        for (int j = 0; j < buyerList.size(); j++) {
                                                            if (buyerList.get(j).getUsername().equals(user)) {
                                                                if (buyerList.get(j).addToCart(superListOfProducts.get(i))) {
                                                                    System.out.println("Added to cart!");
                                                                } else {
                                                                    System.out.println("This item is out of stock!");
                                                                }

                                                            }
                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    }

                                }

                            }


                        }

                    }

                } else if (choice == 2) {
                    int checkoutChoice = -1;
                    while (checkoutChoice != 3) {
                        for (int i = 0; i < buyerList.size(); i++) {
                            if (buyerList.get(i).getUsername().equals(user)) {
                                for (int j = 0; j < buyerList.get(i).getShoppingCart().size(); j++) {
                                    System.out.println("----------------");
                                    System.out.println(buyerList.get(i).getShoppingCart().get(j).initialToString());
                                }
                            }
                        }
                        System.out.println("1. Purchase all items in shopping cart");
                        System.out.println("2. Remove an item from shopping cart");
                        System.out.println("3. Go back");
                        checkoutChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (checkoutChoice == 1) {
                            for (int i = 0; i < buyerList.size(); i++) {
                                if (buyerList.get(i).getUsername().equals(user)) {
                                    if (buyerList.get(i).getShoppingCart().size() == 0) {
                                        System.out.println("Your shopping cart is empty!!");
                                    }
                                    for (int j = 0; j < buyerList.get(i).getShoppingCart().size(); j++) {
                                        buyerList.get(i).purchase(buyerList.get(i).getShoppingCart().get(j));
                                        buyerList.get(i).removeFromCart(buyerList.get(i).getShoppingCart().get(j));
                                        buyerList.get(i).getShoppingCart().get(j).setQuantity(buyerList.get(i).getShoppingCart().get(j).getQuantity() - 1);
                                        System.out.println("Purchase successful!"); //Prints success message for each
                                        // item
                                        for (int p = 0; p < marketPlace.size(); p++) {
                                            if (marketPlace.get(p).getStoreName().equals(buyerList.get(i).getShoppingCart().get(j).getStoreName())) {
                                                //Add to stores revenue
                                                marketPlace.get(p).setStoreRevenue(marketPlace.get(p).getStoreRevenue() + buyerList.get(i).getShoppingCart().get(j).getPrice());
                                            }
                                        }
                                        //For each item in shopping cart search marketplace for a store name that
                                        // matches product store name and increase that stores revenue


                                    }





                                }
                            }


                        } else if (checkoutChoice == 2) {
                            for (int i = 0; i < buyerList.size(); i++) {
                                if (buyerList.get(i).getUsername().equals(user)) {
                                    for (int j = 0; j < buyerList.get(i).getShoppingCart().size(); j++ ) {
                                        System.out.println("----------------");
                                        System.out.println("Product Number " + (j + 1));
                                        System.out.println(buyerList.get(i).getShoppingCart().get(j).initialToString());
                                    }
                                    System.out.println("Enter the product number of the product you would like to " +
                                            "remove from cart:");
                                    int rmvNum = scanner.nextInt();
                                    scanner.nextLine();
                                    if (rmvNum > buyerList.get(i).getShoppingCart().size()) {
                                        System.out.println("Invalid input!");
                                    } else {
                                        buyerList.get(i).removeFromCart(buyerList.get(i).getShoppingCart().get(rmvNum - 1));
                                        System.out.println("Removed product from cart successfully!");
                                    }


                                }

                            }

                        }

                    }




                } else if (choice == 3) {
                    System.out.println("----------------");
                    System.out.println("Enter file path to which you would like to export your purchase history");
                    String exportPath = scanner.nextLine();
                    for (int i = 0; i < buyerList.size(); i++) {
                        if (buyerList.get(i).getUsername().equals(user)) {
                            if (buyerList.get(i).exportHistory(exportPath)) {
                                System.out.println("Exported successfully!");
                            } else {
                                System.out.println("There was an error writing to the file!");
                            }
                        }
                    }

                } else if (choice == 4) {
                    System.out.println("----------------");
                    System.out.println("1. Edit info");
                    System.out.println("2. Delete account");
                    int choiceFour = scanner.nextInt();
                    if (choiceFour == 1) {
                        System.out.println("Enter your new email ID: ");
                        String newEmail = scanner.nextLine();
                        System.out.println("Enter your new password: ");
                        String newPassword = scanner.nextLine();
                        System.out.println("Enter new path to shopping cart: ");
                        String newPathToCart = scanner.nextLine();
                        System.out.println("Enter new path to purchase history: ");
                        String newPathToHistory = scanner.nextLine();
                        for (int i = 0; i < buyerList.size(); i++) {
                            if (buyerList.get(i).getUsername().equals(user)) {
                                buyerList.get(i).setEmail(newEmail);
                                buyerList.get(i).setPassword(newPassword);
                                buyerList.get(i).setPathToCart(newPathToCart);
                                buyerList.get(i).setPathToHistory(newPathToHistory);
                                System.out.println("Successfully changed account information");
                            }
                        }

                    } else if (choiceFour == 2) {
                        for (int i = 0; i < buyerList.size(); i++) {
                            if (buyerList.get(i).getUsername().equals(user)) {
                                buyerList.remove(i);
                                System.out.println("Account deleted successfully");
                                break;
                            }
                        }
                    }

                } else if (choice == 5) {
                    System.out.println("----------------");
                    System.out.println("Enter the name of the store whose seller you would like to contact");
                    String contactStore = scanner.nextLine();
                    boolean sellerFound = false;
                    for (int i = 0; i < sellerList.size(); i++) {
                        for (int j = 0; j < sellerList.get(i).getStores().size(); j++) {
                            if (sellerList.get(i).getStores().get(j).getStoreName().equals(contactStore)) {
                                System.out.println("The seller of this store can be contacted at this email :" + sellerList.get(i).getEmail());
                                sellerFound = true;
                            }
                        }
                    }
                    if (!sellerFound) {
                        System.out.println("Could not find store specified!");
                    }

                } else if (choice == 6) {
                    loggedInAsBuyer = false;

                }


            }


        }

        if (loggedInAsSeller) {
            /**
             * Sellers can create, edit, or delete products associated with their stores.
             * Sellers can view a list of their sales by store, including customer information and revenues from the sale.
             * Sellers can import or export products for their stores using a csv file.
             * All product details should be included, with one row per product.
             * Sellers can view a dashboard that lists statistics for each of their stores.
             * Data will include a list of customers with the number of items that they have purchased and a list of products with the number of sales.
             * Sellers can choose to sort the dashboard.
             * Sellers can view the number of products currently in customer shopping carts, along with the store and details associated with the products.
             */
            System.out.println("Welcome to the marketplace");
            while (loggedInAsSeller) {

                System.out.println("1. View your stores");
                System.out.println("2. View your dashboard");
                System.out.println("3. View products from your store customers have in their carts");
                System.out.println("4. Logout ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    for (int i  = 0; i < sellerList.size(); i++) {
                        if (sellerList.get(i).getUsername().equals(user)) {
                            for (int j = 0; j < sellerList.get(i).getStores().size(); j++) {
                                System.out.println( (j + 1) + ". " + sellerList.get(i).getStores().get(j).getStoreName());

                            }
                            int sellerChoice = -1;
                            while (sellerChoice != 4) {
                                System.out.println("----------------");
                                System.out.println("1. Select a store to view more information");
                                System.out.println("2. Add a store");
                                System.out.println("3. Delete a store");
                                System.out.println("4. Go back");
                                sellerChoice = scanner.nextInt();
                                scanner.nextLine();
                                if (sellerChoice == 1) {
                                    System.out.println("Enter store number :");

                                    int storeNum = scanner.nextInt();

                                    scanner.nextLine();
                                    if (storeNum > sellerList.get(i).getStores().size()) {
                                        System.out.println("Invalid input!");
                                    } else {
                                        int modifyStoreChoice = -1;
                                        while (modifyStoreChoice != 6) {
                                            for (int k = 0; k < sellerList.get(i).getStores().get(storeNum - 1).getProducts().size(); k++) {
                                                System.out.println("----------------");
                                                System.out.println("Product Number " + (k + 1));
                                                System.out.println(sellerList.get(i).getStores().get(storeNum - 1).getProducts().get(k).toString());
                                                System.out.println(sellerList.get(i).getStores().get(storeNum - 1).getProducts().get(k).getStatistics());

                                            }
                                            System.out.println("----------------");

                                            System.out.println("1. Add a product for this store");
                                            System.out.println("2. Remove a product for this store");
                                            System.out.println("3. Modify information for an existing product");
                                            System.out.println("4. Import products for this store using a csv file");
                                            System.out.println("5. Export products for this store using a csv file");
                                            System.out.println("6. Go back");
                                            modifyStoreChoice = scanner.nextInt();
                                            scanner.nextLine();
                                            if (modifyStoreChoice == 1) {
                                                System.out.println("Enter the product name: ");
                                                String newProdName = scanner.nextLine();
                                                System.out.println("Enter the description for this product");
                                                String description = scanner.nextLine();
                                                System.out.println("Enter the quantity of this product");
                                                int qty = scanner.nextInt();
                                                scanner.nextLine();
                                                System.out.println("Enter the price of this product");
                                                double price = scanner.nextDouble();
                                                scanner.nextLine();
                                                sellerList.get(i).getStores().get(storeNum - 1).addProduct(new Product(newProdName, sellerList.get(i).getStores().get(storeNum - 1).getStoreName(), description, qty, price, 0, new ArrayList<String>()));
                                                System.out.println("Added product successfully!");


                                            } else if (modifyStoreChoice == 2) {
                                                System.out.println("Enter the name of the product you would like to " +
                                                        "remove");
                                                String rmvProdName = scanner.nextLine();
                                                boolean prodFound = false;
                                                for (int k = 0; k < sellerList.get(i).getStores().get(storeNum - 1).getProducts().size(); k++) {
                                                    if (sellerList.get(i).getStores().get(storeNum - 1).getProducts().get(k).getProductName().equals(rmvProdName)) {
                                                        prodFound = true;
                                                        sellerList.get(i).getStores().get(storeNum - 1).getProducts().remove(k);
                                                        System.out.println("Removed product successfully!");
                                                    }
                                                }
                                                if (!prodFound) {
                                                    System.out.println("Could not find specified product!");
                                                }



                                            } else if (modifyStoreChoice == 3) {
                                                System.out.println("Enter the name of the product you would like to " +
                                                        "modify");
                                                String changeProdName = scanner.nextLine();
                                                boolean prodFound = false;
                                                for (int k = 0; k < sellerList.get(i).getStores().get(storeNum - 1).getProducts().size(); k++) {
                                                    if (sellerList.get(i).getStores().get(storeNum - 1).getProducts().get(k).getProductName().equals(changeProdName)) {
                                                        System.out.println("Enter the new description");
                                                        String description = scanner.nextLine();
                                                        System.out.println("Enter the new quantity");
                                                        int qty = scanner.nextInt();
                                                        scanner.nextLine();
                                                        double price = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        sellerList.get(i).editProduct(sellerList.get(i).getStores().get(storeNum - 1).getProducts().get(k), changeProdName, sellerList.get(i).getStores().get(storeNum - 1).getStoreName(), description, qty, price);
                                                        System.out.println("Edited product successfully!");
                                                    }

                                                }
                                                if (!prodFound) {
                                                    System.out.println("Could not find specified product!");
                                                }


                                            } else if (modifyStoreChoice == 4) {
                                                System.out.println("Enter the file path from which you would like to " +
                                                        "import products: ");
                                                String importPath = scanner.nextLine();
                                                ArrayList<Product> importArray = Seller.importProducts(importPath);
                                                boolean errorMsg = false;
                                                for (int k = 0; k < importArray.size(); k++) {
                                                    //Checking if kth products product name does not already exist
                                                    //Since product name must be a unique identifier
                                                    boolean productExistsAlready = false;
                                                   for (int j = 0; j < sellerList.get(i).getStores().get(storeNum - 1).getProducts().size(); j++) {
                                                       if (sellerList.get(i).getStores().get(storeNum - 1).getProducts().get(j).getProductName().equals(importArray.get(k).getProductName())) {
                                                           productExistsAlready = true;
                                                           errorMsg = true;
                                                       }

                                                   }
                                                   if (!productExistsAlready) {
                                                       sellerList.get(i).getStores().get(storeNum - 1).addProduct(importArray.get(k));
                                                   }
                                                }
                                                System.out.println("Imported products successfully");
                                                if (errorMsg) {
                                                    System.out.println("One or more of the products on the file " +
                                                            "already exists in the store and could not be imported");
                                                    System.out.println("Choose the modify product information option " +
                                                            "on the menu to change product info");
                                                }

                                            } else if (modifyStoreChoice == 5) {
                                                System.out.println("Enter the file path to which you would like to " +
                                                        "export the products of this store");
                                                String exportPath = scanner.nextLine();
                                                if (Seller.exportProducts(exportPath,
                                                        sellerList.get(i).getStores().get(storeNum - 1).getProducts())) {
                                                    System.out.println("Exported product info successfully!");
                                                } else {
                                                    System.out.println("There was an error writing to the file!");
                                                }

                                            }
                                        }
                                    }
                                } else if (sellerChoice == 2) {
                                    System.out.println("Enter the store name:");
                                    String newStoreName = scanner.nextLine();
                                    boolean storeNameExists = false;
                                    //Making sure storeName does not already exist
                                    for (int k = 0; k < marketPlace.size(); k++) {
                                        if (marketPlace.get(k).getStoreName().equals(newStoreName)) {
                                            System.out.println("Error! This store name already exists!");
                                            storeNameExists = true;
                                        }
                                    }
                                    if (!storeNameExists) {
                                        System.out.println("Enter the file path to the store");
                                        String filePathToStore = scanner.nextLine();
                                        /**System.out.println("Enter the existing revenue for this store");
                                        int currentRevenue = scanner.nextInt();
                                        scanner.nextLine(); **/
                                        marketPlace.add(new Store(user, newStoreName, filePathToStore));
                                        sellerList.get(i).addStore(new Store(user, newStoreName, filePathToStore));

                                        System.out.println("Added store successfully!");
                                    }


                                } else if (sellerChoice == 3) {
                                    boolean storeRemoved = false;
                                    System.out.println("Enter the name of the store you would like to remove ");
                                    String rmvStoreName = scanner.nextLine();
                                    for (int k = 0; k < sellerList.get(i).getStores().size(); k++) {
                                        if (sellerList.get(i).getStores().get(k).getStoreName().equals(rmvStoreName)) {
                                            sellerList.get(i).getStores().remove(k);
                                            //Remove from marketplace as well
                                            for (int j = 0; j < marketPlace.size(); j++) {
                                                if (marketPlace.get(j).getStoreName().equals(rmvStoreName)) {
                                                    marketPlace.remove(j);
                                                }
                                            }
                                            System.out.println("Store removed successfully!");
                                            storeRemoved = true;
                                        }
                                    }
                                    if (!storeRemoved) {
                                        System.out.println("Could not find specified store!");
                                    }
                                }

                            }
                        }
                    }


                } else if (choice == 2) {
                    System.out.println("Enter store for which you would like to view dashboard");
                    String dashboardStore = scanner.nextLine();
                    boolean storeFound = false;
                    for (int h = 0; h < sellerList.size(); h++ ) {
                        if (sellerList.get(h).getUsername().equals(user)) {
                            for (int i = 0; i < sellerList.get(h).getStores().size(); i++) {
                                if (sellerList.get(h).getStores().get(i).getStoreName().equals(dashboardStore)) {
                                    storeFound = true;
                                    System.out.println("Store name: " + dashboardStore);
                                    System.out.println("Store revenue: " + sellerList.get(h).getStores().get(i).getStoreRevenue());
                                    for (int b = 0; b < sellerList.get(h).getStores().get(i).getProducts().size(); b++) {
                                        System.out.println("----------------");
                                        System.out.println("Product Number :" + (b + 1));
                                        System.out.println(sellerList.get(h).getStores().get(i).getProducts().get(b).getStatistics());
                                    }



                                }



                            }

                        }
                    }



                } else if (choice == 3 ) {
                    System.out.println("----------------");

                    for (int p = 0; p < sellerList.size(); p++) {
                        if (sellerList.get(p).getUsername().equals(user)) {
                            for (int i = 0; i < buyerList.size(); i++) {
                                for (int j = 0; j < buyerList.get(i).getShoppingCart().size(); j++) {
                                    if (buyerList.get(i).getShoppingCart().get(j).getStoreName().equals(sellerList.get(p).getUsername())) {
                                        System.out.println(buyerList.get(i).getUsername() + " has the item " + buyerList.get(i).getShoppingCart().get(j).getProductName() + "from your store " + buyerList.get(i).getShoppingCart().get(j).getStoreName());
                                    }
                                }
                            }

                        }
                    }


                } else if (choice == 4) {
                    loggedInAsSeller = false;
                }

            }

        }
        //Farewell message
        System.out.println("Goodbye! Thank you for using our marketplace");

        //Since all changes in menu interactions make changes to sellerList in place need to make changes reflect in
        // marketplace array list as well
        //Make changes made in seller store array reflect in marketplace array TODO
        for (int i = 0; i < sellerList.size(); i++) {
            if (sellerList.get(i).getUsername().equals(user)) {
                for (int j = 0; j < sellerList.get(i).getStores().size(); j++) {
                    for (int k = 0; k < marketPlace.size(); k++) {
                        if (marketPlace.get(k).getStoreName().equals(sellerList.get(i).getStores().get(j).getStoreName())) {
                            marketPlace.get(k).setProducts(sellerList.get(i).getStores().get(j).getProducts());
                        }
                    }
                }
            }
        }
        //Write back onto seller.txt, buyer.txt and storeFileInfo.txt
        try {
            File f = new File ("seller.txt");
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < sellerList.size(); i++) {
                pw.println(sellerList.get(i).getUsername() + ";" + sellerList.get(i).getPassword() + ";" + sellerList.get(i).getEmail() + ";" + sellerList.get(i).getFilePath());
            }
            pw.flush();
            pw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


        //Write back onto buyer.txt
        try {
            File f = new File("buyer.txt");
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < buyerList.size(); i++) {
                pw.println(buyerList.get(i).getUsername() + ";" + buyerList.get(i).getPassword() + ";" + buyerList.get(i).getEmail() + ";" + buyerList.get(i).getCart() + ";" + buyerList.get(i).getHistory() );


            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write back into storeListFile.txt
        try {
            File f = new File("storeListFile.txt");
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0 ; i < marketPlace.size(); i++) {
                pw.println(marketPlace.get(i).getSellerName() + ";" + marketPlace.get(i).getStoreName() + ";" + marketPlace.get(i).getStoreRevenue() + ";" + marketPlace.get(i).getFilePath());
                pw.flush();
                File loopf = new File(marketPlace.get(i).getFilePath());
                if (!loopf.exists()) {
                    loopf.createNewFile();

                }
                PrintWriter loopPw = new PrintWriter(loopf);
                for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                    loopPw.println(marketPlace.get(i).getProducts().get(j).fileString());
                    loopPw.flush();
                }
                pw.close();
                loopPw.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
