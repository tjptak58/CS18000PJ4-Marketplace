import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> usernameArrayList = new ArrayList<>();
        ArrayList<String> passwordArrayList = new ArrayList<>();
        HashMap<String, String> login1 = new HashMap<String, String>();
        System.out.println("Welcome");
        boolean flag=false;
        // if you create an account then go back to beginning and ask if they want to create a new account or login with existing one
        do{
            flag=false;
            // ask if they have an exisiting or new account
            System.out.println("Do you have an existing account or would you like to create a new account?");
        System.out.println("1. Existing account\n2.Create new account");
        String newOrExisting = scanner.nextLine();
        // if 2 create a new account and ask if they are seller or buyer
        if (newOrExisting.equals("2")) {
            System.out.println("Are you a seller or buyer");
            String sellOrBuy = scanner.nextLine();
            // if they are seller ask username password and what stores they would like to sell through
            if (sellOrBuy.equalsIgnoreCase("Seller")) {
                System.out.println("Enter your new username");
                String username = scanner.nextLine();
                usernameArrayList.add(username);
                System.out.println("Enter your new password");
                String password = scanner.nextLine();
                passwordArrayList.add(password);
                login1.put(username, password);
                System.out.println("Enter the stores (separate each store by a comma(,))");
                String stores = scanner.nextLine();
                if (stores.contains(",")) {
                    String[] storesSplitByComma = stores.split(",");
                }
                System.out.println("Account made!");

            } else if (sellOrBuy.equalsIgnoreCase("Buyer")) {
                System.out.println("Enter your new username");
                String username = scanner.nextLine();
                usernameArrayList.add(username);
                System.out.println("Enter your new password");
                String password = scanner.nextLine();
                passwordArrayList.add(password);
                login1.put(username, password);
                System.out.println("Account made!");
            }
            flag=true;
            // if 1 is input then you try to login
            
        } else if (newOrExisting.equals("1")) {
            
            System.out.println("Enter your username");
            String username = scanner.nextLine();
            // if username is not located in hasmap then show an error
            if (login1.containsKey(username)) {
                System.out.println("Enter your password");
                String password = scanner.nextLine();
                // if password and username are not in hashmap, say password is incorrect
                if (login1.containsKey(username) && login1.containsValue(password)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Password is incorrect");
                }
            } else {
                System.out.println("Username not found");
            }


        }
        // for when account is created so user can go back to beginning
    } while (flag);
    }
}
