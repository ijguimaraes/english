package com.speakto.english.controller;

import com.speakto.english.controller.request.NextFrameBody;
import com.speakto.english.controller.response.FrameResponse;
import com.speakto.english.model.FrameEntity;
import com.speakto.english.repository.FrameRepository;
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
                        frameEntity.setMinutesToNextTest(frameEntity.getMinutesToNextTest() * 2);
                        frameEntity.setNextTest(
                                LocalDateTime.now().plusMinutes(frameEntity.getMinutesToNextTest())
                        );
                    } else {
                        frameEntity.setMinutesToNextTest(5);
                        frameEntity.setNextTest(
                                LocalDateTime.now()
                        );
                    }
                    frameRepository.save(frameEntity);
                    return frameEntity;
                });
    }
}
