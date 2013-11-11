package nc.itf.lxt.pub.sqltool;

import java.util.Hashtable;

public class SQLWhereClause {
	private OPERATOR logicOP;
	private BRACKET bracket;
	private String leftKey;
	private OPERATOR compareOP;
	private String rightKey;
	
	private Hashtable<String, SQLField> sqlFields;
	
	public SQLWhereClause( OPERATOR logicOP, BRACKET bracket, String leftKey, OPERATOR compareOP, String rightKey){
		this.logicOP = logicOP;
		this.bracket = bracket;
		this.leftKey = leftKey;
		this.compareOP = compareOP;
		this.rightKey = rightKey;
	}

	public String getLogicOP() {
		return logicOP.getValue();
	}

	public boolean isLeftBracket() {
		return bracket.getValue().equals("(");
	}

	public String getLeftTable() {
		return sqlFields.get(leftKey).getTable();
	}
	
	public String getLeftField() {
		return sqlFields.get(leftKey).getField();
	}

	public String getCompareOP() {
		return compareOP.getValue();
	}

	public String getRightTable() {
		if (sqlFields.containsKey(rightKey))
			return sqlFields.get(rightKey).getTable();
		else
			return null;
	}
	
	public String getRightField() {
		if (sqlFields.containsKey(rightKey))
			return sqlFields.get(rightKey).getField();
		else
			return rightKey;
	}

	public boolean isRigthBracket() {
		return bracket.getValue().equals(")");
	}

	public void setSqlFields(Hashtable<String, SQLField> sqlFields) {
		this.sqlFields = sqlFields;
	}
}
