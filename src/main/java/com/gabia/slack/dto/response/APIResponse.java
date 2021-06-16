package com.gabia.slack.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class APIResponse {
    private String message;
    private Object result;

    public static APIResponse build(String message, Object result){
        return APIResponse.builder()
                .message(message)
                .result(result)
                .build();
    }

    @Builder
    public APIResponse(String message, Object result) {
        this.message = message;
        this.result = result;
    }
}
