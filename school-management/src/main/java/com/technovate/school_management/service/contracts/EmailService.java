package com.technovate.school_management.service.contracts;

import com.mailjet.client.errors.MailjetException;
import com.technovate.school_management.model.EmailModel;

public interface EmailService {
    void sendEmail(EmailModel email) throws MailjetException;
}
