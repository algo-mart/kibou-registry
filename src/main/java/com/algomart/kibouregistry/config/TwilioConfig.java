package com.algomart.kibouregistry.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TwilioConfig {

    private String accountSid;
    private String authToken;
    private String trialNumber;
}
