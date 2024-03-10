package com.algomart.kibouregistry.service.impl;//package com.alaoabdulhakeem.mvpproject.service.impl;
//
//import com.alaoabdulhakeem.mvpproject.service.SmsService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class SmsServiceImpl implements SmsService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);
//
//    private SmsProvider smsProvider; // Inject the SMS provider interface implementation
//
//    @Override
//    public void sendSMS(String phoneNumber, String message) throws SMSException {
//        try {
//            // Call the SMS provider to send the SMS
//            smsProvider.sendSMS(phoneNumber, message);
//        } catch (Exception e) {
//            // Log the exception
//            LOGGER.error("Failed to send SMS to: " + phoneNumber, e);
//
//            // Throw custom SMSException with a meaningful message
//            throw new SMSException("Failed to send SMS to: " + phoneNumber, e);
//        }
//    }
//}
