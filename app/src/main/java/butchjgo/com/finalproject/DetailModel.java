package butchjgo.com.finalproject;

import java.io.Serializable;
import java.time.DayOfWeek;

/**
 * Created by thoai on 11/1/2017.
 */

public class DetailModel implements Serializable {
    private DayOfWeek dayOfWeek;
    private int slotNum;
    private String subject, location,note;
    private boolean isActive;

    public DetailModel(DayOfWeek dayOfWeek, int slotNum, String subject, String location, String note, boolean isActive) {
        this.dayOfWeek = dayOfWeek;
        this.slotNum = slotNum;
        this.subject = subject;
        this.location = location;
        this.note = note;
        this.isActive = isActive;
    }

    public DetailModel() {
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getSlotNum() {
        return slotNum;
    }

    public void setSlotNum(int slotNum) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
