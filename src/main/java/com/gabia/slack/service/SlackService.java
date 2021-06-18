package com.gabia.slack.service;

import com.gabia.slack.dto.request.SendSlackRequest;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SlackService {

    private MethodsClient client;

    public SlackService() {
        this.client = Slack.getInstance().methods();
    }

    @KafkaListener(topics = "slack", groupId = "slack", containerFactory = "consumerListener")
    public List<ChatPostMessageResponse> sendMessageToSlack(SendSlackRequest sendSlackRequest) throws Exception {
        List<ChatPostMessageResponse> responses = new ArrayList<>();

        try {
            for (String to : sendSlackRequest.getRaws()) {
                ChatPostMessageResponse result = client.chatPostMessage(r ->
                        r.token(sendSlackRequest.getAccessToken())
                                .channel(to)
                                .text(sendSlackRequest.getContent()));
                responses.add(result);
            }
            return responses;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
