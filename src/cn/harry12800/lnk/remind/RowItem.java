package cn.harry12800.lnk.remind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.style.UI;
/**
 * 两种形态。
 * @author Yuexin
 *
 */
public class RowItem extends JPanel {
	boolean hover = false;
	boolean isSelect = true;
	boolean isDir = false;
	private int w;
	private int h;
	public Image image= null;
	public int borderRadius= 0;
	public String url;
	public JLabel icon = new JLabel();
	public JLabel text = new JLabel();
	protected File file;

	public RowItem(int w, int h) {
		this.w = w;
		this.h = h;
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(null);
		setOpaque(false);
		setMinimumSize(new Dimension(w, h) );
		setMaximumSize( new Dimension(w, h) );
		text.setForeground(Color.WHITE);
		icon.setOpaque(false);
		text.setOpaque(false);
		icon.setBounds(2, h/2-8, 16, 16);
		text.setBounds(18, 0, w-18, h);
		text.setFont(new Font("宋体", Font.PLAIN, 12));
		add(icon);
		add(text);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Color color = new Color(255, 255, 255);
				setForeground(color);
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Color color = new Color(186, 186, 186);
				setForeground(color);
				hover = false;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
				}
				super.mouseReleased(e);
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		if (hover||isSelect) {
			g2d.setColor(UI.foreColor);
			g2d.fillRoundRect(0, 0, w , h, 0, 0);
		}  else {
			g2d.setColor(UI.backColor);
//			g2d.fillRoundRect(0, 0, w , h, 0, 0);
		} 
		g2d.dispose();
	}

	private static final long serialVersionUID = 1L;

	public static RowItem createDirItem(BufferedImage byName) {
		RowItem rowItem = new RowItem(200, 30);
		rowItem.image = byName;
		rowItem.isDir = true;
		return rowItem;
	}
	public static RowItem createUrlItem(String url) {
		RowItem rowItem = new RowItem(200, 30);
		rowItem.url = url;
		return rowItem;
	}
}
