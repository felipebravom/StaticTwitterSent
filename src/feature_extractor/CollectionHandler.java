/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feature_extractor;

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
    protected String inputFile;    // file with the collection of tweets
    protected String evalTweetsFolder;  // Folder where evaluated Tweets will be stored    
    protected List<EntryController> entryControllers;  // A list of controlles for each entry within the collection
    
    
    public CollectionHandler(String inputFolder,String evalTweetsFolder){
        this.inputFile=inputFolder;
        this.evalTweetsFolder=evalTweetsFolder;
        this.entryControllers=new ArrayList<EntryController>();   // A list of EntryControllers    

    }
    

    // process the dataset
   public abstract void process();  

    

   
}
