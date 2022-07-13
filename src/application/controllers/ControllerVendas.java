package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Venda;
import application.model.entidades.enums.Cargo;
import application.model.facades.FacadePrincipal;
import application.model.facades.FacadeUsuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerVendas implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Collection<Venda> listaItens;
    private ObservableList<Venda> observableListVendas;   
    private Venda venda;

    @FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExcluir;
    
    @FXML
    private Button buttonRelatorio;

    @FXML
    private TableColumn<Venda, String> colunaCliente;

    @FXML
    private TableColumn<Venda, String> colunaData;

    @FXML
    private TableColumn<Venda, String> colunaHora;

    @FXML
    private TableColumn<Venda, String> colunaID;

    @FXML
    private TableColumn<Venda, String> colunaItens;

    @FXML
    private TableColumn<Venda, Double> colunaPreco;
    
    @FXML
    private TableColumn<Venda, String> colunaPagamento;

    @FXML
    private TableView<Venda> tabelaVendas;

    private ControllerJanelaCadastroVenda controllerPopUpCadastro;
    private ControllerJanelaEdicaoVenda controllerPopUpEdicao;
    
   
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	tabelaVendas .getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> itemSelecionado(newValue));
		carregarTabela();
		
	}
    
    public void carregarTabela() {
		tabelaVendas.getItems().clear();	
		colunaID.setCellValueFactory(new PropertyValueFactory<Venda, String>("id"));		
		colunaData.setCellValueFactory(cellData -> {			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String dataFormatada = cellData.getValue().getData().format(formatter); 
			return new SimpleStringProperty(dataFormatada);
		});
		colunaHora.setCellValueFactory(cellData -> {			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	        String horaFormatada = cellData.getValue().getHora().format(formatter); 
	        return new SimpleStringProperty(horaFormatada);
		});
		colunaPreco.setCellValueFactory(new PropertyValueFactory<Venda, Double>("preco_total"));
		
		colunaItens.setCellValueFactory(cellData -> {
			Collection<CarrinhoDeCompra> carrinho = cellData.getValue().getItens_comprados().values();
			ArrayList<String> stringItens = new ArrayList<>(); 
			for(CarrinhoDeCompra item: carrinho) {
				stringItens.add(item.getItem_comprado().getNome());
			}
			return new SimpleStringProperty(stringItens.toString());
		});
		colunaPagamento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getForma_de_pagamento().toString()));
		colunaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome()));	
		tabelaVendas.setItems(getListaVendas());
		
	}
	
	public ObservableList<Venda> getListaVendas() {
		listaItens =  bancoDados.getMap_vendas().values();
		observableListVendas = FXCollections.observableArrayList(listaItens);
		return observableListVendas;
	}
    
	public boolean itemSelecionado(Venda venda) {
		if(venda != null) {
			this.venda = venda;
			return true;
		}
		return false;
		
	}
        

    @FXML
    void openJanelaCadastro(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaCadastroVenda.fxml"));
		Parent root = loader.load();
		controllerPopUpCadastro = loader.getController();
		setNovaJanela(mainStage, root, "Cadastro Venda");
		carregarTabela();
    }
    

    @FXML
    void openJanelaEdicao(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonEdit.getScene().getWindow();
		Venda venda = tabelaVendas.getSelectionModel().getSelectedItem();
		try {
			if(itemSelecionado(venda) && checaPermissaoUsuarioLogado()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaEdicaoVenda.fxml"));
				Parent root = loader.load();
				controllerPopUpEdicao = loader.getController();
				controllerPopUpEdicao.setVenda(venda);
				controllerPopUpEdicao.loadCampos(venda);
				setNovaJanela(mainStage, root, "Edicao Item do Cardapio");
				carregarTabela();
			}
			else throw new NullPointerException("Escolha um item na Tabela!");
		} catch (IllegalAccessException e) {
			showAlerta(e.getMessage());
		} catch (NullPointerException e3) {
			showAlerta(e3.getMessage());
		}
    }
    
    public void setNovaJanela(Stage mainStage, Parent root, String titulo) throws IOException {
		Stage novaJanela = new Stage();		
		
		Scene sceneEdicao = new Scene(root);	
		novaJanela.setResizable(false);
		novaJanela.setTitle(titulo);
		novaJanela.getIcons().add(new Image("file:resources/local_localstoreicon.png"));
		novaJanela.setScene(sceneEdicao);
		novaJanela.initModality(Modality.WINDOW_MODAL);
		novaJanela.initOwner(mainStage);
		novaJanela.showAndWait();
	}
    
    @FXML
    void excluir(ActionEvent event) {
    	venda = tabelaVendas.getSelectionModel().getSelectedItem();
		try {		
			if(itemSelecionado(venda) && checaPermissaoUsuarioLogado()) 
				sistema.excluirVenda(venda.getId());
			else throw new NullPointerException("Escolha um item no cardapio!");
		} catch (IllegalArgumentException e) {
			showAlerta(e.getMessage());
		} catch (IllegalAccessException error) {
			showAlerta(error.getMessage());
		} catch (NullPointerException nao_selecionado) {
			showAlerta(nao_selecionado.getMessage());
		}
		carregarTabela();
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
	

    @FXML
    void openJanelaRelatorio(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaRelatorioVenda.fxml"));
		Parent root = loader.load();
		setNovaJanela(mainStage, root, "Relatorio Venda");
		carregarTabela();
    }

}
