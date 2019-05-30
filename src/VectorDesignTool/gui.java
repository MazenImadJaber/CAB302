package VectorDesignTool;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gui extends JFrame implements ActionListener, MouseListener, ComponentListener, Runnable {
    private static int WIDTH = 700; // initial width of the frame
    private static int HEIGHT = 700;// initial height of the frame
    private List<String[]> content;      // initialise the content list
    private JPanel pnlDisplay, pnlEast, pnlUp, pnlWest, pnlDown; // initialise JPanels;
    private Shape s; // initialise S, Shape object
    private String dirr = ""; // initialise the drictory string for the file
    private String filName = "";
    private JFileChooser filChsr;
    private String choosertitle = "";
    private FileReader flReader;
    private File file;
    private JButton OpenBtn, plotBtn, saveAsBrn, linebtn, rectBtn, elipsBtn, ployBtn, unDoBtn, fillBtn, penBtn, noFillBtn;
    private Graphics g;
    private boolean plot, line, rect, elipse, ploy = false;
    private int xoffset = 0;
    private int yoffset = 0;
    private int x1, y1;

    private JLabel txt, filTilte;
    private List<Double> pPoints = new ArrayList<>();


    public gui(String title) {
        super(title);
    }

    /**
     * handels logic for buttons
     *
     * @param e action preformed, AKA button clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonString = e.getActionCommand();
        System.out.println(e.getActionCommand());
        if (buttonString.equals(OpenBtn.getText())) {

            filChsr = new JFileChooser();
            filChsr.setCurrentDirectory(new java.io.File("."));
            filChsr.setDialogTitle(choosertitle);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "VEC files ", "vec");
            filChsr.setFileFilter(filter);
            filChsr.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (filChsr.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

                dirr += filChsr.getCurrentDirectory();
                file = filChsr.getSelectedFile();
                filName = file.getName();
                filTilte.setText(filName);

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


                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            } else {
                System.out.println("No Selection ");
            }
        } else if (buttonString.equals(plotBtn.getText())) {
            plotBtn.setBackground(Color.white);
            linebtn.setBackground(Color.gray);
            rectBtn.setBackground(Color.gray);
            elipsBtn.setBackground(Color.gray);
            txt.setVisible(false);
            plot = true;
            line = false;
            rect = false;
            elipse = false;
            pPoints.clear();
            txt.setText("Right click Mouse to draw polygon, number of points: " + pPoints.size() / 2);

        } else if (buttonString.equals(linebtn.getText())) {
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
            pPoints.clear();
            txt.setText("Right click Mouse to draw polygon, number of points: " + pPoints.size() / 2);

        } else if (buttonString.equals(rectBtn.getText())) {
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
            pPoints.clear();
            txt.setText("Right click Mouse to draw polygon, number of points: " + pPoints.size() / 2);


        } else if (buttonString.equals(elipsBtn.getText())) {
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
            pPoints.clear();
            txt.setText("Right click Mouse to draw polygon, number of points: " + pPoints.size() / 2);

        } else if (buttonString.equals(ployBtn.getText())) {

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
        } else if (buttonString.equals(saveAsBrn.getText())) {
            System.out.println("Save as..   Clicked");
            filChsr.showSaveDialog(this);
            File f = filChsr.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(f);
                for (String[] line : content) {
                    String l = "";
                    for (int i = 0; i < line.length; i++) {
                        l += line[i] + " ";

                    }
                    l += "\n";
                    System.out.println(l);
                    fw.write(l);

                }
                fw.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } else if (buttonString.equals((unDoBtn.getText()))) {
            content.remove(content.size() - 1);
            g = makeGraphics(content);
            s.paintComponent(g);
        } else if (buttonString.equals((penBtn.getText()))) {
            penBtn.setBackground(Color.white);
            JColorChooser jcc = new JColorChooser();
            JDialog co = JColorChooser.createDialog(null, "Pick A Color", true, jcc, this, null);
            co.setVisible(true);
            Color c = jcc.getColor();
            String[] line = new String[2];
            line[0] = "PEN";
            System.out.println(String.format("#%06x", c.getRGB() & 0x00FFFFFF));
            line[1] = String.format("#%06x", c.getRGB() & 0x00FFFFFF);
            content.add(line);
            penBtn.setBackground(Color.gray);

        } else if (buttonString.equals((fillBtn.getText()))) {
            fillBtn.setBackground(Color.white);
            JColorChooser jcc = new JColorChooser();
            JDialog co = JColorChooser.createDialog(null, "Pick A Color", true, jcc, this, null);
            co.setVisible(true);
            Color c = jcc.getColor();
            String[] line = new String[2];
            line[0] = "FILL";
            System.out.println(String.format("#%06x", c.getRGB() & 0x00FFFFFF));
            line[1] = String.format("#%06x", c.getRGB() & 0x00FFFFFF);
            content.add(line);
            fillBtn.setBackground(Color.gray);
            noFillBtn.setEnabled(true);


        } else if (buttonString.equals((noFillBtn.getText()))) {
            noFillBtn.setBackground(Color.white);
            String[] line = new String[2];
            line[0] = "FILL";
            line[1] = "OFF";
            content.add(line);
            noFillBtn.setBackground(Color.gray);
            noFillBtn.setEnabled(false);
        }


    }

    /**
     * gets the contents of the file in an array, and coverts them to graphics
     *
     * @param content the content of the loaded file in List<String[]>
     * @return returns the graphics specified n the file
     */

    private Graphics makeGraphics(List<String[]> content) {
        Graphics g = pnlDisplay.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);

        Color outline = null;
        Color fill = null;
        for (String[] line : content) {
            switch (line[0]) {
                case "PEN":
                    outline = Color.decode(line[1]);
                    break;
                case "FILL":
                    if (line[1].equals("OFF")) {
                        fill = null;
                    } else {
                        fill = Color.decode(line[1]);
                    }

                    break;
                case "LINE": {
                    if (outline != null) {
                        g.setColor(outline);
                    }
                    int x1 = (int) (Double.parseDouble(line[1]) * (WIDTH - xoffset));
                    int y1 = (int) (Double.parseDouble(line[2]) * (HEIGHT - yoffset));
                    int x2 = (int) (Double.parseDouble(line[3]) * (WIDTH - xoffset));
                    int y2 = (int) (Double.parseDouble(line[4]) * (HEIGHT - yoffset));
                    g.drawLine(x1, y1, x2, y2);
                    break;
                }
                case "ELLIPSE": {

                    int x1 = (int) (Double.parseDouble(line[1]) * (WIDTH - xoffset));
                    int y1 = (int) (Double.parseDouble(line[2]) * (HEIGHT - yoffset));
                    int x2 = (int) (Double.parseDouble(line[3]) * (WIDTH - xoffset));
                    int y2 = (int) (Double.parseDouble(line[4]) * (HEIGHT - yoffset));
                    int x = x1;
                    int y = y1;
                    if (x1 > x2) {
                        x = x2;
                    }
                    if (y1 > y2) {
                        y = y2;
                    }


                    if (fill != null) {
                        g.setColor(fill);
                        g.fillOval(x, y, Math.abs(x2 - x1), Math.abs(y2 - y1));
                    }
                    if (outline != null) {
                        g.setColor(outline);
                    }
                    g.drawOval(x, y, Math.abs(x2 - x1), Math.abs(y2 - y1));

                    break;
                }
                case "PLOT": {
                    g.setColor(outline);
                    int x1 = (int) (Double.parseDouble(line[1]) * (WIDTH - xoffset));
                    int y1 = (int) (Double.parseDouble(line[2]) * (HEIGHT - yoffset));
                    g.fillRect(x1, y1, 1, 1);
                    break;
                }
                case "RECTANGLE": {

                    int x1 = (int) (Double.parseDouble(line[1]) * (WIDTH - xoffset));
                    int y1 = (int) (Double.parseDouble(line[2]) * (HEIGHT - yoffset));
                    int x2 = (int) (Double.parseDouble(line[3]) * (WIDTH - xoffset));
                    int y2 = (int) (Double.parseDouble(line[4]) * (HEIGHT - yoffset));
                    int x = x1;
                    int y = y1;
                    if (x1 > x2) {
                        x = x2;
                    }
                    if (y1 > y2) {
                        y = y2;
                    }


                    if (fill != null) {
                        g.setColor(fill);
                        g.fillRect(x, y, Math.abs(x2 - x1), Math.abs(y2 - y1));
                    }
                    if (outline != null) {
                        g.setColor(outline);
                    }

                    g.drawRect(x, y, Math.abs(x2 - x1), Math.abs(y2 - y1));
                    break;
                }
                case "POLYGON": {
                    int numPoints = line.length - 1;

                    int[] y = new int[numPoints / 2];
                    int[] x = new int[numPoints / 2];

                    for (int i = 1; i <= numPoints; i++) {
                        if (i % 2 == 0) {
                            y[i / 2 - 1] = (int) (Double.parseDouble(line[i]) * (HEIGHT - yoffset));
                        } else {
                            x[(i - 1) / 2] = (int) (Double.parseDouble(line[i]) * (WIDTH - xoffset));
                        }
                    }
                    Polygon p = new Polygon(x, y, numPoints / 2);


                    if (fill != null) {
                        g.setColor(fill);
                        g.fillPolygon(p);
                    }
                    if (outline != null) {
                        g.setColor(outline);
                    }

                    g.drawPolygon(p);
                    break;
                }
            }
        }
        return g;
    }


    /**
     * Runs GUI
     */
    @Override
    public void run() {
        createGUI();
    }

    /**
     * Creats GUT
     */
    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        s = new Shape();
        txt = new JLabel("Right click Mouse to draw polygon");
        filTilte = new JLabel();
        txt.setVisible(false);

        setUpPanels();
        setUpButtons();
        pnlDisplay.add(s);
        setVisible(true);
        repaint();

    }

    /**
     * Sets up panels and adds them to a BorderLayout
     */
    private void setUpPanels() {
        setLayout(new BorderLayout());
        pnlDisplay = createPanel(Color.white);
        pnlDisplay.addMouseListener(this);
        pnlDisplay.addComponentListener(this);
        pnlEast = createPanel(Color.lightGray);
        pnlUp = createPanel(Color.lightGray);
        pnlWest = createPanel(Color.gray);
        pnlDown = createPanel(Color.lightGray);
        getContentPane().add(pnlDisplay, BorderLayout.CENTER);
        getContentPane().add(pnlEast, BorderLayout.EAST);
        getContentPane().add(pnlUp, BorderLayout.NORTH);
        getContentPane().add(pnlWest, BorderLayout.WEST);
        getContentPane().add(pnlDown, BorderLayout.SOUTH);
        pnlDown.add(txt);

    }

    /**
     * creates all bottons for the GUI and add them to the specified panel
     */
    private void setUpButtons() {
        OpenBtn = new JButton("Select file");
        saveAsBrn = new JButton("Save as..");
        plotBtn = new JButton(" Plot");
        linebtn = new JButton("  LINE");
        rectBtn = new JButton("Rectangle");
        elipsBtn = new JButton("  Ellipse");
        ployBtn = new JButton("Polygon");
        fillBtn = new JButton("Fill shape");
        noFillBtn = new JButton("Fill off");
        penBtn = new JButton("Pen Colour");
        unDoBtn = new JButton("UNDO");
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
        pnlUp.add(filTilte);
        pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
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

    /**
     * creates a JPanel with a specific Background color, c.
     *
     * @param c Colour for Background
     * @return a JPanel
     */

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
        x /= WIDTH;
        double y = e.getY();
        y /= HEIGHT;

        if (plot) {
            String[] line = new String[3];
            line[0] = "PLOT";


            line[1] = String.format("%.5f", x);
            line[2] = String.format("%.5f", y);
            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
            //System.out.println(content);

        } else if (ploy) {
            if (e.getButton() == 1) {
                pPoints.add(x);
                pPoints.add(y);
                txt.setText("Right click Mouse to draw polygon, number of points: " + pPoints.size() / 2);
            } else if (e.getButton() == 3) {
                String[] line = new String[pPoints.size() + 1];
                line[0] = "POLYGON";
                int i = 1;
                for (double p : pPoints) {
                    line[i] = String.format("%.5f", p);
                    i++;
                }

                pPoints.clear();
                txt.setText("Right click Mouse to draw polygon, number of points: " + pPoints.size() / 2);
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

        y1 = e.getY();



    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        int x2 = e.getX();
        int y2 = e.getY();
        if (x2>WIDTH)    x2 = WIDTH;
        if (x2<0) x2=0;
        if (y2>HEIGHT) y2=HEIGHT;
        if (y2<0) y2=0;




        if (line) {
            String[] line = new String[5];
            line[0] = "LINE";
            line[1] = String.format("%.5f", (double) x1 / WIDTH);
            line[2] = String.format("%.5f", (double) y1 / HEIGHT);
            line[3] = String.format("%.5f", (double) x2 / WIDTH);
            line[4] = String.format("%.5f", (double) y2 / HEIGHT);
            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
        } else if (rect) {
            String[] line = new String[5];
            line[0] = "RECTANGLE";
            line[1] = String.format("%.5f", (double) x1 / WIDTH);
            line[2] = String.format("%.5f", (double) y1 / HEIGHT);
            line[3] = String.format("%.5f", (double) x2 / WIDTH);
            line[4] = String.format("%.5f", (double) y2 / HEIGHT);

            content.add(line);
            g = makeGraphics(content);
            s.paintComponent(g);
        } else if (elipse) {
            String[] line = new String[5];
            line[0] = "ELLIPSE";
            line[1] = String.format("%.5f", (double) x1 / WIDTH);
            line[2] = String.format("%.5f", (double) y1 / HEIGHT);
            line[3] = String.format("%.5f", (double) x2 / WIDTH);
            line[4] = String.format("%.5f", (double) y2 / HEIGHT);
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
        HEIGHT = pnlDisplay.getSize().height;
        WIDTH = pnlDisplay.getSize().width;
        if (g != null) {
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

