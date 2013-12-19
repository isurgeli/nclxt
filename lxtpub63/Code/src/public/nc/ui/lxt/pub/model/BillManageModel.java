package nc.ui.lxt.pub.model;

import nc.ui.pubapp.uif2app.AppUiState;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.model.AppModelExDelegate;
import nc.ui.pubapp.uif2app.model.IAppModelEx;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.AppEventListener;

public class BillManageModel extends nc.ui.uif2.model.BillManageModel implements IAppModelEx{
	private AppModelExDelegate appModelExDelegate = new AppModelExDelegate(this);
	
	@Override
	public void addAppEventListener(Class<? extends AppEvent> eventType,
			IAppEventHandler<? extends AppEvent> l) {
		this.appModelExDelegate.addAppEventListener(eventType, l);
	}

	@Override
	public AppUiState getAppUiState() {
		return this.appModelExDelegate.getAppUiState();
	}

	@Override
	public void removeAppEventListener(Class<? extends AppEvent> eventType,
			IAppEventHandler<? extends AppEvent> l) {
		this.appModelExDelegate.removeAppEventListener(eventType, l);
	}

	@Override
	public void setAppUiState(AppUiState appUiState) {
		this.appModelExDelegate.setAppUiState(appUiState);
	}
	
	public void addAppEventListener(AppEventListener l) {
		this.appModelExDelegate.addAppEventListener(l);
	}
	
	public void removeAppEventListener(AppEventListener l) {
		this.appModelExDelegate.removeAppEventListener(l);
	}

	@Override
	public void fireEvent(AppEvent event) {
		super.fireEvent(event);
		this.appModelExDelegate.fireEvent(event);
	}
}
