package cn.harry12800.lnk.remind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import cn.harry12800.Lnk.core.Context;
import cn.harry12800.Lnk.core.CorePanel;
import cn.harry12800.Lnk.core.FunctionPanelModel;
import cn.harry12800.Lnk.core.util.JsonUtil;
import cn.harry12800.j2se.style.MyScrollBarUI;
import cn.harry12800.j2se.style.MyTreeUI;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.lnk.remind.alarm.AlarmTask;
import cn.harry12800.lnk.remind.dialog.BookDialog;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Lists;
import cn.harry12800.tools.Maps;
import cn.harry12800.tools.StringUtils;

@SuppressWarnings("rawtypes")
@FunctionPanelModel(configPath = "book", height = 420, width = 200, backgroundImage = "back_2.jpg", desc = "记录", headerImage = "alarm.png")
public class BookPanel extends CorePanel implements DropTargetListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1458715407663322791L;
	protected static final Object BookItemNode = null;
	private int width = 200;
	private int height = 420;
	DefaultMutableTreeNode root;
	private DefaultTreeModel model;
	private JTree catalogTree;
	// 微软雅黑
	public static Font BASIC_FONT = new Font("微软雅黑", Font.PLAIN, 12);
	public static Font BASIC_FONT2 = new Font("微软雅黑", Font.TYPE1_FONT, 12);
	// 楷体
	public static Font DIALOG_FONT = new Font("楷体", Font.PLAIN, 16);

	public static Border GRAY_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	public static Border ORANGE_BORDER = BorderFactory.createLineBorder(Color.ORANGE);
	public static Border LIGHT_GRAY_BORDER = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	public Map<String, BookDialog> maps = Maps.newHashMap();
	public static BookDialog defaultBookDialog = null;
	public static void main(String[] args) {
		try {
			// Main.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BookPanel(Context context) {
		super(context);
		setLayout(new BorderLayout());
		root = new DefaultMutableTreeNode();
		model = new DefaultTreeModel(root);
		setBackground(UI.backColor);
		File file = new File(dirPath);
		// new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		setSize(width, height);
		File[] listFiles = file.listFiles();
		List<UserData> aa = Lists.newArrayList();
		for (File f : listFiles) {
			if (f.isDirectory()) {
				BookItemNode node = new BookItemNode(this, f);
				UserData userData = null;
				try {
					userData = JsonUtil.string2Json(new File(f.getAbsolutePath() + "/" + "info.json"), UserData.class);
					if (userData.file == null) {
						userData.file = f;
						userData.initImageData();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				node.userData = userData;
				root.add(node);
				aa.add(userData);
				// File[] listFiles2 = f.listFiles();
				// for (File file2 : listFiles2) {
				// BookItemNode node1 = new BookItemNode(this, file2);
				// node.add(node1);
				// }
				maps.put(f.getName(), new BookDialog(node));
				if(defaultBookDialog == null)
					defaultBookDialog = maps.get(f.getName());
			} else {
				BookItemNode node = new BookItemNode(this, f);
				root.add(node);
			}
		}

		Date date = new Date();
		date.setTime(date.getTime() + 10000);
		new Timer().schedule(new AlarmTask(aa), date);
		catalogTree = new JTree(model);
		catalogTree.setToggleClickCount(1);// 点击次数
		catalogTree.setOpaque(false);
		catalogTree.setRootVisible(false);// 隐藏根节点
		catalogTree.setDragEnabled(true);
		catalogTree.putClientProperty("JTree.lineStyle", "None");
		catalogTree.setCellRenderer(new TreeNodeRenderer());
		catalogTree.setUI(new MyTreeUI());
		catalogTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) catalogTree.getLastSelectedPathComponent(); // 返回最后选中的结点
				if (node instanceof BookItemNode) {

				}
				if (node instanceof BookCategoryNode) {

				}
			}
		});
		catalogTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				TreeNodeRenderer.mouseRow = -1;
				catalogTree.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					final TreePath path = catalogTree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						// path中的node节点（path不为空，这里基本不会空）
						final Object object = path.getLastPathComponent();
						if (object instanceof BookItemNode) {
							BookItemNode node = (BookItemNode) object;
							maps.get(node.file.getName()).setVisible(true);
						}
					}
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					final TreePath path = catalogTree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						// path中的node节点（path不为空，这里基本不会空）
						final Object object = path.getLastPathComponent();
						if (object instanceof BookCategoryNode) {
							JPopupMenu pm = new JPopupMenu();
							pm.setBackground(Color.WHITE);
							pm.setBorderPainted(false);
							pm.setBorder(LIGHT_GRAY_BORDER);
							JMenuItem mit0 = new JMenuItem("删除分组");
							// mit0.setOpaque(false);
							mit0.setFont(BASIC_FONT);
							JMenuItem mit1 = new JMenuItem("更换名称");
							// mit1.setOpaque(false);
							mit1.setFont(BASIC_FONT);
							JMenuItem mit2 = new JMenuItem("添加记录");
							// mit2.setOpaque(false);
							mit2.setFont(BASIC_FONT);
							// 删除分组
							mit0.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									((BookCategoryNode) (object)).removeFromParent();
									FileUtils.deleteDir(((BookCategoryNode) (object)).rowItem.file);
									catalogTree.setUI(new MyTreeUI());
									catalogTree.revalidate();
								}
							});
							// 删除分组
							mit1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

								}
							});
							// 新建
							mit2.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									File file = createBook(((BookCategoryNode) (object)).rowItem.file);
									BookItemNode newChild = new BookItemNode(BookPanel.this, file);
									// ((CategoryNode)(object)).add(newChild);
									((BookCategoryNode) (object)).insert(newChild, 0);
									catalogTree.expandPath(path);
									catalogTree.setUI(new MyTreeUI());
									catalogTree.revalidate();
								}
							});
							pm.add(mit2);
							pm.add(mit1);
							pm.add(mit0);
							pm.show(catalogTree, e.getX(), e.getY());
						}
						if (object instanceof BookItemNode) {
							JPopupMenu pm = new JPopupMenu();
							pm.setBackground(Color.WHITE);
							pm.setBorder(LIGHT_GRAY_BORDER);
							pm.setBorderPainted(false);
							JMenuItem mit0 = new JMenuItem("删除记录");
							// mit0.setOpaque(false);
							mit0.setFont(BASIC_FONT);
							mit0.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									((BookItemNode) (object)).removeFromParent();
									((BookItemNode) (object)).panelItem.file.delete();
									catalogTree.setUI(new MyTreeUI());
								}
							});
							pm.add(mit0);
							pm.show(catalogTree, e.getX(), e.getY());
						}
					} else {
						JPopupMenu pm = new JPopupMenu();
						pm.setBackground(Color.WHITE);
						pm.setBorder(LIGHT_GRAY_BORDER);
						JMenuItem mit0 = new JMenuItem("添加分组");
						// mit0.setOpaque(false);
						mit0.setFont(BASIC_FONT);
						mit0.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// ((AricleNode)(object)).removeFromParent();
								// diaryPanel.delAricle(((AricleNode)(object)).getFile());
								// catalogTree.setUI(new MyTreeUI());
							}
						});
						pm.add(mit0);
						pm.show(catalogTree, e.getX(), e.getY());
					}
				}
			}
		});
		catalogTree.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				int x = (int) arg0.getPoint().getX();
				int y = (int) arg0.getPoint().getY();
				// TreePath path = catalogTree.getPathForLocation(x, y);
				catalogTree.getComponentAt(x, y).repaint();
				TreeNodeRenderer.mouseRow = catalogTree.getRowForLocation(x, y);
				catalogTree.repaint();
			}
		});
		catalogTree.setTransferHandler(new MyJTreeTransferHandler());
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setOpaque(false);
		jScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		jScrollPane.setViewportView(catalogTree);
		jScrollPane.getViewport().setBackground(UI.backColor);
		jScrollPane.getViewport().setOpaque(false);
		// jScrollPane.setBackground(UI.backColor);
		jScrollPane.getVerticalScrollBar().setBackground(UI.backColor);
		// jScrollPane.getVerticalScrollBar().setVisible(false);
		jScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		// 屏蔽横向滚动条
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(jScrollPane, BorderLayout.CENTER);
		new TaskScanDialog(this);
	}

	public File createBook(File file) {
		String filepath = file.getAbsolutePath() + File.separator + StringUtils.getUUID();
		try {
			new File(filepath).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new File(filepath);
	}

	public interface CallBack {
		public void callBack();
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) // 如果拖入的文件格式受支持
			{
				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
				// 接收拖拽来的数据
				// @SuppressWarnings("unchecked");
				// List<File> list = (List<File>)
				// (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
				// MainFrame.save(this, list);
			} else {
				dtde.rejectDrop();// 否则拒绝拖拽来的数据
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}
}
