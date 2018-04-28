package cn.harry12800.lnk.remind.dialog;

import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import cn.harry12800.j2se.action.ChangeListener;
import cn.harry12800.j2se.style.UI;

class TextLabel extends JTextField implements
			DocumentListener, MouseListener, FocusListener {
		/**
		 * 
		 */
		boolean isEditing = false;
		private ChangeListener listener;
		private static final long serialVersionUID = -7552904957340082605L;

		public void setChangeListener(ChangeListener listener){
			this.listener = listener;
		}
		public TextLabel(int x) {
			super(x);
			setOpaque(false);
			setForeground(UI.fontColor);
			setCaretColor(UI.transColor);
			addMouseListener(this);
			addFocusListener(this);
			// 获取与编辑器关联的模型
			Document doc = getDocument();
			doc.addDocumentListener(this);
		}

		@Override
		protected void paintBorder(Graphics g) {
			if (isEditing) {
				super.paintBorder(g);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				isEditing = true;
				setCaretColor(UI.fontColor);
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// isEditing = true;
			// repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// isEditing = false;
			// repaint();
		}

		@Override
		public void focusGained(FocusEvent e) {

		}

		@Override
		public void focusLost(FocusEvent e) {
			isEditing = false;
			setCaretColor(UI.transColor);
			repaint();
		}

		/**
		 * 实现DocumentListener接口中insertUpdate方法 该方法可以跟踪文本框中输入的内容
		 */
		public void insertUpdate(DocumentEvent e) {
//			Document doc = e.getDocument();
			if(listener!=null){
				listener.changed(getText());
			}
//			try {
//				String s = doc.getText(0, doc.getLength());
//				System.out.println("insertUpdate");
//			} catch (BadLocationException e1) {
//				e1.printStackTrace();
//			} // 返回文本框输入的内容
		}

		/**
		 * 实现DocumentListener接口removeUpdate方法
		 * 该方法可以跟踪文本框中移除的内容，例如：在文本框中点击Backspace
		 */
		public void removeUpdate(DocumentEvent e) {
			if(listener!=null){
				listener.changed(getText());
			}
//			Document doc = e.getDocument();
//			try {
//				String s = doc.getText(0, doc.getLength());
//				System.out.println("removeUpdate");
//			} catch (BadLocationException e1) {
//				e1.printStackTrace();
//			} // 返回文本框输入的内容
		}

		/**
		 * 实现DocumentListener接口changedUpdate方法 该方法可以跟踪当文本框中已存在的内容改变时，获取相应的值
		 */
		public void changedUpdate(DocumentEvent e) {
			if(listener!=null){
				listener.changed(getText());
			}
//			Document doc = e.getDocument();
//			try {
//				String s = doc.getText(0, doc.getLength());
//				System.out.println("changedUpdate");
//			} catch (BadLocationException e1) {
//				e1.printStackTrace();
//			} // 返回文本框输入的内容
		}
	}