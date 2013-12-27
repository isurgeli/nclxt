package nc.itf.lxt.pub.sqltool;

public enum OPERATOR {
	EQ("="),
	NEQ("<>"),
	LTE("<="),
	GTE(">="),
	LT("<"),
	GT(">"),
	IN("in"),
	LIKE("like"),
	
	AND("and"),
	OR("or"),
	
	INNER("inner"),
	LEFT("left"),
	RIGHT("right");
	
	private String value;
	
    public String getValue(){
    	return this.value;
    }
    
    private OPERATOR(String context){
    	this.value = context;
    }
}
