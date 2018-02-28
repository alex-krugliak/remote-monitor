package com.web.persistence;


import com.web.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created on 28.10.15.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select max(t) from Tag t")
    Tag findLastTag();

    @Query("select max(t) from Tag t WHERE t.connectionOk = true")
    Tag findLastTagConnectionOK();
}
