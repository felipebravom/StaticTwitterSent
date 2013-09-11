/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feature_extractor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author felipe Controlls and EntrySet
 */
public class EntrySetController {

    private EntrySet entrySet;

    public EntrySetController(EntrySet entrySet) {
        this.entrySet = entrySet;
    }

    public EntrySet getEntrySet() {
        return entrySet;
    }

    public void setEntrySet(EntrySet entrySet) {
        this.entrySet = entrySet;
    }

    // Evaluates the Polarity of a set of Entries using the TwitterSentimentAPI
    // the list of entries is partionated in order to avoid executing several calls to
    // the API
    public void evaluateSentimentApiEntrySet() {

        int maxSize = 6000; // Max number of elements for API call
        int numEntries = this.entrySet.getEntries().size();
        List<List<Entry>> entryListList = new ArrayList<List<Entry>>(); // a list having each partition



        // We partionate the entries in different lists
        int i = 0;
        while (i + maxSize < numEntries) {
            entryListList.add(this.entrySet.getEntries().subList(i, i + maxSize));
            i += maxSize;
        }
        if (i < numEntries) {
            entryListList.add(this.entrySet.getEntries().subList(i, numEntries));
        }
        


        for (List<Entry> entryList : entryListList) {
            

            try {
                JSONParser parser = new JSONParser();
                JSONObject prim = new JSONObject();
                JSONArray list = new JSONArray();

                for (Entry entry : entryList) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("text", entry.getContent());
                    jsonObj.put("query", this.entrySet.getTopic());
                    list.add(jsonObj);

                }


                prim.put("data", list);


                String requestUrl = "http://www.sentiment140.com/api/bulkClassifyJson?appid=" + URLEncoder.encode("felipebravom@gmail.com", "UTF-8");


                URL url = new URL(requestUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");


                OutputStream os = conn.getOutputStream();
                os.write(prim.toJSONString().getBytes());
                os.flush();


                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                while ((output = br.readLine()) != null) {
                    // System.out.println(output);

                    //Object results = parser.parse(output);
                    JSONObject res = (JSONObject) parser.parse(output);

                    JSONArray list2 = (JSONArray) res.get("data");

                    ListIterator it = list2.listIterator();
                    
                    

                    ListIterator entIt = entryList.listIterator();

                    while (it.hasNext() && entIt.hasNext()) {

                        JSONObject tmp = (JSONObject) it.next();
                        Entry entry = (Entry) entIt.next();
                        Long textPolarity = (Long) tmp.get("polarity");
                        String polarity;
                        if (textPolarity == 0) {
                            polarity = "negative";
                        } else if (textPolarity == 2) {
                            polarity = "neutral";
                        } else {
                            polarity = "positive";
                        }
                        
                       

                        entry.getMetaData().put("supervised_polarity", polarity);
                        System.out.println("Procese");
                        StaticOperations.printTimeStamp();

                    }



                }

                os.close();
                br.close();
                conn.disconnect();

          
                
                Thread.sleep(60 * 1000);



            } catch (InterruptedException ex) {
                Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            } catch (ParseException ex) {
                Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            } catch (IOException ex) {
                Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }


        }




    }


    // Writes the tweets with their respective Sentiment Values
    public void writeProcessedEntries(String fileName) {
        PrintWriter out = null;
        try {
          
            out = new PrintWriter(new FileWriter(fileName, true));
            // check the presence of any Entry
            if (!this.entrySet.getEntries().isEmpty()) {
                // retrieves the metadata of the Entries
                String[] metaData = this.entrySet.getEntries().get(0).getMetaData().keySet().toArray(new String[1]);

                // Writes the header of the file with the features
                String firstLine = "content\ttimeStamp\ttweetId\tuserId\t";

                for (int i = 0; i < metaData.length; i++) {
                    firstLine += metaData[i];
                    if (i < metaData.length - 1) {
                        firstLine += "\t";
                    }
                }
                out.println(firstLine);

                // traverses all entries 
                for (Entry entry : this.entrySet.getEntries()) {
                    String timeStamp = entry.getFeatures().get("timeStamp").toString();
                    String tweetId = entry.getFeatures().get("tweetId").toString();
                    String userId = entry.getFeatures().get("userId").toString();



                    String line = entry.getContent().replaceAll("\t", " ") + "\t" + timeStamp + "\t" + tweetId + "\t" + userId + "\t";
                    for (int i = 0; i < metaData.length; i++) {
                        line += entry.getMetaData().get(metaData[i]);
                        if (i < metaData.length - 1) {
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
