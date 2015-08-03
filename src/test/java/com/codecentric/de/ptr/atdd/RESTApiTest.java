package com.codecentric.de.ptr.atdd;

import com.codecentric.de.ptr.Application;
import com.codecentric.de.ptr.model.Place;
import com.codecentric.de.ptr.repository.PlaceRepository;
import com.codecentric.de.ptr.web.RestURIConstants;
import org.concordion.api.ResultSummary;
import org.concordion.internal.ConcordionBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class RESTApiTest {


    private static final String URI = "http://localhost:8080/";
    @Autowired
    private PlaceRepository placeRepository;

    @BeforeClass
    public static void configureConcordion() {

        System.setProperty("concordion.output.dir", "target/concordion");
    }

    @Before
    public void setUp() {
        placeRepository.deleteAll();
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

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(URI + RestURIConstants.PLACES_CREATE, place, String.class);

    }

    private Date getDateFromString(String lastVisitDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date date;
        try {
            date = formatter.parse(lastVisitDate);

        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public int countPlaces() {

        RestTemplate restTemplate = new RestTemplate();
        URI urlGETList = UriComponentsBuilder.fromUriString(URI)
                .path(RestURIConstants.PLACES_LIST)
                .build()
                .toUri();

        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(urlGETList, Object[].class);
        Object[] objects = responseEntity.getBody();
        //MediaType contentType = responseEntity.getHeaders().getContentType();
        //HttpStatus statusCode = responseEntity.getStatusCode();

        return objects.length;
    }

    public String findByNameAndReturnDaysSinceLastVisit(String name, String calcWithDate) {
        Date endDate = getDateFromString(calcWithDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        RestTemplate restTemplate = new RestTemplate();
        URI urlFindByName = UriComponentsBuilder.fromUriString(URI)
                .path(RestURIConstants.PLACES_FIND)
                .queryParam("name", name)
                .build()
                .toUri();

        Place place = restTemplate.getForObject(urlFindByName, Place.class);

        URI urlLastVisit = UriComponentsBuilder.fromUriString(URI)
                .path(RestURIConstants.PLACES_LASTVISIT)
                .queryParam("startDate", place.getLastVisit() == null ? "" : formatter.format(place.getLastVisit()))
                .queryParam("endDate", endDate == null ? "" : formatter.format(endDate))
                .build()
                .toUri();


        return restTemplate.getForObject(urlLastVisit, String.class);
    }


}
