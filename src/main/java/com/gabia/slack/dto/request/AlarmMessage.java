package com.gabia.slack.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class AlarmMessage {
    private Long userId;
    private String traceId;
    private Long groupId;
    private List<String> raws;
    private String title;
    private String content;

    @Builder
    public AlarmMessage(Long userId, String traceId, Long groupId, List<String> raws, String title, String content) {
        this.userId = userId;
        this.traceId = traceId;
        this.groupId = groupId;
        this.raws = raws;
        this.title = title;
        this.content = content;
    }
}