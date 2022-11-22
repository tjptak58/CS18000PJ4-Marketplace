
import java.util.ArrayList;
import javax.swing.*;

import java.awt.*; 
import java.awt.event.*;

/**
 * Main method for project that implements the marketplace
 *
 * @author
 *
 * @verion 11/14/22
 */

public class MarketPlaceGUI extends JComponent implements Runnable{
    /**
     *Static string that stores the username of the logged in buyer or seller
     */
    static String username;

    /**
     * Static booleans to determine whether a buyer or seller has logged in
     */
    static boolean loggedInAsBuyer;
    static boolean loggedInAsSeller;
    Person user;
    ArrayList<Store> marketPlace = new ArrayList<Store>();
    ArrayList<Product> superListOfProducts = new ArrayList<Product>();
    ArrayList<Buyer> buyers = new ArrayList<Buyer>();
    ArrayList<Seller> sellers = new ArrayList<Seller>();
        

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MarketPlaceGUI());
    }

    public MarketPlaceGUI() {
        
    }

    public void run() {
        Product honda = new Product("Honda", "tptak", "Good Car", 10, 100.0);
        Product ford = new Product("Ford", "tptak", "Great Car", 20, 1000.0);
        Product bugati = new Product("Bugati", "tptak", "Crazy Car", 30, 10000.0);
        Store tptak = new Store("TJ", "tptak", "Car.txt");
        Product lamborgini = new Product("Lamborgini", "tptak2", "Good Car", 10, 100.0);   //TEST ITEMS
        Product tesla = new Product("Tesla", "tptak2", "Elon musk's baby", 20, 1000.0);
        Product ferrari = new Product("Ferrari", "tptak2", "Crazy Car", 30, 10000.0);
        Store tptak2 = new Store("TJ", "tptak", "Car.txt");
        Seller tj = new Seller("TJ", "Ptak", username, "tj.txt");
        Buyer user1 = new Buyer("james123", "12435" , "james@gmail.com" , "cart.txt" , "hist.txt");
        Seller user2 = new Seller("james123", "12435" , "james@gmail.com", "file.txt");

        tptak.addProduct(honda);
        tptak.addProduct(ford);
        tptak.addProduct(bugati);
        marketPlace.add(tptak);
        tptak2.addProduct(lamborgini);
        tptak2.addProduct(tesla);
        tptak2.addProduct(ferrari);
        marketPlace.add(tptak2);
        sellers.add(tj);

        loggedInAsSeller = true;
       
        if (loggedInAsBuyer) {
            for (int i = 0; i < marketPlace.size(); i++ ) {
                for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                    superListOfProducts.add(marketPlace.get(i).getProducts().get(j));
                }
            }
            buyerMain(user1);
        } else if (loggedInAsSeller) {
            for (int i = 0; i < marketPlace.size(); i++ ) {
                for (int j = 0; j < marketPlace.get(i).getProducts().size(); j++) {
                    superListOfProducts.add(marketPlace.get(i).getProducts().get(j));
                }
            }
            sellerMain(user2);
            
        }
    }

    /*
     * Creates the Home Page for Buyer
     */
    public void buyerMain(Buyer user) {
        JFrame buyerMain = new JFrame("THE MARKETPLACE");
        Container buyerMainPanel = buyerMain.getContentPane();
        buyerMainPanel.setLayout(new BorderLayout());

        JPanel buyerMainNorth = new JPanel(new FlowLayout());
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Refreshes the Home Page
                buyerMain.dispose();
                buyerMain(user);
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
                displayPurchaseHistory(user);
                buyerMain.dispose();
            }
        
        });
        JButton viewCart = new JButton("View Cart");                
        viewCart.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the shopping cart of a user
                displayCart(user);
                buyerMain.dispose();
            }
        
        });
        JButton editAccount = new JButton("Edit Account");
        editAccount.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the page to edit a buyer account
                editAccount(user);
                buyerMain.dispose();
            }
        
        });
        JButton logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Link to the page to goodbye page
                goodbye();
                buyerMain.dispose();
            }
        
        });
        buyerMainSouth.add(purchaseHistory);
        buyerMainSouth.add(viewCart);
        buyerMainSouth.add(editAccount);
        buyerMainSouth.add(logout);
        buyerMain.add(buyerMainSouth, BorderLayout.SOUTH);

        JPanel buyerMainCentral = new JPanel(new BorderLayout());
        JPanel searchAndSort = new JPanel(new FlowLayout());
        JTextField searchText = new JTextField(10);
        JButton searchButton = new JButton("Search");
        String[] s = {"Sort the menu","Sort by price High to Low","Sort by price Low to High","Sort by quantity High to Low","Sort by quantity Low to Hight"};
        JComboBox sortBox = new JComboBox(s);
        JButton sortButton = new JButton("Sort");
        searchAndSort.add(searchText);                    //Creates search and sort bars
        searchAndSort.add(searchButton);
        searchAndSort.add(sortBox);
        searchAndSort.add(sortButton);
        buyerMainCentral.add(searchAndSort , BorderLayout.NORTH);

        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (Product p : superListOfProducts) {
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel(p.getProductName());
            product1.add(product1Text);
            JButton product1Add = new JButton("Add To Cart");
            product1Add.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {           //ACTION LISTENER - Add to cart Ask the user how many they want to buy
                    String s = JOptionPane.showInputDialog("How many items do you want to buy?");
                    int available = p.getQuantity();
                    Product chosen = p;
                    chosen.setQuantity(Integer.parseInt(s));
                    user.addToCart(chosen , available);
                }
            
            });
            product1.add(product1Add);
            JButton product1Info = new JButton("Info");
            product1Info.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {   
                    displayProductInfo(user , p);      //ACTION LISTENER - Sends the user to the Product info page for the specific product
                    buyerMain.dispose();
                    
                }
            
            });
            product1.add(product1Info);
            panels.add(product1);
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
    public void displayProductInfo(Buyer user, Product p) {
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
        JLabel productNameLabel = new JLabel(String.format("Product Name: %s" , p.getProductName()));
        JLabel storeNameLabel = new JLabel(String.format("Store Name: %s" , p.getStoreName()));
        JLabel descriptionLabel = new JLabel(String.format("Description: %s" , p.getDescription()));
        JLabel quantityLabel = new JLabel(String.format("Quantity: %d" , p.getQuantity()));
        JLabel priceLabel = new JLabel(String.format("Price: %.2f" , p.getPrice()));
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
                user.addToCart(p , Integer.parseInt(s));
            }
        
        });
        JButton contactSeller = new JButton("Contact Seller"); //WHAT IS THIS FUNCTION SUPPOSED TO DO?
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Send the user back to the buyer home
                buyerMain(user);
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
    public void displayPurchaseHistory(Buyer user) {
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
        ArrayList<Product> phistory = user.getPurchased();
        for (Product p : phistory) {
            purchaseHistoryCentral.add(new JLabel(p.toString()));  //Gets the user purchase history and displays it : how does marketplace do this?
        }
        JScrollPane scrollPane = new JScrollPane(purchaseHistoryCentral); 
        purchaseHistory.add(scrollPane , BorderLayout.CENTER);

        JPanel purchaseHistorySouth = new JPanel(new FlowLayout());
        JTextField exportText = new JTextField("purchaseHistory.txt");  //FilePath
        JButton exportProduct = new JButton("Export to File"); //Adds bottom buttons
        exportProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Exports the purchase history to a file
                user.exportHistory(exportText.getText());
            }
        
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {           //Send the user back to the buyer home
                buyerMain(user);
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
    public void displayCart(Buyer user) {
        JFrame cart = new JFrame("THE MARKETPLACE");
        Container cartPanel = cart.getContentPane();
        cartPanel.setLayout(new BorderLayout());

        JPanel cartNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>SHOPPING CART</h1></html>");     //Createds title
        cartNorth.add(title);
        cart.add(cartNorth, BorderLayout.NORTH);

        JPanel cartSouth = new JPanel(new FlowLayout());
        JButton continueShopping = new JButton("Continue Shopping");
        continueShopping.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                cart.dispose();      //ACTION LISTENER - Sends the user back to the main page
                buyerMain(user);
            }
        
        });
        JButton checkout = new JButton("Checkout");                 
        checkout.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure?");      //ACTION LISTENER - Sends the user back to the main page
                if (choice == JOptionPane.YES_OPTION) {
                    for (Product p : user.getShoppingCart()) {
                        user.purchase(p);
                    }
                    cart.dispose();
                    displayCart(user);
                    JOptionPane.showMessageDialog(null, "Purchase Confirmed", "THE MARKETPLACE", JOptionPane.PLAIN_MESSAGE);
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
        ArrayList<Product> c = user.getShoppingCart();
        for (Product p : c) {    
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel(String.format("Name: %s, Store: %s, Quantity: %d, Price %.2f" //Populates shopping cart on screen
            , p.getProductName() , p.getStoreName(), p.getQuantity(), p.getPrice()));
            product1.add(product1Text);
            JButton delete = new JButton("Delete");
            delete.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {   
                    user.removeFromCart(p);      //ACTION LISTENER - removes an item from the cart and updates the page
                    cart.dispose();
                    displayCart(user);
                    
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
    public void editAccount(Buyer user) {
        JFrame account = new JFrame("THE MARKETPLACE");
        Container accountPanel = account.getContentPane();
        accountPanel.setLayout(new BorderLayout());

        JPanel accountNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>EDIT ACCOUNT</h1></html>");     //Createds title
        accountNorth.add(title);
        account.add(accountNorth, BorderLayout.NORTH);

       

        JPanel accountCentral = new JPanel(new BorderLayout());
        JPanel accountFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(accountFields, BoxLayout.Y_AXIS); //Variable number of products from product list
        accountFields.setLayout(boxlayout);
        
        JPanel username = new JPanel(new FlowLayout());
        JLabel usernameText = new JLabel("Username: ");
        JTextField usernameField = new JTextField("username"); //prepopulated with username
        username.add(usernameText);
        username.add(usernameField);
        JPanel password = new JPanel(new FlowLayout());
        JLabel passwordText = new JLabel("Password: ");
        JTextField passwordField = new JTextField("password"); //prepopulated with username
        password.add(passwordText);
        password.add(passwordField);
        JPanel email = new JPanel(new FlowLayout());
        JLabel emailText = new JLabel("Email: ");
        JTextField emailField = new JTextField("email"); //prepopulated with username
        email.add(emailText);
        email.add(emailField);

        JPanel accountSouth = new JPanel(new FlowLayout());
        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                account.dispose();      //ACTION LISTENER - Sends the user back to the main page
                user.setUsername(usernameText.getText());
                user.setPassword(passwordText.getText());
                user.setEmail(emailText.getText());
                buyerMain(user);
                
            }
        
        });
        
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                account.dispose();      //ACTION LISTENER - Sends the user back to the main page
                buyerMain(user);
                
            }
        
        });
        accountSouth.add(update);
        accountSouth.add(back);
        account.add(accountSouth, BorderLayout.SOUTH);

        accountFields.add(username);
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

    public void editAccount(Seller user) {
        JFrame account = new JFrame("THE MARKETPLACE");
        Container accountPanel = account.getContentPane();
        accountPanel.setLayout(new BorderLayout());

        JPanel accountNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>EDIT ACCOUNT</h1></html>");     //Createds title
        accountNorth.add(title);
        account.add(accountNorth, BorderLayout.NORTH);

        JPanel accountCentral = new JPanel(new BorderLayout());
        JPanel accountFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(accountFields, BoxLayout.Y_AXIS); //Variable number of products from product list
        accountFields.setLayout(boxlayout);
        
        JPanel username = new JPanel(new FlowLayout());
        JLabel usernameText = new JLabel("Username: ");
        JTextField usernameField = new JTextField("username"); //prepopulated with username
        username.add(usernameText);
        username.add(usernameField);
        JPanel password = new JPanel(new FlowLayout());
        JLabel passwordText = new JLabel("Password: ");
        JTextField passwordField = new JTextField("password"); //prepopulated with username
        password.add(passwordText);
        password.add(passwordField);
        JPanel email = new JPanel(new FlowLayout());
        JLabel emailText = new JLabel("Email: ");
        JTextField emailField = new JTextField("email"); //prepopulated with username
        email.add(emailText);
        email.add(emailField);

        JPanel accountSouth = new JPanel(new FlowLayout());
        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                account.dispose();      //ACTION LISTENER - Sends the user back to the main page
                user.setUsername(usernameText.getText());
                user.setPassword(passwordText.getText());
                user.setEmail(emailText.getText());
                sellerMain(user);
                
            }
        
        });
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                account.dispose();      //ACTION LISTENER - Sends the user back to the main page
                sellerMain(user);
                
                
            }
        
        });
        accountSouth.add(update);
        accountSouth.add(back);
        account.add(accountSouth, BorderLayout.SOUTH);

        accountFields.add(username);
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
    public void sellerMain(Seller user) {
        JFrame sellerMain = new JFrame("THE MARKETPLACE");
        Container sellerMainPanel = sellerMain.getContentPane();
        sellerMainPanel.setLayout(new BorderLayout());

        JPanel sellerMainNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>THE MARKETPLACE</h1></html>");     //Creates title
        sellerMainNorth.add(title);
        JButton addStore = new JButton("Add Store");
        addStore.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {   
                String s = JOptionPane.showInputDialog(null, "Enter Store Name:");     //ACTION LISTENER - adds a store
                user.createStore(user.getUsername(), s, "file.txt"); //Temporary File Name
                sellerMain.dispose();
                sellerMain(user);
                
            }
        
        });
        sellerMainNorth.add(addStore);
        sellerMain.add(sellerMainNorth, BorderLayout.NORTH);

        JPanel sellerMainCentral = new JPanel(new BorderLayout());
        JLabel yourStores = new JLabel("<html><h2>Your Stores:</h2></html>"); 
        sellerMainCentral.add(yourStores, BorderLayout.NORTH);

        JPanel storesPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(storesPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        storesPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (Store s : user.getStores()) {
            JPanel store1 = new JPanel(new FlowLayout());
            JLabel store1Text = new JLabel(s.getStoreName());
            store1.add(store1Text);
            JButton store1Edit = new JButton("Edit");  
            store1Edit.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {           //ACTION LISTENER links to edit page
                    displayEditStore(user, s);
                    sellerMain.dispose();
                    
                }
            
            });
            store1.add(store1Edit);
            JButton store1Delete = new JButton("Delete");
            store1Delete.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {  
                    user.deleteStore(s);
                    sellerMain.dispose();                          //ACTION LISTENER Deletes store
                    sellerMain(user);
                    
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

        JPanel sellerMainSouth = new JPanel(new FlowLayout());
        JButton stats = new JButton("Statistics");
        stats.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayStatistics(user);          //Links to stats page
                sellerMain.dispose();
            }
        
        });
        JButton editAccount = new JButton("Edit Account");
        editAccount.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                editAccount(user);          //Links to edit account page
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
        sellerMainSouth.add(stats);
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
    public void displayEditStore(Seller user , Store store) {
        JFrame editStore = new JFrame("THE MARKETPLACE");
        Container editStorePanel = editStore.getContentPane();
        editStorePanel.setLayout(new BorderLayout());

        JPanel editStoreNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>" + store.getStoreName() + "</h1></html>");     //Creates title
        editStoreNorth.add(title);
        JButton addProduct = new JButton("Add Product");
        addProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayAddProduct(user , store);
                editStore.dispose();
            }
        
        });
        editStoreNorth.add(addProduct);
        editStore.add(editStoreNorth, BorderLayout.NORTH);

        JPanel editStoreCentral = new JPanel(new BorderLayout());
        JPanel yourStoresWindow = new JPanel(new FlowLayout());
        JLabel yourStores = new JLabel("<html>Products in:</html>"); 
        JTextField storeTitle = new JTextField(store.getStoreName());
        JButton storeTitleUpdate = new JButton("Update Store Title");
        storeTitleUpdate.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                store.setStoreName(storeTitle.getText());
                editStore.dispose();
                displayEditStore(user, store);                 //ACTION Listener changes store name
            }
        
        });
        yourStoresWindow.add(yourStores);
        yourStoresWindow.add(storeTitle);
        yourStoresWindow.add(storeTitleUpdate);

        editStoreCentral.add(yourStoresWindow, BorderLayout.NORTH);

        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (Product p : store.getProducts()) {
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel(p.getProductName());
            product1.add(product1Text);
            JButton product1Edit = new JButton("Edit");  
            product1Edit.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {  
                    editStore.dispose();          //Links to stats page
                    displayEditProduct(user,store, p);
                    
                }
            
            });
            product1.add(product1Edit);
            JButton product1Delete = new JButton("Delete");
            product1Delete.addActionListener(new ActionListener() {      
                public void actionPerformed(ActionEvent e) {  
                    store.removeProduct(p);        //Links to stats page
                    editStore.dispose();
                    displayEditStore(user, store);

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
            public void actionPerformed(ActionEvent e) {  
                ArrayList<Product> imported = user.importProducts(importText.getText());
                for (Product p : imported) {        //ACTIONLISTENER Import PRODUCATS
                    store.addProduct(p);
                }
                editStore.dispose();
                displayEditStore(user, store);


            }
        
        });
        JTextField exportText = new JTextField(10);  //FilePath
        JButton exportProduct = new JButton("Export"); //Adds bottom buttons
        exportProduct.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                user.exportProducts(exportText.getText(), store.getProducts());  //ACTIONLISTENER EXPORT PRODUCATS
            }
        
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                sellerMain(user);
                editStore.dispose();
            }
        
        });
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
    public void displayEditProduct(Seller user ,Store s,  Product p) {
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
        JTextField productNameField = new JTextField(p.getProductName()); //prepopulated with product name
        productName.add(productNameText);
        productName.add(productNameField);

        JPanel storeName = new JPanel(new FlowLayout());
        JLabel storeNameText = new JLabel("Store Name: ");
        JTextField storeNameField = new JTextField(p.getStoreName()); //prepopulated with store name
        storeName.add(storeNameText);
        storeName.add(storeNameField);

        JPanel description = new JPanel(new FlowLayout());
        JLabel descriptionText = new JLabel("Description: ");
        JTextField descriptionField = new JTextField(p.getDescription()); //prepopulated with descirption
        description.add(descriptionText);
        description.add(descriptionField);

        JPanel quantity = new JPanel(new FlowLayout());
        JLabel quantityText = new JLabel("Quantity: ");
        JTextField quantityField = new JTextField(Integer.toString(p.getQuantity())); //prepopulated with descirption
        quantity.add(quantityText);
        quantity.add(quantityField);

        JPanel price = new JPanel(new FlowLayout());
        JLabel priceText = new JLabel("Price: ");
        JTextField priceField = new JTextField(Double.toString(p.getPrice())); //prepopulated with descirption
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
                for (Product prod : s.getProducts()) {
                    if (p.getProductName().equals(prod.getProductName())) {
                        prod.setProductName(productNameText.getText()); 
                        prod.setStoreName(storeNameText.getText());  
                        prod.setDescription(descriptionText.getText());
                        prod.setQuantity(Integer.parseInt(quantityText.getText()));
                        prod.setPrice(Double.parseDouble(priceText.getText()));
                    }
                }
                displayEditStore(user, s);
                editProduct.dispose();
            }
        
        });

        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayEditStore(user, s);
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
    public void displayAddProduct(Seller user , Store s) {                               //UPDATE TO ADD EVERYTHING
        JFrame editProduct = new JFrame("THE MARKETPLACE");
        Container editProductPanel = editProduct.getContentPane();
        editProductPanel.setLayout(new BorderLayout());

        JPanel editProductNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>ADD PRODUCT</h1></html>");     //Createds title
        editProductNorth.add(title);
        editProduct.add(editProductNorth, BorderLayout.NORTH);

        

        JPanel editProductCentral = new JPanel(new BorderLayout());
        JPanel editProductFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(editProductFields, BoxLayout.Y_AXIS); //Variable number of products from product list
        editProductFields.setLayout(boxlayout);
        
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

        JPanel editProductSouth = new JPanel(new FlowLayout());
        JButton create = new JButton("Create");
        create.addActionListener(new ActionListener() {   
            public void actionPerformed(ActionEvent e) {
                Product p = new Product(productNameText.getText(), s.getStoreName(), descriptionText.getText(), 
                Integer.parseInt(quantityText.getText()), Double.parseDouble(priceText.getText()));      
                s.addProduct(p);
                editProduct.dispose();
                displayEditStore(user, s);
                
            }
        
        });
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        back.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {  
                displayEditStore(user, s);
                editProduct.dispose();
            }
        
        });
        editProductSouth.add(create);
        editProductSouth.add(back);
        editProduct.add(editProductSouth, BorderLayout.SOUTH);

        editProductFields.add(productName);
        editProductFields.add(description);
        editProductFields.add(quantity);
        editProductFields.add(price);

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

    public void displayStatistics(Seller user) {
        JFrame stats = new JFrame("THE MARKETPLACE");
        Container statsPanel = stats.getContentPane();
        statsPanel.setLayout(new BorderLayout());

        JPanel statsNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>STATISTICS</h1></html>");     //Createds title
        statsNorth.add(title);
        stats.add(statsNorth, BorderLayout.NORTH);
        
        JPanel statsCentral = new JPanel(new BorderLayout());
        JPanel sort = new JPanel(new FlowLayout());
        String[] s = {"Sort the menu","Sort by price High to Low","Sort by price Low to High","Sort by quantity High to Low","Sort by quantity Low to Hight"};
        JComboBox sortBox = new JComboBox(s);
        JButton sortButton = new JButton("Sort");
        sort.add(sortBox);
        sort.add(sortButton);
        statsCentral.add(sort , BorderLayout.NORTH);
        JPanel statsItems = new JPanel();
        BoxLayout boxlayout = new BoxLayout(statsItems, BoxLayout.Y_AXIS); //Add Product info
        statsItems.setLayout(boxlayout);
        ArrayList<String> customers = new ArrayList<String>();
        customers.add("CAT");
        customers.add("CAT");
        customers.add("CAT");  //Dummy purchases
        customers.add("CAT");
        customers.add("CAT");
        customers.add("CAT");
        for (String c : customers) {
            statsItems.add(new JLabel(c));
        }
        JScrollPane scrollPane = new JScrollPane(statsItems); 
        statsCentral.add(scrollPane , BorderLayout.CENTER);
        stats.add(statsCentral,  BorderLayout.CENTER);

        JPanel statsSouth = new JPanel(new FlowLayout());
        JTextField exportText = new JTextField("statistics.txt");  //FilePath
        JButton exportProduct = new JButton("Export to File"); //Adds bottom buttons
        JButton back = new JButton("Back");
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

    public void goodbye() {
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

}