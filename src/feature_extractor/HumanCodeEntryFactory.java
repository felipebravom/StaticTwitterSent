package feature_extractor;

public class HumanCodeEntryFactory extends EntryFactory {

	public HumanCodeEntryFactory(String data) {
		super(data);

	}

	@Override
	public Entry createEntry() {
		Entry e = new Entry();		
		String parts[] = this.entryData.split("\t");		
		if(parts.length==3){
			e.setContent(parts[2]);
			e.getFeatures().put("avgPos", parts[0]);
			e.getFeatures().put("avgNeg", parts[1]);
			e.setValid(true);

		}
		else{
			System.out.println(parts.length+" "+ this.entryData);		
		}
		
		return e;




	}


	static public void main(String args[]){
		String line="3	2	?RT @justinbiebcr: The bigger the better....if you know what I mean ;)";
		EntryFactory ef=new HumanCodeEntryFactory(line);
		Entry e=ef.createEntry();
		System.out.println(e.toString());


	}


}
