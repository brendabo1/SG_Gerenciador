package application.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.entidades.enums.UnidadeMedida;
import application.model.facades.FacadePrincipal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerJanelaEdicaoProduto implements Initializable{
	private BancoDeDados bancoDados = BancoDeDados.getInstance();
    private FacadePrincipal sistema = new FacadePrincipal(bancoDados);
    private Produto produto;
    private Double initalQnt = 100.5;
    
    @FXML
    private Button buttonOK;

    @FXML
    private TextField campoNome;


    @FXML
    private ChoiceBox<UnidadeMedida> choiceBoxUndMedida;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label msgErro;

    @FXML
    private Spinner<Double> spinnerQnt;

    
    ObservableList<Fornecedor> listaFornecedores = FXCollections.observableArrayList();
    ObservableList<UnidadeMedida> listaUndMedida = FXCollections.observableArrayList();
    SpinnerValueFactory<Double> quantidadeFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000.0, initalQnt);
    
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//loadCampos(produto);
		loadChoiceBox();
		loadSpinner();
		
	}

	private void loadChoiceBox() {
		listaUndMedida.add(UnidadeMedida.KG);
		listaUndMedida.add(UnidadeMedida.G);
		listaUndMedida.add(UnidadeMedida.L);
		listaUndMedida.add(UnidadeMedida.ML);
		listaUndMedida.add(UnidadeMedida.UNIDADE);
		choiceBoxUndMedida.getItems().addAll(listaUndMedida);
		
    }
    
	private void loadSpinner() {
		this.spinnerQnt.setValueFactory(quantidadeFactory);
		this.spinnerQnt.setEditable(true);
	}
	
	@FXML
    public void closeJanela(ActionEvent event) {
    	Stage janela = (Stage) buttonOK.getScene().getWindow();
    	if(edicao()) {
    		janela.close();
    	}   	
    }

	boolean checaCamposFill() {
		if(choiceBoxUndMedida.getSelectionModel().isEmpty())
			throw new IllegalArgumentException("Todos os campos devem estar preenchidos");		
		return true;
	}
	
	public boolean edicao() {
		   String nome;
		   Double qnt;

		   try {
			   labelTitulo.setText("Edicao");
			   msgErro.setText("");
		   		nome = campoNome.getText();
		   		UnidadeMedida und = choiceBoxUndMedida.getSelectionModel().getSelectedItem();
	    		qnt = spinnerQnt.getValue();
	    		if(!nome.equals(produto.getNome()))
		   			sistema.editarNomeProduto(nome, produto);
		   		
	    		if(!qnt.equals(produto.getConteudo_produto()))
		   			sistema.editarConteudoProduto(qnt, produto);
		   		
	    		if(!und.equals(produto.getUnidade_medida()))
		   			sistema.editarUnidadeMedidaDoProduto(und, produto);
	    		
	        		return true;	   		
			}catch (IllegalArgumentException erro2) {
				msgErro.setText(erro2.getMessage());
		    }catch (NullPointerException erro3) {
				msgErro.setText(erro3.getMessage());
			}
		   		return false;
	   }

	public void loadCampos(Produto prodSelecionado) {
		this.produto = prodSelecionado;
		//labelTitulo.setText("Edicao");
		campoNome.setText(prodSelecionado.getNome());
		initalQnt = prodSelecionado.getConteudo_produto();
		choiceBoxUndMedida.setValue(prodSelecionado.getUnidade_medida());
	}
	
	
}
