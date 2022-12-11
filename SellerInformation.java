import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
public class SellerInformation {

    // have them enter information
    // then once create account is created send a string over to server with all information unless email does not have a @ or .
    //
    JFrame frame=new JFrame();

    JLabel sellerInformation=new JLabel("           Enter Account Information Below");

    JLabel enterUsername= new JLabel("Enter New Username");
    JLabel enterPassword=new JLabel("Enter New Password");

    JLabel enterEmail=new JLabel("Enter your email");

    JLabel enterStatistics=new JLabel("Enter your statistics filepath");

    JLabel enterStatistics1=new JLabel("statistics");
    JTextField username=new JTextField(9);
    JTextField password=new JTextField(9);

    JTextField email=new JTextField(9);

    JTextField statistics= new JTextField(9);

    JButton createAccount=new JButton("Create Account!");

    ArrayList<String> usernameAndPasswordSeller = new ArrayList<>();

    SellerInformation(){


        // text
        JPanel textAtTop= new JPanel(new BorderLayout(10,10));
        textAtTop.add(sellerInformation);
        // username [anel
        JPanel usernameInformation= new JPanel(new BorderLayout(10,10));
        usernameInformation.add(enterUsername, BorderLayout.LINE_START);
        usernameInformation.add(username, BorderLayout.LINE_END);
        //password panel
        JPanel passwordInformation=new JPanel(new BorderLayout(10,10));
        passwordInformation.add(enterPassword,BorderLayout.LINE_START);
        passwordInformation.add(password,BorderLayout.LINE_END);
        // email panel
        JPanel emailInformation=new JPanel(new BorderLayout(10,10));
        emailInformation.add(enterEmail, BorderLayout.LINE_START);
        emailInformation.add(email, BorderLayout.LINE_END);
        // statistics panel
//        JPanel statisticsInformation= new JPanel(new BorderLayout(10,10));
//        statisticsInformation.add(enterStatistics,BorderLayout.LINE_START);
       // statisticsInformation.add(enterStatistics1,BorderLayout.AFTER_LINE_ENDS);

      //  statisticsInformation.add(statistics, BorderLayout.LINE_END);
        //Button Create new Account
        JPanel createNewAccount=new JPanel( );
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
                        || email.getText().contains(";") ||statistics.getText().contains(";")) {
                    JOptionPane.showMessageDialog(null, "Error: None of the Fields should contain a semi colin (;)", "Seller Information",
                            JOptionPane.ERROR_MESSAGE);
                    flag = true;
                }
//                    if (statistics.getText().equals("buyer.txt") ||
//                           statistics.getText().equals("seller.txt") || statistics.getText().equals("storeListFile.txt")) {
//                        JOptionPane.showMessageDialog(null, "Error: Can not use this txt file)", "Seller Information",
//                                JOptionPane.ERROR_MESSAGE);
//                        flag=true;
//                    }
//                    File f =new File(statistics.getText());
//                    if (f.exists()) {
//                        JOptionPane.showMessageDialog(null, "Error: File Already Exists. Please Enter a new one", "Seller Information",
//                                JOptionPane.ERROR_MESSAGE);
//                        flag=true;
//                    }
                if (!flag){
                    String hostName = "localhost";
                    int portNumber = 4242; //do later

                    try {
                        Socket echoSocket = new Socket(hostName, portNumber);        // 1st statement
                        ObjectOutputStream oos = new ObjectOutputStream(echoSocket.getOutputStream());
                        PrintWriter pw =                                            // 2nd statement
                                new PrintWriter(echoSocket.getOutputStream(), true);
                        pw.println("CREATEACCSELLER");
                        pw.println(username.getText());
                        pw.println(password.getText());
                        pw.println(email.getText());

                      //  pw.write(username.getText() + "; " + password.getText() + "; " + email.getText() + "; " + statistics.getText() + "\n");



                        Scanner in = new Scanner(echoSocket.getInputStream());
//                        BufferedReader reader;
//                        reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                        String linesRead="";

                                linesRead=in.nextLine();
                                if ( linesRead.contains("ERROR User Information Already Exists")) {
                                    JOptionPane.showMessageDialog(null, "Error: Username already exists. Please Enter a new username", "Seller Information",
                                            JOptionPane.ERROR_MESSAGE);

                                } else if (linesRead.contains("CONFIRM")) {
                                   // System.out.println("hi");
                                    frame.dispose();
                                    AccountCreated accountCreated = new AccountCreated();
                                }


//                            BufferedReader stdIn =                                       // 4th statement
//                                    new BufferedReader(
//                                            new InputStreamReader(System.in))

//
                    } catch (IOException g) {
                        g.printStackTrace();
                    }

                }
            }
        });
        // Gemeral Panel
        JPanel panelGeneral=new JPanel(new GridLayout(0,1));
        panelGeneral.add(sellerInformation);
        panelGeneral.add(usernameInformation);
        panelGeneral.add(passwordInformation);
        panelGeneral.add(emailInformation);
     //   panelGeneral.add(statisticsInformation);
        panelGeneral.add(createNewAccount);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setLocationRelativeTo(null);
        frame.add(panelGeneral);
        frame.setVisible(true);

    }

}
