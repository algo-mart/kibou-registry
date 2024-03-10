package com.algomart.kibouregistry.service;

public interface SmsService {
    void sendSMS(String phoneNumber, String message);
}
