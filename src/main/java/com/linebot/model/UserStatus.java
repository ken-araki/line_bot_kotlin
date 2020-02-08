package com.linebot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@NoArgsConstructor
public class UserStatus implements Serializable {
    public UserStatus(String userId, String nextAction) {
        this.userId = userId;
        this.nextAction = nextAction;
    }

    private String userId;
    private String nextAction;
}
