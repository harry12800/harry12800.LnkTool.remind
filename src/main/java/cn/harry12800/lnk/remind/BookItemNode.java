package cn.harry12800.lnk.remind;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;

public class BookItemNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	public Icon icon;
	public JLabel picture;
	public JLabel nickName;
	public RowItem panelItem = null;
	public UserData userData;
	public File file;

	public BookItemNode(BookPanel httpUrlNewPanel, File file) {
		panelItem = RowItem.createUrlItem(file.getAbsolutePath());
		panelItem.file = file;
		this.file = file;
		panelItem.text.setText(file.getName());
		panelItem.setBounds(0, 0, 200, 25);
	}

	public Component getView() {
		return panelItem;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		panelItem.icon.setIcon(icon);
		this.icon = icon;
	}

	public Component setSelect(boolean selected, boolean mouseEnter) {
		panelItem.isSelect = selected;
		panelItem.hover = mouseEnter;
		return panelItem;
	}

	public Component getView(boolean mouseEnter) {
		panelItem.hover = mouseEnter;
		return panelItem;
	}
}
