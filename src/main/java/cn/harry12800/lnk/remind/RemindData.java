package cn.harry12800.lnk.remind;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import cn.harry12800.j2se.calendar.Lunar;
import cn.harry12800.j2se.calendar.LunarSolarConverter;
import cn.harry12800.j2se.calendar.Solar;
import cn.harry12800.tools.DateUtils;

public class RemindData implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1423172158359417386L;

	private boolean isLunar = false;
	private String id  ; 
	private Date time;
	private String remindDetail;
	private String remindTitle;
	private String insertTime;
	private String currTime;
	private int cycleType; 
	private Solar solar = new Solar();
	private int month;
	private int day;
	private Lunar lunar;
	/**
	 * 
	 * @param year
	 * @param month
	 *            这里自己会把月份减一
	 * @param day
	 */
	public RemindData(Lunar lunar, String id,String title, String content,String insertTime) {
		isLunar = true;
		this.id= id;
		this.lunar = lunar;
		this.remindTitle = title;
		this.insertTime = insertTime;
		currTime = lunar.toString();
		this.remindDetail = content;
		this.month = lunar.month;
		this.day = lunar.day;
		realLunarThatDay(lunar);
	}
	/**
	 * 
	 * @param year
	 * @param month
	 *            这里自己会把月份减一
	 * @param day
	 */
	public RemindData(Calendar calendar,String id, String title, String content , String insertTime) {
		isLunar = false;
		this.id= id;
		this.remindTitle = title;
		this.remindDetail = content;
		time = calendar.getTime();
		this.insertTime  = insertTime;
		currTime = DateUtils.getCurrTime(time);
		this.solar.year = calendar.get(Calendar.YEAR);
		this.solar.month =calendar.get(Calendar.MONTH)+1;
		this.solar.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.month = solar.month;
		this.day = solar.day;
		setTaskTime(calendar);	
//		System.err.println(this);
	}

	/**
	 * 阳历的定时器设置时间。
	 * 
	 * @param calendar
	 */
	private void setTaskTime(Calendar calendar) {
		if(time == null) time = new Date();
		long x = new Date().getTime() - calendar.getTime().getTime();
		if (x >= 0 && x < 24 * 60 * 60 * 1000) {
			this.time.setTime(new Date().getTime() + 2 * 60 * 1000);
		} else if(x > 0){
			this.time.setTime(calendar.getTimeInMillis() + 8 * 60 * 60 * 1000);
		} else{
			this.time = null;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemindData other = (RemindData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public void reStructure() {
		/**
		 * 农历
		 */
		if(isLunar) {
	       	int y =Lunar.getCurrentYear();
			int leapMonth = Lunar.leapMonth(y);
			/**
			 * 如果是闰月
			 */
			if(leapMonth > 0 && month == leapMonth) {
				 Lunar lunar2 = new Lunar();
				 lunar2.isleap= true;
				 lunar2.year = y;
				 lunar2.month = month;
				 lunar2.day = day;
				 realLunarThatDay(lunar2);
				 Lunar lunar3 = new Lunar();
				 lunar3.isleap= false;
				 lunar3.year = y;
				 lunar3.month = month;
				 lunar3.day = day;
				 realLunarThatDay(lunar3);
			}
			/**
			 * 农历非闰月
			 */
			else {
				Lunar lunar3 = new Lunar();
				lunar3.isleap= false;
				lunar3.year = y;
				lunar3.month = month;
				lunar3.day = day;
//				System.out.println(month);
//				System.out.println(lunar3);
				realLunarThatDay(lunar3);
			}
		}
		/**
		 *  非农历
		 */
		else {
//			System.out.println("isSolar");
			int y = Solar.getCurrentYear();
			Calendar calendar = Calendar.getInstance();
			calendar.set(y, month-1,day);
			setTaskTime(calendar);
		}
	}
	/**
	 * 
	 * @param lunar
	 * @return
	 */
	private boolean realLunarThatDay(Lunar lunar) {
		Calendar calendar = Calendar.getInstance();
		this.solar = LunarSolarConverter.LunarToSolar(lunar);
		calendar.set(Calendar.YEAR, solar.year);
		calendar.set(Calendar.MONTH, solar.month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, solar.day);
		time = calendar.getTime();
//		String currTime2 = DateUtils.getCurrTime(time);
//		System.out.println("设置时间："+currTime2);
		if (isOverdue(calendar.getTime())) {
//				System.out.println("过期的");
				lunar.year+=1;
				this.solar = LunarSolarConverter.LunarToSolar(lunar);
				calendar.set(Calendar.YEAR, solar.year);
				calendar.set(Calendar.MONTH, solar.month - 1);
				calendar.set(Calendar.DAY_OF_MONTH, solar.day);
				time = calendar.getTime();
				setTaskTime(calendar);
				return false;
		}else{
//			System.out.println("咩过期");
			setTaskTime(calendar);
			return true;
		}
	}


	/**
	 * 判断这个时间是否过期
	 * @param time2
	 * @return
	 */
	private boolean isOverdue(Date time2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		if (time2.getTime() < zero.getTime()) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RemindData [isLunar=" + isLunar + ", time=" + time
				+ ", remindDetail=" + remindDetail + ", remindTitle="
				+ remindTitle + ", currTime=" + currTime + ", solar=" + solar
				+ ", month=" + month + ", day=" + day + ", lunar=" + lunar
				+ "]";
	}

	/**
	 * 获取month
	 *	@return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 设置month
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * 获取day
	 *	@return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 设置day
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	public boolean isLunar() {
		return isLunar;
	}

	public void setLunar(boolean isLunar) {
		this.isLunar = isLunar;
	}

	/**
	 * 获取solar
	 *
	 * @return the solar
	 */
	public Solar getSolar() {
		return solar;
	}

	/**
	 * 设置solar
	 * 
	 * @param solar
	 *            the solar to set
	 */
	public void setSolar(Solar solar) {
		this.solar = solar;
	}

	/**
	 * 获取lunar
	 *
	 * @return the lunar
	 */
	public Lunar getLunar() {
		return lunar;
	}

	/**
	 * 设置lunar
	 * 
	 * @param lunar
	 *            the lunar to set
	 */
	public void setLunar(Lunar lunar) {
		this.lunar = lunar;
	}

	/**
	 * 获取time
	 *
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * 设置time
	 * 
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * 获取remindDetail
	 *
	 * @return the remindDetail
	 */
	public String getRemindDetail() {
		return remindDetail;
	}

	/**
	 * 设置remindDetail
	 * 
	 * @param remindDetail
	 *            the remindDetail to set
	 */
	public void setRemindDetail(String remindDetail) {
		this.remindDetail = remindDetail;
	}

	/**
	 * 获取remindTitle
	 *
	 * @return the remindTitle
	 */
	public String getRemindTitle() {
		return remindTitle;
	}

	/**
	 * 设置remindTitle
	 * 
	 * @param remindTitle
	 *            the remindTitle to set
	 */
	public void setRemindTitle(String remindTitle) {
		this.remindTitle = remindTitle;
	}

	/**
	 * 获取currTime
	 *
	 * @return the currTime
	 */
	public String getCurrTime() {
		return currTime;
	}

	/**
	 * 设置currTime
	 * 
	 * @param currTime
	 *            the currTime to set
	 */
	public void setCurrTime(String currTime) {
		this.currTime = currTime;
	}

	public int getCycleType() {
		return cycleType;
	}

	public void setCycleType(int cycleType) {
		this.cycleType = cycleType;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
