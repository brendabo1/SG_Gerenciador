package application.controllers;
import java.io.IOException;
import application.model.BancoDeDados;
import application.model.subsistemtest.SubsistemUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerTelaLogin {

    private Parent root;
    private Stage janela;
    private BancoDeDados bancoDados = new BancoDeDados();
    private SubsistemUsuario subUsuario = new SubsistemUsuario(bancoDados);
	
	@FXML
    private TextField campoIDUser = null;

    @FXML
    private PasswordField campoSenha = null;
    
    @FXML
    private Label msgErro;
    
    @FXML
    private Button buttonLogin;
    
    @FXML
    public void checaLogin(ActionEvent event) throws IOException{ 	
    	if(subUsuario.login(campoIDUser.getText(), campoSenha.getText())) {
			carregaTela("/application/views/TelaHome.fxml");
    	}
    	else msgErro.setText("ID ou senha incorretos");
	
    }

	public void carregaTela(String caminho) throws IOException {
		root = FXMLLoader.load(getClass().getResource(caminho));
		janela = (Stage) buttonLogin.getScene().getWindow();
		janela.setScene(new Scene(root));
	}
    
    /*@FXML
    void login(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            stage.close();
        }

    }*/
    

    
}
