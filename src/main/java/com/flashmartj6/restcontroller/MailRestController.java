package com.flashmartj6.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashmartj6.services.AccountService;
import com.flashmartj6.services.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@CrossOrigin("*")
@RestController
public class MailRestController {

	@Autowired
	AccountService accountService;

	@Autowired
	JavaMailSender sender;

	private @Autowired MailService mailService;

	@PutMapping("/user/getpassword/{gmail}")
	public void ForgetPassword(@PathVariable("gmail") String gmail) {
		accountService.UpdatePasswordByMail(gmail);
		try {// Tạo message
			MimeMessage message = sender.createMimeMessage();
			// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom("Phi");
			helper.setTo(gmail);
			helper.setSubject("Thông báo mật khẩu mới của bạn");
			helper.setText("Mật khẩu mới của bạn là: 123456");
			mailService.add(message);
			sender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
