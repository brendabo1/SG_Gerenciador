package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.PreCadastro;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;
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

public class ControllerFornecedores implements Initializable {
   
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Collection<Fornecedor> listaFornecedores;
    private ObservableList<Fornecedor> observableListFornecedores;   
    private Fornecedor fornecedor;
    
    @FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExcluir;

    @FXML
    private TableColumn<Fornecedor, String> colunaCNPJ;

    @FXML
    private TableColumn<Fornecedor, String> colunaEndereco;

    @FXML
    private TableColumn<Fornecedor, String> colunaID;

    @FXML
    private TableColumn<Fornecedor, String> colunaNome;

    @FXML
    private TableColumn<Fornecedor, String> colunaProdutos;

    @FXML
    private Label msgErro;
    
    @FXML
    private Label labelDisplay;
    
    @FXML
    private TableView<Fornecedor> tabelaFornecedores;

    
    private ControllerJanelaCadastroFornecedor controllerPopUpCadastro;
    private ControllerJanelaEdicaoFornecedor controllerPopUpEdicao;
       
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		tabelaFornecedores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> fornecedorSelecionado(newValue));
		carregarTabela();
	}
	
	public void carregarTabela() {
		tabelaFornecedores.getItems().clear();	
		colunaID.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("id"));
		colunaCNPJ.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("CNPJ"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("nome"));
		colunaEndereco.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("endereco"));
		colunaProdutos.setCellValueFactory(cellData -> {
			Collection<Produto> listaProdutos = cellData.getValue().getMap_produtosFornecidos().values();
			ArrayList<String> arrayProdutos = new ArrayList<>(); 
			for(Produto produto: listaProdutos) {
				arrayProdutos.add(produto.getNome());
			}
			return new SimpleStringProperty(arrayProdutos.toString());
		});
		tabelaFornecedores.setItems(getListaFornecedores());
		
	}
	
	public ObservableList<Fornecedor> getListaFornecedores() {
		listaFornecedores =  bancoDados.getMap_fornecedores().values();
		observableListFornecedores = FXCollections.observableArrayList(listaFornecedores);
		return observableListFornecedores;
	}
	
	
	
	@FXML
	public void openJanelaCadastro(ActionEvent event) throws IOException { 
		Stage mainStage = (Stage) buttonCadastrar.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaCadastroFornecedor.fxml"));
		Parent root = loader.load();
		controllerPopUpCadastro = loader.getController();			
		controllerPopUpCadastro.getLabelTitulo().setText("Cadastro");
		setNovaJanela(mainStage, root, "Cadastro Fornecedor");
		carregarTabela();
	}
	
	@FXML
	public void openJanelaEdicao(ActionEvent event) throws IOException {
		Stage mainStage = (Stage) buttonEdit.getScene().getWindow();
		fornecedor = tabelaFornecedores.getSelectionModel().getSelectedItem();
		try {
			if(fornecedorSelecionado(fornecedor) && checaPermissaoUsuarioLogado()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/JanelaEdicaoFornecedor.fxml"));
				Parent root = loader.load();
				controllerPopUpEdicao = loader.getController();			
				controllerPopUpEdicao.loadCampos(fornecedor);
				setNovaJanela(mainStage, root, "Edicao Fornecedor");
				carregarTabela();
			}
			else throw new NullPointerException("Escolha um fornecedor na Tabela!");
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
	
	public boolean fornecedorSelecionado(Fornecedor fornecedor) {
		if(fornecedor != null) {
			this.fornecedor = fornecedor;
			return true;
		}
		return false;
		
	}
		
	
	 @FXML
	 public void excluir(ActionEvent event){	
		try {	
			this.fornecedor = tabelaFornecedores.getSelectionModel().getSelectedItem();
			if(fornecedor != null && checaPermissaoUsuarioLogado()) 
				sistema.excluirFornecedor(fornecedor.getId());		
			else throw new NullPointerException("Escolha um fornecedor na Tabela!");
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
		sistema.gerarPDFdeFornecedores();
		labelDisplay.setText("Relatorio gerado no diretorio D:");
    }

}
