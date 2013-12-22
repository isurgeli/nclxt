package nc.ui.lxt.pub.view;

import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pubapp.uif2app.event.OrgChangedEvent;
import nc.ui.pubapp.uif2app.model.IAppModelEx;

public class BDOrgPanel extends nc.ui.bd.pub.BDOrgPanel{
	private static final long serialVersionUID = 1L;
	private String oldPkOrg = null;
	public void valueChanged(ValueChangedEvent event) {
		super.valueChanged(event);
		
		if (getModel() instanceof IAppModelEx) {
			String pk_org = getRefPane().getRefPK();
			OrgChangedEvent orgChangedEvent = new OrgChangedEvent(oldPkOrg, pk_org);
		    this.getModel().fireEvent(orgChangedEvent);
		    oldPkOrg = pk_org;
		}
	}
}
