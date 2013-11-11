package nc.itf.lxt.pub.sqltool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

public class SQLBuilderTool {
	private Hashtable<String, SQLField> sqlFields;
	private Hashtable<String, SQLJoinClause> sqlJoins;
	private Hashtable<String, SQLTable> sqlTables;
	private ArrayList<SQLOrderbyClause> sqlOrders;
	private ArrayList<SQLWhereClause> fixWheres;
	private String mainTable;
	
	public SQLBuilderTool(ISQLDefine sqlDef){
		sqlFields = new Hashtable<String, SQLField>();
		sqlJoins = new Hashtable<String, SQLJoinClause>();
		sqlTables = new Hashtable<String, SQLTable>();
		sqlOrders = new ArrayList<SQLOrderbyClause>();
		fixWheres = new ArrayList<SQLWhereClause>();
		
		mainTable = sqlDef.getMianTable();
		
		for(SQLField sqlField : sqlDef.getSQLFields())
			sqlFields.put(sqlField.getKey(), sqlField);
		
		for(SQLJoinClause sqlJoin : sqlDef.getTableJoins())
			sqlJoins.put(sqlJoin.getJoinTable(), sqlJoin);	
		
		for(SQLTable sqlTable : sqlDef.getSQLTables())
			sqlTables.put(sqlTable.getKey(), sqlTable);	
		
		for(SQLWhereClause sqlWhere : sqlDef.getFixWheres()) {
			sqlWhere.setSqlFields(sqlFields);
			fixWheres.add(sqlWhere);
		}
		
		for(SQLOrderbyClause sqlOrder : sqlDef.getOrderbys()) {
			sqlOrder.setSqlFields(sqlFields);
			sqlOrders.add(sqlOrder);
		}
		
	}
	
	public String buildSQL(String[] keys, SQLWhereClause[] flexWheres, Hashtable<String, Object> paras) {
		ArrayList<SQLField> fields = new ArrayList<SQLField>();
		ArrayList<SQLTable> tables = new ArrayList<SQLTable>();
		ArrayList<SQLJoinClause> joins = new ArrayList<SQLJoinClause>();
		ArrayList<SQLOrderbyClause> orders = new ArrayList<SQLOrderbyClause>();
		ArrayList<SQLWhereClause> wheres = new ArrayList<SQLWhereClause>();
		
		prepareTemplatePara(keys, flexWheres, fields, tables, joins, orders, wheres);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(SQLBuilderTool.class.getResourceAsStream("basesql.stg")));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
		}
		STGroup group = new STGroupString(sb.toString());
		ST baseSt = group.getInstanceOf("sql");
		baseSt.add("fields", fields);
		baseSt.add("tables", tables);
		baseSt.add("joins", joins);
		baseSt.add("wheres", wheres);
		baseSt.add("orderbys", orders);
		String sqlBase = baseSt.render();
		
		ST st = new ST(sqlBase, '$', '$');
		for (String para : paras.keySet())
			st.add(para, paras.get(para));
		
		return st.render();
	}

	private void prepareTemplatePara(String[] keys,
			SQLWhereClause[] flexWheres, ArrayList<SQLField> fields,
			ArrayList<SQLTable> tables, ArrayList<SQLJoinClause> joins, 
			ArrayList<SQLOrderbyClause> orders,	ArrayList<SQLWhereClause> wheres) {
		for(SQLWhereClause sqlWhere : wheres)
			sqlWhere.setSqlFields(sqlFields);
		
		Hashtable<String, String> joinTables = new Hashtable<String, String>();
		for (String key : keys) {
			fields.add(sqlFields.get(key));
			if (sqlFields.get(key).getTable() != null && !joinTables.containsKey(sqlFields.get(key).getTable()))
				joinTables.put(sqlFields.get(key).getTable(), sqlFields.get(key).getTable());
		}
		
		for (SQLWhereClause where : fixWheres) {
			wheres.add(where);
			if (!joinTables.containsKey(where.getLeftTable()))
				joinTables.put(where.getLeftTable(), where.getLeftTable());
			if (where.getRightTable() != null && !joinTables.containsKey(where.getRightTable()))
				joinTables.put(where.getRightTable(), where.getRightTable());
		}
		
		if (flexWheres != null) {
			for (SQLWhereClause where : flexWheres) {
				wheres.add(where);
				if (!joinTables.containsKey(where.getLeftTable()))
					joinTables.put(where.getLeftTable(), where.getLeftTable());
				if (!joinTables.containsKey(where.getRightTable()))
					joinTables.put(where.getRightTable(), where.getRightTable());
			}
		}
		
		for (SQLOrderbyClause order : sqlOrders) {
			orders.add(order);
			
			if (!joinTables.containsKey(order.getTable()))
				joinTables.put(order.getTable(), order.getTable());
		}
		
		addSortAllNeedTables(tables, joins, joinTables);
	}

	private void addSortAllNeedTables(ArrayList<SQLTable> tables,
			ArrayList<SQLJoinClause> joins, Hashtable<String, String> joinTables) {
		ArrayList<String> joinTableList = new ArrayList<String>();
		for (String joinTable : joinTables.keySet()) {
			joinTableList.add(joinTable);
		}
		for (String joinTable : joinTableList) {
			addParentTables(joinTables, joinTable);
		}
		
		for (String joinTable : joinTables.keySet()) {
			tables.add(getSQLTable(joinTable));
		}
		Collections.sort(tables, new SQLTable(null, null));
		for (SQLTable sqlTable : tables) {
			if (sqlTable.getKey() != null)
				joins.add(sqlJoins.get(sqlTable.getKey()));
			else
				joins.add(sqlJoins.get(sqlTable.getName()));
		}
	}
	
	private void addParentTables(Hashtable<String, String> joinTables, String joinTable) {
		if (!joinTables.containsKey(joinTable))
			joinTables.put(joinTable, joinTable);
		
		if (joinTable.equals(mainTable)) 
			return;
		
		String parent = sqlJoins.get(joinTable).getMainTable();
		addParentTables(joinTables, parent);
	}

	private SQLTable getSQLTable(String tableKey) {
		SQLTable ret = null;
		
		if (sqlTables.containsKey(tableKey))
			ret = sqlTables.get(tableKey);
		else
			ret = new SQLTable(null, tableKey);
		
		ret.setLevel(getTableLevel(tableKey));
		
		return ret;
	}

	private int getTableLevel(String tableKey) {
		if (tableKey.equals(mainTable)) 
			return 1;
		else 
			return getTableLevel(sqlJoins.get(tableKey).getMainTable()) + 1;
	}
}
