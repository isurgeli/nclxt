package nc.itf.lxt.pub.sqltool;

import java.util.Hashtable;

import nc.vo.pub.BusinessException;

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

	public SQLField getSqlField() throws BusinessException {
		if (!sqlFields.containsKey(key))
			throw new BusinessException("["+key+"]定义不存在。");
		
		return sqlFields.get(key);
	}
	
	public void setSqlFields(Hashtable<String, SQLField> sqlFields) {
		this.sqlFields = sqlFields;
	}
}
