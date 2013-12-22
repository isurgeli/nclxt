package nc.ui.lxt.pub.event;

import nc.ui.uif2.AppEvent;

public class ModelStatusChangeEvent extends AppEvent {
	public ModelStatusChangeEvent(Object source, Object contextObject) {
		super("ModelStatus_Changed", source, contextObject);
	}
}
