package nc.itf.lxt.pub.sqltool;

public class SQLField {
	private String key;
	private String table;
	private String field;
	private boolean agg;
	
	public SQLField(String _key, String _table, String _field){
		key = _key;
		table = _table;
		field = _field;
		agg = false;
	}
	
	public boolean isNeedTable() {
		if (table == null) 
			return false;
		
		return field.indexOf(table) == -1;
	}

	public boolean isAgg() {
		return agg;
	}

	public String getKey() {
		return key;
	}

	public String getTable() {
		return table;
	}

	public String getField() {
		return field;
	}
}
