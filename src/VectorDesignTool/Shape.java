package VectorDesignTool;

import javax.swing.*;
import java.awt.*;

public class Shape extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.setColor(Color.PINK);
          g.fillRect(20,20,100,100);

          g.setColor(Color.cyan);
          g.drawRect(20,150,100,100);


    }

}
