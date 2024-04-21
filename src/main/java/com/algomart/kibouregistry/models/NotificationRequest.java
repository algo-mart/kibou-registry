package com.algomart.kibouregistry.models;

import lombok.Data;

@Data
public class NotificationRequest {
    private EmailRequest emailRequest;
    private SMSRequest smsRequest;
}
