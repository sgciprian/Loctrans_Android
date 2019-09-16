package org.room57.loctrans;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.opencsv.CSVReader;

import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StationFragment extends Fragment {
    private String stationCode;
    private String stationName;
    private String filter;
    private String directions;
    private String destinationCode;

    private List<Times> timesList = new ArrayList<>();
    private List<Times> shownTimesList = new ArrayList<>();
    private List<Times> shownTimesListCopy = new ArrayList<>();
    private List<Times> validTimesList = new ArrayList<>();

    private RecyclerView recyclerView;
    private TimesAdapter tAdapter;

    private boolean black = true;
    private boolean filtered = false;

    private SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_station, null);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Times tm = shownTimesList.get(position);

                Intent i = new Intent(getActivity().getBaseContext(), LineActivity.class);
                i.putExtra("LineCode", tm.getLine().getCode());
                i.putExtra("LineName", tm.getLine().getName());
                i.putExtra("LineColor", tm.getLine().getColor());
                i.putExtra("LineReverse", Boolean.toString(tm.isReverse()));
                i.putExtra("SourceStation", stationName);
                i.putExtra("CanReverseView", "false");
                i.putStringArrayListExtra("TimesList", tm.getStationTimes());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle extras = getArguments();
        assert extras != null;
        stationCode = extras.getString("StationCode");
        stationName = extras.getString("StationName");
        directions = extras.getString("IsDirections");
        if (directions.equals("true"))
            destinationCode = extras.getString("DestinationCode");

        super.onCreate(savedInstanceState);

        if (directions.equals("true") || directions.equals("false")) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(stationName);
            modifyToolbar(toolbar);
        }

        processTimes();
        try {
            filterTimes(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        shownTimesList.addAll(validTimesList);
        shownTimesListCopy.addAll(validTimesList);

        Log.d("Times", Integer.toString(shownTimesList.size()));

        tAdapter = new TimesAdapter(shownTimesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private void setListeners(Toolbar toolbar) {
        ImageButton btn = (ImageButton) toolbar.findViewById(R.id.scheduleButton);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageButton btn = (ImageButton) v;
                shownTimesList.clear();
                shownTimesListCopy.clear();

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

                shownTimesListCopy.addAll(shownTimesList);
                if (filtered) {
                    tAdapter.filter(filter, stationCode, shownTimesListCopy);
                }
                tAdapter.notifyDataSetChanged();
            }
        });

        btn = (ImageButton) toolbar.findViewById(R.id.filterButton);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final ImageButton btn = (ImageButton) v;
                if (!filtered) {
                    stationSearchDialog st = new stationSearchDialog(getContext());
                    st.show();
                    st.setDialogResult(new stationSearchDialog.OnMyDialogResult(){
                        public void finish(String result){
                            if (result != null) {
                                filtered = true;
                                filter = result;
                                tAdapter.filter(result, stationCode, shownTimesListCopy);
                                btn.setImageResource(R.drawable.ic_gps_off_black_24dp);
                            }
                        }
                    });
                }
                else {
                    filtered = false;
                    filter = "";
                    tAdapter.filter("", "", shownTimesListCopy);
                    btn.setImageResource(R.drawable.ic_gps_not_fixed_black_24dp);
                }
                tAdapter.notifyDataSetChanged();
            }
        });

    }

    private void modifyToolbar(Toolbar toolbar) {
        if (directions.equals("false"))
            setListeners(toolbar);
        else {
            ImageButton btn = (ImageButton) toolbar.findViewById(R.id.scheduleButton);
            btn.setVisibility(View.GONE);

            btn = (ImageButton) toolbar.findViewById(R.id.filterButton);
            btn.setVisibility(View.GONE);

            tAdapter.filter(destinationCode, stationCode, shownTimesListCopy);
        }
    }

    private void processTimes() {
        timesList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getActivity().getAssets().open("data/lines.csv")));
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
                            CSVReader reader2 = new CSVReader(new InputStreamReader(getActivity().getAssets().open("data/" + path)));

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

                            String[] header = cr;
                            for (;;) {
                                cr = reader2.readNext();
                                if (cr != null) {
                                    Times tm = new Times();
                                    tm.setTime(cr[kth]);
                                    tm.setLine(crLine);
                                    tm.setEnd(last);
                                    tm.setReverse(term != 0);
                                    tm.setStations(Arrays.asList(header));
                                    tm.setStationTimes(new ArrayList<String>(Arrays.asList(cr)));
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
