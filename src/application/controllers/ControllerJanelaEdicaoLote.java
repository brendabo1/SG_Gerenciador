package application.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.entidades.enums.Cargo;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.facades.FacadePrincipal;
import application.model.facades.FacadeUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class ControllerJanelaEdicaoLote implements Initializable{

	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Produto produto;
    private Lote lote;
    Double initalPreco = 0.5;	
    double initalQnt = 2.0;
    
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
    SpinnerValueFactory<Double> quantidadeFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000.0, initalQnt);
    SpinnerValueFactory<Double> precoFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5000.0, initalPreco);
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	loadChoiceBox();
    	loadSpinner();
    	
    	//pickerValidade.setValue(LocalDate.now());
		
	}
    
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
 	
 	public void loadCampos(Lote lote) {
 		initalPreco = lote.getPreco();
 		initalQnt = lote.getUnidades_compradas();
 		comBoxProdutos.setValue(lote.getProduto());		
 		pickerValidade.setValue(lote.getValidade());	
 		
 	}
 	
 	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public boolean edicao() {
 	   Double qnt, preco;
 	   Produto produtoSelecionado;
 	   LocalDate validade;

 	   try {
 		   this.produto = lote.getProduto();
	  		msgErro.setText("");	
	  		preco = spinnerPreco.getValue();
	  		qnt = spinnerQnt.getValue();
	  		validade = pickerValidade.getValue();   
	  		if(!comBoxProdutos.getSelectionModel().isEmpty()) {
	  			produtoSelecionado = comBoxProdutos.getSelectionModel().getSelectedItem();
	  			if(!this.produto.getId().equals(produtoSelecionado.getId()))
	  				sistema.editarProdutoDoLote(produtoSelecionado, lote);
	  			if(!qnt.equals(lote.getUnidades_compradas()))
	  				sistema.editarQuantidadeDoLote(qnt, lote);;
	  			if(!preco.equals(lote.getPreco()))
	  				sistema.editarPrecoUnitarioDoLote(preco, lote);
	  			if(!validade.equals(lote.getValidade()))
	  				sistema.editarPrecoUnitarioDoLote(preco, lote);
 	    		return true;	
     		}
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
    	if(edicao()) {
    		janela.close();
    	}
    }
    
    
    public boolean checaPermissaoUsuarioLogado() throws IllegalAccessException {
		Cargo cargoUsuario;
		cargoUsuario = FacadeUsuario.getUsuarioLogado().getTipoDeUsuario();
		if(cargoUsuario.equals(Cargo.FUNCIONARIO))
			throw new IllegalAccessException("Essa função não está habilitada para funcionários");
		else return true;
	}
	
	public void showAlerta(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
	}

	public Double getInitalPreco() {
		return initalPreco;
	}

	public void setInitalPreco(Double initalPreco) {
		this.initalPreco = initalPreco;
	}

	public double getInitalQnt() {
		return initalQnt;
	}

	public void setInitalQnt(double initalQnt) {
		this.initalQnt = initalQnt;
	}
	

}
