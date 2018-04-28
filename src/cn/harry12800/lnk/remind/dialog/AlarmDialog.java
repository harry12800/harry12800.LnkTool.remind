package cn.harry12800.lnk.remind.dialog;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import cn.harry12800.Lnk.core.util.ImageUtils;
import cn.harry12800.j2se.action.DragListener;

/**
 * 一个用户的信息界面
 * @author Yiliang
 *
 */
public class AlarmDialog extends JDialog{
 
	private static final long serialVersionUID = 1L;
	public static BookDialog bookDialog;

	public AlarmDialog(BookDialog dialog) {
		if(bookDialog == null)
		 bookDialog = dialog;
		setUndecorated(true);
		setLayout(new BorderLayout(0,0));
		new DragListener(this);
		getLayeredPane().setOpaque(false);
		getRootPane().setOpaque(false);
		
		setContentPane(new AlarmPanel(this));
		setSize(510,350);
		setBackground();
		setLocationRelativeTo(null);
//		setVisible(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==27) {
					 setVisible(false);
				}
			}	
		}); 
	}
 
	JLabel picture ;
	public void setBackground(){
		ImageIcon image = new ImageIcon(ImageUtils.getByName("default_back.jpg") );
		getLayeredPane().setLayout(null);
		image.setImage(image.getImage().getScaledInstance( getWidth(),getHeight(),Image.SCALE_DEFAULT));
		picture = new JLabel(image);
		picture.setBounds(0, 0, getWidth(), getHeight());
		getLayeredPane().add(picture, new Integer(Integer.MIN_VALUE));
	}
 
	public static void main(String[] args) {
		new AlarmDialog(null);
	}
}
