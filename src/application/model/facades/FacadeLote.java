package application.model.facades;

import java.time.LocalDate;

import application.model.BancoDeDados;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.gerenciadores.GerenciamentoLote;
import application.model.validacoes.ValidaProduto;

public class FacadeLote {
			private GerenciamentoLote gerLote;
			private ValidaProduto valProduto;
			
			FacadeLote(BancoDeDados banco) {
				this.gerLote = new GerenciamentoLote(banco);
				this.valProduto = new ValidaProduto();
			}
			
		Lote cadastrarLote(Produto produto, Double quantidade_comprada, Double preco_unitario, LocalDate validade) {
			if (!this.valProduto.isConteudoProdutoValido(quantidade_comprada))
				throw new IllegalArgumentException("Digite uma quantidade maior que 0.");
			
			if (!this.valProduto.isConteudoProdutoValido(preco_unitario))
				throw new IllegalArgumentException("Digite um preço maior que 0.");
			
			Lote lote = this.gerLote.cadastrar(produto, quantidade_comprada, preco_unitario, validade);
			if (lote == null)
				throw new IllegalArgumentException("Não foi possível cadastrar o lote.");
			
			return lote;
		}
		
		void editarProdutoDoLote(Produto novo_produto, Lote lote) {
			if (!this.gerLote.editarProduto(novo_produto, lote))
				throw new IllegalArgumentException("Não foi possível editar o lote.");
		}
		
		void editarQuantidadeCompradaNoLote(Double nova_quantidade, Lote lote) {
			if (!this.valProduto.isConteudoProdutoValido(nova_quantidade))
				throw new IllegalArgumentException("Digite uma quantidade maior que 0.");
			
			if (!this.gerLote.editarQuantidadeComprada(nova_quantidade, lote))
				throw new IllegalArgumentException("Não foi possível editar a quantidade comprada.");
		}
		
		void editarPrecoUnitarioDoLote(Double novo_preco, Lote lote) {
			if (!this.valProduto.isConteudoProdutoValido(novo_preco))
				throw new IllegalArgumentException("Digite um preço maior que 0.");
			
			if (!this.gerLote.editarPrecoUnitario(novo_preco, lote))
				throw new IllegalArgumentException("Não foi possível editar o preço.");
		}
		
		void editarValidadeDoLote(LocalDate nova_validade, Lote lote) {
			if (!this.gerLote.editarValidade(nova_validade, lote))
				throw new IllegalArgumentException("Não foi possível editar a validade.");
		}
		
		void excluirLote(String ID) {
			if (!this.gerLote.excluirLote(ID))
				throw new IllegalArgumentException("Não foi possível excluir o lote.");
		}
		
		void gerarPDFdeEstoque() {
			String texto = this.gerLote.gerarStringOrdenadaDoEstoqueAVencer();
			if (texto == null) 
				throw new IllegalArgumentException("Não foi possível gerar o relatório");
			
			this.gerLote.gerarPDF("Relatorio Estoque", texto);
		}
}
