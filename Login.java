import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;

// array list of buyers or sellers and then add the attributes for example
// seller add username, passwords, stores, filepath
// check if username does not exist
// check if username is there in txt file at index 0
// if line at index 0 contains username
public class Login {
     ;
    public static void main(String[] args) {
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
                        usernameAlreadyExists=false;
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
                    System.out.println("Enter the stores (separate each store by a comma(,))");
                    String stores = scanner.nextLine();
                    System.out.println("What is the filepath?");
                    String filePath = scanner.nextLine();

                    Collections.addAll(sellerArrayList, usernameSeller, sellerPassword, email, stores, filePath);
                   // Seller seller = new Seller(usernameSeller, password);

                    if (stores.contains(",")) {
                        String[] storesSplitByComma = stores.split(",");
                    }
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
                        usernameAlreadyExists=false;
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
                    System.out.println("What is the filepath for the account?");
                    String fileName = scanner.nextLine();
                    buyerLogin.put(usernameBuyer, buyerPassword);
                    System.out.println("Account made!");
                    Collections.addAll(buyerArrayList, usernameBuyer, buyerPassword, email, fileName);


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
                        if (usernameAndPasswordSeller.get(i).substring(0, usernameAndPasswordSeller.get(i).indexOf(";")).contains(username)) {
                            System.out.println("Enter your password");
                            String password = scanner.nextLine();
                            for (int j = 1; j < usernameAndPasswordSeller.size(); j++) {
                                String[] split= usernameAndPasswordSeller.get(i).split(";");
                                String passwordTrim=split[1].trim();


                                if (passwordTrim.contains(password)) {
                                    System.out.println("Login successful!");
                                       loginFailed=false;
                                       flag=false;
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

                } else if (sellerOrBuyer.equalsIgnoreCase("Buyer")) {
                    System.out.println("Enter your username");
                    String username = scanner.nextLine();
//                    // of the array list username, password. If 0 index which is username contains username go to next step

                    for (int i = 0; i < usernameAndPasswordBuyer.size(); i++) {
                        if (usernameAndPasswordBuyer.get(i).substring(0, usernameAndPasswordSeller.get(i).indexOf(";")).contains(username)) {
                            System.out.println("Enter your password");
                            String password = scanner.nextLine();
                            for (int j = 1; j < usernameAndPasswordBuyer.size(); j++) {
                                String[] split= usernameAndPasswordSeller.get(i).split(";");
                                String passwordTrim=split[1].trim();
                                if (passwordTrim.contains(password)) {
                                    System.out.println("Login successful!");
                                        loginFailed=false;
                                        flag=false;

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

    }

    public void deleteBuyer(ArrayList<Buyer> buyers, String usernameBuyer, String buyerPassowrd, String filepath) {
        Buyer buyer=new Buyer(usernameBuyer, buyerPassowrd, filepath);
        for (int i = 0; i < buyers.size() ; i++) {
            if (buyer.getUsername().equals(usernameBuyer)) {
                buyers.remove(buyer);
            }
        }
    }
    public void deleteSeller(ArrayList<Seller> sellers, String usernameSeller, String sellerPassword, String filepath) {
        Seller seller=new Seller(usernameSeller, sellerPassword, filepath);
        for (int i = 0; i < sellers.size() ; i++) {
            if (seller.getUsername().equals(usernameSeller)) {
                sellers.remove(seller);
            }
        }
    }
    public void createSeller(ArrayList<Seller> sellers, String usernameSeller, String sellerPassword, String email, String filepath) {
        Seller seller=new Seller(usernameSeller, sellerPassword, email, filepath);
        sellers.add(seller);

    }
    public void createBuyer(ArrayList<Buyer> buyers, String usernameBuyer, String buyerPassword,String email, String filepath) {
        Buyer buyer=new Buyer(usernameBuyer, buyerPassword, email, filepath);
        buyers.add(buyer);
    }
}

// write file to buyer or seller depending and make an array list of username and password.
// For login read through username ArrayList and then password Arraylist
// Do I want to prompt until there is a valid username or password or could I go back to beginning and ask if they want to create an account
// If login is unsuccessful create boolean variable in do while that goes back to create account (set flag to true)

// create account for buyer add username password filepath add to array list
