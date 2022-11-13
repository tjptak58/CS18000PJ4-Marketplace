# CS18000PJ4-Marketplace
How to run our marketplace: 
Open up the terminal and enter two commands 
javac Marketplace.java 
java Marketplace.java 

Student 1 - Submitted Report on Brightspace. 
Student 2 - Submitted Vocareum workspace.

Marketplace Class: 
The main method....

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
sellerName;storeName;storeRevenue;filePathToStore


