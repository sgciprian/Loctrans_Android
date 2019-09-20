package org.room57.loctrans;

import com.opencsv.bean.CsvBindByName;

public class Stations {
    private String code;
    private String name;
    private String time;
    private int type = 0;

    public Stations() {

    }

    public Stations(Stations st) {
        this.code = st.code;
        this.name = st.name;
        this.time = st.time;
        this.type = st.type;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
