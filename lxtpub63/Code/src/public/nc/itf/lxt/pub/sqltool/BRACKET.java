package nc.itf.lxt.pub.sqltool;

public enum BRACKET {
	NONE(""),
	LEFT("("),
	LEFT2("(("),
	RIGHT(")"),
	RIGHT2("))");
	
	private String value;
	
    public String getValue(){
    	return this.value;
    }
    
    private BRACKET(String context){
    	this.value = context;
    }
}
