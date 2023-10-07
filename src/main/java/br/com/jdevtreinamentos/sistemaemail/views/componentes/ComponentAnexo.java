package br.com.jdevtreinamentos.sistemaemail.views.componentes;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ComponentAnexo extends HBox {
	
	private Label nome;
	private Button btnRemover;
	private File anexo;
	
	public ComponentAnexo(File file) {
		if(file != null) {
			this.anexo = file;
		}else {
			throw new IllegalArgumentException("O anexo está nullo!");
		}
		
		configComponents();
		this.setPrefHeight(30);
		this.setMaxHeight(22);
		this.setAlignment(Pos.CENTER_LEFT);
		this.setPadding(new Insets(2, 10, 2, 10));
		this.setSpacing(30);
		this.setStyle("-fx-background-color: #DDDDDD;");
		this.getChildren().addAll(nome, btnRemover);
	}
	
	private void configComponents() {
		String nomeAnexo = this.anexo.getName();
		nome = new Label(nomeAnexo);
		btnRemover = new Button("X");
		nome.setStyle("-fx-font-family: Calibri; -fx-font-weight: bolder; -fx-font-size: 16px;");
		btnRemover.setStyle("-fx-background-color: transparent; -fx-font-weight: bolder; -fx-font-size: 14px; -fx-padding: 0px;");
	}
	
	public File getAnexo() {
		return anexo;
	}
	
	public void setActionBtnRemover(EventHandler<ActionEvent> handler) {
		btnRemover.setOnAction(handler);
	}
	
}
