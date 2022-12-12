import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginBuyer {

    JFrame frame = new JFrame();

    JLabel enterYourUsername = new JLabel("Enter your username");


    JTextField username = new JTextField(20);

    JLabel enterYourPassword = new JLabel("Enter your password");
    JTextField password = new JTextField(20);

    JButton loginButton = new JButton("Login");

    JButton backButton = new JButton("Back to Main Menu");


    ArrayList<String> usernameAndPasswordBuyer = new ArrayList<>();

    LoginBuyer() {
        JPanel usernameInformation = new JPanel(new BorderLayout(10, 10));
        usernameInformation.add(enterYourUsername, BorderLayout.LINE_START);
        usernameInformation.add(username, BorderLayout.LINE_END);

        JPanel passwordInformation = new JPanel(new BorderLayout(10, 10));
        passwordInformation.add(enterYourPassword, BorderLayout.LINE_START);
        passwordInformation.add(password, BorderLayout.LINE_END);

        JPanel login = new JPanel();
        login.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hostName = "localhost";
                int portNumber = 4242; //do later

                try {
                    Socket echoSocket = new Socket(hostName, portNumber);        // 1st statement
                    ObjectOutputStream oos = new ObjectOutputStream(echoSocket.getOutputStream());
                    PrintWriter pw =                                            // 2nd statement
                            new PrintWriter(echoSocket.getOutputStream(), true);
                    pw.println("LOGINBUYER");
                    pw.println(username.getText());
                    pw.println(password.getText());


//
                    //    BufferedReader in =                                          // 3rd statement
//                                    new BufferedReader(
//                                            new InputStreamReader(echoSocket.getInputStream()));
//                            BufferedReader stdIn =                                       // 4th statement
//                                    new BufferedReader(
//                                            new InputStreamReader(System.in))


                    Scanner in = new Scanner(echoSocket.getInputStream());

                    String linesRead = "";

                    linesRead = in.nextLine();
                    if (linesRead.contains("ERROR")) {
                        JOptionPane.showMessageDialog(null, "Error: Username and/or password is wrong. Please try again", "Seller Information",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (linesRead.contains("CONFIRM")) {
                        JOptionPane.showMessageDialog(null, "Login Succesful.", "Seller Information",
                                JOptionPane.INFORMATION_MESSAGE);

                        pw.println("LOGOUT");
                        pw.flush();
                        frame.dispose();
                        MarketPlaceClient marketPlaceClient = new MarketPlaceClient(4242, username.getText(), true, false);
                    }

                } catch (IOException f) {
                    f.printStackTrace();
                }

//                usernameAndPasswordBuyer.clear();
//                // read from file
//                try {
//                    BufferedReader bfr = new BufferedReader(new FileReader("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Seller.txt"));
//                    String line = "";
//                    while ((line = bfr.readLine()) != null) {
//                        usernameAndPasswordBuyer.add(line);
//                    }
//                    bfr.close();
//                } catch (IOException f) {
//                    f.printStackTrace();
//                }
//                // if there is only one element in array list
//                String[] split = usernameAndPasswordBuyer.get(0).split(";");
//                String passwordTrim = split[1].trim();
//                if (usernameAndPasswordBuyer.size() == 1) {
//                    // if username and password or in arraylist
//                    if ((usernameAndPasswordBuyer.get(0).substring(0,
//                            usernameAndPasswordBuyer.get(0).indexOf(";")).contains(username.getText())) && passwordTrim.contains(password.getText())) {
//                        LoginSuccessful loginSuccessful = new LoginSuccessful();
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Error: Username and/or Password is incorrect)", "Login Seller",
//                                JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    // do the same thing for size 1 but for all other sizes of array list
//                }
//            }
            }
        });

        JPanel back = new JPanel();
        back.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginOrCreateAccount loginOrCreateAccount = new LoginOrCreateAccount();
            }
        });

        JPanel generalPanel = new JPanel(new GridLayout(0, 1));
        generalPanel.add(usernameInformation);
        generalPanel.add(passwordInformation);
        generalPanel.add(login);
        generalPanel.add(back);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.add(generalPanel);
        frame.setVisible(true);


    }
}
