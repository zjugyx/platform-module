package com.qingting.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	/**
	 * 
	 * @param date
	 * @param type 
	 * 			1:小时 2：天 3：月
	 * @param amount
	 * @return
	 */
	public static Date addTime(Date date,int type,int amount){
		
		Calendar calendar = Calendar.getInstance();  
		if(date!=null){
			
			calendar.setTime(date);
		}
		
		if(type==1){
			
			calendar.add(Calendar.HOUR_OF_DAY, amount);
		}else if(type==2){
			
			calendar.add(Calendar.DATE, amount);
		}else if(type==3){
			
			calendar.add(Calendar.MONTH, amount);
		}
		
		return calendar.getTime();
	}
	
	public static String getHHmmss(long second){
		
		long h = 0;
		long d = 0;
		long s = 0;
		long temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}

		return h + "小时" + d + "分" + s + "秒";
	}
	
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }   
    
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
    
    /**
	 * 根据格式将日期类型转成字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 符合格式的日期字符串
	 */
	public static String getStringByDate(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}
	
	/**
	 * 根据格式将日期类型转成字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 符合格式的日期字符串
	 */
	public static String getSrByDate(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH);//获取月份
        int day=cal.get(Calendar.DATE);//获取日
		
		return year + "年" + month + "月" + day + "日";
	}
	
	/**
	 * 将String转成Date
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式，如：yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByString(String date, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
}
