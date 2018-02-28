package com.web.persistence;

import com.web.entity.MaterialExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created on 10/16/16.
 */
public interface MaterialExpenditureRepository extends JpaRepository<MaterialExpenditure, Long> {
}
