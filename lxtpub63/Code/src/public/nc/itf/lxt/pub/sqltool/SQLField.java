package nc.itf.lxt.pub.sqltool;

public class SQLField {
	private String key;
	private String table;
	private String field;
	
	public SQLField(String _key, String _table, String _field){
		key = _key;
		table = _table;
		field = _field;
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
