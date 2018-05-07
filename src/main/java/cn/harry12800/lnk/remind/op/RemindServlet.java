package cn.harry12800.lnk.remind.op;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.harry12800.j2se.calendar.Lunar;
import cn.harry12800.lnk.remind.BookPanel;
import cn.harry12800.lnk.remind.RemindData;
import cn.harry12800.server.Request;
import cn.harry12800.server.Response;
import cn.harry12800.server.annotation.ServerServlet;
import cn.harry12800.tools.DateUtils;
import cn.harry12800.tools.Lists;

@ServerServlet(name = "RemindServlet ", url = "/remindServlet")
public class RemindServlet  extends cn.harry12800.server.Servlet {

	@Override
	public void doGet(Request request, Response response) throws Exception {
		
	}

	@Override
	public void doPost(Request request, Response response) throws Exception {
		RemindData e1 = null;
		String id = request.getParameter("id");
		String isLunar = request.getParameter("isLunar");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String triggerTime = request.getParameter("triggerTime");
		String insertTime = request.getParameter("insertTime");
		String dir = request.getParameter("dir");
		Date date = DateUtils.getDateByFormat(triggerTime,"yyyyMMdd");
		Calendar cal  = Calendar.getInstance();
		cal.setTime(date );
		if("true".equals(isLunar)){ //如果是农历
			Lunar lunar = new Lunar(cal  );
			e1 = new RemindData(lunar,id,title , content,insertTime);
		}else{
			e1 = new RemindData(cal,id,title,content,insertTime);
		}
		if(BookPanel.defaultBookDialog.userData == null){
//			AlarmDialog.bookDialog.userData  = new UserData( );
		}
		if(BookPanel.defaultBookDialog.userData.remindDataes==null){
			BookPanel.defaultBookDialog.userData.remindDataes= Lists.newArrayList();
		}
		//http://127.0.0.1/remindServlet?title=adfa&content=asf&isLunar=true
		List<RemindData> remindDataes = BookPanel.defaultBookDialog.userData.remindDataes;
		boolean falg= false;
		for (RemindData remindData : remindDataes) {
			if(e1.getId().equals(remindData.getId()))
			{
				BookPanel.defaultBookDialog.userData.remindDataes.remove(remindData);
				e1.setInsertTime(remindData.getInsertTime());
				break;
			}
		} 
		BookPanel.defaultBookDialog.userData.remindDataes.add(e1);
		BookPanel.defaultBookDialog.saveUserDataToFile();
		response.println("success");
	}

}
