package br.com.jdevtreinamentos.sistemaemail.model.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe modelo do email
 * 
 * @author Erik Vasconcelos
 * @since 02/10/2023
 */

public class Email {

	private String assunto;
	private String corpo;
	private String destinatarios;
	private List<File> anexos = new ArrayList<>();

	public Email(String assunto, String corpo, String destinatarios) {
		this.assunto = assunto;
		this.corpo = corpo;
		this.destinatarios = destinatarios;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public String getListDestinatariosString() {
		return destinatarios.replace(";", ",");
	}

	public void setDestinatarios(String destinatario) {
		this.destinatarios = destinatario;
	}

	public List<File> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<File> anexos) {
		this.anexos = anexos;
	}

	public boolean isValid() {
		boolean assuntoValido = !this.assunto.isBlank();
		boolean corpoValido = !this.corpo.isBlank();
		boolean destinatarioValido = !this.destinatarios.isBlank();

		return assuntoValido && corpoValido && destinatarioValido;
	}

	public boolean possuiMultiplosDestinatarios() {
		return destinatarios.contains(";");
	}

	public boolean possuiAnexos() {
		return anexos.size() > 0;
	}

}
