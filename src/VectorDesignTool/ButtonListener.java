package VectorDesignTool;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class ButtonListener implements ActionListener, ChangeListener,
        MouseListener, MouseMotionListener {

    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
    }

    public void stateChanged(ChangeEvent e) {
        System.out.println(e);
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(e);
    }

    public void mousePressed(MouseEvent e) {
        System.out.println(e);
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println(e);
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println(e);
    }

    public void mouseExited(MouseEvent e) {
        System.out.println(e);
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println(e);
    }

    public void mouseMoved(MouseEvent e) {
        System.out.println(e);
    }
}
