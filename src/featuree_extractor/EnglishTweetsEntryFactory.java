/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featuree_extractor;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fbravo
 */
public class EnglishTweetsEntryFactory extends EntryFactory {

    public EnglishTweetsEntryFactory(String data) {
        super(data);
    }

    @Override
    // Invalid entries must be omitted
    public Entry createEntry() {
        Entry e = new Entry();

        String parts[] = this.entryData.split("\",\"");
        if (parts.length == 6) {
            String date = "";
            String tweetId = parts[0].replaceAll("\"", "");
            String timeStamp = parts[1];
            String userId = parts[3];

            e.getFeatures().put("tweetId", tweetId);
            e.getFeatures().put("timeStamp", timeStamp);
            e.getFeatures().put("userId", userId);


            /*
            try {
                date = StaticOperations.dateConverter(timeStamp);
            } catch (ParseException ex) {
                Logger.getLogger(EnglishTweetsEntryFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.setDate(date);

             * */
             
            
            String message = parts[4].replaceAll("\t", " ");
            e.setContent(message);
            
            // The entry is valid
            e.setValid(true);

        }
        
        else{
        
        // Reporto Falla
        System.out.println("falle");
        System.out.println(parts.length);
        System.out.println(this.entryData);
        }




        return e;
    }

    static public void main(String args[]) {
        String t = "\"131290957600669696\",\"Tue Nov 01 05:46:18 CLST 2011\",\"rajoy 20N -RT lang:es\",\"93677724\",\"Obama: '$77 Billion A Year From Hemp good Won't Help Economy!' http://t.co/3TLSFV7Y\",\"NO LOCATION\"";
        EntryFactory ef = new EnglishTweetsEntryFactory(t);
        Entry e = ef.createEntry();
        System.out.println(e.toString());



    }
}
