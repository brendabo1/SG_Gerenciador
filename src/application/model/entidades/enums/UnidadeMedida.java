package application.model.entidades.enums;

public enum UnidadeMedida {
	KG("Kg"),
	G("G"),
	L("L"),
	ML("mL"),
	UNIDADE("UNIDADE");
	
	private String descricao;
	
	UnidadeMedida(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
	    return this.descricao;
	}
}
