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

    // computes Aggregated features of the EntrySet according to the features of the Entries
    public void computeAggregatedFeatures() {
        int size = this.entrySet.getEntries().size();



        int supervised_neutral_count = 0;
        int supervised_positive_count = 0;
        int supervised_negative_count = 0;

        int lex_positive_count = 0;
        int lex_negative_count = 0;

        int afinn_positive_count = 0;
        int afinn_negative_count = 0;

        int afinn_positiveness_sum = 0;
        int afinn_negativeness_sum = 0;

        int swn3_positive_count = 0;
        int swn3_neutral_count = 0;
        int swn3_negative_count = 0;

        double swn3_positiveness = 0.0d;
        double swn3_negativeness = 0.0d;

        int sentiStrength_positiveness = 0;
        int sentiStrength_negativeness = 0;


        for (Entry entry : this.entrySet.getEntries()) {
            String supervised_polarity = entry.getMetaData().get("supervised_polarity").toString();
            if (supervised_polarity.equals("positive")) {
                supervised_positive_count++;
            } else if (supervised_polarity.equals("neutral")) {
                supervised_neutral_count++;
            } else {
                supervised_negative_count++;
            }

            lex_positive_count += (Integer) entry.getMetaData().get("lex_positive_words");
            lex_negative_count += (Integer) entry.getMetaData().get("lex_negative_words");
            afinn_positive_count += (Integer) entry.getMetaData().get("afinn_positive_words");
            afinn_negative_count += (Integer) entry.getMetaData().get("afinn_negative_words");
            afinn_positiveness_sum += (Integer) entry.getMetaData().get("afinn_positiveness");
            afinn_negativeness_sum += (Integer) entry.getMetaData().get("afinn_negativeness");

            swn3_positive_count += (Integer) entry.getMetaData().get("swn3_positive_words");
            swn3_neutral_count += (Integer) entry.getMetaData().get("swn3_neutral_words");
            swn3_negative_count += (Integer) entry.getMetaData().get("swn3_negative_words");

            swn3_positiveness += (Double) entry.getMetaData().get("swn3_positiveness");
            swn3_negativeness += (Double) entry.getMetaData().get("swn3_negativeness");

            sentiStrength_positiveness += (Integer) entry.getMetaData().get("sentiStrength_pos");
            sentiStrength_negativeness += (Integer) entry.getMetaData().get("sentiStrength_neg");



        }



        this.entrySet.getAggFeatures().put("tweets_count", size);
        this.entrySet.getAggFeatures().put("supervised_positive_count", supervised_positive_count);
        this.entrySet.getAggFeatures().put("supervised_neutral_count", supervised_neutral_count);
        this.entrySet.getAggFeatures().put("supervised_negative_count", supervised_negative_count);
        this.entrySet.getAggFeatures().put("lex_positive_count", lex_positive_count);
        this.entrySet.getAggFeatures().put("lex_negative_count", lex_negative_count);
        this.entrySet.getAggFeatures().put("afinn_positive_count", afinn_positive_count);
        this.entrySet.getAggFeatures().put("afinn_negative_count", afinn_negative_count);
        this.entrySet.getAggFeatures().put("afinn_positiveness_sum", afinn_positiveness_sum);
        this.entrySet.getAggFeatures().put("afinn_negativeness_sum", afinn_negativeness_sum);
        this.entrySet.getAggFeatures().put("swn3_positive_count", swn3_positive_count);
        this.entrySet.getAggFeatures().put("swn3_neutral_count", swn3_neutral_count);
        this.entrySet.getAggFeatures().put("swn3_negative_count", swn3_negative_count);
        this.entrySet.getAggFeatures().put("swn3_positiveness", swn3_positiveness);
        this.entrySet.getAggFeatures().put("swn3_negativeness", swn3_negativeness);
        this.entrySet.getAggFeatures().put("sentiStrength_positiveness", sentiStrength_positiveness);
        this.entrySet.getAggFeatures().put("sentiStrength_negativeness", sentiStrength_negativeness);





    }

    // Writes the AggregatedFeatures of the EmptySet into a File
    public void writeAggregatedFeatures(String folderPath) {
        PrintWriter out = null;
        try {
            String fileName = this.entrySet.getTopic() + ":" + this.entrySet.getDate();
            out = new PrintWriter(new FileWriter(folderPath + "/" + fileName, true));

            String[] metaData = this.entrySet.getAggFeatures().keySet().toArray(new String[0]);

            String firstLine = "topic\tdate\t";
            String secondLine = this.entrySet.getTopic() + "\t" + this.entrySet.getDate() + "\t";
            for (int i = 0; i < metaData.length; i++) {
                firstLine += metaData[i];
                secondLine += this.entrySet.getAggFeatures().get(metaData[i]);
                if (i < metaData.length - 1) {
                    firstLine += "\t";
                    secondLine += "\t";
                }
            }

            out.println(firstLine);
            out.println(secondLine);



        } catch (IOException ex) {
            Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }



    }

    // Writes the tweets with their respective Sentiment Values
    public void writeProcessedEntries(String folderPath) {
        PrintWriter out = null;
        try {
            String fileName = this.entrySet.getTopic() + ":" + this.entrySet.getDate();
            out = new PrintWriter(new FileWriter(folderPath + "/" + fileName, true));
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
