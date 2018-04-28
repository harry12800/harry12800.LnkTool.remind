package cn.harry12800.lnk.remind;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cn.harry12800.Lnk.core.util.ImageUtils;
import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.component.BaseWindow;
import cn.harry12800.j2se.style.MyScrollBarUI;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.tip.Letter;
import cn.harry12800.j2se.tip.ListPanel;
import cn.harry12800.lnk.remind.alarm.AlarmTask;
import cn.harry12800.lnk.remind.alarm.MyTimerTask;
import cn.harry12800.tools.Lists;

@SuppressWarnings("serial")
public class TaskScanDialog  extends BaseWindow {
	public static TaskScanDialog instance;
	public TaskScanDialog( BookPanel diaryPanel) {
		instance = this;
		setType(JFrame.Type.UTILITY);
		setSize(350,420);
		List<Letter> letters = Lists.newArrayList();
		List<MyTimerTask> tasks = AlarmTask.allTasks;
		Collections.sort(tasks, new Comparator<MyTimerTask>() {
			@Override
			public int compare(MyTimerTask o1, MyTimerTask o2) {
				if(o1.getData().getInsertTime()==null)return -1;
				if(o2.getData().getInsertTime()==null)return 1;
				return  o1.getData().getInsertTime().compareTo(
						o2.getData().getInsertTime());
			}
		});
		for (MyTimerTask myTimerTask : tasks) {
			letters.add(
					new Letter(myTimerTask.getData().getRemindTitle(),
					myTimerTask.getData().getRemindDetail(),
					myTimerTask.getData().getCurrTime())
					);
		}
		ListPanel<Letter> listPanel = new ListPanel<>( );
		JScrollPane a = new JScrollPane(listPanel ){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.drawImage(ImageUtils.getByName("music1.jpg"),0,0,getWidth()-1,getHeight()-1, null);
				g2d.dispose();
			}
		};
		listPanel.setItems(letters );
		a.setOpaque(false);
		a.getViewport().setOpaque(false);
		a.getVerticalScrollBar().setBackground(UI.backColor);
		MyScrollBarUI myScrollBarUI = new MyScrollBarUI();
		a.getVerticalScrollBar().setUnitIncrement(20);
		a.getVerticalScrollBar().setUI(myScrollBarUI);
		// 屏蔽横向滚动条
		a.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		a.setBounds(0, 0, 300, 420);
		setContentPane(a);
		new DragListener(this);
		diaryPanel.addWindow(this);
	}
	public void setCenterScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
	}
}
