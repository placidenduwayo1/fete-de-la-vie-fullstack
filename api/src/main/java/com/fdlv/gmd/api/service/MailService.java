package com.fdlv.gmd.api.service;

import java.util.LinkedList;

import com.fdlv.gmd.api.domain.fdlv.FdlvUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.fdlv.gmd.api.domain.User;
import com.fdlv.gmd.api.dto.fdlv.FdlvUserDTO;
import com.fdlv.gmd.api.service.mail.GraphAuthenticatorService;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    private static final String GMD_URL = "gmdUrl";
    private static final String FDLV_URL = "fdlvUrl";

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${mail.support}")
    private String mailSupport;

    @Value("${app.gmd.url}")
    private String gmdUrl;

    @Value("${app.fdlv.url}")
    private String fdlvUrl;

    @Autowired
    private GraphAuthenticatorService graphAuthenticatorService;

    public MailService() {
        super();
    }

    public void sendEmail(String to, String subject, String content) {
        log.debug(
            "Send email to '{}' with subject '{}' and content={}",
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        try {
            Message message = new Message();
            ItemBody body = new ItemBody();

            String toSend = to;
            String toCC = mailSupport;

            LinkedList<Recipient> recipientsList = new LinkedList<>();

            Recipient recipient = new Recipient();
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.address = toSend;
            recipient.emailAddress = emailAddress;
            recipientsList.add(recipient);

            // copie
            LinkedList<Recipient> ccRecipientsList = new LinkedList<>();

            Recipient ccRecipient = new Recipient();
            EmailAddress emailAddressReplay = new EmailAddress();
            emailAddressReplay.address = toCC;
            ccRecipient.emailAddress = emailAddressReplay;
            ccRecipientsList.add(ccRecipient);

            body.contentType = BodyType.HTML;
            message.subject = subject;
            body.content = content;
            message.body = body;
            message.toRecipients = recipientsList;
            message.ccRecipients = ccRecipientsList;

            graphAuthenticatorService
                .getAuthenticationProvider()
                .me()
                .sendMail(UserSendMailParameterSet.newBuilder().withMessage(message).withSaveToSentItems(true).build())
                .buildRequest()
                .post();

            log.debug("Sent email to User '{}'", to);
        } catch (ClientException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(String email, Object user, String templateName, String subject) {
        if (email == null) {
            log.debug("Email doesn't exist '{}'", email);
            return;
        }
        Context context = new Context();
        context.setVariable(USER, user);
        context.setVariable(GMD_URL, gmdUrl);
        context.setVariable(FDLV_URL, fdlvUrl);
        String content = templateEngine.process(templateName, context);
        sendEmail(email, subject, content);
    }

    @Async
    public void sendGMDActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), user, "mail/activationEmail", "goMyDefi account activation is required");
    }

    @Async
    public void sendGMDCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), user, "mail/creationEmail", "goMyDefi account activation is required");
    }

    @Async
    public void sendGMDPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), user, "mail/passwordResetEmail", "goMyDefi password reset");
    }

    @Async
    public void sendFDLVActivatedMail(FdlvUserDTO user) {
        log.debug("Sending account activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), user, "mail/fdlvUserActive", "Compte Organisateur activé");
    }
    
    @Async
    public void sendFDLVResetMail(FdlvUserDTO user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), user, "mail/fdlvResetEmail", "Réinitialisation de votre mot de passe");
    }

    @Async
    public void sendFDLVReinitAdminMail(FdlvUserDTO user) {
        log.debug("Sending idk email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), user, "mail/fdlvResetFromGmdEmail", "Activation de votre compte lafetedelavie");
    }

    @Async
    public void sendFDLVValidPassWord(FdlvUserDTO user) {
        log.debug("Sending valide password email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), user, "mail/changePassword", "Envoie la validation du changement de mot de passe");
    }
}
