package application.testes.testeValidacoes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.model.validacoes.ValidaNumero;


public class TesteValidaNumero {

	@Test
	void test_isDoublePositivo_Zero() {
		assertFalse(ValidaNumero.isDoublePositivo(0.0));
	}
	
	@Test
	void test_isDoublePositivo_InteiroNegativo() {
		assertFalse(ValidaNumero.isDoublePositivo(-14));
	}
	
	@Test
	void test_isDoublePositivo_InteiroPositivo() {
		assertTrue(ValidaNumero.isDoublePositivo(14));
		
	}
	
	@Test
	void test_isDoublePositivo_DoublePositivo() {
		assertTrue(ValidaNumero.isDoublePositivo(10.90));
	}


}
