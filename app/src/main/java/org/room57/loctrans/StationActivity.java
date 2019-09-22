package org.room57.loctrans;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

public class StationActivity extends AppCompatActivity {
    Fragment frag;

    private String stationCode;
    private String stationName;
    private String directions;
    private String destinationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        stationCode = extras.getString("StationCode");
        stationName = extras.getString("StationName");
        directions = extras.getString("IsDirections");
        if (directions.equals("true"))
            destinationCode = extras.getString("DestinationCode");

        frag = new StationFragment();
        frag.setArguments(extras);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag, "stationView").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

}
