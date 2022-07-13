package application.testes.testeValidacoes;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import application.model.validacoes.ValidaData;


class TesteValidaEntidade {

	@Test
	void testValidarData_ExistenteFutura() {
		LocalDate data = LocalDate.of(2022, 8, 15);   // 15/8/2022
		assertTrue(ValidaData.validarData(data));
	}
	
	@Test
	void testValidarData_ExistentePassada() {
		LocalDate data = LocalDate.of(2022, 1, 10);  //  10/01/2022
		assertFalse(ValidaData.validarData(data));
	}
		

}