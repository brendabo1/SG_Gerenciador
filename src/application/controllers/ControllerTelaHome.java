package application.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ControllerTelaHome {
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

	public void carregarPagina(String pagina){
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pagina + ".fxml"));
			borderPane.setCenter(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

}
