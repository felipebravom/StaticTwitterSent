package feature_extractor;

public class SemEvalEntryFactory extends EntryFactory {

	public SemEvalEntryFactory(String data) {
		super(data);
		
	}

	@Override
	public Entry createEntry() {
		Entry e = new Entry();		
		String parts[] = this.entryData.replaceAll("\"", "").split("\t");
		

		
		if(parts.length==4){
			
			e.setContent(parts[3]);
			
			String label=parts[2];
			if(label.equals("objective-OR-neutral") || label.equals("objective")){
				label="neutral";
			}
	
			e.getFeatures().put("label", label);
			e.setValid(true);
		
			
			
		}
		else{
			System.out.println(parts.length+" "+ this.entryData);			
		}
		
		
		return e;
		
			
				
		
	}
	
	
	static public void main(String args[]){
		String line="264183816548130816	15140428	\"positive\"	Gas by my house hit $3.39!!!! I'm going to Chapel Hill on Sat. :)";
		EntryFactory ef=new SemEvalEntryFactory(line);
		Entry e=ef.createEntry();
		System.out.println(e.toString());
		
		
	}
	

}
