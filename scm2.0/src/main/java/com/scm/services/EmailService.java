// package com.scm.services;

// import javax.mail.PasswordAuthentication;
// import java.util.Properties;

// import javax.mail.Authenticator;
// import javax.mail.Session;
// import javax.mail.internet.InternetAddress;
// import javax.mail.internet.MimeMessage;

// import javax.mail.Message;

// import javax.mail.Transport;

// import org.springframework.stereotype.Service;

// @Service
// public class EmailService {
// // You can implement email sending functionality here
// public boolean sendEmail(String subject, String message, String to) {

// boolean f = false;

// String from = "soumyapandey1899@gmail.com";
// // variable for gmail smtp is server to send email.
// String host = "smtp.gmail.com";

// // get the system properties.
// Properties properties = System.getProperties();
// System.out.println(properties);
// // settings important info to properties object.
// properties.put("mail.smtp.host", host);
// properties.put("mail.smtp.port", "465"); // 465 for SSL or 587 for TLS
// properties.put("mail.smtp.ssl.enable", "true");
// properties.put("mail.smtp.auth", "true");

// // step1. to get the session object:
// Session session = Session.getInstance(properties, new Authenticator() {
// @Override
// protected PasswordAuthentication getPasswordAuthentication() {
// return new PasswordAuthentication

// ("soumyapandey1899@gmail.com", "");
// }
// });
// session.setDebug(true);

// // step2.compose the message[text,multimedia].
// MimeMessage m = new MimeMessage(session);
// try {
// // from email
// m.setFrom(from);
// // adding recipient
// m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

// // adding subject to message.
// m.setSubject(subject);
// // adding text to message
// m.setText(message);
// m.setContent(message, "text/html");
//
// // Step3 send the message using transport class
// Transport.send(m);
// System.out.println("Sent successfully");
// f = true;
// } catch (Exception e) {
// e.printStackTrace();
// }
// return f;

// }

// }
