package cn.harry12800.lnk.remind.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import chrriis.dj.nativeswing.swtimpl.components.JFileDialog;
import cn.harry12800.Lnk.core.util.GaussianBlurUtil;
import cn.harry12800.Lnk.core.util.ImageUtils;
import cn.harry12800.j2se.action.ChangeListener;
import cn.harry12800.j2se.component.btn.ImageBtn;
import cn.harry12800.j2se.component.label.HeaderJLabel;
import cn.harry12800.j2se.style.MyScrollBarUI;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.utils.Clip;
import cn.harry12800.lnk.remind.UserData;
import cn.harry12800.tools.FileUtils;

/**
 * 
 * @author Yiliang
 *
 */
class BookPanel extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	/**
	 * 照片墙
	 */
	CarouselImageLabel pictureJLabel = new CarouselImageLabel();
	/**
	 * title 上的名称Label
	 */
	JLabel nameLabel = new JLabel();
	/**
	 * 头像位置的label
	 */
	TextLabel nameTextLabel = new TextLabel(20);
	/**
	 * 性别的位置label
	 */
	JLabel genderJLabel = new JLabel();
	/**
	 * 头像
	 */
	JLabel headerJLabel = new HeaderJLabel();
	/**
	 * 上传图片
	 */
	ImageBtn uploadButton;
	ImageBtn deleteButton;
	ImageBtn closeButton;
	ImageBtn leftButton;
	ImageBtn rightButton;
	ImageBtn editButton;
	/**
	 * 添加监听器
	 */
	ImageBtn addRemind;
	JPanel jPanel = new JPanel();
	JScrollPane jsp = new JScrollPane();
	JPanel contentJPanel;
	private UserData userData;
	private BookDialog bookDialog;

	public BookPanel(final BookDialog bookDialog, final UserData userData) {
		this.userData = userData;
		this.bookDialog = bookDialog;
		contentJPanel = new UserDataCorePanel(userData);
		uploadButton = new ImageBtn(ImageUtils.getByName("plus.png"));
		deleteButton = new ImageBtn(ImageUtils.getByName("delete.png"));
		addRemind = new ImageBtn(ImageUtils.getByName("ic_twitter_plus.png"));
		closeButton= new ImageBtn(ImageUtils.getByName("close24.png"));
		leftButton= new ImageBtn(ImageUtils.getByName("arrow_left1.png"));
		rightButton= new ImageBtn(ImageUtils.getByName("arrow_right1.png"));
		editButton= new ImageBtn(ImageUtils.getByName("post.png"));
		setLayout(null);
		addComponent();
		nameLabel.setFont(UI.微软雅黑Font);
		nameLabel.setText(userData.getName());
		nameTextLabel.setFont(UI.normalFont);
		nameTextLabel.setText(userData.getName());
		nameLabel.setForeground(Color.WHITE);
		nameTextLabel.setForeground(Color.black);
		
		jsp.setPreferredSize(new Dimension(325, 450));

		int sex = userData.getSex();
		ImageIcon icon1 = new ImageIcon();
		if(sex==0)
			icon1.setImage(GaussianBlurUtil.blur(ImageUtils
				.getByName("male_icon.png")));
		else{
			icon1.setImage(GaussianBlurUtil.blur(ImageUtils
					.getByName("female_icon.png")));
		}
		icon1.setImage(icon1.getImage().getScaledInstance(13, 13,
				Image.SCALE_DEFAULT));

		ImageIcon icon3 = new ImageIcon();
		icon3.setImage(ImageUtils.getByName("plus.png"));
		icon3.setImage(icon3.getImage().getScaledInstance(32, 32,
				Image.SCALE_DEFAULT));
		uploadButton.setIcon(icon3);
		uploadButton.setBorderPainted(false);
		uploadButton.setContentAreaFilled(false);
		genderJLabel.setIcon(icon1);
		try {
			pictureJLabel.setImages(userData.imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		this.imageRotationThread = new ImageRotationThread(userData.imagePath, pictureJLabel);
		initCompBounds();
		initCompListener();
		jsp.setViewportView(contentJPanel);
		jsp.setOpaque(false);
		jsp.getVerticalScrollBar().setBackground(UI.backColor);
		jsp.getVerticalScrollBar().setUI(new MyScrollBarUI());
		jsp.getVerticalScrollBar().setUnitIncrement(3);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBorder(null);
		jsp.getViewport().setOpaque(false);
		jsp.getVerticalScrollBar().setVisible(false);
	}
	private void initCompListener() {
		pictureJLabel.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
				}
			}
		});
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookDialog.dispose();
			}
		});
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pictureJLabel.before();
			}
		});
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pictureJLabel.after();
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentImagePath = pictureJLabel.currentImagePath();
				FileUtils.deleteFile(currentImagePath);
				pictureJLabel.after();
			}
		});
		addRemind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlarmDialog alarmDialog = new AlarmDialog(bookDialog);
				alarmDialog.setVisible(true);
			}
		});
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Clip.openFile(userData.infoPath);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		nameTextLabel.setChangeListener(new ChangeListener() {
			public void changed(Object checked) {
				userData.name =(String) checked;
				userData.setName(userData.name);
			}
		});
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileDialog dialog = new JFileDialog();
				// dialog.setSelectedFileName( );
				dialog.setExtensionFilters(new String[] { "*.jpg", "*.bmp",
						"*.png" }, new String[] { "*.jpg", "*.bmp", "*.png" },
						3);
				dialog.show(bookDialog);
				String selectedFileName = dialog.getSelectedFileName();
				if (selectedFileName == null) {
					return;
				}
				String parentDirectory = dialog.getParentDirectory();
				selectedFileName = parentDirectory + File.separator
						+ selectedFileName;
				try {
					System.out.println(selectedFileName);
					System.out.println( userData.imagePath);
					FileUtils.copyFile(selectedFileName, userData.imagePath);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
	}

	private void initCompBounds() {
		pictureJLabel.setBounds(0, 0, 350, 400);
		nameLabel.setBounds(352, 0, 200, 25);
		nameTextLabel.setBounds(150, 450,200, 25);
		jPanel.setBounds(0, 400, 350, 100);
		uploadButton.setBounds(320-40, 368, 32, 32);
		leftButton.setBounds(1, 150, 34, 54 );
		rightButton.setBounds(315, 150, 31, 54);
		deleteButton.setBounds(320, 368, 32, 32);
		closeButton.setBounds(675, 0, 25, 25);
		editButton.setBounds(675,475, 25, 25);
		addRemind.setBounds(645, 0, 25, 25);
		genderJLabel.setBounds(150, 420, 13, 13);
		headerJLabel.setBounds(50, 410, 80, 80);
		jsp.setBounds(365, 30, 325, 450);
	}

	private void addComponent() {
		add(headerJLabel);
		add(genderJLabel);
		add(uploadButton);
		add(leftButton);
		add(rightButton);
		add(deleteButton);
		add(closeButton);
		add(editButton);
		add(addRemind);
		add(pictureJLabel);
		add(nameLabel);
		add(nameTextLabel);
		add(jPanel);
		add(jsp);
	}

}
