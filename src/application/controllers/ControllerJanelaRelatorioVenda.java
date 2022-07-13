package application.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import application.model.BancoDeDados;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Venda;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.facades.FacadePrincipal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class ControllerJanelaRelatorioVenda implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);


    @FXML
    private Button buttonGeral;

    @FXML
    private Button buttonPorCategoria;

    @FXML
    private Button buttonPorPeriodo;

    @FXML
    private DatePicker dataFinal;

    @FXML
    private DatePicker dataInicial;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msg;
    
    @FXML
    private ComboBox<CategoriasDeItens> comboBoxCategoria;
   
    ObservableList<CategoriasDeItens> categorias = FXCollections.observableArrayList();
       
    
    void loadComboBox() {		
		
		categorias.add(CategoriasDeItens.PRATO);
		categorias.add(CategoriasDeItens.BEBIDA);
		categorias.add(CategoriasDeItens.SOBREMESA);
		comboBoxCategoria.getItems().addAll(categorias);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadComboBox();
		
	}
	
    @FXML
    void gerarRelatorioGeral(ActionEvent event) {
		sistema.gerarPDFdeVendasGerais();
		msg.setText("Relatorio gerado no diretorio D:");
	}
	
    @FXML
    void gerarRelatorioPorCategoria(ActionEvent event) {
		sistema.gerarPDFdeVendasPorCategoria(comboBoxCategoria.getValue());
		msg.setText("Relatorio gerado no diretorio D:");
	}
	
    @FXML
    void gerarRelatorioPorPeriodo(ActionEvent event) {
		LocalDate data1, data2;
		data1 = dataInicial.getValue();
		data2 = dataFinal.getValue();
		sistema.gerarPDFdeVendasPorPeriodo(data1, data2);
		msg.setText("Relatorio gerado no diretorio D:");
	}
    
}
