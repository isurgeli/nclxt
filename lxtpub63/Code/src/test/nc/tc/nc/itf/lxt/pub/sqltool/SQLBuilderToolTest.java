package nc.tc.nc.itf.lxt.pub.sqltool;

import java.util.Hashtable;

import nc.itf.lxt.pub.sqltool.SQLBuilderTool;
import nc.vo.pub.BusinessException;
import junit.framework.TestCase;

public class SQLBuilderToolTest extends TestCase {
	public SQLBuilderToolTest(String name) {
		super(name);
	}
	
	public void testST() throws BusinessException{
		SQLBuilderTool tool = new SQLBuilderTool(new SQLDefine());
		Hashtable<String, Object> paras  = new Hashtable<String, Object>();
		paras.put("BUSITYPE", "'上收下级单位款'");
		paras.put("TABLENAME", "'cmp_recbilldetail'");
		paras.put("FIELDNAME", "'def20'");
		paras.put("PKFIELD", "'pk_recbill_detail'");
		paras.put("PK_ORG", "'0001A510000000000L83'");
		String ret = tool.buildSQL(new String[] {"pk_group","pk_org","vbusitype","vbillcode","ddate","vbank",
				"vbkaccount","pk_opporg","voppbank","voppbkacc","udef1","udef2","udef3","nmny","pk_busibill"}, 
				null, paras);
		
		System.out.println(ret);
	}
}
	