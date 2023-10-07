package br.com.jdevtreinamentos.sistemaemail.controllers;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.Notifications;
import org.jsoup.Jsoup;

import br.com.jdevtreinamentos.sistemaemail.model.entities.Email;
import br.com.jdevtreinamentos.sistemaemail.model.services.EmailService;
import br.com.jdevtreinamentos.sistemaemail.views.componentes.ListViewAnexos;
import br.com.jdevtreinamentos.sistemaemail.views.componentes.ListViewDestinatarios;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Classe que recebe as requisições da view para executar ações relaciondas ao
 * email.
 * 
 * @author Erik Vasconcelos
 * @since 02/10/2023
 */

public class EmailController {

	@FXML
	private TextField campoDestinatario;

	@FXML
	private TextField campoAssunto;

	@FXML
	private HTMLEditor corpoEmail;

	@FXML
	private ScrollPane contentDestinatarios;

	@FXML
	private ScrollPane contentAnexos;

	@FXML
	private Button btnAddDestinatario;

	@FXML
	private Button btnSelectAnexo;

	@FXML
	private Button btnEnviar;
	
	@FXML
	private Button btnClean;
	
	@FXML
	private ImageView imageAnexo;
	
	@FXML
	private ImageView imageClean;
	
	private ListViewDestinatarios visualizadorDestinatarios;

	private ListViewAnexos visualizadorAnexos;

	private EmailService emailService = new EmailService();

	public EmailController() {
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(500);

				Platform.runLater(() -> {
					initComponents();
				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t.setDaemon(true);
		t.start();
	}

	/*-----------------------INICIALIZAÇÃO DE COMPONENTES-------------------------*/

	private void initComponents() {
		iniciarListViewDestinatarios();
		iniciarListViewAnexo();
		setIcones();
	}

	private void iniciarListViewDestinatarios() {
		if (visualizadorDestinatarios == null) {
			visualizadorDestinatarios = new ListViewDestinatarios();
			contentDestinatarios.setContent(visualizadorDestinatarios);
		}
	}

	private void iniciarListViewAnexo() {
		if (visualizadorAnexos == null) {
			visualizadorAnexos = new ListViewAnexos();
			contentAnexos.setContent(visualizadorAnexos);
		}
	}
	
	private void setIcones() {
		imageAnexo.setImage(new Image(getClass().getResourceAsStream("/br/com/jdevtreinamentos/sistemaemail/views/imagens/clip.png")));
		imageClean.setImage(new Image(getClass().getResourceAsStream("/br/com/jdevtreinamentos/sistemaemail/views/imagens/excluir.png")));
	}

	public void enviarEmail() {
		String assunto = campoAssunto.getText();
		String corpo = corpoEmail.getHtmlText();
		String destinatarios = String.join(";",
				visualizadorDestinatarios.getItens().stream().map(i -> i.getEmail()).collect(Collectors.toList()));

		if (camposValidos()) {
			Email email = new Email(assunto, corpo, destinatarios);

			if (visualizadorAnexos.getItens().size() > 0) {
				List<File> anexos = visualizadorAnexos.getItens().stream().map(i -> i.getAnexo()).collect(Collectors.toList());
				email.getAnexos().addAll(anexos);
			}

			emailService.enviar(email);

			Notifications.create().text("Enviando email").showConfirm();
			resetDados();
		}
	}

	public void addDestinatario() {
		if (!contentDestinatarios.isVisible()) {
			contentDestinatarios.setVisible(true);
		}

		String destinatario = campoDestinatario.getText();

		if (!destinatario.isBlank()) {
			visualizadorDestinatarios.addDestinatario(destinatario);
			campoDestinatario.setText("");
		}
	}

	public void addAnexo() {
		if (!contentAnexos.isVisible()) {
			contentAnexos.setVisible(true);
		}

		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("Arquivos PDF", "*.pdf"),
				new ExtensionFilter("Imagens", Arrays.asList("*.jpeg", "*.jpg", "*.png")));

		List<File> anexos = chooser.showOpenMultipleDialog(new Stage());

		if (anexos != null) {
			anexos.stream().forEach(a -> {
				if (a != null) {
					this.visualizadorAnexos.addAnexo(a);
				}
			});
		}
	}

	private boolean camposValidos() {
		boolean destinatarioValido = visualizadorDestinatarios.getItens().size() > 0;
		boolean assuntoValido = !campoAssunto.getText().isBlank();

		String textoEmail = getSomenteTexto(corpoEmail.getHtmlText());
		boolean corpoValido = !textoEmail.isBlank();

		if (destinatarioValido && assuntoValido && corpoValido) {
			return true;
		} else {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Informação");
			alert.setHeaderText("Verifique os campos");

			if (!destinatarioValido) {
				alert.setContentText("Adicione no mínimo um destinatário!");

			} else if (!assuntoValido) {
				alert.setContentText("Informe o assunto");

			} else if (!corpoValido) {
				alert.setContentText("Informe o corpo do email!");
			}

			alert.showAndWait();
			return false;
		}
	}

	public void resetDados() {
		campoDestinatario.clear();
		campoAssunto.clear();
		corpoEmail.setHtmlText("");
		visualizadorDestinatarios.clear();
		visualizadorAnexos.clear();
	}
	
	private String getSomenteTexto(String html) {
		//O método recebe uma string com html e retorna apenas o texto 
		return Jsoup.parse(html).text();
	}

}
