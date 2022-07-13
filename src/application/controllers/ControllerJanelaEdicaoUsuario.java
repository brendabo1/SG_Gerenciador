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

public class ControllerJanelaEdicaoUsuario implements Initializable{
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Usuario user;
   

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
    

	private void loadChoiceBox() {
    	listaCargos.add(Cargo.FUNCIONARIO);
    	listaCargos.add(Cargo.GERENTE);
    	choiceBoxCargo.getItems().addAll(listaCargos);
    }
    
   public boolean edicao() {
	   String nome, senha;
	   
	   labelTitulo.setText("Edicao");
	   msgErro.setText("");
	   
	   try {
	   		nome = campoNome.getText();
	   		senha = campoSenha.getText();
	   		Cargo cargo = choiceBoxCargo.getSelectionModel().getSelectedItem();
	   		if(!nome.equals(user.getNome())){
	   			sistema.editarNomeUsuario(nome, user);
	   		}
	   		if(!senha.equals(user.getSenha()))
	   			sistema.editarSenhaUsuario(senha, user);
	   		
	   		if(!cargo.equals(user.getTipoDeUsuario()))
	   			sistema.editarCargoUsuario(cargo, user);
	   		return true;
   		
		}catch (IllegalArgumentException erro2) {
			msgErro.setText(erro2.getMessage());
	    }catch (NullPointerException erro3) {
			msgErro.setText(erro3.getMessage());
		}
	   		return false;
   }
    

	@FXML
    void closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	if(edicao() == true) {
    		janela.close();
    	}
 
    }

	boolean checaCamposFill() {
		if(choiceBoxCargo.getSelectionModel().isEmpty())
			throw new NullPointerException("Todos os campos devem estar preenchidos");		
		return true;
	}
    
	
	public void loadCampos(Usuario usuarioRecebido) {
		this.user = usuarioRecebido;
		labelTitulo.setText("Edicao");
		campoNome.setText(usuarioRecebido.getNome());
		campoSenha.setText(usuarioRecebido.getSenha());
		choiceBoxCargo.setValue(usuarioRecebido.getTipoDeUsuario());
	}
	
	public Label getLabelTitulo() {
		return labelTitulo;
	}

	public void setLabelTitulo(Label labelTitulo) {
		this.labelTitulo = labelTitulo;
	}
}
