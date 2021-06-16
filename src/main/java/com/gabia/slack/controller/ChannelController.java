package com.gabia.slack.controller;

import com.gabia.slack.dto.response.APIResponse;
import com.gabia.slack.service.ChannelService;
import com.slack.api.model.Conversation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/channels")
public class ChannelController {
    private ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public ResponseEntity<?> getChannels(@RequestHeader("SLACK-TOKEN") String token) throws Exception {
        List<Conversation> result = channelService.getChannels(token);

        APIResponse response = APIResponse.build(
                String.format("%d 개의 채널목록을 조회 했습니다.", result.size()),
                result);

        return ResponseEntity.ok(response);
    }
}
