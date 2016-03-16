package kindlereport.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static final String KINDLE_DATE_FORMAT = "yyyy-MM-dd";
	public static final Locale LOCALE = Locale.JAPAN;
	
	public static String getTodayByString() {
		SimpleDateFormat format = new SimpleDateFormat(KINDLE_DATE_FORMAT, LOCALE);
		return format.format(new Date());
	}

	public static String dateFormatter(String releaseDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.KINDLE_DATE_FORMAT, LOCALE);
		SimpleDateFormat returnSdf = new SimpleDateFormat("M/d(E)", LOCALE);
		Date date = null;

		try {
			date = sdf.parse(releaseDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return returnSdf.format(date);
	}
	
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(KINDLE_DATE_FORMAT, LOCALE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -3);

		return sdf.format(calendar.getTime());
	}
}
