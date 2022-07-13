package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Lote;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerEstoque implements Initializable{
	
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Collection<Lote> listaEstoque;
    private ObservableList<Lote> observableListLote;   
    private Lote loteItem;
    
    @FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExcluir;

    @FXML
    private TableColumn<Lote, String> colunaID;

    @FXML
    private TableColumn<Lote, String> colunaNome;

    @FXML
    private TableColumn<Lote, Double> colunaPreco;

    @FXML
    private TableColumn<Lote, Double> colunaQntArmazenamento;

    @FXML
    private TableColumn<Lote, Double> colunaUndCompradas;

    @FXML
    private TableColumn<Lote, String> colunaValidade;

    @FXML
    private TableView<Lote> tabelaEstoque;

    @FXML
    private Button buttonRelatorio;
    
    @FXML
    private Label labelDisplay;

    private ControllerJanelaCadastroLote controllerPopUpCadastro;
    private ControllerJanelaEdicaoLote controllerPopUpEdicao;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	tabelaEstoque.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> itemSelecionado(newValue));
		carregarTabela();
		
	}
    
    public void carregarTabela() {
    	tabelaEstoque.getItems().clear();	
		colunaID.setCellValueFactory(new PropertyValueFactory<Lote, String>("id"));
		colunaNome.setCellValueFactory(cellData -> {			
			return new SimpleStringProperty(cellData.getValue().getProduto().getNome());
		});
		colunaPreco.setCellValueFactory(new PropertyValueFactory<Lote, Double>("preco"));
		colunaUndCompradas.setCellValueFactory(new PropertyValueFactory<Lote, Double>("unidades_compradas"));
		colunaValidade.setCellValueFactory(cellData -> {			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	        String validadeFormatada = cellData.getValue().getValidade().format(formatter);
			return new SimpleStringProperty(validadeFormatada);
		});
		colunaQntArmazenamento.setCellValueFactory(new PropertyValueFactory<Lote, Double>("quantidade_em_armazenamento"));
		tabelaEstoque.setItems(getListacardapio());
		
	}
	
	public ObservableList<Lote> getListacardapio() {
		listaEstoque =  bancoDados.getMap_estoque().values(); 
		observableListLote = FXCollections.observableArrayList(listaEstoque);
		return observableListLote;
	}
    
	public boolean itemSelecionado(Lote lote) {
		if(lote != null) {
			this.loteItem = lote;
			return true;
		}
		return false;
		
	}
    
    @FXML
    void openJanelaCadastro(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaCadastroLote.fxml"));
		Parent root = loader.load();
		controllerPopUpCadastro = loader.getController();
		setNovaJanela(mainStage, root, "Cadastro Lote");
		carregarTabela();
    }

    @FXML
    void openJanelaEdicao(ActionEvent event) throws IOException {
    	Stage mainStage = (Stage) buttonEdit.getScene().getWindow();
		loteItem = tabelaEstoque.getSelectionModel().getSelectedItem();
		try {
			if(itemSelecionado(loteItem) && checaPermissaoUsuarioLogado()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaEdicaoLote.fxml"));
				Parent root = loader.load();
				controllerPopUpEdicao = loader.getController();		
				controllerPopUpEdicao.setLote(loteItem);
				controllerPopUpEdicao.loadCampos(loteItem);
				setNovaJanela(mainStage, root, "Edicao Lote");
				carregarTabela();
			}
			else throw new NullPointerException("Escolha um lote na Tabela!");
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
    	loteItem = tabelaEstoque.getSelectionModel().getSelectedItem();
		try {		
			if(itemSelecionado(loteItem) && checaPermissaoUsuarioLogado()) 
				sistema.excluirLote(loteItem.getId());
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
    void gerarRelatorio(ActionEvent event) {
 		sistema.gerarPDFdeEstoque();
 		labelDisplay.setText("Relatorio gerado no diretorio D:");
    }

	
}