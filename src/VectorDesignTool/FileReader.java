package VectorDesignTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    File fl;
    Scanner scan ;
    List<String[]> content = new ArrayList<>();
    FileReader(File fl) throws Exception
    {
        this.fl = fl;
        scan = new Scanner(fl);
        while(scan.hasNextLine())
        {
            String line = scan.nextLine().toString();
             System.out.println(line);
            String[] a = (line.split(" "));
            System.out.println(a.toString());
           content.add( a);
        }

    }

    public List<String[]> getcontent(){
        return content;
    }


}
