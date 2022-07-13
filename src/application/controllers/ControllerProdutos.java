package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Produto;
import application.model.entidades.Usuario;
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

public class ControllerProdutos implements Initializable {
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Collection<Produto> listaProdutos;
    private ObservableList<Produto> observableListProdutos;   
    private Produto produto;	
	
	
	@FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExcluir;

    @FXML
    private TableColumn<Produto, Double> colunaConteudo;

    @FXML
    private TableColumn<Produto, String> colunaID;

    @FXML
    private TableColumn<Produto, String> colunaNome;

    @FXML
    private TableColumn<Produto, String> colunaUndMedida;

    @FXML
    private TableView<Produto> tabelaProdutos;


    private ControllerJanelaCadastroProduto controllerPopUpCadastro;
    private ControllerJanelaEdicaoProduto controllerPopUpEdicao;
       
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		tabelaProdutos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> produtoSelecionado(newValue));
		carregarTabela();
	}
	
	public void carregarTabela() {
		tabelaProdutos.getItems().clear();	
		colunaID.setCellValueFactory(new PropertyValueFactory<Produto, String>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
		colunaConteudo.setCellValueFactory(new PropertyValueFactory<Produto, Double>("conteudo_produto"));
		colunaUndMedida.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidade_medida().toString()));
		tabelaProdutos.setItems(getListaProdutos());
		
	}
	
	public ObservableList<Produto> getListaProdutos() {
		listaProdutos =  bancoDados.getMap_produtos().values();
		observableListProdutos = FXCollections.observableArrayList(listaProdutos);
		return observableListProdutos;
	}
	
	public boolean produtoSelecionado(Produto produto) {
		if(produto != null) {
			this.produto = produto;
			return true;
		}
		return false;
		
	}
	

    @FXML
    public void openJanelaCadastro(ActionEvent event) throws IOException {
    	carregarTabela();
    	Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/views/JanelaCadastroProduto.fxml"));
		setNovaJanela(mainStage, root, "Cadastro Produto");
		carregarTabela();
    }

    @FXML
    void openJanelaEdicao(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonEdit.getScene().getWindow();
		this.produto = tabelaProdutos.getSelectionModel().getSelectedItem();
		try {
			if(produtoSelecionado(produto) && checaPermissaoUsuarioLogado()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaEdicaoProduto.fxml"));
				Parent root = loader.load();
				controllerPopUpEdicao = loader.getController();			
				controllerPopUpEdicao.loadCampos(produto);
				setNovaJanela(mainStage, root, "Edicao Produto");
				carregarTabela();
			}	
			else throw new NullPointerException("Escolha um produto na Tabela!");
		} catch (IllegalAccessException e) {
			showAlerta(e.getMessage());
		} catch (IllegalArgumentException e2) {
			showAlerta(e2.getMessage());
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
    void excluir(ActionEvent event) {
		produto = tabelaProdutos.getSelectionModel().getSelectedItem();
		try {		
			if(produtoSelecionado(produto) && checaPermissaoUsuarioLogado()) 
				sistema.excluirProduto(produto.getId());
			else throw new NullPointerException("Escolha um produto na Tabela!");
		} catch (IllegalArgumentException e) {
			showAlerta(e.getMessage());
		} catch (IllegalAccessException error) {
			showAlerta(error.getMessage());
		} catch (NullPointerException nao_selecionado) {
			showAlerta(nao_selecionado.getMessage());
		}
		carregarTabela();
		
    }
	
}
