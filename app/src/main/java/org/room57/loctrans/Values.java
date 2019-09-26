package org.room57.loctrans;

import android.content.Context;
import android.content.SharedPreferences;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Values {
    public static List<Stations> stationsList;

    public static Map<String, Stations> stopsList;

    public static CharSequence[] displaySettingsText = {"Luminos", "Întunecat", "În funcție de setarea economisire baterie"};
    public static int displaySetting = 2;

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;

    public static void setStationsList(Context context) {
        stationsList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open("data/stations.csv")));
            for(;;) {
                String[] next = reader.readNext();
                if(next != null) {
                    Stations nw = new Stations();
                    nw.setCode(next[0]);
                    nw.setName(next[1]);
                    stationsList.add(nw);
                } else {
                    break;
                }
            }
            stationsList.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(stationsList, new Comparator<Stations>() {
            @Override
            public int compare(Stations o1, Stations o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public static void setStopsList(Context context) {
        stopsList = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open("data/stations.csv")));
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
}
