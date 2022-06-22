 package application.model.validacoes;


/**
 * Interface respons�vel por identificar e validar os numeros aceitos pelas entidades do sistema
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */
public interface ValidaNumero {
	/**
	 * Verifica se o numero � real e positivo
	 * @param num Numero real
	 * @return true se o numero real for positivo ou false caso seja negativo
	 * @throws IllegalArgumentException
	 */
	public static boolean isDoublePositivo(double num) throws IllegalArgumentException{
		return num > 0;
	}
	
	
}
