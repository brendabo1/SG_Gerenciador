package application.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerTelaLogin implements Initializable{

    private Parent root;
    private Scene scene;
    private Stage stage;
	
	@FXML
    private TextField campoIDUser;

    @FXML
    private PasswordField campoSenha;
    
    @FXML
    private Button buttonLogin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//buttonLogin.setText("Logado");
	}
    
    /*@FXML
    void the_name_of_the_method(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            stage.close();
        }

    }*/
    

    
}
