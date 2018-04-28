package cn.harry12800.lnk.remind.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.component.panel.AreaTextPanel;
import cn.harry12800.lnk.remind.UserData;

/**
 * 滚动面板的内容。
 * 
 * @author Yiliang
 *
 */
class UserDataCorePanel extends JPanel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 8446929748096474523L;

	/**
	 * @param userData
	 */
	public UserDataCorePanel(UserData userData) {
		setOpaque(false);
		// setPreferredSize(new Dimension(100,1000));
		Box createVerticalBox = Box.createVerticalBox();
		add(createVerticalBox);
		Box createVerticalBox2 = Box.createVerticalBox();
		createVerticalBox2.setFocusable(true);
		createVerticalBox2.setRequestFocusEnabled(true);
		createVerticalBox2.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		add(createVerticalBox2);
		Map<String, String> data = userData.getData();
		int i = 0;
		for (Entry<String, String> e : data.entrySet()) {
			if("name".equals(e.getKey())||"sex".equals(e.getKey()))
				continue;
			createVerticalBox2.add(new PropertiesPanel(0, e), i++);
		}
		// createVerticalBox2.add(new PropertiesPanel(1,e), 0);
		JPanel jPanel = new JPanel();
		jPanel.setOpaque(false);
		jPanel.setLayout(new BorderLayout(5, 5));
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		jPanel.add(new JLabel("个人说明"));
		createVerticalBox2.add(jPanel);
		AreaTextPanel areaTextPanel = new AreaTextPanel();
		areaTextPanel.setSize(200, 50);
		areaTextPanel.setPreferredSize(new Dimension(200, 50));
		createVerticalBox2.add(areaTextPanel);
		createVerticalBox.add(createVerticalBox2);
	}
}