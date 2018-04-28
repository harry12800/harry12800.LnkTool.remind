package cn.harry12800.lnk.remind.alarm;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.harry12800.lnk.remind.RemindData;
import cn.harry12800.lnk.remind.UserData;
import cn.harry12800.tools.Lists;

/**
 * 
 * @author Yiliang
 *
 */
public class AlarmTask extends TimerTask{

	public static List<MyTimerTask> tasks;
	public static List<MyTimerTask> allTasks;

	public AlarmTask(List<UserData> userDatas) {
		tasks = Lists.newArrayList();
		allTasks = Lists.newArrayList();
		/**
		 * 遍历所有用户
		 */
		for (UserData userData : userDatas) {
			List<RemindData> accessibility = userData.remindDataes;
			/**
			 * 寻找有效的提示任务。
			 */
			for (RemindData remindData : accessibility) {
				remindData.reStructure();
//				System.out.println(remindData);
				Date time = remindData.getTime();
				allTasks.add(new MyTimerTask(remindData));
				if(time != null)
					tasks.add(new MyTimerTask(remindData));
			}
		}
		Collections.sort(tasks, new Comparator<MyTimerTask>() {
			@Override
			public int compare(MyTimerTask o1, MyTimerTask o2) {
				long x = o1.getDate().getTime() - o2.getDate().getTime();
				if(x>0)return -1;
				if(x<0)return 1;
				return  0;
			}
		});
	}
	
	@Override
	public void run() {
		for (MyTimerTask taskOne : tasks) {
			new Timer().schedule(taskOne,taskOne.getDate());
		}
	}

}
