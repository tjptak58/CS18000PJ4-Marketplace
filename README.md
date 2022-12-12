# CS18000PJ5-Marketplace
How to run our marketplace: 
Open up the terminal and enter two commands 
javac MarketPlaceServer.java 
java MarketPlaceServer.java 
javac LoginOrCreateAccount.java
java LoginOrCreateAccount.java

Shreya Kamath - Submitted Review and Planning Report on Brightspace. 

Tyler Ptak - Submitted Vocareum workspace.

Vijay Vittal- Submitted Presentation Video


Login and Create Account Classes (LoginOrCreateAccount, LoginSellerOrBuyer, LoginBuyer, LoginSeller, 
BuyerInformation, SellerInformation, AccountCreated)
All of these were classes which went from one frame to another depending on what method was called upon when using the GUI. BuyerInformation and SellerInformation had all the information related to the buyer and seller when creating an account respectively. LoginOrCreateAccount is where the program starts on the client side asking the user if they want to login or create an account. LoginSeller and LoginBuyer are the login forms respecitvely for each class. Account Created is a new frame telling the user that the account was created.

MarketPlaceServer Class:
The main method first initializes an ArrayList of Buyers, an ArrayList of Sellers and an ArrayList of Store. The main method then enters an infinite while loop that waits for a client to connect to its ServerSocket. Once it accepts a connection, it starts a new thread for that connection which in turn calls the run method. The run method initializes input and output streams between the server and client and then enters a while loop that listens for a specific keyword from the client.
Based on the keyword recieved, information is modified in the ArrayLists or information is retrieved from it. At each instance data is written onto the three permanent text files so that data persists even if the server crashes. 
The run method exists the while loop if it recieves the keyword "LOGOUT" from the client.

MarketPlaceClientClass:

This class runs the GUI for the marketplace. This GUI inteacts with a buyer or a seller
A seller can view their current stores, add or delete a store and can add, delete or modify products
for an existing store
A seller can also view a statistics dashboard for a particular store
A buyer can view all the products currently listed on the marketplace, add a product to their cart,
remove a product from their cart.
A buyer can contact the seller of a particular store and can checkout all the products in their cart at once
Both buyers and sellers can sort their dashboards.

Person Class:
The Person class holds the generic information for a user. This info
includes the username, password, and email of that users. It includes 
accessor methods, mutator methods, and a toString method. It is the parent class 
of Buyer and Seller

Buyer Class: (Extends Person)
The Buyer class holds more specific information about an individual buyer including
its shopping cart and purchase history. It interacts with two files as well: one
for the shopping cart and one for the purchase history. This class also has methods 
to sort and search for products based on different parameters. It also handles
reading and writing to the two files mentioned above. With this implementation,
the shopping cart and purchase history of a customer are preserved in the event 
of a logout or a system crash. 

Seller Class: (Extends Person)
The Seller class holds information about an individual seller. A seller
can have a list of store objects that it owns. A seller object can add,
edit, and delete products from its stores. There are also methods that allow
a user to import and export products from a CSV file. 

Store Class:
The Store class hold sthe information for a store. Accessor and mutator
methods are written for each parameter.

Product Class:
The Product class hold sthe information for a store. Accessor and mutator
methods are written for each parameter. There are also methods which 
allow a user to see statistics about a specific product.

Files used:
buyer.txt
Stores buyer info in format:
username;password;email;filePathToShoppingCart;filePathToPurchaseHistory

seller.txt
Stores seller info in format:
username;password;email;filePath

storeListFile.txt
Stores info in format:
sellerName;storeName;storeRevenue;filePathToStore;filePathToPurchaseLog


