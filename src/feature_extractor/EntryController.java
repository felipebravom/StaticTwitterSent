/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feature_extractor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.wlv.sentistrength.SentiStrength;

/**
 *
 * @author felipe Controls an Entry, extract features
 */
public class EntryController {

	private Entry entry;
	private String data;
	private String format;
	private String clean_message;
	private String[] words;

	public EntryController(String d) {
		this.data = d;
	}

	// crea una entrada segun el formato del dato
	public void createEntry() {
		try {
			Class c = Class.forName("feature_extractor." + format + "EntryFactory");
			Constructor EntryFactoryConstructor = c.getConstructor(String.class); // Creamos la instancia
			EntryFactory ef = (EntryFactory) EntryFactoryConstructor.newInstance(this.data); // Creamos la instancia

			this.entry = ef.createEntry();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	
	// Clean the message and tokenize words
	public void processWords() {

		this.clean_message = StaticOperations.clean(this.entry.getContent());
		this.words = StaticOperations.wordTokenizer(this.clean_message);
	}

	// Calculate OpinionFinder Features
	
	public void evaluateOpfinderLexicon(LexiconEvaluator le) {

		int negativeness = 0;
		int positiveness = 0;

		for (String w : this.words) {

			String pol = le.retrieveValue(w);
			if (pol.equals("positive")) {
				positiveness++;
			} else if (pol.equals("negative")) {
				negativeness++;
			}
		}


		entry.getFeatures().put("OPW", positiveness);
		entry.getFeatures().put("ONW", negativeness);


	}
	
	public void evaluateBingLiuLexicon(LexiconEvaluator le) {

		int negativeness = 0;
		int positiveness = 0;

		for (String w : this.words) {

			String pol = le.retrieveValue(w);
			if (pol.equals("positive")) {
				positiveness++;
			} else if (pol.equals("negative")) {
				negativeness++;
			}
		}


		entry.getFeatures().put("BLPW", positiveness);
		entry.getFeatures().put("BLNW", negativeness);


	}
	
	public void evaluateS140Lexicon(LexiconEvaluator le) {

		int pos_words = 0;
		int neg_words = 0;

		// accumulated postiveness and negativeness
		double ac_neg = 0;
		double ac_pos = 0;


		for (String w : this.words) {

			String pol = le.retrieveValue(w);
			if (!pol.equals("not_found")) {
				double value = Double.parseDouble(pol);
				if (value > 0) {
					pos_words++;
					ac_pos += value;
				} else {
					neg_words++;
					ac_neg += value;
				}

			}


		}


		//entry.getFeatures().put("afinn_positive_words", pos_words);
		// entry.getFeatures().put("afinn_negative_words", neg_words);

		entry.getFeatures().put("S140LEXPOS", ac_pos);
		entry.getFeatures().put("S140LEXNEG", ac_neg);


	}
	
	
	public void evaluateNRCHashtagLexicon(LexiconEvaluator le) {

		int pos_words = 0;
		int neg_words = 0;

		// accumulated postiveness and negativeness
		double ac_neg = 0;
		double ac_pos = 0;


		for (String w : this.words) {

			String pol = le.retrieveValue(w);
			if (!pol.equals("not_found")) {
				
				double value = Double.parseDouble(pol);
				if (value > 0) {
					pos_words++;
					ac_pos += value;
				} else {
					neg_words++;
					ac_neg += value;
				}

			}


		}


		//entry.getFeatures().put("afinn_positive_words", pos_words);
		// entry.getFeatures().put("afinn_negative_words", neg_words);

		entry.getFeatures().put("NRCHASHPOS", ac_pos);
		entry.getFeatures().put("NRCHASHNEG", ac_neg);


	}
	
	
	

	public void evaluateAFINNLexicon(LexiconEvaluator le) {

		int pos_words = 0;
		int neg_words = 0;

		int negativeness = 0;
		int positiveness = 0;


		for (String w : this.words) {

			String pol = le.retrieveValue(w);
			if (!pol.equals("not_found")) {
				int value = Integer.parseInt(pol);
				if (value > 0) {
					pos_words++;
					positiveness += value;
				} else {
					neg_words++;
					negativeness += value;
				}

			}


		}


		//entry.getFeatures().put("afinn_positive_words", pos_words);
		// entry.getFeatures().put("afinn_negative_words", neg_words);

		entry.getFeatures().put("APO", positiveness);
		entry.getFeatures().put("ANE", negativeness);


	}

	// evaluates the sentiment score using SentiWordnet
	public void evaluateSWN3(SWN3 swn3) {
		int swn3_positive_words = 0;
		int swn3_neutral_words = 0;
		int swn3_negative_words = 0;

		double swn3_positiveness = 0.0d;
		double swn3_negativeness = 0.0d;


		for (String word : this.words) {
			if (swn3.getDict().containsKey(word)) {
				double score = swn3.getDict().get(word).doubleValue();
				if (score == 0) {
					swn3_neutral_words++;
				} else if (score < 0) {
					swn3_negative_words++;
					swn3_negativeness += score;
				} else {
					swn3_positive_words++;
					swn3_positiveness += score;
				}

			}
		}
		
		
//		entry.getFeatures().put("swn3_positive_words", swn3_positive_words);
//		entry.getFeatures().put("swn3_neutral_words", swn3_neutral_words);
//		entry.getFeatures().put("swn3_negative_words", swn3_negative_words);
//		
		
		entry.getFeatures().put("SWP", swn3_positiveness);
		entry.getFeatures().put("SWN", swn3_negativeness);




	}

	// Calculate emotion-oriented features using NRC
	public void evaluateNRC(NRCEvaluator nrc){

		int anger = 0;
		int anticipation = 0;
		int disgust = 0;
		int fear = 0;
		int joy = 0;
		int negative = 0;
		int positive = 0;
		int sadness = 0;
		int surprise = 0;
		int trust = 0;

		for (String word : this.words) {
			// I retrieve the EmotionMap if the word match the lexicon
			if(nrc.getDict().containsKey(word)){        		
				Map<String,Integer> emotions=nrc.getDict().get(word);
				anger += emotions.get("anger");
				anticipation += emotions.get("anticipation");
				disgust += emotions.get("disgust");
				fear += emotions.get("fear");
				joy += emotions.get("joy");
				negative += emotions.get("negative");
				positive += emotions.get("positive");
				sadness += emotions.get("sadness");
				surprise += emotions.get("surprise");
				trust += emotions.get("trust");        		
			}
		}

		entry.getFeatures().put("NCRanger", anger);
		entry.getFeatures().put("NCRanticipation", anticipation);
		entry.getFeatures().put("NCRdisgust", disgust);
		entry.getFeatures().put("NCRfear", fear);
		entry.getFeatures().put("NCRjoy", joy);
		entry.getFeatures().put("NCRnegative", negative);
		entry.getFeatures().put("NCRpositive", positive);
		entry.getFeatures().put("NCRsadness", sadness);
		entry.getFeatures().put("NCRsurprise", surprise);
		entry.getFeatures().put("NCRtrust", trust);        


	}




	public void evaluateSentiStrength(SentiStrength sentiStrength) {

		String sentence = "";
		for (int i = 0; i < this.words.length; i++) {
			sentence += words[i];
			if (i < this.words.length - 1) {
				sentence += "+";
			}
		}

		String result = sentiStrength.computeSentimentScores(sentence);
		String[] values = result.split(" ");


		int pos = Integer.parseInt(values[0]);
		int neg = Integer.parseInt(values[1]);
		int neu = Integer.parseInt(values[2]);
		
		String sspol;
		
		if(neu>0){
			sspol="positive";
		}
		else if(neu==0){
			sspol="neutral";
		}
		else{
			sspol="negative";
		}
		

		entry.getFeatures().put("SSP", pos);
		entry.getFeatures().put("SSN", neg);
		entry.getFeatures().put("SSPOL", sspol);

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
