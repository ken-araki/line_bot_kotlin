package com.linebot.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class RestApiResponse {
    public RestApiResponse(String status) {
        this.status = status;
    }

    private String status;
}
