package feature_extractor;

import java.util.HashMap;
import java.util.Map;

// Creates a MetaLex from all other Lexicons

public class MetaLexCreator {

	static public void main(String args[]){




		LexiconEvaluator opfinder = new LexiconEvaluator(
				"extra/polarity-lexicon.txt");
		opfinder.processDict();
		LexiconEvaluator afinnLex = new LexiconEvaluator("extra/AFINN-111.txt");
		afinnLex.processDict();

		LexiconEvaluator s140Lex = new LexiconEvaluator(
				"extra/Sentiment140-Lexicon-v0.1/unigrams-pmilexicon.txt");
		s140Lex.processDict();

		LexiconEvaluator nrcHashtag = new LexiconEvaluator(
				"extra/NRC-Hashtag-Sentiment-Lexicon-v0.1/unigrams-pmilexicon.txt");
		nrcHashtag.processDict();

		LexiconEvaluator liuLex = new LexiconEvaluator("extra/BingLiu.csv");
		liuLex.processDict();



		SWN3 swn3 = new SWN3("extra/SentiWordNet_3.0.0.txt");

		NRCEvaluator nrcEmo = new NRCEvaluator(
				"extra/NRC-emotion-lexicon-wordlevel-v0.92.txt");
		nrcEmo.processDict();


		// Maps for each word within all Lexicons
		Map<String,Map<String,String>> wordMap=new HashMap<String,Map<String,String>>();

		Map<String,String> opDict=opfinder.getDict();

		for(String word:opDict.keySet()){

			// if the word doesn't appear in wordMap we add the entry
			if(!wordMap.containsKey(word)){
				Map<String,String> valueMap=new HashMap<String,String>();
				valueMap.put("OpFinder", opDict.get(word));
				wordMap.put(word,valueMap);
			}
			else{
				Map<String,String> valueMap=wordMap.get(word);
				valueMap.put("OpFinder", opDict.get(word));			

			}

		}


		Map<String,String> afinnDict=afinnLex.getDict();

		for(String word:afinnDict.keySet()){

			// if the word doesn't appear in wordMap we add the entry
			if(!wordMap.containsKey(word)){
				Map<String,String> valueMap=new HashMap<String,String>();
				valueMap.put("Afinn", afinnDict.get(word));
				wordMap.put(word,valueMap);
			}
			else{
				Map<String,String> valueMap=wordMap.get(word);
				valueMap.put("Afinn", afinnDict.get(word));			

			}

		}


		Map<String,String> s140Dict=s140Lex.getDict();

		for(String word:s140Dict.keySet()){

			// if the word doesn't appear in wordMap we add the entry
			if(!wordMap.containsKey(word)){
				Map<String,String> valueMap=new HashMap<String,String>();
				valueMap.put("s140Lex", s140Dict.get(word));
				wordMap.put(word,valueMap);
			}
			else{
				Map<String,String> valueMap=wordMap.get(word);
				valueMap.put("s140Lex", s140Dict.get(word));			

			}

		}


		Map<String,String> nrcHashDict=nrcHashtag.getDict();

		for(String word:nrcHashDict.keySet()){

			// if the word doesn't appear in wordMap we add the entry
			if(!wordMap.containsKey(word)){
				Map<String,String> valueMap=new HashMap<String,String>();
				valueMap.put("nrcHash", nrcHashDict.get(word));
				wordMap.put(word,valueMap);
			}
			else{
				Map<String,String> valueMap=wordMap.get(word);
				valueMap.put("nrcHash", nrcHashDict.get(word));			

			}

		}

		Map<String,String> liuDict=liuLex.getDict();

		for(String word:liuDict.keySet()){

			// if the word doesn't appear in wordMap we add the entry
			if(!wordMap.containsKey(word)){
				Map<String,String> valueMap=new HashMap<String,String>();
				valueMap.put("liuLex", liuDict.get(word));
				wordMap.put(word,valueMap);
			}
			else{
				Map<String,String> valueMap=wordMap.get(word);
				valueMap.put("liuLex", liuDict.get(word));		

			}

		}


		Map<String,Double> swn3Dict=swn3.getDict();

		for(String word:swn3Dict.keySet()){

			// if the word doesn't appear in wordMap we add the entry
			if(!wordMap.containsKey(word)){
				Map<String,String> valueMap=new HashMap<String,String>();
				valueMap.put("swn3", swn3Dict.get(word).toString());
				wordMap.put(word,valueMap);
			}
			else{
				Map<String,String> valueMap=wordMap.get(word);
				valueMap.put("swn3", swn3Dict.get(word).toString());	

			}

		}


		Map<String, Map<String,Integer>> nrcEmoDict=nrcEmo.getDict();

		for(String word:nrcEmoDict.keySet()){

			// if the word doesn't appear in wordMap we add the entry
			if(!wordMap.containsKey(word)){
				Map<String,String> valueMap=new HashMap<String,String>();

				Map<String,Integer> emoValues=nrcEmoDict.get(word);
				for(String emotion:emoValues.keySet()){
					valueMap.put("NRC"+emotion, emoValues.get(emotion).toString());
					wordMap.put(word,valueMap);				
				}


			}
			else{
				Map<String,String> valueMap=wordMap.get(word);
				Map<String,Integer> emoValues=nrcEmoDict.get(word);
				for(String emotion:emoValues.keySet()){
					valueMap.put("NRC"+emotion, emoValues.get(emotion).toString());			
				}
				
			}

		}







		for(String word:wordMap.keySet()){
			System.out.print(word+" ");
			for(String field:wordMap.get(word).keySet()){
				System.out.print(field+" "+wordMap.get(word).get(field)+" ");
			}
			System.out.println();
		}

		System.out.println("Hola Mundo");




	}

}
