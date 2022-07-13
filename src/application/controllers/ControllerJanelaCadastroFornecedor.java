package application.controllers;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.facades.FacadePrincipal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaCadastroFornecedor {
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
    private Label msgErroCNPJ;

    @FXML
    private Label msgErroNome;
    
    @FXML
    private Label msgErroEndereco;

    @FXML
    private Label msgErroFornecedor;

    
    public Fornecedor cadastrar() {
    	try {    		
    		String nome, cnpj, endereco;
    		msgErroFornecedor.setText("");
    		cnpj = campoCNPJ.getText();
    		nome = campoNome.getText();
    		endereco = campoEndereco.getText();
    		fornecedor = sistema.cadastrarFornecedor(nome, cnpj, endereco);
    		if(fornecedor != null) return fornecedor;
		}catch (IllegalArgumentException erro) {
			msgErroFornecedor.setText(erro.getMessage());
		}catch (ExceptionInInitializerError erroInicializacao) {
			msgErroFornecedor.setText(erroInicializacao.getMessage());
		}    	
			return null;
    }
       
    @FXML
    void closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	Fornecedor fornecedor = cadastrar();
    	if(fornecedor != null) {
    		janela.close();
    	}
    }



	public Label getLabelTitulo() {
		return labelTitulo;
	}


	public void setLabelTitulo(Label labelTitulo) {
		this.labelTitulo = labelTitulo;
	}


    
    
}
