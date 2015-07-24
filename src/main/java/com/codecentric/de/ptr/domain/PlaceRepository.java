package com.codecentric.de.ptr.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by benjaminwilms on 23.07.15.
 */
public interface PlaceRepository extends CrudRepository<Place, Long> {

    Place findByName(String name);
}
