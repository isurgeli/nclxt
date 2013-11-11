package nc.itf.lxt.pub.sqltool;

public enum ORDER {
	ASC("asc"),
	DESC("desc");
	
	private String value;
	
    public String getValue(){
    	return this.value;
    }
    
    private ORDER(String context){
    	this.value = context;
    }
}
