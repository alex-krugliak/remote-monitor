package com.web.persistence;

import com.web.entity.ModuleCounter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by alex on 19.12.17.
 */
public interface ModuleCountersRepository extends JpaRepository<ModuleCounter, Long> {
}
