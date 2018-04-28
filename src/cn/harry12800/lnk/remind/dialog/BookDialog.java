package cn.harry12800.lnk.remind.dialog;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import cn.harry12800.Lnk.core.util.ImageUtils;
import cn.harry12800.Lnk.core.util.JsonUtil;
import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.lnk.remind.BookItemNode;
import cn.harry12800.lnk.remind.UserData;

/**
 * 一个用户的信息界面
 * 
 * @author harry12800
 *
 */
public class BookDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	public UserData userData;
	private File file;
	static final String default_back="default_back.jpg";

	public BookDialog(BookItemNode bookItemNode) {
		this.userData = bookItemNode.userData;
		this.file = bookItemNode.file;
		setUndecorated(true);
		setLayout(new BorderLayout(0, 0));
		new DragListener(this);
		getLayeredPane().setOpaque(false);
		getRootPane().setOpaque(false);

		// JsonUtil.saveObj(parseJsonWithGson,
		// file.getAbsolutePath()+"/"+"info.txt");
		// String readJsonConfig = JsonUtil.readJsonConfig(file);
		setContentPane(new BookPanel(this, bookItemNode.userData));
		setSize(700, 500);
		setBackground();
		setLocationRelativeTo(null);
		setVisible(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 27) {
					setVisible(false);
				}
			}
		});
	}

	public void saveUserDataToFile() {
		JsonUtil.saveObj(userData, file.getAbsolutePath() + File.separator + "info.json");
		System.out.println(file.getAbsolutePath() + File.separator + "info.json");
	}

	JLabel picture;

	public void setBackground() {
		ImageIcon image = new ImageIcon(ImageUtils.getByName(default_back));
		getLayeredPane().setLayout(null);
		image.setImage(image.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
		picture = new JLabel(image);
		picture.setBounds(0, 0, getWidth(), getHeight());
		getLayeredPane().add(picture, new Integer(Integer.MIN_VALUE));
	}
}
