# Project Documentation: NGO Participant Management System

## Introduction

This document outlines the development plan for a Minimum Viable Product (MVP) for an NGO Participant Management System. The system aims to streamline the management of participant data, track attendance, manage daily financial disbursements, and facilitate communication through automated notifications. This document covers the user stories, entity-relationship diagram (ERD), and interface design for the system.

## User Stories

### New Participant Entry
- **As an admin, I want to add a new participant record** to keep the database updated with all participants' information.
  - Acceptance Criteria:
    - Form to enter participant details.
    - Data validation and confirmation of addition.

### Attendance Recording
- **As an admin, I want to record attendance for each meeting day** to track participant presence.
  - Acceptance Criteria:
    - List of participants with attendance marking.
    - Attendance data saved with date and ID.

### Daily Payments Management
- **As an admin, I want to enter the total amount paid to participants every meeting day** for accurate financial records.
  - Acceptance Criteria:
    - Form to enter total daily payments.
    - Validation and confirmation of recorded payments.

### Monthly Summary Reports
- **As an admin, I want to see a monthly summary of attendance and payments** for quick assessment of activities.
  - Acceptance Criteria:
    - Report showing total attendance and payments.
    - Accessible from the admin dashboard.

### Automated Notifications for Absentees
- **As an admin, I want the system to automatically send an email or SMS to absentees** to encourage attendance.
  - Acceptance Criteria:
    - Automatic notification post-meeting.
    - Log of sent notifications.

### Appreciation Emails for Attendance
- **As an admin, I want the system to automatically send a thank-you email to attendees** to foster a positive community.
  - Acceptance Criteria:
    - Automatic appreciation email post-meeting.
    - Log of appreciation emails sent.

## Entity-Relationship Diagram (ERD)

To view the Entity-Relationship Diagram for the NGO Participant Management System, please follow this [link to ERD](https://lucid.app/lucidchart/cdb14083-ef34-4930-b420-9755c3ea8075/edit?viewport_loc=281%2C-134%2C1963%2C1095%2C0_0&invitationId=inv_1259cdef-2b5a-4bd9-a464-2a22939d62fe).
<img width="1046" alt="image" src="https://github.com/algo-mart/kibou-registry/assets/62708917/bc974145-582b-495e-a28c-b77e87c32219">


The ERD for the NGO Participant Management System includes four primary entities:

1. **Participants**: Stores details about each participant including ID, name, category, and contact information.
2. **Attendance**: Tracks the attendance status of participants for each meeting day.
3. **Daily Payments**: Records the total amount paid out on each meeting day, without linking to individual participants.
4. **Notifications**: Manages the sending of notifications (email/SMS) to participants regarding their attendance.

Relationships:
- **Participants** have a one-to-many relationship with **Attendance** and **Notifications**.
- **Daily Payments** is not directly linked to **Participants** but is recorded based on the date.

## Interface Design and Pages Linkage

### Admin Dashboard
- Central hub for accessing all features.
- Quick links to add participants, record attendance, enter daily payments, and view monthly summaries.

### Participant Entry Form
- For entering new participant details.
- Fields for name, category, and contact information.
- Link back to the Admin Dashboard upon submission.

### Attendance Recording Page
- Displays list of participants with checkboxes for attendance marking.
- Submission leads to the recording of attendance data.
- Option to navigate back to the Admin Dashboard.

### Daily Payments Entry Form
- Form to enter total amount paid for the day.
- Validates and confirms the entry of payment information.
- Direct link back to the Admin Dashboard.

### Monthly Summary Reports Page
- Displays reports on total attendance and payments for each category, for each day of the week.
- Accessible directly from the Admin Dashboard.

### Automated Notifications Setup
- Configuration settings for automated emails and SMS for absentees and attendees.
- Accessed through the Admin Dashboard for setup and logs review.

## Conclusion

This document provides a comprehensive overview of the planned NGO Participant Management System MVP, including user stories, the database design (ERD), and a preliminary interface design. The focus is on creating a functional, user-friendly system that meets the core needs of managing participant data, tracking attendance, managing finances, and facilitating communication through notifications. Further iterations will build upon this foundation, incorporating feedback and additional features to enhance the system's capabilities.
