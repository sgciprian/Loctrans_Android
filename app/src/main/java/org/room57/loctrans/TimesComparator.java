package org.room57.loctrans;

import org.room57.loctrans.Times;

import java.util.Comparator;

class TimesComparator implements Comparator<Times> {

    @Override
    public int compare(Times o1, Times o2) {
        return o1.getTime().compareTo(o2.getTime());
    }

}