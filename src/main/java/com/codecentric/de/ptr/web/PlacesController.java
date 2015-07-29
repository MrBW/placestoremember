package com.codecentric.de.ptr.web;

import com.codecentric.de.ptr.dto.PlaceDTO;
import com.codecentric.de.ptr.model.Place;
import com.codecentric.de.ptr.repository.PlaceRepository;
import com.codecentric.de.ptr.service.PlaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by benjaminwilms on 23.07.15.
 */

@RestController
public class PlacesController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

    @RequestMapping(value = RestURIConstants.PLACES_CREATE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createPlace(@RequestBody Place place) {
        placeRepository.save(place);

        return HttpStatus.CREATED.toString();

    }

    @RequestMapping(value = RestURIConstants.PLACES_FIND, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceDTO> findPlaceByName(@RequestParam(value = "name", required = true) String name) {
        // required = true
        if (StringUtils.isEmpty(name))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        else {
            Place place = placeRepository.findByName(name);

            if (place == null)
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            else {
                // Map to DTO
                PlaceDTO placeDTO = mapToPlaceDTO(place);

                return new ResponseEntity<PlaceDTO>(placeDTO, HttpStatus.FOUND);
            }
        }
    }

    @RequestMapping(value = RestURIConstants.PLACES_LASTVISIT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLastVisit(@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate, @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

        String daysSinceLastVisit = placeService.getDaysSinceLastVisit(startDate, endDate);

        return new ResponseEntity<String>(daysSinceLastVisit, HttpStatus.OK);
    }


    private PlaceDTO mapToPlaceDTO(Place placeEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(placeEntity, PlaceDTO.class);

    }
}
