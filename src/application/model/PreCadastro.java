package application.model;

import java.time.LocalDate;
import java.util.HashMap;

import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Cliente;
import application.model.entidades.Fornecedor;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.entidades.enums.Cargo;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;
import application.model.entidades.enums.UnidadeMedida;
import application.model.gerenciadores.GerenciamentoCliente;
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
	GerenciamentoCliente gerCliente;
	
	public PreCadastro(BancoDeDados banco) {
		this.banco = banco;
		this.gerUsuario = new GerenciamentoUsuario(banco);
		this.gerFornecedor = new GerenciamentoFornecedor(banco);
		this.gerProduto = new GerenciamentoProduto(banco);
		this.gerLote = new GerenciamentoLote(banco);
		this.gerItemCardapio = new GerenciamentoItemCardapio(banco);
		this.gerVenda = new GerenciamentoVenda(banco);
		this.gerCliente = new GerenciamentoCliente(banco);
	}
	
	public void fornecedor() {
		gerFornecedor.cadastrar("Paulo", "91919191919123", "Rua Ansiedade");
		Fornecedor fornecedor1 = gerFornecedor.cadastrar("Antonio", "00012319191910", "Rua SemTempo");
		Fornecedor fornecedor2 = gerFornecedor.cadastrar("Ronaldo", "81231231000015", "Rua TerminaMI");
		Produto produto2 = gerProduto.cadastrar("Banana", fornecedor2, UnidadeMedida.KG, 8.0);
		gerProduto.cadastrar("Manga", fornecedor1, UnidadeMedida.KG, 0.5);
		Produto produto3 = gerProduto.cadastrar("Laranja", fornecedor2, UnidadeMedida.KG, 8.0);
	}
	
	public void itemCardapio() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Mario", "0101010101", "Viela da Amargura");
		Produto produto = gerProduto.cadastrar("Morango", fornecedor, UnidadeMedida.KG, 0.5);
		Produto produtoLeite = gerProduto.cadastrar("Leite", fornecedor, UnidadeMedida.L, 1.2);
		HashMap<String, IngredienteDoItem> ingredientes = new HashMap<>();
		IngredienteDoItem ingrediente1 = gerItemCardapio.criarIngrediente(produto, 2.5);
		IngredienteDoItem leite = gerItemCardapio.criarIngrediente(produtoLeite, 2.5);
		ingredientes.put(ingrediente1.getId(), ingrediente1);
		ingredientes.put(leite.getId(), leite);
		gerItemCardapio.cadastrar("Vitamina", ingredientes, 5.20, CategoriasDeItens.BEBIDA);

	}
	
	public void produtos() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Antonio", "9123213223", "Rua dos Passos");
		gerProduto.cadastrar("Manga", fornecedor, UnidadeMedida.KG, 0.5);
	}
	
	public void lote() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
		Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
		gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.now());
		
	}
	
	public void venda() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
		Produto produto = gerProduto.cadastrar("Mel", fornecedor, UnidadeMedida.KG, 8.0);
		Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
		
		HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
		IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 10.0);
		map_ingredientes.put(ingrediente.getId(), ingrediente);
		ItemCardapio item = gerItemCardapio.cadastrar("Bananada", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
		CarrinhoDeCompra carrinhoDeUmItem = gerVenda.criarCarrinhoDeCompra(item, 2);
		HashMap<String, CarrinhoDeCompra> map_compra = new HashMap<>();
		map_compra.put(carrinhoDeUmItem.getId(), carrinhoDeUmItem);
		Cliente cliente = gerCliente.cadastrar("Thiago", "12545688878", "batata@gmail.com", "78321624");
		gerVenda.cadastrar(map_compra, FormasDePagamento.PIX, cliente);
		
		//gerVenda.cadastrar(map_compra, FormasDePagamento.DEBITO);

	}
	
	public void usuarios() {
		gerUsuario.cadastrar("Maria", "Maria123", Cargo.FUNCIONARIO);
		gerUsuario.cadastrar("Brenda", "admin", Cargo.GERENTE);
		gerUsuario.cadastrar("Elmer", "admin", Cargo.GERENTE);
	}
	
	public void clientes() {
		gerCliente.cadastrar("Felipe", "11111111111", "algo@uefs.com", "12332112");
		gerCliente.cadastrar("Marta", "10210311041", "misericordia@uefs.com", "02597364");
		gerCliente.cadastrar("Miguel do Carmo", "05545688842", "mi@gmail.com", "78515624");
		
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
