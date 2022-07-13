package application.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Cliente;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Produto;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;
import application.model.facades.FacadePrincipal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class ControllerJanelaCadastroVenda implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private ItemCardapio item;
    private HashMap<String, CarrinhoDeCompra> carrinho = new HashMap<>();

    @FXML
    private Button buttonAddProduto;

    @FXML
    private Button buttonOK;
    
    @FXML
    private ComboBox<ItemCardapio> comboBoxItens;
    

    @FXML
    private ComboBox<Cliente> comboBoxClientes;

    @FXML
    private ComboBox<FormasDePagamento> comboBoxPagamento;

    @FXML
    private Label displayItens;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msgErro;

    @FXML
    private Spinner<Integer> spinnerQnt;
    
    private ObservableList<FormasDePagamento> formaPagamento = FXCollections.observableArrayList();
    ObservableList<ItemCardapio> listaItens = FXCollections.observableArrayList();
    ObservableList<Cliente> clientesCadastrados = FXCollections.observableArrayList();
    SpinnerValueFactory<Integer> quantidadeFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
    
    
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

    @FXML
    void adicionaItem(ActionEvent event) {
    	Integer qnt = spinnerQnt.getValue();
    	String stringItens = "";
    	
    	if(!comboBoxItens.getSelectionModel().isEmpty() ) {	
    		item = comboBoxItens.getValue();
    		CarrinhoDeCompra compra = sistema.criarCarrinhoDeCompras(item, qnt);
    		this.carrinho.put(compra.getId(), compra);
    		stringItens += " ";
    		stringItens +=  item.getNome(); 
    		displayItens.setText(stringItens);
    		
    	}
    	else 
    		msgErro.setText("Escolha um item");
    		  		
    }
    
    public boolean cadastrar() {
    	try {
       		Integer qnt;
	
    		msgErro.setText("");
    		FormasDePagamento pagamento = comboBoxPagamento.getSelectionModel().getSelectedItem();
    		qnt = spinnerQnt.getValue();
    		if(checaCamposFill()) {
    			Cliente cliente = comboBoxClientes.getValue();
    			sistema.cadastrarVenda(carrinho, pagamento, cliente);
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
		if(carrinho.isEmpty() || comboBoxPagamento.getSelectionModel().isEmpty() || comboBoxClientes.getSelectionModel().isEmpty())
			throw new IllegalArgumentException("Todos os campos devem estar preenchidos");		
		return true;
	}
}
