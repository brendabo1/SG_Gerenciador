package application.testes.testeGerenciamentos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.entidades.enums.UnidadeMedida;
import application.model.gerenciadores.GerenciamentoFornecedor;
import application.model.gerenciadores.GerenciamentoLote;
import application.model.gerenciadores.GerenciamentoProduto;


public class TesteGerenciamentoLote {
		BancoDeDados banco = BancoDeDados.getInstance();
		GerenciamentoFornecedor gerFornecedor = new GerenciamentoFornecedor(banco);
		GerenciamentoProduto gerProduto = new GerenciamentoProduto(banco);
		GerenciamentoLote gerLote = new GerenciamentoLote(banco);
		
		@Test
		void testCriandoUmLote() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
			Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
			assertNotNull(gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21")));
		}
		
		@Test
		void testConsumirUmLote () {
			Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
			Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
			Lote lote = gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21"));
			assertTrue(gerLote.consumirLote(20.0, produto.getNome().toLowerCase()));
		}
		
		@Test
		void testEditarProdutoDoLote() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
			Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
			Lote lote = gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21"));
			
			Produto novo_produto = gerProduto.cadastrar("Arroz", fornecedor, UnidadeMedida.KG, 45.70);
			assertTrue(gerLote.editarProduto(novo_produto, lote));
		}
		
		@Test
		void testEditarQuantidadeCompradaDoLote() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
			Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
			Lote lote = gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21"));
			
			assertTrue(gerLote.editarQuantidadeComprada(45.0, lote));
		}
		
		@Test
		void testEditarPrecoUnitarioDoLote() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
			Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
			Lote lote = gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21"));
			
			assertTrue(gerLote.editarPrecoUnitario(86.75, lote));
		}
		
		@Test
		void testEditarValidarDoLote() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
			Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
			Lote lote = gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21"));
			
			assertTrue(gerLote.editarValidade(LocalDate.parse("2023-11-09"), lote));
		}
		
		@Test
		void testExcluirUmLote() {
			Fornecedor fornecedor = gerFornecedor.cadastrar("JoãozinDaQuebrada", "66666666666", "Batalhão do Amor");
			Produto produto = gerProduto.cadastrar("Farinha", fornecedor, UnidadeMedida.KG, 50.75);
			Lote lote = gerLote.cadastrar(produto, 75.0, 25.60, LocalDate.parse("2022-08-21"));
			
			assertTrue(gerLote.excluirLote(lote.getId()));
		}
		

}
