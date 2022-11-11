import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class ClassWithMainMethod {

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

    public void printMarketPlace (ArrayList<Store> marketPlace) {
        for (int i = 0; i < marketPlace.size(); i++) {
            System.out.println("--------------------");
            Store loopStore = marketPlace.get(i);
            for (int j = 0; j < loopStore.getProducts().size(); j++) {
                System.out.println(loopStore.getProducts().get(j).toString());
            }


        }

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
                String sellerName = line.substring(0, line.indexOf(';'));
                line = line.substring(line.indexOf(';') + 1);
                String storeName = line.substring(0, line.indexOf(';'));
                line = line.substring(line.indexOf(';') + 1);
                String storeFilePath = line;
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
                Store loopStore = new Store(sellerName, storeName, storeFilePath, productsInStore );
                marketPlace.add(loopStore);






            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        //Read from users.txt to create an array of buyer objects
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






        boolean loggedInAsBuyer = true;
        boolean loggedInAsSeller = false;
        if (loggedInAsBuyer) {
            for (int i = 0; i < buyerList.size(); i ++) {
                if (buyerList.get(i).getUsername().equals(user)) {
                    buyerList.get(i).modify();
                }
            }
            while (loggedInAsBuyer) {
                System.out.println("Welcome to the marketplace!");
                System.out.println("1. View product listings");
                System.out.println("2. View shopping cart");
                System.out.println("3. Sort and view items by price");
                System.out.println("4. Sort and ");
                System.out.println("3. Logout");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {

                } else if (choice == 2) {

                } else if (choice == 3) {

                }


            }


        }

        if (loggedInAsSeller) {
            while (loggedInAsSeller) {
                System.out.println("Welcome to the marketplace");
                System.out.println("1. View your stores");
                System.out.println("2. Add a store");
                System.out.println("3. Delete a store");
                System.out.println("4. ");

            }

        }
    }
}
