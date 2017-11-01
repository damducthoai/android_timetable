package butchjgo.com.finalproject;

import java.time.DayOfWeek;

/**
 * Created by thoai on 11/1/2017.
 */

public interface DetailRepository {
    void save(DayOfWeek dayOfWeek, SlotNum slotNum);

    String get(DayOfWeek dayOfWeek, SlotNum slotNum);
}
