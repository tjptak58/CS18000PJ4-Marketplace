import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;


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

public class MarketplaceServer implements Runnable {
    Socket socket;
    static ArrayList<Buyer> buyerArrayList = new ArrayList<>();
    static ArrayList<Seller> sellerArrayList = new ArrayList<>();
    static ArrayList<Store> marketPlace = new ArrayList<>();
    static ArrayList<Product> superListOfProducts = new ArrayList<>();
    //Creating a gatekeeper object
    Object objectForBuyerListModification = new Object();

    //Constructor for this class
    public MarketplaceServer(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        //FileReader for buyer.txt
        File bf = new File("buyer.txt");
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
            //Adding store to marketplace
            marketPlace.add( new Store (storeArray[0], storeArray[1], storeFile, storeProducts, Double.parseDouble(storeArray[2])));
            storeLine = blfr.readLine();
        }
        //When main main method runs for the first time all static variables are initialized before entering infinite while loop

        ServerSocket serverSocket = new ServerSocket(4242);
        while (true) {
            Socket socket = serverSocket.accept();
            MarketplaceServer server  = new MarketplaceServer(socket);
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
                if (keyWord.equals("GETSUPERNAMES")) {
                    //Send ArrayList of Strings containing all product names in marketplace
                    ArrayList<String> arrayListToSend = new ArrayList<>();
                    for (int j = 0; j < marketPlace.size(); j++) {
                        for (int k = 0; k < marketPlace.get(j).getProducts().size(); k++) {
                            arrayListToSend.add(marketPlace.get(j).getProducts().get(k).getProductName());

                        }
                    }
                    //Send ArrayList back to client
                    oos.writeObject(arrayListToSend);
                    oos.flush(); //FLUSHING!!!

                } else if (keyWord.equals("ADDTOCART")) {
                     //Listen for productname
                     //Listen for storename
                     //Listen for quantity
                     //LISTEN FOR username
                     String productName = in.nextLine(); //STRING!!
                     String userName = in.nextLine(); //STRING!!
                     int quantity = in.nextInt(); //INT!!

                     in.nextLine();
                     for (int i = 0; i < buyerArrayList.size(); i++) {
                        //Search for matching username in the arraylist and update cart where username is a match
                        if (buyerArrayList.get(i).getUsername().equals(userName)) {

                        }
                     }


 

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
                                    String priceString = String.format("%d", marketPlace.get(i).getProducts().get(j).getPrice());
                                    writeInfo.add(priceString);
                                    try {
                                        oos.writeObject(writeInfo);
                                        oos.flush();

                                    } catch (IOException e) {
                                        //WHAT TO DO HERE?

                                    }
                                    

                                }
                            }
                        }
                    }
                    //Use ObjectOutputStream to send ArrayList back to client



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




                } else if (keyWord.equals("VIEWCART")) {
                    //Listen for username
                    //Return shopping cart for that username as ArrayList<String>
                    String userNameViewCart = in.nextLine();
                    
                    //Scroll down to see method viewCart
                    ArrayList<String> viewCartList = viewCart(userNameViewCart);
                    //ArrayList of strings "ProductName;QuantityOfThatProductInCart"
                    oos.writeObject(viewCartList);
                    oos.flush(); //FLUSHING!!!


                    

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
                            }

                        }
                    }


                } else if (keyWord.equals("GETACCOUNTINFO")) {
                    //Listen for username
                    //Returns ArrayList<String>
                    String viewAccUserName = in.nextLine();
                    ArrayList<String> sendInfoList = new ArrayList<>();
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(viewAccUserName)) {
                            sendInfoList.add(buyerArrayList.get(i).getEmail());
                            sendInfoList.add(buyerArrayList.get(i).getPassword());
                        }
                    }
                    oos.writeObject(sendInfoList);
                    oos.flush(); //FLUSHING!!!

                } else if (keyWord.equals("UPDATEACCOUNTINFO")) {
                    //Listen for username
                    //Listen for ArrayList<String>
                    String updateUserName = in.nextLine();
                    ArrayList<String> newInfo = null; //ASSUMING FIRST STRING IS NEW EMAIL AND SECOND STRING IS NEW PASSWORD TODO !!!
                    try {
                        newInfo = (ArrayList<String>) ois.readObject();

                    } catch (Exception e) {
                        System.out.println("Class Not Found!"); //Will never reach here since class will always be defined
                    }
                    //Updating info
                    for (int i = 0; i < buyerArrayList.size(); i++) {
                        if (buyerArrayList.get(i).getUsername().equals(updateUserName)) {
                            synchronized (objectForBuyerListModification) {
                                buyerArrayList.get(i).setEmail(newInfo.get(0));
                                buyerArrayList.get(i).setPassword(newInfo.get(2));
                            }
                        }
                    }
                    



                } else if (keyWord.equals("")) {
                    
                }
                
            }

            //If reached here then client has logged out or closed the application
            //Call writeToFiles method here

            




        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server Error!");
        }


    };

    public synchronized void writeToFiles(ArrayList<Buyer> buyerArrayList, ArrayList<Seller> sellerArrayList, ArrayList<Store> storeArrayList) {


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
        //cartListUnique is a arrayList of unique items in the cart
        ArrayList<String> viewCartList  = new ArrayList<>();
        for (String uniqueS: cartListUnique) {
            viewCartList.add(String.format(uniqueS + ";%d", Collections.frequency(cartListDuplicate, uniqueS)));
        }

        return viewCartList;

    }

}