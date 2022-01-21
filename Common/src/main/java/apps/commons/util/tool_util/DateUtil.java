package apps.commons.util.tool_util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 日期通用工具类
 * @author ZJL
 * @date 2019年9月18日 下午6:46:43
 */
public class DateUtil {

	
	/** 毫秒 */
	public final static long MS = 1;
	/** 每秒钟的毫秒数 */
	public final static long SECOND_MS = MS * 1000;
	/** 每分钟的毫秒数 */
	public final static long MINUTE_MS = SECOND_MS * 60;
	/** 每小时的毫秒数 */
	public final static long HOUR_MS = MINUTE_MS * 60;
	/** 每天的毫秒数 */
	public final static long DAY_MS = HOUR_MS * 24;

	/** 标准日期格式 */
	public final static String NORM_DATE_PATTERN = "yyyy-MM-dd";
	/** 标准时间格式 */
	public final static String NORM_TIME_PATTERN = "HH:mm:ss";
	/** 标准日期时间格式，精确到分 */
	public final static String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
	/** 标准日期时间格式，精确到秒 */
	public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 标准日期时间格式，精确到毫秒 */
	public final static String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	/** HTTP头中日期时间格式 */
	public final static String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
	
	
	/**
	 * 获取YYYY格式
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 获取YYYY格式
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 */
	public static String getDay() {
		return formatDate(new Date(), NORM_DATE_PATTERN);
	}

	/**
	 * 获取YYYY-MM-DD格式
	 */
	public static String getDay(Date date) {
		return formatDate(date, NORM_DATE_PATTERN);
	}

	/**
	 * 获取YYYYMMDD格式
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 获取YYYYMMDD格式
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 */
	public static String getTime() {
		return formatDate(new Date(), NORM_DATETIME_PATTERN);
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 */
	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 */
	public static String getTime(Date date) {
		return formatDate(date, NORM_DATETIME_PATTERN);
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = "";
		if (pattern != null && !pattern.isEmpty()) {
			formatDate = format(date, pattern);
		} else {
			formatDate = format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 日期比较，如果s>=e 返回true 否则返回false)
	 *
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 */
	public static Date parseDate(String date) {
		return parse(date, NORM_DATE_PATTERN);
	}

	/**
	 * 格式化日期
	 */
	public static Date parseTimeMinutes(String date) {
		return parse(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 格式化日期
	 */
	public static Date parseTime(String date) {
		return parse(date, NORM_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期
	 */
	public static Date parse(String date, String pattern) {
		SimpleDateFormat sdfd = new SimpleDateFormat(pattern);
		try {
			return sdfd.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 格式化日期
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdfd = new SimpleDateFormat(pattern);
		return sdfd.format(date);
	}


	public static Date parseFormat(String format){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = dateFormat.parse(format);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * 时间戳格式化日期
	 */
	public static String formatTime(Long time) {
		String dateStr = "";
		if (time != null) {
			if (time.toString().length() == 10) {
				dateStr = format(new Date(time * 1000), "yyyy-MM-dd HH:mm:ss");
			} else if (time.toString().length() == 13) {
				dateStr = format(new Date(time), "yyyy-MM-dd HH:mm:ss");
			}
		}
		return dateStr;
	}
	
	/**
	 * 把日期转换为Timestamp
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 */
	public static boolean isValidDate(String s) {
		return parse(s, NORM_DATETIME_PATTERN) != null;
	}

	/**
	 * 校验日期是否合法
	 */
	public static boolean isValidDate(String s, String pattern) {
		return parse(s, pattern) != null;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat(NORM_DATE_PATTERN);
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(NORM_DATE_PATTERN);
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat(NORM_DATETIME_PATTERN);
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 计算两个日期的时间差，精确到分
	 * 
	 * @param d1
	 *            后
	 * @param d2
	 *            前
	 * @return
	 */
	public static String diffDate(Date d1, Date d2) {
		String result = "";
		try {
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60);
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60))
					/ (1000 * 60);
			long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
			if(days > 0){
				result += days + "天";
			}
			if(hours > 0){
				result += hours + "小时";
			}
			if(minutes > 0){
				result += minutes + "分";
			}
			if(seconds > 0){
				result += seconds + "秒";
			}
			//System.out.println(result);
			return result;
		} catch (Exception e) {
			return result;
		}
	}

	/**
	 * 计算两个日期的时间差，精确到分
	 * 
	 * @param d1s
	 *            后 "yyyy-MM-dd HH:mm:ss"
	 * @param d2s
	 *            前 "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String diffDate(String d1s, String d2s) {
		DateFormat df = new SimpleDateFormat(NORM_DATETIME_PATTERN);
		String result = "";
		try {
			Date d1 = df.parse(d1s);
			Date d2 = df.parse(d2s);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60);
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60))
					/ (1000 * 60);
			long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
			if(days > 0){
				result += days + "天";
			}
			if(hours > 0){
				result += hours + "小时";
			}
			if(minutes > 0){
				result += minutes + "分";
			}
			if(seconds > 0){
				result += seconds + "秒";
			}
			
			//System.out.println(result);
			return result;
		} catch (Exception e) {
			return result;
		}
	}
	
	/**
	 * 计算两个日期的时间差，精确到毫秒
	 * 
	 * @param d1s
	 *            后 "yyyy-MM-dd HH:mm:ss"
	 * @param d2s
	 *            前 "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static long getDiffTime(String d1s, String d2s){
		DateFormat df = new SimpleDateFormat(NORM_DATETIME_PATTERN);
		try {
			Date d1 = df.parse(d1s);
			Date d2 = df.parse(d2s);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			return diff;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 格式：<br>
	 * 1、yyyy-MM-dd HH:mm:ss<br>
	 * 2、yyyy-MM-dd<br>
	 * 3、HH:mm:ss<br>
	 * 4、yyyy-MM-dd HH:mm 5、yyyy-MM-dd HH:mm:ss.SSS
	 *
	 * @param dateStr 日期字符串
	 * @return 日期
	 */
	public static Date parse(String dateStr) {
		if (null == dateStr) {
			return null;
		}
		dateStr = dateStr.trim();
		int length = dateStr.length();
		try {
			if (length == NORM_DATETIME_PATTERN.length()) {
				return parseTime(dateStr);
			} else if (length == NORM_DATE_PATTERN.length()) {
				return parseDate(dateStr);
			} else if (length == NORM_TIME_PATTERN.length()) {
				return parse(dateStr, NORM_TIME_PATTERN);
			} else if (length == NORM_DATETIME_MINUTE_PATTERN.length()) {
				return parseTimeMinutes(dateStr);
			} else if (length >= NORM_DATETIME_MS_PATTERN.length() - 2) {
				return parse(dateStr, NORM_DATETIME_MS_PATTERN);
			}
		} catch (Exception e) {
			throw new ToolBoxException(StrKit.format("Parse [{}] with format normal error!", dateStr));
		}

		// 没有更多匹配的时间格式
		throw new ToolBoxException(StrKit.format(" [{}] format is not fit for date pattern!", dateStr));
	}
	// ------------------------------------ Parse end ----------------------------------------------

	// ------------------------------------ Offset start ----------------------------------------------
	/**
	 * 获取某天的开始时间
	 *
	 * @param date 日期
	 * @return 某天的开始时间
	 */
	public static Date getBeginTimeOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取某天的结束时间
	 *
	 * @param date 日期
	 * @return 某天的结束时间
	 */
	public static Date getEndTimeOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 昨天
	 *
	 * @return 昨天
	 */
	public static Date yesterday() {
		return offsiteDay(new Date(), -1);
	}

	/**
	 * 上周
	 *
	 * @return 上周
	 */
	public static Date lastWeek() {
		return offsiteWeek(new Date(), -1);
	}

	/**
	 * 上个月
	 *
	 * @return 上个月
	 */
	public static Date lastMouth() {
		return offsiteMonth(new Date(), -1);
	}

	/**
	 * 偏移天
	 *
	 * @param date 日期
	 * @param offsite 偏移天数，正数向未来偏移，负数向历史偏移
	 * @return 偏移后的日期
	 */
	public static Date offsiteDay(Date date, int offsite) {
		return offsiteDate(date, Calendar.DAY_OF_YEAR, offsite);
	}

	/**
	 * 偏移周
	 *
	 * @param date 日期
	 * @param offsite 偏移周数，正数向未来偏移，负数向历史偏移
	 * @return 偏移后的日期
	 */
	public static Date offsiteWeek(Date date, int offsite) {
		return offsiteDate(date, Calendar.WEEK_OF_YEAR, offsite);
	}

	/**
	 * 偏移月
	 *
	 * @param date 日期
	 * @param offsite 偏移月数，正数向未来偏移，负数向历史偏移
	 * @return 偏移后的日期
	 */
	public static Date offsiteMonth(Date date, int offsite) {
		return offsiteDate(date, Calendar.MONTH, offsite);
	}

	/**
	 * 获取指定日期偏移指定时间后的时间
	 *
	 * @param date 基准日期
	 * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
	 * @param offsite 偏移量，正数为向后偏移，负数为向前偏移
	 * @return 偏移后的日期
	 */
	public static Date offsiteDate(Date date, int calendarField, int offsite) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarField, offsite);
		return cal.getTime();
	}
	// ------------------------------------ Offset end ----------------------------------------------

	/**
	 * 判断两个日期相差的时长<br/>
	 * 返回 minuend - subtrahend 的差
	 * 
	 * @param subtrahend 减数日期
	 * @param minuend 被减数日期
	 * @param diffField 相差的选项：相差的天、小时
	 * @return 日期差
	 */
	public static long diff(Date subtrahend, Date minuend, long diffField) {
		long diff = minuend.getTime() - subtrahend.getTime();
		return diff / diffField;
	}

	/**
	 * 计时，常用于记录某段代码的执行时间，单位：纳秒
	 * 
	 * @param preTime 之前记录的时间
	 * @return 时间差，纳秒
	 */
	public static long spendNt(long preTime) {
		return System.nanoTime() - preTime;
	}

	/**
	 * 计时，常用于记录某段代码的执行时间，单位：毫秒
	 * 
	 * @param preTime 之前记录的时间
	 * @return 时间差，毫秒
	 */
	public static long spendMs(long preTime) {
		return System.currentTimeMillis() - preTime;
	}

	/**
	 * 格式化成yyMMddHHmm后转换为int型
	 * 
	 * @param date 日期
	 * @return int
	 */
	public static int toIntSecond(Date date) {
		return Integer.parseInt(format(date, "yyMMddHHmm"));
	}

	/**
	 * 计算指定指定时间区间内的周数
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 周数
	 */
	public static int weekCount(Date start, Date end) {
		final Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		final Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);

		final int startWeekofYear = startCalendar.get(Calendar.WEEK_OF_YEAR);
		final int endWeekofYear = endCalendar.get(Calendar.WEEK_OF_YEAR);

		int count = endWeekofYear - startWeekofYear + 1;

		if (Calendar.SUNDAY != startCalendar.get(Calendar.DAY_OF_WEEK)) {
			count--;
		}

		return count;
	}

	/**
	 * 生日转为年龄，计算法定年龄
	 * @param birthDay 生日，标准日期字符串
	 * @return 年龄
	 * @throws Exception
	 */
	public static int ageOfNow(String birthDay) {
		return ageOfNow(parse(birthDay));
	}

	/**
	 * 生日转为年龄，计算法定年龄
	 * @param birthDay 生日
	 * @return 年龄
	 * @throws Exception
	 */
	public static int ageOfNow(Date birthDay) {
		return age(birthDay, new Date());
	}
	
	/**
	 * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
	 * @param birthDay 生日
	 * @param dateToCompare 需要对比的日期
	 * @return 年龄
	 * @throws Exception
	 */
	public static int age(Date birthDay, Date dateToCompare) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToCompare);

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(StrKit.format("Birthday is after date {}!", getTime(dateToCompare)));
		}

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int age = year - cal.get(Calendar.YEAR);
		
		int monthBirth = cal.get(Calendar.MONTH);
		if (month == monthBirth) {
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < dayOfMonthBirth) {
				//如果生日在当月，但是未达到生日当天的日期，年龄减一
				age--;
			}
		} else if (month < monthBirth){
			//如果当前月份未达到生日的月份，年龄计算减一
			age--;
		}

		return age;
	}

    public static String  specialTime(Date date){
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return df2.format(date1);

    }


}
