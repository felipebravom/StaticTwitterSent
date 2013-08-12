package feature_extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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


        SentiStrength sentiStrength = new SentiStrength();
        String sentiParams[] = {"sentidata", "extra/SentiStrength/", "trinary"};
        sentiStrength.initialise(sentiParams);
        
        File inpFolder = new File(this.inputFile);
        
        try {
			BufferedReader bf = new BufferedReader(new FileReader(this.inputFile));
			
			String line;
            try {
				while ((line = bf.readLine()) != null) {

				    EntryController ec = new EntryController(line); // create the EntryController
				    ec.setFormat("STS"); // seteo al formato elections
				    ec.createEntry();  // create the Entry
				    if (ec.getEntry().isValid()) {

				        ec.processWords();
				        Entry entry = ec.getEntry();
//                    String date = entry.getDate();

				        String topic = inpFolder.getName();
				        entry.getMetaData().put("topic", topic);
				        ec.evaluatePolarityLexicon(polarLex); // evaluate Lexicon Polarity

				        ec.evaluateAFINNLexicon(afinnLex);
				        ec.evaluateSWN3(swn3);
				        ec.evaluateSentiStrength(sentiStrength);
				        
				        System.out.println(ec.getEntry().toString());
				        
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
		CollectionHandler ch=new STSCollectionHandler("datasets/STS.csv","lla");
		ch.process();
		
	}

}
