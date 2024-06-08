package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDto {
    private String recipientEmail;
    private String subject;
    private String textContent;
    private String fileName;

    public boolean sendEmail() {
        System.out.println("Enter password");
        // Sender's email address and password
        String senderEmail = "prelanr@gmail.com";
        String senderPassword = "password";


        // Recipient's email address


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        // Create a session with the specified properties
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress(senderEmail));

            // Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.recipientEmail));

            if(this.subject!=null){
                message.setSubject(this.subject);
            }
            // Set the subject and text of the email


            // Create a multipart message
            Multipart multipart = new MimeMultipart();




            // Text part of the email

            if(this.textContent!=null){
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(this.textContent);
                multipart.addBodyPart(textPart);
            }




            if(this.fileName!=null){
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                String filename = "src\\main\\resources\\reports\\PdfFiles\\"+this.fileName;
                messageBodyPart.attachFile(filename);
                multipart.addBodyPart(messageBodyPart);
            }

//ER_DIAGRAM_ICM105_ThiwankaReiss.pdf


            // Set the content of the message
            message.setContent(multipart);

            System.out.println("Email sent");
            Transport.send(message);
            // Send the email
            return true;

        } catch (MessagingException e) {
            System.out.println("Error sent");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Email Sent finally");
        return false;
    }

}
