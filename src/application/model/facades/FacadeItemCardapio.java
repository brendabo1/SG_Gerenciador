package application.model.facades;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Produto;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.gerenciadores.GerenciamentoItemCardapio;
import application.model.validacoes.ValidaItemCardapio;

public class FacadeItemCardapio {
		private GerenciamentoItemCardapio gerItem;
		private ValidaItemCardapio valItem;
		
		FacadeItemCardapio(BancoDeDados banco) {
			this.gerItem = new GerenciamentoItemCardapio(banco);
			this.valItem = new ValidaItemCardapio();
		}
			
		ItemCardapio cadastrarItemCardapio(String nome, HashMap<String, IngredienteDoItem> ingredientes, Double preco, CategoriasDeItens categoria) {
			if (!this.valItem.isNomeValido(nome))
				throw new IllegalArgumentException("Digite um nome com no m�nimo 3 caracteres.");
			
			if (!this.valItem.isPrecoValido(preco))
				throw new IllegalArgumentException("Digite um pre�o maior que 0.");
			
			ItemCardapio item = this.gerItem.cadastrar(nome, ingredientes, preco, categoria);
			if (item == null)
				throw new IllegalArgumentException("N�o foi poss�vel cadastrar o item do cardapio.");
			
			return item;
		}
		
		IngredienteDoItem criarIngrediente(Produto produto, Double quantidade_usada) {
			IngredienteDoItem ingrediente = this.gerItem.criarIngrediente(produto, quantidade_usada);
			if (ingrediente == null)
				throw new IllegalArgumentException("N�o foi poss�vel criar o ingrediente.");
		
			return ingrediente;
		}
		
		void editarNomeItem(String novo_nome, ItemCardapio item) {
			if (!this.valItem.isNomeValido(novo_nome))
				throw new IllegalArgumentException("Digite um nome com no m�nimo 3 caracteres.");
			
			if (!this.gerItem.editarNome(novo_nome, item))
				throw new IllegalArgumentException("N�o foi poss�vel editar o nome.");
		}
		
		void editarPrecoItem(Double novo_preco, ItemCardapio item) {
			if (!this.valItem.isPrecoValido(novo_preco))
				throw new IllegalArgumentException("Digite um pre�o maior que 0.");
			
			
			if (!this.gerItem.editarPreco(novo_preco, item))
				throw new IllegalArgumentException("N�o foi poss�vel editar o pre�o.");
		}
		
		void editarCategoriaDoItem(CategoriasDeItens nova_categoria, ItemCardapio item) {
			if (!this.gerItem.editarCategoria(nova_categoria, item))
				throw new IllegalArgumentException("N�o foi poss�vel editar a categoria do item.");
		}
		
		void editarProdutoDoIngrediente(Produto novo_produto, String ID, ItemCardapio item) {
			if (!this.gerItem.editarProdutoDoIngrediente(novo_produto, ID, item))
				throw new IllegalArgumentException("N�o foi poss�vel editar o produto do ingrediente.");
		}
		
		void editarQuantidadeDoIngrediente(Double nova_quantidade, String ID, ItemCardapio item) {
			if (!this.gerItem.editarQuantidadeDoIngrediente(nova_quantidade, ID, item))
				throw new IllegalArgumentException("N�o foi poss�vel editar a quantidade do ingrediente.");
		}
		
		void adicionarIngredienteNoItem(IngredienteDoItem novo_ingrediente, ItemCardapio item) {
			if (!this.gerItem.adicionarIngrediente(novo_ingrediente, item))
					throw new IllegalArgumentException("N�o foi poss�vel adicionar o ingrediente");
		}
		
		void removerIngredienteNoItem(String ID, ItemCardapio item) {
			if (!this.gerItem.excluirIngrediente(ID, item))
					throw new IllegalArgumentException("N�o foi poss�vel remover o ingrediente.");
		}
		
		void excluirItemDoCardapio(String ID) {
			if (!this.gerItem.excluir(this.gerItem.getMap_itemCardapio(), ID))
				throw new IllegalArgumentException("N�o foi poss�vel excluir o item do cardapio.");
		}
}