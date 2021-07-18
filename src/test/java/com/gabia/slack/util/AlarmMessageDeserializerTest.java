package com.gabia.slack.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.slack.dto.request.AlarmMessage;
import org.apache.kafka.common.serialization.Serializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AlarmMessageDeserializerTest {

    class AlarmMessageSerializer implements Serializer<Object> {

        @Override
        public byte[] serialize(String topic, Object message) {
            byte[] data = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                data = objectMapper.writeValueAsString(message).getBytes();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }
    }


    private Long groupId = 1L;
    private Long userId = 1L;
    private String title = "title";
    private String content = "content";
    private String traceId = "abc";
    private List<String> receivers = Arrays.asList("C023WJKCPUM");

    private AlarmMessageSerializer alarmMessageSerializer = new AlarmMessageSerializer();
    private AlarmMessageDeserializer alarmMessageDeserializer = new AlarmMessageDeserializer();

    @Test
    void serialize_성공() {
        //given
        AlarmMessage message1 = AlarmMessage.builder()
                .userId(userId)
                .groupId(groupId)
                .traceId(traceId)
                .receivers(receivers)
                .title(title)
                .content(content)
                .build();

        //when
        byte[] serialize = alarmMessageSerializer.serialize("topic", message1);
        AlarmMessage message2 = alarmMessageDeserializer.deserialize("topic", serialize);

        //then
        Assertions.assertThat(message1.getContent()).isEqualTo(message2.getContent());
        Assertions.assertThat(message1.getTitle()).isEqualTo(message2.getTitle());
        Assertions.assertThat(message1.getGroupId()).isEqualTo(message2.getGroupId());
        Assertions.assertThat(message1.getTraceId()).isEqualTo(message2.getTraceId());
        Assertions.assertThat(message1.getUserId()).isEqualTo(message2.getUserId());
        Assertions.assertThat(message1.getReceivers().get(0)).isEqualTo(message2.getReceivers().get(0));
    }
}