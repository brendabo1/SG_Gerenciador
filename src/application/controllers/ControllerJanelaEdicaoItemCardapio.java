package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Produto;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.UnidadeMedida;
import application.model.facades.FacadePrincipal;
import application.model.facades.FacadeUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaEdicaoItemCardapio implements Initializable{
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Produto produto;
    private IngredienteDoItem ingrediente;
    private ItemCardapio item; 
    private Double initalQnt = 1.0;
    private Double initalPreco = 1.0;
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
    private Button buttonRemoveProduto;

    @FXML
    private Spinner<Double> spinnerQnt;
    
    @FXML
    private Spinner<Double> spinnerPreco;
    
    private String stringIngredientes =  "";
    
    private ObservableList<CategoriasDeItens> categorias = FXCollections.observableArrayList();
    ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    SpinnerValueFactory<Double> quantidadeFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000.0, initalQnt);
    SpinnerValueFactory<Double> precoFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 1000.0, initalPreco);
	
    void loadSpinner() {
		this.spinnerQnt.setValueFactory(quantidadeFactory);
		this.spinnerQnt.setEditable(true);
		
		this.spinnerPreco.setValueFactory(precoFactory);
		this.spinnerPreco.setEditable(true);
	}
    
	void loadChoiceBox() {		
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
        
    public void loadCampos(ItemCardapio item) {
		this.item = item;
		labelTitulo.setText("Edicao");
		campoNome.setText(item.getNome());
		choiceBoxCategoria.setValue(item.getCategoria());
		initalPreco = item.getPreco();		
		choiceBoxProdutos.setValue(produto);
		this.ingredientes = item.getIngredientes();
		Collection<IngredienteDoItem> collectionIngredientes = item.getIngredientes().values();
		for(IngredienteDoItem ingrediente :collectionIngredientes) {
			this.produto = ingrediente.getProduto();
			this.stringIngredientes += ingrediente.getProduto().getNome();
			this.stringIngredientes += " ";
		}
		
		choiceBoxProdutos.setValue(produto);
		displayIngredientes.setText(stringIngredientes);
		
	}
    

	boolean checaCamposFill() {
		if(choiceBoxProdutos.getSelectionModel().isEmpty() || choiceBoxCategoria.getSelectionModel().isEmpty())
			throw new NullPointerException("Todos os campos devem estar preenchidos");		
		return true;
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
	
	@FXML
    void removerIngrediente(ActionEvent event) {
		produto = choiceBoxProdutos.getSelectionModel().getSelectedItem();		
		Collection<IngredienteDoItem> collectionIngredientes = item.getIngredientes().values();
		this.stringIngredientes = "";
		try {
			for(IngredienteDoItem ingrediente : collectionIngredientes) {
				if(ingrediente.getProduto().getId().equals(produto.getId())) 
					sistema.removerIngredienteNoItem(ingrediente.getId(), item);	
				}
			displayIngredientes.setText(this.ingredientes.values().toString());	
					
		}catch (NullPointerException e) {
			msgErro.setText("Ingrediente ainda não pertencente ao item");
		}
				
    }
	
	public boolean edicao() {
	   String nome;
	   Double qnt, preco;
	   Produto produtoSelecionado;

	   try {			   
		    labelTitulo.setText("Edicao");
		    msgErro.setText("");
	   		nome = campoNome.getText();
	   		CategoriasDeItens categ = choiceBoxCategoria.getSelectionModel().getSelectedItem();
	   		produtoSelecionado = choiceBoxProdutos.getSelectionModel().getSelectedItem();
	   		this.ingredientes = item.getIngredientes();
	   		IngredienteDoItem ingredienteBuscado = this.item.getIngredientes().get(produtoSelecionado.getId());
    		qnt = spinnerQnt.getValue();
    		preco = spinnerPreco.getValue();   
    		if(checaCamposFill()) {
				if(!nome.equals(item.getNome()))
		   			sistema.editarNomeDoItem(nome, item);
	    		
	    		if(!preco.equals(item.getPreco())) 
	    			sistema.editarPrecoDoItem(preco, item);
	    		  		
	    		if(!categ.equals(item.getCategoria()))
		   			sistema.editarCategoriaDoItem(categ, item);
	    		
	    		if(ingredienteBuscado != null) {
	    			if(!produtoSelecionado.getId().equals(ingredienteBuscado.getProduto().getId())) 
	        			sistema.editarQuantidadeDoIngrediente(qnt, ingredienteBuscado.getId(), item);
	        		
	        		if(!qnt.equals(ingredienteBuscado.getQuantidade_usada())) 
	        			sistema.editarQuantidadeDoIngrediente(qnt, ingredienteBuscado.getId(), item);
	    		}
			
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
	
}
