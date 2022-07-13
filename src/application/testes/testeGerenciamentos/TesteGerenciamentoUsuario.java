package application.testes.testeGerenciamentos;



import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import application.model.BancoDeDados;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;
import application.model.gerenciadores.GerenciamentoUsuario;




class TesteGerenciamentoUsuario {
			BancoDeDados banco = BancoDeDados.getInstance();;
			GerenciamentoUsuario gerUsuario = new GerenciamentoUsuario(banco);
	
	
	@Test
	void testCriandoUsuario() {
		assertNotNull(gerUsuario.cadastrar("Maria", "Maria123", Cargo.FUNCIONARIO));
	}
	
	@Test
	void testFazendoLoginDeUsuario() {
		Usuario novo_usuario = gerUsuario.cadastrar("Ronaldo", "Gauchinho", Cargo.FUNCIONARIO);
		assertTrue(gerUsuario.loginID(novo_usuario.getId(), novo_usuario.getSenha()));
	}
	
	@Test
	void testEditarNome() {
		Usuario novo_usuario = gerUsuario.cadastrar("Ronaldo", "Gauchinho", Cargo.FUNCIONARIO);
		assertTrue(gerUsuario.editarNome("Neymar", novo_usuario));
	}
	
	@Test
	void testEditarSenha() {
		Usuario novo_usuario = gerUsuario.cadastrar("Ronaldo", "Gauchinho", Cargo.FUNCIONARIO);
		assertTrue(gerUsuario.editarSenha("MacacoPrego", novo_usuario));
	}

}
