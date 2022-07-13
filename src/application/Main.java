package application;
	
import java.io.IOException;

import application.model.BancoDeDados;
import application.model.PreCadastro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
	private PreCadastro precadastro = new PreCadastro(bancoDados);
	
	@Override
	public void start(Stage stage) {
		try {
			precadastro.usuarios();
			precadastro.fornecedor();
			precadastro.clientes();
			precadastro.itemCardapio();
			precadastro.lote();
			precadastro.venda();
			openLogin(stage);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void openLogin(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("views/TelaLogin.fxml"));
		
		Scene sceneLogin = new Scene(root);
		sceneLogin.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setTitle("SG Gerenciador");
		stage.getIcons().add(new Image("file:resources/local_localstoreicon.png"));
		stage.setScene(sceneLogin);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
