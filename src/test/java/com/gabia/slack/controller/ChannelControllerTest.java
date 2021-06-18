package com.gabia.slack.controller;

import com.gabia.slack.service.ChannelService;
import com.slack.api.model.Conversation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
class ChannelControllerTest {

    private static List<Conversation> validConversations, inValidConversations;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChannelService service;

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

    }

    @DisplayName("유효한 토큰 값으로 채널 들고오기 테스트")
    @Test
    void get_channels_valid_token_test() throws Exception {
        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";
        service = mock(ChannelService.class);
        given(service.getChannels(accessToken)).willReturn(validConversations);

//        this.mockMvc.perform(get("/channels")
//                .header("SLACK-TOKEN", accessToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").exists())
//                .andExpect(jsonPath("$.result").exists());
    }

    @DisplayName("유효하지 않은 토큰 값으로 채널 들고오기 에러 테스트")
    @Test
    void get_channels_invalid_token_test() throws Exception {
        String accessToken = "xoxb-in-valid-token";
        service = mock(ChannelService.class);
        given(service.getChannels(accessToken)).willReturn(null);

//        assertThrows(NullPointerException.class, () ->
//                this.mockMvc.perform(get("/channels")
//                        .header("SLACK-TOKEN", accessToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.message").exists())
//                        .andExpect(jsonPath("$.result").exists()));
    }
}