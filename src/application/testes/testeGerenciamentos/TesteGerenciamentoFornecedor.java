package application.testes.testeGerenciamentos;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.gerenciadores.GerenciamentoFornecedor;
import application.model.gerenciadores.GerenciamentoProduto;




class TesteGerenciamentoFornecedor {
		
	BancoDeDados banco = BancoDeDados.getInstance();;
	GerenciamentoFornecedor gerFornecedor = new GerenciamentoFornecedor(banco);
	GerenciamentoProduto gerProduto = new GerenciamentoProduto(banco);
	
	@Test
	void testCriandoUmFornecedor() {
		assertNotNull(gerFornecedor.cadastrar("Paulo", "91919191919", "Rua Ansiedade"));
	}

	@Test 
	void testEditarNomeDoFornecedor() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Antonio", "9191919191", "Avenida Cansado");
		assertTrue(gerFornecedor.editarNome("Gustavo", fornecedor));
	}
	
	@Test
	void testEditarCNPJdoFornecedor() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Antonio", "9191919191", "Praça VouTrancar");
		assertTrue(gerFornecedor.editarCNPJ("55555555555", fornecedor));
	}
	
	@Test
	void testEditarEnderecoDoFornecedor() {
		Fornecedor fornecedor = gerFornecedor.cadastrar("Antonio", "9191919191", "Rua SemTempo");
		assertTrue(gerFornecedor.editarEndereco("Rua PrecisoRespirar", fornecedor));
	}
	
	
}
