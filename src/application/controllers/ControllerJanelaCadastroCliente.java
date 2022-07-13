package application.controllers;

import application.model.BancoDeDados;
import application.model.entidades.Cliente;
import application.model.entidades.Usuario;
import application.model.facades.FacadePrincipal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaCadastroCliente {
		private BancoDeDados bancoDados = BancoDeDados.getInstance();
	    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
	    private Cliente cliente;
	
	  	@FXML
	    private Button buttonOK;

	    @FXML
	    private TextField campoCPF;

	    @FXML
	    private TextField campoEmail;

	    @FXML
	    private TextField campoNome;

	    @FXML
	    private TextField campoTelefone;

	    @FXML
	    private Label labelTitulo;

	    @FXML
	    private Label msgErro;

	    
	    public Cliente cadastrar() {
	    	try {
	    		String nome, cpf, email, telefone;
	    		msgErro.setText("");
	    		nome = campoNome.getText();
	    		cpf = campoCPF.getText();
	    		email = campoEmail.getText();
	    		telefone = campoTelefone.getText();
	    		cliente = sistema.cadastrarCliente(nome, cpf, email, telefone);
	    		if(cliente != null) return cliente;
			}catch (IllegalArgumentException erro1) {
				msgErro.setText(erro1.getMessage());
		    }catch (NullPointerException erro2) {
				msgErro.setText(erro2.getMessage());
			}
				return null;
	    }
	    
	    @FXML
	    void closeJanela(ActionEvent event) {
	    	Stage janela = (Stage) buttonOK.getScene().getWindow();
	    	Cliente cliente = cadastrar();
	    	if(cliente != null) {
	    		janela.close();
	    	}
	    }

}
