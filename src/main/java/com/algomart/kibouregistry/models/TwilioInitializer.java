package com.algomart.kibouregistry.models;

import com.algomart.kibouregistry.config.TwilioConfig;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class TwilioInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioInitializer(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        try {
            Twilio.init(
                    twilioConfig.getAccountSid(),
                    twilioConfig.getAuthToken()
            );
            LOGGER.info("Twilio initialized with account sid: {}", twilioConfig.getAccountSid());
        } catch (Exception e) {
            LOGGER.error("Error initializing Twilio: {}", e.getMessage());
        }
    }
}
