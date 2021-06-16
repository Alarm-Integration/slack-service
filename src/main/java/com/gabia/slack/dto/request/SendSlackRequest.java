package com.gabia.slack.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SendSlackRequest {
    private String title;
    private String content;

    private String accessToken;
    private String appName;

    private List<String> raws;
}
