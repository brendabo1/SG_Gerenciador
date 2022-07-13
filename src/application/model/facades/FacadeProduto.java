package application.model.facades;

import java.util.InputMismatchException;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;
import application.model.entidades.enums.UnidadeMedida;
import application.model.gerenciadores.GerenciamentoProduto;
import application.model.validacoes.ValidaProduto;

public class FacadeProduto {
			private GerenciamentoProduto gerProduto;
			private ValidaProduto valProduto;
			
			FacadeProduto (BancoDeDados banco) {
				this.gerProduto = new GerenciamentoProduto(banco);
				this.valProduto = new ValidaProduto();
			}

		public Produto cadastrarProduto (String nome, Fornecedor fornecedor, UnidadeMedida uniMedida, Double conteudo_produto) {
			if  (!this.valProduto.isNomeValido(nome))
				throw new InputMismatchException("Digite um nome com no mínimo 3 caracteres");
			
			
			Produto novo_produto = this.gerProduto.cadastrar(nome, fornecedor, uniMedida, conteudo_produto);
			if  (novo_produto == null)
				throw new InputMismatchException("Houve um problema, cadastro não realizado");
			return novo_produto;
		}
		
		void editarNomeProduto (String novo_nome, Produto produto) {
			if  (!this.valProduto.isNomeValido(novo_nome))
				throw new IllegalArgumentException("Digite um nome com no mínimo 3 caracteres");
			
			if (!this.gerProduto.editarNome(novo_nome, produto))
				throw new IllegalArgumentException("Não foi possível editar o nome.");
		}
		
		void editarConteudoDoProduto (Double novo_conteudo, Produto produto) {
			if (!this.valProduto.isConteudoProdutoValido(novo_conteudo))
				throw new IllegalArgumentException("Conteudo do produto deve ser um numero real");
			
			if (!this.gerProduto.editarQuantidadeConteudo(novo_conteudo, produto))
				throw new IllegalArgumentException("Não foi possível editar o conteudo.");
		}
		
		void editarUnidadeDeMedidaDoProduto (UnidadeMedida nova_unidade, Produto produto) {
			if (!this.gerProduto.editarUnidadeMedida(nova_unidade, produto))
				throw new IllegalArgumentException("Não foi possível editar a unidade de medida.");
		}
		
		void excluirProduto (String ID) {
			if (!this.gerProduto.excluir(this.gerProduto.getMap_produtos(), ID))
				throw new IllegalArgumentException("Não foi possível apagar o produto.");
		}
		
}
