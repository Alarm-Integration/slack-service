package com.gabia.slack.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.gabia.slack.dto.request.AlarmMessage;
import com.gabia.slack.util.MemoryAppender;
import com.gabia.slack.util.SlackClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SlackServiceTest {

    @Mock
    private SlackClient client;

    @InjectMocks
    private SlackService service;

    private MemoryAppender memoryAppender;

    private String title = "제목";
    private String content = "내용";
    private String traceId = "abc";
    private Long userId = 1L;
    private Long groupId = 1L;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(SlackService.class);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    void 슬랙_알람_발송_성공() {

        // given
        List<String> raws = Arrays.asList("C023WJKCPUM");
        AlarmMessage alarmMessage = AlarmMessage.builder()
                .groupId(groupId)
                .raws(raws)
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        Message message = new Message();
        message.setType("message");
        message.setText(alarmMessage.getContent());
        response.setChannel(alarmMessage.getRaws().get(0));
        response.setMessage(message);
        response.setOk(true);

        when(client.sendAlarm(accessToken,
                alarmMessage.getRaws().get(0),
                alarmMessage)
        ).thenReturn(response);

        // when
        service.sendSlack(alarmMessage);

        // then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains(String.format("%s: userId:%s traceId:%s massage:%s massageId:%s",
                "SlackService", alarmMessage.getUserId(), alarmMessage.getTraceId(), "슬랙 발송 성공", response.getMessage().getText()), Level.INFO)).isTrue();

    }

    @Test
    void 슬랙_알람_발송_비정상_토큰_실패() {

        // given
        List<String> raws = Arrays.asList("C023WJKCPUM");
        AlarmMessage alarmMessage = AlarmMessage.builder()
                .groupId(groupId)
                .raws(raws)
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        Message message = new Message();
        message.setType("message");
        message.setText("invalid_auth");
        response.setMessage(message);
        response.setOk(false);

        when(client.sendAlarm(accessToken,
                alarmMessage.getRaws().get(0),
                alarmMessage)
        ).thenReturn(response);

        // when
        service.sendSlack(alarmMessage);

        // then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains(String.format("%s: userId:%s traceId:%s massage:%s massageId:%s",
                "SlackService", alarmMessage.getUserId(), alarmMessage.getTraceId(), "슬랙 발송 실패", response.getError()), Level.INFO)).isFalse();
    }

    @Test
    void 슬랙_알람_발송_존재하지_않는_채널_전송_실패() {

        // given
        List<String> raws = Arrays.asList("IS NOT A CHANNEL ID");
        AlarmMessage alarmMessage = AlarmMessage.builder()
                .groupId(groupId)
                .raws(raws)
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        String accessToken = "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        Message message = new Message();
        message.setType("message");
        message.setText("channel_not_found");
        response.setMessage(message);
        response.setOk(false);

        when(client.sendAlarm(accessToken,
                alarmMessage.getRaws().get(0),
                alarmMessage)
        ).thenReturn(response);

        // when
        service.sendSlack(alarmMessage);

        // then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains(String.format("%s: userId:%s traceId:%s massage:%s massageId:%s",
                "SlackService", alarmMessage.getUserId(), alarmMessage.getTraceId(), "슬랙 발송 실패", response.getError()), Level.INFO)).isFalse();
    }
}