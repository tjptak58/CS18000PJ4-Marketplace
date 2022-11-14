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
public class SellerTest {

    Store store;
    Seller seller;
    Product p;
    @Before
    public void initialize() {
        store = new Store("tptak", "Cars", "cars.txt");
        seller = new Seller("tptak", "password", "tptak@gmail.com", "test.txt");
        Product p = new Product("Mazzerati", "Cars", "Drives Hella Fast", 
        20, 20000, 0, null);

    }
    
    @Test
    public void newStore() {
        seller.createStore("tptak", "Cars", "cars.txt");
        assertEquals(store.equals(seller.getStores().get(0)), true);
        
    }

    @Test
    public void newProduct() {
        seller.createStore(store);
        seller.createProduct("Mazzerati", "tptak", "Cars", "test.txt", 
        "Drives Hella Fast", 20, 20000, 0, null);
        ArrayList<Product> products = seller.getStores().get(0).getProducts();
        assertEquals(products.get(0), p);
        
    }
}