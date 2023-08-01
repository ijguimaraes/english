package com.speakto.english.controller;

import com.speakto.english.controller.request.NextFrameBody;
import com.speakto.english.controller.response.FrameResponse;
import com.speakto.english.model.FrameEntity;
import com.speakto.english.repository.FrameRepository;
import com.speakto.english.service.NextTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController()
@RequestMapping("/api/frames")
public class FrameController {
    @Autowired
    private FrameRepository frameRepository;

    @PostMapping("next")
    public FrameResponse post(@RequestBody NextFrameBody body) {
        if (validateBody(body)) {
            updateFrame(body);
        }
        return getNextFrame();
    }

    private static boolean validateBody(NextFrameBody body) {
        return body.getId() != null && body.getAnswer() != null;
    }

    private FrameResponse getNextFrame() {
        return frameRepository.findFirstByNextTestLessThanEqualOrderByNextTest(LocalDateTime.now())
                .map(f -> FrameResponse.builder()
                        .id(f.getId())
                        .en(f.getEn())
                        .pt(f.getPt())
                        .build()
                ).orElse(FrameResponse.builder().build());
    }

    private Optional<FrameEntity> updateFrame(NextFrameBody body) {
        return frameRepository.findById(body.getId())
                .map(frameEntity -> {
                    if (body.getAnswer()) {
                        if (frameEntity.getMinutesToNextTest() <= 10) {
                            frameEntity.setMinutesToNextTest(NextTime.get30Minutes());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.get30Minutes()) {
                            frameEntity.setMinutesToNextTest(NextTime.getAnHour());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getAnHour()) {
                            frameEntity.setMinutesToNextTest(NextTime.getAhDay());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getAhDay()) {
                            frameEntity.setMinutesToNextTest(NextTime.getTwoDays());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getTwoDays()) {
                            frameEntity.setMinutesToNextTest(NextTime.getFourDays());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getFourDays()) {
                            frameEntity.setMinutesToNextTest(NextTime.getAhWeek());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getAhWeek()) {
                            frameEntity.setMinutesToNextTest(NextTime.getTwoWeek());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getTwoWeek()) {
                            frameEntity.setMinutesToNextTest(NextTime.getAhMonth());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getAhMonth()) {
                            frameEntity.setMinutesToNextTest(NextTime.getTwoMonth());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getTwoMonth()) {
                            frameEntity.setMinutesToNextTest(NextTime.getThreeMonth());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getThreeMonth()) {
                            frameEntity.setMinutesToNextTest(NextTime.getSixMonth());
                        } else if (frameEntity.getMinutesToNextTest() <= NextTime.getSixMonth()) {
                            frameEntity.setMinutesToNextTest(NextTime.getAhYear());
                        } else {
                            frameEntity.setMinutesToNextTest(NextTime.getAhYear());
                        }
                        frameEntity.setNextTest(
                                LocalDateTime.now().plusMinutes(frameEntity.getMinutesToNextTest())
                        );
                    } else {
                        frameEntity.setMinutesToNextTest(10L);
                        frameEntity.setNextTest(
                                LocalDateTime.now()
                        );
                    }
                    frameRepository.save(frameEntity);
                    return frameEntity;
                });
    }
}
