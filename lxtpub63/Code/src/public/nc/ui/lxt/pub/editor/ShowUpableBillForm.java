package nc.ui.lxt.pub.editor;

import nc.ui.pubapp.uif2app.event.card.CardPanelEventTransformer;
import nc.ui.pubapp.uif2app.model.IAppModelEx;
import nc.ui.uif2.components.AutoShowUpEventSource;
import nc.ui.uif2.components.IAutoShowUpComponent;
import nc.ui.uif2.components.IAutoShowUpEventListener;

public class ShowUpableBillForm extends nc.ui.uif2.editor.BillForm implements IAutoShowUpComponent{

	private static final long serialVersionUID = 1L;
	private AutoShowUpEventSource autoShowUpComponent = new AutoShowUpEventSource(this);
	private CardPanelEventTransformer eventTransformer;
	
	@Override
	public void setAutoShowUpEventListener(IAutoShowUpEventListener l) {
		this.autoShowUpComponent.setAutoShowUpEventListener(l);
	}

	@Override
	public void showMeUp() {
		this.autoShowUpComponent.showMeUp();
	}

	@Override
	public void initUI() {
		super.initUI();
		
		eventTransformer = new CardPanelEventTransformer(this.getBillCardPanel(), (IAppModelEx) this.getModel());
	    eventTransformer.setContext(this.getModel().getContext());
	    //eventTransformer.setOrgBillEditListener(this);
	    //eventTransformer.setBillTabbedPaneTabChangeListener(tabChangeHandler);
	}
}
