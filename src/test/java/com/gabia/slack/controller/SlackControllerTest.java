package com.gabia.slack.controller;

import com.gabia.slack.service.SlackService;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.model.Conversation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static java.lang.String.format;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class SlackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SlackService slackService;

    @InjectMocks
    private SlackController slackController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(slackController).build();
    }

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("/slack-service%s", path));
    }


    /* Slack Channels 성공 요청 응답 결과
    {
      "message": "Slack Channel 조회 완료",
      "result": {
        "ok": true,
        "channels": [
            {
                "id": "C023WJKCPUM",
                "name": "랜덤",
                ...
            },
            ...
        }
    }
     */
    @Test
    void 유효한_토큰_Slack_채널_가져오기_성공() throws Exception {
        //given
        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";

        ConversationsListResponse response = new ConversationsListResponse();
        response.setOk(true);
        Conversation conversation = new Conversation();
        conversation.setId("C023WJKCPUM");
        conversation.setName("랜덤");
        response.setChannels(new ArrayList<>());
        response.getChannels().add(conversation);

        when(slackService.getChannels(accessToken)).thenReturn(response);

        //when
        ResultActions result = mockMvc.perform(get(uri("/channels/" + accessToken))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Slack Channel 조회 완료"))
                .andExpect(jsonPath("$.result.ok").value(true))
                .andExpect(jsonPath("$.result.channels[0].id").value("C023WJKCPUM"))
                .andExpect(jsonPath("$.result.channels[0].name").value("랜덤"));
    }

    /*
    {
        "ok": false,
        "error": "invalid_auth"
    }
    */
    @Test
    void 유효하지않은_토큰_Slack_채널_가져오기_실패() throws Exception {
        //given
        String accessToken = "INVALID-TOKEN";

        ConversationsListResponse response = new ConversationsListResponse();
        response.setOk(false);
        response.setError("invalid_auth");

        when(slackService.getChannels(accessToken)).thenReturn(response);

        //when
        ResultActions result = mockMvc.perform(get(uri("/channels/" + accessToken))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Slack Channel 조회 완료"))
                .andExpect(jsonPath("$.result.ok").value(false))
                .andExpect(jsonPath("$.result.error").value("invalid_auth"));
    }
}