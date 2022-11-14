import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;
import java.util.ArrayList;

/*
 * Tests making a new Store
 */
public class BuyerSellerTest {

    Store store;
    Seller seller;
    Product p;
    Buyer buyer;
    @Before
    public void initialize() {
        store = new Store("tptak", "Cars", "cars.txt");
        seller = new Seller("tptak", "password", "tptak@gmail.com", "test.txt");
        Product p = new Product("Mazzerati", "Cars", "Drives Hella Fast", 
        20, 20000, 0, null);
        buyer = new Buyer("jdilla", "beats", "jdilla@gmail.com", "cart.txt", "history.txt");  

    }
    
    @Test
    public void newStore() {
        seller.createStore("tptak", "Cars", "cars.txt");
        assertEquals(store.equals(seller.getStores().get(0)), true);
        
    }

    @Test 
    public void editProductTest() {
        store.addProduct(p);
        seller.createStore(store);
        seller.editProduct(p, "Mazzerati", "Cars", "Drives Hella fast", 10, 20000);
        Product changedp = new Product( "Mazzerati", "Cars", "Drives Hella fast", 10, 20000);
        ArrayList<Store> stores = seller.getStores();
        ArrayList<Product> products = stores.get(0).getProducts();
        Product outputProduct = products.get(0);
        assertEquals(changedp.equals(outputProduct),true);
    }


    
}