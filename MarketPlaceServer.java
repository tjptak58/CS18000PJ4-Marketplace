import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;
/*
 * 12/09/22
 * TODO
 * FileCreating wherever required
 * use gatekeeper object for createaccount part
 * Check for filewriting wherever required
 * 
 */

/**
 * Information on files is shared between server threads so filereading and filewriting methods
 * must be synchronized
 * Information from files stored on server in static variables is also shared and modification must be done
 * using a gatekeeper object
 *
 *
 * KEYWORDS
 * asdasd
 *
 */

public class MarketPlaceServer implements Runnable {
    Socket socket;
    static ArrayList<Buyer> buyerArrayList = new ArrayList<>();
    static ArrayList<Seller> sellerArrayList = new ArrayList<>();
    static ArrayList<Store> marketPlace = new ArrayList<>();
    static ArrayList<Product> superListOfProducts = new ArrayList<>();
    //Creating a gatekeeper object
    Object objectForBuyerListModification = new Object();
    Object objectForSellerListModification = new Object();
    Object objectForMarketPlace = new Object();

    //Constructor for this class
    public MarketPlaceServer(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        //FileReader for buyer.txt
        File bf = new File("buyer.txt");
        //Create file here if does not exist TODO
        if (!bf.exists()) {
            bf.createNewFile();
        }
        FileReader bfr = new FileReader(bf);
        BufferedReader bbfr = new BufferedReader(bfr);
        String buyerLine = bbfr.readLine();
        while(buyerLine != null) {
            String[] buyerArray = buyerLine.split(";");
            //Add to buyerArrayList
            buyerArrayList.add(new Buyer(buyerArray[0], buyerArray[1], buyerArray[2], buyerArray[3], buyerArray[4]));
            buyerLine = bbfr.readLine();

        }


        //FileReader for seller.txt
        File sf = new File("seller.txt");
        //Creating file if does not exist
        if (!sf.exists()) {
            sf.createNewFile();
        }
        FileReader sfr = new FileReader(sf);
        BufferedReader bsfr = new BufferedReader(sfr);
        String sellerLine = bsfr.readLine();
        while (sellerLine != null) {
            String[] sellerArray = sellerLine.split(";");
            //Add to sellerArrayList
            sellerArrayList.add(new Seller(sellerArray[0], sellerArray[1], sellerArray[2], sellerArray[3]));
            sellerLine = bsfr.readLine();
        }

        //FileReader for storeListFile.txt
        File lf = new File("storeListFile.txt");
        //Creating file if does not exist
        if (!lf.exists()) {
            lf.createNewFile();
        }

        
        FileReader lfr = new FileReader(lf);
        BufferedReader blfr = new BufferedReader(lfr);
        String storeLine = blfr.readLine();
        while (storeLine != null) {
            //storeLine has format
            //sellerName;storeName;storeRevenue;filePathToStore
            String[] storeArray = storeLine.split(";");
            String storeFile = storeArray[3];
            File loopFile = new File(storeFile);
            FileReader loopfr = new FileReader(loopFile);
            BufferedReader bloopfr = new BufferedReader(loopfr);
            String productLine = bloopfr.readLine();
            //ArrayList of products for this store
            ArrayList<Product> storeProducts = new ArrayList<>();
            while (productLine != null) {
                
                /*
                 * ProductLine has format
                 * ProductName;StoreName;description;quantity;price;unitsPurchased;customerList
                 */
                String[] productArray = productLine.split(";");
                ArrayList<String> customerList = new ArrayList<>();
                //If a customer has bought the same product multiple times then their name appears multiple times in the ArrayList
                if (productArray[6].indexOf(",") == -1) {
                    if (productArray[6] != null) {
                        //If here then only one customer has purchased the product
                        customerList.add(productArray[6]);

                    }
                } else {
                    //More than one customer has purchased the product
                    String[] customerL = productArray[6].split(",");
                    for (int i = 0; i < customerL.length; i++) {
                        customerList.add(customerL[i]);
                    }

                }
                storeProducts.add(new Product(productArray[0], productArray[1], productArray[2], Integer.parseInt(productArray[3]), Double.parseDouble(productArray[4]), Integer.parseInt(productArray[5]), customerList ));
                
                productLine = bloopfr.readLine();





            }
            String storeLogFile = storeArray[4]; 
            File logf = new File(storeLogFile);
            FileReader frlogf = new FileReader(logf);
            BufferedReader bfrlogf = new BufferedReader(frlogf);
            String storeLogLine = bfrlogf.readLine();
            ArrayList<Purchase> purchaseLogLoopArray = new ArrayList<>();
            while (storeLogLine != null) {
                String[] loopLogArray = storeLogLine.split(";");
                //storeLogLine has format productName;revenue;buyerUserName
                //Creating a new purchase object for this line
                Purchase loopPurchase = new Purchase(loopLogArray[0], Double.parseDouble(loopLogArray[1]), loopLogArray[2]);
                purchaseLogLoopArray.add(loopPurchase);
                storeLogLine = bfrlogf.readLine();

            }

            //Adding store to marketplace
            marketPlace.add( new Store (storeArray[0], storeArray[1], storeFile, storeProducts, Double.parseDouble(storeArray[2]), purchaseLogLoopArray, storeArray[4]));
            storeLine = blfr.readLine();


        }
        //Adding stores to seller objects
        for (int i = 0; i < marketPlace.size(); i++) {
            for (int j = 0; j < sellerArrayList.size(); j++) {
                if (marketPlace.get(i).getSellerName().equals(sellerArrayList.get(j).getUsername())) {
                    sellerArrayList.get(j).addStore(marketPlace.get(i));
                }
            }
        }
        //When main main method runs for the first time all static variables are initialized before entering infinite while loop
        System.out.println("Started server....waiting for connection"); //DEBUGGING
        ServerSocket serverSocket = new ServerSocket(4242);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.printf("Connection received from %s\n", socket); //DEBUGGING
            MarketPlaceServer server  = new MarketPlaceServer(socket);
            new Thread(server).start();
        }






    }
    public void run() {
        try {
            //Creating Scanner object for this socket
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while (true) {
                //Loop until client closes application or logs out
                //Listening for KEYWORDS and sending information back
                //LISTEN FOR WHEN APPLICATION IS CLOSED AND FOR REFRESH!
                
                String keyWord = in.nextLine();
                if (keyWord.charAt(0) == 'y') {
                    keyWord = keyWord.substring(1);
                }
                System.out.println(keyWord); //DEBUGGING
                if (keyWord.equals("GETSUPERSTORES")) {
                    System.out.println("CLIENT CALLED GETSUPERSTORES");
                    //Send ArrayList of Strings containing all product names in marketplace
                    ArrayList<String> arrayListToSend = new ArrayList<>();
                    for (int j = 0; j < marketPlace.size(); j++) {
                        arrayListToSend.add(marketPlace.get(j).getStoreName());
                    }
                    //Send ArrayList back to client
                    oos.writeObject(arrayListToSend);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation

                } else if (keyWord.equals("ADDTOCART")) {
                     //Listen for productname
                     //Listen for storename
                     //Listen for quantity
                     //LISTEN FOR username
                     String productName = in.nextLine(); //STRING!!
                     String storeName = in.nextLine(); //STRING!!
                     int quantity = in.nextInt(); //INT!!
                     in.nextLine(); //Consuming newline char
                     String userName = in.nextLine(); //STRING!!
                     
                     
                     for (int i = 0; i < buyerArrayList.size(); i++) {
                        //Search for matching username in the arraylist and update cart where username is a match
                        if (buyerArrayList.get(i).getUsername().equals(userName)) {
                            for (int j = 0; j < marketPlace.size(); j++) {
                                if (marketPlace.get(j).getStoreName().equals(storeName)) {
                                    for (int k = 0; k < marketPlace.get(j).getProducts().size(); k++) {
                                        if (marketPlace.get(j).getProducts().get(k).getProductName().equals(productName)) {
                                            //Add to cart kth product
                                            //Check for available quantity
                                            if (marketPlace.get(j).getProducts().get(k).getQuantity() < quantity) {
                                                //Send error message to client
                                                pw.println("ERROR");
                                                pw.flush(); //FLUSHING!!!
                                            } else {
                                                pw.println("CONFIRM");
                                                pw.flush(); //FLUSHING!!!
                                                //Add product to cart 'quantity' times 
                                                for (int u = 0; u < quantity; u++) {
                                                    //addToCart method does not reduce quantity of available product by 'quantity'
                                                    //Need to do so seperately
                                                    synchronized (objectForBuyerListModification) {
                                                        buyerArrayList.get(i).addToCart(marketPlace.get(j).getProducts().get(k));

                                                    }
                                                    
                                                    //Reducind available quantity by 1
                                                    synchronized (objectForMarketPlace) {
                                                        marketPlace.get(j).getProducts().get(k).setQuantity(marketPlace.get(j).getProducts().get(k).getQuantity() - 1);


                                                    }
                                                    

                                                }


                                            }
                                        }
                                    }
                                }
                            }

                        }
                     }
                     //Finished implementation
                     //Calling writeToFiles method
                     writeToFiles(buyerArrayList, sellerArrayList, marketPlace);


 

                } else if (keyWord.equals("PRODUCTINFO") ) {
                    //Listen for product name
                    //Listen for store name
                    //Write back ArrayList of info
                    String productName = in.nextLine();
                    String storeName = in.nextLine();
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(storeName)) {
                            for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                                if (marketPlace.get(i).getProducts().get(j).getProductName().equals(productName)) {
                                    ArrayList<String> writeInfo = new ArrayList<>();
                                    writeInfo.add(marketPlace.get(i).getProducts().get(j).getStoreName());
                                    writeInfo.add(marketPlace.get(i).getProducts().get(j).getDescription());
                                    String quantString = String.format("%d", marketPlace.get(i).getProducts().get(j).getQuantity());
                                    writeInfo.add(quantString);
                                    String priceString = String.format("%.2f", marketPlace.get(i).getProducts().get(j).getPrice());
                                    writeInfo.add(priceString);
                                    try {
                                        oos.writeObject(writeInfo);
                                        oos.flush(); //FLUSHING!!!

                                    } catch (IOException e) {
                                        //WHAT TO DO HERE?
                                        System.out.println("SERVER ERROR! WHAT ARE YOU DOINGVGFSFDFADFSDF3e");

                                    }
                                    

                                }
                            }
                        }
                    }
                    //Use ObjectOutputStream to send ArrayList back to client
                    //Finished implementation



                } else if (keyWord.equals("GETPURCHASEHISTORY")) {
                    //Listen for username
                    //Return purchase history of that username as ArrayList<String>
                    String userNameHist = in.nextLine();
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(userNameHist)) {
                            ArrayList<String> sendHistoryList = new ArrayList<>();
                            //Add product names to array list
                            for (int j = 0; j < buyerArrayList.get(i).getPurchased().size(); j++) {
                                sendHistoryList.add(buyerArrayList.get(i).getPurchased().get(j).getProductName());
                            }
                            oos.writeObject(sendHistoryList);
                            oos.flush(); //FLUSHING!!!
                            //NO TRY CATCH HERE

                        }
                    }
                    //Finished implementation




                } else if (keyWord.equals("VIEWCART")) {
                    //Listen for username
                    //Return shopping cart for that username as ArrayList<String>
                    String userNameViewCart = in.nextLine();
                    
                    //Scroll down to see method viewCart
                    ArrayList<String> viewCartList = viewCart(userNameViewCart);
                    //ArrayList of strings "ProductName;StoreNameOfProduct;QuantityOfThatProductInCart"
                    oos.writeObject(viewCartList);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation


                    

                } else if (keyWord.equals("DELETEPRODUCTCART")) {
                    //Listen for productname
                    //Listen for storename
                    //Listen for username
                    //Does not not write back anything
                    String delProductName = in.nextLine();
                    String delStoreName = in.nextLine();
                    String delUserName = in.nextLine();
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(delUserName)) {
                            for (int j = 0; j < buyerArrayList.get(i).getShoppingCart().size(); j++) {
                                if (buyerArrayList.get(i).getShoppingCart().get(j).getProductName().equals(delProductName) && buyerArrayList.get(i).getShoppingCart().get(j).getStoreName().equals(delStoreName)) {
                                    synchronized (objectForBuyerListModification) { //SYNCHRONIZED
                                        buyerArrayList.get(i).removeFromCart(buyerArrayList.get(i).getShoppingCart().get(j));
                                         //Removes from shopping cart array list and writes to files

                                    }
                                    

                                }

                            }
                        }
                    }
                    oos.writeObject(viewCart(delUserName)); //Sends new shopping cart
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);





                } else if (keyWord.equals("PURCHASE")) {
                    //Listen for username
                    //Modify information in server
                    String purchUserName = in.nextLine();
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(purchUserName)) {
                            //Purchase each item in shopping cart
                            for (int j = 0; j < buyerArrayList.get(i).getShoppingCart().size(); j++) {
                                synchronized (objectForBuyerListModification) { //SYNCHRONIZED!!!
                                    buyerArrayList.get(i).purchase(buyerArrayList.get(i).getShoppingCart().get(j));
                                    

                                }
                                //Need to add to stores revenue
                                synchronized (objectForMarketPlace) {
                                    for (int k = 0; k < marketPlace.size(); k++) {
                                        if (marketPlace.get(k).getStoreName().equals(buyerArrayList.get(i).getShoppingCart().get(j).getStoreName())) {
                                            marketPlace.get(k).setStoreRevenue(marketPlace.get(k).getStoreRevenue() + buyerArrayList.get(i).getShoppingCart().get(j).getPrice());


                                        }
                                    }
                                    //Adding to purchaseLog to store for each object in shopping cart
                                    //Outer for loop moves through shopping cart
                                    for (int c = 0; c < marketPlace.size(); c++) {
                                        if (buyerArrayList.get(i).getShoppingCart().get(j).getStoreName().equals(marketPlace.get(c).getStoreName())) {
                                            marketPlace.get(c).addPurchaseLog(new Purchase(buyerArrayList.get(i).getShoppingCart().get(j).getProductName(), buyerArrayList.get(i).getShoppingCart().get(j).getPrice(), buyerArrayList.get(i).getUsername()));
                                            //Write to purchaseLog file of cth store
                                            String cthLogFile = marketPlace.get(c).getFilePathToPurchaseLog();
                                            try {
                                                File loopLogFile = new File (cthLogFile);
                                                //CREATING FILE IF IT DOESNT EXIST
                                                if (!loopLogFile.exists()) {
                                                    loopLogFile.createNewFile();

                                                }
                                                FileWriter logFileWriter = new FileWriter(loopLogFile, true); //APPENDING!!
                                                PrintWriter logPw = new PrintWriter(logFileWriter);
                                                logPw.println(buyerArrayList.get(i).getShoppingCart().get(j).getProductName() + ";" + buyerArrayList.get(i).getShoppingCart().get(j).getPrice() + ";" + buyerArrayList.get(i).getUsername());
                                                logPw.flush(); //FLUSHING!!
                                                logPw.close();

                                            } catch (IOException e) {
                                                System.out.println("Server error! IOException Occured!");
                                            }
                                            


                                        
                                        }
                                    }
                                    
                                }
                            }

                        }
                    }
                    //Finished implementation
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);


                } else if (keyWord.equals("GETACCOUNTINFO")) {
                    //Listen for username
                    //Returns ArrayList<String> with UserName and Password as strings
                    String viewAccUserName = in.nextLine();
                    ArrayList<String> sendInfoList = new ArrayList<>();
                    boolean foundAccount = false;
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(viewAccUserName)) {
                            sendInfoList.add(buyerArrayList.get(i).getEmail());
                            sendInfoList.add(buyerArrayList.get(i).getPassword());
                            //System.out.println(buyerArrayList.get(i).getEmail() + " " + buyerArrayList.get(i).getPassword()); DEBUGGING REMOVE LATER
                            foundAccount = true;

                        }
                    }
                    if (!foundAccount) {
                        //Traverse sellerArrayList
                        //here
                        for (int i = 0; i < sellerArrayList.size(); i++) {
                            if (sellerArrayList.get(i).getUsername().equals(viewAccUserName)) {
                                sendInfoList.add(sellerArrayList.get(i).getEmail());
                                sendInfoList.add(sellerArrayList.get(i).getPassword());
                            }
                        }
                    }
                    oos.writeObject(sendInfoList);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation

                } else if (keyWord.equals("UPDATEACCOUNTINFO")) {
                    //Add implementation for seller too!! TODO
                    System.out.println("client called UPDATEACCOUNTINFO!!!!");
                    //Listen for username
                    //Listen for ArrayList<String>
                    String updateUserName = in.nextLine();
                    ArrayList<String> newInfo = null; //ASSUMING FIRST STRING IS NEW EMAIL AND SECOND STRING IS NEW PASSWORD TODO !!!

                    try {
                        newInfo = (ArrayList<String>) ois.readObject();
                        System.out.println(newInfo.get(0) + " " + newInfo.get(1));
                        System.out.println("RECIEVED NEW INFO !!!!");

                    } catch (Exception e) {
                        System.out.println("Class Not Found!"); //Will never reach here since class will always be defined
                    }
                    //Updating info
                    boolean accFound = false;
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(updateUserName)) {
                            accFound = true;
                            synchronized (objectForBuyerListModification) { //SYNCHRONIZED!!!
                                buyerArrayList.get(i).setEmail(newInfo.get(0));
                                buyerArrayList.get(i).setPassword(newInfo.get(1));
                            }
                        }
                    }
                    if (!accFound) {
                        for (int i = 0; i < sellerArrayList.size(); i++) {
                            if (sellerArrayList.get(i).getUsername().equals(updateUserName)) {
                                synchronized (objectForSellerListModification) {
                                    sellerArrayList.get(i).setEmail(newInfo.get(0));
                                    sellerArrayList.get(i).setPassword(newInfo.get(1));
                                }
                            }
                        }
                    }

                    //Added implementation for seller 9/12/22
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);
                    



                } else if (keyWord.equals("VIEWSTORES")) {
                    //Listen for username
                    //Sends back ArrayList<String> with each StoreName as a string
                    String sellerUserName = in.nextLine();
                    ArrayList<String> sendStores = new ArrayList<>();
                    for (int i = 0; i < sellerArrayList.size(); i++) {
                        if (sellerArrayList.get(i).getUsername().equals(sellerUserName)) {
                            for (int j = 0; j < sellerArrayList.get(i).getStores().size(); j++) {
                                sendStores.add(sellerArrayList.get(i).getStores().get(j).getStoreName());
                            }

                        }
                    }
                    oos.writeObject(sendStores);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation

                } else if (keyWord.equals("VIEWPRODUCTS")) {
                    //Listen for storename
                    //Sends back ArrayList<String> with each product name as a string
                    String storeNameView = in.nextLine();
                    ArrayList<String> sendProducts = new ArrayList<>();
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(storeNameView)) {
                            for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                                sendProducts.add(marketPlace.get(i).getProducts().get(j).getProductName());
                            }
                            
                        }
                    }
                    oos.writeObject(sendProducts);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation

                } else  if (keyWord.equals("DELETESTORE")) {

                    //Listen for storename
                    //Send back ArrayList of strings with new store names
                    String delStoreName = in.nextLine();
                    
                    //Remove store from marketplace and from seller's list of stores
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(delStoreName)) {
                            synchronized (objectForMarketPlace) {
                                marketPlace.remove(i);
                            }

                        }
                    }
                    

                    for (int i = 0; i < sellerArrayList.size(); i++) {
                        for (int j = 0; j < sellerArrayList.get(i).getStores().size(); j++) {
                            if (sellerArrayList.get(i).getStores().get(j).getStoreName().equals(delStoreName)) {
                                //Remove from seller's storelist
                                ArrayList<Store> newStoreList = sellerArrayList.get(i).getStores();
                                newStoreList.remove(sellerArrayList.get(i).getStores().get(j));
                                synchronized (objectForSellerListModification) {
                                    
                                    sellerArrayList.get(i).setStores(newStoreList);
                                    
                                }
                                ArrayList<String> newStoreNameList = new ArrayList<>();
                                for (int k = 0; k < newStoreList.size(); k++) {
                                    newStoreNameList.add(newStoreList.get(k).getStoreName()); //Switched loop variable to k to remove error
                                }
                                oos.writeObject(newStoreNameList);
                                oos.flush(); //FLUSHING!!!
                            }
                        }
                    }
                    //Finished implementation
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);
                    

                } else if (keyWord.equals("ADDSTORE")) {
                    //Listen for storeName
                    String addStoreName = in.nextLine();
                    //LISTEN FOR SELLERNAME TODO!!
                    String addSellerName = in.nextLine();
                    //LiSTEN FOR FILEPATH OF STORE!!!
                    String addStorePath = addStoreName + "StorePath.txt";
                    //wHO IS CHECKING IF FILEPATH EXISTS?
                    //Listen for filePathToPurchaseLog
                    String addStoreLogPath = addStoreName + "StoreLogPath.txt";
                    Store addStore = new Store(addSellerName, addStoreName, addStorePath, addStoreLogPath); 
                    //Add store to marketPlace and to seller's list of stores
                    //PurchaseLog for this new store will be empty

                    //Debugging 
                    System.out.println(marketPlace.size());
                    
                    boolean storeNameExists = false;
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(addStoreName)) {
                            storeNameExists = true;
                        }

                    }

                    if (!storeNameExists) {
                        synchronized (objectForMarketPlace) {
                            marketPlace.add(addStore);
                        }
                        for (int i = 0; i < sellerArrayList.size(); i++) {
                            if (sellerArrayList.get(i).getUsername().equals(addSellerName)) {
                                synchronized (objectForSellerListModification) {
                                    ArrayList<Store> curStoreList = sellerArrayList.get(i).getStores();
                                    curStoreList.add(addStore);
                                    sellerArrayList.get(i).setStores(curStoreList);
                                }
                            }
                        }
                        System.out.println(marketPlace.size() + " : SIZE AFTER ADDING STORE");
                        pw.println("CONFIRM");
                        pw.flush(); //FLUSHING!!!
                        //ArrayList<String> addedList = new ArrayList<>();
                        String sendMarketPlace = "";
                        for (int j = 0; j < marketPlace.size(); j++) {
                            //addedList.add(marketPlace.get(j).getStoreName());
                            sendMarketPlace += marketPlace.get(j).getStoreName() + ";";
                            System.out.println(marketPlace.get(j).getStoreName()); //DEBUGGING
                        }
                        sendMarketPlace = sendMarketPlace.substring(0, sendMarketPlace.length() - 1); //Removes semicolon in the end
                        //oos.writeObject(addedList);
                        //oos.flush();
                        pw.println(sendMarketPlace);
                        pw.flush(); //FLUSHING

                    } else {
                        pw.println("ERROR");
                        pw.flush();
                    }
                    //CHANGED IMPLEMENTATION 12/9/22!!!!
                    //Finished implementation w/ PurchaseLog 
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);


                } else if (keyWord.equals("ADDPRODUCT")) {
                    //Listen for String "ProductName;StoreName;Description;Quantity;Price"
                    //Each string is productfields separated by ;
                    
                
                    String addProductInfO = in.nextLine();  
                    String[] prodArray = addProductInfO.split(";");
                    Product newProd = new Product(prodArray[0], prodArray[1], prodArray[2], Integer.parseInt(prodArray[3]), Double.parseDouble(prodArray[4])  );
                    String storeName = in.nextLine();
                    //Check that each of the product names does not exist already
                    boolean productNameExists = false;
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(storeName)) {
                            for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                                if (marketPlace.get(i).getProducts().get(j).getProductName().equals(newProd.getProductName())) {
                                    productNameExists = true;
                                }
    
                            }

                        }
                        
                    }
                    if (productNameExists) {
                        pw.println("ERROR");
                        pw.flush(); //FLUSHING!!!
                    } else {
                        pw.println("CONFIRM");
                        pw.flush(); //FLUSHING!!!
                        for (int i = 0; i < marketPlace.size(); i++) {
                            if (marketPlace.get(i).getStoreName().equals(storeName)) {
                                synchronized (objectForMarketPlace) {
                                    ArrayList<Product> curProdList = marketPlace.get(i).getProducts();
                                    curProdList.add(newProd);
                                    marketPlace.get(i).setProducts(curProdList);
                                    //SINCE STORE OBJECTS IN MARKETPLACE AND SELLER FIELD ARE THE SAME CHANGES WILL GET REFLECTED AUTOMATICALLY
                                }
                            }
                        }
                    }
                    //Finished implementation
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);





                } else if (keyWord.equals("EDITPRODUCT")) {
                    //Listen for string "productName;storeName;newDescription;newQuantity;newPrice"
                    //WHO IS CHECKING IF QUANTITY IS VALID/NON NEGATIVE INT AND PRICE IS VALID/NON NEGATIVE DOUBLE???? TODO
                    //CANNOT EDIT productName, storeName, unitePurchased, customerList
                    String newProdInfo = in.nextLine();
                    String[] newProdArray = newProdInfo.split(";");
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(newProdArray[1])) {
                            for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                                if (marketPlace.get(i).getProducts().get(j).getProductName().equals(newProdArray[0])) {
                                    synchronized (objectForMarketPlace) {
                                        marketPlace.get(i).getProducts().get(j).setDescription(newProdArray[2]);
                                        marketPlace.get(i).getProducts().get(j).setQuantity(Integer.parseInt(newProdArray[3]));
                                        marketPlace.get(i).getProducts().get(j).setPrice(Double.parseDouble(newProdArray[4]));


                                    }
                                }

                            }
                        }
                    }
                    //Finished implementation w/o 
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);



                } else if (keyWord.equals("DELETEPRODUCT")) {
                    //Listen for product name
                    //Listen for store name
                    String delProductName = in.nextLine();
                    String delStoreName = in.nextLine();
                    
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(delStoreName)) {
                            for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                                if (marketPlace.get(i).getProducts().get(j).getProductName().equals(delProductName)) {
                                    
                                    synchronized (objectForMarketPlace) {
                                        ArrayList<Product> curProdList = marketPlace.get(i).getProducts();
                                        curProdList.remove(marketPlace.get(i).getProducts().get(j));
                                        marketPlace.get(i).setProducts(curProdList);


                                    }
                                    synchronized (objectForSellerListModification) {
                                        for (int k = 0; k < sellerArrayList.size(); k++) {
                                            for (int p = 0; p < sellerArrayList.get(k).getStores().size(); p++) {
                                                for (int c = 0; c < sellerArrayList.get(k).getStores().get(p).getProducts().size(); c++) {
                                                    if (sellerArrayList.get(k).getStores().get(p).getProducts().get(c).getProductName().equals(delProductName)) {
                                                        ArrayList<Product> curSellerProdList = sellerArrayList.get(k).getStores().get(p).getProducts();
                                                        curSellerProdList.remove(sellerArrayList.get(k).getStores().get(p).getProducts().get(c));
                                                        sellerArrayList.get(k).getStores().get(p).setProducts(curSellerProdList);
                                                    }

                                                }
                                            }
                                        }
                                    }
                                    
                                }
                            }
                        }
                    }
                    //Not sending anything back
                    //Finished implementation
                    //Calling writeToFiles method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);

                

                } else if (keyWord.equals("UPDATESTORENAME")) {
                    /** //Listen for old name
                    //Listen for new name
                    String oldStoreName = in.nextLine();
                    String newStoreName = in.nextLine();
                    //Update in marketplace and in seller's list of stores
                    boolean storeNameExists = false;
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(newStoreName)) {
                            storeNameExists = false;
                        }
                    }
                    if (storeNameExists) {
                        pw.println("ERROR");
                        pw.flush(); //FLUSHING!!!
                    } else {
                        pw.println("CONFIRM");
                        for (int p = 0; p < marketPlace.size(); p++) {
                            if (marketPlace.get(p).getStoreName().equals(oldStoreName)) {
                                synchronized (objectForMarketPlace) {
                                    marketPlace.get(p).set
                                }
                            }
                        }

                    } **/
                    //Decided not to implement this



                } else if (keyWord.equals("SALESBYSTORE")) {
                    //Listen for storeName
                    String storeNameDash = in.nextLine();                    
                    //Return purchase log for that store
                    ArrayList<Purchase> logFileForStore = null;
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(storeNameDash)) {
                            logFileForStore = marketPlace.get(i).getPurchaseLog();
                        }
                    }
                    ArrayList<String> sendBackLog = new ArrayList<>();
                    for (int i = 0; i < logFileForStore.size(); i++) {
                        sendBackLog.add(logFileForStore.get(i).getProductName() + ";" + logFileForStore.get(i).getSaleRevenue() + ";" + logFileForStore.get(i).getBuyerUsername());
                    }
                    oos.writeObject(sendBackLog);
                    oos.flush(); //FLUSHING!!!
                    //FINISHED IMPLEMENTATION






                } else if (keyWord.equals("LOGOUT")) {
                    break; //Breaks out of while loop inside run method
                    //Finished implementation
                } else if (keyWord.equals("CONTACTSELLER")) {
                    //Listen for storename
                    //Send back ArrayList<String> with "SellerUsername"and SellerEmail as strings
                    String contactStoreName = in.nextLine();
                    ArrayList<String> contactInfo = new ArrayList<>();
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(contactStoreName)) {
                            contactInfo.add(marketPlace.get(i).getSellerName());
                            //Traverse through sellerArrayList to get emailId of that seller
                            for (int j = 0; j < sellerArrayList.size(); j++) {
                                if (sellerArrayList.get(j).getUsername().equals(marketPlace.get(i).getSellerName())) {
                                    contactInfo.add(sellerArrayList.get(j).getEmail());
                                }
                            }

                        }
                    }
                    oos.writeObject(contactInfo);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation

                } else if (keyWord.equals("GETPRODUCTSINSTORE")) {
                    //Listen for storeName
                    String storeNameForProd = in.nextLine();
                    //Send back ArrayList of ProductNames
                    ArrayList<String> sendBackList = new ArrayList<>();
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(storeNameForProd)) {
                            for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                                sendBackList.add(marketPlace.get(i).getProducts().get(j).getProductName());
                            }

                        }
                    }
                    oos.writeObject(sendBackList);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation

                } else if (keyWord.equals("NUMSALES")) {
                    //Listen for storeName
                    //Listen for productName
                    String salesStoreName = in.nextLine();
                    String salesProdName = in.nextLine();
                    String sendBackInfo = "";
                    //SENDING BACK STRING WITH INFO numberOfUnitsSold;TotalRevenueFromSales
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(salesStoreName)) {
                            for (int j = 0; j < marketPlace.get(i).getPurchaseLog().size(); j++) {


                            }
                        

                        }

                    }

                } else if (keyWord.equals("NUMINCART")) {
                    //Listen for sellerName
                    /*
                     * Sellers can view the number of products currently in customer shopping carts, along with the store and details associated with the products. 
                     */
                    String sellerName = in.nextLine();
                    //ArrayList of strings of each product info
                    //Not implementing with 'quantity' for each product
                    //Will treat each unit as unique //Much easier that way
                    ArrayList<String> sendProdInfo = new ArrayList<>();
                    //Sending ArrayList of strings as "BuyerName;ProductName;StoreName;Price" IS THIS ENOUGH TODO
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        for (int j = 0; j < buyerArrayList.get(i).getShoppingCart().size(); j++) {
                            //For each product in the shopping cart check if it belongs to a store that belongs to the seller
                            for (int k = 0; k < marketPlace.size(); k++) {
                                if (marketPlace.get(i).getStoreName().equals(buyerArrayList.get(i).getShoppingCart().get(j).getStoreName())) {
                                    if (marketPlace.get(i).getSellerName().equals(sellerName)) {
                                        sendProdInfo.add(buyerArrayList.get(i).getUsername() + ";" + buyerArrayList.get(i).getShoppingCart().get(j).getProductName() + ";" + buyerArrayList.get(i).getShoppingCart().get(j).getStoreName() + ";" + buyerArrayList.get(i).getShoppingCart().get(j).getPrice());

                                    }
                                }
                            }
                            
                        }
                    }
                    oos.writeObject(sendProdInfo);
                    oos.flush(); //FLUSHING!!!
                    //Finished implementation w/o TODO

                } else if (keyWord.equals("CREATEACCBUYER")) {
                    String buyerAccUsername = in.nextLine();
                    String buyerAccPassword = in.nextLine();
                    String buyerAccEmail = in.nextLine();
                    //String cart = in.nextLine();
                    //String history = in.nextLine();
                    // Listens for username, password, email, cart, and history of buyer
                    //boolean userExists = false;

                    /*for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerAccUsername.equals(buyerArrayList.get(i).getUsername())) {
                            pw.println("Username Already Exists");
                        } else if (buyerAccEmail.equals(buyerArrayList.get(i).getEmail())) {
                            pw.println("Email Already Exists");
                        } else {
                            buyerArrayList.add(new Buyer(buyerAccUsername, buyerAccPassword, buyerAccEmail, cart, history));
                        }

                    } */
                    
                    boolean userInfoExists = false;
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(buyerAccUsername) || buyerArrayList.get(i).getEmail().equals(buyerAccEmail)) {
                            userInfoExists = true;



                        }
                    } 
                    //Traversing sellerArrayList too
                    for (int i = 0; i < sellerArrayList.size(); i++) {
                        if (sellerArrayList.get(i).getUsername().equals(buyerAccUsername) || sellerArrayList.get(i).getEmail().equals(buyerAccEmail)) {
                            userInfoExists = true;

                        }
                    }
                    if (userInfoExists) {
                        pw.println("ERROR User Information Already Exists");
                        pw.flush();
                    } else {
                        pw.println("CONFIRM");
                        pw.flush();
                        synchronized (objectForBuyerListModification) {
                            buyerArrayList.add(new Buyer(buyerAccUsername, buyerAccPassword, buyerAccEmail, buyerAccUsername + "Cart.txt", buyerAccUsername + "History.txt"));
                        }
                    }


                    pw.flush(); //FLUSHING!!!
                    pw.close();
                    //Calling writeToFile method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);

                } else if (keyWord.equals("CREATEACCSELLER")) {
                    String sellerAccUsername = in.nextLine();
                    String sellerAccPassword = in.nextLine();
                    String sellerAccEmail = in.nextLine();
                    // Listens for username, password, and email of buyer

                    /*for (int i = 0; i < sellerArrayList.size(); i++) {
                        if (sellerAccUsername.equals(sellerArrayList.get(i).getUsername())) {
                            pw.println("Username Already Exists");
                        } else if (sellerAccEmail.equals(sellerArrayList.get(i).getEmail())) {
                            pw.println("Email Already Exists");
                        } 

                    } */
                    boolean userInfoExists = false;
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(sellerAccUsername) || buyerArrayList.get(i).getEmail().equals(sellerAccEmail)) {
                            userInfoExists = true;



                        }
                    } 
                    //Traversing sellerArrayList too
                    for (int i = 0; i < sellerArrayList.size(); i++) {
                        if (sellerArrayList.get(i).getUsername().equals(sellerAccUsername) || sellerArrayList.get(i).getEmail().equals(sellerAccEmail)) {
                            userInfoExists = true;

                        }
                    }
                    if (userInfoExists) {
                        pw.println("ERROR User Information Already Exists");
                        pw.flush();
                    } else {
                        pw.println("CONFIRM");
                        pw.flush();
                        synchronized (objectForSellerListModification) {
                            sellerArrayList.add(new Seller(sellerAccUsername, sellerAccPassword, sellerAccEmail, sellerAccUsername + "FilePath.txt"));
                        }
                    }

                    pw.flush(); //FLUSHING!!!
                    pw.close(); 
                    //Calling writeFile method
                    writeToFiles(buyerArrayList, sellerArrayList, marketPlace);
                    
                } else if (keyWord.equals("LOGINBUYER")) {
                    String buyerUsername = in.nextLine();
                    String buyerPassword = in.nextLine();
                    // Listen for username and password pf buyer

                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerUsername.equals(buyerArrayList.get(i).getUsername()) &&
                                buyerPassword.equals(buyerArrayList.get(i).getPassword())) {
                            pw.println("CONFIRM");
                            // returns "CONFIRM" if username and password does not exist of buyer
                        } else {
                            pw.println("ERROR");
                            // returns "ERROR" is username and password does not exist of buyer
                        }
                    }
                    pw.flush();
                    pw.close();


                } else if (keyWord.equals("LOGINSELLER")) {
                    String sellerUsername = in.nextLine();
                    String sellerPassword = in.nextLine();
                    // Listen for username and password of seller

                    for (int i = 0; i < sellerArrayList.size(); i++) {
                        if (sellerUsername.equals(sellerArrayList.get(i).getUsername()) &&
                                sellerPassword.equals(sellerArrayList.get(i).getPassword())) {
                            pw.println("CONFIRM");
                            // returns "CONFIRM" if username and password does not exist of seller
                        } else {
                            pw.println("ERROR");
                            // returns "ERROR" is username and password does not exist of seller
                        }
                    }
                    pw.flush();
                    pw.close();

                } else if (keyWord.equals("CUSTOMERPURCHASES")) {
                    //Listen for username
                    //Listen for storename
                    String userName = in.nextLine();
                    String storeName = in.nextLine();
                    //Send back int numberOfPurchasesFromThisStoreByUsername
                    int count = 0;
                    for (int i = 0; i < marketPlace.size(); i++) {
                        if (marketPlace.get(i).getStoreName().equals(storeName)) {
                            for (int j = 0; j < marketPlace.get(i).getPurchaseLog().size(); j++) {
                                if (marketPlace.get(i).getPurchaseLog().get(j).getBuyerUsername().equals(userName)) {
                                    count += 1;
                                }

                            }
                        }
                    }
                    pw.println(count);
                    pw.flush(); //FLUSHING!!!
                    //CLIENT MUST LISTEN FOR AN INT AND THEN CONSUME NEW LINE CHAR USING SCANNER!!!

                 }

            }
            
            

            //If reached here then client has logged out or closed the application
            //Call writeToFiles method here
            writeToFiles(buyerArrayList, sellerArrayList, marketPlace);
            System.out.println("Disconnected from server....");

            




        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server Error! Caught Exception in OUTERMOS TRY CATCH BLOCK");
        }

    }   //Closes run method 

    public synchronized void writeToFiles(ArrayList<Buyer> buyerArrayList, ArrayList<Seller> sellerArrayList, ArrayList<Store> storeArrayList) {
        try {
            File bf = new File("buyer.txt");
            /**if (!bf.exists()) {
                bf.createNewFile();
            } **/ //Not required since file creation is done at the beginning of the main method
            FileWriter wbf = new FileWriter(bf, false);
            PrintWriter pwbf = new PrintWriter(wbf);
            for (int i = 0; i < buyerArrayList.size(); i++) {
                pwbf.println(buyerArrayList.get(i).getUsername() + ";" + buyerArrayList.get(i).getPassword() + ";" + buyerArrayList.get(i).getEmail() + ";" + buyerArrayList.get(i).getCart() + ";" + buyerArrayList.get(i).getHistory());
                pwbf.flush();
                //Write to cart and history of buyer too
                File buyerCartFile = new File(buyerArrayList.get(i).getCart());
                FileWriter writerCartFile = new FileWriter(buyerCartFile, false);
                PrintWriter pWriterCartFile = new PrintWriter(writerCartFile);

                for (int j = 0; j < buyerArrayList.get(i).getShoppingCart().size(); j++) {
                    pWriterCartFile.println(buyerArrayList.get(i).getShoppingCart().get(j).fileString()); //CALLING FILESTRING METHOD FOR PRODUCT HERE !!!
                    pWriterCartFile.flush();

                }
                pWriterCartFile.close();

                File buyerHistoryFile = new File(buyerArrayList.get(i).getHistory());
                FileWriter buyerHistoryWriter = new FileWriter(buyerHistoryFile, false);
                PrintWriter pHistoryWriter = new PrintWriter(buyerHistoryWriter);

                for (int j = 0; j < buyerArrayList.get(i).getPurchased().size(); j++) {
                    pHistoryWriter.println(buyerArrayList.get(i).getPurchased().get(j).fileString());
                    pHistoryWriter.flush();
                }
                pHistoryWriter.close();
            }
            
            File sf = new File("seller.txt");
            FileWriter wsf = new FileWriter(sf, false);
            PrintWriter pwsf = new PrintWriter(wsf);
            for (int i = 0; i < sellerArrayList.size(); i++) {
                pwsf.println(sellerArrayList.get(i).getUsername() + ";" + sellerArrayList.get(i).getPassword() + ";" + sellerArrayList.get(i).getEmail() + ";" + sellerArrayList.get(i).getFilePath());
                pwsf.flush();

            }

            File slf = new File("storeListFile.txt");
            FileWriter wslf = new FileWriter(slf, false);
            PrintWriter pwslf = new PrintWriter(wslf);
            for (int i = 0; i < marketPlace.size(); i++) {
                pwslf.println(marketPlace.get(i).getSellerName() + ";" + marketPlace.get(i).getStoreName() + ";" + marketPlace.get(i).getStoreRevenue() + ";" + marketPlace.get(i).getFilePath() + ";" + marketPlace.get(i).getFilePathToPurchaseLog());
                pwslf.flush();
                //Write to the products page and purchase log of each store too
                File loopStoreFile = new File (marketPlace.get(i).getFilePath());
                FileWriter loopStoreWriter = new FileWriter(loopStoreFile, false);
                PrintWriter pwLoopStore = new PrintWriter(loopStoreWriter);
                for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                    pwLoopStore.println(marketPlace.get(i).getProducts().get(j).fileString()); //DOES FILESTRINGMETHOD DO WHAT I WANT??
                    pwLoopStore.flush(); //FLUSHING!!!
                    //here


                }
                pwLoopStore.close();

                File loopLogFile = new File (marketPlace.get(i).getFilePathToPurchaseLog());
                FileWriter loopLogWriter = new FileWriter(loopLogFile, false);
                PrintWriter pwLoopLogWriter = new PrintWriter (loopLogWriter);
                for (int k = 0; k < marketPlace.get(i).getPurchaseLog().size(); k++) {
                    pwLoopLogWriter.println(marketPlace.get(i).getPurchaseLog().get(k).purchaseFileString());
                    pwLoopLogWriter.flush(); //FLUSHING!!!
                    
                }
                pwLoopLogWriter.close();



                

            }

            //Closing all open streams
            pwbf.close();
            pwsf.close();
            pwslf.close();
            

            
        } catch (IOException e) {
            System.out.println("Server Error! Unable to write back to files!");

        } 




    }

    public ArrayList<String> viewCart(String userNameViewCart) {
        ArrayList<String> cartListDuplicate = new ArrayList<>();
        for (int i = 0; i < buyerArrayList.size(); i++) {
            if (buyerArrayList.get(i).getUsername().equals(userNameViewCart)) {
                for (int j = 0; j < buyerArrayList.get(i).getShoppingCart().size(); j++) {
                    cartListDuplicate.add(buyerArrayList.get(i).getShoppingCart().get(j).getProductName());
                }

            }
        }
        ArrayList<String> cartListUnique = new ArrayList<>();
        for (String s: cartListDuplicate) {
            if (cartListUnique.indexOf(s) == -1) {
                cartListUnique.add(s);
            }
        }
        //cartListUnique is an arrayList of unique items in the cart
        ArrayList<String> viewCartList  = new ArrayList<>();
        for (String uniqueS: cartListUnique) {
            for (int i = 0; i < marketPlace.size(); i++) {
                for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                    if (marketPlace.get(i).getProducts().get(j).getProductName().equals(uniqueS)) {
                        viewCartList.add(String.format(uniqueS + ";" + marketPlace.get(i).getProducts().get(j).getStoreName() + ";%d", Collections.frequency(cartListDuplicate, uniqueS)));

                    }
                }
            }
            
        }

        return viewCartList;

    }

}
