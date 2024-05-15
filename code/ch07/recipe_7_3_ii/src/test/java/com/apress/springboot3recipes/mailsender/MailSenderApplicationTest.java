package com.apress.springboot3recipes.mailsender;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MailSenderApplicationTest {

	@RegisterExtension
	static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.ALL)
			.withPerMethodLifecycle(false);

	@Test
	void shouldHaveSendMail() throws Exception {
		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		assertThat(receivedMessages).hasSize(1);
		assertThat(receivedMessages[0].getSubject())
				.isEqualTo("Status message");
		assertThat(receivedMessages[0]
				.getRecipients(Message.RecipientType.TO))
				.contains(new InternetAddress("recipient@some.where"));
	}
}
