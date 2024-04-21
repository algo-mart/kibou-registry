package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.config.TwilioConfig;
import com.algomart.kibouregistry.models.SMSRequest;
import com.algomart.kibouregistry.services.SMSSender;
import com.algomart.kibouregistry.validation.PhoneNumberValidator;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("twilio")
@AllArgsConstructor
public class TwilioSMSSender implements SMSSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSMSSender.class);

    private final TwilioConfig twilioConfig;


    @Override
    public void sendSMS(SMSRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumbers())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumbers());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber()); // Using trial number
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            try {
                creator.create();
                LOGGER.info("Send sms {} " + smsRequest);
            } catch (ApiException e) {
                // Log the error message and rethrow the exception
                LOGGER.error("Error sending SMS: {}", e.getMessage());
                throw e;
            }
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumbers() + "] is not a valid number"
            );
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return PhoneNumberValidator.isValidPhoneNumber(phoneNumber);
    }
}

