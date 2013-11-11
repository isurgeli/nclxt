package nc.itf.lxt.pub.sqltool;

public class SQLJoinClause {
	private String joinTable;
	private String joinField;
	private String mainTable;
	private String mainField;
	
	public SQLJoinClause(String _joinTable, String _joinField, String _mainTable, String _mainField){
		joinTable = _joinTable;
		joinField = _joinField;
		mainTable = _mainTable;
		mainField = _mainField;
	}

	public String getJoinTable() {
		return joinTable;
	}

	public String getJoinField() {
		return joinField;
	}

	public String getMainTable() {
		return mainTable;
	}

	public String getMainField() {
		return mainField;
	}
}
