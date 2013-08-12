/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featuree_extractor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felipe
 * Handles a  collection of Tweets and export a dataset with the corresponding 
 * features 
 * 
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
