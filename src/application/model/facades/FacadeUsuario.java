package application.model.facades;

import java.util.InputMismatchException;

import application.model.BancoDeDados;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.gerenciadores.GerenciamentoUsuario;
import application.model.validacoes.ValidaUsuario;

public class FacadeUsuario {
			private GerenciamentoUsuario gerUsuario;
			private ValidaUsuario valUsuario;
			private static Usuario usuarioLogado;
			
			FacadeUsuario (BancoDeDados banco) {
				this.gerUsuario = new GerenciamentoUsuario(banco);
				this.valUsuario = new ValidaUsuario();			
			}
		

		public static Usuario getUsuarioLogado() {
				return usuarioLogado;
			}


		public static void setUsuarioLogado(Usuario usuarioLogado) {
			FacadeUsuario.usuarioLogado = usuarioLogado;
		}


		Usuario cadastrarUsuario (String nome, String senha, Cargo cargo) {
			if (!this.valUsuario.isNomeValido(nome))
				throw new IllegalArgumentException("Insira um nome com no m�nimo 3 letras ou n�meros.");
			
			if (!this.valUsuario.isSenhaValido(senha))
				throw new IllegalArgumentException("Insira uma senha com no m�nimo 4 caracteres.");
			
			Usuario novo_usuario = this.gerUsuario.cadastrar(nome, senha, cargo);
			if (novo_usuario == null)
				throw new IllegalArgumentException("Houve um problema, cadastro n�o realizado.");
			return novo_usuario;
		}
		
		void editarNomeUsuario (String novo_nome, Usuario usuario) {
			if (!this.valUsuario.isNomeValido(novo_nome))
				throw new IllegalArgumentException("Insira um nome com no m�nimo 3 letras ou n�meros.");
			
			if (!this.gerUsuario.editarNome(novo_nome, usuario))
				throw new IllegalArgumentException("N�o foi poss�vel editar o nome.");
		}
		
		void editarSenhaUsuario (String nova_senha, Usuario usuario) {
			if (!this.valUsuario.isSenhaValido(nova_senha))
				throw new IllegalArgumentException("Insira uma senha com no m�nimo 4 caracteres.");
			
			if (!this.gerUsuario.editarSenha(nova_senha, usuario))
				throw new IllegalArgumentException("N�o foi poss�vel editar a senha.");
		}
		
		void editarCargoUsuario(Cargo novo_cargo, Usuario usuario) {
			if (!this.gerUsuario.editarCargo(novo_cargo, usuario))
				throw new IllegalArgumentException("N�o foi poss�vel editar o cargo.");
		}
		
		void loginUsuario (String id, String senha) {		
			if (!this.gerUsuario.loginID(id, senha))
				throw new IllegalArgumentException("ID ou senha incorretos");
			usuarioLogado = gerUsuario.getUsuario_logado();
		}
		
		void excluirUsuario(String ID) {
			if (!this.gerUsuario.excluirUsuario(ID))
				throw new IllegalArgumentException("N�o foi poss�vel realizar a exclus�o.");
		}
		
		
		
}
