package com.codecentric.de.ptr.service;

import com.codecentric.de.ptr.Application;
import com.codecentric.de.ptr.model.Place;
import com.codecentric.de.ptr.repository.PlaceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by benjaminwilms on 24.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PlaceRepositoryIntegrationTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Before
    public void setUp() {

        placeRepository.deleteAll();
    }


    @Test
    public void testCreatePlace() {
        Place p = new Place();
        p.setName("CREATE");
        p.setLastVisit(new Date());

        placeRepository.save(p);

        assertThat(placeRepository.count(), is(1L));

    }

    @Test
    public void testRemovePlace() {
        Place p = new Place();
        p.setName("REMOVE");
        p.setLastVisit(new Date());

        Place saved = placeRepository.save(p);

        placeRepository.delete(saved);

        Place removed = placeRepository.findOne(saved.getId());

        assertThat(removed, is(nullValue()));


    }

    @Test
    public void testFindByName() {
        String searchForName = "findByName";
        Place p = new Place();
        p.setName(searchForName);
        p.setLastVisit(new Date());

        placeRepository.save(p);

        assertThat(placeRepository.findByName(searchForName), is(notNullValue()));
    }

    @Test
    public void testUpdatePlace() {
        String firstName = "FIRST_NAME";
        String newName = "NEW_NAME";

        Place p = new Place();
        p.setName(firstName);

        // First save
        placeRepository.save(p);

        //Find Place
        Place firstByName = placeRepository.findByName(firstName);

        // Change name
        firstByName.setName(newName);

        // Save with new Name
        placeRepository.save(firstByName);

        // Double check new Name and old origin ID
        Place newByName = placeRepository.findByName(newName);

        // Check new name
        assertThat(newByName.getName(), is(newName));
        assertThat(newByName.getId(), is(firstByName.getId()));


    }

}