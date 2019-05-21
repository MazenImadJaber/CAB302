package VectorDesignTool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class gui extends JFrame implements ActionListener, MouseListener, ComponentListener, Runnable{
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
    JButton OpenBtn,plotbtn, saveAsBrn,linebtn, rectbtn,elipsbtn;
    Graphics g;
    boolean plot,line,rect,elipse = false;
    int xoffset = 0;
    int yoffset = 0;
    int x1,y1;

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
                plotbtn.setEnabled(true);
                saveAsBrn.setEnabled(true);
                plotbtn.doClick();
                linebtn.setEnabled(true);
                rectbtn.setEnabled(true);
                elipsbtn.setEnabled(true);


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
else if (buttonString.equals(plotbtn.getText()))
{
    plotbtn.setBackground(Color.white);
    linebtn.setBackground(Color.gray);
    rectbtn.setBackground(Color.gray);
    elipsbtn.setBackground(Color.gray);
    plot = true;
    line = false;
    rect = false;
    elipse = false;
}
else if (buttonString.equals(linebtn.getText()))
{
    plotbtn.setBackground(Color.gray);
    linebtn.setBackground(Color.white);
    rectbtn.setBackground(Color.gray);
    elipsbtn.setBackground(Color.gray);
    plot = false;
    line = true;
    rect = false;
    elipse = false;
}
else if (buttonString.equals(rectbtn.getText()))
{
    plotbtn.setBackground(Color.gray);
    linebtn.setBackground(Color.gray);
    rectbtn.setBackground(Color.white);
    elipsbtn.setBackground(Color.gray);
    plot = false;
    line = false;
    rect = true;
    elipse = false;
}
else if (buttonString.equals(elipsbtn.getText()))
{
    plotbtn.setBackground(Color.gray);
    linebtn.setBackground(Color.gray);
    rectbtn.setBackground(Color.gray);
    elipsbtn.setBackground(Color.white);
    plot = false;
    line = false;
    rect = false;
    elipse = true;
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
//
//    for (String[] line : content){
//        String l ="";
//        for (int i =0; i<line.length; i++){
//            l += line[i] +" ";
//
//        }
//        System.out.println(l);
//    }




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
                int x=(x1+x2)/2;
                int y=(y1+y2)/2;

                if (fill!= null)
                {
                    g.setColor(fill);
                    g.fillOval(x1,y1,Math.abs(x2-x1),Math.abs(y2-y1));
                }
                if (outline!= null)
                {
                    g.setColor(outline);
                }
                g.drawOval(x1,y1,Math.abs(x2-x1),Math.abs(y2-y1));

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
                int x=(x1+x2)/2;
                int y=(y1+y2)/2;

                if (fill!= null){
                    g.setColor(fill);
                    g.fillRect(x1,y1,Math.abs(x2-x1),Math.abs(y2-y1));
                }
                if (outline!= null){
                    g.setColor(outline);
                }

                g.drawRect(x1,y1,Math.abs(x2-x1),Math.abs(y2-y1));
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
      //  setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pnlDisplay = createPanel(Color.white);
        pnlDisplay.addMouseListener(this );
        pnlDisplay.addComponentListener(this);
        pnlEast = createPanel(Color.lightGray);
        pnlUp = createPanel(Color.lightGray);
        pnlWest = createPanel(Color.lightGray);
        pnlBtn = createPanel(Color.lightGray);
       s=new Shape();
        //ChooseFile chsr = new ChooseFile();
        OpenBtn = new JButton("Select file");
        plotbtn = new JButton("Plot");
        saveAsBrn= new JButton("Save as..");
        linebtn = new JButton("LINE");
        rectbtn = new JButton("RECTANGLE");
        elipsbtn = new JButton("Ellipse");
        OpenBtn.addActionListener(this);
        saveAsBrn.addActionListener(this);
        plotbtn.addActionListener(this);
       linebtn.addActionListener(this);
        rectbtn.addActionListener(this);
        elipsbtn.addActionListener(this);
        saveAsBrn.setEnabled(false);
        linebtn.setEnabled(false);
        rectbtn.setEnabled(false);
        plotbtn.setEnabled(false);
        elipsbtn.setEnabled(false);
        linebtn.setBackground(Color.gray);
        plotbtn.setBackground(Color.gray);
        rectbtn.setBackground(Color.gray);
        elipsbtn.setBackground(Color.gray);




        getContentPane().add(pnlDisplay,BorderLayout.CENTER);
        getContentPane().add(pnlEast,BorderLayout.EAST);
        getContentPane().add(pnlUp,BorderLayout.NORTH);
        getContentPane().add(pnlWest,BorderLayout.WEST);
        getContentPane().add(pnlBtn,BorderLayout.SOUTH);
        pnlDisplay.add(s);
        pnlUp.add(OpenBtn);
        pnlUp.add(saveAsBrn);
        pnlWest.setLayout(new BoxLayout(pnlWest,BoxLayout.Y_AXIS));
        pnlWest.add(plotbtn);
        pnlWest.add(linebtn);
        pnlWest.add(rectbtn);
        pnlWest.add(elipsbtn);




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


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {


        if (plot){
            String[] line = new String[3];
            line[0]="PLOT";
            double x = e.getX();
            x/= WIDTH;
            double y = e.getY();
            y/=HEIGHT;

            line[1]=  String.format("%.5f", x);
            line[2]=  String.format("%.5f", y);
            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
            //System.out.println(content);

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


}

