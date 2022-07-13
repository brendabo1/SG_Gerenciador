package application.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Produto;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.entidades.enums.CategoriasDeItens;
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

public class ControllerJanelaCadastroItemCardapio implements Initializable{
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Produto produto;
    private IngredienteDoItem ingrediente;
    private HashMap<String, IngredienteDoItem> ingredientes = new HashMap<>();
    
    @FXML
    private Button buttonAddProduto;

    @FXML
    private Button buttonOK;

    @FXML
    private TextField campoNome;

    @FXML
    private ChoiceBox<Produto> choiceBoxProdutos;
    
    @FXML
    private ChoiceBox<CategoriasDeItens> choiceBoxCategoria;

    @FXML
    private Label displayIngredientes;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msgErro;

    @FXML
    private Spinner<Double> spinnerQnt;
    
    @FXML
    private Spinner<Double> spinnerPreco;
    
    private String stringIngredientes =  "";
    
    private ObservableList<CategoriasDeItens> categorias = FXCollections.observableArrayList();
    ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    SpinnerValueFactory<Double> quantidadeFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000.0, 1);
    SpinnerValueFactory<Double> precoFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 1000.0, 1.0);
    
    private void loadSpinner() {
		this.spinnerQnt.setValueFactory(quantidadeFactory);
		this.spinnerQnt.setEditable(true);
		
		this.spinnerPreco.setValueFactory(precoFactory);
		this.spinnerPreco.setEditable(true);
	}
    
	private void loadChoiceBox() {		
		Collection<Produto> produtosCadastrados = bancoDados.getMap_produtos().values(); 
		listaProdutos = FXCollections.observableArrayList(produtosCadastrados);
		choiceBoxProdutos.getItems().addAll(listaProdutos);
		
		categorias.add(CategoriasDeItens.PRATO);
		categorias.add(CategoriasDeItens.BEBIDA);
		categorias.add(CategoriasDeItens.SOBREMESA);
    	choiceBoxCategoria.getItems().addAll(categorias);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadChoiceBox();
    	loadSpinner();
    	
	}
	
	@FXML
    void adicionaIngrediente(ActionEvent event) {	  	
    	Double qnt = spinnerQnt.getValue();
    	if(checaCamposFill()) {
    		produto = choiceBoxProdutos.getValue();
    		this.ingrediente = sistema.criarIngredienteDoItem(produto, qnt);
    		this.ingredientes.put(ingrediente.getId(), ingrediente);
    		this.stringIngredientes += " ";
    		this.stringIngredientes +=  ingrediente.getProduto().getNome();
    		displayIngredientes.setText(stringIngredientes);
    	}  	
    }
    
    
    public boolean cadastrar() {
    	try {
    		String nome;
    		Double preco;
    		
    		msgErro.setText("");
    		nome = campoNome.getText();
    		CategoriasDeItens categoria = choiceBoxCategoria.getSelectionModel().getSelectedItem();
    		preco = spinnerPreco.getValue();
    		if(checaCamposFill()) {
    			sistema.cadastrarItemDoCardapio(nome, this.ingredientes, preco, categoria);
        		return true;
    		}   		
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

	
	
	boolean checaCamposFill() {
		if(choiceBoxProdutos.getSelectionModel().isEmpty() || choiceBoxCategoria.getSelectionModel().isEmpty())
			throw new IllegalArgumentException("Todos os campos devem estar preenchidos");		
		return true;
	}

}