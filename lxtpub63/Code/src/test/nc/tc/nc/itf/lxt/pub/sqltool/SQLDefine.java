package nc.tc.nc.itf.lxt.pub.sqltool;

import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.ISQLDefine;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.ORDER;
import nc.itf.lxt.pub.sqltool.SQLField;
import nc.itf.lxt.pub.sqltool.SQLJoinClause;
import nc.itf.lxt.pub.sqltool.SQLOrderbyClause;
import nc.itf.lxt.pub.sqltool.SQLTable;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;

public class SQLDefine implements ISQLDefine {

	@Override
	public String getMianTable() {
		return "cmp_recbill";
	}

	@Override
	public SQLTable[] getSQLTables() {
		return new SQLTable[] {
				new SQLTable("bd_bankdoc_r",	"bd_bankdoc"),
				new SQLTable("bd_bankdoc_p",	"bd_bankdoc"),
				new SQLTable("bd_bankaccsub_r",	"bd_bankaccsub"),
				new SQLTable("bd_bankaccsub_p",	"bd_bankaccsub"),
				new SQLTable("bd_bankaccbas_r",	"bd_bankaccbas"),
				new SQLTable("bd_bankaccbas_p",	"bd_bankaccbas"),
		};
	}

	@Override
	public SQLField[] getSQLFields() {
		return new SQLField[] {
				new SQLField("pk_group",	"cmp_recbill",			"pk_group"),
				new SQLField("pk_org",		"cmp_recbill",			"pk_org"),
				new SQLField("vbusitype",	null,					"$BUSITYPE$"),
				new SQLField("vbillcode",	"cmp_recbill",			"bill_no"),
				new SQLField("ddate",		"cmp_recbill",			"paydate"),
				new SQLField("vbank",		"bd_bankdoc_p",			"name"),
				new SQLField("vbkaccount",	"bd_bankaccsub_p",		"accnum"),
				new SQLField("pk_opporg",	"bd_customer",			"pk_financeorg"),
				new SQLField("voppbank",	"bd_bankdoc_r",			"name"),
				new SQLField("voppbkacc",	"bd_bankaccsub_r",		"accnum"),
				new SQLField("udef1",		null,					"$TABLENAME$"),
				new SQLField("udef2",		null,					"$FIELDNAME$"),
				new SQLField("udef3",		null,					"$PKFIELD$"),
				new SQLField("nmny",		"cmp_recbilldetail",	"rec_primal"),
				new SQLField("pk_busibill",	"cmp_recbilldetail",	"pk_recbill_detail"),
				new SQLField("paystatus",	"cmp_recbill",			"paystatus"),
				new SQLField("def20",		"cmp_recbilldetail",	"def20"),
				new SQLField("pk_fatherorg","org_orgs",				"pk_fatherorg")
		};
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		return new SQLJoinClause[]{
			new SQLJoinClause("cmp_recbilldetail",	"pk_recbill",	"cmp_recbill",		"pk_recbill"),
			new SQLJoinClause("bd_bankaccsub_r",	"pk_bankaccsub","cmp_recbilldetail","pk_account"),
			new SQLJoinClause("bd_bankaccsub_p",	"pk_bankaccsub","cmp_recbilldetail","pk_oppaccount"),
			new SQLJoinClause("bd_bankaccbas_r",	"pk_bankaccbas","bd_bankaccsub_r",	"pk_bankaccbas"),
			new SQLJoinClause("bd_bankaccbas_p",	"pk_bankaccbas","bd_bankaccsub_p",	"pk_bankaccbas"),
			new SQLJoinClause("bd_bankdoc_r",		"pk_bankdoc",	"bd_bankaccbas_r",	"pk_bankdoc"),
			new SQLJoinClause("bd_bankdoc_p",		"pk_bankdoc",	"bd_bankaccbas_p",	"pk_bankdoc"),
			new SQLJoinClause("bd_customer",		"pk_customer",	"cmp_recbilldetail","pk_customer"),
			new SQLJoinClause("org_orgs",			"pk_org",		"bd_customer",		"pk_financeorg")
		};
	}

	@Override
	public SQLOrderbyClause[] getOrderbys() {
		return new SQLOrderbyClause[] {
			new SQLOrderbyClause("ddate", ORDER.ASC)
		};
	}

	@Override
	public SQLWhereClause[] getFixWheres() {
		return new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "paystatus", OPERATOR.EQ, "2"),
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "pk_fatherorg", OPERATOR.EQ, "pk_org"),
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "def20", OPERATOR.NEQ, "'Y'")
		};
	}
}
