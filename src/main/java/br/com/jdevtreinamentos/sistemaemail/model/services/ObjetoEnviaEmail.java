package br.com.jdevtreinamentos.sistemaemail.model.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import br.com.jdevtreinamentos.sistemaemail.model.entities.Email;
import jakarta.activation.DataHandler;
import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

/**
 * Classe responsável por enviar o email usando Thread, ou seja, programação
 * paralela ao processo principal. Esta classe implementa o padrão de projeto
 * Singleton para o acesso a uma única instância do objeto de envio de email.
 * 
 * @author Erik Vasconcelos
 * @since 02/10/2023
 */

public class ObjetoEnviaEmail extends Thread {

	private static ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail();
	private static ConcurrentLinkedQueue<Email> filaEmails = new ConcurrentLinkedQueue<>();

	private static final String EMAIL_REMETENTE = "javaenterpriseoracle@gmail.com";
	private static final String NOME_REMETENTE = "Java Enterprise";
	private static final String SENHA = "jcds fvhp tdmv xywr";

	private ObjetoEnviaEmail() {
	}

	public static ObjetoEnviaEmail getInstance() {
		return enviaEmail;
	}

	public void startSend() {
		if (!enviaEmail.isAlive()) {
			enviaEmail.start();
		}
	}

	public void stopSend() {
		if (enviaEmail.isAlive()) {
			enviaEmail.interrupt();
		}
	}

	public void addEmail(Email email) {
		filaEmails.add(email);
	}

	@Override
	public void run() {
		while (true) {
			synchronized (filaEmails) {

				Iterator<Email> emails = filaEmails.iterator();
				while (emails.hasNext()) {
					Email emailEnviar = emails.next();
					enviarEmail(emailEnviar);

					emails.remove();

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						throw new RuntimeException("Erro no tempo de espera do envio");
					}
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException("Erro no tempo de espera de verificação de novos emails");
			}
		}

	}

	/*-----------------------ENVIO DE EMAIL-------------------------*/

	private void enviarEmail(Email email) {
		try {
			Session session = getSessao();

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(EMAIL_REMETENTE, NOME_REMETENTE));
			message.setSubject(email.getAssunto());

			if (email.possuiMultiplosDestinatarios()) {
				Address[] destinatarios = InternetAddress.parse(email.getListDestinatariosString());
				message.setRecipients(RecipientType.TO, destinatarios);

			} else {
				Address destinatario = new InternetAddress(email.getDestinatarios());
				message.setRecipient(RecipientType.TO, destinatario);
			}

			if (email.possuiAnexos()) {
				Multipart multipart = new MimeMultipart();

				MimeBodyPart corpo = new MimeBodyPart();
				corpo.setContent(email.getCorpo(), "text/html; charset=utf-8");
				multipart.addBodyPart(corpo);

				configurarAnexos(email.getAnexos(), multipart);

				message.setContent(multipart);
			} else {
				message.setContent(email.getCorpo(), "text/html; charset=utf-8");
			}

			Transport.send(message);
		} catch (UnsupportedEncodingException | MessagingException e) {
			throw new RuntimeException("Erro ao enviar email: " + e.getMessage());
		}
	}

	private Session getSessao() {
		Properties propConexao = new Properties();
		propConexao.put("mail.smtp.auth", "true");
		propConexao.put("mail.smtp.starttls", "true");
		propConexao.put("mail.smtp.ssl.trust", "*");
		propConexao.put("mail.smtp.host", "smtp.gmail.com");
		propConexao.put("mail.smtp.port", "465");
		propConexao.put("mail.smtp.socketFactory.port", "465");
		propConexao.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(propConexao, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_REMETENTE, SENHA);
			}
		});

		return session;
	}

	private void configurarAnexos(List<File> arquivos, Multipart multipart) {
		try {
			for (File file : arquivos) {
				FileInputStream streamAnexo = new FileInputStream(file);
				MimeBodyPart anexo = new MimeBodyPart();

				String tipo = "application/" + getExtensaoArquivo(file);
				anexo.setDataHandler(new DataHandler(new ByteArrayDataSource(streamAnexo, tipo)));
				anexo.setFileName(file.getName());

				multipart.addBodyPart(anexo);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*-----------------------UTILITARIOS-------------------------*/

	private String getExtensaoArquivo(File file) {
		String extensao = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		return extensao;
	}

}
