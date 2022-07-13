package application.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Cliente;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Produto;
import application.model.entidades.Venda;
import application.model.entidades.enums.Cargo;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;
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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class ControllerJanelaEdicaoVenda implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Venda venda;
    private ItemCardapio item; 
   
    private HashMap<String, CarrinhoDeCompra> carrinho = new HashMap<>();
    
    @FXML
    private Button buttonAddProduto;

    @FXML
    private Button buttonOK;
    
    @FXML
    private Button buttonRemove;
    
    @FXML
    private ComboBox<ItemCardapio> comboBoxItens;
    

    @FXML
    private ComboBox<Cliente> comboBoxClientes;

    @FXML
    private ComboBox<FormasDePagamento> comboBoxPagamento;

    //@FXML
    //private Label displayItens;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msgErro;

    @FXML
    private Spinner<Integer> spinnerQnt;
    
    Integer initalQnt = 1;
    private ObservableList<FormasDePagamento> formaPagamento = FXCollections.observableArrayList();
    ObservableList<ItemCardapio> listaItens = FXCollections.observableArrayList();
    ObservableList<Cliente> clientesCadastrados = FXCollections.observableArrayList();
    SpinnerValueFactory<Integer> quantidadeFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, initalQnt);
    String stringItens = "";
    
    private void loadSpinner() {
		this.spinnerQnt.setValueFactory(quantidadeFactory);
		
	}
    
    private void loadChoiceBox() {		
		Collection<ItemCardapio> itensCadastrados = bancoDados.getMap_itensCardapio().values(); 
		listaItens = FXCollections.observableArrayList(itensCadastrados);
		comboBoxItens.getItems().addAll(listaItens);
		
		Collection<Cliente> listaClientesCadastrados = bancoDados.getMap_clientes().values();
		clientesCadastrados = FXCollections.observableArrayList(listaClientesCadastrados);
		comboBoxClientes.getItems().addAll(clientesCadastrados);
		
		formaPagamento.add(FormasDePagamento.CREDITO);
		formaPagamento.add(FormasDePagamento.DEBITO);
		formaPagamento.add(FormasDePagamento.DINHEIRO);
		formaPagamento.add(FormasDePagamento.PIX);
		comboBoxPagamento.getItems().addAll(formaPagamento);
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		loadChoiceBox();
    	loadSpinner();		
	}
    
    public void loadCampos(Venda venda) {		
    	comboBoxPagamento.setValue(venda.getForma_de_pagamento());
    	comboBoxClientes.setValue(venda.getCliente());
		
	}
    
    
    @FXML
    void adicionaItem(ActionEvent event) {
    	Integer qnt = spinnerQnt.getValue();
    	this.carrinho = venda.getItens_comprados();
    	ItemCardapio itemSelecionado = comboBoxItens.getValue();
    	if(!this.carrinho.containsKey(itemSelecionado.getId()) && itemSelecionado != null) {
    		sistema.adicionarItemCompradoNaVenda(itemSelecionado, qnt, venda); 		
    	}
    	else 
    		msgErro.setText("Ops, não há quantidade no estoque");
    		  		
    }
    
    
    @FXML
    void removerItem(ActionEvent event) {
		ItemCardapio itemSelect = comboBoxItens.getSelectionModel().getSelectedItem();
		carrinho = venda.getItens_comprados();
		Collection<CarrinhoDeCompra> collectionCarrinhos = carrinho.values();
		Integer qnt = spinnerQnt.getValue();
		try {
			if(itemSelect != null) 	{
				
				for(CarrinhoDeCompra cadaCarrinhoDeCompra : collectionCarrinhos) {
					if(cadaCarrinhoDeCompra.getItem_comprado().getId().equals(itemSelect.getId()));
						sistema.removerItemCompradoNaVenda(cadaCarrinhoDeCompra.getId(), qnt, venda);
					}
			}		
		}catch (NullPointerException e) {
			msgErro.setText("Ops, não foi possivel remover essa quantidade");
		}
				
    }
    
    public boolean edicao() {
 	   Integer qnt;

 	   try {			   
 		    labelTitulo.setText("Edicao");
 		    msgErro.setText("");
 		    FormasDePagamento pagamento = comboBoxPagamento.getSelectionModel().getSelectedItem();
 		    qnt = spinnerQnt.getValue(); 
 		    Cliente cliente = comboBoxClientes.getValue();
 		    carrinho = venda.getItens_comprados();
     		if(checaCamposFill()) {
     			ItemCardapio itemSelecionado = comboBoxItens.getValue();
 				if(!pagamento.equals(venda.getForma_de_pagamento()))
 		   			sistema.editarFormaDePagamentoDaVenda(pagamento, venda);
 	    		
 	    		if(!cliente.getId().equals(venda.getCliente().getId()))
 	    			sistema.editarClienteDaVenda(cliente, venda);
 	    		  		
 	    		else throw new NullPointerException("Todos os campos devem estar preenchidos");
 			
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
	
    
    boolean checaCamposFill() {
		if(comboBoxPagamento.getSelectionModel().isEmpty() || comboBoxClientes.getSelectionModel().isEmpty() || comboBoxItens.getSelectionModel().isEmpty() )
			throw new IllegalArgumentException("Todos os campos devem estar preenchidos");		
		return true;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	

}
