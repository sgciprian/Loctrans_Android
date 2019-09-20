package org.room57.loctrans;

import android.content.Context;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Values {
    public static List<Stations> stationsList;

    public static Map<String, Stations> stopsList;

    public static void setStationsList(List<Stations> stationsList) {
        Values.stationsList = new ArrayList<>();
        Values.stationsList.addAll(stationsList);
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
