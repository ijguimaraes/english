package com.speakto.english.repository;

import com.speakto.english.model.FrameEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FrameRepository extends CrudRepository<FrameEntity, Long> {
    @Query(nativeQuery = true, value = "select * from frames f where next_test <= :now order by next_test, id limit 1")
    Optional<FrameEntity> findNextTest(@Param("now") LocalDateTime now);
}
