package VectorDesignTool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class ChooseFile extends JPanel implements ActionListener
{
    JButton btn;
    public String dirr ="";
    public  String filName="";
    JFileChooser filChsr;
    String choosertitle;
    File file;


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public ChooseFile()
    {
        btn = new JButton("Select file");
        btn.addActionListener(this);
        add(btn);
    }
    public File getFile(){
        return file;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
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
                FileReader flReader = new FileReader(file);

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



}

