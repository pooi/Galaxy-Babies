package com.samsung.galaxy_babies.obj;

import java.util.Calendar;

public class Measure {
    Calendar date;
    double value;

    public Measure(Calendar date, double value) {
        this.date = date;
        this.value = value;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
