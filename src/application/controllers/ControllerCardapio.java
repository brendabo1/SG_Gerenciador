package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Produto;
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

public class ControllerCardapio implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Collection<ItemCardapio> listaCardapio;
    private ObservableList<ItemCardapio> observableListCardapio;   
    private ItemCardapio item;

    @FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExcluir;

    @FXML
    private TableColumn<ItemCardapio, String> colunaCategoria;

    @FXML
    private TableColumn<ItemCardapio, String> colunaID;

    @FXML
    private TableColumn<ItemCardapio, String> colunaIngredientes;

    @FXML
    private TableColumn<ItemCardapio, String> colunaNome;

    @FXML
    private TableColumn<ItemCardapio, Double> colunaPreco;

    @FXML
    private TableView<ItemCardapio> tabelaCardapio;


    private ControllerJanelaCadastroItemCardapio controllerPopUpCadastro;
    private ControllerJanelaEdicaoItemCardapio controllerPopUpEdicao;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {	
		tabelaCardapio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> itemSelecionado(newValue));
		carregarTabela();
	}
	
	public void carregarTabela() {
		tabelaCardapio.getItems().clear();	
		colunaID.setCellValueFactory(new PropertyValueFactory<ItemCardapio, String>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<ItemCardapio, String>("nome"));
		colunaPreco.setCellValueFactory(new PropertyValueFactory<ItemCardapio, Double>("preco"));
		colunaIngredientes.setCellValueFactory(cellData -> {
			Collection<IngredienteDoItem> listaIngredientes = cellData.getValue().getIngredientes().values();
			ArrayList<String> arrayIngredientes = new ArrayList<>(); 
			for(IngredienteDoItem ingrediente: listaIngredientes) {
				arrayIngredientes.add(ingrediente.getProduto().getNome());
			}
			return new SimpleStringProperty(arrayIngredientes.toString());
		});
		colunaCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().toString()));
		tabelaCardapio.setItems(getListacardapio());
		
	}
	
	public ObservableList<ItemCardapio> getListacardapio() {
		listaCardapio =  bancoDados.getMap_itensCardapio().values();
		observableListCardapio = FXCollections.observableArrayList(listaCardapio);
		return observableListCardapio;
	}
    
	public boolean itemSelecionado(ItemCardapio item) {
		if(item != null) {
			this.item = item;
			return true;
		}
		return false;
		
	}
    
   
	@FXML
	public void openJanelaCadastro(ActionEvent event) throws IOException { 
		Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaCadastroItemCardapio.fxml"));
		Parent root = loader.load();
		controllerPopUpCadastro = loader.getController();
		setNovaJanela(mainStage, root, "Cadastro Item do Cardapio");
		carregarTabela();
	}
	
	@FXML
	public void openJanelaEdicao(ActionEvent event) throws IOException {
		Stage mainStage = (Stage) buttonEdit.getScene().getWindow();
		item = tabelaCardapio.getSelectionModel().getSelectedItem();
		try {
			if(itemSelecionado(item) && checaPermissaoUsuarioLogado()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaEdicaoItemCardapio.fxml"));
				Parent root = loader.load();
				controllerPopUpEdicao = loader.getController();			
				controllerPopUpEdicao.loadCampos(item);
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
    	item = tabelaCardapio.getSelectionModel().getSelectedItem();
		try {		
			if(itemSelecionado(item) && checaPermissaoUsuarioLogado()) 
				sistema.excluirItemDoCardapio(item.getId());
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

}