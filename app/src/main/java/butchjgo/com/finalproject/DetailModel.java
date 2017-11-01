package butchjgo.com.finalproject;

import java.io.Serializable;
import java.time.DayOfWeek;

/**
 * Created by thoai on 11/1/2017.
 */

public class DetailModel implements Serializable{
    private DayOfWeek dayOfWeek;
    private SlotNum slotNum;
    private String subject, location;
    private boolean isActive;

    public DetailModel() {
    }

    public DetailModel(DayOfWeek dayOfWeek, SlotNum slotNum, String subject, String location, boolean isActive) {
        this.dayOfWeek = dayOfWeek;
        this.slotNum = slotNum;
        this.subject = subject;
        this.location = location;
        this.isActive = isActive;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public SlotNum getSlotNum() {
        return slotNum;
    }

    public void setSlotNum(SlotNum slotNum) {
        this.slotNum = slotNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
