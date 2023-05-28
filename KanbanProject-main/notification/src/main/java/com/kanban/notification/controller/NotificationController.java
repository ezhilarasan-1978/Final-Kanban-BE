package com.kanban.notification.controller;

import com.kanban.notification.config.MailSender;
import com.kanban.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    private final MailSender mailSender;

    public NotificationController(@Value("${spring.mail.username}") String username,
                          @Value("${spring.mail.password}") String password) {
        this.mailSender = new MailSender(username, password);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getNotification(@PathVariable String name) {
        return new ResponseEntity<>(notificationService.getNotification(name), HttpStatus.OK);
    }

    @GetMapping("/allRead/{name}")
    public ResponseEntity<?> markAllAsRead(@PathVariable String name) {
        return new ResponseEntity<>(notificationService.markAllRead(name), HttpStatus.OK);
    }

    @GetMapping("/read/{name}/{message}")
    public ResponseEntity<?> markAsRead(@PathVariable String name, @PathVariable String message) {
        return new ResponseEntity<>(notificationService.markRead(name, message), HttpStatus.OK);
    }

    @PostMapping("/email")
    public String sendRegistrationEmail(@RequestBody String email) {
        String subject = "Welcome to WorkFlo – Unleash Your Productivity with Kanban Boards!";
        String content = "Dear " + email + ",\n\n" +
                "Welcome aboard to WorkFlo! We're thrilled to have you join our growing community of productivity enthusiasts." +
                " As the creator of a dedicated platform for Kanban board management, we are committed to helping you streamline your workflow, boost collaboration, and accomplish your goals with ease.\n\n" +
                "At WorkFlo, we understand the importance of effective project management, and our intuitive Kanban board system is designed to simplify " +
                "the process for you. Whether you're an individual looking to organize personal tasks or a team striving for seamless teamwork, our platform offers the ideal solution.\n\n" +
                "Once logged in, you'll be greeted by an intuitive interface where you can create boards, add and manage tasks, collaborate with team members, and monitor progress effortlessly." +
                " Our platform is designed to adapt to your unique workflow, allowing you to customize columns, labels, and other features to align with your specific needs.\n\n" +
                "Moreover, we constantly strive to enhance your experience with regular updates and new features. Stay tuned for upcoming enhancements that will further empower you to stay organized," +
                " increase productivity, and achieve your objectives effectively.\n\n" +
                "Should you encounter any questions or require assistance, our support team is always ready to help. Feel free to reach out to us at workflo.site@gmail.com, and we'll respond promptly to address your queries.\n\n" +
                "Thank you for choosing WorkFlo as your go-to platform for Kanban board management. We're excited to have you join us on this productivity journey!\n\n" +
                "Best regards,\n" +
                "WorkFlo Team";

        mailSender.sendEmail(email, subject, content);
        return "Email sent!";
    }
}
