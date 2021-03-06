package nc.ui.lxt.pub.view.helper;

import java.util.ArrayList;
import java.util.List;

import nc.ui.pub.bill.BillModelCellEditableController;

public class DefaultCellEditableController implements
		BillModelCellEditableController {

	private List<String> editableKeys = new ArrayList<String>();
	
	public List<String> getEditableKeys() {
		return editableKeys;
	}
	
	public void setEditableKeys(List<String> editableKeys) {
		this.editableKeys = editableKeys;
	}
	
	@Override
	public boolean isCellEditable(boolean value, int row, String itemkey) {
		if (editableKeys.contains(itemkey)) 
			return value;
		else
			return false;
	}
}
