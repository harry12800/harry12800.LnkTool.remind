package cn.harry12800.lnk.remind.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cn.harry12800.Lnk.core.util.ImageUtils;
import cn.harry12800.j2se.calendar.Lunar;
import cn.harry12800.j2se.component.InputText;
import cn.harry12800.j2se.component.MButton;
import cn.harry12800.j2se.component.btn.DateContainer;
import cn.harry12800.j2se.component.btn.ImageBtn;
import cn.harry12800.j2se.component.btn.TurnButton;
import cn.harry12800.j2se.component.panel.AreaTextPanel;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.lnk.remind.RemindData;
import cn.harry12800.lnk.remind.UserData;
import cn.harry12800.tools.DateUtils;
import cn.harry12800.tools.Lists;
import cn.harry12800.tools.StringUtils;

/**
 * 添加新的任务提醒窗口的画板。。 根据时间周期，农历，阳历。进行设置。
 * 
 * @author harry12800
 *
 */
public class AlarmPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageBtn closeButton;
	private AlarmDialog alarmDialog;
	private JLabel titleLabel = new JLabel("时钟提示器（任务追加）");
	private JLabel solarLabel = new JLabel("农历/阳历");
	private JLabel cycleLabel = new JLabel("周期");
	private JRadioButton yearRadio = new JRadioButton("年");
	private JRadioButton monthRadio = new JRadioButton("月");
	private JRadioButton weekRadio = new JRadioButton("星期");
	AreaTextPanel areaTextPanel = new AreaTextPanel();

	TurnButton turnButton = new TurnButton(50, 24);
	JButton dateChooser = new MButton("OK", 180, 30);
	MButton OkBtn = new MButton("OK", 70, 30);
	UserData userData;
	ButtonGroup bg = new ButtonGroup();
	InputText titleTextInput = new InputText(50);
	private DateContainer dateContainer;

	public AlarmPanel(AlarmDialog alarmDialog) {
		this.alarmDialog = alarmDialog;
		userData = AlarmDialog.bookDialog.userData;
		setOpaque(false);
		yearRadio.setOpaque(false);
		yearRadio.setSelected(true);
		monthRadio.setOpaque(false);
		weekRadio.setOpaque(false);
	
		bg.add(yearRadio);
		bg.add(monthRadio);
		bg.add(weekRadio);
		this.dateContainer = new DateContainer();
		dateContainer.relative(dateChooser);
		dateChooser.setText(dateContainer.value);
		// dateChooser = DateChooser.getInstance("yyyy-MM-dd");
		// dateChooser.addDateActionListener(this);
		closeButton = new ImageBtn(ImageUtils.getByName("close24.png"));
		setProps();
		setLayout(null);
		addComponent();
		initCompBounds();
		initCompListener();
	}

	private void setProps() {
		titleLabel.setFont(UI.微软雅黑Font);
		titleLabel.setForeground(UI.fontColor);
		areaTextPanel.setSize(500, 150);
		areaTextPanel.setPreferredSize(new Dimension(500, 150));
	}

	private void initCompListener() {
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alarmDialog.dispose();
			}
		});
		OkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar date = dateContainer.select;
				boolean value = turnButton.getValue();
				RemindData e1;
				if (value) { // 如果是农历
					Lunar lunar = dateContainer.selectLunar;
					e1 = new RemindData(lunar, StringUtils.getUUID(),
							titleTextInput.getText(),
							areaTextPanel.getText(),
							DateUtils.getCurrTimeByFormat("yyyy-MM-dd hh:mm:ss"));
				} else {
					e1 = new RemindData(date,StringUtils.getUUID(), titleTextInput.getText(), areaTextPanel.getText(),
							DateUtils.getCurrTimeByFormat("yyyy-MM-dd hh:mm:ss"));
				}
			
				if(yearRadio.isSelected()){
					e1.setCycleType(1);
				}
				else if(monthRadio.isSelected()){
					e1.setCycleType(2);
				}
				else if(weekRadio.isSelected()){
					e1.setCycleType(3);
				}
				if (userData.remindDataes == null) {
					userData.remindDataes = Lists.newArrayList();
				}
				userData.remindDataes.add(e1);
				AlarmDialog.bookDialog.saveUserDataToFile();
			}
		});
	}

	private void initCompBounds() {
		titleLabel.setBounds(2, 0, 200, 25);
		closeButton.setBounds(485, 0, 25, 25);
		areaTextPanel.setBounds(5, 160, 500, 150);
		dateChooser.setBounds(30, 30, 180, 25);
		turnButton.setBounds(120, 90, 50, 30);
		solarLabel.setBounds(30, 85, 80, 30);
		cycleLabel.setBounds(30, 55, 80, 30);
		yearRadio.setBounds(100, 55, 50, 30);
		monthRadio.setBounds(150, 55, 50, 30);
		weekRadio.setBounds(200, 55, 50, 30);
		OkBtn.setBounds(210, 315, 70, 30);
		titleTextInput.setBounds(5, 130, 500, 30);
	}

	private void addComponent() {
		add(closeButton);
		add(titleTextInput);
		add(areaTextPanel);
		add(titleLabel);
		add(dateChooser);
		add(OkBtn);
		add(turnButton);
		add(solarLabel);
		add(cycleLabel);
		add(yearRadio);
		add(monthRadio);
		add(weekRadio);
	}

	public void dateActionClick(Calendar calendar, String date) {

	}
}
