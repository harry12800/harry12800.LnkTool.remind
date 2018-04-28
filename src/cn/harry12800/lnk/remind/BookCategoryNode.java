package cn.harry12800.lnk.remind;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.harry12800.Lnk.core.util.ImageUtils;

public class BookCategoryNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Icon icon;
	public JLabel picture;
	public JLabel nickName;
	public RowItem rowItem = null;

	public BookCategoryNode(BookPanel httpUrlNewPanel, File f) {
		icon = ImageUtils.getIcon("arrow_left.png");
		rowItem = RowItem.createDirItem(ImageUtils.getByName("arrow_left.png"));
		rowItem.file=f;
		rowItem.text.setText(f.getName());
		rowItem.setBounds(0, 0, 200, 25);
	}
	public Component getView() {
		return rowItem;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		rowItem.icon.setIcon(icon);
		this.icon = icon;
	}
	 
	public Component setSelect(boolean selected, boolean mouseEnter) {
		rowItem.isSelect =selected;
		rowItem.hover = mouseEnter;
		return rowItem;
	}
	public Component getView(boolean selected, boolean mouseEnter) {
		rowItem.isSelect =selected;
		rowItem.hover = mouseEnter;
		return rowItem;
	}
}
