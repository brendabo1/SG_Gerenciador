package application.model.entidades;

/**
 * Entidade referente ao usuario do sistema.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class Usuario extends EntidadesDoSistema {
		private String nome;
		private String senha;
		final static private String preFixo = "USR";
		
		/**
		 * Construtor do usuario.
		 * @param nome nome do usuario.
		 * @param senha senha do usuario.
		 * @param id identificacao do usuario.
		 */
		public Usuario (String nome, String senha, String id) {
			this.nome = nome;
			this.senha = senha;
			this.id = id;
		}
		
		
		@Override
		public String toString() {
			String message = String.format("\n%10s %10s", this.id, this.nome);
			return message;
		}


		public String linhaTituloToString() {
			String message = String.format("%10s %10s", "ID", "NOME");
			return message;
		}
		
		public String getNome() {
			return this.nome;
		}
		public void setNome(String novo_nome) {
			this.nome = novo_nome;
		}
	
		
		public String getSenha() {
			return this.senha;
		}


		public void setSenha(String senha) {
			this.senha = senha;
		}


		public static String getPrefixo() {
			return preFixo;
		}
}
