package VectorDesignTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {

    File fl;
    Scanner scan ;
    private List<String[]> content = new ArrayList<>();

    /**
     * takes a file and reads its content and converts them into a list of arrays of string
     * @param fl file
     * @throws Exception
     */
    FileReader(File fl) throws Exception
    {
        this.fl = fl;
        scan = new Scanner(fl);
        while(scan.hasNextLine())
        {
            String line = scan.nextLine().toString();
            // System.out.println(line);
            String[] a = (line.split(" "));
           // System.out.println(a.toString());
           content.add( a);
        }

    }

    /**
     * gets a list of arrays with every array having the content of a VEC command line of the loaded drawing
     * @return list of string arrays
     */
    public List<String[]> getcontent(){
        return content;
    }


}
