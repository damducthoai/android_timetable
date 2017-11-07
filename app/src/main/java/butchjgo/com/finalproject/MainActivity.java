package butchjgo.com.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private static final int LOGIN_CODE = 181;
    DayOfWeek[] dayOfWeeks = DayOfWeek.values();
    int curDay;
    DetailRepository repository = null;
    List<DetailModel> data = null;
    Intent mIntent = null;
    DayOfWeek activeDay = null;
    DetailListAdapter adapter;
    ListView listView;
    Spinner spDay;
    ArrayAdapter<DayOfWeek> dayOfWeekArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("Home");
        activeDay = LocalDate.now().getDayOfWeek();
        repository = new DetailRepositoryImpl(this);
        prepareData();
        LocalTime.now().getHour();
        mIntent = new Intent(this, SlotDetail.class);


        Intent notifiCationIn = new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, notifiCationIn, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = ((AlarmManager) getSystemService(ALARM_SERVICE));

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        spDay = findViewById(R.id.spDay);
        dayOfWeekArrayAdapter =
                new ArrayAdapter<DayOfWeek>
                        (this, android.R.layout.simple_spinner_dropdown_item, dayOfWeeks);


        spDay.setAdapter(dayOfWeekArrayAdapter);
        spDay.setSelection(curDay);
        for (int i = 0; i < dayOfWeeks.length; i++) {
            if (activeDay == dayOfWeeks[i]) {
                spDay.setSelection(i);
            }
        }
        spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activeDay = dayOfWeeks[position];
                prepareData();
                updateView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new DetailListAdapter(this, R.layout.time_table_view, data);
        listView = findViewById(R.id.listDetail);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailModel detail = data.get(position);
                mIntent.putExtra("item", detail);
                mIntent.putExtra("close", true);
                //MainActivity.this.startActivities(mIntents);
                MainActivity.this.startActivityForResult(mIntent, 1);
            }
        });
    }

    void prepareData() {
        if (data == null) {
            data = repository.getByDayOfWeek(activeDay);
        } else {
            data.clear();
            data.addAll(repository.getByDayOfWeek(activeDay));
        }
        return;
    }

    void updateView() {
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    void resetView() {
        prepareData();
        updateView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_CODE) {
            SharedPreferences sharedPreferences = getSharedPreferences("butchjgo.com.finalproject", MODE_PRIVATE);
            String user = sharedPreferences.getString("user", "");
            if (!user.isEmpty()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            resetView();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        resetView();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("butchjgo.com.finalproject", MODE_PRIVATE);
        String account = sharedPreferences.getString("user", "");
        if (account.isEmpty()) {
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.backup2Account).setVisible(false);
            menu.findItem(R.id.restoreFromAccount).setVisible(false);
            menu.findItem(R.id.logout).setVisible(false);
        } else {
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.backup2Account).setVisible(true);
            menu.findItem(R.id.restoreFromAccount).setVisible(true);
            menu.findItem(R.id.logout).setVisible(true);
        }

        boolean status = super.onPrepareOptionsMenu(menu);
        return status;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.edit:
                processAdd();
                break;
            case R.id.backup:
                repository.backup();
                break;
            case R.id.restore:
                repository.restore();
                resetView();
                break;
            case R.id.login:
                Intent intent = new Intent(this, Login.class);
                startActivityForResult(intent, LOGIN_CODE);
                break;
        }
        return true;
    }

    private void processAdd() {
        mIntent.putExtra("close", false);
        MainActivity.this.startActivity(mIntent);
    }

    public void doAdd(View view) {
        Spinner spinner = findViewById(R.id.spDay);
        DayOfWeek dayOfWeek = (DayOfWeek) spinner.getSelectedItem();
        DetailModel detail;

        if (data != null && data.size() > 0) {
            int index = data.size() == 8 ? 1 : data.size() + 1;
            detail = new DetailModel(dayOfWeek, index, "", "", "", true);
        } else {
            detail = new DetailModel(dayOfWeek, 1, "", "", "", true);
        }
        mIntent.putExtra("item", detail);
        processAdd();
    }
}
