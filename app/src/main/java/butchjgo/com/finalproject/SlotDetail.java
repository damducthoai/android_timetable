package butchjgo.com.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.time.DayOfWeek;

public class SlotDetail extends AppCompatActivity {

    DayOfWeek[] dayOfWeeks = DayOfWeek.values();
    DetailRepository repository;
    Integer[] slotNums = {1, 2, 3, 4, 5, 6, 7, 8};
    Spinner spDayOfWeek, spSlotNum;
    Intent mIntent;
    boolean shouldClose = false;
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateView();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            return;
        }
    };

    void saveData(View view) {
        int dayIndex = spDayOfWeek.getSelectedItemPosition();
        int slotIndex = spSlotNum.getSelectedItemPosition();

        DayOfWeek dayOfWeek = dayOfWeeks[dayIndex];
        int slotNum = slotNums[slotIndex];

        String subject = ((EditText) findViewById(R.id.txtSubject)).getText().toString();
        String location = ((EditText) findViewById(R.id.txtLocation)).getText().toString();
        String note = ((EditText) findViewById(R.id.txtNote)).getText().toString();
        boolean isActive = ((Switch) findViewById(R.id.activeStatus)).isChecked();
        DetailModel detail = new DetailModel(dayOfWeek, slotNum, subject, location, note, isActive);
        repository.save(detail);

        if (shouldClose) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    void updateView() {


        int dayIndex = spDayOfWeek.getSelectedItemPosition();
        int slotIndex = spSlotNum.getSelectedItemPosition();

        DayOfWeek dayOfWeek = dayOfWeeks[dayIndex];
        int slotNum = slotNums[slotIndex];

        DetailModel detail = repository.get(dayOfWeek, slotNum);

        TextView txtSub = findViewById(R.id.txtSubject);
        TextView txtLoc = findViewById(R.id.txtLocation);
        TextView txtNote = findViewById(R.id.txtNote);
        Switch aSwitch = findViewById(R.id.activeStatus);
        if (detail != null) {
            txtSub.setText(detail.getSubject());
            txtLoc.setText(detail.getLocation());
            txtNote.setText(detail.getNote());
            aSwitch.setChecked(detail.isActive());
        } else {
            txtSub.setText("");
            txtLoc.setText("");
            txtNote.setText("");
            aSwitch.setActivated(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detail");
        setContentView(R.layout.activity_slot_detail);

        repository = new DetailRepositoryImpl(this);

        spDayOfWeek = (Spinner) findViewById(R.id.spDayOfWeek);
        spSlotNum = findViewById(R.id.spSlotNum);

        ArrayAdapter<DayOfWeek> dayOfWeekArrayAdapter =
                new ArrayAdapter<DayOfWeek>
                        (this, android.R.layout.simple_spinner_dropdown_item, dayOfWeeks);

        ArrayAdapter<Integer> slotNumArrayAdapter =
                new ArrayAdapter<Integer>
                        (this, android.R.layout.simple_spinner_dropdown_item, slotNums);

        dayOfWeekArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slotNumArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDayOfWeek.setAdapter(dayOfWeekArrayAdapter);
        spSlotNum.setAdapter(slotNumArrayAdapter);

        spDayOfWeek.setOnItemSelectedListener(onItemSelectedListener);
        spSlotNum.setOnItemSelectedListener(onItemSelectedListener);

        mIntent = getIntent();

        DetailModel mDetail = ((DetailModel) mIntent.getSerializableExtra("item"));
        shouldClose = mIntent.getBooleanExtra("close", false);
        if (mDetail != null) {
            for (int i = 0; i < dayOfWeeks.length; i++) {
                if (dayOfWeeks[i] == mDetail.getDayOfWeek()) {
                    spDayOfWeek.setSelection(i);
                    break;
                }
            }
            spSlotNum.setSelection(mDetail.getSlotNum() - 1);
        }

        updateView();
    }
}
