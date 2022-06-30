package application.model.subsistemtest;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.PreCadastro;
import application.model.entidades.Usuario;
import application.model.gerenciadores.GerenciamentoUsuario;

public class SubsistemUsuario {
	private GerenciamentoUsuario gerenUsuario;
	private HashMap<String, Usuario> map_usuarios;
	private PreCadastro carrega;
	
	public SubsistemUsuario(BancoDeDados bancoDados) {
		this.gerenUsuario = new GerenciamentoUsuario(bancoDados);
		this.map_usuarios = this.gerenUsuario.getMap_usuarios();
		this.carrega = new PreCadastro(bancoDados);
	}
	
	public boolean login(String id, String senha) {
		return this.gerenUsuario.loginID(id, senha);
			
	}
	
	public void carregaDados() {
		this.carrega.usuarios();
	}
	
	public GerenciamentoUsuario getGerenUsuario() {
		return gerenUsuario;
	}

	public void setGerenUsuario(GerenciamentoUsuario gerenUsuario) {
		this.gerenUsuario = gerenUsuario;
	}

	public HashMap<String, Usuario> getMap_usuarios() {
		return map_usuarios;
	}

	public void setMap_usuarios(HashMap<String, Usuario> map_usuarios) {
		this.map_usuarios = map_usuarios;
	}
	
	
}
