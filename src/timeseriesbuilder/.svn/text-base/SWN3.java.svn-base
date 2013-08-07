/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeseriesbuilder;

/**
 *
 * @author fbravo
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SWN3 {

    private HashMap<String, Double> _dict;

    public SWN3(String pathToSWN) {
        BufferedReader csv = null;
        try {
            _dict = new HashMap<String, Double>();
            HashMap<String, Double> _temp = new HashMap<String, Double>();
            csv = new BufferedReader(new FileReader(pathToSWN));
            String line = "";
            try {
                while ((line = csv.readLine()) != null) {
                    if (line.startsWith("#") || line.startsWith("				#")) {
                        continue;
                    }

                    String[] data = line.split("\t");
                    Double score = Double.parseDouble(data[2])
                            - Double.parseDouble(data[3]);
                    String[] words = data[4].split(" ");
                    for (String w : words) {
                        String[] w_n = w.split("#");
                        // w_n[0] += "#" + data[0];
                        // int index = Integer.parseInt(w_n[1]) - 1;
                        if (_temp.containsKey(w_n[0])) {
                            Double v = _temp.get(w_n[0]);
                            _temp.put(w_n[0], (v + score) / 2);
                        } else {
                            _temp.put(w_n[0], score);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(SWN3.class.getName()).log(Level.SEVERE, null, ex);
            }
            Set<String> temp = _temp.keySet();
            for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
                String word = (String) iterator.next();
                double score = _temp.get(word);

                _dict.put(word, new Double(score));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SWN3.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                csv.close();
            } catch (IOException ex) {
                Logger.getLogger(SWN3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public HashMap<String, Double> getDict() {
        return _dict;
    }

    static public void main(String args[]) {
        SWN3 s = new SWN3("extra/SentiWordNet_3.0.0.txt");
        for (String word : s.getDict().keySet()) {
            System.out.println(word + " " + s.getDict().get(word).toString());
        }




    }
}