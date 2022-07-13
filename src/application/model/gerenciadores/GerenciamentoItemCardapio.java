package application.model.gerenciadores;

import java.util.ArrayList;
import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Produto;
import application.model.entidades.enums.CategoriasDeItens;

/**
 * Classe para aplicacao de operacoes sobre ItemCardapio.
 * @author Elmer Carvalho
 * @author Brenda Barbosa
 */
public class GerenciamentoItemCardapio extends GerenciamentoGeral{
		private HashMap<String, ItemCardapio> map_itemCardapio;
		private GerenciamentoLote gerLote;
	 	
		/**
		 * Construtor do gerenciamento de item cardapio.
		 * @param banco objeto para armazenamento das entidades.
		 * @param gerLote gerenciamento de lote.
		 */
	 	public GerenciamentoItemCardapio(BancoDeDados banco) {
	 		this.map_itemCardapio = banco.getMap_itensCardapio();
	 		this.gerLote = new GerenciamentoLote(banco);
	 	}
	 	
	 	/**
	 	 * Método para cadastro do Item do Cardapio.
	 	 * @param nome nome do item.
	 	 * @param ingredientes ingredientes que compoe o item.
	 	 * @param preco preco do item.
	 	 * @param categoria categoria do item.
	 	 * @return retorna o item caso consiga cadastrar e null caso nao consiga.
	 	 */
		public ItemCardapio cadastrar(String nome, HashMap<String, IngredienteDoItem> ingredientes, Double preco, CategoriasDeItens categoria) {
				String novo_id = gerarID(ItemCardapio.getPreFixo());
				ItemCardapio novo_itemCardapio = new ItemCardapio(novo_id, nome, ingredientes, preco, categoria);
				if (adicionar(map_itemCardapio, novo_itemCardapio))
					return novo_itemCardapio;
				return null;
		}
		
		public IngredienteDoItem criarIngrediente(Produto produto, Double quantidade_usada) {
			String novo_id = gerarID(IngredienteDoItem.getPreFixo());
			return new IngredienteDoItem(produto, quantidade_usada, novo_id);
		}
		
		/**
		 * Método para verificar se o item possui os ingredientes disponiveis para que seja vendido.
		 * @param item item que sera vendido.
		 * @param quantidade_item quantidade do item.
		 * @return retorna false se nao puder ser vendido e true caso possa.
		 */
		public boolean verificarSePodeSerVendido (ItemCardapio item, Integer quantidade_item) {
			for (IngredienteDoItem ingrediente : item.getIngredientes().values()) {
				String nome_produto = ingrediente.getProduto().getNome().toLowerCase();
				ArrayList<String> id_lotes = gerLote.getAgrupamentoDeLotes().get(nome_produto);
				
				if (id_lotes == null)
						return false;
				
				Double quantidadeNoEstoque = 0.0;
				for (String id : id_lotes) 
						quantidadeNoEstoque += gerLote.getMap_estoque().get(id).getQuantidade_em_armazenamento();
				
				if (quantidadeNoEstoque < (ingrediente.getQuantidade_usada() * quantidade_item))
						return false;
			}
			return true;
		}
		
		/**
		 * Método para edicao de nome do item.
		 * @param novo_nome novo nome do item.
		 * @param item item que sera modificado.
		 * @return return true se modificar e false se nao modificar.
		 */
		public boolean editarNome(String novo_nome, ItemCardapio item) {
				item.setNome(novo_nome);
				return item.getNome().equals(novo_nome);
		}
		
		/**
		 * Método para editar preco do item.
		 * @param novo_preco novo preco do item.
		 * @param item item que sera modificado.
		 * @return retorna true se modificar e false se nao modificar.
		 */
		public boolean editarPreco(Double novo_preco, ItemCardapio item) {
				item.setPreco(novo_preco);
				return item.getPreco().equals(novo_preco);
		}
		
		/**
		 * Método para editar a categoria do item.
		 * @param nova_categoria nova categoria do item.
		 * @param item item que sera modificado.
		 * @return retorna true se modificar e false se nao modificar.
		 */
		public boolean editarCategoria(CategoriasDeItens nova_categoria, ItemCardapio item) {
				item.setCategoria(nova_categoria);
				return (item.getCategoria() == nova_categoria);
		}
		
		/**
		 * Método para editar o produto usado como ingrediente.
		 * @param novo_produto novo produto que sera utilizado.
		 * @param id_selecionado identificacao do ingrediente especificado.
		 * @param item item que sera modificado.
		 * @return retorna true se modificar e false se nao modificar.
		 */
		public boolean editarProdutoDoIngrediente(Produto novo_produto, String id_selecionado, ItemCardapio item) {
			IngredienteDoItem ingrediente = item.getIngredientes().get(id_selecionado);
			if (ingrediente == null)
					return false;
			ingrediente.setProduto(novo_produto);
			return ingrediente.getProduto().equals(novo_produto);
		}
		
		/**
		 * Método para editar a quantidade usada de um ingrediente.
		 * @param nova_quantidade nova quantidade usada.
		 * @param id_selecionado identificao do ingrediente especificado.
		 * @param item item que sera modificado.
		 * @return retorna true se modificar e lança uma exceção se nao modificar.
		 */
		public boolean editarQuantidadeDoIngrediente(Double nova_quantidade, String id_selecionado, ItemCardapio item) throws IllegalArgumentException {
			IngredienteDoItem ingrediente = item.getIngredientes().get(id_selecionado);
			if (ingrediente == null) {
				throw new IllegalArgumentException();
			}		
			ingrediente.setQuantidade_usada(nova_quantidade);
			return ingrediente.getQuantidade_usada().equals(nova_quantidade);
		}
		
		/**
		 * Método para adicionar um ingrediente.
		 * @param novo_ingrediente novo ingrediente que sera adicionado.
		 * @param item item que sera modificado.
		 * @return retorna true se adicionar e false se nao adicionar.
		 */
		public boolean adicionarIngrediente(IngredienteDoItem novo_ingrediente, ItemCardapio item) {
			return adicionar(item.getIngredientes(), novo_ingrediente);
		}
		
		/**
		 * Método para excluir um ingrediente.
		 * @param id_selecionado identificacao do ingrediente.
		 * @param item item que sera modificado.
		 * @return retorna true se excluir e false se nao excluir.
		 */
		public boolean excluirIngrediente (String id_selecionado, ItemCardapio item) {
			return excluir(item.getIngredientes(), id_selecionado);
		}

		public HashMap<String, ItemCardapio> getMap_itemCardapio() {
			return map_itemCardapio;
		}

		public void setMap_itemCardapio(HashMap<String, ItemCardapio> map_itemCardapio) {
			this.map_itemCardapio = map_itemCardapio;
		}
		
}
