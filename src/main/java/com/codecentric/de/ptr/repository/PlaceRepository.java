package com.codecentric.de.ptr.repository;

import com.codecentric.de.ptr.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by benjaminwilms on 23.07.15.
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findByName(String name);
}
