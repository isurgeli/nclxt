package nc.itf.lxt.pub.sqltool;

import java.util.Hashtable;

import nc.vo.pub.BusinessException;

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
		return bracket.equals(BRACKET.LEFT);
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

	public boolean isRigthBracket() {
		return bracket.equals(BRACKET.RIGHT);
	}

	public void setSqlFields(Hashtable<String, SQLField> sqlFields) {
		this.sqlFields = sqlFields;
	}
}
