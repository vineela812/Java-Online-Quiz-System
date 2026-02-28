import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;
public class LoginFrame{
    ImageIcon img;
    JFrame frame;
    JLabel userLabel;
    JLabel passLabel;
    JTextField userField;
    JPasswordField passwordField;
    JButton loginbtn;
    LoginFrame(){
        frame=new JFrame("Login here");
        frame.setSize(300,200);
        // img=new ImageIcon("google.png");
        // frame.setIconImage(img.getImage());
        frame.setLayout(new GridLayout(3,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //username
        userLabel=new JLabel("username");
        frame.add(userLabel);
        userField=new JTextField();
        frame.add(userField);
        //password
        passLabel=new JLabel("password");
        frame.add(passLabel);
        passwordField=new JPasswordField();
        frame.add(passwordField);

        frame.add(new JLabel());
        loginbtn=new JButton("Login");
        loginbtn.setFocusable(false);
        frame.add(loginbtn);

        loginbtn.addActionListener(e->login());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public void login(){
        String username=userField.getText();
        String password=new String(passwordField.getPassword());
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db","root","Vineela@123");
            String sql="select * from logins where username=? and password=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                frame.dispose();
                new DashBoard(userField.getText());
            }
            else{
                JOptionPane.showMessageDialog(frame,"Invalid credentials");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}