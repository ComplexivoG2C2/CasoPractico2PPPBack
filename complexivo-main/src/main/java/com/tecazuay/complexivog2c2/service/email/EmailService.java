package com.tecazuay.complexivog2c2.service.email;

import com.tecazuay.complexivog2c2.dto.email.EmailBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment environment;
    @Autowired
    private TemplateEngine htmlTemplateEngine;
    private static final String TEMPLATE_NAME = "email";
    private static final String PNG_MIME = "image/png";

    public boolean sendEmail(EmailBody emailBody) {
        LOGGER.info("EmailBody: {}", emailBody.toString());
        return sendEmailTool(emailBody.getContent(), emailBody.getEmail(), emailBody.getSubject(),emailBody.getText2());
    }

    private boolean sendEmailTool(String textMessage, String emailDes, String subject,String text2) {
        boolean send = false;
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper email;
        try {
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            email.setTo(emailDes);
            email.setSubject(subject);
            email.setText(textMessage, true);
            email.setText(text2,true);
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            final String SPRING_LOGO_IMAGE = "templates/images/descarga.png";
            ctx.setVariable("subject", subject);
            ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);
            ctx.setVariable("content", textMessage);
            ctx.setVariable("content2", text2);
            ctx.setVariable("url", "http://eduv.tecazuay.edu.ec/login/index.php");
            final String htmlContent = this.htmlTemplateEngine.process(TEMPLATE_NAME, ctx);
            email.setText(htmlContent, true);
            ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);
            email.addInline("springLogo", clr, PNG_MIME);
            mailSender.send(mimeMessage);
            send = true;
            LOGGER.info("Mail enviado!");
        } catch (MessagingException e) {
            LOGGER.error("Hubo un error al enviar el mail: {}", e);
        }
        return send;
    }
}
