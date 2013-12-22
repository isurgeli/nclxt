package nc.ui.lxt.pub.view.helper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nc.ui.lxt.pub.view.helper.MouseClickShowPanelMediator;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.components.IAutoShowUpComponent;
import nc.ui.uif2.editor.BillListView;

public class MouseClickShowPanelMediator {

	class MouseListenerHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() > 1) {
				MouseClickShowPanelMediator.this.showUpComponent.showMeUp();
				MouseClickShowPanelMediator.this
						.setCardSelectedTabedPaneFromList();
				MouseClickShowPanelMediator.this.showSuccessInfo();
			}
		}
	}

	// 设置卡片界面表体选中页签为列表界面表体选中页签
	protected void setCardSelectedTabedPaneFromList() {
		int listTabIndex = listView.getBillListPanel().getBodyTabbedPane()
				.getSelectedIndex();
		if (showUpComponent instanceof nc.ui.uif2.editor.BillForm) {
			int cardTabCount = ((nc.ui.uif2.editor.BillForm) showUpComponent)
					.getBillCardPanel().getBodyTabbedPane().getTabCount();
			if (cardTabCount == 0)
				return;
			((nc.ui.uif2.editor.BillForm) showUpComponent).getBillCardPanel()
					.getBodyTabbedPane().setSelectedIndex(listTabIndex);
		}
	}

	IAutoShowUpComponent showUpComponent;

	private String hyperLinkColumn;

	private BillListView listView;

	public String getHyperLinkColumn() {
		return this.hyperLinkColumn;
	}

	public BillListView getListView() {
		return this.listView;
	}

	public IAutoShowUpComponent getShowUpComponent() {
		return this.showUpComponent;
	}

	public void setHyperLinkColumn(String hyperLinkColumn) {
		this.hyperLinkColumn = hyperLinkColumn;
	}

	public void setListView(BillListView listView) {
		this.listView = listView;
		this.match();
	}

	public void setShowUpComponent(IAutoShowUpComponent showUpComponent) {
		this.showUpComponent = showUpComponent;
		this.match();
	}

	/**
	 * 切换界面时清空状态栏提示信息
	 */
	void showSuccessInfo() {
		ShowStatusBarMsgUtil.showStatusBarMsg(null, this.listView.getModel()
				.getContext());
	}

	private void match() {
		if (null == this.listView || null == this.showUpComponent) {
			return;
		}
		MouseListenerHandler l = new MouseListenerHandler();
		this.listView.getBillListPanel().getHeadTable().addMouseListener(l);
		// listView.getBillListPanel().getBodyTable().addMouseListener(l);
	}
}
