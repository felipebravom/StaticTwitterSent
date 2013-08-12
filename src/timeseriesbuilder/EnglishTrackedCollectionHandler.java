package timeseriesbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import featuree_extractor.CollectionHandler;
import featuree_extractor.Entry;
import featuree_extractor.EntryController;
import featuree_extractor.EntrySet;
import featuree_extractor.EntrySetController;
import featuree_extractor.SWN3;
import uk.ac.wlv.sentistrength.SentiStrength;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author fbravo
 */
public class EnglishTrackedCollectionHandler extends CollectionHandler {

    public EnglishTrackedCollectionHandler(String inputFolder, String evalTweetsFolder, String timeSeriesFolder) {
        super(inputFolder, evalTweetsFolder, timeSeriesFolder);
    }

    @Override
    public void process() {


        // We first process the lexicons
        LexiconEvaluator polarLex = new LexiconEvaluator("extra/polarity-lexicon.txt");
        polarLex.processDict();
        LexiconEvaluator afinnLex = new LexiconEvaluator("extra/AFINN-111.txt");
        afinnLex.processDict();

        SWN3 swn3 = new SWN3("extra/SentiWordNet_3.0.0.txt");


        SentiStrength sentiStrength = new SentiStrength();
        String sentiParams[] = {"sentidata", "extra/SentiStrength/"};
        sentiStrength.initialise(sentiParams);


        String tmpDate = "";


        File inpFolder = new File(this.inputFolder);

        SimpleDateFormat evalTweetFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tweetFormat = new SimpleDateFormat("dd-MM-yyyy");


        // Create a HashMap with Files already processed 
        File evalTweets = new File(this.evalTweetsFolder);
        List<String> procFiles = new ArrayList<String>();
        for (File tmpFile : evalTweets.listFiles()) {
            if (tmpFile.getName().contains(":") && tmpFile.isFile()) {
                String evalDate = tmpFile.getName().substring(tmpFile.getName().indexOf(":") + 1);

                procFiles.add(evalDate);
                
                /*
                try {
                    Date evalTweetDate = evalTweetFormat.parse(evalDate);
                    procFiles.add(evalTweetDate);                   
                } catch (ParseException ex) {
                    Logger.getLogger(EnglishTrackedCollectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }

                 */
            }




        }



        File[] listOfFiles = inpFolder.listFiles();


        EntrySet entrySet = new EntrySet();

        for (int i = 0; i < listOfFiles.length; i++) {
            // Files already present in EvalTweets are not processed again
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(":")) {


                String tweetDateString = listOfFiles[i].getName().substring(listOfFiles[i].getName().indexOf(":") + 1);
                
                /*
                Date tweetDate = new Date();
                try {
                    tweetDate = tweetFormat.parse(tweetDateString);
                } catch (ParseException ex) {
                    Logger.getLogger(EnglishTrackedCollectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }
                 * */
                 


                //   System.out.println(procFiles.contains(tweetDate));


                if (!procFiles.contains(tweetDateString)) {

                    BufferedReader bf = null;
                    try {


                        bf = new BufferedReader(new FileReader(listOfFiles[i]));
                        String line;
                        while ((line = bf.readLine()) != null) {


                            EntryController ec = new EntryController(line); // create the EntryController
                            ec.setFormat("EnglishTweets"); // seteo al formato elections
                            ec.createEntry();  // create the Entry
        
                            // Add the date of the file
                            ec.getEntry().setDate(tweetDateString);
                            
                            // Only process Valid entries
                            if (ec.getEntry().isValid()) {

                                ec.processWords();
                                Entry entry = ec.getEntry();
                                String date = entry.getDate();

                                String topic = inpFolder.getName();
                                entry.getMetaData().put("topic", topic);
                                ec.evaluatePolarityLexicon(polarLex); // evaluate Lexicon Polarity

                                ec.evaluateAFINNLexicon(afinnLex);
                                ec.evaluateSWN3(swn3);
                                ec.evaluateSentiStrength(sentiStrength);



                                // Create the first EntrySet
                                if (i == 0) {
                                    tmpDate = date;
                                    entrySet.setDate(date);
                                    entrySet.setTopic(topic);
                                    entrySet.getEntries().add(entry);

                                } // If we the entry belongs to the same Date we just add it to the EntrySet
                                else if (tmpDate.equals(date)) {
                                    entrySet.getEntries().add(entry);
                                } // Otherwise we process the EntrySet and create a new one with the new Entry
                                else {
                                    EntrySetController entrySetController = new EntrySetController(entrySet);

                                    entrySetController.evaluateSentimentApiEntrySet();
                                   // entrySetController.computeAggregatedFeatures();

                                    entrySetController.writeProcessedEntries(this.evalTweetsFolder);
                                  //  entrySetController.writeAggregatedFeatures(timeSeriesFolder);

                                    // We start with a new empty EntrySet 
                                    entrySet = new EntrySet();
                                    tmpDate = date;
                                    entrySet.setDate(date);
                                    entrySet.setTopic(topic);
                                    entrySet.getEntries().add(entry);


                                }
                            }



                        }
                    } catch (IOException ex) {
                        Logger.getLogger(EnglishTrackedCollectionHandler.class.getName()).log(Level.SEVERE, null, ex);

                    } finally {
                        try {
                            bf.close();
                        } catch (IOException ex) {
                            Logger.getLogger(EnglishTrackedCollectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }



        EntrySetController entrySetController = new EntrySetController(entrySet);

        entrySetController.evaluateSentimentApiEntrySet();
        //   entrySetController.computeAggregatedFeatures();

        entrySetController.writeProcessedEntries(this.evalTweetsFolder);
        //    entrySetController.writeAggregatedFeatures(timeSeriesFolder);




    }
}
