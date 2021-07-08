package com.gabia.slack.service;

import com.gabia.slack.dto.request.AlarmMessage;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SlackService {

    private MethodsClient client;

    public SlackService() {
        this.client = Slack.getInstance().methods();
    }

    @KafkaListener(topics = "slack", groupId = "slack", containerFactory = "kafkaListenerContainerFactory")
    public void sendSlack(AlarmMessage alarmMessage) {
        String accessToken = getAccessToken(alarmMessage.getGroupId());

        for (String channelId : alarmMessage.getRaws()) {
            try {
                ChatPostMessageResponse response = client.chatPostMessage(r ->
                        r.token(accessToken)
                                .channel(channelId)
                                .text(alarmMessage.getContent())
                );
                if(response.isOk()) {
                    log.info("{}: userId:{} traceId:{} massage:{} massageId:{}",
                            getClass().getSimpleName(),
                            alarmMessage.getUserId(),
                            alarmMessage.getTraceId(),
                            "슬랙 발송 성공",
                            response.getMessage());
                } else{
                    log.error("{}: userId:{} traceId:{} massage:{} error:{}",
                            getClass().getSimpleName(),
                            alarmMessage.getUserId(),
                            alarmMessage.getTraceId(),
                            "슬랙 발송 실패",
                            response.getError());
                }
            } catch (IOException | SlackApiException e) {
                e.printStackTrace();
                log.error("{}: userId:{} traceId:{} massage:{} error:{}",
                        getClass().getSimpleName(),
                        alarmMessage.getUserId(),
                        alarmMessage.getTraceId(),
                        "슬랙 발송 실패",
                        e.getMessage());
            }
        }
    }

    private String getAccessToken(Long groupId) {
        //todo: 그룹 서비스에서 groupId로 accessToken 가져오기
        return "xoxb-2148325514801-2142207279172-ttsneJk3GUgXqkw3dtPPK5bS";
    }
}
