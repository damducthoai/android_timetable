package butchjgo.com.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.String;
import java.time.DayOfWeek;

/**
 * Created by thoai on 11/1/2017.
 */

public class DetailRepositoryImpl extends SQLiteOpenHelper implements DetailRepository {

    private static final String DB_NAME = "timetable2";
    private static final String TBL_NAME = "info";

    private static final int DB_VERSION = 1;

    private final String SCRIPT = "create table info(day_of_week varchar(255),slot_num varchar(11),subject_name varchar(255),location varchar(255),    note varchar(255),    is_active int(12),primary key (day_of_week, slot_num));";

    private final String QUERY_GET_INSTANCE = String.format("select * from %s where day_of_week = ? and slot_num = ?", TBL_NAME);

    private final String CONDITION = "day_of_week = ? AND slot_num = ?";

    public DetailRepositoryImpl(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void save(DetailModel detail) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("subject_name", detail.getSubject());
        contentValues.put("location", detail.getLocation());
        contentValues.put("note", detail.getNote());
        contentValues.put("is_active", detail.isActive() ? 1 : 0);


        SQLiteDatabase db = this.getReadableDatabase();

        if (get(detail.getDayOfWeek(), detail.getSlotNum()) == null) {

            contentValues.put("day_of_week", detail.getDayOfWeek().toString());
            contentValues.put("slot_num", detail.getSlotNum());

            db.insert(TBL_NAME, null, contentValues);
        } else {
            db.update(TBL_NAME, contentValues, CONDITION, new String[]{detail.getDayOfWeek().toString(), String.valueOf(detail.getSlotNum())});
        }
        //db.close();

    }

    @Override
    public DetailModel get(DayOfWeek dayOfWeek, int slotNum) {
        DetailModel detail = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_INSTANCE, new String[]{dayOfWeek.toString(), String.valueOf(slotNum)});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String subjectName = cursor.getString(2);
            String location = cursor.getString(3);
            String note = cursor.getString(4);
            boolean isActive = cursor.getInt(5) == 1 ? true : false;
            detail = new DetailModel(dayOfWeek, slotNum, subjectName, location, note, isActive);
        }
        //db.close();
        return detail;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("drop table if exists %s", DB_NAME));
        onCreate(sqLiteDatabase);
    }
}
