import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSellerOrBuyer extends JComponent implements ActionListener {
    JFrame frame = new JFrame();
    JLabel label = new JLabel("Are you a buyer");

    JLabel label2 = new JLabel("  or Seller?");
    JButton buyer = new JButton("Buyer");
    JButton seller = new JButton("Seller");

    LoginSellerOrBuyer() {
        frame.setLocationRelativeTo(null);
        frame.dispose();
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label.setBounds(75, 10, 300, 50);
        label2.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label2.setBounds(100, 30, 300, 50);

        seller.setBounds(60, 70, 180, 80);
        seller.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginSeller loginSeller = new LoginSeller();


            }
        });
        buyer.setBounds(60, 150, 180, 80);
        buyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginBuyer loginBuyer = new LoginBuyer();

            }
        });
        //buyer.setBounds(60, 150,100,80);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(null);
        frame.add(label);
        frame.add(label2);
        frame.add(buyer);
        frame.add(seller);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(206, 184, 136));


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
