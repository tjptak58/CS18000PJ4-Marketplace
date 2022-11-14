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

public class SellerTest {
    @Test
    public void newStore() {
        Store expectedStore = new Store("tptak", "Cars", "cars.txt");
        Seller seller = new Seller("tptak", "password", "tptak@gmail.com", "test.txt");
        seller.createStore("tptak", "Cars", "cars.txt");
        assertEquals(expectedStore.equals(seller.getStores().get(0)), true);
        
    }
}