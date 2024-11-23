package com.technovate.school_management.service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TrackOpens;
import com.mailjet.client.transactional.TransactionalEmail;
import com.technovate.school_management.model.EmailModel;
import com.technovate.school_management.service.contracts.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final MailjetClient client;
    @Value("${application_config.email_credentials.from_email}")
    private String fromEmail;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    @Async
    public void sendEmail(EmailModel email) {
        try {
            TransactionalEmail message = TransactionalEmail
                    .builder()
                    .to(new SendContact(email.getTo(), ""))
                    .from(new SendContact(fromEmail, ""))
                    .htmlPart(email.getBody())
                    .subject(email.getSubject())
                    .trackOpens(TrackOpens.ENABLED)
                    .build();

            SendEmailsRequest request = SendEmailsRequest
                    .builder()
                    .message(message) // you can add up to 50 messages per request
                    .build();

            request.sendWith(client);
        } catch (Exception ex) {
            logger.error("Attempt to send email with this data: {} failed with the exception: {}", email, ex.toString());
        }
    }
}
