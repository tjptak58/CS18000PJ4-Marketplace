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


public class MarketPlaceTest {
    @Test(timeout = 1000)
    public void testOne() {
        // Set the input
        String input = "2" + System.lineSeparator() + "seller" + System.lineSeparator() + "vv" +
                System.lineSeparator() + "blue" + System.lineSeparator() + "vvittal@gmail.com" + System.lineSeparator()
                + "statistcs.txt" + System.lineSeparator() + "1" + System.lineSeparator() + "seller"  + System.lineSeparator()
                + "vv" + System.lineSeparator() + "blue" + System.lineSeparator() + "500" + System.lineSeparator()
                + "500" + System.lineSeparator() + "400" + System.lineSeparator() + "900" + System.lineSeparator()
                + "700" + System.lineSeparator() + "700" + System.lineSeparator() + "800" + System.lineSeparator()
                + "800" + System.lineSeparator() + "600" + System.lineSeparator() + "500" + System.lineSeparator()
                + "700" + System.lineSeparator() + "1000" + System.lineSeparator() + "350" + System.lineSeparator()
                + "550" + System.lineSeparator() + "550" + System.lineSeparator() + "850" + System.lineSeparator();

        // Expected result
        String expected = "Welcome\n"
                + "Would you like you login or create a new account?\n"
                + "1.Login\n"
                + "2.Create new account\n"
                + "Are you a Seller or Buyer\n"
                + "Enter your new username\n"
                + "Enter your new password\n"
                + "Enter your e-mail\n"
                + "What is the filepath for the statistics of the seller\n"
                + "Account made!\n" 
                + "Would you like you login or create a new account?\n"
                + "1.Login\n"
                + "2.Create new account\n"
                + "Do you want to login as a Seller or Buyer\n"
                + "Enter your username\n"
                + "Enter your password\n"
                + "Login successful!\n"
                + "Enter the number of pieces player 2 used for building on day 4\n"
                + "Enter the number of pieces player 1 used for building on day 5\n"
                + "Enter the number of pieces player 2 used for building on day 5\n"
                + "Enter the number of pieces player 1 used for building on day 6\n"
                + "Enter the number of pieces player 2 used for building on day 6\n"
                + "Enter the number of pieces player 1 used for building on day 7\n"
                + "Enter the number of pieces player 2 used for building on day 7\n"
                + "Enter the number of pieces player 1 used for building on day 8\n"
                + "Enter the number of pieces player 2 used for building on day 8\n"
                + "Enter the number of pieces player 1 used for building on day 9\n"
                + "Enter the number of pieces player 2 used for building on day 9\n"
                + "Enter the number of pieces player 1 used for building on day 10\n"
                + "Enter the number of pieces player 2 used for building on day 10\n"
                + "Congratulations to player 2 for winning the Lego Set Competition!\n"
                + "Additional information about the competition results is below\n"
                + "Player 1 completed the following sets: Tower Bridge, Eiffel Tower\n"
                + "Player 1 did not complete the following sets: Ship in Bottle\n"
                + "Player 1 built a total of 6000 pieces\n"
                + "Player 2 completed the following sets: Tower Bridge, Eiffel Tower, Ship in Bottle\n"
                + "Player 2 did not complete the following sets: None\n"
                + "Player 2 built a total of 7000 pieces\n"
                + "The competition lasted 10 days";

        // Runs the program with the input values
        receiveInput(input);
        MarketPlace.main(new String[0]);

        // Retrieves the output from the program
        String output = getOutput();

        // Trims the output and verifies it is correct.
        output = output.replace("\r\n", "\n");
        assertEquals("Ensure that your results match the format of the ones given in the handout!",
                expected.trim(), output.trim());
    }

}
}
