package nc.bs.lxt.pub;

import java.util.HashMap;
import nc.bs.pub.formulaparse.FormulaParse;
import nc.vo.pub.formulaset.FormulaParseFather;

public class NCBSTool {
	static public Object[][] getValueByFormula(String[] formula, HashMap<String, Object> vars) {
		FormulaParseFather f = new FormulaParse();
		for (String key : vars.keySet())
			f.addVariable(key ,vars.get(key));
		f.setExpressArray(formula);
		Object[][] results = f.getValueSArray();

		return results;
	}
}
