package com.gabia.slack.service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.model.Conversation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ChannelService {

    private MethodsClient client;

    public ChannelService() {
        this.client = Slack.getInstance().methods();
    }

    // 채널 가져오기
    public List<Conversation> getChannels(String accessToken) throws Exception {
        ConversationsListResponse response = getConversationsListResponse(accessToken);
        return response.getChannels();
    }

    private ConversationsListResponse getConversationsListResponse(String accessToken) throws Exception {
        ConversationsListResponse conversationsListResponse;

        try {
            conversationsListResponse = client.conversationsList(r ->
                    r.token(accessToken)
            );
            return conversationsListResponse;
        } catch (IOException | SlackApiException e) {
            throw new Exception(e.getMessage());
        }
    }
}
