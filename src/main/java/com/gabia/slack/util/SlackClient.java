package com.gabia.slack.util;

import com.gabia.slack.dto.request.AlarmMessage;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SlackClient {

    public ChatPostMessageResponse sendAlarm(String accessToken, String channelId, AlarmMessage alarmMessage) {
        MethodsClient client = Slack.getInstance().methods();

        ChatPostMessageResponse response = new ChatPostMessageResponse();
        try {
            response = client.chatPostMessage(r ->
                    r.token(accessToken)
                            .channel(channelId)
                            .text(alarmMessage.getContent()));
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
            response.setOk(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ConversationsListResponse getChannels(String accessToken) {
        MethodsClient client = Slack.getInstance().methods();

        ConversationsListResponse response = new ConversationsListResponse();
        try {
            response = client.conversationsList(r -> r.token(accessToken));
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
            response.setOk(false);
            response.setError(e.getMessage());
        }
        return response;
    }
}
