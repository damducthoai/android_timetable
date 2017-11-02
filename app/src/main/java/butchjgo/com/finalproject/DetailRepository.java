package butchjgo.com.finalproject;

import java.time.DayOfWeek;

/**
 * Created by thoai on 11/1/2017.
 */

public interface DetailRepository {
    void save(DetailModel detail);

    DetailModel get(DayOfWeek dayOfWeek, int slotNum);
}
