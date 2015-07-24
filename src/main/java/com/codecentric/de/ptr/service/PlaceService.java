package com.codecentric.de.ptr.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by benjaminwilms on 24.07.15.
 */
@Service
public class PlaceService {


    public String getDaysSinceLastVisit(Date fromDate, Date toDate) {
        StringBuilder returnString = new StringBuilder();

        if (fromDate == null | toDate == null)
            return "you have never been there";

        LocalDate startDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Period period = Period.between(startDate, endDate);


        // Years
        if (period.getYears() > 0) {
            returnString.append(period.getYears());
            if (period.getYears() == 1)
                returnString.append(" year");
            else
                returnString.append("years");
        }

        // Month
        if (period.getMonths() > 0) {
            // years already appended
            if (period.getYears() > 0)
                returnString.append(", ");

            returnString.append(period.getMonths());
            if (period.getMonths() == 1)
                returnString.append(" month");
            else
                returnString.append(" months");
        }

        // Days
        if (period.getDays() > 0) {
            // month already appended
            if (period.getMonths() > 0)
                returnString.append(" and ");

            returnString.append(period.getDays());
            if (period.getDays() == 1)
                returnString.append(" day");
            else
                returnString.append(" days");
        }


        // Finally
        returnString.append(" ago");


        return returnString.toString();
    }

}
