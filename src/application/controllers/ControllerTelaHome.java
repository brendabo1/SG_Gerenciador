package application.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ControllerTelaHome {

	public void carregarPagina(String pagina){
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(pagina + ".fxml"));
			Parent fxml = loader.load();
			//borderP.setCenter(fxml);
			
			// Abrindo outra tela
			/*Stage stage = new Stage();
			stage.setScene(new Scene(fxml));
			stage.show();*/
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

}
