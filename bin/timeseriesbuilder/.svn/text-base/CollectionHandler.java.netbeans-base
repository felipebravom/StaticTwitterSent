/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeseriesbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felipe
 * Handles a temporal collection of Tweets and export 
 * opinion time series from it
 */
public abstract class CollectionHandler {
    protected String inputFolder;    // path of the collection
    protected String evalTweetsFolder;  // Folder where evaluated Tweets will be stored
    protected String timeSeriesFolder; // Folder where the final time series will be stored
    
    protected List<EntryController> entryControllers;  // A list of controlles for each entry within the collection
    
    
    public CollectionHandler(String inputFolder,String evalTweetsFolder, String timeSeriesFolder){
        this.inputFolder=inputFolder;
        this.evalTweetsFolder=evalTweetsFolder;
        this.timeSeriesFolder=timeSeriesFolder;
        this.entryControllers=new ArrayList<EntryController>();       

    }
    


    // process the dataset
   public abstract void process();  

    

   
}
