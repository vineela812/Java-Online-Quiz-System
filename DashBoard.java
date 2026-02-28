import java.awt.BorderLayout;

import javax.swing.*;
public class DashBoard {
    JFrame frame;
    JButton button;
    ImageIcon img;
    JLabel imgLabel;
    DashBoard(String user){
        frame=new JFrame("Dashboard");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title=new JLabel("Welcome to Quiz App",JLabel.CENTER);

        // img=new ImageIcon("whistle.jpg");
        // imgLabel=new JLabel(img,JLabel.CENTER);
        button=new JButton("Start quiz");
        button.setFocusable(false);
        button.addActionListener(e->{
            frame.dispose();
            new Quiz(user);
    });
        //frame.add(imgLabel,BorderLayout.CENTER);
        
        frame.add(title,BorderLayout.CENTER);
        frame.add(button,BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
