package com.gabia.slack.service;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.conversations.ConversationsListRequest;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.model.Conversation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.Silent.class)
class ChannelServiceTest {

    private static List<Conversation> validConversations, inValidConversations;
    private static ConversationsListResponse validResponse, inValidResponse;

    @Mock
    private MethodsClient client;

    @InjectMocks
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
        validResponse = new ConversationsListResponse();
        validResponse.setOk(true);
        validResponse.setChannels(validConversations);

        inValidConversations = new ArrayList<>();
        inValidResponse = new ConversationsListResponse();
        inValidResponse.setOk(false);
        inValidResponse.setChannels(inValidConversations);
    }

    @DisplayName("유효한 토큰으로 채널 가져오기 테스트")
    @Test
    void getConversations_from_slack_api_valid_token_test() throws Exception {
        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bSa";
        List<Conversation> a = new ArrayList<>() {
            {
                Conversation conversation = new Conversation();
                conversation.setId("C023WJRQTGV");
                conversation.setName("프로젝트");
                conversation.setCreated("1622766699");
                conversation.setCreator("U023ZT400HZ");
                add(conversation);
            }
        };
        ConversationsListResponse b = new ConversationsListResponse();
        b.setOk(true);
        b.setChannels(a);

        ConversationsListRequest mock = mock(ConversationsListRequest.class);
//        lenient().when(client.conversationsList(mock)).thenReturn(b);
//        List<Conversation> result = service.getChannels(accessToken);

//        for (int i = 0; i < result.size(); i++) {
//            Conversation conversation = result.get(i);
//            assertThat(conversation.getId()).isEqualTo(validConversations.get(i).getId());
//            assertThat(conversation.getName()).isEqualTo(validConversations.get(i).getName());
//            assertThat(conversation.getCreated()).isEqualTo(validConversations.get(i).getCreated());
//            assertThat(conversation.getCreator()).isEqualTo(validConversations.get(i).getCreator());
//        }
    }

    @Test
    void getConversationsFromSlackAPIInValidTokenTest() throws Exception {
        String accessToken = "xoxb-NOT-CORRECT-TOKEN";

//        given(service.getConversationsListResponse(accessToken).getChannels()).willReturn(inValidConversations);

//        List<Conversation> result = service.getChannels(accessToken);

//        assertThat(result.size()).isEqualTo(0);
//        assertThat(result).isEqualTo(inValidConversations);
    }

    @Test
    void getConversationsListResponseValidTokenTest() throws Exception {
        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";

        ConversationsListRequest mock = mock(ConversationsListRequest.class);
        mock.setToken(accessToken);

//        lenient().when(client.conversationsList(mock)).thenReturn(validResponse);

//        ConversationsListResponse result = service.getConversationsListResponse(accessToken);

//        assertThat(result.isOk()).isEqualTo(true);
//        assertThat(result.getChannels()).isEqualTo(validConversations);
    }

    @Test
    void getConversationsListResponseInValidTokenTest() throws Exception {
        String accessToken = "xoxb-NOT-CORRECT-TOKEN";

//        given(client.conversationsList(r -> r.token(accessToken))).willReturn(inValidResponse);

//        ConversationsListResponse result = service.getConversationsListResponse(accessToken);

//        assertThat(result.isOk()).isEqualTo(false);
//        assertThat(result.getChannels()).isEqualTo(inValidConversations);
    }
}