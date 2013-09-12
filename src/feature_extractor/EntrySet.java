/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feature_extractor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fbravo
 * Represents a set of Entries with Aggregated Features
 */
public class EntrySet {
    private List<Entry> Entries;
    private Map<String, Object> aggFeatures;  //Aggregated Features
    private String topic;
    private String date;
    
    public EntrySet(){
        this.Entries=new ArrayList<Entry>();
        this.aggFeatures=new HashMap<String, Object>();
        
        
    }

    public List<Entry> getEntries() {
        return Entries;
    }

    public Map<String, Object> getAggFeatures() {
        return aggFeatures;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    
    // Writes the tweets with their respective Sentiment Values
    public void writeProcessedEntries(String fileName) {
        PrintWriter out = null;
        try {
          
            out = new PrintWriter(new FileWriter(fileName, true));
            // check the presence of any Entry
            if (!this.getEntries().isEmpty()) {
                // retrieves the features of the Entries
                String[] features = this.getEntries().get(0).getFeatures().keySet().toArray(new String[1]);

                // Writes the header of the file with the features
                String firstLine = "content\t";

                for (int i = 0; i < features.length; i++) {
                    firstLine += features[i];
                    if (i < features.length - 1) {
                        firstLine += "\t";
                    }
                }
                out.println(firstLine);

                // traverses all entries 
                for (Entry entry : this.getEntries()) {

                    String line = entry.getContent().replaceAll("\t", " ") + "\t";
                    for (int i = 0; i < features.length; i++) {
                        line += entry.getMetaData().get(features[i]);
                        if (i < features.length - 1) {
                            line += "\t";
                        }
                    }
                    out.println(line);


                }


            }
        } catch (IOException ex) {
            Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
    
    
    
    
    
}
