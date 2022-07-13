package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.facades.FacadePrincipal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaCadastroUsuario implements Initializable{
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Usuario user;
    private Stage stage;
   

	@FXML
    private Button buttonOK;
    
	@FXML
    private TextField campoNome;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private ChoiceBox<Cargo> choiceBoxCargo;

    @FXML
    private Label labelTitulo;
    
    @FXML
    private Label msgErro;

  

    ObservableList<Cargo> listaCargos = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		loadChoiceBox();
		
	}
    
	public Usuario getUser() {
		return user;
	}


	public void setUser(Usuario user) {
		this.user = user;
	}


	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage janela) {
		this.stage = janela;
	}

	private void loadChoiceBox() {
    	listaCargos.add(Cargo.FUNCIONARIO);
    	listaCargos.add(Cargo.GERENTE);
    	choiceBoxCargo.getItems().addAll(listaCargos);
    }
    
   
    
    public Usuario cadastrar() {
    	try {
    		String nome, senha;
    		msgErro.setText("");
    		nome = campoNome.getText();
    		senha = campoSenha.getText();
    		Cargo cargo = choiceBoxCargo.getSelectionModel().getSelectedItem();
    		user = sistema.cadastrarUsuario(nome, senha, cargo);
    		if(user != null && checaCamposFill()) return user;
		}catch (IllegalArgumentException erro1) {
			msgErro.setText(erro1.getMessage());
	    }catch (NullPointerException erro2) {
			msgErro.setText(erro2.getMessage());
		}
			return null;
    }
    
    @FXML
    Usuario closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	Usuario usuario = cadastrar();
    	if(usuario != null) {
    		janela.close();
    		return usuario;
    	}
    	return null;
    }

	boolean checaCamposFill() {
		if(choiceBoxCargo.getSelectionModel().isEmpty())
			throw new NullPointerException("Todos os campos devem estar preenchidos");		
		return true;
	}
	

	public void loadCamposEdicao(Usuario usuarioRecebido) {
		labelTitulo.setText("Edicao");
		campoNome.setText(usuarioRecebido.getNome());
		campoSenha.setText(usuarioRecebido.getSenha());
		choiceBoxCargo.setValue(usuarioRecebido.getTipoDeUsuario());
	}
	
	
}
