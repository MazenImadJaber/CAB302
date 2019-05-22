package VectorDesignTool;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gui extends JFrame implements ActionListener, MouseListener, ComponentListener, UndoableEditListener,Runnable{
    public static int WIDTH = 700;
    public static int HEIGHT = 700;
    List<String[]> content;
    private JPanel pnlDisplay, pnlEast, pnlUp, pnlWest, pnlBtn, pnlshape;
    Shape s;
    public String dirr ="";
    public  String filName="";
    JFileChooser filChsr;
    String choosertitle;
    FileReader flReader;
    File file;
    JButton OpenBtn, plotBtn, saveAsBrn,linebtn, rectBtn, elipsBtn, ployBtn, unDoBtn,fillBtn,penBtn,noFillBtn;
    Graphics g;
    boolean plot,line,rect,elipse, ploy, fill,pen = false;
    int xoffset = 0;
    int yoffset = 0;
    int x1,y1;
    JLabel txt ;
    List<Double> pPoints = new ArrayList<Double>();


    public gui(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonString = e.getActionCommand();
        System.out.println(e.getActionCommand());
if (buttonString.equals(OpenBtn.getText()))
{
        System.out.println("Select file Clicked");
        filChsr = new JFileChooser();
        filChsr.setCurrentDirectory(new java.io.File("."));
        filChsr.setDialogTitle(choosertitle);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "VEC files ", "vec");
        filChsr.setFileFilter(filter);
        filChsr.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (filChsr.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {

            dirr += filChsr.getCurrentDirectory();
            file= filChsr.getSelectedFile();
            filName = file.getName();


            System.out.println(dirr);
            System.out.println(filName);
            try {
                 flReader = new FileReader(file);
                 content = flReader.getcontent();
                g = makeGraphics(content);

                s.paintComponent(g);
                plotBtn.setEnabled(true);
                saveAsBrn.setEnabled(true);
                plotBtn.doClick();
                linebtn.setEnabled(true);
                rectBtn.setEnabled(true);
                elipsBtn.setEnabled(true);
                ployBtn.setEnabled(true);
                penBtn.setEnabled(true);
                fillBtn.setEnabled(true);
                unDoBtn.setEnabled(true);


            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }



        }
        else
        {
            System.out.println("No Selection ");
        }
}
else if (buttonString.equals(plotBtn.getText()))
{
    plotBtn.setBackground(Color.white);
    linebtn.setBackground(Color.gray);
    rectBtn.setBackground(Color.gray);
    elipsBtn.setBackground(Color.gray);
    txt.setVisible(false);
    plot = true;
    line = false;
    rect = false;
    elipse = false;
}
else if (buttonString.equals(linebtn.getText()))
{
    plotBtn.setBackground(Color.gray);
    linebtn.setBackground(Color.white);
    rectBtn.setBackground(Color.gray);
    elipsBtn.setBackground(Color.gray);
    ployBtn.setBackground(Color.gray);
    txt.setVisible(false);
    plot = false;
    line = true;
    rect = false;
    elipse = false;
    ploy = false;
}
else if (buttonString.equals(rectBtn.getText()))
{
    plotBtn.setBackground(Color.gray);
    linebtn.setBackground(Color.gray);
    rectBtn.setBackground(Color.white);
    elipsBtn.setBackground(Color.gray);
    ployBtn.setBackground(Color.gray);
    txt.setVisible(false);
    plot = false;
    line = false;
    rect = true;
    elipse = false;
    ploy = false;

}
else if (buttonString.equals(elipsBtn.getText()))
{
    plotBtn.setBackground(Color.gray);
    linebtn.setBackground(Color.gray);
    rectBtn.setBackground(Color.gray);
    elipsBtn.setBackground(Color.white);
    ployBtn.setBackground(Color.gray);
    txt.setVisible(false);
    plot = false;
    line = false;
    rect = false;
    elipse = true;
    ploy = false;
}
else if (buttonString.equals(ployBtn.getText()))
{

   txt.setVisible(true);
    plotBtn.setBackground(Color.gray);
    linebtn.setBackground(Color.gray);
    rectBtn.setBackground(Color.gray);
    elipsBtn.setBackground(Color.gray);
    ployBtn.setBackground(Color.white);

    plot = false;
    line = false;
    rect = false;
    elipse = false;
    ploy = true;
}
else if (buttonString.equals(saveAsBrn.getText()))
{
    System.out.println("Save as..   Clicked");
    filChsr.showSaveDialog(this);
    File f=filChsr.getSelectedFile();
    try {
        FileWriter fw = new FileWriter(f);
        for (String[] line : content){
            String l ="";
            for (int i =0; i<line.length; i++){
                l += line[i] +" ";

            }
            l+= "\n";
            System.out.println(l);
            fw.write(l);

        }
        fw.close();

    } catch (IOException e1) {
        e1.printStackTrace();
    }

}
else if (buttonString.equals((unDoBtn.getText())))
{
    content.remove(content.size()-1);
    g = makeGraphics(content);
    s.paintComponent(g);
}
else if(buttonString.equals((penBtn.getText())))
{
    penBtn.setBackground(Color.white);
    JColorChooser jcc = new JColorChooser();
    JDialog co = JColorChooser.createDialog(null, "Pick A Color",true,jcc,this,null);
    co.setVisible(true);
    Color c = jcc.getColor();
    String[] line = new String[2];
    line[0]="PEN";
    System.out.println(String.format("#%06x", c.getRGB() & 0x00FFFFFF));
   line[1]= String.format("#%06x", c.getRGB() & 0x00FFFFFF);
   content.add(line);
   penBtn.setBackground(Color.gray);

}
else if(buttonString.equals((fillBtn.getText())))
{
    fillBtn.setBackground(Color.white);
    JColorChooser jcc = new JColorChooser();
    JDialog co = JColorChooser.createDialog(null, "Pick A Color",true,jcc,this,null);
    co.setVisible(true);
    Color c = jcc.getColor();
    String[] line = new String[2];
    line[0]="FILL";
    System.out.println(String.format("#%06x", c.getRGB() & 0x00FFFFFF));
    line[1]= String.format("#%06x", c.getRGB() & 0x00FFFFFF);
    content.add(line);
    fillBtn.setBackground(Color.gray);
    noFillBtn.setEnabled(true);


}
else if (buttonString.equals((noFillBtn.getText())))
{
    noFillBtn.setBackground(Color.white);
    String[] line = new String[2];
    line[0]="FILL";
       line[1]= "OFF";
    content.add(line);
    noFillBtn.setBackground(Color.gray);
    noFillBtn.setEnabled(false);
}



    }

    private Graphics makeGraphics(List<String[]> getcontent) {
        Graphics g =pnlDisplay.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.BLACK);

        Color outline = null;
        Color fill= null;
        for(String[] line :getcontent )
        {
            if (line[0].equals("PEN"))
            {
                outline= Color.decode(line[1]);
            }
            else if (line[0].equals("FILL"))
            {
               if (line[1].equals("OFF")){
                   fill = null;
               }
               else
                   {
                       fill=Color.decode(line[1]);
               }

            }
            else if (line[0].equals("LINE"))
            {
                if (outline!= null){
                    g.setColor(outline);
                }
                paintLine(g, line);
            }
            else if (line[0].equals("ELLIPSE"))
            {

                int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-xoffset));
                int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-yoffset));
                int x2 = (int)(Double.parseDouble(line[3])*(WIDTH-xoffset));
                int y2 = (int)(Double.parseDouble(line[4])*(HEIGHT-yoffset));
                int x = x1;
                if (x1>x2){
                    x= x2;
                }


                if (fill!= null)
                {
                    g.setColor(fill);
                    g.fillOval(x,y1,Math.abs(x2-x1),Math.abs(y2-y1));
                }
                if (outline!= null)
                {
                    g.setColor(outline);
                }
                g.drawOval(x,y1,Math.abs(x2-x1),Math.abs(y2-y1));

            }
            else if (line[0].equals("PLOT"))
            {
                g.setColor(outline);
                int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-xoffset));
                int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-yoffset));
                g.fillRect(x1,y1,1,1);
            }
            else if (line[0].equals("RECTANGLE"))
            {

                int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-xoffset));
                int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-yoffset));
                int x2 = (int)(Double.parseDouble(line[3])*(WIDTH-xoffset));
                int y2 = (int)(Double.parseDouble(line[4])*(HEIGHT-yoffset));
                int x=x1;
                if (x1>x2){
                   x= x2;
                }

                int y=(y1+y2)/2;

                if (fill!= null){
                    g.setColor(fill);
                    g.fillRect(x,y1,Math.abs(x2-x1),Math.abs(y2-y1));
                }
                if (outline!= null){
                    g.setColor(outline);
                }

                g.drawRect(x,y1,Math.abs(x2-x1),Math.abs(y2-y1));
            }
            else if (line[0].equals("POLYGON"))
            {
                int numPoints = line.length-1;

                int[] y = new int[numPoints/2];
                int[] x = new int[numPoints/2];

                for(int i=1;i<=numPoints;i++){
                    if ( i % 2 == 0){
                        y[i/2-1]=(int)(Double.parseDouble(line[i])*(HEIGHT-yoffset));
                    }
                    else {
                        x[(i-1)/2]=(int)(Double.parseDouble(line[i])*(WIDTH-xoffset));
                    }
                }
                Polygon p = new Polygon(x,y,numPoints/2);


                if (fill!= null){
                    g.setColor(fill);
                    g.fillPolygon(p);
                }
                if (outline!= null){
                    g.setColor(outline);
                }

                g.drawPolygon(p);
            }
        }
        return g;
    }

    private void paintLine(Graphics g, String[] line) {
        int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-xoffset));
        int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-yoffset));
        int x2 = (int)(Double.parseDouble(line[3])*(WIDTH-xoffset));
        int y2 = (int)(Double.parseDouble(line[4])*(HEIGHT-yoffset));
        g.drawLine(x1,y1,x2,y2);
    }


    @Override
    public void run() {
        createGUI();
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        s=new Shape();
        txt = new JLabel("Right click Mouse to draw polygon");
        txt.setBackground(Color.red);
       txt.setVisible(false);

        setUpPanels();
        setUpButtons();
        pnlDisplay.add(s);




        setVisible(true);
        repaint();

    }

    private void setUpPanels() {
        setLayout(new BorderLayout());
        pnlDisplay = createPanel(Color.white);
        pnlDisplay.addMouseListener(this );
        pnlDisplay.addComponentListener(this);
        pnlEast = createPanel(Color.lightGray);
        pnlUp = createPanel(Color.lightGray);
        pnlWest = createPanel(Color.gray);
        pnlBtn = createPanel(Color.lightGray);
        getContentPane().add(pnlDisplay,BorderLayout.CENTER);
        getContentPane().add(pnlEast,BorderLayout.EAST);
        getContentPane().add(pnlUp,BorderLayout.NORTH);
        getContentPane().add(pnlWest,BorderLayout.WEST);
        getContentPane().add(pnlBtn,BorderLayout.SOUTH);
        pnlBtn.add(txt);
    }

    private void setUpButtons() {
        OpenBtn =  new JButton("Select file");
        saveAsBrn= new JButton("Save as..");
        plotBtn =  new JButton(" Plot");
        linebtn =  new JButton("  LINE");
        rectBtn =  new JButton("Rectangle");
        elipsBtn = new JButton("  Ellipse");
        ployBtn  = new JButton("Polygon");
        fillBtn  = new JButton("Fill shape");
        noFillBtn = new JButton("Fill off");
        penBtn   = new JButton("Pen Colour");
        unDoBtn  = new JButton("UNDO");
        OpenBtn.addActionListener(this);
        saveAsBrn.addActionListener(this);
        plotBtn.addActionListener(this);
        linebtn.addActionListener(this);
        rectBtn.addActionListener(this);
        elipsBtn.addActionListener(this);
        ployBtn.addActionListener(this);
        fillBtn.addActionListener(this);
        penBtn.addActionListener(this);
        unDoBtn.addActionListener(this);
        noFillBtn.addActionListener(this);
        plotBtn.setBorderPainted(false);
        linebtn.setBorderPainted(false);
        elipsBtn.setBorderPainted(false);
        rectBtn.setBorderPainted(false);
        ployBtn.setBorderPainted(false);
        fillBtn.setBorderPainted(false);
        penBtn.setBorderPainted(false);
        unDoBtn.setBorderPainted(false);
        noFillBtn.setBorderPainted(false);
        saveAsBrn.setEnabled(false);
        linebtn.setEnabled(false);
        rectBtn.setEnabled(false);
        plotBtn.setEnabled(false);
        elipsBtn.setEnabled(false);
        ployBtn.setEnabled(false);
        fillBtn.setEnabled(false);
        penBtn.setEnabled(false);
        unDoBtn.setEnabled(false);
        noFillBtn.setEnabled(false);
        noFillBtn.setBorderPainted(false);
        linebtn.setBackground(Color.gray);
        plotBtn.setBackground(Color.gray);
        rectBtn.setBackground(Color.gray);
        elipsBtn.setBackground(Color.gray);
        ployBtn.setBackground(Color.gray);
        fillBtn.setBackground(Color.gray);
        penBtn.setBackground(Color.gray);
        unDoBtn.setBackground(Color.gray);
        noFillBtn.setBackground(Color.gray);
        pnlUp.add(OpenBtn);
        pnlUp.add(saveAsBrn);
        pnlWest.setLayout(new BoxLayout(pnlWest,BoxLayout.Y_AXIS));
        pnlWest.add(unDoBtn);
        pnlWest.add(plotBtn);
        pnlWest.add(linebtn);
        pnlWest.add(rectBtn);
        pnlWest.add(elipsBtn);
        pnlWest.add(ployBtn);
        pnlWest.add(fillBtn);
        pnlWest.add(noFillBtn);
        pnlWest.add(penBtn);

    }

    private JPanel createPanel(Color c) {
        //Create a JPanel object and store it in a local var
        JPanel pan = new JPanel();
        //set the background colour to that passed in c
        pan.setBackground(c);
        //Return the JPanel object
        return pan;
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        x/= WIDTH;
        double y = e.getY();
        y/=HEIGHT;

        if (plot){
            String[] line = new String[3];
            line[0]="PLOT";


            line[1]=  String.format("%.5f", x);
            line[2]=  String.format("%.5f", y);
            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
            //System.out.println(content);

        }else if (ploy){
            if (e.getButton()==1){
                pPoints.add(x);
                pPoints.add(y);
            }
            else if (e.getButton()==3){
                String[] line = new String[pPoints.size()+1];
                line[0]="POLYGON";
                int i =1;
                for (double p : pPoints){
                    line[i] = String.format("%.5f", p);
                    i++;
                }

                pPoints.clear();
                content.add(line);
                g = makeGraphics(content);
                s.paintComponent(g);


            }



        }

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

         x1 = e.getX();
      //  x/= WIDTH;
         y1 = e.getY();
       // y/=HEIGHT;
        System.out.println(e);
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if (line){
            String[] line = new String[5];
            line[0]= "LINE";
            int x2 = e.getX();
            int y2 = e.getY();
//


            line[1]=  String.format("%.5f", (double)x1/WIDTH);
            line[2]=  String.format("%.5f", (double)y1/HEIGHT);
            line[3]=  String.format("%.5f", (double)x2/WIDTH);
            line[4]=  String.format("%.5f", (double)y2/HEIGHT);
            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
        }else  if (rect){
            String[] line = new String[5];
            line[0]= "RECTANGLE";
            int x2 = e.getX();
            int y2 = e.getY();
//


            line[1]=  String.format("%.5f", (double)x1/WIDTH);
            line[2]=  String.format("%.5f", (double)y1/HEIGHT);
            line[3]=  String.format("%.5f", (double)x2/WIDTH);
            line[4]=  String.format("%.5f", (double)y2/HEIGHT);
            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
        }else  if (elipse){
            String[] line = new String[5];
            line[0]= "ELLIPSE";
            int x2 = e.getX();
            int y2 = e.getY();
//


            line[1]=  String.format("%.5f", (double)x1/WIDTH);
            line[2]=  String.format("%.5f", (double)y1/HEIGHT);
            line[3]=  String.format("%.5f", (double)x2/WIDTH);
            line[4]=  String.format("%.5f", (double)y2/HEIGHT);
            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
        }



    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when the component's size changes.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println();

       HEIGHT = pnlDisplay.getSize().height;
        WIDTH = pnlDisplay.getSize().width;
        if (g != null){
            g = makeGraphics(content);
            s.paintComponent(g);

        }

    }

    /**
     * Invoked when the component's position changes.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentMoved(ComponentEvent e) {

    }

    /**
     * Invoked when the component has been made visible.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentShown(ComponentEvent e) {

    }

    /**
     * Invoked when the component has been made invisible.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentHidden(ComponentEvent e) {

    }


    /**
     * An undoable edit happened
     *
     * @param e an {@code UndoableEditEvent} object
     */
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        System.out.println(e);

    }
}

