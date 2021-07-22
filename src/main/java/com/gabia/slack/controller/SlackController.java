package com.gabia.slack.controller;

import com.gabia.slack.dto.response.APIResponse;
import com.gabia.slack.service.SlackService;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("slack-service")
public class SlackController {

    private final SlackService slackService;

    @GetMapping("/channels/{accessToken}")
    public ResponseEntity<?> getSlackChannels(@PathVariable String accessToken) {
        ConversationsListResponse conversationsListResponse = slackService.getChannels(accessToken);

        APIResponse response = APIResponse.builder()
                .message("Slack Channel 조회 완료")
                .result(conversationsListResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
