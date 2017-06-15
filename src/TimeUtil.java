import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengjunming_t on 2017/6/15 0015.
 */
public class TimeUtil {

    public static final SimpleDateFormat mYyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat mYyyyMM = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat mYyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    public static int diffDay(long currentTime, long preBillTime) {
        return (int) ((currentTimeDay(preBillTime) - currentTimeDay(currentTime)) / TimeConstants.DAY);
    }

    public static long monthPlusDay(int day, long currentTime) {
//        return currentTimeMonth(currentTime) + (long) (day - 1) * TimeConstants.DAY;
        Date date = new Date(currentTime);
        date.setDate(day);
        return date.getTime();
    }

    public static long currentTimeMonth(long currentTime) {
        try {
            return mYyyyMM.parse(mYyyyMMddHHmmss.format(currentTime).substring(0, 6)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public static long currentTimeDay(long currentTime) {
        try {
            return mYyyyMMdd.parse(mYyyyMMddHHmmss.format(currentTime).substring(0, 8)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public static long currentTimePlusMonth(long currentTime, int months) {
        Date date = new Date(currentTime);
        date.setMonth(date.getMonth() + months);
        return date.getTime();
    }

    public static long currentTimePlusDay(long currentTime, int days) {
        Date date = new Date(currentTime);
        date.setDate(date.getDate() + days);
        return date.getTime();
    }

}
