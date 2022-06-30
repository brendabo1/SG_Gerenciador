package application.model;

import java.time.LocalDate;
import java.util.HashMap;

import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Fornecedor;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;
import application.model.entidades.enums.UnidadeMedida;
import application.model.gerenciadores.GerenciamentoFornecedor;
import application.model.gerenciadores.GerenciamentoItemCardapio;
import application.model.gerenciadores.GerenciamentoLote;
import application.model.gerenciadores.GerenciamentoProduto;
import application.model.gerenciadores.GerenciamentoUsuario;
import application.model.gerenciadores.GerenciamentoVenda;

public class PreCadastro {
	BancoDeDados banco;
	GerenciamentoUsuario gerUsuario;
	GerenciamentoFornecedor gerFornecedor;
	GerenciamentoProduto gerProduto;
	GerenciamentoLote gerLote;
	GerenciamentoItemCardapio gerItemCardapio;
	GerenciamentoVenda gerVenda;
	
	public PreCadastro(BancoDeDados banco) {
		this.banco = banco;
		this.gerUsuario = new GerenciamentoUsuario(banco);
		this.gerFornecedor = new GerenciamentoFornecedor(banco);
		this.gerProduto = new GerenciamentoProduto(banco, gerFornecedor);
		this.gerLote = new GerenciamentoLote(banco);
		this.gerItemCardapio = new GerenciamentoItemCardapio(banco, gerLote);
		this.gerVenda = new GerenciamentoVenda(banco, gerItemCardapio, gerLote);
	}
	
	public void fornecedor() {
		gerFornecedor.cadastrar("Paulo", "91919191919", "Rua Ansiedade");
		gerFornecedor.cadastrar("Antonio", "1231919191", "Rua SemTempo");
	}
	
	public void itemCardapio() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Mario", "0101010101", "Viela da Amargura");
		Produto produto = gerProduto.cadastrar("Banana", fornecedor, UnidadeMedida.KG, 2.0);
		HashMap<String, IngredienteDoItem> ingredientes = new HashMap<>();
		IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 10.0);
		ingredientes.put(ingrediente.getId(), ingrediente);

	}
	
	public void produtos() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Antonio", "9123213223", "Rua dos Passos");
		gerProduto.cadastrar("Manga", fornecedor, UnidadeMedida.KG, 0.5);
	}
	
	public void lote() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
		Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
		gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21"));
		
	}
	
	public void venda() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
		Produto produto = gerProduto.cadastrar("Banana", fornecedor, UnidadeMedida.KG, 8.0);
		Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
		
		HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
		IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 10.0);
		map_ingredientes.put(ingrediente.getId(), ingrediente);
		ItemCardapio item = gerItemCardapio.cadastrar("Bananada", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
		CarrinhoDeCompra carrinhoDeUmItem = gerVenda.criarCarrinhoDeCompra(item, 2);
		HashMap<String, CarrinhoDeCompra> map_compra = new HashMap<>();
		map_compra.put(carrinhoDeUmItem.getId(), carrinhoDeUmItem);
		
		gerVenda.cadastrar(map_compra, FormasDePagamento.DEBITO);

	}
	
	public void usuarios() {
		gerUsuario.cadastrar("Maria", "Maria123");
		gerUsuario.cadastrar("Brenda", "admin");
		gerUsuario.cadastrar("Elmer", "admin ");
	}
	
	public void preenchendoListas() {
		Fornecedor fornecedor1 = gerFornecedor.cadastrar("Mario", "0101010101", "Viela da Amargura");
		Produto banana = gerProduto.cadastrar("Banana", fornecedor1, UnidadeMedida.KG, 8.0);
		HashMap<String, IngredienteDoItem> ingredientes = new HashMap<>();
		IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(banana, 10.0);
		ingredientes.put(ingrediente.getId(), ingrediente);
		
		Fornecedor fornecedor2 = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
		Produto farinha = gerProduto.cadastrar("Farinha", fornecedor2, UnidadeMedida.KG, 50.75);
		gerLote.cadastrar(farinha, 75.0, 25.60, LocalDate.parse("2022-08-21"));
		
		Fornecedor fornecedor3 = gerFornecedor.cadastrar("Antonio", "9123213223", "Rua dos Passos");
		gerProduto.cadastrar("Manga", fornecedor3, UnidadeMedida.KG, 0.5);
	}
	
	

}
