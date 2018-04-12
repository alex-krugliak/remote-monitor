package com.web.persistence;

import com.web.entity.DayTotalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by alex on 02.04.18.
 */
@Service
public interface DayTotalRepository extends JpaRepository<DayTotalData, Long> {

    @Query("select t from DayTotalData t WHERE t.timeStamp = ?1")
    DayTotalData getTotalDataByDate(Date date);
}
