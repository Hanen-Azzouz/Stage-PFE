package tn.esprit.gestionhospitalierebackend.Services.implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public  void sendSimpleEmail(String toEmail,String body,String subject){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(("haneneazzouz2023@gmail.com"));
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println(("Mail Sent ..."));
    }

    public void sendEmailWithAttachment(String toEmail,String body,String subject,String attachment) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,
                true);
        mimeMessageHelper.setFrom("haneneazzouz2023@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject((subject));
        FileSystemResource fileSystem=new FileSystemResource(new File(attachment));
        mimeMessageHelper.addAttachment(fileSystem.getFilename(),fileSystem);
        mailSender.send(mimeMessage);
        System.out.println(("Mail Sent with attachment..."));
    }

}
