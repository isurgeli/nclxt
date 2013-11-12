package nc.itf.lxt.pub.sqltool;

public enum DELIMITER {

	START('$'),
	END('$');
	
	private char value;
	
    public char getValue(){
    	return this.value;
    }
    
    private DELIMITER(char context){
    	this.value = context;
    }
    
    public static String getParaExp(String paraName) {
    	return DELIMITER.START.getValue()+paraName+DELIMITER.END.getValue();
    }
    
    public static String getStringParaValue(String paraName) {
    	return "'"+paraName+"'";
    }
}
