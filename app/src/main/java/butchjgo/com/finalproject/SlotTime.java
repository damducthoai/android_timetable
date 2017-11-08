package butchjgo.com.finalproject;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by root on 11/8/2017.
 */

public class SlotTime {
    private final long MIN2MILISECON = 1 * 60 * 1000;
    private final long HOUR2MIN = 1 * 60;
    private final long HOUR2MILISECON = HOUR2MIN * MIN2MILISECON;
    private final long DAY2MILISECON = 24 * HOUR2MILISECON;
    private Map<Integer, Long> TIME = new LinkedHashMap<>();

    public SlotTime() {
        TIME.put(1, 7 * HOUR2MILISECON);
        TIME.put(2, 8 * HOUR2MILISECON + 45 * MIN2MILISECON);
        TIME.put(3, 10 * HOUR2MILISECON + 30 * MIN2MILISECON);
        TIME.put(4, 12 * HOUR2MILISECON + 30 * MIN2MILISECON);
        TIME.put(5, 14 * HOUR2MILISECON + 15 * MIN2MILISECON);
        TIME.put(6, 16 * HOUR2MILISECON);
        TIME.put(7, 17 * HOUR2MILISECON + 45 * MIN2MILISECON);
        TIME.put(8, 19 * HOUR2MILISECON + 30 * MIN2MILISECON);
    }

    public Map<Integer, Long> getTIME() {
        return TIME;
    }

    public long getMIN2MILISECON() {
        return MIN2MILISECON;
    }

    public long getHOUR2MIN() {
        return HOUR2MIN;
    }

    public long getHOUR2MILISECON() {
        return HOUR2MILISECON;
    }

    public long getDAY2MILISECON() {
        return DAY2MILISECON;
    }

    public long getCurMilisecond() {
        Calendar now = Calendar.getInstance();
        long seconds = now.get(Calendar.MILLISECOND);
        return seconds;
    }

}
