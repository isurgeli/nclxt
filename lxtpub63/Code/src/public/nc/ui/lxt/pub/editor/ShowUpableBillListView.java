package nc.ui.lxt.pub.editor;

import nc.ui.pubapp.uif2app.event.list.ListEventTransformer;
import nc.ui.pubapp.uif2app.model.IAppModelEx;
import nc.ui.uif2.UIState;
import nc.ui.uif2.components.AutoShowUpEventSource;
import nc.ui.uif2.components.IAutoShowUpComponent;
import nc.ui.uif2.components.IAutoShowUpEventListener;

public class ShowUpableBillListView extends nc.ui.uif2.editor.BillListView implements IAutoShowUpComponent{

	private static final long serialVersionUID = 1L;
	
	private AutoShowUpEventSource autoShowUpComponent = new AutoShowUpEventSource(this);

	@Override
	public void setAutoShowUpEventListener(IAutoShowUpEventListener l) {
		this.autoShowUpComponent.setAutoShowUpEventListener(l);
	}

	@Override
	public void showMeUp() {
		// ��ת�������ݴ�����棬���ذ�ť�ڱ༭̬Ҳ�ǿɼ��ģ������ڷ����л��б�����ʱ��
		// Ҫ��UI״̬�޸ĳɷǱ༭̬���������İ�ť��ʾ��������⡣
		if (this.getModel() != null	&& this.getModel().getUiState() != UIState.NOT_EDIT) {
			this.getModel().setUiState(UIState.NOT_EDIT);
		}
		this.autoShowUpComponent.showMeUp();
	}
	
	@Override
	public void initUI() {
		super.initUI();
		ListEventTransformer eventTransformer = new ListEventTransformer(this.getBillListPanel(), (IAppModelEx) this.getModel());
		eventTransformer.setOriginEditListener(this);
		eventTransformer.setOriginMouseListener(this);
		eventTransformer.setOriginBillSortListener(this.getModel());
		eventTransformer.setContext(this.getModel().getContext());
	}
}
