package application.model.entidades;

public class Cliente extends EntidadesDoSistema {
		private String nome;
		private String CPF;
		private String email;
		private String telefone;
		final private static String preFixo = "CLT";
		
			public Cliente(String nome, String CPF, String email, String telefone, String id) {
				this.nome = nome;
				this.CPF = CPF;
				this.email = email;
				this.telefone = telefone;
				this.id = id;
			}
		
		@Override
		public String toString() {
			String message = String.format("\n%10s %10s", this.id, this.nome);
			return message;
		}
		
		public String linhaTituloToString() {
			String message = String.format("%10s %10s %10s %10s %10s", "ID", "NOME", "CPF", "EMAIL", "TEL");
			return message;
		}

		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCPF() {
			return CPF;
		}
		public void setCPF(String cPF) {
			CPF = cPF;
		}

		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}

		public String getTelefone() {
			return telefone;
		}
		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}

		public static String getPrefixo() {
			return preFixo;
		}
}
