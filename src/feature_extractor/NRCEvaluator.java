package feature_extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class NRCEvaluator {
	protected String path;
	protected Map<String, Map<String,Integer>> dict;


	public NRCEvaluator(String path) {
		this.path=path;
		this.dict=new HashMap<String, Map<String,Integer>>();

	}

	public Map<String, Map<String,Integer>> getDict(){
		return this.dict;
	}

	public Map<String,Integer> getWord(String word){
		if(this.dict.containsKey(word))
			return dict.get(word);
		else
			return null;

	}

	public void processDict(){
		Scanner sc;
		try {
			sc = new Scanner(new File(this.path));
			sc.useDelimiter("\n");
			for (String line = sc.next(); sc.hasNext(); line = sc.next()) {
				String content[] = line.split("\t");
				String word=content[0];
				String emotion=content[1];
				int value=Integer.parseInt(content[2]);

				// I check whether the word has been inserted into the dict
				if(this.dict.containsKey(word)){
					Map<String,Integer> emotionMap=this.dict.get(content[0]);
					emotionMap.put(emotion, value);		             
				}
				else{
					Map<String,Integer> emotionMap=new HashMap<String,Integer>();
					emotionMap.put(emotion,value);
					this.dict.put(word, emotionMap);
				}



			}

			sc.close();	



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static public void main(String args[]){
		NRCEvaluator eval=new NRCEvaluator("extra/NRC-emotion-lexicon-wordlevel-v0.92.txt");
		eval.processDict();

		Map<String,Integer> pal=eval.getWord("love");

		if(pal!=null){
			for(String emo:pal.keySet()){
				System.out.println(emo+" "+pal.get(emo));
			}
		}



	}

}
