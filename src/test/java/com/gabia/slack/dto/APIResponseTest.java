package com.gabia.slack.dto;

import com.gabia.slack.dto.response.APIResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class APIResponseTest {

    @DisplayName("lombok 테스트")
    @Test
    void dto_test(){
        String message = "response message";
        Object object = new Object();

        APIResponse response = APIResponse.build(message, object);

        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getResult()).isEqualTo(object);
    }
}
