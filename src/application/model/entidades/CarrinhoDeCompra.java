package application.model.entidades;


/**
 * Classe utilizada para compor uma venda, indicando o item comprado, sua quantidade
 * e tamb�m o pre�o total.
 * 
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class CarrinhoDeCompra extends EntidadesDoSistema{
		private ItemCardapio item_comprado;
		private Integer quantidade_comprada;
		private Double preco_compras = 0.0;
		final static private String preFixo = "CAR";
		

		
		/**
		 * Cria uma entidade que representa a compra de um item.
		 * @param item_comprado Item que foi consumido.
		 * @param quantidade_comprada Quantidade comprada do determinado item.
		 * @param id Identifica��o �nica gerada.
		 */
		public CarrinhoDeCompra (String id, ItemCardapio item_comprado, Integer quantidade_comprada) {
				this.id = id;
				this.item_comprado = item_comprado;
				this.quantidade_comprada = quantidade_comprada;
				this.preco_compras += this.item_comprado.getPreco() * quantidade_comprada;
		}
		
		/**
		 * M�todo para acesso do item comprado.
		 * @return Item comprado.
		 */
		public ItemCardapio getItem_comprado() {
			return item_comprado;
		}
		
		/**
		 * M�todo para alterar o item comprado.
		 * @param item_comprado Novo item.
		 */
		public void setItem_comprado(ItemCardapio item_comprado) {
			this.item_comprado = item_comprado;
		}

		/**
		 * M�todo para acesso da quantidade comprada.
		 * @return Quantidade comprada.
		 */
		public Integer getQuantidade_comprada() {
			return quantidade_comprada;
		}
		
		/**
		 * M�todo para alterar a quantidade comprada.
		 * @param quantidade_comprada Nova quantidade comprada.
		 */
		public void setQuantidade_comprada(Integer quantidade_comprada) {
			this.quantidade_comprada = quantidade_comprada;
		}
		
		/**
		 * M�todo de acesso ao pre�o da compra.
		 * @return Pre�o da compra.
		 */
		public Double getPreco_compras() {
			return preco_compras;
		}
		
		/**
		 * M�todo para alterar o pre�o da compra.
		 * @param preco_compras Novo pre�o da compra.
		 */
		public void setPreco_compras(Double preco_compras) {
			this.preco_compras = preco_compras;
		}

		/**
		 * M�todo para acessar o preFixo.
		 * @return PreFixo.
		 */
		public static String getPreFixo() {
			return preFixo;
		}

		
		@Override
		public String toString() {
			return null;
		}
}
