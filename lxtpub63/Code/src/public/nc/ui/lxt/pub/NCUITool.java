package nc.ui.lxt.pub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import antlr.collections.List;

import nc.ui.pub.formulaparse.FormulaParseRunInServer;
import nc.vo.pub.BusinessException;
import nc.vo.pub.formulaset.FormulaParseFather;
import nc.vo.pubapp.query2.sql.process.QueryCondition;

public class NCUITool {
	static public String[] getQueryCondition(HashMap<String, QueryCondition> conditions, String id, String[] initdata) throws BusinessException {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(initdata));
		if (conditions.get(id) != null) {
			String[] cvals = conditions.get(id).getValues();
			
			for (int i=0;i<cvals.length;i++) {
				if (cvals[i]!=null && cvals[i].length()>0 && i<initdata.length)
					result.set(i, cvals[i]);
				else if (cvals[i]!=null && cvals[i].length()>0) 
					result.add(cvals[i]);
			}
		}
		
		return result.toArray(new String[]{});
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
