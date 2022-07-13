package application.model.gerenciadores;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;

/**
 * Classe para a aplicacao de operacoes do usuario do sistema
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */

public class GerenciamentoUsuario extends GerenciamentoGeral {
		private HashMap<String, Usuario> map_usuarios;
		private static Usuario usuario_logado;
		
		/**
		 * Construtor do gerenciamento de usuario
		 * @param banco Classe responsavel por armazenar os dados dos usuarios
		 */
		public GerenciamentoUsuario(BancoDeDados banco) {
			this.map_usuarios = banco.getMap_usuarios();
			
			if (this.map_usuarios.isEmpty())
				usuario_logado = cadastrar("admin", "admin", Cargo.GERENTE);
			
		}
		
		/**
		 * Cadastra um usuario
		 * @param nome Nome do usuario a ser castrado
		 * @param senha Senha a ser cadastrada
		 * @return Novo Usuario ou null caso o castro nao seja realizado
		 */
		public Usuario cadastrar(String nome, String senha, Cargo tipoDeUsuario) {	
				String novo_id;
				if (this.map_usuarios.isEmpty())
						novo_id = Usuario.getPrefixo() + "00000";
				else
						novo_id = gerarID(Usuario.getPrefixo());
				
				Usuario novo_usuario = new Usuario(nome, senha, tipoDeUsuario, novo_id);
				if (adicionar(map_usuarios, novo_usuario))
					return novo_usuario;
				return null;
		}
		/**
		 * Verifica se os dados do usuario estao cadastrados para realizar o login
		 * @param id Identificacao do usuario
		 * @param senha Senha a ser comparada com a senha cadastrada
		 * @return true caso os dados estejam corretos ou flase caso o usuario não seja encontrado ou a senha nao corresponda
		 */
		public boolean loginID(String id, String senha) {
			Usuario usuario = this.map_usuarios.get(id);
			if (usuario == null)
				return false;
			
			if (usuario.getSenha().equals(senha)) {
				usuario_logado = usuario;
				return true;
			}
			return false;
		}
		/**
		 * Edita o nome do usuario cadastrado
		 * @param novo_nome Novo nome a ser vinculado ao usuario
		 * @param usuario Usuario a ser editado
		 * @return true se a edicao for bem sucedida e false caso falhe
		 */
		public boolean editarNome(String novo_nome, Usuario usuario) {
			usuario.setNome(novo_nome);
			return usuario.getNome().equals(novo_nome);
		}
		/**
		 * Edita a senha do usuario cadastrado
		 * @param nova_senha Nova senha a ser vinculada ao usuario
		 * @param usuario Usuario a ser editado
		 * @return
		 */
		public boolean editarSenha(String nova_senha, Usuario usuario) {
			usuario.setSenha(nova_senha);
			return usuario.getSenha().equals(nova_senha);
		}
		
		public boolean editarCargo (Cargo novo_cargo, Usuario usuario) {
			usuario.setTipoDeUsuario(novo_cargo);
			return usuario.getTipoDeUsuario().equals(novo_cargo);
		}
		
		public boolean excluirUsuario(String ID) {
			if (ID.equals(usuario_logado.getId()))
				return false;
			
			return excluir(this.map_usuarios, ID);
		}
		
		
		public HashMap<String, Usuario> getMap_usuarios() {
			return map_usuarios;
		}

		public Usuario getUsuario_logado() {
			return usuario_logado;
		}

		public void setUsuario_logado(Usuario usuario_logado) {
			usuario_logado = usuario_logado;
		}
				
}
