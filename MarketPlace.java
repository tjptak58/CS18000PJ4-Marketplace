import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class MarketPlace {

    static String user;



    public void deleteBuyer(ArrayList<Buyer> buyers, String usernameBuyer, String buyerPassowrd, String filepath, String email) {
        Buyer buyer = new Buyer(usernameBuyer, buyerPassowrd, filepath, email);
        for (int i = 0; i < buyers.size(); i++) {
            if (buyer.getUsername().equals(usernameBuyer)) {
                buyers.remove(buyer);
            }
        }
    }

    public void deleteSeller(ArrayList<Seller> sellers, String usernameSeller, String sellerPassword, String filepath, String email) {
        Seller seller = new Seller(usernameSeller, sellerPassword, filepath, email);
        for (int i = 0; i < sellers.size(); i++) {
            if (seller.getUsername().equals(usernameSeller)) {
                sellers.remove(seller);
            }
        }
    }

    public void createSeller(ArrayList<Seller> sellers, String usernameSeller, String sellerPassword, String email, String filepath) {
        Seller seller = new Seller(usernameSeller, sellerPassword, email, filepath);
        sellers.add(seller);

    }

    public void createBuyer(ArrayList<Buyer> buyers, String usernameBuyer, String buyerPassword, String email, String filepath) {
        Buyer buyer = new Buyer(usernameBuyer, buyerPassword, email, filepath);
        buyers.add(buyer);
    }

    public  static void printMarketPlace (ArrayList<Product> superListOfProducts) {
        for (int i = 0; i <superListOfProducts.size(); i++) {
            System.out.println("--------------------");
            System.out.println(superListOfProducts.get(i).toString());


        }

    }

    public ArrayList<Product> processStoreFile(String filePath) {

    }


    /*public Product processFileString(String fileLine) {
        //Process fileLine to get a product object

    }*/

    public static void main(String[] args) {
        /**
         * Loging part
         */
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> sellerArrayList = new ArrayList<>();
        ArrayList<String> buyerArrayList = new ArrayList<>();
        HashMap<String, String> sellerLogin = new HashMap<String, String>();
        HashMap<String, String> buyerLogin = new HashMap<String, String>();
        ArrayList<String> usernameAndPasswordSeller = new ArrayList<>();
        ArrayList<String> usernameAndPasswordBuyer = new ArrayList<>();


        try {
            BufferedReader bfr = new BufferedReader(new FileReader("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Seller.txt"));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                usernameAndPasswordSeller.add(line);
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Buyer.txt"));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                usernameAndPasswordBuyer.add(line);
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Welcome");
        boolean flag;
        boolean loginFailed;
        boolean usernameAlreadyExists;

        // if you create an account then go back to beginning and ask if they want to create a new account or login with existing one
        do {
            flag = false;
            loginFailed = false;
            usernameAlreadyExists = false;
            // ask if they have an exisiting or new account
            System.out.println("Would you like you login or create a new account?");
            System.out.println("1.Login\n2.Create new account");
            String newOrExisting = scanner.nextLine();
            // if 2 create a new account and ask if they are sellerArrayList or buyer
            if (newOrExisting.equals("2")) {
                System.out.println("Are you a Seller or Buyer");
                String sellOrBuy = scanner.nextLine();
                // if they are sellerArrayList ask username password and what stores they would like to sell through
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
                                if (usernameAndPasswordSeller.get(i).substring(0, usernameAndPasswordSeller.get(i).indexOf(";")).contains(usernameSeller)) {
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
                    String statisticsFilepath=scanner.nextLine();

                    Collections.addAll(sellerArrayList, usernameSeller, sellerPassword, email,  statisticsFilepath);
                    // Seller seller = new Seller(usernameSeller, password);


                    System.out.println("Account made!");

                    try {
                        FileOutputStream fos = new FileOutputStream("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Seller.txt", true);
                        PrintWriter pw = new PrintWriter(fos);
                        //BufferedWriter bfw = new BufferedWriter
                        //        (new FileWriter("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Seller.txt"));
                        pw.write(usernameSeller + "; " + sellerPassword + "\n");
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
                        for (int i = 0; i < usernameAndPasswordBuyer.size(); i++) {
                            if (usernameAndPasswordBuyer.get(i).substring(0, usernameAndPasswordSeller.get(i).indexOf(";")).contains(usernameBuyer)) {
                                System.out.println("Error: Username already exists. Pick a new username");
                                usernameAlreadyExists = true;
                            }
                        }

                    } while (usernameAlreadyExists);

                    System.out.println("Enter your new password");
                    String buyerPassword = scanner.nextLine();
                    System.out.println("Enter your e-mail");
                    String email = scanner.nextLine();
                    buyerLogin.put(usernameBuyer, buyerPassword);
                    System.out.println("What is the filepath to the purchase history of this account?");
                    String purchaseHistoryFilepath=scanner.nextLine();
                    System.out.println("What is the filepath to the shopping cart for this account?");
                    String shoppingCartFilepath=scanner.nextLine();
                    System.out.println("Account made!");
                    Collections.addAll(buyerArrayList, usernameBuyer, buyerPassword, email, purchaseHistoryFilepath, shoppingCartFilepath);


                    try {
                        FileOutputStream fos = new FileOutputStream("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Buyer.txt", true);
                        PrintWriter pw = new PrintWriter(fos);
                        //  BufferedWriter bfw = new BufferedWriter
                        //          (new FileWriter("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Buyer.txt"));
                        pw.write(usernameBuyer + "; " + buyerPassword + "\n");
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

                    System.out.println("Enter your username");
                    String username = scanner.nextLine();
//                    // of the array list username, password. If 0 index which is username contains username go to next step
                    for (int i = 0; i < usernameAndPasswordSeller.size(); i++) {
                        boolean repeat = true;
                        while (repeat){


                            if (usernameAndPasswordSeller.get(i).substring(0, usernameAndPasswordSeller.get(i).indexOf(";")).contains(username)) {
                                repeat=false;
                                System.out.println("Enter your password");
                                String password = scanner.nextLine();
                                for (int j = 1; j < usernameAndPasswordSeller.size(); j++) {

                                    String[] split = usernameAndPasswordSeller.get(i).split(";");
                                    String passwordTrim = split[1].trim();


                                    if (passwordTrim.contains(password)) {
                                        System.out.println("Login successful!");
                                        loginFailed = false;
                                        flag = false;



                                        //boolean goIntoElse=false;
                                    } else {
                                        System.out.println("Error: Password is incorrect");
                                        loginFailed = true;

                                    }
                                }
                            } else {
                                System.out.println("Username not found");
                                loginFailed = true;

                            }
                        }


                    }

                } else if (sellerOrBuyer.equalsIgnoreCase("Buyer")) {
                    System.out.println("Enter your username");
                    String username = scanner.nextLine();
//                    // of the array list username, password. If 0 index which is username contains username go to next step

                    for (int i = 0; i < usernameAndPasswordBuyer.size(); i++) {
                        if (usernameAndPasswordBuyer.get(i).substring(0, usernameAndPasswordSeller.get(i).indexOf(";")).contains(username)) {
                            System.out.println("Enter your password");
                            String password = scanner.nextLine();
                            for (int j = 1; j < usernameAndPasswordBuyer.size(); j++) {
                                String[] split = usernameAndPasswordSeller.get(i).split(";");
                                String passwordTrim = split[1].trim();
                                if (passwordTrim.contains(password)) {
                                    System.out.println("Login successful!");
                                    loginFailed = false;
                                    flag = false;
                                    break;

                                } else {
                                    System.out.println("Error: Password is incorrect");
                                    loginFailed = true;
                                }
                            }
                        } else {
                            System.out.println("Username not found");
                            loginFailed = true;
                        }
                    }


                }
            }
            // for when account is created so user can go back to beginning
        } while (flag || loginFailed);

        /**
         * Login part of program must return a static string user
         * which is username of buyer if logged in as buyer
         * and is username of seller if logged in as seller
         *
         * Login part must also set loggedInAsBuyer or loggedInAsSeller to true
         *
         *
         *
         */

        //ArrayList of Stores that stores information from storeListFile
        ArrayList<Store> marketPlace = new ArrayList<>();


        /**
         *Read from storeListFile in the beginning of the program to create
         * store objects and add them to marketPlace
         *  ArrayList
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
                String storeFilePath = line.substring(0, line.indexOf(';'));
                line = line.substring(line.indexOf(';') + 1);
                double storeRevenue = Double.parseDouble(line);
                File loopFile = new File(storeFilePath);
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

                }
                //End of storeFile
                Store loopStore = new Store(sellerName, storeName, storeFilePath, productsInStore, storeRevenue );
                marketPlace.add(loopStore);






            }

        } catch (IOException e) {
            e.printStackTrace();

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

        //Read from buyer.txt to create an array of buyer objects
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
                Buyer buyer = new Buyer(username, password, email, filePathToShoppingCart, filePathToPurchaseHistory);
                buyerList.add(buyer);
                //Initialize new buyer object

            }

        } catch (IOException e) {
            e.printStackTrace();

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
        }

        for (int i = 0; i < marketPlace.size(); i++) {
            for (int j = 0; j < sellerList.size(); j++) {
                if (marketPlace.get(i).getSellerName().equals(sellerList.get(j).getUsername())) {
                    sellerList.get(j).addStore(marketPlace.get(i));

                }

            }
        }







        boolean loggedInAsBuyer = true;
        boolean loggedInAsSeller = false;
        if (loggedInAsBuyer) {
            /**for (int i = 0; i < buyerList.size(); i ++) {
                if (buyerList.get(i).getUsername().equals(user)) {
                    buyerList.get(i).modify();
                }
            } //Processing buyer objects format **/
            System.out.println("Welcome to the marketplace!");
            while (loggedInAsBuyer) {

                System.out.println("1. View product listings");
                System.out.println("2. View shopping cart");
                System.out.println("3. View previously purchased items");
                System.out.println("4. Edit your account info");
                System.out.println("5. Logout");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {

                    int secondChoice = -1;
                    while (secondChoice != 7) {
                        MarketPlace.printMarketPlace(superListOfProducts);
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
                            //TODO: Check if product number is valid
                            System.out.println(superListOfProducts.get(productNumber - 1).toString());
                            System.out.println("1. Add to cart");
                            System.out.println("2. Go back");
                            int purchaseChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (purchaseChoice == 1) {
                                //Add to cart
                                for (int i = 0; i < buyerList.size(); i++) {
                                    if (buyerList.get(i).getUsername().equals(user)) {
                                        buyerList.get(i).addToCart(superListOfProducts.get(productNumber - 1));
                                        superListOfProducts.get(productNumber - 1).setQuantity(superListOfProducts.get(productNumber - 1).getQuantity() - 1);
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
                                    //Printout product number as well TODO
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
                                        System.out.println(matchArray.get(productNum - 1).toString());
                                        System.out.println("1. Add to cart");
                                        System.out.println("2. Go back");
                                        int specCartChoice = scanner.nextInt();
                                        if (specCartChoice == 1) {
                                            for (int i = 0; i < superListOfProducts.size(); i++) {
                                                if (superListOfProducts.get(i).equals(matchArray.get(productNum - 1))) {
                                                    for (int j = 0; j < buyerList.size(); j++) {
                                                        if (buyerList.get(j).getUsername().equals(user)) {
                                                            buyerList.get(j).addToCart(superListOfProducts.get(i));
                                                            superListOfProducts.get(i).setQuantity(superListOfProducts.get(i).getQuantity() - 1);
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
                                    for (int j = 0; j < buyerList.get(i).getShoppingCart().size(); j++) {
                                        buyerList.get(i).purchase(buyerList.get(i).getShoppingCart().get(j));

                                    }
                                    //Empty shopping cart after purchase
                                    buyerList.get(i).setShoppingCart(new ArrayList<Product>());
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
                                    //Check for bounds TODO
                                    buyerList.get(i).removeFromCart(buyerList.get(i).getShoppingCart().get(rmvNum - 1));

                                }

                            }




                        }

                    }




                } else if (choice == 3) {
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




                            }

                        }

                    }


                } else if (choice == 2) {

                }

            }

        }

        //Write back onto seller.txt, buyer.txt and storeFileInfo.txt
        try {
            File f = new File ("Seller.txt");
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
                System.out.println(buyerList.get(i).getUsername() + ";" + buyerList.get(i).getPassword() + ";" + buyerList.get(i).getEmail() + ";" + buyerList.get(i).getCart() + ";" + buyerList.get(i).getHistory());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write back into storeListFile.txt
        try {
            File f = new File("storeListFile.txt");
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0 ; i < marketPlace.size(); i++) {
                System.out.println(marketPlace.get(i).getSellerName() + ";" + marketPlace.get(i).getStoreName() + ";" + marketPlace.get(i).getStoreRevenue() + ";" + marketPlace.get(i).getFilePath());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
