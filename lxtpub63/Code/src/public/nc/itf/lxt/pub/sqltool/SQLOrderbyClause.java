package nc.itf.lxt.pub.sqltool;

import java.util.Hashtable;

public class SQLOrderbyClause {
	private ORDER order;
	private String key;
	private Hashtable<String, SQLField> sqlFields;
	
	public SQLOrderbyClause(String _key, ORDER _order){
		order = _order;
		key = _key;
	}

	public String getOrder() {
		return order.getValue();
	}

	public String getTable() {
		return sqlFields.get(key).getTable();
	}
	
	public String getField() {
		return sqlFields.get(key).getField();
	}
	
	public void setSqlFields(Hashtable<String, SQLField> sqlFields) {
		this.sqlFields = sqlFields;
	}
}
