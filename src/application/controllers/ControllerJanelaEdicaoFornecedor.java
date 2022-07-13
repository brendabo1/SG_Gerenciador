package application.controllers;

import java.util.InputMismatchException;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.facades.FacadePrincipal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaEdicaoFornecedor{
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Fornecedor fornecedor;	
	
	@FXML
    private Button buttonOK;

    @FXML
    private TextField campoCNPJ;

    @FXML
    private TextField campoEndereco;

    @FXML
    private TextField campoNome;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msgErroFornecedor;

  
    
    @FXML
    void closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	if(edicao()) {
    		janela.close();
    	}
    }

    public void loadCampos(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
		labelTitulo.setText("Edicao");
		campoNome.setText(fornecedor.getNome());
		campoCNPJ.setText(fornecedor.getCNPJ());
		campoEndereco.setText(fornecedor.getEndereco());
	}


	public Label getLabelTitulo() {
		return labelTitulo;
	}


	public void setLabelTitulo(Label labelTitulo) {
		this.labelTitulo = labelTitulo;
	}


	public boolean edicao() {	   	   
	   labelTitulo.setText("Edicao");
	   msgErroFornecedor.setText("");
	   
	   try {  
		    String nome, cnpj, endereco;
	   		cnpj = campoCNPJ.getText();
	   		nome = campoNome.getText();
	   		endereco = campoEndereco.getText();
	   		if(!nome.equals(fornecedor.getNome()))
	   			sistema.editarNomeFornecedor(nome, fornecedor);
	   		if(!cnpj.equals(fornecedor.getCNPJ()))
	   			sistema.editarCNPJfornecedor(cnpj, fornecedor);
	   		if(!endereco.equals(fornecedor.getEndereco()))
	   			sistema.editarEnderecoFornecedor(endereco, fornecedor);
	   		return true;
		}catch (IllegalArgumentException erroNome) {
			msgErroFornecedor.setText(erroNome.getMessage());	
		}catch (ExceptionInInitializerError erroInicializacao) {
			msgErroFornecedor.setText(erroInicializacao.getMessage());
		} 
	   		return false;
   }
    
	
}
