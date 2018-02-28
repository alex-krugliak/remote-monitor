package com.web.persistence;

import com.web.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 21.06.16.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}
