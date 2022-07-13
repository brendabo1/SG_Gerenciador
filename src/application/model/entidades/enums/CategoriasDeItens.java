package application.model.entidades.enums;

public enum CategoriasDeItens {
	PRATO("Prato"), 
	SOBREMESA("Sobremesa"), 
	BEBIDA("Bebida");

	private String descricao;
	
	CategoriasDeItens(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
	    return this.descricao;
	}

}
