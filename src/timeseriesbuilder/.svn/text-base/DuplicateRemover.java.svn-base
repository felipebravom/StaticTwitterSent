/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeseriesbuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fbravo
 * Removes potential  duplicate Tweets from a file
 */
public class DuplicateRemover {

    private String fileName;
    private String outFileName;

    public DuplicateRemover(String name, String out) {
        this.fileName = name;
        this.outFileName = out;
    }

    // Process the File
    public void process() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));
            PrintWriter pw = new PrintWriter(new FileWriter(this.outFileName));
            HashMap<String, String> maper = new HashMap<String, String>();
            String line;

            while ((line = br.readLine()) != null) {
                String parts[] = line.split("\",\"");

                if (parts.length == 6) {
                    if (!parts[4].isEmpty()) {
                        System.out.println(parts.length);
                        System.out.println(parts[4].length());

                        String key = parts[4].length() <= 70 ? parts[4] : parts[4].substring(0, 70);
                        //  System.out.println(key);

                        if (!maper.containsKey(key)) {
                            maper.put(key, line);
                        }

                    }
                }


            }
            br.close();




            for (String outline : maper.keySet()) {
                pw.println(maper.get(outline));
            }
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(DuplicateRemover.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    static public void main(String args[]) {
        String fileName = "/home/fbravo/opinionmining/España/rajoy/rajoy:23-10-2011";
        DuplicateRemover dr = new DuplicateRemover(fileName, "lala.txt");
        dr.process();

    }
}
