package com.vw.virtualwallet.services.impl;

import com.vw.virtualwallet.services.contracts.EmailService;
import com.vw.virtualwallet.utils.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caito Vilas
 * date: 08/2024
 * This class is used to send emails using the JavaMailSender and Thymeleaf template engine.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * This method is used to send an email with a confirmation link and activation code.
     *
     * @param to                the recipient's email address
     * @param username          the username of the recipient
     * @param template          the email template to use
     * @param confirmationUrl   the URL to confirm the email
     * @param activationCode    the activation code for the user
     * @param subject           the subject of the email
     * @throws MessagingException if there is an error sending the email
     */
    @Override
    @Async
    public void sendEmail(String to, String username, EmailTemplateName template,
                          String confirmationUrl, String activationCode, String subject) throws MessagingException {

        String templateName;
        if (template == null) {
            templateName = "confirm-email";
        }else {
            templateName = template.getName();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationULR", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        messageHelper.setFrom("no-reply");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);

        String templateContent = templateEngine.process(templateName, context);
        messageHelper.setText(templateContent, true);
        mailSender.send(mimeMessage);
    }
}
