package br.com.jdevtreinamentos.sistemaemail.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe reponsável por iniciar a aplicação.
 * 
 * @author Erik Vasconcelos
 * @since 02/10/2023
 */
public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setClassLoader(getClass().getClassLoader());
		loader.setLocation(getClass().getResource("/br/com/jdevtreinamentos/sistemaemail/views/telas/EnvioEmail.fxml"));
		
		Parent parent = loader.load();
		
		Scene cena = new Scene(parent);
		
		primaryStage.setTitle("App email");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/br/com/jdevtreinamentos/sistemaemail/views/imagens/logo.png")));		
		primaryStage.setScene(cena);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
