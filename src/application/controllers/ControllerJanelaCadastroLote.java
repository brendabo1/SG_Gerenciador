package application.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Produto;
import application.model.facades.FacadePrincipal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class ControllerJanelaCadastroLote implements Initializable{
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Produto produto;
      
    
    @FXML
    private Button buttonOK;

    @FXML
    private ComboBox<Produto> comBoxProdutos;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msgErro;

    @FXML
    private DatePicker pickerValidade;

    @FXML
    private Spinner<Double> spinnerPreco;

    @FXML
    private Spinner<Double> spinnerQnt;
    
    ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    SpinnerValueFactory<Double> quantidadeFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000.0, 1);
    SpinnerValueFactory<Double> precoFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5000.0, 10.0);
    
    
    private void loadSpinner() {
		this.spinnerQnt.setValueFactory(quantidadeFactory);
		this.spinnerQnt.setEditable(true);
		
		this.spinnerPreco.setValueFactory(precoFactory);
		this.spinnerPreco.setEditable(true);
	}
    
	private void loadChoiceBox() {		
		Collection<Produto> produtosCadastrados = bancoDados.getMap_produtos().values(); 
		listaProdutos = FXCollections.observableArrayList(produtosCadastrados);
		comBoxProdutos.getItems().addAll(listaProdutos);

    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	loadChoiceBox();
    	loadSpinner();
    	pickerValidade.setValue(LocalDate.now());
		
	}
    
    public boolean cadastrar() {
    	try {
    		Double preco, qnt;
    		LocalDate validade;
    		
    		msgErro.setText("");	
    		preco = spinnerPreco.getValue();
    		qnt = spinnerQnt.getValue();
    		validade = pickerValidade.getValue();
    		if(!comBoxProdutos.getSelectionModel().isEmpty()) {
    			this.produto = comBoxProdutos.getSelectionModel().getSelectedItem();
    			sistema.cadastrarLote(this.produto, qnt, preco, validade);
        		return true;
    		} 
    		else throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
    	}catch (InputMismatchException erroInvalido) {
			msgErro.setText(erroInvalido.getMessage());
    	}catch (IllegalArgumentException erro) {
			msgErro.setText(erro.getMessage());
	    
	    }	
			return false;
    }
    
    
    @FXML
    void closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	if(cadastrar()) {
    		janela.close();
    	}

    }
    

	

}