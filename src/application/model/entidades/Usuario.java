package application.model.entidades;

import application.model.entidades.enums.Cargo;

/**
 * Entidade referente ao usuario do sistema.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class Usuario extends EntidadesDoSistema {
		private String nome;
		private String senha;
		private Cargo tipoDeUsuario;
		final static private String preFixo = "USR";
		
		/**
		 * Construtor do usuario.
		 * @param nome nome do usuario.
		 * @param senha senha do usuario.
		 * @param id identificacao do usuario.
		 */
		public Usuario (String nome, String senha, Cargo tipoDeUsario, String id) {
			this.nome = nome;
			this.senha = senha;
			this.tipoDeUsuario = tipoDeUsario;
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


		public Cargo getTipoDeUsuario() {
			return tipoDeUsuario;
		}
		public void setTipoDeUsuario(Cargo tipoDeUsuario) {
			this.tipoDeUsuario = tipoDeUsuario;
		}


		public static String getPrefixo() {
			return preFixo;
		}
}
