module sistema_email {
	
	/*--********| Java Mail  |********--*/
	requires jakarta.mail;
	
	/*--********| JavaFX  |********--*/
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires javafx.web;
	requires java.desktop;
	
	/*--********| Jsoup  |********--*/
	requires org.jsoup;
	
	opens br.com.jdevtreinamentos.sistemaemail.views;
	opens br.com.jdevtreinamentos.sistemaemail.controllers;
	
}