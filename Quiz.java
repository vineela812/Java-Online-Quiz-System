import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*; 
import java.sql.*;

import java.util.List;
import java.util.ArrayList;
class QuestionAnswer{
    String question;
    String selectedAnswer;
    String  correctAnswer;
}
public class Quiz {
    JFrame frame;
    JPanel topPanel;
    JLabel questionLabel;
    JPanel optPanel;
    JRadioButton opt1,opt2,opt3,opt4;
    ButtonGroup bg;
    JButton nextbtn;

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String currentCorrect;
    int score=0;
    String username;

    Timer timer;
    int timeLeft;
    final int totalTime=15;
    //below one is for progress bar timer
    // int timeLeft;
    JLabel timerLabel;

    // JProgressBar timeBar;
    // int totalTime=15;

    boolean quizEnded=false;

    JProgressBar questionBar;
    int totalQuestions=0;
    int currentQuestionIndex=0;

    List<QuestionAnswer> reviewList=new ArrayList<>();
    Quiz(String user){
        username=user;
        frame=new JFrame("Quizz");
        frame.setSize(800,500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // top panel
        topPanel=new JPanel(new BorderLayout());
        //question progress bar
        questionBar=new JProgressBar(0,1);
        questionBar.setValue(0);
        questionBar.setPreferredSize(new Dimension(300,15));
        questionBar.setStringPainted(true);
        questionBar.setFont(new Font("MV Boli", Font.BOLD, 12));
        questionBar.setForeground(Color.BLUE);
        questionBar.setBackground(Color.LIGHT_GRAY);
        topPanel.add(questionBar,BorderLayout.NORTH);

        timeLeft=totalTime;
        timerLabel=new JLabel("Time left: "+timeLeft+"s",JLabel.CENTER);
        timerLabel.setForeground(Color.RED);
        timerLabel.setFont(new Font("Arial",Font.BOLD,15));
        topPanel.add(timerLabel,BorderLayout.EAST);

        // topPanel=new JPanel(new BorderLayout());
        // timeBar=new JProgressBar(0,totalTime);
        // timeBar.setValue(totalTime);
        // timeBar.setStringPainted(true);
        // timeBar.setBackground(Color.RED);
        // timeBar.setForeground(Color.BLACK);
        // timeBar.setPreferredSize(new Dimension(150,15));
        // timeBar.setFont(new Font("MV Boli",Font.BOLD,10));
        // topPanel.add(timeBar,BorderLayout.EAST);

        //question label
        questionLabel=new JLabel();
        topPanel.add(questionLabel,BorderLayout.CENTER);
        frame.add(topPanel,BorderLayout.NORTH);

        //options
        optPanel=new JPanel(new GridLayout(4,1));
        opt1=new JRadioButton();
        opt2=new JRadioButton();
        opt3=new JRadioButton();
        opt4=new JRadioButton();
        //button group to only allow one option to be selected
        bg=new ButtonGroup();
        bg.add(opt1);
        bg.add(opt2);
        bg.add(opt3);
        bg.add(opt4);

        optPanel.add(opt1);
        optPanel.add(opt2);
        optPanel.add(opt3);
        optPanel.add(opt4);

        frame.add(optPanel,BorderLayout.CENTER);
        //next button
        nextbtn=new JButton("Next");
        // nextbtn.setVisible(true);
        nextbtn.setBackground(Color.pink);
        nextbtn.setFocusable(false);
        nextbtn.addActionListener(e->nextQuestion());
        frame.add(nextbtn,BorderLayout.SOUTH);
        
        connectDB();
        countTotalQuestions();
        if(totalQuestions==0){
            JOptionPane.showMessageDialog(frame, "Noquestions found!");
            frame.dispose();
            return;
        }
        questionBar.setMaximum(totalQuestions);
        loadQuestion();
        startTimer();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public void connectDB(){
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db","root","Vineela@123");
            ps=con.prepareStatement("select * from questions", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=ps.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void countTotalQuestions(){
        try{
            rs.last();
            totalQuestions=rs.getRow();
            rs.beforeFirst();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void loadQuestion(){
        try{
            if(rs.next()){
                currentQuestionIndex++;
                questionLabel.setText(rs.getString("question"));

                opt1.setText(rs.getString("opt1"));
                opt2.setText(rs.getString("opt2"));
                opt3.setText(rs.getString("opt3"));
                opt4.setText(rs.getString("opt4"));

                currentCorrect = rs.getString("correct_ans");

                bg.clearSelection();
                resetTimer();
                //update progress bar
                questionBar.setValue(currentQuestionIndex);
                questionBar.setString(" "+currentQuestionIndex+" / "+ totalQuestions);
            }else{
                //quiz is ended if there is no next question
                timer.stop();
                quizEnded=true;
                saveResult();
            SwingUtilities.invokeLater(() -> {
                frame.dispose();
                new ReviewFrame(username, score,reviewList);
            });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void nextQuestion(){
        //try{
           // String correct=rs.getString("correct_ans");
           if(quizEnded) return;
           timer.stop();
           //check answer
           String selected="";
            if(opt1.isSelected()) selected = opt1.getText();
            if(opt2.isSelected()) selected = opt2.getText();
            if(opt3.isSelected()) selected = opt3.getText();
            if(opt4.isSelected()) selected = opt4.getText();

            QuestionAnswer qa=new QuestionAnswer();
            qa.question=questionLabel.getText();
            qa.correctAnswer=currentCorrect;
            qa.selectedAnswer=selected;
            reviewList.add(qa);
        ///check score
            if(selected.equals(currentCorrect)) score++;
            loadQuestion();
            if(!quizEnded){
            timer.start();
            }
        //}
        // catch(Exception e){
        //     e.printStackTrace();
        // }
    }
    public void saveResult(){
        try{
            String sql="insert into results(username,score) values(?,?)";
            PreparedStatement ps1=con.prepareStatement(sql);
            ps1.setString(1,username);
            ps1.setInt(2, score);
            ps1.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void startTimer(){
        timeLeft=totalTime;
        timerLabel.setText(timeLeft + "s left");
        timer=new javax.swing.Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent e){
                timeLeft--;
                timerLabel.setText("Time left: "+timeLeft+"s");
                if(timeLeft<=0){
                    nextQuestion();
                }
            }
        });
        timer.start();
        /////lambda expression  
        /// progress bar
        // timeLeft=totalTime;
        // timeBar.setValue(timeLeft);
        // timeBar.setString(timeLeft+"s left");
        // timer=new Timer(1000,e->{
        //    timeLeft--;
        //     timeBar.setValue(timeLeft);
        //     timeBar.setString(timeLeft+"s left");
        //     if(timeLeft<=0){
        //         nextQuestion();
        //     }
        // });
        // timer.start();
    }
    public void resetTimer(){
        timeLeft=totalTime;
        timerLabel.setText("Time left: "+timeLeft+"s");
        // timeLeft=totalTime;
        // timeBar.setValue(timeLeft);
        // timeBar.setString(timeLeft+"s left");
    }
}
