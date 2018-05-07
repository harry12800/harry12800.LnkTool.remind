package cn.harry12800.lnk.remind.dialog;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.UI;
class PropertiesPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JLabel sJLabel;

		public PropertiesPanel(int type, Entry<String, String> e) {
			setOpaque(false);
			if (type == 1)
				sJLabel = new JLabel("+");
			else {
				sJLabel = new JLabel("-");
			}
			sJLabel.addMouseListener(new MouseListener() {

				public void mouseReleased(MouseEvent e) {

				}

				public void mousePressed(MouseEvent e) {

				}

				public void mouseExited(MouseEvent e) {
					sJLabel.setForeground(UI.foreColor);
				}

				public void mouseEntered(MouseEvent e) {
					sJLabel.setForeground(UI.earnColor);
				}

				public void mouseClicked(MouseEvent e) {

				}
			});
			add(sJLabel);
			sJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			sJLabel.setForeground(UI.foreColor);
			sJLabel.setFont(new Font("宋体", Font.BOLD, 18));
			TextLabel textLabel = new TextLabel(5);
			textLabel.setText(e.getKey());
			TextLabel textLabel2 = new TextLabel(20);
			textLabel2.setText(e.getValue());
			add(textLabel);
			add(textLabel2);
		}
	}