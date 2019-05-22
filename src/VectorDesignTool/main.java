package VectorDesignTool;

import javax.swing.*;

public class main {
    public static void main(String[] args){
        try {
            SwingUtilities.invokeLater(new gui("BorderLayout"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
