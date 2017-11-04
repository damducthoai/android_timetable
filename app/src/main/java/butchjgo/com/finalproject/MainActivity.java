package butchjgo.com.finalproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DetailRepository repository;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new DetailRepositoryImpl(this);

        DayOfWeek dayOfWeek = DayOfWeek.of(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);

        List<DetailModel> data = repository.getByDayOfWeek(dayOfWeek);
        Intent intent = new Intent(this, SlotDetail.class);
        Intent[] intents = {intent};
        MainActivity.this.startActivities(intents);

    }


}
