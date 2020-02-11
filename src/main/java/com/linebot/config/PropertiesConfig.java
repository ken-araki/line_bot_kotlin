package com.linebot.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "app")
public class PropertiesConfig {
    private String id;
    private String ownHost;
    private String ownPort;

    // kotlinからgetterが見れないので、一時的にgetterを提供する
    public String getId() { return this.id; }
    public String getOwnHost() { return this.ownHost; }
    public String getOwnPort() { return this.ownPort; }
}
