package VectorDesignTool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class gui extends JFrame implements ActionListener, Runnable{
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    private JPanel pnlDisplay, pnlEast, pnlUp, pnlWest, pnlBtn, pnlshape;
    Shape s;
    public String dirr ="";
    public  String filName="";
    JFileChooser filChsr;
    String choosertitle;
    File file;
    JButton btn;

    public gui(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
                FileReader flReader = new FileReader(file);
                Graphics g = makeGraphics(flReader.getcontent());

                s.paintComponent(g);
//                JPanel drawing = createPanel(Color.WHITE);
//                drawing.paint(g);
//                getContentPane().add(drawing,BorderLayout.CENTER);
                //repaint();


            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            //ExamineImage.lum(sourceFolder);

        }
        else
        {
            System.out.println("No Selection ");
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

                int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-35));
                int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-85));
                int x2 = (int)(Double.parseDouble(line[3])*(WIDTH-35));
                int y2 = (int)(Double.parseDouble(line[4])*(HEIGHT-85));
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
                int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-35));
                int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-85));
                g.fillRect(x1,y1,1,1);
            }
            else if (line[0].equals("RECTANGLE"))
            {

                int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-35));
                int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-85));
                int x2 = (int)(Double.parseDouble(line[3])*(WIDTH-35));
                int y2 = (int)(Double.parseDouble(line[4])*(HEIGHT-85));
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
                        y[i/2-1]=(int)(Double.parseDouble(line[i])*(HEIGHT-85));
                    }
                    else {
                        x[(i-1)/2]=(int)(Double.parseDouble(line[i])*(WIDTH-35));
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
        int x1 = (int)(Double.parseDouble(line[1])*(WIDTH-35));
        int y1 = (int)(Double.parseDouble(line[2])*(HEIGHT-85));
        int x2 = (int)(Double.parseDouble(line[3])*(WIDTH-35));
        int y2 = (int)(Double.parseDouble(line[4])*(HEIGHT-85));
        g.drawLine(x1,y1,x2,y2);
    }


    @Override
    public void run() {
        createGUI();
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pnlDisplay = createPanel(Color.white);
        pnlEast = createPanel(Color.lightGray);
        pnlUp = createPanel(Color.lightGray);
        pnlWest = createPanel(Color.lightGray);
        pnlBtn = createPanel(Color.lightGray);
       s=new Shape();
        //ChooseFile chsr = new ChooseFile();
        btn = new JButton("Select file");
        btn.addActionListener(this);
        add(btn);



        getContentPane().add(pnlDisplay,BorderLayout.CENTER);
        getContentPane().add(pnlEast,BorderLayout.EAST);
        getContentPane().add(pnlUp,BorderLayout.NORTH);
        getContentPane().add(pnlWest,BorderLayout.WEST);
        getContentPane().add(pnlBtn,BorderLayout.SOUTH);
        pnlDisplay.add(s);
        pnlUp.add(btn, BorderLayout.NORTH);





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

