package org.room57.loctrans;

import com.opencsv.bean.CsvBindByName;

public class Stations {
    @CsvBindByName
    private String code;

    @CsvBindByName
    private String name;

    private String time;

    private int type = 0;

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
