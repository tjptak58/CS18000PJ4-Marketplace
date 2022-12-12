import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class BuyerInformation {
    JFrame frame = new JFrame();

    JLabel buyerInformation = new JLabel("           Enter Account Information Below");

    JLabel enterUsername = new JLabel("Enter New Username");
    JLabel enterPassword = new JLabel("Enter New Password");

    JLabel enterEmail = new JLabel("Enter your email");

    JLabel enterPurchaseHistoru = new JLabel("Enter your purchase history filepath");

    JLabel enterShoppingCart = new JLabel("Enter your shopping cart filepath");

    JLabel enterStatistics1 = new JLabel("statistics");
    JTextField username = new JTextField(9);
    JTextField password = new JTextField(9);

    JTextField email = new JTextField(9);

    JTextField purchaseHistory = new JTextField(5);

    JTextField shoppingCart = new JTextField(6);

    JButton createAccount = new JButton("Create Account!");

    ArrayList<String> usernameAndPasswordBuyer = new ArrayList<>();

    BuyerInformation() {
        JPanel textAtTop = new JPanel(new BorderLayout(10, 10));
        textAtTop.add(buyerInformation);
        // username [anel
        JPanel usernameInformation = new JPanel(new BorderLayout(10, 10));
        usernameInformation.add(enterUsername, BorderLayout.LINE_START);
        usernameInformation.add(username, BorderLayout.LINE_END);
        //password panel
        JPanel passwordInformation = new JPanel(new BorderLayout(10, 10));
        passwordInformation.add(enterPassword, BorderLayout.LINE_START);
        passwordInformation.add(password, BorderLayout.LINE_END);
        // email panel
        JPanel emailInformation = new JPanel(new BorderLayout(10, 10));
        emailInformation.add(enterEmail, BorderLayout.LINE_START);
        emailInformation.add(email, BorderLayout.LINE_END);
        // purchase history
//        JPanel purchaseHistoryInformation = new JPanel(new BorderLayout(10, 10));
//        purchaseHistoryInformation.add(enterPurchaseHistoru, BorderLayout.LINE_START);
        //purchaseHistoryInformation.add(purchaseHistory, BorderLayout.LINE_END);
        // shopping cart
//        JPanel shoppingCartInformation = new JPanel(new BorderLayout(10, 10));
//        shoppingCartInformation.add(enterShoppingCart, BorderLayout.LINE_START);
//        shoppingCartInformation.add(shoppingCart, BorderLayout.LINE_END);
        // general panel

        JPanel createNewAccount = new JPanel();
        createNewAccount.add(createAccount);
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag=false;
                if (!email.getText().contains("@") && (!email.getText().contains("."))) {

                    JOptionPane.showMessageDialog(null, "Error: email is not in the right format(needs to include @ and .)", "Seller Information",
                            JOptionPane.ERROR_MESSAGE);
                    flag = true;
                }
                if (username.getText().contains(";")|| password.getText().contains(";")
                        || email.getText().contains(";") ||purchaseHistory.getText().contains(";")||
                        shoppingCart.getText().contains(";")) {
                    JOptionPane.showMessageDialog(null, "Error: None of the Fields should contain a semi colin (;)", "Seller Information",
                            JOptionPane.ERROR_MESSAGE);
                    flag = true;
                }
                if (username.getText().equals("")|| password.getText().equals("") || email.getText().equals("") ||
                        purchaseHistory.getText().equals("") || shoppingCart.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: None of the Fields should be empty", "Seller Information",
                            JOptionPane.ERROR_MESSAGE);
                    flag = true;
                }

//                    if (purchaseHistory.getText().equals("buyer.txt") ||
//                            purchaseHistory.getText().equals("seller.txt") || purchaseHistory.getText().equals("storeListFile.txt")) {
//                        JOptionPane.showMessageDialog(null, "Error: Can not use this txt file)", "Seller Information",
//                                JOptionPane.ERROR_MESSAGE);
//                        flag=true;
//                    }
//                    if (shoppingCart.getText().equals("buyer.txt") ||
//                            shoppingCart.getText().equals("seller.txt") || shoppingCart.getText().equals("storeListFile.txt")) {
//                        JOptionPane.showMessageDialog(null, "Error: Can not use this txt file)", "Seller Information",
//                                JOptionPane.ERROR_MESSAGE);
//                        flag=true;
//                    }
                File f =new File(purchaseHistory.getText());
                if (f.exists()) {
                    JOptionPane.showMessageDialog(null, "Error: File Already Exists. Please Enter a new one", "Seller Information",
                            JOptionPane.ERROR_MESSAGE);
                    flag=true;
                }
                File file=new File(shoppingCart.getText());
                if (file.exists()) {
                    JOptionPane.showMessageDialog(null, "Error: File Already Exists. Please Enter a new one", "Seller Information",
                            JOptionPane.ERROR_MESSAGE);
                    flag=true;
                }


                if (!flag){
                    String hostName = "localhost";
                    int portNumber = 4242; //do later

                    try {
                        Socket echoSocket = new Socket(hostName, portNumber);        // 1st statement
                        ObjectOutputStream oos = new ObjectOutputStream(echoSocket.getOutputStream());
                        PrintWriter pw =                                            // 2nd statement
                                new PrintWriter(echoSocket.getOutputStream(), true);
                        pw.println("CREATEACCBUYER");
                        pw.println(username.getText());
                        pw.println(password.getText());
                        pw.println(email.getText());

                      //  pw.write(username.getText() + "; " + password.getText() + "; " + email.getText()  + "\n");


//
                        //    BufferedReader in =                                          // 3rd statement
//                                    new BufferedReader(
//                                            new InputStreamReader(echoSocket.getInputStream()));
//                            BufferedReader stdIn =                                       // 4th statement
//                                    new BufferedReader(
//                                            new InputStreamReader(System.in))

//                        BufferedReader reader;
//                        reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                      //  String linesRead = "";
                        Scanner in = new Scanner(echoSocket.getInputStream());

                        String linesRead="";

                        linesRead=in.nextLine();


                            if (linesRead.contains("ERROR User Information Already Exists")) {
                                JOptionPane.showMessageDialog(null, "Error: Username already exists. Please Enter a new username", "Seller Information",
                                        JOptionPane.ERROR_MESSAGE);

                            } else if (linesRead.contains("CONFIRM")) {

                                frame.dispose();
                                AccountCreated accountCreated = new AccountCreated();
                            }

                    } catch (IOException g) {
                        g.printStackTrace();
                    }

                }
            }
//                try {
//                    BufferedReader bfr = new BufferedReader(new FileReader("/Users/vijayvittal/IdeaProjects/Project/LoginPageGUI/src/buyer.txt"));
//                    String line = "";
//                    while ((line = bfr.readLine()) != null) {
//                        usernameAndPasswordBuyer.add(line);
//                    }
//                    bfr.close();
//                } catch (IOException s) {
//                    s.printStackTrace();
//                }
//                // do {
//                if (!usernameAndPasswordBuyer.isEmpty()) {
//                    boolean flag=false;
//                    // check if there is already a username that exists
//
//                    for (int i = 0; i < usernameAndPasswordBuyer.size(); i++) {
//                        System.out.println("hi");
//                        if (usernameAndPasswordBuyer.get(i).substring(0,
//                                usernameAndPasswordBuyer.get(i).indexOf(";")).contains(username.getText())) {
//                            //flag=false;
//                            flag=true;
//
//        // have to fix buyer and add email condiiton
//                            if (flag) {
//                                JOptionPane.showMessageDialog(null, "Error: Username already exists! Please enter another username", "Seller Information",
//                                        JOptionPane.ERROR_MESSAGE);
//
//                            }
//                        } else {
//                            if (!email.getText().contains("@") && (!email.getText().contains("."))){
//
//                                JOptionPane.showMessageDialog(null, "Error: email is not in the right format(needs to include @ and .)", "Seller Information",
//                                        JOptionPane.ERROR_MESSAGE);
//                            }
//                            try {
//                                FileOutputStream fos = new FileOutputStream("/Users/vijayvittal/IdeaProjects/Project/LoginPageGUI/src/buyer.txt", true);
//                                PrintWriter pw = new PrintWriter(fos);
//                                //BufferedWriter bfw = new BufferedWriter
//                                //        (new FileWriter("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Seller.txt"));
//                                pw.write(username.getText() + "; " + password.getText() + "; " + email.getText() + "; " + purchaseHistory.getText() + shoppingCart.getText() +
//                                        "\n");
//                                pw.close();
//                            } catch (IOException f) {
//                                f.printStackTrace();
//                            }
//                            frame.dispose();
//                            AccountCreated accountCreated = new AccountCreated();
//                        }
//                    }
//                } else {
//                    if (!email.getText().contains("@") && (!email.getText().contains("."))){
//
//                        JOptionPane.showMessageDialog(null, "Error: email is not in the right format(needs to include @ and .)", "Seller Information",
//                                JOptionPane.ERROR_MESSAGE);
//                    }
//                    try {
//                        FileOutputStream fos = new FileOutputStream("/Users/vijayvittal/IdeaProjects/Project/LoginPageGUI/src/buyer.txt", true);
//                        PrintWriter pw = new PrintWriter(fos);
//                        //BufferedWriter bfw = new BufferedWriter
//                        //        (new FileWriter("/Users/vijayvittal/IdeaProjects/Project/Project4/src/Seller.txt"));
//                        pw.write(username.getText() + "; " + password.getText() + "; " + email.getText() + "; " + purchaseHistory.getText() + shoppingCart.getText() +
//                                "\n");
//                        pw.close();
//                    } catch (IOException f) {
//                        f.printStackTrace();
//                    }
//                    frame.dispose();
//                    AccountCreated accountCreated = new AccountCreated();
//                }
//            }
        });
        JPanel panelGeneral = new JPanel(new GridLayout(0, 1));
        panelGeneral.add(buyerInformation);
        panelGeneral.add(usernameInformation);
        panelGeneral.add(passwordInformation);
        panelGeneral.add(emailInformation);
      //  panelGeneral.add(purchaseHistoryInformation);
    //    panelGeneral.add(shoppingCartInformation);
        panelGeneral.add(createNewAccount);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.add(panelGeneral);
        frame.setVisible(true);
    }
}
