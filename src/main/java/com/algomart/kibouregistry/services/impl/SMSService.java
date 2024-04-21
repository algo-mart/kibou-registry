package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.models.SMSRequest;
import com.algomart.kibouregistry.services.SMSSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class SMSService {

    private final SMSSender smsSender;

    @Autowired
    public SMSService(@Qualifier("twilio") TwilioSMSSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSMS(SMSRequest smsRequest)  {
        smsSender.sendSMS(smsRequest);
    }
}
