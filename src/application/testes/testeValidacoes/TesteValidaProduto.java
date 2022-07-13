package application.testes.testeValidacoes;

import org.junit.jupiter.api.BeforeEach;

import application.model.BancoDeDados;
import application.model.entidades.enums.UnidadeMedida;
import application.model.gerenciadores.GerenciamentoFornecedor;
import application.model.gerenciadores.GerenciamentoProduto;

class TesteValidaProduto {
	
	@BeforeEach
	public BancoDeDados inicializandoProdutoBancoDeDados() {
		BancoDeDados banco_teste = BancoDeDados.getInstance();
		GerenciamentoFornecedor gerenFornecedor = new GerenciamentoFornecedor(banco_teste);
		//gerenFornecedor.cadastrar("Central das Massas", "11112312330001", "Rua da Paz, n201, Algoinhas");
		GerenciamentoProduto gerenProduto = new GerenciamentoProduto(banco_teste);
		gerenProduto.cadastrar("Molho de tomate", gerenFornecedor.cadastrar("Central das Massas", "11112312330001", "Rua da Paz, n201, Algoinhas"), UnidadeMedida.L, 1.0);
		return banco_teste;
	}


}
