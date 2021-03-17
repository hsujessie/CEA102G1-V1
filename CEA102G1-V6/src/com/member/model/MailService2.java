package com.member.model;


import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.member.model.MemberVO;

public class MailService2 {
	
	public void sendMail(String to, String subject, String messageText) {
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容	public void sendMail(String to, String subject, String messageText) {
			
	   try {
		// 設定使用SSL連線至 Gmail smtp Server
		   Properties props = new Properties();
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.socketFactory.port", "465");
		   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.port", "465");

		// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
	    // ●須將myGmail的【安全性較低的應用程式存取權】打開
	     final String myGmail = "javacea102@gmail.com";
	     final String myGmail_password = "javaCEA102";
		   Session session = Session.getInstance(props, new Authenticator() {
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(myGmail, myGmail_password);
			   }
		   });

		   Message message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(myGmail));
		   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		  
		 //設定信中的主旨
		   message.setSubject(subject);
		 //設定信中的內容 
		   message.setText(messageText);

		   Transport.send(message);
		   System.out.println("傳送成功!");
     }catch (MessagingException e){
	     System.out.println("傳送失敗!");
	     e.printStackTrace();
     }
   }

public String getMessageText(String memName, String link) {
		
		String message = memName + "您好!\n" + "您的驗證網址為:" + link ;

		return message;
		
	}
public String getMessageText2(String memName, String link) {
	
	String message = memName + "您好!\n" + "您的密碼修改網址為:" + link ;

	return message;
	
}

//	 public static void main (String args[]){
//
//      String to = "mike90072@gmail.com";
//      
//      String subject = "�K�X�q��";
//      
//      String ch_name = "peter1";
//      String passRandom = "111";
//      String messageText = "Hello! " + ch_name + " ���԰O���K�X: " + passRandom + "\n" +" (�w�g�ҥ�)"; 
//       
//      MailService mailService = new MailService();
//      mailService.sendMail(to, subject, messageText);
//
//   }


}
