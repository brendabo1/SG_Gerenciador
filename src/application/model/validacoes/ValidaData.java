package application.model.validacoes;


import java.time.LocalDate;
import java.util.ArrayList;


public class ValidaData {
	public static boolean validarData(LocalDate data) {	
		if(data.compareTo(LocalDate.now())>0) return true;
		return false;
	}  

}
