import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;


public class LoginOrCreateAccount extends JComponent implements ActionListener{
    JButton login;
    JButton createNewAccount;
    public static void main(String[] args) {
     LoginOrCreateAccount loginOrCreateAccount=new LoginOrCreateAccount();


    }
    LoginOrCreateAccount(){
        Border border= BorderFactory.createLineBorder(Color.BLACK,10);

        JLabel label = new JLabel();
        JLabel marketplaceLabel=new JLabel();
        label.setText("Welcome to the");
        label.setFont(new Font("MV Boli",Font.PLAIN,25));
        marketplaceLabel.setText("MarketPlace");
        marketplaceLabel.setFont(new Font("MV Boli",Font.PLAIN,25));
        marketplaceLabel.setBounds(175,60,200,50);
        //  label.setBounds(100,100,100,100);
        label.setBounds(150,20,280,50);
        //  label.setBorder(border);
        JLabel login1=new JLabel("Would you like to login");
        login1.setFont(new Font("MV Boli",Font.PLAIN,20));
        login1.setBounds(150,150,300,50);
        JLabel login2=new JLabel("or create a new account");
        login2.setFont(new Font("MV Boli",Font.PLAIN,20));
        login2.setBounds(150,190,300,50);

//
        JButton login=new JButton("Login");
        JButton createNewAccount= new JButton("Create New Account");

        //createNewAccount.setFont();

        login.setBounds(100, 250, 300, 50);
        login.setFocusable(false);
        createNewAccount.setBounds(100,300,300,50);
        createNewAccount.setFocusable(false);

        //  createNewAccount.addActionListener();

        JFrame frame=new JFrame();
        frame.dispose();
        // frame.setLayout(new GridLayout(2,1));
        frame.setSize(500,500);
        frame.setTitle("Login Page");

        // frame.setLayout(new GridLayout(2,1));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(206, 184, 136));
        frame.add(label);
        frame.add(marketplaceLabel);
        frame.add(login1);
        frame.add(login2);
        frame.add(login);
        frame.add(createNewAccount);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginSellerOrBuyer loginSellerOrBuyer=new LoginSellerOrBuyer();
            }
        });

        createNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                CreateAccountSellerOrBuyer createAccountSellerOrBuyer =new CreateAccountSellerOrBuyer();
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
