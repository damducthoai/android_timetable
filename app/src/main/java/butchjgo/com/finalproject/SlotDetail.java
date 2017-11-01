package butchjgo.com.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.time.DayOfWeek;

public class SlotDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DayOfWeek[] dayOfWeeks = DayOfWeek.values();


    String[] test = {"abc", "def"};

    SlotNum[] slotNums = SlotNum.values();

    Gson gson = new Gson();

    Spinner spDayOfWeek, spSlotNum;

    void saveData(View view) {
        int dayIndex = spDayOfWeek.getSelectedItemPosition();
        int slotIndex = spSlotNum.getSelectedItemPosition();

        DayOfWeek dayOfWeek = dayOfWeeks[dayIndex];
        SlotNum slotNum = slotNums[slotIndex];

        String subject = ((EditText) findViewById(R.id.txtSubject)).getText().toString();
        String location = ((EditText) findViewById(R.id.txtLocation)).getText().toString();

        boolean isActive = ((Switch) findViewById(R.id.activeStatus)).isActivated();

        DetailModel detailModel = new DetailModel(dayOfWeek, slotNum, subject, location, isActive);

        SharedPreferences sharedPreferences =
                getSharedPreferences("butchjgo.com.finalproject", MODE_PRIVATE);

        String stData = gson.toJson(detailModel);
        sharedPreferences.edit().putString(dayOfWeek.toString() + slotNum.toString(), stData).apply();
    }

    void updateView() {
        int dayIndex = spDayOfWeek.getSelectedItemPosition();
        int slotIndex = spSlotNum.getSelectedItemPosition();

        DayOfWeek dayOfWeek = dayOfWeeks[dayIndex];
        SlotNum slotNum = slotNums[slotIndex];

        SharedPreferences sharedPreferences =
                getSharedPreferences("butchjgo.com.finalproject", MODE_PRIVATE);

        String stData = sharedPreferences.getString(dayOfWeek.toString() + slotNum.toString(), "");


        TextView txtSub = findViewById(R.id.txtSubject);
        TextView txtLoc = findViewById(R.id.txtLocation);

        if (!stData.isEmpty()) {
            DetailModel detailModel = gson.fromJson(stData, DetailModel.class);
            txtSub.setText(detailModel.getSubject());
            txtLoc.setText(detailModel.getLocation());
        } else {
            txtSub.setText("");
            txtLoc.setText("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_detail);

        spDayOfWeek = (Spinner) findViewById(R.id.spDayOfWeek);
        spSlotNum = findViewById(R.id.spSlotNum);

        ArrayAdapter<DayOfWeek> dayOfWeekArrayAdapter =
                new ArrayAdapter<DayOfWeek>
                        (this, android.R.layout.simple_spinner_dropdown_item, dayOfWeeks);

        ArrayAdapter<SlotNum> slotNumArrayAdapter =
                new ArrayAdapter<SlotNum>
                        (this, android.R.layout.simple_spinner_dropdown_item, slotNums);

        dayOfWeekArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slotNumArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDayOfWeek.setAdapter(dayOfWeekArrayAdapter);
        spSlotNum.setAdapter(slotNumArrayAdapter);

        spDayOfWeek.setOnItemSelectedListener(this);
        spSlotNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        updateView();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        return;
    }
}
