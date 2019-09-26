package org.room57.loctrans;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Fragment frag;
    Fragment lastFrag;

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

        Intent intent = getIntent();
        if (intent.getExtras() != null && intent.getExtras().getString("fragmentTag") != null) {
            switch (intent.getExtras().getString("fragmentTag")) {
                case "lines":
                    navigation.setSelectedItemId(R.id.navigation_lines);
                    break;
                case "stations":
                    navigation.setSelectedItemId(R.id.navigation_stations);
                    break;
                case "directions":
                    navigation.setSelectedItemId(R.id.navigation_directions);
                    break;
            }
        }
        else
            navigation.setSelectedItemId(R.id.navigation_stations);

        loadSettings();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orar Loctrans");

        Values.setStopsList(getApplicationContext());
        Values.setStationsList(getApplicationContext());

        if (intent.getExtras() != null && intent.getExtras().getString("fragmentTag") != null) {
            loadFragmentByTag(intent.getExtras().getString("fragmentTag"));
        }
        else
            loadFragmentByTag("stations");
    }

    private void loadSettings() {
        Values.pref = getApplicationContext().getSharedPreferences("Pref", 0);

        Values.displaySetting = Values.pref.getInt("display setting", 2);
        AppCompatDelegate.setDefaultNightMode(Values.displaySetting + 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.display:
                DialogFragment newFragment = new DisplaySettingsDialog();
                newFragment.show(getSupportFragmentManager(), "display settings");
                return true;
            case R.id.help:
                return true;
            case R.id.about:
                Toast.makeText(getApplicationContext(), "Orar Loctrans, versiunea 0.3", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadFragmentByTag(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        frag = manager.findFragmentByTag(tag);
        if (frag == null){
            switch (tag) {
                case "lines":
                    frag = new AllLinesFragment();
                    transaction.add(R.id.fragment, frag, tag);
                    break;
                case "stations":
                    frag = new AllStationsFragment();
                    transaction.add(R.id.fragment, frag, tag);
                    break;
                case "directions":
                    frag = new DirectionsFragment();
                    transaction.add(R.id.fragment, frag, tag);
                    break;
            }
        }

        if (lastFrag == null)
            lastFrag = frag;
        else {
            transaction.hide(lastFrag);
            lastFrag = frag;
        }

        transaction.show(frag);
        transaction.commit();
    }

}
