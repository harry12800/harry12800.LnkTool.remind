package cn.harry12800.lnk.remind.alarm;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.harry12800.j2se.tip.TipFrame;
import cn.harry12800.lnk.remind.RemindData; 
/**
 * 实现定时任务
 * 
 */
public class MyTimerTask extends TimerTask {

	/**
	 * 获取data
	 *	@return the data
	 */
	public RemindData getData () {
		return data;
	}

	/**
	 * 设置data
	 * @param data the data to set
	 */
	public void setData(RemindData data) {
		this.data = data;
	}
	private RemindData data;
	private Date time;
	public MyTimerTask(RemindData data) {
		this.data = data;
		this.time = data.getTime();
		
	}
	
	@Override
	public void run() {
		 TipFrame.show(data.getRemindTitle(), "ok",data.getCurrTime()+"\r\n"+data.getRemindDetail());
	}

	public Date getDate() {
		return time; 
	}
	public static void main(String[] args) {
		final Timer timer = new Timer();
		int purge = timer.purge();
		System.out.println(purge);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int purge = timer.purge();
				System.out.println(purge);
				System.exit(0);
			}
		}, new Date());
		
	}
}