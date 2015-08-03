package com.codecentric.de.ptr.service;

import com.codecentric.de.ptr.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by benjaminwilms on 29.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PlaceServiceTest {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    @Autowired
    private PlaceService placeService;

    @Test
    public void testGetDaysSinceLastVisit1Year5Months15Days() throws Exception {
        String expected = "1 year, 5 months and 15 days ago";
        Date toDate = formatter.parse("22/07/2015");
        Date fromDate = formatter.parse("07/02/2014");

        String daysSinceLastVisit = placeService.getDaysSinceLastVisit(fromDate, toDate);

        assertThat(daysSinceLastVisit, is(expected));
    }

    @Test
    public void testGetDaysSinceLastVisit28Days() throws Exception {
        String expected = "8 days ago";
        Date toDate = formatter.parse("22/07/2015");
        Date fromDate = formatter.parse("14/07/2015");

        String daysSinceLastVisit = placeService.getDaysSinceLastVisit(fromDate, toDate);

        assertThat(daysSinceLastVisit, is(expected));
    }

    @Test
    public void testGetDaysSinceLastVisitNULLNULL() throws Exception {
        String expected = "you have never been there";

        String daysSinceLastVisit = placeService.getDaysSinceLastVisit(null, null);

        assertThat(daysSinceLastVisit, is(expected));
    }

    @Test
    public void testGetDaysSinceLastVisitToDateIsNULL() throws Exception {
        String expected = "you have never been there";
        Date fromDate = formatter.parse("14/07/2015");

        String daysSinceLastVisit = placeService.getDaysSinceLastVisit(fromDate, null);

        assertThat(daysSinceLastVisit, is(expected));
    }

    @Test
    public void testGetDaysSinceLastVisitFromDateIsNULL() throws Exception {
        String expected = "you have never been there";
        Date toDate = formatter.parse("14/07/2015");

        String daysSinceLastVisit = placeService.getDaysSinceLastVisit(null, toDate);

        assertThat(daysSinceLastVisit, is(expected));
    }
}