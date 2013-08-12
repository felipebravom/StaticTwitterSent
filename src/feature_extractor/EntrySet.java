/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feature_extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    
    @Override
    public String toString(){
        String value="";
        for(Entry e:this.Entries){
            value+=e.toString()+"\n";
        }
        
       for(String feat:this.aggFeatures.keySet()){
           value+=feat+":"+this.aggFeatures.get(feat)+"\n";
       }
        
        
        return value;
    }
    
    
    
    
    
}
