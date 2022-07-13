package application.testes.testeGerenciamentos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Cliente;
import application.model.entidades.Fornecedor;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.entidades.Venda;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;
import application.model.entidades.enums.UnidadeMedida;
import application.model.gerenciadores.GerenciamentoCliente;
import application.model.gerenciadores.GerenciamentoFornecedor;
import application.model.gerenciadores.GerenciamentoItemCardapio;
import application.model.gerenciadores.GerenciamentoLote;
import application.model.gerenciadores.GerenciamentoProduto;
import application.model.gerenciadores.GerenciamentoVenda;




public class TesteGerenciamentoVenda {
		BancoDeDados banco = BancoDeDados.getInstance();
		GerenciamentoFornecedor gerFornecedor = new GerenciamentoFornecedor(banco);
		GerenciamentoProduto gerProduto = new GerenciamentoProduto(banco);
		GerenciamentoLote gerLote = new GerenciamentoLote(banco);
		GerenciamentoItemCardapio gerItemCardapio = new GerenciamentoItemCardapio(banco);
		GerenciamentoVenda gerVenda = new GerenciamentoVenda(banco);
		GerenciamentoCliente gerCliente = new GerenciamentoCliente(banco);
		
		@Test
		void testCriandoUmaVenda() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
			Produto produto = gerProduto.cadastrar("Banana", fornecedor, UnidadeMedida.KG, 8.0);
			Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
			
			HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
			IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 2.0);
			map_ingredientes.put(ingrediente.getId(), ingrediente);
			ItemCardapio item = gerItemCardapio.cadastrar("Bananada", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
			CarrinhoDeCompra carrinhoDeUmItem = gerVenda.criarCarrinhoDeCompra(item, 2);
			HashMap<String, CarrinhoDeCompra> map_compra = new HashMap<>();
			map_compra.put(carrinhoDeUmItem.getId(), carrinhoDeUmItem);
			Cliente cliente = gerCliente.cadastrar("Thiago", "12545688878", "batata@gmail.com", "78321624");
			assertNotNull(gerVenda.cadastrar(map_compra, FormasDePagamento.DEBITO, cliente));
		}
		
		
		@Test
		void testCriandoUmCarrinhoDeCompra() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
			Produto produto = gerProduto.cadastrar("Banana", fornecedor, UnidadeMedida.KG, 4.0);
			Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
			
			HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
			IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 2.0);
			map_ingredientes.put(ingrediente.getId(), ingrediente);
			ItemCardapio item = gerItemCardapio.cadastrar("Bananada", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
			
			assertNotNull(gerVenda.criarCarrinhoDeCompra(item, 2));
		}
		
		@Test
		void testEditarFormaDePagamento() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
			Produto produto = gerProduto.cadastrar("Banana", fornecedor, UnidadeMedida.KG, 4.0);
			Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
			
			HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
			IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 2.0);
			map_ingredientes.put(ingrediente.getId(), ingrediente);
			ItemCardapio item = gerItemCardapio.cadastrar("Bananada", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
			CarrinhoDeCompra carrinhoDeUmItem = gerVenda.criarCarrinhoDeCompra(item, 2);
			HashMap<String, CarrinhoDeCompra> map_compra = new HashMap<>();
			map_compra.put(carrinhoDeUmItem.getId(), carrinhoDeUmItem);
			Cliente cliente = gerCliente.cadastrar("Thiago", "12545688878", "batata@gmail.com", "78321624");
			Venda venda = gerVenda.cadastrar(map_compra, FormasDePagamento.DEBITO, cliente);
			
			assertTrue(gerVenda.editarFormaDePagamento(FormasDePagamento.CREDITO, venda));
		}
		
		@Test
		void testAdicionarItemComprado() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
			Produto produto = gerProduto.cadastrar("Arroz", fornecedor, UnidadeMedida.KG, 4.0);
			Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
			
			HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
			IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 2.0);
			map_ingredientes.put(ingrediente.getId(), ingrediente);
			ItemCardapio item = gerItemCardapio.cadastrar("Risoto", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
			CarrinhoDeCompra carrinhoDeUmItem = gerVenda.criarCarrinhoDeCompra(item, 2);
			HashMap<String, CarrinhoDeCompra> map_compra = new HashMap<>();
			map_compra.put(carrinhoDeUmItem.getId(), carrinhoDeUmItem);
			Cliente cliente = gerCliente.cadastrar("Thiago", "12545688878", "batata@gmail.com", "78321624");		
			Venda venda = gerVenda.cadastrar(map_compra, FormasDePagamento.DEBITO, cliente);
			
			Produto novo_produto = gerProduto.cadastrar("Xuxu", fornecedor, UnidadeMedida.KG, 4.0);
			IngredienteDoItem novo_ingrediente = gerItemCardapio.criarIngrediente(novo_produto, 1.5);
			HashMap<String, IngredienteDoItem> novo_map_ingredientes = new HashMap<>();
			novo_map_ingredientes.put(novo_ingrediente.getId(), novo_ingrediente);
			ItemCardapio novo_item = gerItemCardapio.cadastrar("Salada de Xuxu", novo_map_ingredientes, 34.67, CategoriasDeItens.BEBIDA);
			
			assertTrue(gerVenda.adicionarItemComprado(novo_item, 5, venda));
		}
		
		@Test
		void testRemoverUmItemDaCompra() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
			Produto produto = gerProduto.cadastrar("Banana", fornecedor, UnidadeMedida.KG, 4.0);
			Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
			
			HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
			IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 2.0);
			map_ingredientes.put(ingrediente.getId(), ingrediente);
			ItemCardapio item = gerItemCardapio.cadastrar("Bananada", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
			CarrinhoDeCompra carrinhoDeUmItem = gerVenda.criarCarrinhoDeCompra(item, 3);
			HashMap<String, CarrinhoDeCompra> map_compra = new HashMap<>();
			map_compra.put(carrinhoDeUmItem.getId(), carrinhoDeUmItem);
			Cliente cliente = gerCliente.cadastrar("Thiago", "12545688878", "batata@gmail.com", "78321624");
			
			Venda venda = gerVenda.cadastrar(map_compra, FormasDePagamento.DEBITO, cliente);
			
			Produto novo_produto = gerProduto.cadastrar("Melancia", fornecedor, UnidadeMedida.KG, 4.0);
			IngredienteDoItem novo_ingrediente = gerItemCardapio.criarIngrediente(novo_produto, 1.5);
			HashMap<String, IngredienteDoItem> novo_map_ingredientes = new HashMap<>();
			novo_map_ingredientes.put(novo_ingrediente.getId(), novo_ingrediente);
			ItemCardapio novo_item = gerItemCardapio.cadastrar("Salada de Melancia", novo_map_ingredientes, 34.67, CategoriasDeItens.BEBIDA);
			gerVenda.adicionarItemComprado(novo_item, 5, venda);
			
			assertTrue(gerVenda.removerItemComprado(carrinhoDeUmItem.getId(), 1, venda));
		}
		
		@Test
		void testRemoverUmaQuantidadeInexistenteNaCompra() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "81231231", "Rua TerminaMI");
			Produto produto = gerProduto.cadastrar("Banana", fornecedor, UnidadeMedida.KG, 4.0);
			Lote lote = gerLote.cadastrar(produto, 5.0, 2.50, LocalDate.parse("2022-06-22"));
			
			HashMap<String, IngredienteDoItem> map_ingredientes = new HashMap<>();
			IngredienteDoItem ingrediente = gerItemCardapio.criarIngrediente(produto, 2.0);
			map_ingredientes.put(ingrediente.getId(), ingrediente);
			ItemCardapio item = gerItemCardapio.cadastrar("Bananada", map_ingredientes, 13.40, CategoriasDeItens.SOBREMESA);
			CarrinhoDeCompra carrinhoDeUmItem = gerVenda.criarCarrinhoDeCompra(item, 2);
			HashMap<String, CarrinhoDeCompra> map_compra = new HashMap<>();
			map_compra.put(carrinhoDeUmItem.getId(), carrinhoDeUmItem);
			Cliente cliente = gerCliente.cadastrar("Thiago", "12545688878", "batata@gmail.com", "78321624");
			
			Venda venda = gerVenda.cadastrar(map_compra, FormasDePagamento.DEBITO, cliente);
			
			Produto novo_produto = gerProduto.cadastrar("Melancia", fornecedor, UnidadeMedida.KG, 4.0);
			IngredienteDoItem novo_ingrediente = gerItemCardapio.criarIngrediente(novo_produto, 1.5);
			HashMap<String, IngredienteDoItem> novo_map_ingredientes = new HashMap<>();
			novo_map_ingredientes.put(novo_ingrediente.getId(), novo_ingrediente);
			ItemCardapio novo_item = gerItemCardapio.cadastrar("Salada de Melancia", novo_map_ingredientes, 34.67, CategoriasDeItens.BEBIDA);
			gerVenda.adicionarItemComprado(novo_item, 5, venda);
			
			assertFalse(gerVenda.removerItemComprado(carrinhoDeUmItem.getId(), 100, venda));
		}
		
		
}
