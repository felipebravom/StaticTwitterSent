/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeseriesbuilder;

/**
 *
 * @author fbravo
 */
public class TimeSeriesBuilder {

    /**
     * @param args the command line arguments
     * tweets/iphone evalTweets timeSeriesData eval.csv
     */
    public static void main(String[] args) {
        String inputFolder;  // the Folder with the Input tweets
        String evalTweetsFolder; // Folder with evaluated tweets
        String timeSeriesFolder; // Folder with timeSeries Data
        String timeSeriesFileName; // Name of the final time series

        

        if (args.length == 4) {
            inputFolder = args[0];
            evalTweetsFolder = args[1];
            timeSeriesFolder = args[2];
            timeSeriesFileName = args[3];


            CollectionHandler ch = new EnglishTrackedCollectionHandler(inputFolder, evalTweetsFolder, timeSeriesFolder);
            ch.process();
          //  StaticOperations.createTimeSeries(timeSeriesFolder, timeSeriesFileName);


        }











        /*
        String folder = "tweets/iphone";
        CollectionHandler ch = new EnglishTrackedCollectionHandler(folder);
        ch.process();
        StaticOperations.createTimeSeries("timeSeriesData","eval.csv");
        
         */
    }
}
