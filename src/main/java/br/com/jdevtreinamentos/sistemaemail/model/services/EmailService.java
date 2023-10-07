package br.com.jdevtreinamentos.sistemaemail.model.services;

import br.com.jdevtreinamentos.sistemaemail.model.entities.Email;

/**
 * Classe responsável por prover os serviços do email.
 * 
 * @author Erik Vasconcelos
 * @since 03/10/2023
 */
public class EmailService {

	private ObjetoEnviaEmail enviaEmail = ObjetoEnviaEmail.getInstance();
	
	public boolean enviar(Email email) {
		if(email.isValid()) {
			enviaEmail.addEmail(email);
			enviaEmail.startSend();
			
			return true;
		}else {
			throw new IllegalArgumentException("Os dados são inválidos!");
		}
	}
	
}
