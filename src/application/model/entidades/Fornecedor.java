package application.model.entidades;

import java.util.HashMap;
import java.util.Objects;


/**
 * Entidade referente ao fornecedor de produtos do sistema.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class Fornecedor extends EntidadesDoSistema {
		private String CNPJ;
		private String nome;
		private String endereco;
		private HashMap<String, Produto> map_produtosFornecidos = new HashMap<>();
		final private static String preFixo = "FOR";
		
		/**
		 * Construtor da entidade fornecedor.
		 * @param id Identificação única da entidade.
		 * @param CNPJ cnpj do fornecedor.
		 * @param nome nome do fornecedor.
		 * @param endereco endereço do fornecedor.
		 */
		public Fornecedor(String id, String CNPJ, String nome, String endereco) {
			this.id = id;
			this.CNPJ = CNPJ;
			this.nome = nome;
			this.endereco = endereco;
		}
	
		
		
		@Override
		public String toString() {
			String message = String.format("\n%5s %5s %5s %5s", this.id, this.CNPJ, this.nome, this.endereco);
			return message;
		}
	

		public String linhaTituloToString() {
			String message = String.format("\n%2s %19s %22s %12s", "ID", "CNPJ", "NOME", "ENDERECO");
			return message;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Fornecedor other = (Fornecedor) obj;
			return Objects.equals(CNPJ, other.CNPJ) && Objects.equals(endereco, other.endereco)
					&& Objects.equals(map_produtosFornecidos, other.map_produtosFornecidos)
					&& Objects.equals(nome, other.nome);
		}



		public String getCNPJ() {
			return CNPJ;
		}
		public void setCNPJ(String CNPJ) {
			this.CNPJ = CNPJ;
		}

		
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}

		
		public String getEndereco() {
			return endereco;
		}
		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}


		public HashMap<String, Produto> getMap_produtosFornecidos() {
			return map_produtosFornecidos;
		}


		public static String getPreFixo() {
			return preFixo;
		}
}
