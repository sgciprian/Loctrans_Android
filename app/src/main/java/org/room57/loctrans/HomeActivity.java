package org.room57.loctrans;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    MenuItem search_item;
    Fragment frag;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_lines:
                    loadFragmentByTag("lines");
                    return true;
                case R.id.navigation_stations:
                    loadFragmentByTag("stations");
                    return true;
                case R.id.navigation_directions:
                    loadFragmentByTag("directions");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_stations);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orar Loctrans");

        Values.setStopsList(getApplicationContext());

        loadFragmentByTag("stations");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    private void loadFragmentByTag(String tag) {
        frag = getSupportFragmentManager().findFragmentByTag(tag);
        if (frag == null){
            switch (tag) {
                case "lines":
                    frag = new AllLinesFragment();
                    break;
                case "stations":
                    frag = new AllStationsFragment();
                    break;
                case "directions":
                    frag = new DirectionsFragment();
                    break;
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag, tag).commit();
    }

}
