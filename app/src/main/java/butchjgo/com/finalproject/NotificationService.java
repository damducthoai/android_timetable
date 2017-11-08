package butchjgo.com.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 11/8/2017.
 */

public class NotificationService extends Service {
    SlotTime slotTime = new SlotTime();
    Intent mIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mIntent = intent;
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long miliBefore = 10 * 60 * 1000;
        long cur = slotTime.getCurMilisecond();
        List<Long> prepareTime = new LinkedList<>();

        Collection<Long> seconds = slotTime.getTIME().values();

        for (long second : seconds) {
            if (second > cur) {
                prepareTime.add(second - miliBefore);
            } else {
                prepareTime.add(second + slotTime.getDAY2MILISECON() - miliBefore);
            }
        }

        for (long waitInt : prepareTime) {
            Intent notifiCationIn = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.
                    getBroadcast(getApplicationContext(),
                            100,
                            notifiCationIn,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = ((AlarmManager) getSystemService(ALARM_SERVICE));

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, waitInt, AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
