package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.PreCadastro;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.facades.FacadePrincipal;
import application.model.facades.FacadeUsuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerUsuarios implements Initializable {
   
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Collection<Usuario> listaUsuarios;
    private ObservableList<Usuario> observableListUsuarios;   
    private Usuario usuario;
    
	@FXML
	private Label msgErro;

	@FXML
    private Button buttonTesteAdd;
	
	@FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExcluir;
    
    @FXML
    private TableView<Usuario> tabelaUsuarios;
    
	@FXML
    private TableColumn<Usuario, String> colunaID;

    @FXML
    private TableColumn<Usuario, String> colunaNome; 

    @FXML
    private TableColumn<Usuario, String> colunaCargo;
       
    
    private ControllerJanelaEdicaoUsuario controllerPopUpEdicao;
       
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		tabelaUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> usuarioSelecionado(newValue));
		carregarTabela();
	}
	
	public void carregarTabela() {
		tabelaUsuarios.getItems().clear();	
		colunaID.setCellValueFactory(new PropertyValueFactory<Usuario, String>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
		colunaCargo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoDeUsuario().toString()));
		tabelaUsuarios.setItems(getListaUsuarios());
		
	}
	
	public ObservableList<Usuario> getListaUsuarios() {
		listaUsuarios =  bancoDados.getMap_usuarios().values();
		observableListUsuarios = FXCollections.observableArrayList(listaUsuarios);
		return observableListUsuarios;
	}
	
	
	
	@FXML
	public void openJanelaCadastro(ActionEvent event) throws IOException { //handleButtonAdd
		Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/views/JanelaCadastroUsuario.fxml"));
		setNovaJanela(mainStage, root, "Cadastro Usuario");
		carregarTabela();
	}
	
	@FXML
	public void openJanelaEdicao(ActionEvent event) throws IOException {
		Stage mainStage = (Stage) buttonEdit.getScene().getWindow();
		this.usuario = tabelaUsuarios.getSelectionModel().getSelectedItem();
		try {
			if(usuarioSelecionado(usuario) && checaPermissaoUsuarioLogado()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaEdicaoUsuario.fxml"));
				Parent root = loader.load();
				controllerPopUpEdicao = loader.getController();			
				controllerPopUpEdicao.loadCampos(usuario);
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
	
	public boolean usuarioSelecionado(Usuario usuario) {
		if(usuario != null) {
			this.usuario = usuario;
			return true;
		}
		return false;
		
	}
	
	@FXML
	public void excluirUsuario(ActionEvent event) {
		usuario = tabelaUsuarios.getSelectionModel().getSelectedItem();
		
		try {		
			if(usuarioSelecionado(usuario) && checaPermissaoUsuarioLogado()) 
				sistema.excluirUsuario(usuario.getId());
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
