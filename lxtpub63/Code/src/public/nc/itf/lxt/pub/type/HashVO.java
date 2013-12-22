package nc.itf.lxt.pub.type;

import java.util.Hashtable;

import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;

public class HashVO extends SuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, Object> values = new Hashtable<String, Object>();

	@Override
	public String[] getAttributeNames() {
		return values.keySet().toArray(new String[] {});
	}

	@Override
	public Object getAttributeValue(String arg0) {
		return values.get(arg0);
	}

	@Override
	public void setAttributeValue(String arg0, Object arg1) {
		values.put(arg0, arg1);
	}

	@Override
	public String getEntityName() {
		return null;
	}

	@Override
	public void validate() throws ValidationException {
	}
}
