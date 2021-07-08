package com.gabia.slack.util;

import com.gabia.slack.dto.request.AlarmMessage;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;

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
}
