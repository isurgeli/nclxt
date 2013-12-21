package nc.itf.lxt.pub.sqltool;

import java.util.Hashtable;

import nc.vo.pub.BusinessException;

public class SQLWhereClause {
	private OPERATOR logicOP;
	private int bracket;
	
	public void addLeftBracket() {
		bracket--;
	}
	
	public void addRightBracket() {
		bracket++;
	}

	private String leftKey;
	private OPERATOR compareOP;
	public void setLogicOP(OPERATOR logicOP) {
		this.logicOP = logicOP;
	}

	public void setCompareOP(OPERATOR compareOP) {
		this.compareOP = compareOP;
	}

	private String rightKey;
	
	private Hashtable<String, SQLField> sqlFields;
	
	public SQLWhereClause( OPERATOR logicOP, BRACKET bracket, String leftKey, OPERATOR compareOP, String rightKey){
		this.logicOP = logicOP;
		this.leftKey = leftKey;
		this.compareOP = compareOP;
		this.rightKey = rightKey;
		
		if (bracket.equals(BRACKET.NONE))
			this.bracket = 0;
		else if (bracket.equals(BRACKET.LEFT))
			this.bracket = -1;
		else if (bracket.equals(BRACKET.LEFT2))
			this.bracket = -2;
		else if (bracket.equals(BRACKET.RIGHT))
			this.bracket = 1;
		else if (bracket.equals(BRACKET.RIGHT2))
			this.bracket = 2;
	}

	public String getLogicOP() {
		return logicOP.getValue();
	}

	public String getLeftBracket() {
		if (bracket < 0)
			return "((((((((((".substring(0,Math.abs(bracket));
		else
			return "";
	}

	public SQLField getLeftField() throws BusinessException {
		if (!sqlFields.containsKey(leftKey))
			throw new BusinessException("["+leftKey+"]定义不存在。");
		
		return sqlFields.get(leftKey);
	}
	
	public String getLeftKey() throws BusinessException {
		return leftKey;
	}
	
	public boolean isLeftSqlField() throws BusinessException {
		return sqlFields.containsKey(leftKey);
	}
	
	public String getCompareOP() {
		return compareOP.getValue();
	}
	
	public SQLField getRightField() throws BusinessException {
		if (!sqlFields.containsKey(rightKey))
			throw new BusinessException("["+rightKey+"]定义不存在。");
		
		return sqlFields.get(rightKey);
	}
	
	public String getRightKey() throws BusinessException {
		return rightKey;
	}
	
	public boolean isRightSqlField() throws BusinessException {
		return sqlFields.containsKey(rightKey);
	}

	public String getRigthBracket() {
		if (bracket > 0)
			return "))))))))))".substring(0,Math.abs(bracket));
		else
			return "";
	}

	public void setSqlFields(Hashtable<String, SQLField> sqlFields) {
		this.sqlFields = sqlFields;
	}
}
