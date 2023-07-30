package com.speakto.english.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "frames")
@Data
public class FrameEntity {
    @Id
    private Long id;
    private String en;
    private String pt;
    @Column(name = "next_test")
    private LocalDateTime nextTest;
    @Column(name = "num_right_answer")
    private Integer minutesToNextTest;
}
