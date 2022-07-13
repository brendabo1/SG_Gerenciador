package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerTelaHome implements Initializable{
    @FXML
    private BorderPane borderPane;
	
	@FXML
    private Button buttonCardapio;

    @FXML
    private Button buttonClientes;

    @FXML
    private Button buttonEstoque;

    @FXML
    private Button buttonFornecedores;

    @FXML
    private Button buttonProdutos;

    @FXML
    private Button buttonUsuarios;

    @FXML
    private Button buttonVendas;
    
    @FXML
    private Button botaoLogout;
    
    @FXML
    void pageUsuarios(ActionEvent event) {
    	carregarPagina("/application/views/TelaUsuarios");
    }

    @FXML
    void pageFornecedores(ActionEvent event) {
    	carregarPagina("/application/views/TelaFornecedores");
    }

    @FXML
    void pageCardapio(ActionEvent event) {
    	carregarPagina("/application/views/TelaCardapio");
    }

    @FXML
    void pageProdutos(ActionEvent event) {
    	carregarPagina("/application/views/TelaProdutos");
    }
    
    @FXML
    void pageEstoque(ActionEvent event) {
    	carregarPagina("/application/views/TelaEstoque");
    }
    
    @FXML
    void pageVendas(ActionEvent event) {
    	carregarPagina("/application/views/TelaVendas");
    }
    
    @FXML
    void pageClientes(ActionEvent event) {
    	carregarPagina("/application/views/TelaClientes");
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	setStage("/application/views/TelaLogin.fxml");
    	
    }

	public void carregarPagina(String pagina){
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pagina + ".fxml"));
			borderPane.setCenter(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
	
	public void setStage(String caminhoTela) throws IOException {
		Stage mainStage = (Stage) botaoLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource(caminhoTela));
		Scene sceneLogin = new Scene(root);
		sceneLogin.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		mainStage.setResizable(false);
		mainStage.setTitle("SG Gerenciador");
		mainStage.getIcons().add(new Image("file:resources/local_localstoreicon.png"));
		mainStage.setScene(sceneLogin);
		mainStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	carregarPagina("/application/views/TelaUsuarios");
		
	}
	
	

}
