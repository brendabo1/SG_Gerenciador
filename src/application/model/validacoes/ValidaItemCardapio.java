package application.model.validacoes;

/**
 * Classe respons�vel por identificar os objetos e valores correspondentes ao Item do Cardapio aceito pelo sistema.
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */
public class ValidaItemCardapio {
	/**
	 * Verifica se a string nao vazia correspondente ao nome possui tamanho minimo 3
	 * @param nomeItem Nome a ser atribuido ao item
	 * @return true se o nome for valido ou false caso nao atinja os requisitos
	 */
	public boolean isNomeValido(String nomeItem) {
		return ValidaString.stringTamMin(nomeItem, 3);
	}
	/**
	 * Verifica se o numero correspondente ao preco do item � um numero real positivo
	 * @param preco Numero real a ser verificado
	 * @return true se o numero real for positivo ou false caso seja negativo
	 * @throws IllegalArgumentException
	 */
	public boolean isPrecoValido(Double preco) throws IllegalArgumentException{
		return ValidaNumero.isDoublePositivo(preco);
	}
	
}
