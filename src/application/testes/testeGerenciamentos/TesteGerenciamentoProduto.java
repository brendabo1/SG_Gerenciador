package application.testes.testeGerenciamentos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;
import application.model.entidades.enums.UnidadeMedida;
import application.model.gerenciadores.GerenciamentoFornecedor;
import application.model.gerenciadores.GerenciamentoProduto;


class TesteGerenciamentoProduto {
	BancoDeDados banco = BancoDeDados.getInstance();
	GerenciamentoFornecedor gerFornecedor = new GerenciamentoFornecedor(banco);
	GerenciamentoProduto gerProduto = new GerenciamentoProduto(banco);	
	
	@Test
	void testCriandoUmProduto() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Antonio", "9123213223", "Rua dos Passos");
		assertNotNull(gerProduto.cadastrar("Manga", fornecedor, UnidadeMedida.KG, 0.5));
	}
	
	@Test
	void testEditandoNome() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Antonio", "9123213223", "Rua dos Passos");
		Produto produto = gerProduto.cadastrar("Arroz", fornecedor, UnidadeMedida.G, 300.0);
		assertTrue(gerProduto.editarNome("Feijão", produto));
	}
	
	
	@Test
	void testEditandoUnidadeDeMedida() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "8888888888", "Praça de Amelia");
		Produto produto = gerProduto.cadastrar("Abobora", fornecedor, UnidadeMedida.G, 750.50);
		assertTrue(gerProduto.editarUnidadeMedida(UnidadeMedida.KG, produto));
	}
	
	@Test
	void testEditandoQuantidadeDoConteudo() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Ronaldo", "8888888888", "Praça de Amelia");
		Produto produto = gerProduto.cadastrar("Abobora", fornecedor, UnidadeMedida.G, 750.50);
		assertTrue(gerProduto.editarQuantidadeConteudo(840.20, produto));
	}
}
