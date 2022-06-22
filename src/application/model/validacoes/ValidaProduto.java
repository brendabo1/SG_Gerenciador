package application.model.validacoes;

/**
 * Classe respons�vel por identificar os objetos e valores correspondentes ao produto aceito pelo sistema.
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */

public class ValidaProduto implements ValidaString, ValidaNumero{
	/**
	 * Verifica se a string nao vazia correspondente ao nome possui tamanho minimo 3
	 * @param nome String correspondente ao nome do produto
	 * @return true se o nome for valido ou false caso nao atinja os requisitos
	 */
	public boolean isNomeValido(String nome) {
		return ValidaString.stringTamMin(nome, 3);
	}
	/**
	 * Verifica se a quantidade correspondente ao conteudo do produto � um numero valido
	 * @param qnt Quantidade numerica do conteudo do produto
	 * @return true se o numero real for valido ou false caso nao atinja os requisitos
	 */
	public boolean isConteudoProdutoValido(Double qnt) {
		return ValidaNumero.isDoublePositivo(qnt);
	}
	
	/**
	 * Verifica se o produto � valido a partir da validacao de nome e quantidade do conteudo
	 * @param nome String correspondente ao nome do produto
	 * @param qnt Quantidade numerica do conteudo do produto
	 * @return true caso atinnja os requisitos ou false caso nao atinja
	 * @throws IllegalArgumentException
	 */
	public boolean isProdutoValido(String nome, double qnt) throws IllegalArgumentException{
		if(this.isNomeValido(nome) && this.isConteudoProdutoValido(qnt)) return true;
		return false;
		
	}
}
