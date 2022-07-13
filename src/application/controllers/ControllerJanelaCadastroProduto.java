package application.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;
import application.model.entidades.enums.UnidadeMedida;
import application.model.facades.FacadePrincipal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaCadastroProduto implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Produto produto;
    
	@FXML
    private Button buttonOK;

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoQnt;

    @FXML
    private ChoiceBox<UnidadeMedida> choiceBoxUndMedida;

    @FXML
    private ChoiceBox<Fornecedor> choiceBoxFornecedores;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msgErro;
    
    @FXML
    private Spinner<Double> spinnerQnt;

    
    ObservableList<Fornecedor> listaFornecedores = FXCollections.observableArrayList();
    ObservableList<UnidadeMedida> listaUndMedida = FXCollections.observableArrayList();
    SpinnerValueFactory<Double> quantidadeFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000.0, 100.5);
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {    	
    	loadChoiceBox();
    	loadSpinner();
		
	}


	private void loadChoiceBox() {
		listaUndMedida.add(UnidadeMedida.KG);
		listaUndMedida.add(UnidadeMedida.G);
		listaUndMedida.add(UnidadeMedida.L);
		listaUndMedida.add(UnidadeMedida.ML);
		listaUndMedida.add(UnidadeMedida.UNIDADE);
		choiceBoxUndMedida.getItems().addAll(listaUndMedida);
		
		Collection<Fornecedor> fornecedoresCadastrados = bancoDados.getMap_fornecedores().values();
		listaFornecedores = FXCollections.observableArrayList(fornecedoresCadastrados);
		choiceBoxFornecedores.getItems().addAll(listaFornecedores);
    }
    
	private void loadSpinner() {
		this.spinnerQnt.setValueFactory(quantidadeFactory);
		this.spinnerQnt.setEditable(true);
	}
    
    public Produto cadastrar() {
    	try {
    		String nome;
    		double qnt;
    		
    		msgErro.setText("");
    		nome = campoNome.getText();
    		UnidadeMedida und = choiceBoxUndMedida.getSelectionModel().getSelectedItem();
    		qnt = spinnerQnt.getValue();
    		if(checaCamposFill()) {
    			Fornecedor fornecedorSelected = choiceBoxFornecedores.getSelectionModel().getSelectedItem();
        		produto = sistema.cadastrarProduto(nome, fornecedorSelected, und, qnt);
        		return produto;
    		}
    		
    	}catch (InputMismatchException erroInvalido) {
			msgErro.setText(erroInvalido.getMessage());
    	}catch (IllegalArgumentException erro) {
			msgErro.setText(erro.getMessage());
	    }	
			return null;
    }
    
    @FXML
    public void closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	Produto produto = cadastrar();
    	if(produto != null) {
    		janela.close();
    	}   	
    }

	boolean checaCamposFill() {
		if(choiceBoxFornecedores.getSelectionModel().isEmpty() || choiceBoxUndMedida.getSelectionModel().isEmpty())
			throw new IllegalArgumentException("Todos os campos devem estar preenchidos");		
		return true;
	}
	

	
	
    
}
