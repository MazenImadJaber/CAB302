package VectorDesignTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui extends JFrame implements ActionListener, Runnable{
    public static final int WIDTH = 300;
    public static final int HEIGHT = 300;
    private JPanel pnlDisplay, pnlEast, pnlUp, pnlWest, pnlBtn, pnlshape;

    public gui(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void run() {
        createGUI();
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pnlDisplay = createPanel(Color.white);
        pnlEast = createPanel(Color.lightGray);
        pnlUp = createPanel(Color.lightGray);
        pnlWest = createPanel(Color.lightGray);
        pnlBtn = createPanel(Color.lightGray);
        Shape s =new Shape();
        ChooseFile chsr = new ChooseFile();



        getContentPane().add(pnlDisplay,BorderLayout.CENTER);
        getContentPane().add(pnlEast,BorderLayout.EAST);
        getContentPane().add(pnlUp,BorderLayout.NORTH);
        getContentPane().add(pnlWest,BorderLayout.WEST);
        getContentPane().add(pnlBtn,BorderLayout.SOUTH);
        getContentPane().add(s,BorderLayout.CENTER);
        getContentPane().add(chsr, BorderLayout.NORTH);





        setVisible(true);
        repaint();

    }

    private JPanel createPanel(Color c) {
        //Create a JPanel object and store it in a local var
        JPanel pan = new JPanel();
        //set the background colour to that passed in c
        pan.setBackground(c);
        //Return the JPanel object
        return pan;
    }


}

