package morutask.models;

public interface ModelNote extends Model {
	
	public static final String PROP_NOTE = "note";
	
	public String getNote();
	
	public void setNote(String note);
	
}
