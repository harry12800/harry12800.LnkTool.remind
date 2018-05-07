package cn.harry12800.lnk.remind;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import cn.harry12800.Lnk.core.util.ImageUtils;

public class TreeNodeRenderer extends DefaultTreeCellRenderer {
	// 通过mouseEnter判定当前鼠标是否悬停
    private boolean mouseEnter = false;
    public TreeNodeRenderer() {
        super();
    }
	private static final long serialVersionUID = 1L;
	public static int mouseRow = -1;
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// 执行父类原型操作
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		 //通过mouseRow判断鼠标是否悬停在当前行
        if (mouseRow == row) {
            mouseEnter = true;
        } else {
            mouseEnter = false;
        }
		if (value instanceof BookItemNode) {
			if(selected)
				return ((BookItemNode)value).setSelect(selected,mouseEnter);
			else{
				return ((BookItemNode)value).setSelect(selected,mouseEnter);
			}
		}
		if (value instanceof BookCategoryNode) {
			if (expanded) {
				((BookCategoryNode)value).setIcon(ImageUtils.getIcon("arrow_down.png"));
			} else {
				((BookCategoryNode)value).setIcon(ImageUtils.getIcon("arrow_left.png"));
			}
			return ((BookCategoryNode)value).getView(selected,mouseEnter);
		}
		 
		return this;
	}

}
