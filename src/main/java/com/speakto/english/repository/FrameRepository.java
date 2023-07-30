package com.speakto.english.repository;

import com.speakto.english.model.FrameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FrameRepository extends CrudRepository<FrameEntity, Long> {
    Optional<FrameEntity> findFirstByNextTestLessThanEqualOrderByNextTest(LocalDateTime now);
}
