package feature_extractor;

public class STSEntryFactory extends EntryFactory {

	public STSEntryFactory(String data) {
		super(data);
		
	}

	@Override
	public Entry createEntry() {
		Entry e = new Entry();		
		String parts[] = this.entryData.split("\",\"");
		
		// The line is parsed only if the structure is respected
		if(parts.length==6){
			
			// Parse the label
			int sent=Integer.parseInt(parts[0].replace("\"",""));
			String label;
			switch(sent){
			case 0: label="negative";
			break;
			case 2: label="neutral";
			break;
			case 4: label="positive";
			break;
			default: label="incorrect";
			break;

			}

			// Parse the remainder fields
			e.getFeatures().put("label", label);
			
			e.getMetaData().put("id",parts[1]);
			e.getMetaData().put("date",parts[2]);
			e.getMetaData().put("topic",parts[3]);
			e.getMetaData().put("user", parts[4]);

			String content=parts[5].replaceAll("\"", "");
			e.setContent(content.replaceAll("\t", ""));
			
			e.setValid(true);
		}
		
		return e;
		
			
				
		
	}
	
	
	static public void main(String args[]){
		String line="\"4\",\"3\",\"Mon May 11 03:17:40 UTC 2009\",\"kindle2\",\"tpryan\",\"@stellargirl I loooooooovvvvvveee my Kindle2. Not that the DX is cool, but the 2 is fantastic in its own right.\"";
		EntryFactory ef=new STSEntryFactory(line);
		Entry e=ef.createEntry();
		System.out.println(e.toString());
		
		
	}
	

}
