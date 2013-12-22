package nc.ui.lxt.pub;

import java.util.Arrays;
import java.util.HashMap;

import nc.ui.pub.formulaparse.FormulaParseRunInServer;
import nc.vo.pub.BusinessException;
import nc.vo.pub.formulaset.FormulaParseFather;
import nc.vo.pubapp.query2.sql.process.QueryCondition;

public class NCUITool {
	static public String[] getQueryCondition(HashMap<String, QueryCondition> conditions, String id, String[] initdata) throws BusinessException {
		if (initdata.length != 2)
			throw new BusinessException("必需是两个值。");
		String[] result = Arrays.copyOf(initdata, 2);
		if (conditions.get(id) != null) {
			String[] cvals = conditions.get(id).getValues();
			
			if (cvals[0]!=null && cvals[0].length()>0)
				result[0] = cvals[0];
			if (cvals.length>1 && cvals[1]!=null && cvals[1].length()>0)
				result[1] = cvals[1];
		}
		
		return result;
	}
	
	static public Object[][] getValueByFormula(String[] formula, HashMap<String, Object> vars) {
		FormulaParseFather f = new FormulaParseRunInServer();
		for (String key : vars.keySet())
			f.addVariable(key ,vars.get(key));
		f.setExpressArray(formula);
		Object[][] results = f.getValueSArray();

		return results;
	}
}
