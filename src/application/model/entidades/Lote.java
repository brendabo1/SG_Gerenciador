package application.model.entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Entidade referente a compra de produtos e suas quantidades.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class Lote extends EntidadesDoSistema {
		private Produto produto;
		private Double unidades_compradas;
		private Double preco;
		private LocalDate validade;
		private Double quantidade_em_armazenamento;
		final private static String preFixo = "LOT";
		
		/**
		 * Construtor da entidade lote.
		 * @param produto produto que compoe a compra do lote.
		 * @param quantidade_comprada quantidade comprada de produto.
		 * @param preco_unitario preco do produto.
		 * @param validade validade do produto.
		 * @param id identificacao do produto.
		 */
		public Lote (Produto produto, Double quantidade_comprada, Double preco_unitario, LocalDate validade, String id) {
				this.produto = produto;
				this.unidades_compradas = quantidade_comprada;
				this.preco = preco_unitario;
				this.validade = validade;
				this.id = id;
				this.quantidade_em_armazenamento = this.unidades_compradas + produto.getConteudo_produto();
		}
	
		
	
		@Override
		public String toString() {
			String validade = this.validade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String message = String.format("\n%5s %5s R$%4.2f %2.1f %10s", this.id, this.produto.getNome(), this.preco,  this.unidades_compradas, validade);
			return message;
		}
		
		public String linhaTituloToString() {
			String message = String.format("\n%2s %19s %22s %12s", "ID LOT", "PRODUTO", "PRE�O", "QNT", "VALIDADE");
			return message;
		}
	
	
		public Produto getProduto() {
			return produto;
		}	
		public void setProduto(Produto produto) {
			this.produto = produto;
		}
		
		
		public Double getPreco() {
			return preco;
		}
		public void setPreco(Double preco) {
			this.preco = preco;
		}
		
		
		public LocalDate getValidade() {
			return validade;
		}
		public void setValidade(LocalDate validade) {
			this.validade = validade;
		}
	
	
		public Double getUnidades_compradas() {
			return unidades_compradas;
		}
		public void setUnidades_compradas(Double unidades_compradas) {
			this.unidades_compradas = unidades_compradas;
		}
	
	
		public Double getQuantidade_em_armazenamento() {
			return quantidade_em_armazenamento;
		}
		public void setQuantidade_em_armazenamento(Double quantidade_em_armazenamento) {
			this.quantidade_em_armazenamento = quantidade_em_armazenamento;
		}



		public static String getPreFixo() {
			return preFixo;
		}
}
