package com.gabia.slack.service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@ActiveProfiles("test")
public class ChannelServiceTests {

    @Test
    void 슬랙_채널_받아오기_정상_토큰_결과_테스트() throws SlackApiException, IOException {
        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";

        MethodsClient client = Slack.getInstance().methods();
        ConversationsListResponse conversationsListResponse = client.conversationsList(r -> r.token(accessToken));

        System.out.println(conversationsListResponse);
    }

    @Test
    void 슬랙_채널_받아오기_비정상_토큰_결과_테스트() throws SlackApiException, IOException {
        String accessToken = "not-a-token";

        MethodsClient client = Slack.getInstance().methods();
        ConversationsListResponse conversationsList = client.conversationsList(r -> r.token(accessToken));

        System.out.println(conversationsList);
    }
}
