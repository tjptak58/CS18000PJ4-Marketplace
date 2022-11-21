import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import javax.naming.ldap.PagedResultsResponseControl;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.Content;

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
    static String user;

    /**
     * Static booleans to determine whether a buyer or seller has logged in
     */
    static boolean loggedInAsBuyer;
    static boolean loggedInAsSeller;
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
        loggedInAsSeller = true;

        
        
        if (loggedInAsBuyer) {
            
        } else if (loggedInAsSeller) {
            displayStatistics();
        }
    }

    /*
     * Creates the Home Page for Buyer
     */
    public void buyerMain() {
        JFrame buyerMain = new JFrame("THE MARKETPLACE");
        Container buyerMainPanel = buyerMain.getContentPane();
        buyerMainPanel.setLayout(new BorderLayout());

        JPanel buyerMainNorth = new JPanel(new FlowLayout());
        JButton refresh = new JButton("Refresh"); 
        JLabel title = new JLabel("<html><h1>THE MARKETPLACE</h1></html>");     //Createds title
        buyerMainNorth.add(title);
        buyerMainNorth.add(refresh);
        buyerMain.add(buyerMainNorth, BorderLayout.NORTH);

        JPanel buyerMainSouth = new JPanel(new FlowLayout());
        JButton purchaseHistory = new JButton("Purchase History");
        JButton viewCart = new JButton("View Cart");                 //Displays Bottom Buttons
        JButton editAccount = new JButton("Edit Account");
        JButton logout = new JButton("Logout");
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
        for (int i = 0 ; i < 10 ; i++) {
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel("INFORMATION : : : ");
            product1.add(product1Text);
            JButton product1Add = new JButton("Add To Cart");
            product1.add(product1Add);
            JButton product1Info = new JButton("Info");
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
    public void displayProductInfo() {
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
        JLabel productNameLabel = new JLabel(String.format("Product Name: pname"));
        JLabel storeNameLabel = new JLabel(String.format("Store Name: sname"));
        JLabel descriptionLabel = new JLabel(String.format("Description: desc"));
        JLabel quantityLabel = new JLabel(String.format("Quantity: q"));
        JLabel priceLabel = new JLabel(String.format("Price: price"));
        productInfoCentral.add(productNameLabel);
        productInfoCentral.add(storeNameLabel);
        productInfoCentral.add(descriptionLabel);
        productInfoCentral.add(quantityLabel);
        productInfoCentral.add(priceLabel);
        productInfo.add(productInfoCentral , BorderLayout.CENTER);

        JPanel productInfoSouth = new JPanel(new FlowLayout());
        JButton addToCart = new JButton("Add to Cart");
        JButton contactSeller = new JButton("Contact Seller"); //Adds bottom buttons
        JButton back = new JButton("Back");
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
    public void displayPurchaseHistory() {
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
        ArrayList<String> purchases = new ArrayList<String>();
        purchases.add("CAT");
        purchases.add("CAT");
        purchases.add("CAT");        //Dummy purchases
        purchases.add("CAT");
        purchases.add("CAT");
        purchases.add("CAT");
        for (String p : purchases) {
            purchaseHistoryCentral.add(new JLabel(p));
        }
        JScrollPane scrollPane = new JScrollPane(purchaseHistoryCentral); 
        purchaseHistory.add(scrollPane , BorderLayout.CENTER);

        JPanel purchaseHistorySouth = new JPanel(new FlowLayout());
        JTextField importText = new JTextField(10);  //FilePath
        JButton importProduct = new JButton("Import");
        JTextField exportText = new JTextField(10);  //FilePath
        JButton exportProduct = new JButton("Export"); //Adds bottom buttons
        JButton back = new JButton("Back");
        purchaseHistorySouth.add(importText);
        purchaseHistorySouth.add(importProduct);
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
    public void displayCart() {
        JFrame cart = new JFrame("THE MARKETPLACE");
        Container cartPanel = cart.getContentPane();
        cartPanel.setLayout(new BorderLayout());

        JPanel cartNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>SHOPPING CART</h1></html>");     //Createds title
        cartNorth.add(title);
        cart.add(cartNorth, BorderLayout.NORTH);

        JPanel cartSouth = new JPanel(new FlowLayout());
        JButton continueShopping = new JButton("Continue Shopping");
        JButton checkout = new JButton("Checkout");                 //Displays Bottom Buttons
        cartSouth.add(continueShopping);
        cartSouth.add(checkout);
        cart.add(cartSouth, BorderLayout.SOUTH);

        JPanel cartCentral = new JPanel(new BorderLayout());
        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (int i = 0 ; i < 10 ; i++) {    //Dummy Products
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel("INFORMATION : : : ");
            product1.add(product1Text);
            JButton delete = new JButton("Delete");
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
    public void editAccount() {
        JFrame account = new JFrame("THE MARKETPLACE");
        Container accountPanel = account.getContentPane();
        accountPanel.setLayout(new BorderLayout());

        JPanel accountNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>EDIT ACCOUNT</h1></html>");     //Createds title
        accountNorth.add(title);
        account.add(accountNorth, BorderLayout.NORTH);

        JPanel accountSouth = new JPanel(new FlowLayout());
        JButton update = new JButton("Update");
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        accountSouth.add(update);
        accountSouth.add(back);
        account.add(accountSouth, BorderLayout.SOUTH);

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
    public void sellerMain() {
        JFrame sellerMain = new JFrame("THE MARKETPLACE");
        Container sellerMainPanel = sellerMain.getContentPane();
        sellerMainPanel.setLayout(new BorderLayout());

        JPanel sellerMainNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>THE MARKETPLACE</h1></html>");     //Creates title
        sellerMainNorth.add(title);
        JButton addStore = new JButton("Add Store");
        sellerMainNorth.add(addStore);
        sellerMain.add(sellerMainNorth, BorderLayout.NORTH);

        JPanel sellerMainCentral = new JPanel(new BorderLayout());
        JLabel yourStores = new JLabel("<html><h2>Your Stores:</h2></html>"); 
        sellerMainCentral.add(yourStores, BorderLayout.NORTH);

        JPanel storesPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(storesPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        storesPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (int i = 0 ; i < 10 ; i++) {
            JPanel store1 = new JPanel(new FlowLayout());
            JLabel store1Text = new JLabel("INFORMATION : : : ");
            store1.add(store1Text);
            JButton store1Edit = new JButton("Edit");  //Creates dummy stores
            store1.add(store1Edit);
            JButton store1Delete = new JButton("Delete");
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
        JButton editAccount = new JButton("Edit Account");
        JButton logout = new JButton("Logout");
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
    public void displayEditStore() {
        JFrame editStore = new JFrame("THE MARKETPLACE");
        Container editStorePanel = editStore.getContentPane();
        editStorePanel.setLayout(new BorderLayout());

        JPanel editStoreNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>Individual Store Name</h1></html>");     //Creates title
        editStoreNorth.add(title);
        JButton addProduct = new JButton("Add Product");
        editStoreNorth.add(addProduct);
        editStore.add(editStoreNorth, BorderLayout.NORTH);

        JPanel editStoreCentral = new JPanel(new BorderLayout());
        JPanel yourStoresWindow = new JPanel(new FlowLayout());
        JLabel yourStores = new JLabel("<html>Products in:</html>"); 
        JTextField storeTitle = new JTextField("Individual Store Name");
        JButton storeTitleUpdate = new JButton("Update Store Title");
        yourStoresWindow.add(yourStores);
        yourStoresWindow.add(storeTitle);
        yourStoresWindow.add(storeTitleUpdate);

        editStoreCentral.add(yourStoresWindow, BorderLayout.NORTH);

        JPanel productsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS); //Variable number of products from product list
        productsPanel.setLayout(boxlayout);
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (int i = 0 ; i < 10 ; i++) {
            JPanel product1 = new JPanel(new FlowLayout());
            JLabel product1Text = new JLabel("INFORMATION : : : ");
            product1.add(product1Text);
            JButton product1Edit = new JButton("Edit");  //Creates dummy stores
            product1.add(product1Edit);
            JButton product1Delete = new JButton("Delete");
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
        JTextField exportText = new JTextField(10);  //FilePath
        JButton exportProduct = new JButton("Export"); //Adds bottom buttons
        JButton back = new JButton("Back");
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
    public void displayEditProduct() {
        JFrame editProduct = new JFrame("THE MARKETPLACE");
        Container editProductPanel = editProduct.getContentPane();
        editProductPanel.setLayout(new BorderLayout());

        JPanel editProductNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>EDIT PRODUCT</h1></html>");     //Createds title
        editProductNorth.add(title);
        editProduct.add(editProductNorth, BorderLayout.NORTH);

        JPanel editProductSouth = new JPanel(new FlowLayout());
        JButton update = new JButton("Update");
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        editProductSouth.add(update);
        editProductSouth.add(back);
        editProduct.add(editProductSouth, BorderLayout.SOUTH);

        JPanel editProductCentral = new JPanel(new BorderLayout());
        JPanel editProductFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(editProductFields, BoxLayout.Y_AXIS); //Variable number of products from product list
        editProductFields.setLayout(boxlayout);
        
        JPanel productName = new JPanel(new FlowLayout());
        JLabel productNameText = new JLabel("Product Name: ");
        JTextField productNameField = new JTextField("product name"); //prepopulated with product name
        productName.add(productNameText);
        productName.add(productNameField);

        JPanel storeName = new JPanel(new FlowLayout());
        JLabel storeNameText = new JLabel("Store Name: ");
        JTextField storeNameField = new JTextField("store name"); //prepopulated with store name
        storeName.add(storeNameText);
        storeName.add(storeNameField);

        JPanel description = new JPanel(new FlowLayout());
        JLabel descriptionText = new JLabel("Description: ");
        JTextField descriptionField = new JTextField("description"); //prepopulated with descirption
        description.add(descriptionText);
        description.add(descriptionField);

        JPanel quantity = new JPanel(new FlowLayout());
        JLabel quantityText = new JLabel("Quantity: ");
        JTextField quantityField = new JTextField("quanitity"); //prepopulated with descirption
        quantity.add(quantityText);
        quantity.add(quantityField);

        JPanel price = new JPanel(new FlowLayout());
        JLabel priceText = new JLabel("Price: ");
        JTextField priceField = new JTextField("price"); //prepopulated with descirption
        price.add(priceText);
        price.add(priceField);

        editProductFields.add(productName);
        editProductFields.add(storeName);
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

    /*
     * Opens a page for a seller to add a product
     */
    public void displayAddProduct() {                               //UPDATE TO ADD EVERYTHING
        JFrame editProduct = new JFrame("THE MARKETPLACE");
        Container editProductPanel = editProduct.getContentPane();
        editProductPanel.setLayout(new BorderLayout());

        JPanel editProductNorth = new JPanel(new FlowLayout());
        JLabel title = new JLabel("<html><h1>ADD PRODUCT</h1></html>");     //Createds title
        editProductNorth.add(title);
        editProduct.add(editProductNorth, BorderLayout.NORTH);

        JPanel editProductSouth = new JPanel(new FlowLayout());
        JButton create = new JButton("Create");
        JButton back = new JButton("Back");                 //Displays Bottom Buttons
        editProductSouth.add(create);
        editProductSouth.add(back);
        editProduct.add(editProductSouth, BorderLayout.SOUTH);

        JPanel editProductCentral = new JPanel(new BorderLayout());
        JPanel editProductFields = new JPanel();
        BoxLayout boxlayout = new BoxLayout(editProductFields, BoxLayout.Y_AXIS); //Variable number of products from product list
        editProductFields.setLayout(boxlayout);
        
        JPanel productName = new JPanel(new FlowLayout());
        JLabel productNameText = new JLabel("Product Name: ");
        JTextField productNameField = new JTextField(10); //prepopulated with product name
        productName.add(productNameText);
        productName.add(productNameField);

        JPanel storeName = new JPanel(new FlowLayout());
        JLabel storeNameText = new JLabel("Store Name: ");
        JTextField storeNameField = new JTextField(10); //prepopulated with store name
        storeName.add(storeNameText);
        storeName.add(storeNameField);

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

        editProductFields.add(productName);
        editProductFields.add(storeName);
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

    public void displayStatistics() {
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
        customers.add("CAT");        //Dummy purchases
        customers.add("CAT");
        customers.add("CAT");
        customers.add("CAT");
        for (String c : customers) {
            statsItems.add(new JLabel(c));
        }
        JScrollPane scrollPane = new JScrollPane(statsItems); 
        statsCentral.add(scrollPane , BorderLayout.CENTER);
        stats.add(statsCentral,  BorderLayout.CENTER);

        stats.pack();
        stats.setSize(600, 400);
        stats.setLocationRelativeTo(null);
        stats.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stats.setVisible(true);
        stats.requestFocus();


    }

}