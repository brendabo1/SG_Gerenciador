package application.model.validacoes;

import java.util.Collection;
import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;

/**
 * Classe responsável por identificar os objetos e valores correspondentes ao fornecedor aceito pelo sistema.
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */
public class ValidaFornecedor implements ValidaString {
	
	/**
	 * Verifica se a string corresponde a um cnpj com 14 dígitos
	 * @param cnpj string a ser analisada
	 * @return true se o cnpj contiver exatamente 14 dígitos numericos
	 */
	public boolean isCnpjValido(String cnpj) {
		return ValidaString.isNumericTamExato(cnpj, 14);
	}
	/**
	 * Verifica se a string contendo letras e/ou numeros corresponde a um nome, simples ou composto, com comprimento mínimo
	 * @param nome string a ser analisada
	 * @return true se o nome nao vazio possuir comprimento igual ou superior a 4 caracteres
	 */
	public boolean isNomeValido(String nome) {
		return ValidaString.isAlfaNumericaComposta(nome, 4);
	}
	
	/**
	 * Varifica se a string atende ao tamanho mínimo
	 * @param endereco string a ser analisada
	 * @return true se o endereco possui comprimento igual ou superior a 4 caracteres
	 */	
	public boolean isEnderecoValido(String endereco) {
		return ValidaString.stringTamMin(endereco, 4);
	}
	
	/**
	 * Verifica se o cnpj já está cadastrado no sistema
	 * @param cnpjBuscado string unica de cada fornecedor a ser verificada
	 * @param bancoDados banco responsável por armazenar as estruturas de dados do sistema
	 * @return true se o cnpj já estiver cadastrado no sistema
	 */
	public boolean isFornecedorExistente(String cnpjBuscado, HashMap<String, Fornecedor> mapFornecedores) {
		Collection<Fornecedor> fornecedores = mapFornecedores.values();
		for(Fornecedor f:fornecedores) {
			if(f.getCNPJ().equals(cnpjBuscado)) 
				return true;
		}
		return false;		
	}
	
	public boolean isFornecedorValido(String nome, String cnpj, String end, HashMap<String, Fornecedor> mapFornecedores) throws IllegalArgumentException{
		if(this.isNomeValido(nome) && this.isCnpjValido(cnpj) && this.isEnderecoValido(end)) {
			if(!this.isFornecedorExistente(cnpj, mapFornecedores)) return true;
		}
		return false;
		
	}

}
