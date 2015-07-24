package com.codecentric.de.ptr.web;

import com.codecentric.de.ptr.domain.Place;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by benjaminwilms on 23.07.15.
 */

@RestController
public class PlacesController {

    @RequestMapping(value = RestURIConstants.CREATE_PLACE, method = RequestMethod.POST)
    public String createPlace(@RequestBody Place place) {

        return HttpStatus.CREATED.toString();

    }


    @RequestMapping("/place")
    public ResponseEntity<Place> getPlaceJson() {

        Place p = new Place();
        p.setName("Codecentric");
        p.setLastVisit(new Date());


        return new ResponseEntity<Place>(p, HttpStatus.CREATED);
    }

}
