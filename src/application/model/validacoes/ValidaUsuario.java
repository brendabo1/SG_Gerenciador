package application.model.validacoes;

/**
 * Classe responsável por identificar os objetos e valores correspondentes ao usuario aceito pelo sistema.
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */
public class ValidaUsuario {
	/**
	 * Verifica se a string nao vazia correspondente ao nome possui tamanho minimo 3
	 * @param nome String correspondente ao nome
	 * @return true se o nome for valido ou false caso nao atinja os requisitos
	 */
	public boolean isNomeValido(String nome) {
		return ValidaString.isAlfaNumericaComposta(nome, 3);
	}
	/**
	 * Verifica se a string simple e nao vazia correspondente a senha possui tamanho minimo 4
	 * @param senha String correspondente a senha
	 * @return true se a senha for valida ou false caso nao atinja os requisitos
	 */
	public boolean isSenhaValido(String senha) {
		return ValidaString.stringTamMin(senha, 4);
	}
	
	/**
	 * Verifica se o usuario é valido a partir da validacao de nome e senha
	 * @param nome String correspondente ao nome
	 * @param senha String correspondente a senha
	 * @return true caso atinnja os requisitos ou false contrario
	 * @throws IllegalArgumentException
	 */
	public boolean isUsuarioValido(String nome, String senha) {
		if(this.isNomeValido(nome)) {
			if(this.isSenhaValido(senha)) return true;
		}
		return false;
	}	

}
