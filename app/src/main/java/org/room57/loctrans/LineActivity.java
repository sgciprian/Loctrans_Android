package org.room57.loctrans;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class LineActivity extends AppCompatActivity {
    private String lineCode;
    private String lineName;
    private String lineColor;
    private String isReverse;
    private String sourceStation;
    private String canReverseView;

    private Map<String, Stations> stopsList;
    private List<Stations> shownStopsList = new ArrayList<>();
    private List<String> stopCodes;
    private RecyclerView recyclerView;
    private StationsAdapter sAdapter;
    private ArrayList<String> timesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        assert extras != null;

        lineCode = extras.getString("LineCode");
        lineName = extras.getString("LineName");
        lineColor = extras.getString("LineColor");
        isReverse = extras.getString("LineReverse");
        sourceStation = extras.getString("SourceStation");
        canReverseView = extras.getString("CanReverseView");
        timesList = extras.getStringArrayList("TimesList");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton btn = (ImageButton) findViewById(R.id.imageButton);
        if (canReverseView.equals("false"))
            btn.setVisibility(View.GONE);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isReverse.equals("true"))
                    isReverse = "false";
                else
                    isReverse = "true";

                setTitleBar();

                getStops();
                processStops();
                filterStops((ArrayList<String>) stopCodes);
                sAdapter.notifyDataSetChanged();
            }
        });

        setTitleBar();

        int bgColor = ContextCompat.getColor(getApplicationContext(), getResources().getIdentifier(lineColor, "color", getPackageName()));
        toolbar.setBackgroundColor(bgColor);
        int bgColorDark = ContextCompat.getColor(getApplicationContext(), getResources().getIdentifier(lineColor + "Dark", "color", getPackageName()));
        getWindow().setStatusBarColor(bgColorDark);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        getStops();
        processStops();
        filterStops((ArrayList<String>) stopCodes);

        sAdapter = new StationsAdapter(shownStopsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Stations station = shownStopsList.get(position);

                Intent i = new Intent(getBaseContext(), StationActivity.class);
                i.putExtra("StationCode", station.getCode());
                i.putExtra("StationName", station.getName());
                i.putExtra("IsDirections", "false");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setTitleBar() {
        if (isReverse.equals("false"))
            getSupportActionBar().setTitle(lineName + " Tur");
        else
            getSupportActionBar().setTitle(lineName + " Retur");
    }

    private void getStops() {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("data/lines.csv")));
            reader.skip(1);
            for(;;) {
                String[] next = reader.readNext();
                if(next != null) {
                    if (next[0].equals(lineCode)) {
                        String end;
                        if (isReverse.equals("false")) {
                            if (TimeUtils.isLuniVineri(Calendar.getInstance()))
                                end = "_lv_t.csv";
                            else
                                end = "_sd_t.csv";
                        }
                        else {
                            if (TimeUtils.isLuniVineri(Calendar.getInstance()))
                                end = "_lv_r.csv";
                            else
                                end = "_sd_r.csv";
                        }
                        String path = next[0] + end;
                        try {
                            CSVReader reader2 = new CSVReader(new InputStreamReader(getAssets().open("data/" + path)));
                            String[] header = reader2.readNext();
                            if (header == null)
                                stopCodes = new ArrayList<>();
                            else
                                stopCodes = new ArrayList<>(Arrays.asList(header));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processStops() {
        stopsList = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("data/stations.csv")));
            reader.skip(1);
            for(;;) {
                String[] next = reader.readNext();
                if(next != null) {
                    Stations nw = new Stations();
                    nw.setCode(next[0]);
                    nw.setName(next[1]);
                    stopsList.put(nw.getCode(), nw);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterStops(ArrayList<String> stopCodes) {
        shownStopsList.clear();
        for (int i = 0; i < stopCodes.size(); i++) {
            Stations stop = stopsList.get(stopCodes.get(i));
            if (timesList != null)
                stop.setTime(timesList.get(i));
            if (stop != null ) {
                if (stop.getName().equals(sourceStation))
                    stop.setType(1);
                shownStopsList.add(stop);
            }
        }
    }
}
