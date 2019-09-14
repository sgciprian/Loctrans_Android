package org.room57.loctrans;

import java.util.ArrayList;
import java.util.List;

public final class Values {
    public static List<Stations> stationsList;

    public static void setStationsList(List<Stations> stationsList) {
        Values.stationsList = new ArrayList<>();
        Values.stationsList.addAll(stationsList);
    }
}
