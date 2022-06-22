package application.model.subsistemtest;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.Usuario;
import application.model.gerenciadores.GerenciamentoUsuario;

public class SubsistemUsuario {
	private GerenciamentoUsuario gerenUsuario;
	private HashMap<String, Usuario> map_usuarios;
	
	public SubsistemUsuario(BancoDeDados bancoDados) {
		this.gerenUsuario = new GerenciamentoUsuario(bancoDados);
		this.map_usuarios = this.gerenUsuario.getMap_usuarios();
	}
	
	public boolean login(String id, String senha) {
		return this.gerenUsuario.loginID(id, senha);
			
	}
	
}
