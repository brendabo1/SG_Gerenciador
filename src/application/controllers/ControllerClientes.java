package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Cliente;
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

public class ControllerClientes implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Collection<Cliente> listaClientes;
    private ObservableList<Cliente> observableListClientes;   
    private Cliente cliente;
    
    @FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExcluir;

    @FXML
    private TableColumn<Cliente, String> colunaCPF;

    @FXML
    private TableColumn<Cliente, String> colunaEmail;

    @FXML
    private TableColumn<Cliente, String> colunaID;

    @FXML
    private TableColumn<Cliente, String> colunaNome;

    @FXML
    private TableColumn<Cliente, String> colunaTelefone;

    @FXML
    private TableView<Cliente> tabelaClientes;

    
   private ControllerJanelaEdicaoCliente controllerPopUpEdicao;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	tabelaClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> clienteSelecionado(newValue));
		carregarTabela();
		
	}
    
    public void carregarTabela() {
    	tabelaClientes.getItems().clear();	
		colunaID.setCellValueFactory(new PropertyValueFactory<Cliente, String>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		colunaCPF.setCellValueFactory(new PropertyValueFactory<Cliente, String>("CPF"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
		tabelaClientes.setItems(getListaClientes());
		
	}
	
	public ObservableList<Cliente> getListaClientes() {
		listaClientes =  bancoDados.getMap_clientes().values();
		observableListClientes = FXCollections.observableArrayList(listaClientes);
		return observableListClientes;
	}

	public boolean clienteSelecionado(Cliente cliente) {
		if(cliente != null) {
			this.cliente = cliente;
			return true;
		}
		return false;
		
	}
	
    @FXML
    void openJanelaCadastro(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/views/JanelaCadastroCliente.fxml"));
		setNovaJanela(mainStage, root, "Cadastro Cliente");
		carregarTabela();
    }

    @FXML
    void openJanelaEdicao(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonEdit.getScene().getWindow();
		this.cliente = tabelaClientes.getSelectionModel().getSelectedItem();
		try {
			if(clienteSelecionado(cliente) && checaPermissaoUsuarioLogado()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaEdicaoCliente.fxml"));
				Parent root = loader.load();
				controllerPopUpEdicao = loader.getController();			
				controllerPopUpEdicao.loadCampos(cliente);
				setNovaJanela(mainStage, root, "Edicao Usuario");
				carregarTabela();
			}	
			else throw new NullPointerException("Escolha um usuario na Tabela!");
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
	
	@FXML
    void excluir(ActionEvent event) {
		cliente = tabelaClientes.getSelectionModel().getSelectedItem();
		
		try {		
			if(clienteSelecionado(cliente) && checaPermissaoUsuarioLogado()) 
				sistema.excluirCliente(cliente.getId());
			else throw new NullPointerException("Escolha um usuario na Tabela!");
		} catch (IllegalArgumentException e) {
			showAlerta("Impossivel excluir usuario logado");
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