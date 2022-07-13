package application.model.entidades.enums;

public enum Cargo  {
	GERENTE("Gerente"),
	FUNCIONARIO("Funcionario");
	
	private String descricao;
	
	Cargo(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
	    return this.descricao;
	}
}
