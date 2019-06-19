package rs.ac.uns.ftn.isa9.tim8.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.stereotype.Component;
 
import java.io.IOException;
 
@Component
public class IEmailService {
 
    public void sendEmail() {
        Email from = new Email("isa.mrs.tim8@gmail.com");
        String subject = "Amazing Email";
        Email to = new Email("nikolabrishman@gmail.com");
        Content content = new Content("text/plain", "You have received this email complements of SendGrid and Heroku!");
        Mail mail = new Mail(from, subject, to, content);
 
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Status code sending email:" + response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Email was not sent." + ex.getLocalizedMessage());
        }
    }
 
}