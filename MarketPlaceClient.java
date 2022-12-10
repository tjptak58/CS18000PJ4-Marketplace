
import javax.print.event.PrintJobListener;
import javax.swing.*;
import java.io.*;
import java.awt.*; 
import java.awt.event.*;
import java.net.*;
import java.util.*; //I NEED TO CREATE PW,OOS,OIS,SCANNER an close it for each instance we're calling the server

/**
 * Main method for project that implements the marketplace
 *
 * @author
 *
 * @verion 11/14/22
 */

public class MarketPlaceClient extends JComponent implements Runnable {
    /**
     *Static string that stores the username of the logged in buyer or seller
     */
    String username;

    /**
     * Static booleans to determine whether a buyer or seller has logged in
     */
    boolean loggedInAsBuyer;
    boolean loggedInAsSeller;
    Socket socket;
    static int portnumber;
    /* static PrintWriter pw;
    static Scanner in;
    static ObjectOutputStream oos;
    static ObjectInputStream ois; */
    ArrayList<String> myStoreNames = new ArrayList<String>();
    ArrayList<String> productNames = new ArrayList<String>();
    ArrayList<String> superStores = new ArrayList<String>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MarketPlaceClient(portnumber));
    }

    public MarketPlaceClient(int portnumber) {
        MarketPlaceClient.portnumber = portnumber;
    }

    public void run() {
        try {
            //username = "tptak";
            username = "greg";
            socket = new Socket("localhost", 4242); //MarketPlaceClient.portnumber FIX
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); 
            loggedInAsBuyer = false;
            loggedInAsSeller = true;
            if (loggedInAsBuyer) {
                pw.println("GETSUPERSTORES");
                pw.flush();
                superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                productNames.clear();
                for (String store : superStores) {
                    pw.println("GETPRODUCTSINSTORE");
                    pw.println(store);
                    pw.flush();
                    ArrayList<String> productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                    productNames.addAll(productNamesProxy);
                }
                buyerMain(productNames);
            } else if (loggedInAsSeller) {     
                pw.println("VIEWSTORES");
                pw.println(username);
                pw.flush();
                myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWSTORES
                sellerMain(myStoreNames);           
            }
            pw.close();
            in.close();
            oos.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Creates the Home Page for Buyer
     */
    public void buyerMain(ArrayList<String> productNames) {
        JFrame buyerMain = new JFrame("THE MARKETPLACE");
        Container buyerMainPanel = buyerMain.getContentPane();
        buyerMainPanel.setLayout(new BorderLayout());

        JPanel buyerMainNorth = new JPanel(new FlowLayout());
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Refreshes the Home Page
                buyerMain.dispose();
                var productNames = new ArrayList<String>();
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());     
                       
                    pw.println("GETSUPERSTORES");
                    pw.flush();
                    superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                    for (String store : superStores) {
                        pw.println("GETPRODUCTSINSTORE");
                        pw.println(store);
                        pw.flush();
                        var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                    productNames.addAll(productNamesProxy);
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                buyerMain(productNames);  
                
            }
        
        }); 
        JLabel title = new JLabel("<html><h1>THE MARKETPLACE</h1></html>");     //Creates title for home page
        buyerMainNorth.add(title);
        buyerMainNorth.add(refresh);
        buyerMain.add(buyerMainNorth, BorderLayout.NORTH);

        JPanel buyerMainSouth = new JPanel(new FlowLayout());
        JButton purchaseHistory = new JButton("Purchase History");
        purchaseHistory.addActionListener(new ActionListener() {         
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the purchase history of the user
                ArrayList<String> history = new ArrayList<String>(); 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());     
                    pw.println("GETPURCHASEHISTORY");
                    pw.println(username);
                    pw.flush();
                    history = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPURCHASEHISTORY
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                displayPurchaseHistory(history);   
                buyerMain.dispose();
            }
        
        });

        JButton viewDashboard = new JButton("View Dashboard");
        viewDashboard.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the dashboard of a buyer
                ArrayList<String> superStores = new ArrayList<String>();
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());     
                    pw.println("GETSUPERSTORES");
                    pw.flush();
                    superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                buyerDashboard(superStores);   
                buyerMain.dispose();
            }
        
        });

        JButton viewCart = new JButton("View Cart");                
        viewCart.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the shopping cart of a user
                ArrayList<String> cart = new ArrayList<String>(); 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());     
                    pw.println("VIEWCART");
                    pw.println(username);
                    pw.flush();
                    cart = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWCART
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                displayCart(cart);
                buyerMain.dispose();
            }
        
        });
        JButton editAccount = new JButton("Edit Account");
        editAccount.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the page to edit a buyer account
                var info = new ArrayList<String>(); 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream()); 
                    pw.println("GETACCOUNTINFO");   //ROHANFIX Only works if the user is a buyer, not a seller
                    pw.println(username);
                    pw.flush();
                    info = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETACCOUNTINFO
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                editAccount(info);
                buyerMain.dispose();
            }
        
        });
        JButton logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the page to goodbye page
                buyerMain.dispose();
                goodbye();
                
            }
        
        });
        buyerMainSouth.add(purchaseHistory);
        buyerMainSouth.add(viewDashboard);
        buyerMainSouth.add(viewCart);
        buyerMainSouth.add(editAccount);
        buyerMainSouth.add(logout);
        buyerMain.add(buyerMainSouth, BorderLayout.SOUTH);

        JPanel buyerMainCentral = new JPanel(new BorderLayout());
        JPanel searchAndSort = new JPanel(new FlowLayout());
        JTextField searchText = new JTextField(10);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   //ACTION LISTENER - Searches for a product
                ArrayList<String> searched = searchProducts(searchText.getText());      
                buyerMain.dispose();
                buyerMain(searched);
                
            }
        
        });
        String[] s = {"Sort the menu","Sort by price High to Low","Sort by price Low to High","Sort by quantity High to Low","Sort by quantity Low to Hight"};
        JComboBox sortBox = new JComboBox(s);
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());       
            pw.println("GETSUPERSTORES");
            pw.flush();
            superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
            for (String store : superStores) {
                pw.println("GETPRODUCTSINSTORE");
                pw.println(store);
                pw.flush();
                var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                productNames.addAll(productNamesProxy);
            }
            pw.close();
            in.close();
            oos.close();
            ois.close();    
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        ArrayList<String> productName = productNames;
        sortBox.addItemListener(listener -> {
            String choice;
            JComboBox getSelection = (JComboBox) listener.getSource();
            choice = (String) getSelection.getSelectedItem();
            if (choice == s[1]) { 
                buyerMain.dispose();
                buyerMain(sortPrice(true));
            } else if (choice == s[2]) {
                buyerMain.dispose();
                buyerMain(sortPrice(false));
            } else if (choice == s[3]) {
                sortQuantity(true);
                buyerMain.dispose();
                buyerMain(productName);
            } else if (choice == s[4]) {
                sortQuantity(false);
                buyerMain.dispose();
                buyerMain(productName);
            }
        
        });
        searchAndSort.add(searchText);                    //Creates search and sort bars
        searchAndSort.add(searchButton);
        searchAndSort.add(sortBox);
        buyerMainCentral.add(searchAndSort , BorderLayout.NORTH);

        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());       
            pw.println("GETSUPERSTORES");
            pw.flush();  
            superStores = (ArrayList<String>) ois.readObject();  //SERVERREQUEST - GETSUPERSTORES
            pw.close();
            in.close();
            oos.close();
            ois.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        for (String store : superStores) {
            ArrayList<String> pInStore = new ArrayList<String>();
            try {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                pw = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());        
                pw.println("GETPRODUCTSINSTORE");
                pw.println(store);
                pw.flush();
                pInStore = (ArrayList<String>) ois.readObject(); //SERVERREQUEST - GETPRODUCTSINSTORE
                pw.close();
                in.close();
                oos.close();
                ois.close();     
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String product: pInStore) {
                JPanel product1 = new JPanel(new FlowLayout());
                JLabel product1Text = new JLabel(product);
                product1.add(product1Text);
                JButton product1Add = new JButton("Add To Cart");
                product1Add.addActionListener(new ActionListener() {      
                    public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Add to cart Ask the user how many they want to buy
                        String s = JOptionPane.showInputDialog("How many items do you want to buy?");
                        try {
                            PrintWriter pw;
                            Scanner in;
                            ObjectOutputStream oos;
                            ObjectInputStream ois;
                            pw = new PrintWriter(socket.getOutputStream());
                            in = new Scanner(socket.getInputStream());
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            ois = new ObjectInputStream(socket.getInputStream());      
                            pw.println("ADDTOCART");
                            pw.println(product);
                            pw.println(store);
                            pw.println(s);
                            pw.println(username);
                            pw.flush();
                            String flag = in.nextLine(); //SERVERREQUEST - ADDTOCART
                            if (flag.equals("ERROR")) {
                                JOptionPane.showMessageDialog(null, "This item is out of stock for your purchase amount", 
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            pw.close();
                            in.close();
                            oos.close();
                            ois.close();      
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                product1.add(product1Add);
                JButton product1Info = new JButton("Info");
                product1Info.addActionListener(new ActionListener() {      
                    public void actionPerformed(ActionEvent e) {   
                        displayProductInfo(product , store);      //ACTION LISTENER - Sends the user to the Product info page for the specific product
                        buyerMain.dispose();
                        
                    }
                });
                product1.add(product1Info);
                panels.add(product1);
            }
        }

        for (JPanel p : panels) {  //Displays the panels
            productsPanel.add(p);
        }
        JScrollPane scrollPane = new JScrollPane(productsPanel); 
        buyerMainCentral.add(scrollPane , BorderLayout.CENTER);
        buyerMain.add(buyerMainCentral, BorderLayout.CENTER);
            
            

        buyerMain.pack();
        buyerMain.setSize(600, 400);
        buyerMain.setLocationRelativeTo(null);
        buyerMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buyerMain.setVisible(true);

    }

    /*
    * Creates the info page for unique products
    */
    public void displayProductInfo(String product , String store) {
        
        JFrame productInfo = new JFrame("THE MARKETPLACE");
        Container productInfoPanel = productInfo.getContentPane();
        productInfoPanel.setLayout(new BorderLayout());

        JPanel productInfoNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>PRODUCT INFO</h1></html>");
        productInfoNorth.add(title);
        productInfo.add(productInfoNorth, BorderLayout.NORTH);

        JPanel productInfoCentral = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productInfoCentral, BoxLayout.Y_AXIS); //Add Product info
        productInfoCentral.setLayout(boxlayout);
        String info = "";
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());      
            pw.println("PRODUCTINFO");
            pw.println(product);
            pw.println(store);
            pw.flush();
            info = in.nextLine(); //SERVERREQUEST PRODUCTINFO
            pw.close();
            in.close();
            oos.close();
            ois.close();        
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] infoSplit = info.split(";");
        JLabel productNameLabel = new JLabel(String.format("Product Name: %s" , infoSplit[0]));
        JLabel storeNameLabel = new JLabel(String.format("Store Name: %s" , infoSplit[1]));
        JLabel descriptionLabel = new JLabel(String.format("Description: %s" , infoSplit[2]));
        JLabel quantityLabel = new JLabel(String.format("Quantity: %d" , infoSplit[3]));
        JLabel priceLabel = new JLabel(String.format("Price: %.2f" , infoSplit[4]));
        productInfoCentral.add(productNameLabel);
        productInfoCentral.add(storeNameLabel);
        productInfoCentral.add(descriptionLabel);
        productInfoCentral.add(quantityLabel);
        productInfoCentral.add(priceLabel);
        productInfo.add(productInfoCentral , BorderLayout.CENTER);

        JPanel productInfoSouth = new JPanel(new FlowLayout());
        JButton addToCart = new JButton("Add to Cart");
        addToCart.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to add a product to the cart
                String s = JOptionPane.showInputDialog("How many items do you want to buy?");
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());       
                    pw.println("ADDTOCART");
                    pw.println(product);
                    pw.println(store);
                    pw.println(s);
                    pw.println(username);
                    pw.flush();   
                    String flag = in.nextLine(); //SERVERREQUEST - ADDTOCART
                    if (flag.equals("ERROR")) {
                        JOptionPane.showMessageDialog(null, "This item is out of stock for your purchase amount", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    } 
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();    
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        
        });
        JButton contactSeller = new JButton("Contact Seller"); //WHAT IS THIS FUNCTION SUPPOSED TO DO?
        contactSeller.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Send the user back to the buyer home
                String output = "";
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());     
                    pw.println("CONTACTSELLER");
                    pw.println(infoSplit[1]); //gets store title
                    pw.flush();
                    var returned = (ArrayList<String>) ois.readObject(); //SERVERREQUEST CONTACTSELLER   
                    for (String name : returned) {
                        output += name + "\n";
                    }
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();        
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                productInfo.dispose();
                displayProductInfo(product , store);
                JOptionPane.showMessageDialog(null, output);
            }
        
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Send the user back to the buyer home
                productNames = new ArrayList<String>();
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());      
                    pw.println("GETSUPERSTORES");
                    pw.flush();
                    superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                    for (String store : superStores) {
                        pw.println("GETPRODUCTSINSTORE");
                        pw.println(store);
                        pw.flush();
                        var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                        productNames.addAll(productNamesProxy);
                } 
                pw.close();
                in.close();
                oos.close();
                ois.close();      
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                buyerMain(productNames);
                productInfo.dispose();
            }
        
        });
        productInfoSouth.add(addToCart);
        productInfoSouth.add(contactSeller);
        productInfoSouth.add(back);
        productInfo.add(productInfoSouth, BorderLayout.SOUTH);

        productInfo.setSize(600, 400);
        productInfo.setLocationRelativeTo(null);
        productInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productInfo.setVisible(true);
        productInfo.requestFocus();  //Requests focus for the frame
    }

    /*
     * Creates the page for user to see their purchase history
     */
    public void displayPurchaseHistory(ArrayList<String> history) {
        JFrame purchaseHistory = new JFrame("THE MARKETPLACE");
        Container purchaseHistoryPanel = purchaseHistory.getContentPane();
        purchaseHistoryPanel.setLayout(new BorderLayout());

        JPanel purchaseHistoryNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>PURCHASE HISTORY</h1></html>");
        purchaseHistoryNorth.add(title);
        purchaseHistory.add(purchaseHistoryNorth, BorderLayout.NORTH);

        JPanel purchaseHistoryCentral = new JPanel();
        BoxLayout boxlayout = new BoxLayout(purchaseHistoryCentral, BoxLayout.Y_AXIS); //Add Product info
        purchaseHistoryCentral.setLayout(boxlayout);
        for (String line : history) {
            purchaseHistoryCentral.add(new JLabel(line));  //Gets the user purchase history and displays it : how does marketplace do this?
        }
        JScrollPane scrollPane = new JScrollPane(purchaseHistoryCentral); 
        purchaseHistory.add(scrollPane , BorderLayout.CENTER);

        JPanel purchaseHistorySouth = new JPanel(new FlowLayout());
        JTextField exportText = new JTextField("purchaseHistory.txt");  //FilePath
        JButton exportProduct = new JButton("Export to File"); //Adds bottom buttons
        exportProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Exports the purchase history to a file
                exportHistory(exportText.getText() , history);
            }
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Send the user back to the buyer home
                productNames = new ArrayList<String>();
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());     
                    pw.println("GETSUPERSTORES");
                    pw.flush();
                    superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                    for (String store : superStores) {
                        pw.println("GETPRODUCTSINSTORE");
                        pw.println(store);
                        pw.flush();
                        var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                        productNames.addAll(productNamesProxy);
                    } 
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();     
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                buyerMain(productNames);
                purchaseHistory.dispose();
            }
        
        });
        purchaseHistorySouth.add(exportText);
        purchaseHistorySouth.add(exportProduct);
        purchaseHistorySouth.add(back);
        purchaseHistory.add(purchaseHistorySouth, BorderLayout.SOUTH);

        purchaseHistory.setSize(600, 400);
        purchaseHistory.setLocationRelativeTo(null);
        purchaseHistory.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        purchaseHistory.setVisible(true);
        purchaseHistory.requestFocus();
    }

    /*
     * Allows the user to see and edit their cart or checkout.
     */
    public void displayCart(ArrayList<String> viewCart) {
        JFrame cart = new JFrame("THE MARKETPLACE");
        Container cartPanel = cart.getContentPane();
        cartPanel.setLayout(new BorderLayout());

        JPanel cartNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>CART</h1></html>");     //Createds title
        cartNorth.add(title);
        cart.add(cartNorth, BorderLayout.NORTH);

        JPanel cartSouth = new JPanel(new FlowLayout());
        JButton continueShopping = new JButton("Continue Shopping");
        continueShopping.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                cart.dispose();      //ACTION LISTENER - Sends the user back to the main page
                productNames = new ArrayList<String>();
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());    
                    pw.println("GETSUPERSTORES");
                    pw.flush();
                    superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                    for (String store : superStores) {
                        pw.println("GETPRODUCTSINSTORE");
                        pw.println(store);
                        pw.flush();
                        var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                        productNames.addAll(productNamesProxy);
                    }
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();  
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                buyerMain(productNames);
            }
        
        });
        JButton checkout = new JButton("Checkout");                 
        checkout.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure?");      //ACTION LISTENER - Sends the user back to the main page
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        PrintWriter pw;
                        Scanner in;
                        ObjectOutputStream oos;
                        ObjectInputStream ois;
                        pw = new PrintWriter(socket.getOutputStream());
                        in = new Scanner(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream());       
                        pw.println("PURCHASE");  //SERVERREQUEST PURCHASE
                        pw.println(username);
                        pw.flush();
                        cart.dispose();
                        pw.println("GETSUPERSTORES");
                        pw.flush();
                        superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                        productNames = new ArrayList<String>();
                        for (String store : superStores) {
                            pw.println("GETPRODUCTSINSTORE");
                            pw.println(store);
                            pw.flush();
                            var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                            productNames.addAll(productNamesProxy);
                        }
                        buyerMain(productNames);
                        JOptionPane.showMessageDialog(null, "Purchase Confirmed", "THE MARKETPLACE", JOptionPane.PLAIN_MESSAGE);
                        pw.close();
                        in.close();
                        oos.close();
                        ois.close();     
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        
        });
        cartSouth.add(continueShopping);
        cartSouth.add(checkout);
        cart.add(cartSouth, BorderLayout.SOUTH);

        JPanel cartCentral = new JPanel(new BorderLayout());
        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (String line : viewCart) {    
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel(line); //Populates shopping cart on screen
            product1.add(product1Text);
            JButton delete = new JButton("Delete");
            delete.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {   //ACTION LISTENER - removes an item from the cart and updates the page
                    try {
                        PrintWriter pw;
                        Scanner in;
                        ObjectOutputStream oos;
                        ObjectInputStream ois;
                        pw = new PrintWriter(socket.getOutputStream());
                        in = new Scanner(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream());     
                        pw.println("DELETEPRODUCTCART");
                        pw.println(); //product name       FIXXX THIS
                        pw.println(); //store name
                        pw.println(username); 
                        pw.flush(); 
                        cart.dispose();
                        pw.println("VIEWCART");
                        pw.flush();
                        ArrayList<String> view = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWCART
                        displayCart(view);
                        pw.close();
                        in.close();
                        oos.close();
                        ois.close();  
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    
                }
            
            });
            product1.add(delete);
            panels.add(product1);
        }
        for (JPanel p : panels) {  //Displays the panels
            productsPanel.add(p);
        }
        JScrollPane scrollPane = new JScrollPane(productsPanel); 
        cartCentral.add(scrollPane , BorderLayout.CENTER);
        cart.add(cartCentral, BorderLayout.CENTER);
        

        cart.pack();
        cart.setSize(600, 400);
        cart.setLocationRelativeTo(null);
        cart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cart.setVisible(true);
        cart.requestFocus();
    }

    /*
     * Opens a page for a user to edit their account
     */
    public void editAccount(ArrayList<String> edit) {
        JFrame account = new JFrame("THE MARKETPLACE");
        Container accountPanel = account.getContentPane();
        accountPanel.setLayout(new BorderLayout());

        JPanel accountNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>EDIT ACCOUNT</h1></html>");     //Createds title
        accountNorth.add(title);
        account.add(accountNorth, BorderLayout.NORTH);
        ArrayList<String> returned = new ArrayList<String>();
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());       
            pw.println("GETACCOUNTINFO");
            pw.println(username);
            pw.flush();
            returned = (ArrayList<String>) ois.readObject(); //SERVERREQUEST - GETACCOUNTINFO
            pw.close();
            in.close();
            oos.close();
            ois.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel accountCentral = new JPanel(new BorderLayout());
        JPanel accountFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(accountFields, BoxLayout.Y_AXIS);
        accountFields.setLayout(boxlayout);
        
        JPanel password = new JPanel(new FlowLayout());
        JLabel passwordText = new JLabel("Password: ");
        JTextField passwordField = new JTextField(returned.get(1)); //prepopulated with password
        password.add(passwordText);
        password.add(passwordField);
        JPanel email = new JPanel(new FlowLayout());
        JLabel emailText = new JLabel("Email: ");
        JTextField emailField = new JTextField(returned.get(0)); //prepopulated with email
        email.add(emailText);
        email.add(emailField);

        JPanel accountSouth = new JPanel(new FlowLayout());
        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                account.dispose();      //ACTION LISTENER - Sends the user back to the main page
                ArrayList<String> out = new ArrayList<String>();
                String p = passwordField.getText();
                String em = emailField.getText();
                out.add(em);
                out.add(p);
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());     
                    pw.println("UPDATEACCOUNTINFO");
                    pw.println(username);
                    oos.writeObject(out);  //FIX
                    pw.flush();
                    oos.flush();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();     
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (loggedInAsBuyer) {
                    try {
                        PrintWriter pw;
                        Scanner in;
                        ObjectOutputStream oos;
                        ObjectInputStream ois;
                        pw = new PrintWriter(socket.getOutputStream());
                        in = new Scanner(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream());     
                        pw.println("GETSUPERSTORES");
                        pw.flush();
                        superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                        pw.close();
                        in.close();
                        oos.close();
                        ois.close();  
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    productNames = new ArrayList<String>();
                    for (String store : superStores) {
                        try {
                            PrintWriter pw;
                            Scanner in;
                            ObjectOutputStream oos;
                            ObjectInputStream ois;
                            pw = new PrintWriter(socket.getOutputStream());
                            in = new Scanner(socket.getInputStream());
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            ois = new ObjectInputStream(socket.getInputStream());   
                            pw.println("GETPRODUCTSINSTORE");
                            pw.println(store);
                            pw.flush();
                            var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                            productNames.addAll(productNamesProxy);
                            pw.close();
                            in.close();
                            oos.close();
                            ois.close();  
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    buyerMain(productNames);
                } else if (loggedInAsSeller) {
                    try {
                        PrintWriter pw;
                        Scanner in;
                        ObjectOutputStream oos;
                        ObjectInputStream ois;
                        pw = new PrintWriter(socket.getOutputStream());
                        in = new Scanner(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream());  
                        pw.println("VIEWSTORES");
                        pw.println(username);
                        pw.flush();
                        myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                        sellerMain(myStoreNames);
                        pw.close();
                        in.close();
                        oos.close();
                        ois.close();      
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                
            }
        
        });
        
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                account.dispose();      //ACTION LISTENER - Sends the user back to the main page
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());  
                    if (loggedInAsBuyer) {
                        pw.println("GETSUPERSTORES");
                        pw.flush();
                        superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                        productNames = new ArrayList<String>();
                        for (String store : superStores) {
                            pw.println("GETPRODUCTSINSTORE");
                            pw.println(store);
                            pw.flush();
                            var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                            productNames.addAll(productNamesProxy);
                        }
                        buyerMain(productNames);
                    } else if (loggedInAsSeller) {
                        pw.println("VIEWSTORES");
                        pw.println(username);
                        pw.flush();
                        myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                        sellerMain(myStoreNames);
                    }
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();  
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
            }
        
        });
        accountSouth.add(update);
        accountSouth.add(back);
        account.add(accountSouth, BorderLayout.SOUTH);

        accountFields.add(password);
        accountFields.add(email);
        JScrollPane scrollPane = new JScrollPane(accountFields); 
        accountCentral.add(scrollPane , BorderLayout.CENTER);
        account.add(accountCentral, BorderLayout.CENTER);
        account.pack();
        account.setSize(600, 400);
        account.setLocationRelativeTo(null);
        account.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        account.setVisible(true);
        account.requestFocus();
    }

    /*
     * Opens the home page for a seller
     */
    public void sellerMain(ArrayList<String> storeNames) {
        JFrame sellerMain = new JFrame("THE MARKETPLACE");
        Container sellerMainPanel = sellerMain.getContentPane();
        sellerMainPanel.setLayout(new BorderLayout());

        JPanel sellerMainNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>THE MARKETPLACE</h1></html>");     //Creates title
        sellerMainNorth.add(title);
        JButton addStore = new JButton("Add Store");
        addStore.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream()); 
                    String s = JOptionPane.showInputDialog(null, "Enter Store Name:");     //ACTION LISTENER - adds a store
                    pw.println("ADDSTORE"); //SERVERREQUEST - ADDSTORE
                    pw.println(s);
                    pw.println(username);
                    pw.flush();
                    String confirmation = in.nextLine();
                    System.out.println(confirmation);
                    if (confirmation.equals("ERROR")) {
                        JOptionPane.showMessageDialog(null, "There is already a store with that name", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        myStoreNames = (ArrayList<String>) ois.readObject();
                    }
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();             
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                sellerMain(myStoreNames); 
                
            }
        
        });
        sellerMainNorth.add(addStore);
        sellerMain.add(sellerMainNorth, BorderLayout.NORTH);

        JPanel sellerMainCentral = new JPanel(new BorderLayout());
        JLabel yourStores = new JLabel("<html><h2>Your Stores:</h2></html>"); 
        sellerMainCentral.add(yourStores, BorderLayout.NORTH);

        JPanel storesPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(storesPanel, BoxLayout.Y_AXIS); //Variable number of stores from store list
        storesPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (String line : storeNames) {
            JPanel store1 = new JPanel(new FlowLayout());
            JLabel store1Text = new JLabel(line);
            store1.add(store1Text);
            JButton store1Edit = new JButton("Edit");  
            store1Edit.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {           //ACTION LISTENER links to edit page
                    displayEditStore(line);
                    sellerMain.dispose();
                }
            
            });
            store1.add(store1Edit);
            JButton store1Delete = new JButton("Delete");
            store1Delete.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {  
                    ArrayList<String> returned = new ArrayList<String>();
                    try {
                        PrintWriter pw;
                        Scanner in;
                        ObjectOutputStream oos;
                        ObjectInputStream ois;
                        pw = new PrintWriter(socket.getOutputStream());
                        in = new Scanner(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream()); 
                        pw.println("DELETESTORE");
                        pw.print(line);
                        pw.flush();
                        returned = (ArrayList<String>) ois.readObject(); //SERVERREQUEST - DELETESTORE
                        pw.close();
                        in.close();
                        oos.close();
                        ois.close();  
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } 
                    sellerMain.dispose();                          //ACTION LISTENER Deletes store
                    sellerMain(returned);
                    
                }
            
            });
            store1.add(store1Delete);
            panels.add(store1);
        }
        for (JPanel p : panels) {  //Displays the panels
            storesPanel.add(p);
        }
        JScrollPane scrollPane = new JScrollPane(storesPanel); 
        sellerMainCentral.add(scrollPane , BorderLayout.CENTER);
        sellerMain.add(sellerMainCentral, BorderLayout.CENTER);

        JPanel sellerDashboard = new JPanel(new FlowLayout());
        JButton dash = new JButton("View Dashboard");
        dash.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                sellerDashboard(true);
                sellerMain.dispose();
            }
        
        });
        
        JPanel sellerMainSouth = new JPanel(new FlowLayout());
        JButton viewInCart = new JButton("Products in Customer's carts");
        viewInCart.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the page to edit a buyer account
                var info = new ArrayList<String>(); 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream()); 
                    pw.println("NUMINCART");   //ROHANFIX Only works if the user is a buyer, not a seller
                    pw.println(username);
                    pw.flush();
                    info = (ArrayList<String>) ois.readObject(); //SERVERREQUEST NUMINCART
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                viewProductsInCarts(info);
                sellerMain.dispose();
            }
        
        });
        
        JButton editAccount = new JButton("Edit Account");
        editAccount.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) { 
                var info = new ArrayList<String>(); 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream()); 
                   pw.println("GETACCOUNTINFO"); 
                   pw.println(username);
                   pw.flush();
                   info = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETACCOUNTINFO
                   pw.close();
                   in.close();
                   oos.close();
                   ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                } 
                editAccount(info);
                sellerMain.dispose();
            }
        
        });
        JButton logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                goodbye();          //Links to goodbye page
                sellerMain.dispose();
            }
        
        });
        sellerMainSouth.add(sellerDashboard); 
        sellerMainSouth.add(viewInCart);
        sellerMainSouth.add(editAccount);
        sellerMainSouth.add(logout);
        sellerMain.add(sellerMainSouth , BorderLayout.SOUTH);

        sellerMain.pack();
        sellerMain.setSize(600, 400);
        sellerMain.setLocationRelativeTo(null);
        sellerMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sellerMain.setVisible(true);
        sellerMain.requestFocus();

    }

    /*
     * Opens a menu to edit a chosen store
     */
    public void displayEditStore(String line) {
        JFrame editStore = new JFrame("THE MARKETPLACE");
        Container editStorePanel = editStore.getContentPane();
        editStorePanel.setLayout(new BorderLayout());

        JPanel editStoreNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>" + line + "</h1></html>");     //Creates title
        editStoreNorth.add(title);
        JButton addProduct = new JButton("Add Product");
        addProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayAddProduct(line);
                editStore.dispose();
            }
        
        });
        editStoreNorth.add(addProduct);
        editStore.add(editStoreNorth, BorderLayout.NORTH);

        JPanel editStoreCentral = new JPanel(new BorderLayout());
        JPanel yourStoresWindow = new JPanel(new FlowLayout());
        JLabel yourStores = new JLabel("<html>Products in:</html>"); 
        JLabel storeTitle = new JLabel(line);
        yourStoresWindow.add(yourStores);
        yourStoresWindow.add(storeTitle);

        editStoreCentral.add(yourStoresWindow, BorderLayout.NORTH);

        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        ArrayList<String> products = new ArrayList<String>();
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream()); 
            pw.println("VIEWPRODUCTS");
            pw.println(line);
            pw.flush();
            products = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWPRODCUTS
            pw.close();
            in.close();
            oos.close();
            ois.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String p : products) {
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel(p);
            product1.add(product1Text);
            JButton product1Edit = new JButton("Edit");  
            product1Edit.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {  
                    editStore.dispose();          //Links to stats page
                    displayEditProduct(p , line);
                    
                }
            
            });
            product1.add(product1Edit);
            JButton product1Delete = new JButton("Delete");
            product1Delete.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {  
                    try {
                        PrintWriter pw;
                        Scanner in;
                        ObjectOutputStream oos;
                        ObjectInputStream ois;
                        pw = new PrintWriter(socket.getOutputStream());
                        in = new Scanner(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream()); 
                        pw.println("DELETEPRODUCT");
                        pw.println(p); //SERVERREQUEST - DELETEPRODCUT
                        pw.println(line);
                        pw.flush();
                        pw.close();
                        in.close();
                        oos.close();
                        ois.close(); 
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    editStore.dispose();
                    displayEditStore(line);
                }
            
            });
            product1.add(product1Delete);
            panels.add(product1);
        }
        for (JPanel p : panels) {  //Displays the panels
            productsPanel.add(p);
        }
        JScrollPane scrollPane = new JScrollPane(productsPanel); 
        editStoreCentral.add(scrollPane , BorderLayout.CENTER);
        editStore.add(editStoreCentral, BorderLayout.CENTER);

        JPanel editStoreSouth = new JPanel(new FlowLayout());
        JTextField importText = new JTextField(10);  //FilePath
        JButton importProduct = new JButton("Import");
        importProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  //ACTIONLISTENER Import PRODUCATS
                ArrayList<String> errors = new ArrayList<String>();
                ArrayList<String> imported = importProducts(importText.getText());
                for (String i : imported) {
                    try {
                        PrintWriter pw;
                        Scanner in;
                        ObjectOutputStream oos;
                        ObjectInputStream ois;
                        pw = new PrintWriter(socket.getOutputStream());
                        in = new Scanner(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream()); 
                        pw.println("ADDPRODUCT");     //SERVERREQUEST ADDPRODUCT
                        pw.print(i);
                        pw.println(line);
                        pw.flush();
                        String returned = in.nextLine();
                        if (returned.equals("ERROR")) {
                            errors.add(i);
                        }
                        pw.close();
                        in.close();
                        oos.close();
                        ois.close(); 
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                editStore.dispose();
                displayEditStore(line);
                if (errors.size() > 0) {
                    String errorMessage = "Error adding these products: ";
                    for (String error : errors) {
                        errorMessage +=  error + ", ";
                    }
                    errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
                    JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        
        });
        JTextField exportText = new JTextField(10);  //FilePath
        JButton exportProduct = new JButton("Export"); //Adds bottom buttons
        exportProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                exportProducts(exportText.getText(), line);  //ACTIONLISTENER EXPORT PRODUCATS
            }
        
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream()); 
                    pw.println("VIEWSTORES");
                    pw.println(username);
                    pw.flush();
                    myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWSTORES
                    sellerMain(myStoreNames);
                    editStore.dispose();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        
        });
        JButton stats = new JButton("Sales Summary");
        stats.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displaySales(line);          //Links to stats page
                editStore.dispose();
            }
        
        });
        editStoreSouth.add(stats);
        editStoreSouth.add(importText);
        editStoreSouth.add(importProduct);
        editStoreSouth.add(exportText);
        editStoreSouth.add(exportProduct);
        editStoreSouth.add(back);
        editStore.add(editStoreSouth, BorderLayout.SOUTH);

        editStore.pack();
        editStore.setSize(600, 400);
        editStore.setLocationRelativeTo(null);
        editStore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editStore.setVisible(true);
        editStore.requestFocus();
    }
    /*
     * Opens a page for a seller to edit
     */
    public void displayEditProduct(String product , String storeString) {
        String info = "";
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream()); 
            pw.println("PRODUCTINFO");
            pw.print(product);
            pw.println(storeString);
            pw.flush();
            info = in.nextLine();   //SERVERREQUEST - PRODUCTINFO
            pw.close();
            in.close();
            oos.close();
            ois.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        } 
        String[] infoSplit = info.split(";");
        JFrame editProduct = new JFrame("THE MARKETPLACE");
        Container editProductPanel = editProduct.getContentPane();
        editProductPanel.setLayout(new BorderLayout());

        JPanel editProductNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>EDIT PRODUCT</h1></html>");     //Createds title
        editProductNorth.add(title);
        editProduct.add(editProductNorth, BorderLayout.NORTH);


        JPanel editProductCentral = new JPanel(new BorderLayout());
        JPanel editProductFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(editProductFields, BoxLayout.Y_AXIS); //Variable number of products from product list
        editProductFields.setLayout(boxlayout);
        
        JPanel productName = new JPanel(new FlowLayout());
        JLabel productNameText = new JLabel("Product Name: ");
        JTextField productNameField = new JTextField(infoSplit[0]); //prepopulated with product name
        productName.add(productNameText);
        productName.add(productNameField);

        JPanel storeName = new JPanel(new FlowLayout());
        JLabel storeNameText = new JLabel("Store Name: ");
        JTextField storeNameField = new JTextField(infoSplit[1]); //prepopulated with store name
        storeName.add(storeNameText);
        storeName.add(storeNameField);

        JPanel description = new JPanel(new FlowLayout());
        JLabel descriptionText = new JLabel("Description: ");
        JTextField descriptionField = new JTextField(infoSplit[2]); //prepopulated with descirption
        description.add(descriptionText);
        description.add(descriptionField);

        JPanel quantity = new JPanel(new FlowLayout());
        JLabel quantityText = new JLabel("Quantity: ");
        JTextField quantityField = new JTextField(infoSplit[3]); //prepopulated with descirption
        quantity.add(quantityText);
        quantity.add(quantityField);

        JPanel price = new JPanel(new FlowLayout());
        JLabel priceText = new JLabel("Price: ");
        JTextField priceField = new JTextField(infoSplit[4]); //prepopulated with descirption
        price.add(priceText);
        price.add(priceField);

        editProductFields.add(productName);
        editProductFields.add(storeName);
        editProductFields.add(description);
        editProductFields.add(quantity);
        editProductFields.add(price);


        JPanel editProductSouth = new JPanel(new FlowLayout());
        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {   
            public void actionPerformed(ActionEvent e) {      
                String pInfo = "";
                ArrayList<String> output = new ArrayList<String>();
                pInfo += productNameText.getText() + ";";
                pInfo += storeName + ";";
                pInfo += descriptionText.getText() + ";";
                pInfo += quantityText.getText() + ";";
                pInfo += priceText.getText();
                output.add(pInfo);
                String confirmed = ""; 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream()); 
                    pw.println("EDITPRODUCT");
                    pw.println(output);
                    pw.flush();
                    confirmed = in.nextLine(); //SERVERREQUEST - EDITPRODUCT
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
                if (confirmed.equals("ERROR")) {
                    JOptionPane.showMessageDialog(null, "There is already a product with that name in your store",
                     "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                displayEditStore(storeString);
                editProduct.dispose();
            }
        
        });

        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayEditStore(storeString);
                editProduct.dispose();
            }
        
        });
        editProductSouth.add(update);
        editProductSouth.add(back);
        editProduct.add(editProductSouth, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(editProductFields); 
        editProductCentral.add(scrollPane , BorderLayout.CENTER);
        editProduct.add(editProductCentral, BorderLayout.CENTER);
        editProduct.pack();
        editProduct.setSize(600, 400);
        editProduct.setLocationRelativeTo(null);
        editProduct.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editProduct.setVisible(true);
        editProduct.requestFocus();
    }

    /*
     * Opens a page for a seller to add a product
     */
    public void displayAddProduct(String storeName) {                               //UPDATE TO ADD EVERYTHING
        JFrame addProduct = new JFrame("THE MARKETPLACE");
        Container addProductPanel = addProduct.getContentPane();
        addProductPanel.setLayout(new BorderLayout());

        JPanel addProductNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>ADD PRODUCT</h1></html>");     //Createds title
        addProductNorth.add(title);
        addProduct.add(addProductNorth, BorderLayout.NORTH);

        

        JPanel addProductCentral = new JPanel(new BorderLayout());
        JPanel addProductFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(addProductFields, BoxLayout.Y_AXIS); //Variable number of products from product list
        addProductFields.setLayout(boxlayout);
        
        JPanel productName = new JPanel(new FlowLayout());
        JLabel productNameText = new JLabel("Product Name: ");
        JTextField productNameField = new JTextField(10); //prepopulated with product name
        productName.add(productNameText);
        productName.add(productNameField);

        JPanel description = new JPanel(new FlowLayout());
        JLabel descriptionText = new JLabel("Description: ");
        JTextField descriptionField = new JTextField(10); //prepopulated with descirption
        description.add(descriptionText);
        description.add(descriptionField);

        JPanel quantity = new JPanel(new FlowLayout());
        JLabel quantityText = new JLabel("Quantity: ");
        JTextField quantityField = new JTextField(10); //prepopulated with descirption
        quantity.add(quantityText);
        quantity.add(quantityField);

        JPanel price = new JPanel(new FlowLayout());
        JLabel priceText = new JLabel("Price: ");
        JTextField priceField = new JTextField(10); //prepopulated with descirption
        price.add(priceText);
        price.add(priceField);

        JPanel addProductSouth = new JPanel(new FlowLayout());
        JButton create = new JButton("Create");
        create.addActionListener(new ActionListener() {   
            public void actionPerformed(ActionEvent e) {
                String pInfo = "";
                ArrayList<String> output = new ArrayList<String>();
                pInfo += productNameText.getText() + ";";
                pInfo += storeName + ";";
                pInfo += descriptionText.getText() + ";";
                pInfo += quantityText.getText() + ";";
                pInfo += priceText.getText();
                output.add(pInfo);
                String confirmed = "";
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("ADDPRODUCT");
                    pw.println(output);
                    pw.flush();
                    confirmed = in.nextLine();
                    if (confirmed.equals("ERROR")) {
                        JOptionPane.showMessageDialog(null, "There is already a store with that name","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (confirmed.equals("ERROR")) {
                    JOptionPane.showMessageDialog(null, "There is already a product with that name in your store",
                     "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                addProduct.dispose();
                displayEditStore(storeName);
                
            }
        
        });
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayEditStore(storeName);
                addProduct.dispose();
            }
        
        });
        addProductSouth.add(create);
        addProductSouth.add(back);
        addProduct.add(addProductSouth, BorderLayout.SOUTH);

        addProductFields.add(productName);
        addProductFields.add(description);
        addProductFields.add(quantity);
        addProductFields.add(price);

        JScrollPane scrollPane = new JScrollPane(addProductFields); 
        addProductCentral.add(scrollPane , BorderLayout.CENTER);
        addProduct.add(addProductCentral, BorderLayout.CENTER);
        addProduct.pack();
        addProduct.setSize(600, 400);
        addProduct.setLocationRelativeTo(null);
        addProduct.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addProduct.setVisible(true);
        addProduct.requestFocus();
    }

    public void displaySales(String storeName) {
        JFrame stats = new JFrame("THE MARKETPLACE");
        Container statsPanel = stats.getContentPane();
        statsPanel.setLayout(new BorderLayout());

        JPanel statsNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>SALES SUMMARY</h1></html>");     //Createds title
        statsNorth.add(title);
        stats.add(statsNorth, BorderLayout.NORTH);
        
        JPanel statsCentral = new JPanel(new BorderLayout());
        JPanel sort = new JPanel(new FlowLayout());       
        statsCentral.add(sort , BorderLayout.NORTH);
        JPanel statsItems = new JPanel();
        BoxLayout boxlayout = new BoxLayout(statsItems, BoxLayout.Y_AXIS); //Add Product info
        statsItems.setLayout(boxlayout);
        ArrayList<String> returned = new ArrayList<String>(); 
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            pw.println("SALESBYSTORE");
            pw.println(storeName);
            pw.flush();
            returned = (ArrayList<String>) ois.readObject();//SERVERREQUEST - SALESSBYSTORE
            pw.close();
            in.close();
            oos.close();
            ois.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String c = "";
        for (String line : returned) {
            c += line + "\n";
        }
        statsItems.add(new JLabel(c));
        JScrollPane scrollPane = new JScrollPane(statsItems); 
        statsCentral.add(scrollPane , BorderLayout.CENTER);
        stats.add(statsCentral,  BorderLayout.CENTER);

        JPanel statsSouth = new JPanel(new FlowLayout());
        JTextField exportText = new JTextField("sales.txt");  //FilePath
        JButton exportProduct = new JButton("Export to File"); //Adds bottom buttons
        exportProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Exports the purchase history to a file
                ArrayList<String> returned = new ArrayList<String>(); 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("SALESBYSTORE");
                    pw.println(storeName);
                    pw.flush();
                    returned = (ArrayList<String>) ois.readObject();//SERVERREQUEST - SALESSBYSTORE
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                }    
                exportSales(exportText.getText() , returned);
            }
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayEditStore(storeName);
                stats.dispose();
            }
        
        });

        statsSouth.add(exportText);
        statsSouth.add(exportProduct);
        statsSouth.add(back);
        stats.add(statsSouth, BorderLayout.SOUTH);

        stats.pack();
        stats.setSize(600, 400);
        stats.setLocationRelativeTo(null);
        stats.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stats.setVisible(true);
        stats.requestFocus();
    }

    public void sellerDashboard(Boolean sorted) { //FIX NEEDS TO BE ABLE TO SORT DASHBOARD
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            pw.println("VIEWSTORES");
            pw.println(username);
            pw.flush();
            myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWSTORES
            pw.close();
            in.close();
            oos.close();
            ois.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> customers = new ArrayList<>(); 
        ArrayList<String> products = new ArrayList<>();

        JFrame sellerDash = new JFrame("THE MARKETPLACE");
        Container sellerDashPanel = sellerDash.getContentPane();
        sellerDashPanel.setLayout(new BorderLayout());

        JPanel sellerDashNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>DASHBOARD</h1></html>");
        sellerDashNorth.add(title);
        sellerDash.add(sellerDashNorth, BorderLayout.NORTH);

        JPanel sort = new JPanel(new FlowLayout());
        String[] s = {"Sort the menu","Best Sellers","Worst Sellers"};
        JComboBox sortBox = new JComboBox(s);
        sort.add(sortBox);
        sortBox.addItemListener(listener -> {
            String choice;
            JComboBox getSelection = (JComboBox) listener.getSource();
            choice = (String) getSelection.getSelectedItem();
            if (choice == s[1]) { 
                sellerDash.dispose();
                sellerDashboard(true);
            } else if (choice == s[2]) {
                sellerDash.dispose();
                sellerDashboard(false);
            }
        
        });
       
        sellerDashNorth.add(sort);

        JPanel sellerDashCentral = new JPanel();
        BoxLayout boxlayout = new BoxLayout(sellerDashCentral, BoxLayout.Y_AXIS); //Add Product info
        sellerDashCentral.setLayout(boxlayout);
        
        for (String store : myStoreNames) {
            try {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                pw = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                pw.println("CUSTOMERLIST");
                pw.println(store);
                pw.flush();
                customers = (ArrayList<String>) ois.readObject(); //SERVERREQUEST CUSTOMERLIST
                pw.close();
                in.close();
                oos.close();
                ois.close(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            sellerDashCentral.add(new JLabel(store));
            sellerDashCentral.add(new JLabel("CUSTOMERS:"));
            customers = sortBestCustomers(customers , store , sorted);
            for (String customer : customers) {
                sellerDashCentral.add(new JLabel(customer));
            }
            try {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                pw = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                pw.println("VIEWPRODUCTS");
                pw.println(store);
                pw.flush();
                products = (ArrayList<String>) ois.readObject(); //SERVERREQUEST CUSTOMERLIST
                pw.close();
                in.close();
                oos.close();
                ois.close(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            products = sortBestProducts(products , store , sorted);
            sellerDashCentral.add(new JLabel("PRODUCTS:"));
            for (String product : products) {
                sellerDashCentral.add(new JLabel(product));
            }

        }


        JScrollPane scrollPane = new JScrollPane(sellerDashCentral); 
        sellerDash.add(scrollPane , BorderLayout.CENTER);

        JPanel sellerDashSouth = new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("VIEWSTORES");
                    pw.println(username);
                    pw.flush();
                    myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWSTORES
                    sellerMain(myStoreNames);
                    sellerDash.dispose();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        
        });
        sellerDashSouth.add(back);
        sellerDash.add(sellerDashSouth, BorderLayout.SOUTH);

        sellerDash.setSize(600, 400);
        sellerDash.setLocationRelativeTo(null);
        sellerDash.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sellerDash.setVisible(true);
        sellerDash.requestFocus();
    }

    public void buyerDashboard(ArrayList<String> superStores) {
        JFrame buyerDash = new JFrame("THE MARKETPLACE");
        Container buyerDashPanel = buyerDash.getContentPane();
        buyerDashPanel.setLayout(new BorderLayout());

        JPanel buyerDashNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>DASHBOARD</h1></html>");
        buyerDashNorth.add(title);
        JPanel sort = new JPanel(new FlowLayout());
        String[] s = {"Sort the menu","Most Products Sold","Least Products Sold","My Most Shopped Stores",
        "My Least Shopped Stores"};
        JComboBox sortBox = new JComboBox(s);
        sort.add(sortBox);

        sortBox.addItemListener(listener -> {
            String choice;
            JComboBox getSelection = (JComboBox) listener.getSource();
            choice = (String) getSelection.getSelectedItem();
            if (choice == s[1]) { 
                ArrayList<String> sorted = sortProductsSold(superStores , true);
                buyerDash.dispose();
                buyerDashboard(sorted);
            } else if (choice == s[2]) {
                ArrayList<String> sorted = sortProductsSold(superStores , false);
                buyerDash.dispose();
                buyerDashboard(sorted);
            } else if (choice == s[3]) {
                ArrayList<String> sorted = sortMostShopped(superStores , true);
                buyerDash.dispose();
                buyerDashboard(sorted);
            } else if (choice == s[4]) {
                ArrayList<String> sorted = sortMostShopped(superStores , false);
                buyerDash.dispose();
                buyerDashboard(sorted);
            }
        
        });
        buyerDashNorth.add(sort);

        buyerDash.add(buyerDashNorth, BorderLayout.NORTH);

        JPanel buyerDashCentral = new JPanel();
        BoxLayout boxlayout = new BoxLayout(buyerDashCentral, BoxLayout.Y_AXIS); //Add Product info
        buyerDashCentral.setLayout(boxlayout);
        for (String line : superStores) {
            buyerDashCentral.add(new JLabel(line));  //Gets the user purchase history and displays it : how does marketplace do this?
        }
        JScrollPane scrollPane = new JScrollPane(buyerDashCentral); 
        buyerDash.add(scrollPane , BorderLayout.CENTER);

        JPanel buyerDashSouth = new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Send the user back to the buyer home
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream()); 
                    pw.println("GETSUPERSTORES");
                    pw.flush();
                    myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
                    productNames = new ArrayList<String>();
                    for (String store : myStoreNames) {
                        pw.println("GETPRODUCTSINSTORE");
                        pw.println(store);
                        pw.flush();
                        var productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                        productNames.addAll(productNamesProxy);
                    }
                    buyerMain(productNames);
                    buyerDash.dispose();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        
        });
        buyerDashSouth.add(back);
        buyerDash.add(buyerDashSouth, BorderLayout.SOUTH);

        buyerDash.setSize(600, 400);
        buyerDash.setLocationRelativeTo(null);
        buyerDash.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buyerDash.setVisible(true);
        buyerDash.requestFocus();
    }

    public void goodbye() {
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            pw.println("LOGOUT");
            pw.flush();
            pw.close();
            oos.close();
            ois.close();
            in.close();    
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame goodbye = new JFrame("THE MARKETPLACE");
        Container goodbyePanel = goodbye.getContentPane();
        goodbyePanel.setLayout(new BorderLayout());

        JPanel goodbyeNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>BYE! THANKS FOR USING THE MARKETPLACE</h1></html>");     //Createds title
        goodbyeNorth.add(title);
        goodbye.add(goodbyeNorth, BorderLayout.NORTH);

        goodbye.pack();
        goodbye.setSize(600, 400);
        goodbye.setLocationRelativeTo(null);
        goodbye.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        goodbye.setVisible(true);
        goodbye.requestFocus();


        
    }

    public boolean exportHistory(String path , ArrayList<String> purchases) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.createNewFile();
            }
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));           
            for (String sale : purchases) {
                pw.println(sale); 
            }
            pw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportSales(String path , ArrayList<String> sales) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.createNewFile();
            }
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));           
            for (String sale : sales) {
                pw.println(sale); 
            }
            pw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Sorts an Arraylist of products based on price
     * Sorts highest price to lowest if highToLow = true    NOT SURE IF I EVEN NEED THIS
     * if it's false, sorts lowest to highest
     */
    public ArrayList<String> sortPrice(boolean highToLow) {
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            pw.println("GETSUPERSTORES");
            pw.flush();
            superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES   
            pw.close();
            in.close();
            oos.close();
            ois.close();  
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> productsWithInfo = new ArrayList<String>();
        for (String store : superStores) {
            ArrayList<String> productNamesProxy = new ArrayList<String>();
            try {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                pw = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                pw.println("GETPRODUCTSINSTORE");
                pw.println(store);
                pw.flush();
                productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE   
                pw.close();
                in.close();
                oos.close();
                ois.close(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String product : productNamesProxy) {
                String productInfo = ""; 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("PRODUCTINFO");
                    pw.println(product);
                    pw.println(store);
                    pw.flush();
                    String out = in.nextLine(); //SERVERREQUEST PRODUCTINFO
                    productsWithInfo.add(out);
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close(); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            productNames.addAll(productNamesProxy);
        }

        ArrayList<String> output = new ArrayList<String>();
        
        while (productsWithInfo.size() > 0) {
            double max = 0;
            int index = 0;
            for (int i = 0 ; i < productsWithInfo.size() ; i++) {
                String[] productSplit = productsWithInfo.get(i).split(";");
                Double price = Double.parseDouble(productSplit[4]);
                if (price >= max) {
                    max = price;
                    index = i;
                }
            }
            output.add(productsWithInfo.get(index));
            productsWithInfo.remove(index);
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<String> reversed = new ArrayList<String>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    /*
     * Sorts an Arraylist of products based on price
     * Sorts highest price to lowest if highToLow = true    NOT SURE IF I EVEN NEED THIS
     * if it's false, sorts lowest to highest
     */
    public ArrayList<String> sortQuantity(boolean highToLow) {
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            pw.println("GETSUPERSTORES");
            pw.flush();
            superStores = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES 
            pw.close();
            in.close();
            oos.close();
            ois.close();    
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> productsWithInfo = new ArrayList<String>();
        for (String store : superStores) {
            ArrayList<String> productNamesProxy = new ArrayList<String>();
            try {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                pw = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                pw.println("GETPRODUCTSINSTORE");
                pw.println(store);
                pw.flush();
                productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE    
                pw.close();
                in.close();
                oos.close();
                ois.close(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String product : productNamesProxy) {
                String productInfo = ""; 
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("PRODUCTINFO");
                    pw.println(product);
                    pw.println(store);
                    pw.flush();
                    String out = in.nextLine(); //SERVERREQUEST PRODUCTINFO
                    productsWithInfo.add(out);
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            productNames.addAll(productNamesProxy);
        }

        ArrayList<String> output = new ArrayList<String>();
        
        while (productsWithInfo.size() > 0) {
            double max = 0;
            int index = 0;
            for (int i = 0 ; i < productsWithInfo.size() ; i++) {
                String[] productSplit = productsWithInfo.get(i).split(";");
                int quantity = Integer.parseInt(productSplit[3]);
                if (quantity >= max) {
                    max = quantity;
                    index = i;
                }
            }
            output.add(productsWithInfo.get(index));
            productsWithInfo.remove(index);
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<String> reversed = new ArrayList<String>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    /*
     * Sorts an Arraylist of stores based on number of products sold
     * Sorts highest price to lowest if highToLow = true
     * if it's false, sorts lowest to highest
     */
    public ArrayList<String> sortProductsSold(ArrayList<String> stores , boolean highToLow) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> s = stores;
        while (s.size() > 0) {
            int max = 0;
            String picked = "";
            int index = 0;
            for (int i = 0 ; i < s.size() ; i++) {
                int count = 0;
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("VIEWPRODUCTS");
                    pw.println(s.get(i));
                    pw.flush();
                    ArrayList<String> products = (ArrayList<String>) ois.readObject();
                    for (String product : products) {
                        pw.println("NUMSALES");
                        pw.println(s.get(i));
                        pw.println(product);
                        pw.flush();
                        ArrayList<String> returned = (ArrayList<String>) ois.readObject();
                        int num = Integer.parseInt(returned.get(0));
                        count += num;
                    }
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (count > max) {
                    picked = s.get(i);
                    index = i;
                } 
            }   
            output.add(picked);
            s.remove(index); 
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<String> reversed = new ArrayList<String>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    public ArrayList<String> sortBestCustomers(ArrayList<String> customers , String storeName, boolean highToLow) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> s = customers;
        while (s.size() > 0) {
            int max = 0;
            String picked = "";
            int index = 0;
            for (int i = 0 ; i < s.size() ; i++) {
                int num = 0;
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("CUSTOMERPURCHASES");
                    pw.println(s.get(i));
                    pw.print(storeName);
                    pw.flush();
                    num = in.nextInt();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (num > max) {
                    picked = s.get(i);
                    index = i;
                } 
            }   
            output.add(picked);
            s.remove(index); 
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<String> reversed = new ArrayList<String>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    public ArrayList<String> sortBestProducts(ArrayList<String> products , String storeName, boolean highToLow) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> s = products;
        while (s.size() > 0) {
            int max = 0;
            String picked = "";
            int index = 0;
            for (int i = 0 ; i < s.size() ; i++) {
                int num = 0;
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("NUMSALES");
                    pw.println(storeName);
                    pw.print(s.get(i));
                    pw.flush();
                    num = in.nextInt();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (num > max) {
                    picked = s.get(i);
                    index = i;
                } 
            }   
            output.add(picked);
            s.remove(index); 
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<String> reversed = new ArrayList<String>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    /*
     * Sorts an Arraylist of products based on number sold
     * Sorts highest price to lowest if highToLow = true
     * if it's false, sorts lowest to highest
     */
    public ArrayList<String> sortMostShopped(ArrayList<String> stores , boolean highToLow) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> s = stores;
        while (s.size() > 0) {
            int max = 0;
            String picked = "";
            int index = 0;
            for (int i = 0 ; i < s.size() ; i++) {
                int count = 0;
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("CUSTOMERPURCHASES");
                    pw.println(username);
                    pw.flush();
                    int products = in.nextInt();
                    count += products;
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (count > max) {
                    picked = s.get(i);
                    index = i;
                } 
            }   
            output.add(picked);
            s.remove(index); 
        }
        if (highToLow) {
            return output;
        } else {
            ArrayList<String> reversed = new ArrayList<String>();
            for (int i = output.size() - 1 ; i >= 0 ; i--) {
                reversed.add(output.get(i));
            }
            return reversed;
        }
    }

    /*
     * Gets all product in users.txt that have a name, description, or store that matches the search parameter
     */
    public ArrayList<String> searchProducts(String searchParameter) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            PrintWriter pw;
            Scanner in;
            ObjectOutputStream oos;
            ObjectInputStream ois;
            pw = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            pw.println("GETSUPERSTORES");
            pw.flush();
            myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETSUPERSTORES
            pw.close();
            in.close();
            oos.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String store : myStoreNames) {
            var productNamesProxy = new ArrayList<String>();
            try {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                pw = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                pw.println("GETPRODUCTSINSTORE");
                pw.println(store);
                pw.flush();
                productNamesProxy = (ArrayList<String>) ois.readObject(); //SERVERREQUEST GETPRODCUTSINSTORE
                pw.close();
                in.close();
                oos.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String line : productNamesProxy) {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                String productInfo = "";
                try {
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("PRODUCTINFO");
                    pw.println(line);
                    pw.println(store);
                    productInfo = in.nextLine();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] productSplit = productInfo.split(";");  //SERVERREQUEST PRODCUT INFO
                if (productSplit[0].contains(searchParameter)) {
                    output.add(line);
                } else if (productSplit[1].contains(searchParameter)) {
                    output.add(line);
                } else if (productSplit[2].contains(searchParameter)) {
                    output.add(line);
                } 
            }
        }
        
        return output;
    }

    public ArrayList<String> importProducts(String path) {
        try {
            var output = new ArrayList<String>();
            BufferedReader buf = new BufferedReader(new FileReader(new File(path)));
            String s = buf.readLine();
            while (true) {
                if (s == null) {
                    break;
                } else {
                    output.add(s);
                    s = buf.readLine();
                }  
            }
            return output;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //Changed return type from void to boolean 11/13/22
    public boolean exportProducts(String path , String storeName) {
        try {
            ArrayList<String> products = new ArrayList<String>(); //SERVERREQUEST - VIEWPRODUCTS
            try {
                PrintWriter pw;
                Scanner in;
                ObjectOutputStream oos;
                ObjectInputStream ois;
                pw = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                pw.println("VIEWPRODUCTS");
                pw.println(storeName);
                pw.flush();
                products = (ArrayList<String>) ois.readObject();
                pw.close();
                in.close();
                oos.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            PrintWriter filepw = new PrintWriter(new FileWriter(new File(path)));
            for (String line : products) {
                filepw.print(line + "\n");

            }
            filepw.flush();
            filepw.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewProductsInCarts(ArrayList<String> info) {
        JFrame viewcart = new JFrame("THE MARKETPLACE");
        Container viewcartpanel = viewcart.getContentPane();
        viewcartpanel.setLayout(new BorderLayout());

        JPanel viewcartNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>Products in Customers Carts</h1></html>");     //Createds title
        viewcartNorth.add(title);
        viewcart.add(viewcartNorth, BorderLayout.NORTH);

        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (String s : info) {
            JPanel jp = new JPanel();
            JLabel jl = new JLabel(s);
            jp.add(jl);
            panels.add(jp);
        }

        viewcart.add(viewcartNorth, BorderLayout.NORTH);
        viewcart.add(productsPanel, BorderLayout.CENTER);

        JPanel viewcartSouth = new JPanel(new FlowLayout());

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                try {
                    PrintWriter pw;
                    Scanner in;
                    ObjectOutputStream oos;
                    ObjectInputStream ois;
                    pw = new PrintWriter(socket.getOutputStream());
                    in = new Scanner(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    pw.println("VIEWSTORES");
                    pw.println(username);
                    pw.flush();
                    myStoreNames = (ArrayList<String>) ois.readObject(); //SERVERREQUEST VIEWSTORES
                    sellerMain(myStoreNames);
                    viewcart.dispose();
                    pw.close();
                    in.close();
                    oos.close();
                    ois.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        
        });
        viewcartSouth.add(back);
        viewcart.add(viewcartSouth, BorderLayout.SOUTH);


        viewcart.pack();
        viewcart.setSize(600, 400);
        viewcart.setLocationRelativeTo(null);
        viewcart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewcart.setVisible(true);
        viewcart.requestFocus();
    }
}