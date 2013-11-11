package nc.itf.lxt.pub.sqltool;

public interface ISQLDefine {
	String getMianTable();
	SQLTable[] getSQLTables();
	SQLField[] getSQLFields();
	SQLJoinClause[] getTableJoins();
	SQLOrderbyClause[] getOrderbys();
	SQLWhereClause[] getFixWheres();
}
