import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccountCreated {

    AccountCreated() {
        JFrame frame = new JFrame();
        JLabel accountCreated = new JLabel("     Account Created!");
        JButton continueButton = new JButton("Continue");

        continueButton.setBounds(100, 250, 300, 50);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginOrCreateAccount loginOrCreateAccount = new LoginOrCreateAccount();
            }
        });
        accountCreated.setBounds(150, 175, 500, 50);
        accountCreated.setFont(new Font("MV Boli", Font.PLAIN, 20));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.add(accountCreated);
        frame.add(continueButton);
        frame.getContentPane().setBackground(new Color(206, 184, 136));
        frame.setVisible(true);


    }


}
