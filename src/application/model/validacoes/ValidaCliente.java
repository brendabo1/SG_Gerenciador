package application.model.validacoes;

/**
 * Classe responsável por identificar os objetos e valores correspondentes ao cliente aceito pelo sistema.
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */
public class ValidaCliente {
	/**
	 * Verifica se a string nao vazia correspondente ao nome possui tamanho minimo 3
	 * @param nome String correspondente ao nome
	 * @return true se o nome for valido ou false caso nao atinja os requisitos
	 */
	public boolean isNomeValido(String nome) {
		return ValidaString.isAlfabeticaComposta(nome, 3);
	}
	/**
	 * Verifica se a string simple e nao vazia correspondente ao cpf possui 11 digitos
	 * @param cpf String correspondente ao cpf
	 * @return true se o cpf for valida ou false caso nao atinja os requisitos
	 */
	public boolean isCPFValido(String cpf) {
		return ValidaString.isNumericTamExato(cpf, 11);
	}
	
	/**
	 * Varifica se a string numerica atende ao tamanho mínimo
	 * @param telefone string a ser analisada
	 * @return true se o endereco possui comprimento igual ou superior a 8 numeros
	 */	
	public boolean isTelefoneValido(String telefone) {
		return ValidaString.isNumeric(telefone, 8);
	}
	
	/**
	 * Varifica se a string atende ao tamanho mínimo
	 * @param endereco string a ser analisada
	 * @return true se o endereco possui comprimento igual ou superior a 4 caracteres
	 */	
	public boolean isEmailValido(String email) {
		return email.contains("@");
	}
	
	/**
	 * Verifica se o cliente é valido a partir da validacao de nome, cpf, email e endereco
	 * @param nome String correspondente ao nome
	 * @param cpf String correspondente ao cpf
	 * @param email String correspondente ao email
	 * @param tel String correspondente ao telefone
	 * @return true caso atinnja os requisitos ou false contrario
	 * @throws IllegalArgumentException
	 */
	public boolean isUsuarioValido(String nome, String cpf, String email, String tel) {
		if(isNomeValido(nome) && isCPFValido(cpf) && isEmailValido(email) && isTelefoneValido(tel)) {
			return true;
		}
		return false;
	}	

}
