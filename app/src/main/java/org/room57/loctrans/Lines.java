package org.room57.loctrans;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class Lines {
    @CsvBindByName
    private String code;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String color;

    public String getCode() {
        return code;
    }
    public void setCode(String code) { this.code = code; }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getColor() {
        return color;
    }
    public void setColor(String color) { this.color = color; }
}
