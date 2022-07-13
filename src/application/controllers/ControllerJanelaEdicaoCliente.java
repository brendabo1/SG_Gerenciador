package application.controllers;

import application.model.BancoDeDados;
import application.model.entidades.Cliente;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.facades.FacadePrincipal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaEdicaoCliente {
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

    @FXML
    void closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	if(edicao() == true) {
    		janela.close();
    	}
    }
	    
    public boolean edicao() { 	
    	String nome, cpf, email, telefone;
 	   
 	   labelTitulo.setText("Edicao");
 	   try {
 		    msgErro.setText("");
	   		nome = campoNome.getText();
	   		cpf = campoCPF.getText();
	 	   	email = campoEmail.getText();
			telefone = campoTelefone.getText();
	   		if(!nome.equals(cliente.getNome()))
	   			sistema.editarNomeCliente(nome, cliente);
	   		if(!cpf.equals(cliente.getCPF()))
	   			sistema.editarCPFcliente(cpf, cliente);
	   		if(!email.equals(cliente.getEmail()))
	   			sistema.editarEmailCliente(email, cliente);
	   		if(!telefone.equals(cliente.getTelefone()))
	   			sistema.editarTelefoneCliente(telefone, cliente);	   		
	   		return true;
    		
 		}catch (IllegalArgumentException erro2) {
 			msgErro.setText(erro2.getMessage());
 	    }catch (NullPointerException erro3) {
 			msgErro.setText(erro3.getMessage());
 		}
 	   		return false;
    }
    
    public void loadCampos(Cliente clienteRecebido) {
		this.cliente = clienteRecebido;
		campoNome.setText(clienteRecebido.getNome());
		campoCPF.setText(clienteRecebido.getCPF());
		campoEmail.setText(clienteRecebido.getEmail());
		campoTelefone.setText(clienteRecebido.getTelefone());
	}

}
