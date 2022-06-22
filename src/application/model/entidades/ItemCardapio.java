package application.model.entidades;

import java.util.HashMap;

import application.model.entidades.enums.CategoriasDeItens;


/**
 * Entidade referente aos itens que compoe o cardapio.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class ItemCardapio extends EntidadesDoSistema {
		private String nome;
		private Double preco;
		private HashMap<String, IngredienteDoItem> ingredientes = new HashMap<>();
		private CategoriasDeItens categoria;
		final private static String preFixo = "ITM";

		
		/**
		 * Construtor do item.
		 * @param id Identifica��o do item.
		 * @param nome Nome do item.
		 * @param ingredientes HashMap dos ingredientes que compoe o item.
		 * @param preco Preco do item.
		 * @param categoria Categoria do item.
		 */
		public ItemCardapio(String id, String nome, HashMap<String, IngredienteDoItem> ingredientes, Double preco, CategoriasDeItens categoria) {
			this.id = id;
			this.nome = nome;
			this.ingredientes = ingredientes;
			this.preco = preco;
			this.categoria = categoria;
		} 

		
		@Override
		public String toString() {
			String message = String.format("\nID: %s  NOME: %s  PRE�O: %.2f  CATEGORIA: %s  \nINGREDIENTES: ", this.id, this.nome, this.preco, this.categoria.toString());
					
			for (IngredienteDoItem i: this.ingredientes.values()) 
					message +=  i.toString()+  " | "; 		
		
			return message;
		}


		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}


		public HashMap<String, IngredienteDoItem> getIngredientes() {
			return this.ingredientes;
		}
		public void setIngredientes(HashMap<String, IngredienteDoItem> ingredientes) {
			this.ingredientes = ingredientes;
		}


		public Double getPreco() {
			return preco;
		}
		public void setPreco(Double preco) {
			this.preco = preco;
		}


		public CategoriasDeItens getCategoria() {
			return categoria;
		}
		public void setCategoria(CategoriasDeItens categoria) {
			this.categoria = categoria;
		}


		public static String getPreFixo() {
			return preFixo;
		}
}