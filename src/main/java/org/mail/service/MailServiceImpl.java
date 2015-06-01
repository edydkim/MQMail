package org.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


// FIXME: define the spec
@Component
public class MailServiceImpl implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final Locale LOCALE = Locale.JAPANESE;
    private final String FILE_SEPARATOR = File.separator;

    private String resourceLoaderPath;

    public void setResourceLoaderPath(String resourceLoaderPath) {
        this.resourceLoaderPath = resourceLoaderPath;
    }

    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void setTemplateEngine(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // TODO: to inject DAO for template and From and To address to get a properties
    /*
    @Autowired
    private SomeDAO someDAO = ....;
     */

    @Override
    public void send(String whichMailToSend) throws MessagingException {
        whichMailToSend = "default";

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "ISO-2022-JP");

        // TODO: to need to set real data, not test
        final String imageResourceName = new StringBuilder(resourceLoaderPath)
                .append(FILE_SEPARATOR)
                .append("test.jpeg")
                .toString();
        final byte[] imageBytes = new byte[102400];
        final String imageContentType = "image/jpeg";

        // Prepare the evaluation context
        final Context ctx = new Context(LOCALE);
        ctx.setVariable("userName", "dao.name");
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        // Prepare message using a Spring helper
        mimeMessageHelper.setFrom("eddy@localhost");
        mimeMessageHelper.setTo("eddy@localhost");
        mimeMessageHelper.setText("Test English an 日本語", true);  // true = isHtml

        mimeMessage.setSubject("Test English an 日本語", "ISO-2022-JP");

        // Create the HTML body using Thymeleaf
        final String htmlContent = templateEngine.process("test.html", ctx);
        mimeMessageHelper.setText(htmlContent, false);

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        mimeMessageHelper.addInline(imageResourceName, imageSource, imageContentType);

        mailSender.send(mimeMessage);
    }
}
