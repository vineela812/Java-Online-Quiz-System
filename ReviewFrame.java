import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;
public class ReviewFrame{
    JFrame frame;
    JLabel userLabel;
    JPanel mainPanel;
    // JLabel scoreLabel;
    // JPanel reviewPanel;
    ReviewFrame(String username,int score,List<QuestionAnswer> reviewList){
        frame=new JFrame("Results");
        frame.setSize(800, 600);
        //frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel=new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //username+Score
        userLabel=new JLabel("username: "+username+ "  score: "+score);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //frame.add(userLabel,BorderLayout.NORTH);
        //panel to display all questions
        // reviewPanel=new JPanel();
        // reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

        addGap(10);
        mainPanel.add(userLabel);
        addGap(15);
        for(int i=0;i<reviewList.size();i++){
            QuestionAnswer qa=reviewList.get(i);
            //panel to display question and answer
            JPanel qPanel=new JPanel(new GridLayout(3,1));
            qPanel.setBorder(BorderFactory.createLineBorder(Color.gray,1));
            qPanel.setBackground(Color.white);
            qPanel.setMaximumSize(new Dimension(750,100));
            //i+1 is just to display q1
            JLabel qLabel=new JLabel("Q" + (i+1) +": " +qa.question);
            JLabel correctLabel=new JLabel("Correct Answer: "+qa.correctAnswer);
            JLabel selectedLabel=new JLabel("Your answer: "+(qa.selectedAnswer.isEmpty() ? "Not Answered" : qa.selectedAnswer));

            qPanel.add(qLabel);
            qPanel.add(correctLabel);
            qPanel.add(selectedLabel);

            mainPanel.add(qPanel);
            addGap(8);

            // reviewPanel.add(qPanel);
            // reviewPanel.add(Box.createRigidArea(new Dimension(0,5)));
            
        }
        addGap(15);
        JLabel doneLabel=new JLabel("🎉 U did it !!! 🎉");
        doneLabel.setForeground(Color.BLUE);
        doneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(doneLabel);
        JScrollPane scroll=new JScrollPane(mainPanel);
        frame.add(scroll);
        //scoreLabel=new JLabel("Your Score: "+score);
        // frame.add(scoreLabel,BorderLayout.EAST);
        

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void addGap(int size){
        mainPanel.add(Box.createRigidArea(new Dimension(0,size)));
    }
}

