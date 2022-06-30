package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Usuario;
import application.model.subsistemtest.SubsistemUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerUsuarios implements Initializable {
   
	@FXML
	private Label msgErro;
	
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
    
    private Stage novaJanela;  
    
    private Collection<Usuario> listaUsuarios;    
    private ObservableList<Usuario> observableListUsuarios;    
    
    private SubsistemUsuario sistemUsuario = new SubsistemUsuario(new BancoDeDados());
    private HashMap<String, Usuario> map_usuarios = sistemUsuario.getMap_usuarios();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTabela();
	}
	
	public void carregarTabela() {
		sistemUsuario.carregaDados();
		colunaID.setCellValueFactory(new PropertyValueFactory<Usuario, String>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
		tabelaUsuarios.setItems(getListaUsuarios());
	}
	
	public ObservableList<Usuario> getListaUsuarios() {
		listaUsuarios = map_usuarios.values();
		observableListUsuarios = FXCollections.observableArrayList(new ArrayList<Usuario>(listaUsuarios));
		return observableListUsuarios;
	}
	
	@FXML
	public void openJanelaCadastro(ActionEvent event) {
		
		try {
			novaJanela = new Stage();
			Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/views/JanelaActionUsuario.fxml"));
			Scene sceneCadastro = new Scene(root);	
			novaJanela.setResizable(false);
			novaJanela.setTitle("Cadastro Usuario");
			novaJanela.getIcons().add(new Image("file:resources/add_user.png"));
			novaJanela.setScene(sceneCadastro);
			novaJanela.initModality(Modality.WINDOW_MODAL);
			novaJanela.initOwner(mainStage);
			novaJanela.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void openJanelaEdicao(ActionEvent event) {
		
		try {
			novaJanela = new Stage();
			Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/views/JanelaActionUsuario.fxml"));
			Scene sceneEdicao = new Scene(root);	
			novaJanela.setResizable(false);
			novaJanela.setTitle("Edicao Usuario");
			novaJanela.getIcons().add(new Image("file:resources/edit_pencil.png"));
			novaJanela.setScene(sceneEdicao);
			novaJanela.initModality(Modality.WINDOW_MODAL);
			novaJanela.initOwner(mainStage);
			novaJanela.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
