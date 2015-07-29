package com.codecentric.de.ptr.dto;

/**
 * Created by benjaminwilms on 28.07.15.
 */
public class PeriodDTO {

    private int years;
    private int months;
    private int days;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
