package br.com.jdevtreinamentos.sistemaemail.views.componentes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ComponentEmailDestinatario extends HBox {
	
	private Label labelEmail;
	private Button btnRemover;
	
	public ComponentEmailDestinatario(String email) {
		configComponents(email);
		this.setPrefHeight(23);
		this.setMaxHeight(22);
		this.setAlignment(Pos.CENTER_LEFT);
		this.setPadding(new Insets(2, 10, 2, 10));
		this.setSpacing(15);
		this.setStyle("-fx-border-width: 1 1 1 1; -fx-border-style: solid; -fx-border-color: #CDCDCD; -fx-border-radius: 15;");
		this.getChildren().addAll(labelEmail, btnRemover);
	}
	
	private void configComponents(String email) {
		labelEmail = new Label(email);
		btnRemover = new Button("X");
		labelEmail.setStyle("-fx-font-family: Calibri; -fx-font-weight: bolder; -fx-font-size: 16px;");
		btnRemover.setStyle("-fx-background-color: transparent; -fx-font-weight: bolder; -fx-font-size: 14px; -fx-padding: 0px;");
	}
	
	public void setActionBtnRemover(EventHandler<ActionEvent> handler) {
		btnRemover.setOnAction(handler);
	}
	
	public String getEmail() {
		return labelEmail.getText();
	}
	
}
