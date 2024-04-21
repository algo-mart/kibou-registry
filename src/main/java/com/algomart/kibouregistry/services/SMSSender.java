package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.models.SMSRequest;

public interface SMSSender {
    void sendSMS(SMSRequest smsRequest);
}