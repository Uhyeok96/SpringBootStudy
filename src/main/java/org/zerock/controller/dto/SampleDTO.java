package org.zerock.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)  // 빌더 패턴을 사용함
public class SampleDTO {

    private Long sno;

    private String first;

    private String last;

    private LocalDateTime regTime;
}
