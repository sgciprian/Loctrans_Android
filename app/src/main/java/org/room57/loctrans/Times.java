package org.room57.loctrans;

import java.util.ArrayList;
import java.util.List;

public class Times {
    private String time;
    private Lines line;
    private String end;
    private List<String> stations;
    private ArrayList<String> stationTimes;
    private boolean reverse;

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public Lines getLine() { return line; }
    public void setLine(Lines line) { this.line = line; }

    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }

    public boolean isReverse() { return reverse; }
    public void setReverse(boolean reverse) { this.reverse = reverse; }

    public List<String> getStations() { return stations; }
    public void setStations(List<String> stations) { this.stations = stations; }

    public ArrayList<String> getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(ArrayList<String> stationTimes) {
        this.stationTimes = stationTimes;
    }
}
