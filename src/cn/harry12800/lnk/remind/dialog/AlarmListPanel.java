package cn.harry12800.lnk.remind.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

public class AlarmListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageBtn closeButton;
	private AlarmListDialog alarmDialog;
	private JLabel titleLabel = new JLabel("时钟提示器（任务追加）");
	AreaTextPanel areaTextPanel = new AreaTextPanel();
	
	TurnButton turnButton = new TurnButton(50, 24);
	JButton dateChooser = new MButton("OK", 140, 30);
	MButton OkBtn = new MButton("OK", 70, 30);
	UserData userData;
	InputText titleTextInput = new InputText(50);
	private DateContainer dateContainer;
	public AlarmListPanel(AlarmListDialog alarmDialog) {
		this.alarmDialog =alarmDialog;
		userData = alarmDialog.bookDialog.userData;
		setOpaque(false);
		this.dateContainer = new DateContainer();
		dateContainer.relative(dateChooser);
		dateChooser.setText(dateContainer.value);
//		dateChooser = DateChooser.getInstance("yyyy-MM-dd");
//		dateChooser.addDateActionListener(this);
		closeButton= new ImageBtn(ImageUtils.getByName("close24.png"));
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
				if(value){ //如果是农历
					Lunar lunar = dateContainer.selectLunar;
					e1 = new RemindData(lunar,StringUtils.getUUID(),titleTextInput.getText(),
							areaTextPanel.getText(),
							DateUtils.getCurrTimeByFormat("yyyy-MM-dd hh:mm:ss"));
				}else{
					e1 = new RemindData(date,StringUtils.getUUID(),titleTextInput.getText(),areaTextPanel.getText(),
							DateUtils.getCurrTimeByFormat("yyyy-MM-dd hh:mm:ss"));
				}
				if(userData.remindDataes==null){
					userData.remindDataes= Lists.newArrayList();
				}
				userData.remindDataes.add(e1);
				alarmDialog.bookDialog.saveUserDataToFile();
			}
		});
	}
	private void initCompBounds() {
		titleLabel.setBounds(2, 0, 200, 25);
		closeButton.setBounds(485, 0, 25, 25);
		areaTextPanel.setBounds(5, 110, 500, 150);
		dateChooser.setBounds(150, 30, 100, 25);
		turnButton.setBounds(320, 35, 50, 30);
		OkBtn.setBounds(210, 265, 70, 30);
		titleTextInput.setBounds(5, 80, 500, 30);
	}
	private void addComponent() {
		add(closeButton);
		add(titleTextInput);
		add(areaTextPanel);
		add(titleLabel);
		add(dateChooser);
		add(OkBtn);
		add(turnButton);
	}
	public void dateActionClick(Calendar calendar,String date) {
		
	}
}
