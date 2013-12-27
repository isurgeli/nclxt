package nc.itf.lxt.pub.sqltool;

public class SQLJoinClause {
	private String joinTable;
	private String joinField;
	private String mainTable;
	private String mainField;
	
	private OPERATOR jointype;
	
	public SQLJoinClause(String _joinTable, String _joinField, String _mainTable, String _mainField){
		joinTable = _joinTable;
		joinField = _joinField;
		mainTable = _mainTable;
		mainField = _mainField;
		
		jointype = OPERATOR.INNER;
	}
	
	public SQLJoinClause(String _joinTable, String _joinField, String _mainTable, String _mainField, OPERATOR _join){
		joinTable = _joinTable;
		joinField = _joinField;
		mainTable = _mainTable;
		mainField = _mainField;
		
		jointype = _join;
	}
	
	public boolean isLeft() {
		return jointype.equals(OPERATOR.LEFT);
	}
	
	public boolean isRigth() {
		return jointype.equals(OPERATOR.RIGHT);
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
