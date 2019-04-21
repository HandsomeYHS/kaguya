package com.akanemurakawa.kaguya.util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * JavaEmail发送邮件，测试成功。
 */
@Slf4j
public class SendEmailUtils {
	
	/**
	 * sendEmail发送邮件
	 * @param from 发送人
	 * @param password 授权码
	 * @param to 接收人
	 * @param subject 主题
	 * @param content 内容
	 * @throws Exception
	 */
	public void send(String from, String password, String to, String subject, String content) throws Exception {
		/*
		 * 1.配置邮件
		 */
		Properties props = new Properties();
		String host = "smtp." + from.split("@")[1];
		props.setProperty("mail.host", host); // 发件人的SMTP服务器地址，可以通过登录web邮箱查询
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.setProperty("mail.transport.protocol", "smtp"); // 设置协议
        props.setProperty("mail.port", "465");// 端口可以到邮箱去查看，QQ的一般是465
        props.put("mail.smtp.ssl.enable", "true"); // 需要ssl
        // 创建ssl加密证书并放到资源文件中
		MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
		props.put("mail.smtp.socketFactory", mailSSLSocketFactory); 
		// 邮箱服务器登录验证
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
		
		/*
		 * 2.创建会话对象，用于和邮件服务器交互
		 */
		Session session = Session.getInstance(props, authenticator);
		session.setDebug(true); // 设置为debug模式
		
		/*
		 * 3.创建邮件
		 */
		Message message = new MimeMessage(session);
		// 创建默认的Mime类型邮件
		// Set From： 头部头字段， 发送人
		message.setFrom(new InternetAddress(from));
		/*
		 * Set To： 头部头字段， 收件人
		 * Set Cc： 抄送(可选)
		 * Set Bcc： 密送(可选)
		 * message.addRecipients(RecipientType type, Address[] addresses) 增加收件人(可选)
		 */
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// Subject： 头部头字段 ，邮件主题
		message.setSubject(subject);
		// Content：设置正文
		message.setContent(content, "text/html;charset=UTF-8");
		// SentDate：设置发送时间
		message.setSentDate(new Date());
		// 保存设置
		message.saveChanges();
		
		/*
		 * 4.发送邮件
		 */
		// 获得邮件传输对象
		Transport transport = session.getTransport();
		// 连接
		transport.connect(host, from, password);
		transport.sendMessage(message, message.getAllRecipients());
        log.info("Sent message successfully....");
		// 6. 关闭
		transport.close();
	}
}
