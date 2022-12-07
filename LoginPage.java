import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage {

    LoginPage(){
        JFrame frame=new JFrame();
        JLabel loginSuccessful =new JLabel("     Login Successful!");
        JButton continueButton=new JButton("Continue");

        continueButton.setBounds(100, 250, 300, 50);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginOrCreateAccount loginOrCreateAccount =new LoginOrCreateAccount();
            }
        });
        loginSuccessful.setBounds(150,175,500,50);
        loginSuccessful.setFont(new Font("MV Boli",Font.PLAIN,20));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.add(loginSuccessful);
        frame.add(continueButton);
        frame.getContentPane().setBackground(new Color(206, 184, 136));
        frame.setVisible(true);
    }
}
