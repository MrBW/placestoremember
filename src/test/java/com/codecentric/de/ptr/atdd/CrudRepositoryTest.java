package com.codecentric.de.ptr.atdd;

import com.codecentric.de.ptr.Application;
import com.codecentric.de.ptr.domain.Place;
import com.codecentric.de.ptr.domain.PlaceRepository;
import com.codecentric.de.ptr.service.PlaceService;
import org.concordion.api.ResultSummary;
import org.concordion.internal.ConcordionBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CrudRepositoryTest {


    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

    @BeforeClass
    public static void configureConcordion() {
        System.setProperty("concordion.output.dir", "target/concordion");
    }

    @Test
    public final void runConcordion() throws IOException {
        ResultSummary summary = new ConcordionBuilder().build().process(this);
        summary.print(System.out, this);
        summary.assertIsSatisfied(this);
    }

    public void createPlace(String name, String lastVisitDate) {


        Date date = getDateFromString(lastVisitDate);

        Place place = new Place();
        place.setLastVisit(date);
        place.setName(name);

        placeRepository.save(place);


//        String uri = "http://localhost:8080/create";
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.postForObject(uri, place, String.class);
//
//        System.out.println(result);

    }

    private Date getDateFromString(String lastVisitDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;
        try {
            date = formatter.parse(lastVisitDate);

        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public long countPlaces() {
        return placeRepository.count();
    }

    public String findByNameAndReturnDaysSinceLastVisit(String name, String calcWithDate) {
        Place place = placeRepository.findByName(name);
        Date endDate = getDateFromString(calcWithDate);

        String daysSinceLastVisit = placeService.getDaysSinceLastVisit(place.getLastVisit(), endDate);

        return daysSinceLastVisit;
    }


}
