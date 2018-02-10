package com.ctvit.weibo.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ctvit.weibo.task.entity.Task;
public class QuartzRuleUtil {
	
	public QuartzRuleUtil(){}

	/**
	 * 将临时字段拼写存入数据库detail_time
	 * @param tempMonth
	 * @param tempDay
	 * @param tempWeek
	 * @param tempHour
	 * @param tempMinute
	 * @param tempSecond
	 * @return
	 */
	
	public String toDBString(String tempMonth,String tempDay,String tempWeek,String tempHour,String tempMinute,String tempSecond){
		String customize = "";
		String quarte[] = new String[6];
		for(int i=0;i<quarte.length;i++){
			quarte[i] ="00";
		}
		if(null!=tempMonth &&  tempMonth.length()>0){
			quarte[0] = tempMonth;
		}
		if(null!=tempDay && tempDay.length()>0){
			quarte[1] = tempDay;
		}
		if(null!=tempWeek &&  tempWeek.length()>0){
			quarte[2] = tempWeek;
		}
		if(null!=tempHour &&  tempHour.length()>0){
			quarte[3] = tempHour;
		}
		if(null!=tempMinute &&  tempMinute.length()>0){
			quarte[4] = tempMinute;
		}
		if(null!=tempSecond &&  tempSecond.length()>0){
			quarte[5] = tempSecond;
		}
		for(int i=0;i<quarte.length;i++){
			customize = customize+quarte[i]+":";
		}
		return customize;
	}
    /**
	 * 00:00:00:00:00:00
	 * 获取频率
	 * 进行quzrtz规则定制
	 * @param frequency_sort
	 * @param every_sort
	 * @param detail_time
	 * @param timers
	 * @param customize_quartz
	 * @return
	 */
	public String makeQuartzRule(String frequency_sort,String every_sort,String tempMonth,String tempDay,String tempWeek,
			String tempHour,String tempMinute,String tempSecond,Date startTime,Date endTime){
		String ruleStr="";
		String[] temp =new String[6];
		for(int i=0;i<temp.length;i++){
			temp[i]="0";
		}
		if(null!=frequency_sort && frequency_sort.length()>0){
			if(frequency_sort.equals(Task.TASK_FREQUENCY_SORT_POINT)){//定点
				if(check(every_sort).equals(Task.TASK_EVERY_DAY)){//md
					if(null!=tempSecond && tempSecond.length()>0){
						temp[0]= tempSecond;
					}
					if(null!=tempMinute && tempMinute.length()>0){
						temp[1]= tempMinute;
					}
					temp[2]= tempHour;//0:0:0
					temp[3]="*";
					temp[4]="*";
					temp[5]="?";
					ruleStr = resultString(temp);
				}else if(check(every_sort).equals(Task.TASK_EVERY_WEEK)){//mz
					if(null!=tempSecond && tempSecond.length()>0){
						temp[0]= tempSecond;
					}
					if(null!=tempMinute && tempMinute.length()>0){
						temp[1]= tempMinute;
					}
					if(null!=tempHour && tempHour.length()>0){
						temp[2]= tempHour;//0:0:0
					}
					temp[3]="?";
					temp[4]="*";
					temp[5]=tempWeek;
					ruleStr = resultString(temp);
				}else if(check(every_sort).equals(Task.TASK_EVERY_MONTH)){//my
					if(null!=tempSecond && tempSecond.length()>0){
						temp[0]= tempSecond;
					}
					if(null!=tempMinute && tempMinute.length()>0){
						temp[1]= tempMinute;
					}
					if(null!=tempHour && tempHour.length()>0){
						temp[2]= tempHour;//0:0:0
					}
					temp[3]=tempDay;
					temp[4]="*";
					temp[5]="?";
					ruleStr = resultString(temp);
				}
			}else if(frequency_sort.equals(Task.TASK_FREQUENCY_SORT_INTERVAL)){//间隔 
				if(null!=tempSecond && tempSecond.length()>0){
					temp[0]= "0/"+tempSecond;
					temp[1]= "*";
					temp[2]= "*";
				}
				if(null!=tempMinute && tempMinute.length()>0){
					temp[0]= "0";
					temp[1]= "0/"+tempMinute;
					temp[2]= "*";
				}
				if(null!=tempHour && tempHour.length()>0){
					temp[0]= "0";
					temp[1]= "0";
					temp[2]= "0/"+tempHour;//0:0:0
				}
				temp[3]= "*";
				temp[4]= "*";
				temp[5]= "?";
				ruleStr = resultString(temp);
			}else if(frequency_sort.equals(Task.TASK_FREQUENCY_SORT_ONCE)){//次数 
				temp = seperateTime(startTime, endTime, "1",new String [6]);
				ruleStr = resultString(temp);
			}
		}
		return ruleStr;
	}
	/**
	 * 校验空置
	 * @param str
	 * @return
	 */
	public String check(String str){
		if(null!= str && str.trim().length()>0){
			return str;
		}else{
			return "";
		}
	}
	/**
	 * 结果返回字符串
	 * 最终表达式
	 * @param arr
	 * @return
	 */
	public String resultString(String arr[]){
		String str="";
		for(int i=0;i<arr.length;i++){
           str = str + arr[i]+" ";
		}
		str = str.substring(0,str.length()-1);
		return str;
	}
	
	/**
	 * 计算时间次数分成
	 */
	public static String [] seperateTime(Date startTime,Date endTime,String timers,String [] arr){
		Long  temp=0L;
		arr = new String[6];
		try{
	        temp = (endTime.getTime() - startTime.getTime())/(Integer.parseInt(timers));
	        Long day = temp/1000/60/60/24;
	        Long hour;
	        Long minnute;
	        if(day>0){
	        	arr[0] = Calendar.SECOND + "";
	        	arr[1] = Calendar.MINUTE + "";
	        	arr[2] = Calendar.HOUR_OF_DAY + "";
	        	arr[3] = "*";
	        	arr[4] = "0/"+day;
	        	arr[5] = "?";
	        }else{
	        	hour = (temp)/(1000*60*60);
	        	if(hour>0){
	        		arr[0] = "0";
		        	arr[1] = "0";
		        	arr[2] = "0/"+hour;
		        	arr[3] = "*";
		        	arr[4] = "*";
		        	arr[5] = "?";
	        	}else{
	        		minnute = (temp)/(1000*60);
	        		arr[0] = "0";
		        	arr[1] = "0/"+minnute;
		        	arr[2] = "*";
		        	arr[3] = "*";
		        	arr[4] = "*";
		        	arr[5] = "?";
	        	}
	        }
		}catch(Exception e){
		  e.printStackTrace();	
		}
		return arr;
	}
	/**
	 * 判断年份是否相同
	 * @return
	 */
	public static boolean isYearEquals(Date startTime,Date endTime){
		if(startTime.getYear()==endTime.getYear()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 将字符串转换成时间
	 * @param args
	 */
	public static Date stringToDate(String str)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date satrtDate = sf.parse(str);
        return satrtDate;
	}
	
	/**
	 * 将 11 11 11 * * ?格式转化为时分秒年月日
	 * @param taskFrequency
	 * @return
	 * @throws Exception
	 */
	public Task parse(String taskFrequency) throws Exception{
		if(taskFrequency != null && !"".equals(taskFrequency)) {
			String[] arr = taskFrequency.split(" ");
			for(int i=0;i<arr.length;i++) {
				
			}
		}
 		return null;
	}
	
	public static void main(String args[])throws Exception{
		//QuartzRuleUtil util = new QuartzRuleUtil();
		//System.out.println(util.betweenDay("2010-1-2","2010-2-2"));
		//System.out.println(seperateTime(stringToDate("2010-1-2"),stringToDate("2011-2-2"),"4"));
//		QuartzRuleUtil quar = new QuartzRuleUtil();
//		quar.makeQuartzRule("2", "1", "", "", "", "", "10", "", "", null, null, "");

		Long a = 2L;
		Long b = 3L;
		System.out.println(a/b);
//		String tempHour="21,";
//		if(tempHour.lastIndexOf(",")>0){tempHour=tempHour.substring(0,tempHour.length()-1);}
//		System.out.println(tempHour);  
	}
	
}
