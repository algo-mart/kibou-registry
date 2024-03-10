package com.algomart.kibouregistry.service.impl;

import com.algomart.kibouregistry.dao.AttendanceRepo;
import com.algomart.kibouregistry.dao.NotificationsRepo;
import com.algomart.kibouregistry.enums.AttendanceStatus;
import com.algomart.kibouregistry.enums.NotificationStatus;
import com.algomart.kibouregistry.enums.NotificationType;
import com.algomart.kibouregistry.model.Attendance;
import com.algomart.kibouregistry.model.Notifications;
import com.algomart.kibouregistry.model.Participants;
import com.algomart.kibouregistry.service.NotificationsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationsServiceImpl implements NotificationsService {

    private AttendanceRepo attendanceRepo;
    private NotificationsRepo notificationsRepo;

    @Transactional
    @Override
    public Notifications sendNotification(Notifications notification) {
        // Implement notification sending logic (e.g., send email or SMS)

        // Set notification date to current date
        notification.setDate(new Date());

        // Save the notification
        return notificationsRepo.save(notification);
    }

    public List<Notifications> getNotificationsByParticipantId(Participants participantId) {
        return notificationsRepo.findByParticipantId(participantId);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // Run at midnight every day
    public void processAttendanceNotifications() {
        // Get today's date
        LocalDate currentDate = LocalDate.now();

        // Define start and end dates for the period you want to check attendance
        LocalDate startDate = currentDate.minusDays(1); // Start date is yesterday

        // Retrieve attendance records for the specified period
        List<Attendance> attendanceRecords = attendanceRepo.findByDateBetween(startDate, currentDate);

        // Iterate through attendance records and generate notifications
        for (Attendance attendance : attendanceRecords) {
            // Check if participant is absent
            if (attendance.getStatus() == AttendanceStatus.ABSENT) {
                try {
                    // Generate notification content
                    String notificationContent = "Dear " + attendance.getParticipantId().getName() +
                            ", you were marked absent in today's meeting. Please ensure your attendance in the future.";

                    // Create and save notification
                    Notifications notification = new Notifications();
                    notification.setParticipantId(attendance.getParticipantId());
                    notification.setDate(new Date());
                    notification.setType(NotificationType.EMAIL); // Assuming email notification
                    notification.setType(NotificationType.SMS); // Assuming sms notification
                    notification.setStatus(NotificationStatus.SENT);
                    notification.setNotificationContent(notificationContent);
                    notificationsRepo.save(notification);

                    // Generate SMS notification

                    // Log the notification sent
                    logNotification(notification);
                } catch (Exception e) {
                    // Log any errors during notification sending
                    e.printStackTrace();
                    // Create and save failed notification
                    Notifications failedNotification = new Notifications();
                    failedNotification.setParticipantId(attendance.getParticipantId());
                    failedNotification.setDate(new Date());
                    failedNotification.setType(NotificationType.EMAIL); // Assuming email notification
                    failedNotification.setStatus(NotificationStatus.FAILED);
                    failedNotification.setNotificationContent("Failed to send notification for attendance on " +
                            attendance.getDate());
                    notificationsRepo.save(failedNotification);
                }
            }
        }
    }

    private void logNotification(Notifications notification) {
        // Log the details of the notification sent (participant, notification type, content, timestamp, etc.)
        System.out.println("Notification sent to participant " + notification.getParticipantId() +
                " on " + notification.getDate() + " - Content: " + notification.getNotificationContent());
    }


}
