package application.testes.testeValidacoes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.model.validacoes.ValidaString;


class TesteValidaString {
	

	@Test
	void testeIsAlfabeticaVazia_TamMinimoZero() {
		assertFalse(ValidaString.isAlfabetica("", 0));
	}
	
	@Test
	void testeAlfabetica_TamMinimoZero() {
		assertTrue(ValidaString.isAlfabetica("nome", 0));
	}

	@Test
	void testeAlfabeticaVazia_TamMinimoNegativo() {
		assertFalse(ValidaString.isAlfabetica("", -1));
	}
	
	@Test
	void testeIsAlfabetica_TamMinimoNegativo() {
		assertTrue(ValidaString.isAlfabetica("app", -1));
	}
	
	@Test
	void testeIsAlfabetica_ApenasLetras_TamMenor_TamMinimo() {
		assertFalse(ValidaString.isAlfabetica("nome", 6));
	}
	
	@Test
	void testeIsAlfabetica_ApenasLetras_TamIgualTamMinimo() {
		assertTrue(ValidaString.isAlfabetica("teste", 5));
	}
	
	@Test
	void testeIsAlfabetica_LetrasComEspaco() {
		assertFalse(ValidaString.isAlfabetica("Item do cardapio", 5));
	}
		
	@Test
	void testeIsAlfabetica_ComNumero() {
		assertFalse(ValidaString.isAlfabetica("t3ste", 5));
	}
	
	@Test
	void testeIsAlfabetica_ApenasCaracteresEspeciais() {
		assertFalse(ValidaString.isAlfabetica("#$%&*()", 5));
	}
	
	@Test
	void testeIsAlfabetica_Letras_CaracteresEspeciais() {
		assertFalse(ValidaString.isAlfabetica("#Bebida", 5));
	}
	
	@Test
	void testeIsAlfabetica_ApenasEspacos() {
		assertFalse(ValidaString.isAlfabetica("   ", 1));
	}
	
	@Test
	void testeIsAlfabeticaComposta_ApenasEspacos() {
		assertFalse(ValidaString.isAlfabeticaComposta("   ", 1));
	}
	
	@Test
	void testeIsAlfabeticaComposta_PalavraCompostaComNumeros() {
		assertFalse(ValidaString.isAlfabeticaComposta("Batatinha Frita 123", 1));
	}
	
	@Test
	void testeIsAlfabeticaComposta_PalavraComposta() {
		assertTrue(ValidaString.isAlfabeticaComposta("Batata Frita", 3));
	} 
	
	@Test
	void testeIsAlfaNumerica_Vazia_TamMinimoZero() {
		assertFalse(ValidaString.isAlfaNumerica("", 0));
	}
	
	@Test
	void testeIsAlfaNumerica_ApenasEspacos_TamMinimoZero() {
		assertFalse(ValidaString.isAlfaNumerica("   ", 0));
	}
	
	@Test
	void testeIsAlfaNumerica_ApenasNumeros() {
		assertTrue(ValidaString.isAlfaNumerica("123", 1));
	}
	
	@Test
	void testeIsAlfaNumerica_NumerosComEspaco() {
		assertFalse(ValidaString.isAlfaNumerica("12 3", 1));
	}
	
	@Test
	void testeIsAlfaNumerica_ApenasLetras() {
		assertTrue(ValidaString.isAlfaNumerica("Algoritmo", 1));
	}
	
	@Test
	void testeIsAlfaNumerica_NomeComposto() {
		assertFalse(ValidaString.isAlfaNumerica("Marco Polo", 1));
	}
	
	@Test
	void testeIsAlfaNumerica_LetrasNumeros() {
		assertTrue(ValidaString.isAlfaNumerica("senha123", 1));
	}
	
	@Test
	void testeIsAlfaNumerica_LetrasNumerosComCaracteresEspeciais() {
		assertFalse(ValidaString.isAlfaNumerica("senh@_1", 1));
	}
	
	@Test
	void testeIsAlfaNumericaComposta_LetrasNumeroComEspaco() {
		assertTrue(ValidaString.isAlfaNumericaComposta("Batatinha 123", 1));
	}
	
	@Test
	void testeIsNumerica_Vazia() {
		assertFalse(ValidaString.isNumeric("", 0));
	}
	
	@Test
	void testeIsNumerica_ApenasEspacos() {
		assertFalse(ValidaString.isNumeric("   ", 1));
	}
	
	@Test
	void testeIsNumerica_ApenasCaracteresEspeciais() {
		assertFalse(ValidaString.isNumeric("#$&* )", 1));
	}
	
	@Test
	void testeIsNumerica_ApenasLetras() {
		assertFalse(ValidaString.isNumeric("preco", 1));
	}
	
	@Test
	void testeIsNumerica_LetrasNumeros() {
		assertFalse(ValidaString.isNumeric("123Testando", 1));
	}
	
	@Test
	void testeIsNumerica_NumerosComCaracteresEspeciais() {
		assertFalse(ValidaString.isNumeric("123...4", 1));
	}
	
	@Test
	void testeIsNumerica_ApenasNumeros() {
		assertTrue(ValidaString.isNumeric("123", 1));
	}
	
	@Test
	void testeIsNumericaTamExato_Vazio_TamZero() {
		assertFalse(ValidaString.isNumericTamExato("", 0));
	}
	
	@Test
	void testeIsNumericaTamExato_Tam5() {
		assertTrue(ValidaString.isNumericTamExato("12345", 5));
	}
	
	@Test
	void testeIsNumericaTamExato_LetrasTam5() {
		assertFalse(ValidaString.isNumericTamExato("cinco", 5));
	}
	
	@Test
	void testeIsNumericaTamExato_StringMenor() {
		assertFalse(ValidaString.isNumericTamExato("1234", 5));
	}
	
	@Test
	void testeIsNumericaTamExato_StringMaior() {
		assertFalse(ValidaString.isNumericTamExato("1234560051", 5));
	}


}
