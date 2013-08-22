package feature_extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.wlv.sentistrength.SentiStrength;

public class STSCollectionHandler extends CollectionHandler {

	public STSCollectionHandler(String inputFolder, String evalTweetsFolder) {
		super(inputFolder, evalTweetsFolder);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	    // We first process the lexicons
        LexiconEvaluator polarLex = new LexiconEvaluator("extra/polarity-lexicon.txt");
        polarLex.processDict();
        LexiconEvaluator afinnLex = new LexiconEvaluator("extra/AFINN-111.txt");
        afinnLex.processDict();

        SWN3 swn3 = new SWN3("extra/SentiWordNet_3.0.0.txt");
        
		NRCEvaluator nrc=new NRCEvaluator("extra/NRC-emotion-lexicon-wordlevel-v0.92.txt");
		nrc.processDict();


        SentiStrength sentiStrength = new SentiStrength();
        String sentiParams[] = {"sentidata", "extra/SentiStrength/", "trinary"};
        sentiStrength.initialise(sentiParams);
        
        File inpFolder = new File(this.inputFile);
        
        try {
			BufferedReader bf = new BufferedReader(new FileReader(this.inputFile));
			
			String line;
            try {
            	// Create an EntrySet to be used to submitt the tweets to the Sentiment140 API
            	List<Entry> entSet=new ArrayList<Entry>();
            	
				while ((line = bf.readLine()) != null) {

				    EntryController ec = new EntryController(line); // create the EntryController
				    ec.setFormat("STS"); // seteo al formato elections
				    ec.createEntry();  // create the Entry
				    if (ec.getEntry().isValid()) {

				        ec.processWords();
				        Entry entry = ec.getEntry();
//                    String date = entry.getDate();

			
				        ec.evaluateOpfinderLexicon(polarLex); // evaluate Lexicon Polarity

				        ec.evaluateAFINNLexicon(afinnLex);
				        ec.evaluateSWN3(swn3);
				        ec.evaluateNRC(nrc);
				        
				        
				        ec.evaluateSentiStrength(sentiStrength);
				        
				        // Add the entry to the EntrySet
				        entSet.add(entry);
				        
				        
				        
				    }
				    
				    Sent140Evaluator s140=new Sent140Evaluator(entSet);
				    s140.evaluateSentimentApiEntrySet();
				    
				    for(Entry ent:entSet){
				    	System.out.println(ent.toString());
					    	
				    }
				    
				    
				    
				    
				    
				    
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            
        
	}
	
	static public void main(String args[]){
		CollectionHandler ch=new STSCollectionHandler("datasets/test.csv","lla");
		ch.process();
		
	}

}
