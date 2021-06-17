package com.gabia.slack.service;

import com.gabia.slack.dto.request.SendSlackRequest;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlackServiceTest {
    private static List<Conversation> validConversations, inValidConversations;
    private static ChatPostMessageResponse chatPostMessageResponse;
    private static SendSlackRequest sendSlackRequest;

    @Mock
    private MethodsClient client;

    @InjectMocks
    private SlackService service;

    @BeforeAll
    static void setup() {
        validConversations = new ArrayList<>() {
            {
                Conversation conversation = new Conversation();
                conversation.setId("C023WJRQTGV");
                conversation.setName("프로젝트");
                conversation.setCreated("1622766699");
                conversation.setCreator("U023ZT400HZ");
                add(conversation);
            }
        };

        inValidConversations = new ArrayList<>();

        chatPostMessageResponse = new ChatPostMessageResponse();

        chatPostMessageResponse.setOk(true);
        chatPostMessageResponse.setChannel("C023WJKCPUM");

        sendSlackRequest = new SendSlackRequest();
        sendSlackRequest.setAppName("Gabia Alarm Bot");
        sendSlackRequest.setRaws(new ArrayList<>(){
            {
                add("C023WJKCPUM");
            }
        });
        sendSlackRequest.setContent("알림 내용");
        sendSlackRequest.setAccessToken( "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS");
    }

    @DisplayName("유효한 토큰으로 메세지 전송 테스트")
    @Test
    void send_message_to_slack() throws Exception {
        client = Slack.getInstance().methods();
        client.setEndpointUrlPrefix("localhost");
//        when(Slack.getInstance().methods()).thenReturn(client);
//
//        when(client.chatPostMessage(r ->
//                r.token(sendSlackRequest.getAccessToken())
//                        .channel(sendSlackRequest.getRaws().get(0))
//                        .text(sendSlackRequest.getContent()))).thenReturn(chatPostMessageResponse);
//
//        service.sendMessageToSlack(sendSlackRequest);
    }
}