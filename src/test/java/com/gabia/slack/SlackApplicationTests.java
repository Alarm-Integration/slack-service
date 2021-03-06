package com.gabia.slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SlackApplicationTests {

    @Test
    void 슬랙_전송_실제_테스트() throws SlackApiException, IOException {
        MethodsClient client = Slack.getInstance().methods();

        ChatPostMessageResponse res = client.chatPostMessage(r ->
                r.token("require-token")
                        .channel("C023WJKCPUM")
                        .text("Hello"));


        // 실제 토큰이 아니면 auth 없다고 실패, 따라서 실제 토큰을 넣으면 실제 알람 전송.
        assertThat(res.isOk()).isFalse();
        assertThat(res.getError()).isEqualTo("invalid_auth");
    }
}

