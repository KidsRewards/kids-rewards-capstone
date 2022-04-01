package com.codeup.kidsrewardscapstone.services;

import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.User;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service("mailService")
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String sendGridAPI;

    public String sendTextEmail(String recepient, Reward reward) throws IOException {
        // the sender email should be the same as we used to Create a Single Sender Verification
        System.out.println("Email being configured");
        Email from = new Email("kidsrewards123@gmail.com");
        String subject = "A reward has been purchased!";
        Email to = new Email(recepient);
        Content content = new Content("text/plain",
                "Details about the reward purchased are shown below: \nReward Purchased: " + reward.getTitle() + "\nReward Description: " + reward.getBody() + "\nPurchased By User: " + reward.getUser().getUsername()
                );
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridAPI);
        Request request = new Request();
        try {
            System.out.println("Email being sent");
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }

}
