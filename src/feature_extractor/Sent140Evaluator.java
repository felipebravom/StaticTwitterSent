package feature_extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Evalutes the polarity of an EntrySet using the Sentiment140 API

public class Sent140Evaluator {

	private List<Entry> entrySet;

	public Sent140Evaluator(List<Entry> entSet){
		this.entrySet=entSet;
	}


	public void evaluateSentimentApiEntrySet() {

		int maxSize = 6000; // Max number of elements for API call
		int numEntries = this.entrySet.size();
		List<List<Entry>> entryListList = new ArrayList<List<Entry>>(); // a list having each partition

		// We partionate the entries in different lists
		int i = 0;
		while (i + maxSize < numEntries) {
			entryListList.add(this.entrySet.subList(i, i + maxSize));
			i += maxSize;
		}
		if (i < numEntries) {
			entryListList.add(this.entrySet.subList(i, numEntries));
		}

		for (List<Entry> entryList : entryListList) {

			try {
				JSONParser parser = new JSONParser();
				JSONObject prim = new JSONObject();
				JSONArray list = new JSONArray();

				for (Entry entry : entryList) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("text", entry.getContent());
					
					// if the Entry has a topic as metadata it is used as a query
					if(entry.getMetaData().containsKey("topic"))					
						jsonObj.put("query", entry.getMetaData().get("topic"));
					
					list.add(jsonObj);

				}


				prim.put("data", list);

				String requestUrl = "http://www.sentiment140.com/api/bulkClassifyJson?appid=" + URLEncoder.encode("felipebravom@gmail.com", "UTF-8");

				URL url = new URL(requestUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				OutputStream os = conn.getOutputStream();
				os.write(prim.toJSONString().getBytes());
				os.flush();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				while ((output = br.readLine()) != null) {
					// System.out.println(output);

					//Object results = parser.parse(output);
					JSONObject res = (JSONObject) parser.parse(output);

					JSONArray list2 = (JSONArray) res.get("data");

					ListIterator it = list2.listIterator();                    

					ListIterator entIt = entryList.listIterator();

					while (it.hasNext() && entIt.hasNext()) {

						JSONObject tmp = (JSONObject) it.next();
						Entry entry = (Entry) entIt.next();
						Long textPolarity = (Long) tmp.get("polarity");
						String polarity;
						if (textPolarity == 0) {
							polarity = "negative";
						} else if (textPolarity == 2) {
							polarity = "neutral";
						} else {
							polarity = "positive";
						}
						entry.getFeatures().put("s140", polarity);
						System.out.println("Procese");
						StaticOperations.printTimeStamp();

					}

				}

				os.close();
				br.close();
				conn.disconnect();
				Thread.sleep(60 * 1);


			} catch (InterruptedException ex) {
				Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
				continue;
			} catch (ParseException ex) {
				Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
				continue;
			} catch (IOException ex) {
				Logger.getLogger(EntrySetController.class.getName()).log(Level.SEVERE, null, ex);
				continue;
			}


		}
	}

}

	
	


