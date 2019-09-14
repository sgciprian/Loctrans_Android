package org.room57.loctrans;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import org.room57.loctrans.TimeUtils;

import com.opencsv.CSVReader;

import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class StationActivity extends AppCompatActivity {
    private String stationCode;
    private String stationName;
    private List<Times> timesList = new ArrayList<>();
    private List<Times> shownTimesList = new ArrayList<>();
    private List<Times> validTimesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TimesAdapter tAdapter;
    private boolean black = true;
    private SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        stationCode = extras.getString("StationCode");
        stationName = extras.getString("StationName");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(stationName);

        ImageButton btn = (ImageButton) findViewById(R.id.imageButton);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageButton btn = (ImageButton) v;
                shownTimesList.clear();
                if (black) {
                    btn.setImageResource(R.drawable.ic_schedule_white_24dp);
                    shownTimesList.addAll(timesList);
                    black = false;
                }
                else {
                    btn.setImageResource(R.drawable.ic_schedule_black_24dp);
                    shownTimesList.addAll(validTimesList);
                    black = true;
                }
                tAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        processTimes();
        try {
            filterTimes(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        shownTimesList.addAll(validTimesList);

        tAdapter = new TimesAdapter(shownTimesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Times tm = shownTimesList.get(position);

                Intent i = new Intent(getBaseContext(), LineActivity.class);
                i.putExtra("LineCode", tm.getLine().getCode());
                i.putExtra("LineName", tm.getLine().getName());
                i.putExtra("LineColor", tm.getLine().getColor());
                i.putExtra("LineReverse", Boolean.toString(tm.isReverse()));
                i.putExtra("SourceStation", stationName);
                i.putExtra("CanReverseView", "false");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void processTimes() {
        timesList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("data/lines.csv")));
            reader.skip(1);
            for(;;) {
                String[] next = reader.readNext();
                if(next != null) {
                    Lines crLine = new Lines();
                    crLine.setCode(next[0]);
                    crLine.setName(next[1]);
                    crLine.setColor(next[2]);

                    String[] end;
                    if (TimeUtils.isLuniVineri(Calendar.getInstance())) {
                        end = new String[]{"_lv_t.csv", "_lv_r.csv"};
                    }
                    else {
                        end = new String[]{"_sd_t.csv", "_sd_r.csv"};
                    }

                    for (int term = 0; term < end.length; term++) {
                        String path = next[0] + end[term];
                        search:
                        try {
                            CSVReader reader2 = new CSVReader(new InputStreamReader(getAssets().open("data/" + path)));

                            String[] cr = reader2.readNext();

                            if (cr == null)
                                break;

                            String last = cr[cr.length - 1];

                            if (last.equals(stationCode))
                                break search;

                            int kth = -1;
                            for (int k = 0; k < cr.length; k++) {
                                if (cr[k].equals(stationCode))
                                    kth = k;
                            }

                            if (kth == -1)
                                break search;

                            for (;;) {
                                cr = reader2.readNext();
                                if (cr != null) {
                                    Times tm = new Times();
                                    tm.setTime(cr[kth]);
                                    tm.setLine(crLine);
                                    tm.setEnd(last);
                                    tm.setReverse(term != 0);
                                    timesList.add(tm);
                                } else {
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(timesList, new TimesComparator());
    }

    private void filterTimes(String crStrTime) throws ParseException {
        Times crTime = new Times();
        crTime.setTime(crStrTime);

        int index = Collections.binarySearch(timesList, crTime, new TimesComparator());

        if (index < 0) {
            index = -(index + 1);
        }

        if (index == timesList.size()) {
            validTimesList = timesList;
            return;
        }

        while(true) {
            if (index > 0 && DateUtils.addMinutes(df.parse(crStrTime), -10).before(df.parse(timesList.get(index).getTime())))
                index--;
            else
                break;
        }
        index++;

        validTimesList = new ArrayList<>(timesList.subList(index, timesList.size()));
    }
}