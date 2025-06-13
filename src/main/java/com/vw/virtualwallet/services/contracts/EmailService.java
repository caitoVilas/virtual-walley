package com.vw.virtualwallet.services.contracts;

import com.vw.virtualwallet.utils.enums.EmailTemplateName;
import jakarta.mail.MessagingException;

/**
 * @author caito Vilas
 * date: 08/2024
 * This interface defines the contract for sending emails.
 */
public interface EmailService {
    void sendEmail(String to, String username, EmailTemplateName template,
                   String confirmationUrl, String activationCode, String subject) throws MessagingException;
}
