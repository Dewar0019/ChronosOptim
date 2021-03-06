package com.example.dewartan.chronosoptim;

/**
 * This is an individualized event view
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class EventDisplayActivity extends AppCompatActivity {
    private Toolbar actionBarToolBar;
    TextView title, location, description, date, toolbarTitle, subtitle, time;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_event);

        //Get the Object
        Event eventOnDay = this.getIntent().getParcelableExtra("viewEvent");

        title = (TextView) findViewById(R.id.title);
        location = (TextView) findViewById(R.id.location);
        description = (TextView) findViewById(R.id.description);
        date = (TextView) findViewById(R.id.date);
        subtitle = (TextView) findViewById(R.id.subtitle);
        time = (TextView) findViewById(R.id.time);


        actionBarToolBar = (Toolbar) findViewById(R.id.empty_bar);
        setSupportActionBar(actionBarToolBar);
        toolbarTitle = (TextView) actionBarToolBar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (eventOnDay != null){
            title.setText(eventOnDay.getTitle());
            toolbarTitle.setText(eventOnDay.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            location.setText(eventOnDay.getLocation());
            description.setText(eventOnDay.getDescription());
            date.setText(EventDate.getDayOfWeek(eventOnDay));
            subtitle.setText(eventOnDay.getSubtitle());
            time.setText(eventOnDay.getStartTime()+"-"+eventOnDay.getEndTime());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

