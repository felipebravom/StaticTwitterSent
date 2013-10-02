package feature_extractor;

public class SandersEntryFactory extends EntryFactory {

	public SandersEntryFactory(String data) {
		super(data);
		
	}

	@Override
	public Entry createEntry() {
		Entry e = new Entry();		
		String parts[] = this.entryData.split(",");		
		if(parts.length==6){
			if(!parts[4].equals("irrelevant")){
				e.setContent(parts[3]);
				e.getMetaData().put("topic", parts[5]);
				e.getFeatures().put("label", parts[4]);
				e.setValid(true);
			}
			
			
		}
		System.out.println(parts.length+" "+ this.entryData);
		
		return e;
		
			
				
		
	}
	
	
	static public void main(String args[]){
		String line="10	126418790706713000	2011-10-18 22:06:03	RT @cjwallace03: So apparently @apple put MB cap on your SMS with the new update. 25mb storage before it tells you your inbox is full. What is this 2001?	negative	apple";
		EntryFactory ef=new SandersEntryFactory(line);
		Entry e=ef.createEntry();
		System.out.println(e.toString());
		
		
	}
	

}
