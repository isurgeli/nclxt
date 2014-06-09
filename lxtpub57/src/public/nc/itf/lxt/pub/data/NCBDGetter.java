package nc.itf.lxt.pub.data;

import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.vo.pub.BusinessException;

public class NCBDGetter {
	public static String get1Record1Value(String sql, String[] paras, String errMsg) throws BusinessException {
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		
		SQLParameter sqlp = new SQLParameter();
		for(String para : paras)
			sqlp.addParam(para);
		
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> data = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), sqlp, 
				new VectorProcessor());
		if (data == null || data.size() == 0) 
			throw new BusinessException(errMsg);
		
		return data.get(0).get(0).toString();
	}
	
	public static Vector<String> get1RecordNValue(String sql, String[] paras, String errMsg) throws BusinessException {
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		
		SQLParameter sqlp = new SQLParameter();
		for(String para : paras)
			sqlp.addParam(para);
		
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> data = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), sqlp, 
				new VectorProcessor());
		if (data == null || data.size() == 0) 
			throw new BusinessException(errMsg);
		
		Vector<String> ret = new Vector<String>();
		for(Object o : data.get(0))
			ret.add(o.toString());
		
		return ret;
	}
	
	public static Vector<String> getDeptCodeAndNameFromPk(String pk_dept) throws BusinessException {
		String sql = "select bd_deptdoc.deptcode, bd_deptdoc.deptname from bd_deptdoc where bd_deptdoc.pk_deptdoc = ?";
		String errMsg = "找不到对应部门["+pk_dept+"]。";
		
		return get1RecordNValue(sql, new String[]{pk_dept}, errMsg);
	}

	public static Vector<String> getUserCodeAndNameFromPk(String pk_user) throws BusinessException {
		String sql = "select sm_user.user_code, sm_user.user_name from sm_user where sm_user.cuserid=?";
		String errMsg = "找不到操作员["+pk_user+"]。";
		
		return get1RecordNValue(sql, new String[]{pk_user}, errMsg);
	}
}
