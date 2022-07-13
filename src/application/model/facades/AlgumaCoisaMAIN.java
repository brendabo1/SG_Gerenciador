package application.model.facades;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Usuario;
import application.model.entidades.enums.Cargo;

public class AlgumaCoisaMAIN {
	public static void main(String[] args) {
		BancoDeDados banco = BancoDeDados.getInstance();
		FacadePrincipal facade = new FacadePrincipal(banco);
		
		Cargo cargo = Cargo.FUNCIONARIO;
		Cargo cargo2 = Cargo.FUNCIONARIO;
		
		System.out.println(cargo == cargo2);
		System.out.println(cargo.equals(cargo2));
		
		
		try {
		facade.cadastrarFornecedor("Antonio", "11111111111", "Babababa123");
		}
		catch (IllegalArgumentException e) {
			e.getMessage();
		} {
			
		}
		
		HashMap<String, Fornecedor> hashzin = banco.getMap_fornecedores();
		
	for (Fornecedor f : hashzin.values())
		System.out.println(f.getNome());
		
	facade.cadastrarUsuario("BATATA", "bilululu123", Cargo.GERENTE);
	
	for (Usuario user : banco.getMap_usuarios().values())
		System.out.println(user.getNome());
	
	}
	

}
