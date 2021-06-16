package com.gabia.slack.dto;

import com.gabia.slack.dto.request.SendSlackRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SendSlackRequestTest {

    @DisplayName("lombok 테스트")
    @Test
    void dto_test(){
        String title = "알림 제목";
        String content = "알림 내용";

        String accessToken = "SLACK-TOKEN";
        String appName = "SLACK-BOT-NAME";

        List<String> raws = new ArrayList<>(){
            {
                add("T13DA561");
                add("U13DA561");
                add("C13DA561");
            }
        };

        SendSlackRequest request = new SendSlackRequest();
        request.setAccessToken(accessToken);
        request.setAppName(appName);
        request.setContent(content);
        request.setTitle(title);
        request.setRaws(raws);

        assertThat(request.getAccessToken()).isEqualTo(accessToken);
        assertThat(request.getAppName()).isEqualTo(appName);
        assertThat(request.getContent()).isEqualTo(content);
        assertThat(request.getTitle()).isEqualTo(title);
        assertThat(request.getRaws()).isEqualTo(raws);
    }
}
