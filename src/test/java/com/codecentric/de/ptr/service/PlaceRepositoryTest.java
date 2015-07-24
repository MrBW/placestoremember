package com.codecentric.de.ptr.service;

import com.codecentric.de.ptr.Application;
import com.codecentric.de.ptr.domain.Place;
import com.codecentric.de.ptr.domain.PlaceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by benjaminwilms on 24.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;


    @Test
    public void createPlace() {
        Place p = new Place();
        p.setName("CREATE");
        p.setLastVisit(new Date());

        placeRepository.save(p);

        assertThat(placeRepository.count(), is(1L));

    }

    @Test
    public void removePlace() {
        Place p = new Place();
        p.setName("REMOVE");
        p.setLastVisit(new Date());

        Place saved = placeRepository.save(p);

        placeRepository.delete(saved);

        Place removed = placeRepository.findOne(saved.getId());

        assertThat(removed, is(nullValue()));


    }

}