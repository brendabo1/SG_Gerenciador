package application.model.validacoes;

/**
 * Interface respons�vel por identificar e validar as Strings aceitas pelas entidades do sistema
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */

public interface ValidaString {
	
	/**
	 * Remove os espa�os separadores internos de uma string a ser verificada
	 * @param str a palavra a ser analisada
	 * @return a palavra sem espa�os internos
	 */
	
	private static String stringSemEspacos(String str) {
		String str_semEspacos;
		
		if(str.trim().contains(" ")) {
			str_semEspacos = str.replace(" ", "");
			return str_semEspacos;
		}
		return str;
	}
	/**
	 * Verifica se a string possui o tamanho m�nimo especificado
	 * @param entrada string a ser analisada
	 * @param tam_min tam_min comprimento m�nimo ao qual a String entrada deve atender
	 * @return true se a string nao nula possuir comprimento igual ou superior ao tamanho minimo especificado
	 */
	public static boolean stringTamMin(String entrada, int tam_min) {
		return (!entrada.isBlank() && entrada.trim().length() >= tam_min);		
	}
	
	/**
	 * Verifica se a String contendo numeros e/ou letras apenas possui ou n�o um comprimento m�nimo 
	 * @param entrada string a ser analisada 
	 * @param tam_min comprimento m�nimo ao qual a String entrada deve atender
	 * @return true se a entrada for alfanumerica e de comprimento superior ou igual ao tamanho minimo especificado
	 */
	
	public static boolean isAlfaNumerica(String entrada, int tam_min) {
		return (entrada.trim().matches("[a-zA-Z_0-9]+") && entrada.length() >= tam_min);	//aceita letras e numeros, exceto simbolos, espaco e especiais	
	}
	
	/**
	 * Verifica se a String contendo apenas letras possui ou n�o um comprimento m�nimo 
	 * @param entrada string a ser analisada 
	 * @param tam_min comprimento m�nimo ao qual a String entrada deve atender
	 * @return true se a entrada for alfabetica e de comprimento superior ou igual ao tamanho minimo
	 */
	public static boolean isAlfabetica(String entrada, int tam_min) {
		return (entrada.trim().matches("[a-zA-Z]+") && entrada.length() >= tam_min); //1 ou + letras 	nao permite espaco
	}
	
	/**
	 * Verifica se a String contendo apenas numeros possui ou n�o um comprimento m�nimo.
	 * @param entrada string a ser analisada 
	 * @param tam_min comprimento m�nimo ao qual a String entrada deve atender
	 * @return true se a entrada for composta apenas por numeros e de comprimento superior ou igual ao tamanho minimo especificado
	 */
	public static boolean isNumeric(String entrada, int tam_min) {    
		return (entrada.trim().matches("[0-9]+") && entrada.length() >= tam_min);
	}
	
	/**
	 * Verifica se a String contendo apenas numeros possui ou n�o um comprimento exato.
	 * @param entrada string a ser analisada 
	 * @param tam comprimento exato ao qual a String entrada deve atender
	 * @return true se a entrada for composta apenas por numeros e de comprimento igual ao tamanho especificado
	 */
	public static boolean isNumericTamExato(String entrada, int tam) {    
		return (entrada.trim().matches("[0-9]+") && entrada.length() == tam);
	}
	
	/**
	 * Verifica se a String, formada por uma ou mais strings, � alfanumerica e atende ao comprimento m�nimo.
	 * @param entrada sequencia a ser analisada podendo ser composta por uma ou mais palavras separadas por espa�os 
	 * @param tam_min comprimento m�nimo ao qual a String entrada deve atender
	 * @return true se a entrada for composta por letras e/ou numeros e de comprimento superior ou igual ao tamanho minimo especificado
	 */
	public static boolean isAlfaNumericaComposta(String entrada, int tam_min) {
		
		return ValidaString.isAlfaNumerica(ValidaString.stringSemEspacos(entrada), tam_min);
	}
	/**
	 * Verifica se a String, formada por uma ou mais strings, � alfanumerica e atende ao comprimento m�nimo.
	 * @param entrada sequencia a ser analisada podendo ser composta por uma ou mais palavras separadas por espa�os 
	 * @param tam_min comprimento m�nimo ao qual a String entrada deve atender
	 * @return true se a entrada for composta por letras e/ou numeros e de comprimento superior ou igual ao tamanho minimo especificado
	 */
	
	public static boolean isAlfabeticaComposta(String entrada, int tam_min) {
		
		return ValidaString.isAlfabetica(ValidaString.stringSemEspacos(entrada), tam_min);
	}
	
}
