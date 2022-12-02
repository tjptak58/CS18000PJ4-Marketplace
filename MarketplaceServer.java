import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


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
    Object object = new Object();

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

            //Listening for KEYWORDS and sending information back
            String keyWord = in.nextLine();
            if (keyWord.equals("GETSUPERNAMES")) {
                //Send ArrayList of Strings containing all product names in marketplace

            } else if (keyWord.equals("ADDTOCART")) {

            } else if (keyWord.equals("PRODUCTINFO") ) {

            } else if (keyWord.equals("GETPURCHASE")) {

            } else if (keyWord.equals("")) {

            }




        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server Error!");
        }


    };

}